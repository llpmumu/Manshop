package com.manshop.android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.manshop.android.model.User;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by Lin on 2017/12/27.
 */

public class MyApplication extends Application {
    private User user;
    private SharedPreferences share;

    @Override
    public void onCreate() {
        super.onCreate();
        share = getSharedPreferences("User", MODE_PRIVATE);
        this.myApplication = this;
        Fresco.initialize(this);
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
    }

    public static MyApplication myApplication;

    public MyApplication() {
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return share.getInt("id", -1);
    }

}

