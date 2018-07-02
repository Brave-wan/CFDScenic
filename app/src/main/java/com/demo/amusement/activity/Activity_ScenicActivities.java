package com.demo.amusement.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.amusement.fragment.GoFragment;
import com.demo.amusement.fragment.RecommendFragment;
import com.demo.fragment.MainActivity;
import com.demo.fragment.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 景区活动
 * Created by Administrator on 2016/7/26 0026.
 */
public class Activity_ScenicActivities extends FragmentActivity {

    @Bind(R.id.tv_activity_tuijian)
    TextView tvActivityTuijian;
    @Bind(R.id.tv_activity_tuijianxian)
    TextView tvActivityTuijianxian;
    @Bind(R.id.tv_activity_jieban)
    TextView tvActivityJieban;
    @Bind(R.id.tv_activity_jiebanxian)
    TextView tvActivityJiebanxian;

    Fragment currentFragment,recommendFragment,goFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_activities);
        ButterKnife.bind(this);

        initFragment();
    }

    @OnClick({R.id.tv_activity_tuijian, R.id.tv_activity_jieban})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_tuijian:
                tvActivityTuijian.setTextColor(Color.parseColor("#f95600"));
                tvActivityJieban.setTextColor(Color.parseColor("#000000"));
                tvActivityTuijianxian.setVisibility(View.VISIBLE);
                tvActivityJiebanxian.setVisibility(View.INVISIBLE);
                if (recommendFragment == null) {
                    recommendFragment = new RecommendFragment();//活动推荐
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(),recommendFragment);
                break;
            case R.id.tv_activity_jieban:
                tvActivityTuijian.setTextColor(Color.parseColor("#000000"));
                tvActivityJieban.setTextColor(Color.parseColor("#f95600"));
                tvActivityTuijianxian.setVisibility(View.INVISIBLE);
                tvActivityJiebanxian.setVisibility(View.VISIBLE);
                if (goFragment==null){
                    goFragment=new GoFragment();//结伴游
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(),goFragment);
                break;
        }
    }

    /**
     * 初始化显示的第一个fragment
     */
    private void initFragment() {
        if (recommendFragment == null) {
            recommendFragment = new RecommendFragment();
        }

        if (!recommendFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, recommendFragment).commit();
            // 记录当前Fragment
            currentFragment = recommendFragment;
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
