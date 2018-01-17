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
import com.manshop.android.R;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.okHttp.okHttpUtil;

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

//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    String path = okHttpUtil.doPost("http://10.0.2.2:8080/user/login", param);
//                    Message msg = new Message();
//                    msg.obj = path;
//                    handler.sendMessage(msg);
//
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            String s = (String) msg.obj;
//            JSONObject json = JSON.parseObject(s);
//            if(json.getInteger("code")==200){
////                Toast.makeText(getApplicationContext(),"successful",Toast.LENGTH_SHORT).show();
//            }else if(json.getInteger("code") == 205){
//                Toast.makeText(getApplicationContext(),json.getString("data"),Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
        final Map<String, String> param = new HashMap<>();
        param.put("phone", phone);
        param.put("password", password);
        okhttp.doPost("http://10.0.2.2:8080/user/login", new CallBack(LoginActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(),"手机号或密码错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        },param);
    }
}
