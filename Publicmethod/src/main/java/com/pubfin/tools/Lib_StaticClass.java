package com.pubfin.tools;

import android.app.Activity;
import android.content.SharedPreferences;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
public class Lib_StaticClass {

    public static SharedPreferences preferences;
    public static List<Activity> lib_list_activity = new ArrayList<Activity>();
    public static int screenWidth, screenHeight;//屏幕宽，屏幕高
    public static int n_id = 2;

}
