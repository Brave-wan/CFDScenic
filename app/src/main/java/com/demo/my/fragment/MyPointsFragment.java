package com.demo.my.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.adapter.ReplaceRecordAdapter;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_Exchange;
import com.demo.my.activity.Activity_ExchangeRecord;
import com.demo.my.activity.Activity_OrderDetailsKeepsake;
import com.demo.my.bean.MyIntegralBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
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
 * 我的钱包-我的积分
 * Created by Administrator on 2016/7/25 0025.
 */
public class  MyPointsFragment extends Fragment {

    @Bind(R.id.my_Wallet_quduihuan)
    Button myWalletQuduihuan;
    @Bind(R.id.my_Wallet_duihuanjilu)
    Button myWalletDuihuanjilu;
    @Bind(R.id.lv_MyPoints)
    ListView lvMyPoints;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.tv_Record)
    TextView tvRecord;
    @Bind(R.id.iv_Record)
    ImageView ivRecord;
    @Bind(R.id.ll_Record)
    LinearLayout llRecord;
    @Bind(R.id.sv_points)
    ScrollView svPoints;

    List<MyIntegralBean.DataBean.TradeLogListBean> list;
    DialogProgressbar dialogProgressbar;
    boolean state = true;//交易明细是否展开

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_points, null);
        ButterKnife.bind(this, view);

       /* lvMyPoints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Activity_OrderDetailsKeepsake.class);
                intent.putExtra("id", list.get(position).getId());
                startActivity(intent);
            }
        });*/

        dialogProgressbar =new DialogProgressbar(getActivity(),R.style.AlertDialogStyle);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myIntegral();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.my_Wallet_quduihuan, R.id.my_Wallet_duihuanjilu, R.id.ll_Record})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.my_Wallet_quduihuan:
                intent.setClass(getContext(), Activity_Exchange.class);
                startActivity(intent);
                break;
            case R.id.my_Wallet_duihuanjilu:
                intent.setClass(getContext(), Activity_ExchangeRecord.class);
                startActivity(intent);
                break;
            case R.id.ll_Record:
                if (state) {
                    state = false;
                    tvRecord.setTextColor(Color.parseColor("#FF5400"));
                    ivRecord.setImageResource(R.mipmap.xiangxiajiantou_cheng);
                    lvMyPoints.setVisibility(View.VISIBLE);
                } else {
                    state = true;
                    tvRecord.setTextColor(Color.parseColor("#333333"));
                    ivRecord.setImageResource(R.mipmap.xiangyoujiantou);
                    lvMyPoints.setVisibility(View.GONE);
                }
                break;
        }
    }


    //兑换交易记录
    private void myIntegral() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getContext(), SpName.token, ""));
        params.addQueryStringParameter("type",1+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.myIntegral, params,
                new RequestCallBack<String>() {

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
                            MyIntegralBean myIntegralBean = new Gson().fromJson(responseInfo.result, MyIntegralBean.class);
                            int i = myIntegralBean.getHeader().getStatus();
                            if (i == 0) {
                                tvIntegral.setText(myIntegralBean.getData().getIntegralMap().getIntegration()+"");
                                if (myIntegralBean.getData().getTradeLogList()!=null){
                                    list = myIntegralBean.getData().getTradeLogList();
                                    lvMyPoints.setAdapter(new ReplaceRecordAdapter(getContext(), list));
                                }


                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getContext());
                            } else {
                                ToastUtil.show(getContext(), myIntegralBean.getHeader().getMsg());
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
