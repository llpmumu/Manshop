package com.manshop.android.ui.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.model.User;
import com.manshop.android.model.msg.LoginRespMsg;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.okHttp.okHttpUtil;
import com.manshop.android.util.Constant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private TextView tvRegister;
    //文本框
    private TextView etPhone;
    private TextView etPassword;
    private okHttpUtil okhttp = okHttpUtil.getOkhttpHelper() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public void init(){
        etPhone = (TextView) findViewById(R.id.et_phone);
        etPassword = (TextView) findViewById(R.id.et_password);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public void login(View v) {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        if (phone.equals("")) {
            Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        final Map<String, String> param = new HashMap<>();
        param.put("phone", phone);
        param.put("password", password);
        okhttp.doPost(Constant.baseURL+"user/login", new CallBack(LoginActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(),"手机号或密码错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {

            }

//            @Override
//            public void callBackSuccess(Response response, Object o) throws IOException {
//                LoginRespMsg<User> userLoginRespMsg = new LoginRespMsg<>;

        },param);
    }
}
