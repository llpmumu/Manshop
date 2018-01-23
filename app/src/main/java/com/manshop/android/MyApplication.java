package com.manshop.android;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.manshop.android.model.User;

/**
 * Created by HP on 2017/12/27.
 */

public class MyApplication extends Application {
    private User user;
    private SharedPreferences share;

    @Override
    public void onCreate() {
        super.onCreate();
        share = getSharedPreferences("User", MODE_PRIVATE);
        this.myApplication = this ;
        Fresco.initialize(this);
    }

    public static MyApplication myApplication ;

    public MyApplication () {};

    public static MyApplication getInstance(){
        return myApplication ;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId(){
        return share.getInt("id",-1);
    }
}
