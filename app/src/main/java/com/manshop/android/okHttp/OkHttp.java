package com.manshop.android.okHttp;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.manshop.android.myException.GET_RESPONSE_MESSAGE_FAILURE;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Lin on 2018/1/16.
 */

public class OkHttp {
    private static OkHttpClient client;
    private Handler handler;
    private Gson gson;
    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");


    //定义枚举类型
    enum METHOD_TYPE {
        GET,
        POST
    }

    //    static {
//        client = new OkHttpClient();
//    }
    private OkHttp() {
        client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttp getOkhttpHelper() {
        return new OkHttp();
    }


    //    public static String doGet(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }
//
//    public static String doPost(String url, Map param) throws IOException {
//        RequestBody body = RequestBody.create(JSON_TYPE, JSON.toJSONString(param));
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }


    //提交get请求
    public void doGet(String url, BaseCallback callback) {
        doGet(url, callback, null);
    }

    public void doGet(String url, BaseCallback callback, Map<String,Object> param) {
        Request request = buildRequest(url, METHOD_TYPE.GET, param);
        doResponse(request, callback);
    }

    //提交post请求
    public void doPost(String url, BaseCallback callback, Map<String,Object> param) {
        Request request = buildRequest(url, METHOD_TYPE.POST, param);
        doResponse(request, callback);
    }

    private Request buildRequest(String url, METHOD_TYPE method_type, Map<String,Object> param) {
        Request.Builder builder = new Request.Builder();

        if (method_type == METHOD_TYPE.GET) {
            builder.url(url);
            builder.get();
        } else if (method_type == METHOD_TYPE.POST) {
            RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(param));
            builder.url(url);
            builder.post(requestBody);
        }
        return builder.build();
    }

    //提交请求
    private void doResponse(final Request request, final BaseCallback callback) {

        //在进行提交之前进行
        callback.onRequestBefore();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //提交成功，得到回返信息
                    String result = response.body().string();
                    JSONObject json = JSON.parseObject(result);
                    Log.d("----", "------------------------------------result:" + result);
                    Log.d("----", "------------------------------------json:" + json);
                    if (json.getInteger("code") == 200) {
                        callbackSuccess(callback, response, result);
                    } else if (json.getInteger("code") == 205) {
                        callbackError(callback, response, null);
                    }
                }
            }
        });
    }

    private void callbackSuccess(final BaseCallback baseCallback, final Response response, final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    baseCallback.callBackSuccess(response, object);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void callbackError(final BaseCallback baseCallback, final Response response, final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    baseCallback.onError(response, new GET_RESPONSE_MESSAGE_FAILURE("获取服务器信息失败"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
