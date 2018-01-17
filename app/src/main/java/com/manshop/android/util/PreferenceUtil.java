package com.manshop.android.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lin on 2018/1/17.
 */

public class PreferenceUtil {
    private static SharedPreferences sharedPreferences = null ;
    private static SharedPreferences.Editor editor = null ;
    private static Context mcontext = null ;
    public static String CAINIAO_SHOPPING = "cainiao_shopping" ;

    private static void GetPreferenceUtil(Context context) {
        mcontext = context ;
        sharedPreferences = context.getSharedPreferences( "Cainiao" , Context.MODE_PRIVATE) ;
        editor = sharedPreferences.edit() ;
    }

    public static void putString(Context context , String key , String value){

        if (mcontext == null || sharedPreferences == null ){
            GetPreferenceUtil(context) ;
        }

        editor.putString(key , value) ;
        editor.commit() ;

    }


    public static String getString(Context context , String key , String defautString){

        if (mcontext == null || sharedPreferences == null ){
            GetPreferenceUtil(context) ;
        }

        return sharedPreferences.getString(key , defautString) ;
    }
}
