package com.demo.my.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.adapter.RefundOrderAdapter;
import com.demo.demo.myapplication.R;
import com.demo.my.fragment.MyOrderFdFragment;
import com.demo.my.fragment.MyOrderJdFragment;
import com.demo.my.fragment.MyOrderSpFragment;
import com.demo.my.fragment.MyRefundFdFragment;
import com.demo.my.fragment.MyRefundJdFragment;
import com.demo.my.fragment.MyRefundSpFragment;
import com.demo.view.MyTopBar;
import com.jauker.widget.BadgeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退款订单
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_RefundOrder extends FragmentActivity {


    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
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
    @Bind(R.id.tv_weishiyong)
    TextView tvWeishiyong;
    @Bind(R.id.fl_weishiyong)
    FrameLayout flWeishiyong;
    @Bind(R.id.tv_yishiyong)
    TextView tvYishiyong;
    @Bind(R.id.fl_yishiyong)
    FrameLayout flYishiyong;
    @Bind(R.id.tv_yiguoqi)
    TextView tvYiguoqi;
    @Bind(R.id.fl_yiguoqi)
    FrameLayout flYiguoqi;
    @Bind(R.id.tv_yiwancheng)
    TextView tvYiwancheng;
    @Bind(R.id.fl_yiwancheng)
    FrameLayout flYiwancheng;
    @Bind(R.id.ll_state)
    LinearLayout llState;
    @Bind(R.id.fl_fragment)
    FrameLayout flFragment;


    public static Fragment jd_1,
            fd_1,
            sp_1, sp_2, sp_3, sp_4,
            currentFragment;

    private static BadgeView badgeView;//角标
    int state_shang = 1, state_xia = 1;//状态值，state_shang用来描述上边选中的位置，state_xia下边选中的位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_order);
        ButterKnife.bind(this);
        viewTopbar.setLeftImageOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        badgeView = new BadgeView(this);
        initFragment();
    }

    @OnClick({R.id.tv_Order_jiudian, R.id.tv_Order_fandian, R.id.tv_Order_shangpin, R.id.fl_weishiyong, R.id.fl_yishiyong, R.id.fl_yiguoqi,R.id.fl_yiwancheng})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_Order_jiudian:
                flYiwancheng.setVisibility(View.GONE);
                llState.setVisibility(View.GONE);
                changeState(tvWeishiyong, tvYishiyong, tvYiguoqi, tvYiwancheng);
                change(tvOrderJiudian, tvOrderFandian, tvOrderShangpin, tvOrderJiudianxian, tvOrderFandianxian, tvOrderShangpinxian);
                state_shang = 1;
                state_xia = 1;
                if (jd_1 == null) {
                    jd_1 = new MyRefundJdFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    jd_1.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), jd_1);
                break;
            case R.id.tv_Order_fandian:
                flYiwancheng.setVisibility(View.GONE);
                llState.setVisibility(View.GONE);
                changeState(tvWeishiyong, tvYishiyong, tvYiguoqi,tvYiwancheng);
                change(tvOrderFandian, tvOrderJiudian, tvOrderShangpin, tvOrderFandianxian, tvOrderJiudianxian, tvOrderShangpinxian);
                state_shang = 2;
                state_xia = 1;
                if (fd_1 == null) {
                    fd_1 = new MyRefundFdFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    fd_1.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), fd_1);
                break;
            case R.id.tv_Order_shangpin:
                flYiwancheng.setVisibility(View.VISIBLE);
                llState.setVisibility(View.VISIBLE);
                changeState(tvWeishiyong, tvYishiyong, tvYiguoqi,tvYiwancheng);
                change(tvOrderShangpin, tvOrderJiudian, tvOrderFandian, tvOrderShangpinxian, tvOrderJiudianxian, tvOrderFandianxian);
                state_shang = 3;
                state_xia = 1;
                if (sp_1!=null){
                    sp_1=null;
                }
                if (sp_1 == null) {
                    sp_1 = new MyRefundSpFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    sp_1.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), sp_1);
                break;
            case R.id.fl_weishiyong:
                changeState(tvWeishiyong, tvYishiyong, tvYiguoqi,tvYiwancheng);
                state_xia = 1;
                if (sp_1 == null) {
                    sp_1 = new MyRefundSpFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    sp_1.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), sp_1);
                break;
            case R.id.fl_yishiyong:
                changeState(tvYishiyong, tvWeishiyong, tvYiguoqi,tvYiwancheng);
                state_xia = 2;
                if (sp_2 == null) {
                    sp_2 = new MyRefundSpFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    sp_2.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), sp_2);
                break;
            case R.id.fl_yiguoqi:
                changeState(tvYiguoqi, tvWeishiyong, tvYishiyong,tvYiwancheng);
                state_xia = 3;
                if (sp_3 == null) {
                    sp_3 = new MyRefundSpFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    sp_3.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), sp_3);
                break;
            case R.id.fl_yiwancheng:
                changeState(tvYiwancheng,tvYiguoqi, tvWeishiyong, tvYishiyong);
                state_xia = 4;
                if (sp_4 == null) {
                    sp_4 = new MyRefundSpFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", state_xia);
                    sp_4.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), sp_4);
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
    private void changeState(TextView tv_1, TextView tv_2, TextView tv_3, TextView tv_4) {
        tv_1.setBackgroundResource(R.mipmap.wd_dingdan_landi);
        tv_1.setTextColor(Color.parseColor("#ffffff"));
        tv_2.setBackgroundResource(R.mipmap.bt_touming);
        tv_2.setTextColor(Color.parseColor("#333333"));
        tv_3.setBackgroundResource(R.mipmap.bt_touming);
        tv_3.setTextColor(Color.parseColor("#333333"));
        tv_4.setBackgroundResource(R.mipmap.bt_touming);
        tv_4.setTextColor(Color.parseColor("#333333"));
    }


    /**
     * 初始化显示的第一个fragment
     */
    private void initFragment() {
        if (jd_1 == null) {
            jd_1 = new MyRefundJdFragment();
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


    public  void setCorner(int i){
        badgeView.setTargetView(tvWeishiyong);
        badgeView.setBadgeCount(i);
    }
}
