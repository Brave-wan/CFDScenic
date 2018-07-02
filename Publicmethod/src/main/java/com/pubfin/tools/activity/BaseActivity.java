package com.pubfin.tools.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pubfin.tools.Lib_StaticClass;
import com.pubfin.R;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
public abstract class BaseActivity extends FragmentActivity {

    private ImageButton ib_back;//返回按钮
    private TextView tv_title;//标题
    private LinearLayout ly;
    private TextView tv_register, tv_line;
    private FrameLayout frameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_base);

        ib_back = (ImageButton) findViewById(R.id.lib_base_ib_back);
        tv_title = (TextView) findViewById(R.id.lib_base_tv_title);
        ly = (LinearLayout) findViewById(R.id.lib_base_ly);
        tv_register = (TextView) findViewById(R.id.lib_base_register);
        tv_line = (TextView) findViewById(R.id.lib_base_tv_line);
        frameLayout = (FrameLayout) findViewById(R.id.lib_base_fl);

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (showBack())
            ib_back.setVisibility(View.VISIBLE);
        else
            ib_back.setVisibility(View.GONE);

        ly.addView(LayoutInflater.from(this).inflate(setMyContentView(), null));
        tv_title.setText(setTitile());

    }

    /**
     * 设置是否显示返回按钮 true 显示 false：不显示
     * @return
     */
    public abstract boolean showBack();

    /**设置需要显示layout布局
     * @return
     */
    public abstract int setMyContentView();

    /**
     * 设置标题
     * @return
     */
    public abstract String setTitile();

    /**
     *
     * 取消显示标题
     */
    public void disTitle()
    {
        frameLayout.setVisibility(View.GONE);
        tv_line.setVisibility(View.GONE);
    }

    public void UpdataTitle(String title)
    {
        tv_title.setText(title);
    }

    /**
     * 设置显示注册按钮
     */
    public void showRegister(){
        tv_register.setVisibility(View.VISIBLE);
    }

    /**
     * TOAST
     */
    public void showToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * ACTIVITY 跳转 不结束当前ACTIVTTY
     * @param cls
     */
    public void goToActivity(Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * ACTIVITY 跳转 结束当前ACTIVITY
     * @param cls
     */
    public void goToActivityFinish(Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    /**
     * ACTIVITY 跳转 不结束当前ACTIVITY
     * @param cls
     * @param code 请求码
     */
    public void goToActivityForResult(Class<?> cls, int code)
    {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, code);
    }

    /**
     * 结束所有记录的ACTIVITY
     */
    public void finishAllActivity()
    {
        for (int i = 0; i < Lib_StaticClass.lib_list_activity.size(); i++)
            Lib_StaticClass.lib_list_activity.get(i).finish();
        Lib_StaticClass.lib_list_activity.clear();
    }

    /**
     * 添加当前ACTIVITY到结束集合
     */
    public void addActivityList()
    {
        Lib_StaticClass.lib_list_activity.add(this);
    }

}
