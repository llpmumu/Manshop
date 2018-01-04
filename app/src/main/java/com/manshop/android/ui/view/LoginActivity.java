package com.manshop.android.ui.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.manshop.android.R;
import com.manshop.android.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    TextView tvRegister;
    TextView etUername;
    TextView etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        showToolbar();
        etUername = (TextView) findViewById(R.id.et_uername);
        etPassword = (TextView) findViewById(R.id.et_password);
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(v);
            }
        });
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void doLogin(View v) {
        String username = etUername.getText().toString();
        String pwd = etPassword.getText().toString();
        System.out.println(username + "--" + pwd);
        if (username == null) {
            Toast.makeText(v.getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
        } else if (username.length() != 11) {
            Toast.makeText(v.getContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
        }
        if (pwd == null) {
            Toast.makeText(v.getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
        }

        String uname = "12345678900";
        String password = "admin";
        if (username.equals(uname) && pwd.equals(password)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else
            Toast.makeText(v.getContext(), "账号或者密码错误", Toast.LENGTH_SHORT).show();
    }
}
