package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.MyPurseBean;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 兑换结果--纪念品
 * Created by Administrator on 2016/8/5 0005.
 */
public class Activity_ExchangeResultesKeepsake extends Activity {


    @Bind(R.id.view_Topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.ll_OrderDetails)
    LinearLayout llOrderDetails;
    @Bind(R.id.tv_shouhuoren)
    TextView tvShouhuoren;
    @Bind(R.id.tv_shoujihao)
    TextView tvShoujihao;
    @Bind(R.id.tv_dizhi)
    TextView tvDizhi;
    @Bind(R.id.tv_bianhao)
    TextView tvBianhao;
    @Bind(R.id.tv_chuangjian)
    TextView tvChuangjian;
    @Bind(R.id.tv_fukuan)
    TextView tvFukuan;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.ll_address)
    LinearLayout llAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_results_keepsake);
        ButterKnife.bind(this);

        viewTopbar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        MyPurse();
    }

    @OnClick(R.id.ll_OrderDetails)
    public void onClick() {
        Intent intent = new Intent(getApplicationContext(), Activity_OrderDetailsKeepsake.class);
        startActivity(intent);
    }

    private void MyPurse() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("id", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.MyPurse, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_ExchangeResultesKeepsake.this, R.style.AlertDialogStyle);

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
                            MyPurseBean myPurseBean = new Gson().fromJson(responseInfo.result, MyPurseBean.class);
                            int i = myPurseBean.getHeader().getStatus();
                            if (i == 0) {
                                tvName.setText(myPurseBean.getData().getName());
                                if (myPurseBean.getData().getUser_name() != null) {
                                    if (!myPurseBean.getData().getUser_name().equals("")) {
                                        tvShouhuoren.setText(myPurseBean.getData().getUser_name() + "");
                                        tvShoujihao.setText(myPurseBean.getData().getTelphone() + "");
                                        tvDizhi.setText(myPurseBean.getData().getPlace_address() + "");
                                    } else {
                                        llAddress.setVisibility(View.GONE);
                                    }
                                } else {
                                    llAddress.setVisibility(View.GONE);
                                }

                                tvBianhao.setText("订单编号：" + myPurseBean.getData().getOrder_code() + "");
                                tvChuangjian.setText("创建时间：" + myPurseBean.getData().getCreate_time() + "");
                                tvFukuan.setText("付款时间：" + myPurseBean.getData().getPay_time());
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ExchangeResultesKeepsake.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), myPurseBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }
}
