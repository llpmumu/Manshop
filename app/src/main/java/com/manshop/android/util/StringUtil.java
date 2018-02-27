package com.manshop.android.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lin on 2018/2/26.
 */

public class StringUtil {
    public static StringUtil stringUtil ;

    public StringUtil () {};

    public static StringUtil getInstance(){
        return stringUtil ;
    }
    //    分割图片字符
    public static List spiltPic(String txt) {
//        List<String> mString = new ArrayList<>();
        String[] txtpicture = txt.split(";");
        return Arrays.asList(txtpicture);
    }

}
