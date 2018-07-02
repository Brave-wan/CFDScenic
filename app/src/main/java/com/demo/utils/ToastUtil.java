package com.demo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author 宋玉磊 on 2016/1/11 17:23.
 */
public class ToastUtil {
    public static void show(Context context,String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context,String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
