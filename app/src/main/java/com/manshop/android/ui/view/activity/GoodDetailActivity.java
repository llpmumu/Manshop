package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
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
import com.manshop.android.R;
import com.manshop.android.adapter.GoodDetailPicAdapter;
import com.manshop.android.model.Goods;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.util.Constant;
import com.manshop.android.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class GoodDetailActivity extends BaseActivity {
    private RoundedImageView RivPhoto;
    private TextView tvUsername;
    private TextView tvPrice;
    private TextView tvDetail;
    private RecyclerView rePic;

    private Intent intent;
    private GoodDetailPicAdapter adapter;

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
        RivPhoto = (RoundedImageView) findViewById(R.id.user_photo);
        tvUsername = (TextView) findViewById(R.id.user_name);
        tvPrice = (TextView) findViewById(R.id.good_price);
        tvDetail = (TextView) findViewById(R.id.good_detail);
        rePic = (RecyclerView) findViewById(R.id.recycle_pic);
        LinearLayoutManager manager = new LinearLayoutManager(GoodDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        rePic.setLayoutManager(manager);
    }

    public void setData() {
        intent = getIntent();
        Map<String, Object> params = new HashMap<>();
        params.put("id", intent.getIntExtra("gid",0));
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
                tvPrice.setText(good.getPrice());
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
        Intent intentToMsg = new Intent(GoodDetailActivity.this, DialogueActivity.class);
        intentToMsg.putExtra("sid", intent.getIntExtra("uid", 0));
        startActivity(intentToMsg);
    }
}
