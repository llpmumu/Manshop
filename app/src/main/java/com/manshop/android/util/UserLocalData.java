package com.manshop.android.util;

import android.content.Context;
import android.text.TextUtils;

import com.manshop.android.model.User;

/**
 * Created by Lin on 2018/1/17.
 */

public class UserLocalData {
    public static void putUser(Context context, User user) {

        String user_json = JsonUtil.toJSON(user);
        PreferenceUtil.putString(context, "user_json", user_json);
    }

    public static void putToken(Context context, String token) {

        PreferenceUtil.putString(context, "token", token);
    }

    public static User getUser(Context context) {
        String user_json = PreferenceUtil.getString(context, "user_json", null);
        if (!TextUtils.isEmpty(user_json)) {

            return JsonUtil.fromJson(user_json, User.class);
        }
        return null;
    }

    public static String getToken(Context context) {
        String token = PreferenceUtil.getString(context, "token", null);
        if (!TextUtils.isEmpty(token)) {

            return JsonUtil.fromJson(token, String.class);
        }
        return null;
    }

    public static void clearUser(Context context) {
        PreferenceUtil.putString(context, "user_json", "");
    }

    public static void clearToken(Context context) {
        PreferenceUtil.putString(context, "user_json", "");
    }
}
