package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.AddressAdapter;
import com.manshop.android.model.Address;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class ListAddressActivity extends BaseActivity {
    private List<Address> mAddress = new ArrayList<>();
    private RecyclerView recyclerView;
    private AddressAdapter adapter;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        init();
    }

    public void init() {
        showToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.recycleView_address);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        initAddress();
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    //加载标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_toolbar_address, menu);
        return true;
    }

    //标题栏按钮功能实现
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getIntent().getBooleanExtra("order", false)) {
                    finish();
                } else {
                    startActivity(new Intent(ListAddressActivity.this, MainActivity.class));
                }
                break;
            case R.id.item_newaddress:
                Intent intent = new Intent(ListAddressActivity.this, NewAddressActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    public void initAddress() {
        mAddress.clear();
        Map<String, Object> param = new HashMap<>();
        param.put("uid", MyApplication.getInstance().getUserId());
        okHttp.doPost(Constant.baseURL + "address/getAddress", new CallBack(ListAddressActivity.this) {
            @Override
            public void onError(Response response, Exception e) throws IOException {
//                Toast.makeText(getApplicationContext(), "获取地址失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Object jsonArray = json.get("data");
                System.out.println(jsonArray);
                List<Address> listAddress = JSON.parseArray(jsonArray + "", Address.class);
                for (Address address : listAddress) {
                    mAddress.add(address);
                }
                adapter = new AddressAdapter(ListAddressActivity.this, mAddress);
                recyclerView.setAdapter(adapter);
//                ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            }
        }, param);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("----", "onRestart: 刷新");
        initAddress();
    }

}
