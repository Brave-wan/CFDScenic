package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.adapter.MyOrderSpDetailsAdapter;
import com.demo.my.bean.SpFindOrderDetailBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.NoScrollViewListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 退款订单--订单详情--商品
 * Created by Administrator on 2016/8/6 0006.
 */
public class Activity_RefundDetails_sp extends Activity {


    MyOrderSpDetailsAdapter myOrderSpDetailsAdapter;
    SpFindOrderDetailBean spFindOrderDetailBean;
    List<SpFindOrderDetailBean.DataBean> list;

    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.iv_ImageSx)
    ImageView ivImageSx;
    @Bind(R.id.tv_shangjiaName)
    TextView tvShangjiaName;
    @Bind(R.id.lvListView)
    NoScrollViewListView lvListView;
    @Bind(R.id.tv_peisong)
    TextView tvPeisong;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_shouhuorenName)
    TextView tvShouhuorenName;
    @Bind(R.id.tv_Telephone)
    TextView tvTelephone;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.ll_ReceiptAddress)
    LinearLayout llReceiptAddress;
    @Bind(R.id.tv_create)
    TextView tvCreate;
    @Bind(R.id.tv_paymentTime)
    TextView tvPaymentTime;
    @Bind(R.id.tv_payment)
    TextView tvPayment;
    @Bind(R.id.tv_peisongfangshi)
    TextView tvPeisongfangshi;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.ll_bohui)
    LinearLayout llBohui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_details_sp);
        ButterKnife.bind(this);

        findOrderDetail();
    }


    public void findOrderDetail() {
        list = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("type", getIntent().getStringExtra("type"));
        params.addQueryStringParameter("orderId", getIntent().getStringExtra("orderId"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.findOrderDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_RefundDetails_sp.this, R.style.AlertDialogStyle);

                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框意外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        try {
                            spFindOrderDetailBean = new Gson().fromJson(responseInfo.result, SpFindOrderDetailBean.class);
                            int i = spFindOrderDetailBean.getHeader().getStatus();
                            if (i == 0) {
                                list = spFindOrderDetailBean.getData();
                                myOrderSpDetailsAdapter = new MyOrderSpDetailsAdapter(getApplicationContext(), list);
                                lvListView.setAdapter(myOrderSpDetailsAdapter);
                                tvOrderNumber.setText(spFindOrderDetailBean.getData().get(0).getOrder_code());//订单号

                                ImageLoader.getInstance().displayImage(spFindOrderDetailBean.getData().get(0).getInformationHeadImg(), ivImageSx);
                                tvShangjiaName.setText(spFindOrderDetailBean.getData().get(0).getInformationName());

                                //tvName.setText(spFindOrderDetailBean.getData().get(0).getUser_name());
                                //tvPhone.setText(spFindOrderDetailBean.getData().get(0).get);
                                //tvAddress.setText(spFindOrderDetailBean.getData().get(0).getPlace_address() + spFindOrderDetailBean.getData().get(0).getDetail_address());
                                tvCreate.setText("创建时间：" + spFindOrderDetailBean.getData().get(0).getCreate_time());
                                if (spFindOrderDetailBean.getData().get(0) == null) {
                                    tvPaymentTime.setVisibility(View.GONE);
                                } else {
                                    tvPaymentTime.setText("付款时间：" + spFindOrderDetailBean.getData().get(0).getPay_time());
                                }

                                //判断支付方式
                                if (spFindOrderDetailBean.getData().get(0).getPay_way() == 1) {
                                    tvPayment.setText("支付方式：" + "余额支付");
                                } else if (spFindOrderDetailBean.getData().get(0).getPay_way() == 2) {
                                    tvPayment.setText("支付方式：" + "支付宝支付");
                                } else if (spFindOrderDetailBean.getData().get(0).getPay_way() == 3) {
                                    tvPayment.setText("支付方式：" + "微信支付");
                                }

                                tvShouhuorenName.setText(spFindOrderDetailBean.getData().get(0).getUser_name());
                                tvTelephone.setText(spFindOrderDetailBean.getData().get(0).getTelphone());
                                tvAddress.setText(spFindOrderDetailBean.getData().get(0).getPlace_address() + spFindOrderDetailBean.getData().get(0).getDetail_address());

                                //配送方式
                                if (spFindOrderDetailBean.getData().get(0).getIs_pickup() == 1) {
                                    tvPeisongfangshi.setText("配送方式：自提");
                                    llReceiptAddress.setVisibility(View.GONE);
                                } else {
                                    tvPeisongfangshi.setText("配送方式：送货上门");

                                }


                                int num = 0;
                                double total = 0;
                                double deliver = 0;
                                for (int index = 0; index < list.size(); index++) {
                                    if (list.get(index).getIs_deliver_fee() == 1) {
                                        total = list.get(index).getReal_price();
                                        deliver = list.get(index).getDeliver_fee();
                                    }
                                }


                                tvPeisong.setText("￥" + deliver);
                                tvIntegral.setText("所获积分：" + total);
                                tvNum.setText("共" + (list.size() - 1) + "件商品，");
                                tvTotal.setText("￥" + total);

                                state(spFindOrderDetailBean.getData().get(0).getOrder_state());
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_RefundDetails_sp.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), spFindOrderDetailBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                            ToastUtil.show(getApplicationContext(), e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }


    private void state(int state) {
        llBohui.setVisibility(View.GONE);
        if (state == 6) {
            tvState.setText("审核中");
        } else if (state == 7) {
            tvState.setText("审核通过");
        } else if (state == 8) {
            tvState.setText("退款中");
        } else if (state == 9) {
            tvState.setText("已退款");
        } else if (state == 10) {
            tvState.setText("已驳回");
            llBohui.setVisibility(View.VISIBLE);
        } else if (state == 11) {
            tvState.setText("退货中");
        }
    }
}
