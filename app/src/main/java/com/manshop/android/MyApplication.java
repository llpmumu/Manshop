package com.manshop.android;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by HP on 2017/12/27.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
