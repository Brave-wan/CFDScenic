package com.demo.my.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_BankCard;
import com.demo.my.activity.Activity_MyWalletRecharge;
import com.demo.my.activity.Activity_MyWalletTixian;
import com.demo.my.activity.Activity_PaymentPassword;
import com.demo.adapter.DetailedAdapter;
import com.demo.my.bean.MyBalanceBean;
import com.demo.demo.myapplication.R;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyLinearLayoutItem;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的钱包
 * Created by Administrator on 2016/7/25 0025.
 */
public class MyWelletFragment extends Fragment {

    @Bind(R.id.my_Wallet_Recharge)
    Button myWalletRecharge;
    @Bind(R.id.my_Wallet_tixian)
    Button myWalletTixian;
    @Bind(R.id.view_yinhangka)
    MyLinearLayoutItem viewYinhangka;
    @Bind(R.id.view_zhifumima)
    MyLinearLayoutItem viewZhifumima;
    @Bind(R.id.tv_Detailed)
    TextView tvDetailed;
    @Bind(R.id.im_Detailed)
    ImageView imDetailed;
    @Bind(R.id.ll_Detailed)
    LinearLayout llDetailed;
    @Bind(R.id.lv_my_wellet)
    ListView lvMyWellet;
    @Bind(R.id.tv_balance)
    TextView tvBalance;

    boolean state = true;//交易明细是否展开
    List<MyBalanceBean.DataBean.TradeLogListBean> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_wellet, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        myBalance();
        state = true;
        tvDetailed.setTextColor(Color.parseColor("#333333"));
        imDetailed.setImageResource(R.mipmap.xiangyoujiantou);
        lvMyWellet.setVisibility(View.GONE);
        lvMyWellet.setFocusable(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.my_Wallet_Recharge, R.id.my_Wallet_tixian, R.id.view_yinhangka, R.id.view_zhifumima, R.id.ll_Detailed})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.my_Wallet_Recharge:
                intent.setClass(getContext(), Activity_MyWalletRecharge.class);
                startActivity(intent);
                break;
            case R.id.my_Wallet_tixian:
                intent.setClass(getContext(), Activity_MyWalletTixian.class);
                startActivity(intent);
                break;
            case R.id.view_yinhangka:
                intent.setClass(getContext(), Activity_BankCard.class);
                startActivity(intent);
                break;
            case R.id.view_zhifumima:
                intent.setClass(getContext(), Activity_PaymentPassword.class);
                startActivity(intent);
                break;
            case R.id.ll_Detailed:
                if (state) {
                    state = false;
                    tvDetailed.setTextColor(Color.parseColor("#FF5400"));
                    imDetailed.setImageResource(R.mipmap.xiangxiajiantou_cheng);
                    lvMyWellet.setVisibility(View.VISIBLE);
                } else {
                    state = true;
                    tvDetailed.setTextColor(Color.parseColor("#333333"));
                    imDetailed.setImageResource(R.mipmap.xiangyoujiantou);
                    lvMyWellet.setVisibility(View.GONE);
                }
                break;
        }
    }


    //余额和交易记录
    private void myBalance() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getContext(), SpName.token, ""));
        params.addQueryStringParameter("type",0+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.myBalance, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(getActivity(),R.style.AlertDialogStyle);
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
                            MyBalanceBean myBalanceBean = new Gson().fromJson(responseInfo.result, MyBalanceBean.class);
                            if (myBalanceBean.getHeader().getStatus() == 0) {
                                tvBalance.setText(myBalanceBean.getData().getBalanceMap().getBalance() + "");
                                SpUtil.putString(getContext(), SpName.balance, myBalanceBean.getData().getBalanceMap().getBalance() + "");
                                data = myBalanceBean.getData().getTradeLogList();
                                if (data.size() > 0) {
                                    lvMyWellet.setAdapter(new DetailedAdapter(getContext(), data));
                                    lvMyWellet.setVisibility(View.GONE);
                                }
                            } else if (myBalanceBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getContext());
                            } else {
                                ToastUtil.show(getContext(), myBalanceBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), "连接网络失败");
                    }
                });
    }

}
