package com.demo.my.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.amusement.activity.Activity_WriteBlogs;
import com.demo.demo.myapplication.R;
import com.demo.my.fragment.MyRecreationJcFragment;
import com.demo.my.fragment.MyRecreationJqFragment;
import com.demo.my.fragment.MyRecreationSpFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的--游乐圈
 * Created by Administrator on 2016/7/25 0025.
 */
public class  Activity_Recreation extends FragmentActivity {

    @Bind(R.id.im_left_topbar)
    ImageView imLeftTopbar;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.my_recreation_youji)
    TextView myRecreationYouji;
    @Bind(R.id.my_recreation_youjixian)
    TextView myRecreationYoujixian;
    @Bind(R.id.my_recreation_gonglue)
    TextView myRecreationGonglue;
    @Bind(R.id.my_recreation_gongluexian)
    TextView myRecreationGongluexian;
    @Bind(R.id.my_recreation_pingjia)
    TextView myRecreationPingjia;
    @Bind(R.id.my_recreation_pingjiaxian)
    TextView myRecreationPingjiaxian;
    @Bind(R.id.fl_fragment)
    FrameLayout flFragment;

    Fragment currentFragment, myRecreationJc,myRecreationSp,myRecreationJq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recreation);
        ButterKnife.bind(this);

        initFragment();
    }

    @OnClick({R.id.im_left_topbar, R.id.tv_right, R.id.my_recreation_youji, R.id.my_recreation_gonglue, R.id.my_recreation_pingjia})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left_topbar:
                finish();
                break;
            case R.id.tv_right:
                Intent intent=new Intent(getApplicationContext(),Activity_WriteBlogs.class);
                startActivity(intent);
                break;
            case R.id.my_recreation_youji:
                changeView(1);
                if (myRecreationJc == null) {
                    myRecreationJc = new MyRecreationJcFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), myRecreationJc);
                break;
            case R.id.my_recreation_gonglue:
                changeView(2);
                if (myRecreationSp == null) {
                    myRecreationSp = new MyRecreationSpFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), myRecreationSp);
                break;
            case R.id.my_recreation_pingjia:
                changeView(3);
                if (myRecreationJq == null) {
                    myRecreationJq = new MyRecreationJqFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), myRecreationJq);
                break;
        }
    }

    //单击改变界面
    private void changeView(int i) {
        if (i == 1) {
            myRecreationYoujixian.setVisibility(View.VISIBLE);
            myRecreationGongluexian.setVisibility(View.INVISIBLE);
            myRecreationPingjiaxian.setVisibility(View.INVISIBLE);
            changeImage(R.mipmap.wd_ylqyouji_true, R.mipmap.wd_ylqgonglue_false, R.mipmap.wd_ylqpingjia_false,
                    myRecreationYouji, myRecreationGonglue, myRecreationPingjia);
        } else if (i == 2) {
            myRecreationYoujixian.setVisibility(View.INVISIBLE);
            myRecreationGongluexian.setVisibility(View.VISIBLE);
            myRecreationPingjiaxian.setVisibility(View.INVISIBLE);
            changeImage(R.mipmap.wd_ylqgonglue_true, R.mipmap.wd_ylqyouji_false, R.mipmap.wd_ylqpingjia_false,
                    myRecreationGonglue, myRecreationYouji, myRecreationPingjia);
        } else if (i == 3) {
            myRecreationYoujixian.setVisibility(View.INVISIBLE);
            myRecreationGongluexian.setVisibility(View.INVISIBLE);
            myRecreationPingjiaxian.setVisibility(View.VISIBLE);
            changeImage(R.mipmap.wd_ylqpingjia_true, R.mipmap.wd_ylqyouji_false, R.mipmap.wd_ylqgonglue_false,
                    myRecreationPingjia, myRecreationYouji, myRecreationGonglue);
        }
    }

    //改变界面
    private void changeImage(int select1, int select2, int select3, TextView view1, TextView view2, TextView view3) {
        Drawable drawable1 = getResources().getDrawable(select1);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        view1.setCompoundDrawables(drawable1, null, null, null);
        view1.setTextColor(Color.parseColor("#f95600"));

        Drawable drawable2 = getResources().getDrawable(select2);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        view2.setCompoundDrawables(drawable2, null, null, null);
        view2.setTextColor(Color.parseColor("#000000"));

        Drawable drawable3 = getResources().getDrawable(select3);
        drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
        view3.setCompoundDrawables(drawable3, null, null, null);
        view3.setTextColor(Color.parseColor("#000000"));
    }


    /**
     * 初始化显示的第一个fragment
     */
    private void initFragment() {
        if (myRecreationJc == null) {
            myRecreationJc = new MyRecreationJcFragment();
        }

        if (!myRecreationJc.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, myRecreationJc).commit();
            // 记录当前Fragment
            currentFragment = myRecreationJc;
        }
    }

    //H服务fragment优化
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.fl_fragment, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

}
