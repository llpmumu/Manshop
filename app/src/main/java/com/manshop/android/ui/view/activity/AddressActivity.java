package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.AddressAdapter;
import com.manshop.android.model.Address;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.util.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class AddressActivity extends BaseActivity {
    private List<Address> mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        init();
    }

    public void init() {
        showToolbar();
        initAddress();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView_address);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        AddressAdapter adapter = new AddressAdapter(this, mAddress);
        recyclerView.setAdapter(adapter);
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
                startActivity(new Intent(AddressActivity.this, MainActivity.class));
                break;
            case R.id.item_newaddress:
                startActivity(new Intent(AddressActivity.this, EditAddressActivity.class));
                break;
            default:
        }
        return true;
    }

    private OkHttp okHttp = OkHttp.getOkhttpHelper();
    private void initAddress() {
        mAddress = new ArrayList<>();
//        mAddress.add(new Address(1, "Jame", "13459427684", "杭州市拱墅区湖州街51号,浙江大学城市学院", true));
//        mAddress.add(new Address(1, "Jame", "13459427684", "杭州市拱墅区湖州街51号,浙江大学城市学院", false));
//        mAddress.add(new Address(1, "Jame", "13459427684", "杭州市拱墅区湖州街51号,浙江大学城市学院", false));
//        mAddress.add(new Address(1, "Jame", "13459427684", "杭州市拱墅区湖州街51号,浙江大学城市学院", false));
//        mAddress.add(new Address(1, "Jame", "13459427684", "杭州市拱墅区湖州街51号,浙江大学城市学院", false));
        Map<String, Object> param = new HashMap<>();
        param.put("uid", MyApplication.getInstance().getUserId());
        okHttp.doPost(Constant.baseURL + "address/getAddress", new CallBack(AddressActivity.this) {
            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "获取地址失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                JSONArray addressJson = json.getJSONArray("data");
                List<Address> maddress = null;
//                mAddress = new ArrayList<>();
                for (int i = 0; i < addressJson.size(); i++) {
                    //提取出family中的所有
                    Address address = json.getObject("data", Address.class);
                    address.setUser(MyApplication.getInstance().getUser());
                    maddress.add(address);
                }
            }
        },param);
    }
}
