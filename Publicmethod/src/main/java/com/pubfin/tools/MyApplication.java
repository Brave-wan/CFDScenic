package com.pubfin.tools;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Lib_StaticClass.preferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
        WindowManager manager = (WindowManager)getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        Lib_StaticClass.screenWidth = outMetrics.widthPixels;
        Lib_StaticClass.screenHeight = outMetrics.heightPixels;

    }
}
