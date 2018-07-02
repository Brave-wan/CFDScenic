package com.demo.scenicspot.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.fragment.SystemBarTintManager;
import com.demo.scenicspot.bean.PayOrderFinshBean;
import com.demo.scenicspot.fragment.ZxingFragment;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyTopBar;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class PayResultActivity extends FragmentActivity {


    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.tv_payresult_connent)
    TextView tvPayresultConnent;
    @Bind(R.id.tv_youxiaoqi)
    TextView tvYouxiaoqi;
    @Bind(R.id.vp_image)
    ViewPager vpImage;

    PayOrderFinshBean payOrderFinshBean;
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    @Bind(R.id.tv_num)
    TextView tvNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.science_activity_payresult);
        ButterKnife.bind(this);
        viewTopbar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvNum.setText((position+1)+"/"+fragmentList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        payOrderFinsh();
    }

    private void payOrderFinsh() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("orderCode", getIntent().getLongExtra("data", 0) + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.payOrderFinsh, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(PayResultActivity.this,R.style.AlertDialogStyle);
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        try {
                            payOrderFinshBean = new Gson().fromJson(responseInfo.result, PayOrderFinshBean.class);
                            int i = payOrderFinshBean.getHeader().getStatus();
                            if (i == 0) {
                                tvPayresultConnent.setText(payOrderFinshBean.getData().get(0).getName());
                                tvYouxiaoqi.setText("有效期至：" + payOrderFinshBean.getData().get(0).getEnd_valid());
                                initFragment();
                                tvNum.setText(1 + "/" + fragmentList.size());
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(PayResultActivity.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), payOrderFinshBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }


    private void initFragment() {
        for (int i = 0; i < payOrderFinshBean.getData().size(); i++) {
            ZxingFragment viewFragment = new ZxingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Id", payOrderFinshBean.getData().get(i).getVisitorsOrderId() + "");
            viewFragment.setArguments(bundle);
            fragmentList.add(viewFragment);
        }
        vpImage.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
