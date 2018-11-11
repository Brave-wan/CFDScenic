package com.demo.mall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.fragment.SystemBarTintManager;
import com.demo.mall.fragment.HotelFragment;
import com.demo.mall.fragment.RestaurantFragment;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 饭店  酒店首页
 * 作者：sonnerly on 2016/8/6 0006 17:12
 */
public class ChoseDateActivity extends FragmentActivity {
    @Bind(R.id.iv_aty_chosedate)
    ImageView ivAtyChosedate;
    @Bind(R.id.iv_aty_chosedate_jiudian)
    ImageView ivAtyChosedateJiudian;
    @Bind(R.id.tv_aty_chosedate_jiudian)
    TextView tvAtyChosedateJiudian;
    @Bind(R.id.ll_aty_chosedate_jiudian)
    LinearLayout llAtyChosedateJiudian;
    @Bind(R.id.iv_aty_chosedate_fandian)
    ImageView ivAtyChosedateFandian;
    @Bind(R.id.tv_aty_chosedate_fandian)
    TextView tvAtyChosedateFandian;
    @Bind(R.id.ll_aty_chosedate_fandian)
    LinearLayout llAtyChosedateFandian;
    @Bind(R.id.frag_chosedate)
    FrameLayout fragChosedate;
    Intent intent = new Intent();
    private Fragment currentFragment, hotelfragment, restaurantfragment;
    int type;
    int types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_chosedate);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 1);
        if (type == 1) {
            initTab();
            types = 3;
        } else {
            init();
            types = 4;
        }

        getPhotoOfHomePage();
    }


    @OnClick({R.id.ll_aty_chosedate_jiudian, R.id.ll_aty_chosedate_fandian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_aty_chosedate_jiudian://酒店
                ivAtyChosedateJiudian.setImageResource(R.mipmap.jiudian);
                tvAtyChosedateJiudian.setTextColor(Color.parseColor("#ff5203"));
                ivAtyChosedateFandian.setImageResource(R.mipmap.fandianhui);
                tvAtyChosedateFandian.setTextColor(Color.parseColor("#333333"));
                if (hotelfragment == null) {
                    hotelfragment = new HotelFragment();
                }
                types = 3;
                getPhotoOfHomePage();
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), hotelfragment);
                break;
            case R.id.ll_aty_chosedate_fandian://饭店
                ivAtyChosedateJiudian.setImageResource(R.mipmap.jiudianhui);
                tvAtyChosedateJiudian.setTextColor(Color.parseColor("#333333"));
                ivAtyChosedateFandian.setImageResource(R.mipmap.fandian);
                tvAtyChosedateFandian.setTextColor(Color.parseColor("#ff5203"));
                if (restaurantfragment == null) {
                    restaurantfragment = new RestaurantFragment();
                }
                types = 4;
                getPhotoOfHomePage();
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), restaurantfragment);
                break;
        }
    }

    private void init() {
        ivAtyChosedateJiudian.setImageResource(R.mipmap.jiudianhui);
        tvAtyChosedateJiudian.setTextColor(Color.parseColor("#333333"));
        ivAtyChosedateFandian.setImageResource(R.mipmap.fandian);
        tvAtyChosedateFandian.setTextColor(Color.parseColor("#ff5203"));
        if (restaurantfragment == null) {
            restaurantfragment = new RestaurantFragment();
        }

        if (!restaurantfragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frag_chosedate, restaurantfragment).commit();
            // 记录当前Fragment
            currentFragment = restaurantfragment;

        }

    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (hotelfragment == null) {
            hotelfragment = new HotelFragment();
        }

        if (!hotelfragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frag_chosedate, hotelfragment).commit();

            // 记录当前Fragment
            currentFragment = hotelfragment;

        }

    }

    /**
     * 添加或�?显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.frag_chosedate, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }


    private void getPhotoOfHomePage() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("type", types + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getPhotoOfHomePage, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                if (jsonObject.getString("data") != null) {
                                    ImageLoader.getInstance().displayImage(jsonObject.getString("data"), ivAtyChosedate);
                                }
                            } else {
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplication(), "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }
}
