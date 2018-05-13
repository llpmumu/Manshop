package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.manshop.android.R;
import com.manshop.android.ui.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    private final int SPLASH_DISPLAY_LENGHT = 3000;  //延迟3秒
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

}
