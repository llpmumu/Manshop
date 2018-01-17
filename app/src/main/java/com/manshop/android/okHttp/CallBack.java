package com.manshop.android.okHttp;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.manshop.android.ui.view.LoginActivity;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lin on 2018/1/17.
 */

public abstract class CallBack extends BaseCallback {
    private Context mContext;

    public CallBack(Context context) {
        mContext = context;
    }

    @Override
    public void onRequestBefore() {
    }

    @Override
    public void onFailure(Request request, IOException e) {
    }

    @Override
    public void onTokenError(Response response) {
        Toast.makeText(mContext, "TokenError", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }
}
