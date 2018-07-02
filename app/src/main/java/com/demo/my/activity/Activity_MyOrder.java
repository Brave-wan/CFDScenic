package com.demo.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.my.fragment.MyOrderFdFragment;
import com.demo.my.fragment.MyOrderFdFragment2;
import com.demo.my.fragment.MyOrderJdFragment;
import com.demo.my.fragment.MyOrderSpFragment;
import com.demo.my.fragment.MyOrderSpFragment2;
import com.demo.view.MyTopBar;
import com.jauker.widget.BadgeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 * Created by Administrator on 2016/7/23 0023.
 */
public class Activity_MyOrder extends FragmentActivity {
    @Bind(R.id.tv_Order_jiudian)
    TextView tvOrderJiudian;
    @Bind(R.id.tv_Order_jiudianxian)
    TextView tvOrderJiudianxian;
    @Bind(R.id.tv_Order_fandian)
    TextView tvOrderFandian;
    @Bind(R.id.tv_Order_fandianxian)
    TextView tvOrderFandianxian;
    @Bind(R.id.tv_Order_shangpin)
    TextView tvOrderShangpin;
    @Bind(R.id.tv_Order_shangpinxian)
    TextView tvOrderShangpinxian;
    @Bind(R.id.fl_weishiyong)
    FrameLayout flWeishiyong;
    @Bind(R.id.fl_yishiyong)
    FrameLayout flYishiyong;
    @Bind(R.id.fl_yiguoqi)
    FrameLayout flYiguoqi;
    @Bind(R.id.tv_weishiyong)
    TextView tvWeishiyong;
    @Bind(R.id.tv_yishiyong)
    TextView tvYishiyong;
    @Bind(R.id.tv_yiguoqi)
    TextView tvYiguoqi;
    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.fl_fragment)
    FrameLayout flFragment;
    @Bind(R.id.tv_yiwancheng)
    TextView tvYiwancheng;
    @Bind(R.id.fl_yiwancheng)
    FrameLayout flYiwancheng;

    public static Fragment fd_1, fd_2, fd_3,
            jd_1, jd_2, jd_3,
            sp_1, sp_2, sp_3,sp_4,
            currentFragment;

    private  BadgeView badgeView;
    int state_shang = 1, state_xia = 1;//状态值，state_shang用来描述上边选中的位置，state_xia下边选中的位置
    boolean state = false;//是否处于编辑状态，true在编辑，false没有在编辑

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);

        viewTopbar.setLeftImageOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        badgeView = new com.jauker.widget.BadgeView(this);
        initFragment();
    }

    @OnClick({R.id.tv_Order_jiudian, R.id.tv_Order_fandian, R.id.tv_Order_shangpin, R.id.fl_weishiyong, R.id.fl_yishiyong, R.id.fl_yiguoqi,R.id.fl_yiwancheng})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_Order_jiudian:
                tvWeishiyong.setText("未使用");
                tvYishiyong.setText("已使用");
                tvYiguoqi.setText("已过期");
                flYiwancheng.setVisibility(View.GONE);
                changeState(tvWeishiyong, tvYishiyong, tvYiguoqi, tvYiwancheng);
                change(tvOrderJiudian, tvOrderFandian, tvOrderShangpin, tvOrderJiudianxian, tvOrderFandianxian, tvOrderShangpinxian);
                state_shang = 1;
                state_xia = 1;
                if (jd_1!=null){
                    jd_1=null;
                }
                if (jd_1 == null) {
                    jd_1 = new MyOrderJdFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    jd_1.setArguments(bundle);
                }
                /*if (myOrderJdFragment==null){
                    myOrderJdFragment= (MyOrderJdFragment) jd_1;
                }
                myOrderJdFragment.visibilityCorner();*/
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), jd_1);
                break;
            case R.id.tv_Order_fandian:
                tvWeishiyong.setText("未使用");
                tvYishiyong.setText("已使用");
                tvYiguoqi.setText("已过期");
                flYiwancheng.setVisibility(View.GONE);
                changeState(tvWeishiyong, tvYishiyong, tvYiguoqi,tvYiwancheng);
                change(tvOrderFandian, tvOrderJiudian, tvOrderShangpin, tvOrderFandianxian, tvOrderJiudianxian, tvOrderShangpinxian);
                state_shang = 2;
                state_xia = 1;
                if (fd_1!=null){
                    fd_1=null;
                }
                if (fd_1 == null) {
                    fd_1 = new MyOrderFdFragment2();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    fd_1.setArguments(bundle);
                }
                /*if (myOrderFdFragment==null){
                    myOrderFdFragment= (MyOrderFdFragment) fd_1;
                }
                myOrderFdFragment.visibilityCorner();*/
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), fd_1);
                break;
            case R.id.tv_Order_shangpin:
                tvWeishiyong.setText("待付款");
                tvYishiyong.setText("已付款");
                tvYiguoqi.setText("待收货");
                tvYiwancheng.setText("已完成");
                flYiwancheng.setVisibility(View.VISIBLE);
                changeState(tvWeishiyong, tvYishiyong, tvYiguoqi,tvYiwancheng);
                change(tvOrderShangpin, tvOrderJiudian, tvOrderFandian, tvOrderShangpinxian, tvOrderJiudianxian, tvOrderFandianxian);
                state_shang = 3;
                state_xia = 1;
                if (sp_1!=null){
                    sp_1=null;
                }
                if (sp_1 == null) {
                    sp_1 = new MyOrderSpFragment2();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    sp_1.setArguments(bundle);
                }
                /*if (myOrderSpFragment==null){
                    myOrderSpFragment= (MyOrderSpFragment) sp_1;
                }
                myOrderSpFragment.visibilityCorner();*/
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), sp_1);
                break;
            case R.id.fl_weishiyong:
                changeState(tvWeishiyong, tvYishiyong, tvYiguoqi,tvYiwancheng);
                state_xia = 1;
                switch (state_shang) {
                    case 1:
                        if (jd_1 == null) {
                            jd_1 = new MyOrderJdFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            jd_1.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), jd_1);
                        break;
                    case 2:
                        if (fd_1 == null) {
                            fd_1 = new MyOrderFdFragment2();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            fd_1.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), fd_1);
                        break;
                    case 3:
                        if (sp_1 == null) {
                            sp_1 = new MyOrderSpFragment2();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            sp_1.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), sp_1);
                        break;
                }
                break;
            case R.id.fl_yishiyong:
                changeState(tvYishiyong, tvWeishiyong, tvYiguoqi,tvYiwancheng);
                state_xia = 2;
                switch (state_shang) {
                    case 1:
                        if (jd_2 == null) {
                            jd_2 = new MyOrderJdFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            jd_2.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), jd_2);
                        break;
                    case 2:
                        if (fd_2 == null) {
                            fd_2 = new MyOrderFdFragment2();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            fd_2.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), fd_2);
                        break;
                    case 3:
                        if (sp_2 == null) {
                            sp_2 = new MyOrderSpFragment2();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            sp_2.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), sp_2);
                        break;
                }
                break;
            case R.id.fl_yiguoqi:
                changeState(tvYiguoqi, tvWeishiyong, tvYishiyong,tvYiwancheng);
                state_xia = 3;
                switch (state_shang) {
                    case 1:
                        if (jd_3 == null) {
                            jd_3 = new MyOrderJdFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            jd_3.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), jd_3);
                        break;
                    case 2:
                        if (fd_3 == null) {
                            fd_3 = new MyOrderFdFragment2();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            fd_3.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), fd_3);
                        break;
                    case 3:
                        if (sp_3 == null) {
                            sp_3 = new MyOrderSpFragment2();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            sp_3.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), sp_3);
                        break;
                }
                break;
            case R.id.fl_yiwancheng:
                changeState(tvYiwancheng,tvYiguoqi, tvWeishiyong, tvYishiyong);
                state_xia = 4;
                switch (state_shang) {
                    case 1:
                        if (jd_3 == null) {
                            jd_3 = new MyOrderJdFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            jd_3.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), jd_3);
                        break;
                    case 2:
                        if (fd_3 == null) {
                            fd_3 = new MyOrderFdFragment2();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            fd_3.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), fd_3);
                        break;
                    case 3:
                        if (sp_4 == null) {
                            sp_4 = new MyOrderSpFragment2();
                            Bundle bundle = new Bundle();
                            bundle.putInt("state", state_xia);
                            sp_4.setArguments(bundle);
                        }
                        addOrShowFragment(getSupportFragmentManager().beginTransaction(), sp_4);
                        break;
                }
                break;
        }
    }

    //单击预约背景
    public void change(TextView tv_1, TextView tv_2, TextView tv_3, TextView tvd_1, TextView tvd_2, TextView tvd_3) {
        tv_1.setTextColor(Color.parseColor("#4bc4fb"));
        tv_2.setTextColor(Color.parseColor("#333333"));
        tv_3.setTextColor(Color.parseColor("#333333"));

        tvd_1.setVisibility(View.VISIBLE);
        tvd_2.setVisibility(View.INVISIBLE);
        tvd_3.setVisibility(View.INVISIBLE);
    }

    //单击改变使用状态背景
    private void changeState(TextView tv_1, TextView tv_2, TextView tv_3,TextView tv_4) {
        tv_1.setBackgroundResource(R.mipmap.wd_dingdan_landi);
        tv_1.setTextColor(Color.parseColor("#ffffff"));
        tv_2.setBackgroundResource(R.mipmap.bt_touming);
        tv_2.setTextColor(Color.parseColor("#333333"));
        tv_3.setBackgroundResource(R.mipmap.bt_touming);
        tv_3.setTextColor(Color.parseColor("#333333"));
        tv_4.setBackgroundResource(R.mipmap.bt_touming);
        tv_4.setTextColor(Color.parseColor("#333333"));
    }

    public  void setCorner(int i){
        badgeView.setTargetView(tvWeishiyong);
        badgeView.setBadgeCount(i);
        if (state_shang==3){
            badgeView.setBadgeMargin(0,0,0,0);
        }else {
            badgeView.setBadgeMargin(0,0,15,0);
        }

        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
    }

    /**
     * 初始化显示的第一个fragment
     */
    private void initFragment() {
        if (jd_1 == null) {
            jd_1 = new MyOrderJdFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("state", state_xia);
            jd_1.setArguments(bundle);
        }

        if (!jd_1.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, jd_1).commit();
            // 记录当前Fragment
            currentFragment = jd_1;
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
