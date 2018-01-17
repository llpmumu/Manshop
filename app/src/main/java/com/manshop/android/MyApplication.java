package com.manshop.android;

import android.app.Application;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.manshop.android.model.User;
import com.manshop.android.util.UserLocalData;

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
