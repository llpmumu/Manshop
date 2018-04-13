package com.manshop.android.ui.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.R;
import com.manshop.android.model.Address;
import com.manshop.android.model.Order;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.utils.Constant;
import com.manshop.android.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class DeliveryActivity extends BaseActivity {
    @Bind(R.id.userMsg)
    TextView tvUserMsg;
    @Bind(R.id.address)
    TextView tvAddress;
    @Bind(R.id.et_deli_num)
    EditText etDaliNum;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    @Bind(R.id.tv_not_deli)
    TextView tvNotDeli;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        ButterKnife.bind(this);
        showToolbar();
        getOrderData();
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    //加载标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public void getOrderData() {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", getIntent().getStringExtra("oid"));
        okHttp.doPost(Constant.baseURL + "order/getOneOrder", new CallBack(DeliveryActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Order order = json.getObject("data", Order.class);
                Address address = order.getAddress();
                tvUserMsg.setText(address.getConsignee() + "(" + address.getAddphone() + ")");
                tvAddress.setText(address.getAddress());
            }
        }, params);
    }

    @OnClick(R.id.btn_commit)
    public void commit() {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", getIntent().getStringExtra("oid"));
        params.put("trackingnum", etDaliNum.getText().toString());
        params.put("state", 1);
        if (etDaliNum.getText().toString().equals(""))
            params.put("delivery", 1);
        else
            params.put("delivery", 2);
        okHttp.doPost(Constant.baseURL + "order/updataOrder", new CallBack(DeliveryActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
//                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                String result = json.getObject("data", String.class);
                System.out.print(result);
                ToastUtil.shortToast(DeliveryActivity.this, "提交成功");
                finish();
            }
        }, params);
    }

    @OnClick(R.id.tv_not_deli)
    public void commitNot() {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", getIntent().getStringExtra("oid"));
        params.put("trackingnum", "");
        params.put("state", 1);
        params.put("delivery", 1);
        okHttp.doPost(Constant.baseURL + "order/updataOrder", new CallBack(DeliveryActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
//                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                String result = json.getObject("data", String.class);
                System.out.print(result);
                ToastUtil.shortToast(DeliveryActivity.this, "提交成功");
                finish();
            }
        }, params);
    }
}
