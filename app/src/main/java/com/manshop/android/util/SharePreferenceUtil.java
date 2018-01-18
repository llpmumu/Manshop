package com.manshop.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

/**
 * Created by Lin on 2018/1/17.
 */

public class SharePreferenceUtil {
//    private static SharedPreferences sharedPreferences = null ;
//    private static SharedPreferences.Editor editor = null ;
//    private static Context mcontext = null ;
//
//    private static void GetPreferenceUtil(Context context) {
//        mcontext = context ;
//        sharedPreferences = context.getSharedPreferences( "User" , Context.MODE_PRIVATE) ;
//        editor = sharedPreferences.edit() ;
//    }
//
//    public static void putString(Context context , String key , String value){
//
//        if (mcontext == null || sharedPreferences == null ){
//            GetPreferenceUtil(context) ;
//        }
//        editor.putString(key , value) ;
//        editor.commit() ;
//
//    }
//
//
//    public static String getString(Context context , String key , String defautString){
//
//        if (mcontext == null || sharedPreferences == null ){
//            GetPreferenceUtil(context) ;
//        }
//        return sharedPreferences.getString(key , defautString) ;
//    }

    private Context context;

    public SharePreferenceUtil(Context context) {
        this.context = context;
    }

    public boolean saveSharePreference(String filename, Map<String, Object> map) {
        boolean flag = false;
        SharedPreferences preferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object object = entry.getValue();
            if (object instanceof Boolean) {
                boolean b = (boolean) object;
                editor.putBoolean(key, b);
            }
            if (object instanceof String) {
                String s = (String) object;
                editor.putString(key, s);
            }
            if (object instanceof Integer) {
                Integer i = (Integer) object;
                editor.putInt(key, i);
            }
            if (object instanceof Float) {
                Float f = (Float) object;
                editor.putFloat(key, f);
            }
            if (object instanceof Long) {
                Long l = (Long) object;
                editor.putLong(key, l);
            }
        }
        editor.commit();
        return flag;
    }
}
