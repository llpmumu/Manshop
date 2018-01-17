package com.manshop.android.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manshop.android.R;
import com.manshop.android.model.User;
import com.manshop.android.model.msg.LoginRespMsg;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.okHttp.okHttpUtil;
import com.manshop.android.util.Constant;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;


public class RegisterActivity extends BaseActivity {
    //跳转登录界面
    private TextView TvLogin;
    //文本框
    private EditText etPhone;
    private EditText etPassword;
    private okHttpUtil okhttp = okHttpUtil.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        etPhone = (EditText) findViewById(R.id.et_register_phone);
        etPassword = (EditText) findViewById(R.id.et_register_password);
        TvLogin = (TextView) findViewById(R.id.tv_login);
        TvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void register(View v) {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        if (phone.equals("")) {
            Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (phone.length() != 11) {
            Toast.makeText(getApplicationContext(), "请输入标准手机格式", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.equals("")) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < 6 || password.length() > 16) {
            Toast.makeText(getApplicationContext(), "密码长度为6-16", Toast.LENGTH_SHORT).show();
            return;
        }
        final Map<String, String> param = new HashMap<>();
        param.put("phone", phone);
        param.put("password", password);
        okhttp.doPost(Constant.baseURL + "user/register", new CallBack(RegisterActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
//                if (userLoginRespMsg.getStatus() == 1) {
//                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
            }

//            @Override
//            public void callBackSuccess(Response response, Object o) throws IOException {
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//            }
        }, param);
    }
}
