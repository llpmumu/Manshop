package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.GoodDetailPicAdapter;
import com.manshop.android.entity.Event;
import com.manshop.android.entity.EventType;
import com.manshop.android.model.Goods;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.util.Constant;
import com.manshop.android.util.HandleResponseCode;
import com.manshop.android.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.eventbus.EventBus;
import okhttp3.Response;

public class GoodDetailActivity extends BaseActivity {
    private RoundedImageView RivPhoto;
    private TextView tvUsername;
    private TextView tvPrice;
    private TextView tvDetail;
    private RecyclerView rePic;

    private Intent intent;
    private GoodDetailPicAdapter adapter;

    private Conversation conversation;
    private UserInfo mUserInfo;
    private String mTargetId;
    private String mUserID;

    OkHttp okHttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        showToolbar();
        init();
        setData();
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void init() {
        intent = getIntent();
        RivPhoto = (RoundedImageView) findViewById(R.id.user_photo);
        tvUsername = (TextView) findViewById(R.id.user_name);
        tvPrice = (TextView) findViewById(R.id.good_price);
        tvDetail = (TextView) findViewById(R.id.good_detail);
        rePic = (RecyclerView) findViewById(R.id.recycle_pic);
        LinearLayoutManager manager = new LinearLayoutManager(GoodDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        rePic.setLayoutManager(manager);
        mTargetId = getIntent().getStringExtra(MyApplication.TARGET_ID);
        mUserID = getIntent().getStringExtra("targetId");
        getUserInfo();
    }

    public void setData() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", intent.getIntExtra("gid", 0));
        okHttp.doPost(Constant.baseURL + "goods/getOneGood", new CallBack(GoodDetailActivity.this) {
            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Goods good = json.getObject("data", Goods.class);
                Glide.with(GoodDetailActivity.this).load(good.getUser().getHead()).into(RivPhoto);
                tvUsername.setText(good.getUser().getUsername());
                tvPrice.setText(good.getPrice()+"￥");
                tvDetail.setText(good.getDetail());
                TextPaint paint = tvDetail.getPaint();
                paint.setFakeBoldText(true);

                //物品图片
                String picture = good.getPicture();
                adapter = new GoodDetailPicAdapter(GoodDetailActivity.this, StringUtil.getInstance().spiltPic(picture));
                rePic.setAdapter(adapter);
                rePic.setFocusableInTouchMode(false);
                rePic.requestFocus();
            }
        }, params);
    }

    public void buy(View v) {
        Intent intentToOrder = new Intent(GoodDetailActivity.this, MyNewOrderActivity.class);
        intentToOrder.putExtra("gid", intent.getIntExtra("gid", 0));
        startActivity(intentToOrder);
    }

    public void contact(View view) {
        String title = mUserInfo.getNickname();
        if (TextUtils.isEmpty(title)) {
            title = mUserInfo.getUserName();
        }
        Intent intentToMsg = new Intent(this, ChatActivity.class);
        intentToMsg.putExtra(MyApplication.CONV_TITLE, title);
        intentToMsg.putExtra(MyApplication.TARGET_ID, mUserInfo.getUserName());
        intentToMsg.putExtra(MyApplication.TARGET_APP_KEY, mUserInfo.getAppKey());
        startActivity(intentToMsg);
        //如果会话为空，使用EventBus通知会话列表添加新会话
        if (conversation == null) {
            conversation = Conversation.createSingleConversation(mTargetId, MyApplication.APP_KEY);
            EventBus.getDefault().post(new Event.Builder()
                    .setType(EventType.createConversation)
                    .setConversation(conversation)
                    .build());
        }
//        finish();
    }

    //    获取用户信息
    public void getUserInfo() {
        conversation = JMessageClient.getSingleConversation(mTargetId, MyApplication.APP_KEY);
        if (TextUtils.isEmpty(mTargetId) && !TextUtils.isEmpty(mUserID)) {
            mTargetId = mUserID;
        }
        JMessageClient.getUserInfo(mTargetId, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (i == 0) {
                    mUserInfo = userInfo;
                } else {
                    HandleResponseCode.onHandle(GoodDetailActivity.this, i, false);
                }
            }
        });
    }
}
