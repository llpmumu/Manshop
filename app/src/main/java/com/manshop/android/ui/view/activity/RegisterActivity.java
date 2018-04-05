package com.manshop.android.ui.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manshop.android.R;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.util.Constant;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;
import okhttp3.Response;

import static cn.jpush.im.android.api.model.UserInfo.Field.gender;


public class RegisterActivity extends BaseActivity {
    //跳转登录界面
    private TextView TvLogin;
    //文本框
    private EditText etNickName;
    private EditText etPhone;
    private EditText etPassword;
    private OkHttp okhttp = OkHttp.getOkhttpHelper();

    //极光注册
    private static final String TAG = "RegisterActivity";
    private RegisterOptionalUserInfo registerOptionalUserInfo;

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
        etNickName = (EditText) findViewById(R.id.et_register_nickName);
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
        final String phone = etPhone.getText().toString();
        final String password = etPassword.getText().toString();
        String nickName = etNickName.getText().toString();
        final Map<String, Object> param = new HashMap<>();
        param.put("username",nickName);
        param.put("phone", phone);
        param.put("password", password);
        if (!setRegisterOptionalParameters()) {
            return;
        }
        okhttp.doPost(Constant.baseURL + "user/register", new CallBack(RegisterActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                registerOptionalUserInfo.setAvatar("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=256814278,2196155154&fm=27&gp=0.jpg");
                JMessageClient.register(phone, password, registerOptionalUserInfo, new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String registerDesc) {
                        if (responseCode == 0) {
                            Log.i(TAG, "JMessageClient.register " + ", responseCode = " + responseCode + " ; registerDesc = " + registerDesc);
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i(TAG, "JMessageClient.register " + ", responseCode = " + responseCode + " ; registerDesc = " + registerDesc);
                        }
                    }
                });
            }
        }, param);
    }

    private boolean setRegisterOptionalParameters() {
        registerOptionalUserInfo = new RegisterOptionalUserInfo();
        if (!TextUtils.isEmpty(etNickName.getText())) {
            registerOptionalUserInfo.setNickname(etNickName.getText().toString());
        }else {
            Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(etPhone.getText())) {
            Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPhone.getText().length() != 11) {
            Toast.makeText(getApplicationContext(), "请输入标准手机格式", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(etPassword.getText())) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPassword.getText().toString().length() < 6 || etPassword.getText().toString().length() > 16) {
            Toast.makeText(getApplicationContext(), "密码长度为6-16", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
