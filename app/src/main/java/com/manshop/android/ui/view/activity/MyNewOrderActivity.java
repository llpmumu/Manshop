package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.model.Address;
import com.manshop.android.model.Goods;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.util.Constant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class MyNewOrderActivity extends BaseActivity {
    //地址信息
    @Bind(R.id.userMsg)
    TextView tvUserMsg;
    @Bind(R.id.address)
    TextView tvAddress;

    //订单信息
    @Bind(R.id.good_picture)
    ImageView ivGpic;
    @Bind(R.id.good_title)
    TextView tvGtitle;
    @Bind(R.id.good_price)
    TextView tvGprice;
    @Bind(R.id.warePrice)
    TextView tvAllPrice;

    private Goods good;
    private Intent intent;
    private OkHttp okhttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        ButterKnife.bind(MyNewOrderActivity.this);
        showToolbar();
        init();
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
        addAddress();
        getGoodInfo();
    }

    @OnClick(R.id.btn_address_select)
    public void selectAdr() {
        startActivity(new Intent(MyNewOrderActivity.this, ListAddressActivity.class));
    }

    //填写收货地址
    public void addAddress() {
        final Map<String, Object> params = new HashMap<>();
        params.put("uid", MyApplication.getInstance().getUserId());
        okhttp.doPost(Constant.baseURL + "address/getOneAddress", new CallBack(MyNewOrderActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "获取地址失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Address address = json.getObject("data", Address.class);
                Log.d("order", "55" + tvUserMsg);
                tvUserMsg.setText(address.getConsignee() + "(" + address.getAddphone() + ")");
                tvAddress.setText(address.getAddress());
            }
        }, params);
    }

    //获取商品信息
    public void getGoodInfo() {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", intent.getIntExtra("gid", 0));
        okhttp.doPost(Constant.baseURL + "goods/getOneGood", new CallBack(MyNewOrderActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "获取商品失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                good = json.getObject("data", Goods.class);
                String picture = good.getPicture();
                String[] txtpicture = picture.split(";");
                Glide.with(MyNewOrderActivity.this).load(txtpicture[0]).into(ivGpic);
                tvGtitle.setText(good.getTitle());
                tvGprice.setText(good.getPrice() + "￥");
                tvAllPrice.setText(good.getPrice() + "￥");
            }
        }, params);
    }

    //提交订单
    public void submitOrder(View view) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", null);
        params.put("gid", intent.getIntExtra("gid", 0));
        params.put("suid", good.getUser().getId());
        params.put("buid", MyApplication.getInstance().getUserId());
        params.put("state", 1);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        params.put("ordertime", date);
        okhttp.doPost(Constant.baseURL + "order/newOrder", new CallBack(MyNewOrderActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                String oid = json.getObject("data",String.class);

                Intent intentToDetail = new Intent(MyNewOrderActivity.this, OrderDetailActivity.class);
                intentToDetail.putExtra("oid",oid);
                startActivity(intentToDetail);
            }
        }, params);
    }
}
