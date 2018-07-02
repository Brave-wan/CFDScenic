package com.demo.scenicspot.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Gallery;

import com.demo.demo.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：宋玉磊 on 2016/8/5 0005 13:56
 */
public class LookBigPictureActivity extends Activity {
    //屏幕的宽度高度
    public static int screenWidth;
    public static int screenHeight;
    @Bind(R.id.mygallery)
    Gallery mygallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_picture_big);
        ButterKnife.bind(this);
//获取屏幕的宽高对象
        DisplayMetrics metrics = new DisplayMetrics();
        //设置当前窗口为metrics
        getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

}
