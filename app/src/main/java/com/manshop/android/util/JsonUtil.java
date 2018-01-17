package com.manshop.android.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Lin on 2018/1/17.
 */

public class JsonUtil {
    static Gson gson = new Gson() ;

    public static  <T> T fromJson(String json , Type type){
        return gson.fromJson(json , type) ;
    }

    public static String toJSON( Object carts ){
        return gson.toJson(carts) ;
    }

}
