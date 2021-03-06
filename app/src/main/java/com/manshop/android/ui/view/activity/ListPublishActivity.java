package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.ListPublishAdapter;
import com.manshop.android.model.Goods;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.utils.Constant;
import com.manshop.android.utils.ImageLoadUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class ListPublishActivity extends BaseActivity {
    private List<Goods> mGood = new ArrayList<>();
    private ListPublishAdapter adapter;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        showToolbar();
        initData();
        recyclerview = (RecyclerView) findViewById(R.id.recycle_publish);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    //加载标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_add, menu);
        return true;
    }

    //标题栏按钮功能实现
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_add:
                Intent intent = new Intent(ListPublishActivity.this, NewPublishActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
        }
        return true;
    }

    private OkHttp okhttp = OkHttp.getOkhttpHelper();

    public void initData() {
        mGood.clear();
        final List<String> mPic = new ArrayList<>();
        final Map<String, Object> param = new HashMap<>();
        param.put("uid", MyApplication.getInstance().getUserId());
        okhttp.doPost(Constant.baseURL + "goods/getMyGood", new CallBack(ListPublishActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                Log.d("good", "success");
                JSONObject json = JSON.parseObject((String) o);
                Object jsonArray = json.get("data");
                System.out.println(jsonArray);
                List<Goods> listGood = JSON.parseArray(jsonArray + "", Goods.class);
                Log.d("good", " " + mGood.size());
                for (Goods good : listGood) {
                    Log.d("good", " " + good.toString());
                    good.setPics(ImageLoadUtils.displayGoodsImage(good.getPicture()));
                    mGood.add(good);
                    Log.d("good", " goodtime    " + good.getGoodtime());
                }
                adapter = new ListPublishAdapter(ListPublishActivity.this, mGood);
                recyclerview.setNestedScrollingEnabled(false);
                recyclerview.setAdapter(adapter);
            }
        }, param);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("----", "onRestart: 刷新");
        initData();
    }
}
