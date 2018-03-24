package com.manshop.android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lin on 2018/2/26.
 */

public class StringUtil {
    public static StringUtil stringUtil;

    public StringUtil() {
    }

    ;

    public static StringUtil getInstance() {
        return stringUtil;
    }

    //    字符串装换成Bitmap
    public static List<Bitmap> spiltPic(String txt) {
        List<Bitmap> mString = new ArrayList<>();
        String[] txtpicture = txt.split(";");
        Bitmap bitmap = null;
        for (int i = 0; i < txtpicture.length; i++) {
            byte[] bitmapArray = Base64.decode(txtpicture[i], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            mString.add(bitmap);
        }
        return mString;
    }

}
