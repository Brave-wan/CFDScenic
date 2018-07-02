package com.demo.my.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.adapter.MyOrderSpDetailsAdapter;
import com.demo.my.bean.SpFindOrderDetailBean;
import com.demo.my.fragment.MyOrderSpFragment2;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogMyOrderCollect;
import com.demo.view.DialogMyOrderComplete;
import com.demo.view.DialogProgressbar;
import com.demo.view.DialogSeafood;
import com.demo.view.NoScrollViewListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的订单--订单详情--商品
 * Created by Administrator on 2016/8/6 0006.
 */
public class Activity_OrderDetailsSp extends Activity {


    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.lv_listView)
    NoScrollViewListView lvListView;
    @Bind(R.id.tv_peisong)
    TextView tvPeisong;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
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
    @Bind(R.id.tv_quxiao)
    TextView tvQuxiao;
    @Bind(R.id.tv_quzhifu)
    TextView tvQuzhifu;
    @Bind(R.id.tv_shenqing)
    TextView tvShenqing;
    @Bind(R.id.tv_querenwancheng)
    TextView tvQuerenwancheng;
    @Bind(R.id.tv_jiaoyiwancheng)
    TextView tvJiaoyiwancheng;
    @Bind(R.id.tv_querenshouhuo)
    TextView tvQuerenshouhuo;
    @Bind(R.id.tv_fahuozhong)
    TextView tvFahuozhong;
    @Bind(R.id.tv_shanchu)
    TextView tvShanchu;
    @Bind(R.id.ll_state)
    LinearLayout llState;
    @Bind(R.id.tv_gongsi)
    TextView tvGongsi;
    @Bind(R.id.tv_bianhao)
    TextView tvBianhao;
    @Bind(R.id.ll_information)
    LinearLayout llInformation;
    @Bind(R.id.tv_total)
    TextView tvTotal;

    SpFindOrderDetailBean spFindOrderDetailBean;
    List<SpFindOrderDetailBean.DataBean> list;
    MyOrderSpDetailsAdapter myOrderSpDetailsAdapter;
    public static boolean bState = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_sp);
        ButterKnife.bind(this);

        bState = true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bState) {
            findOrderDetail();
            tvQuxiao.setVisibility(View.GONE);
            tvQuzhifu.setVisibility(View.GONE);
            tvQuerenshouhuo.setVisibility(View.GONE);
            tvShenqing.setVisibility(View.GONE);
            tvQuerenwancheng.setVisibility(View.GONE);
            tvShanchu.setVisibility(View.GONE);
            tvJiaoyiwancheng.setVisibility(View.GONE);
            tvFahuozhong.setVisibility(View.GONE);
            bState = false;
        }
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
        http.send(HttpRequest.HttpMethod.GET, URL.findOrderDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_OrderDetailsSp.this, R.style.AlertDialogStyle);

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


                                tvName.setText(spFindOrderDetailBean.getData().get(0).getUser_name());
                                tvPhone.setText(spFindOrderDetailBean.getData().get(0).getTelphone() + "");
                                tvAddress.setText(spFindOrderDetailBean.getData().get(0).getPlace_address() + spFindOrderDetailBean.getData().get(0).getDetail_address());
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
                                        tvNum.setText("共" + list.get(index).getQuantity() + "件商品，");
                                    }
                                }

                                tvPeisong.setText("￥" + deliver);
                                tvIntegral.setText("所获积分：" + total);

                                tvTotal.setText("￥" + total);

                                state(spFindOrderDetailBean.getData().get(0).getOrder_state());

                                //物流信息
                                if (spFindOrderDetailBean.getData().get(0).getExpress_code()!=null){
                                    if (!spFindOrderDetailBean.getData().get(0).getExpress_code().equals("")){
                                        tvGongsi.setText("物流公司："+spFindOrderDetailBean.getData().get(0).getExpress_name());
                                        tvBianhao.setText("物流信息："+spFindOrderDetailBean.getData().get(0).getExpress_code());
                                    }else {
                                        llInformation.setVisibility(View.GONE);
                                    }
                                }else {
                                    llInformation.setVisibility(View.GONE);
                                }
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_OrderDetailsSp.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), spFindOrderDetailBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接数据失败");
                    }
                });
    }


    private void state(int state) {
        if (state == 1) {
            tvQuzhifu.setVisibility(View.VISIBLE);
            tvQuxiao.setVisibility(View.VISIBLE);
            tvPaymentTime.setVisibility(View.GONE);
            tvPayment.setVisibility(View.GONE);
            tvState.setText("待付款");
        } else if (state == 2) {
            tvFahuozhong.setVisibility(View.VISIBLE);
            tvState.setText("已付款");
        } else if (state == 3) {
            tvQuerenshouhuo.setVisibility(View.VISIBLE);
            tvState.setText("待收货");
        } else if (state == 4) {
            tvShenqing.setVisibility(View.VISIBLE);
            tvQuerenwancheng.setVisibility(View.VISIBLE);
            tvState.setText("已收货");
        } else if (state == 5) {
            tvJiaoyiwancheng.setVisibility(View.VISIBLE);
            tvShanchu.setVisibility(View.VISIBLE);
            tvState.setText("已完成");
        } else if (state == 6) {
            llState.setVisibility(View.GONE);
            tvState.setText("审核中");
        }

        tvShanchu.setOnClickListener(new SetOnClick());
        tvQuxiao.setOnClickListener(new SetOnClick());
        tvQuzhifu.setOnClickListener(new SetOnClick());
        tvShenqing.setOnClickListener(new SetOnClick());
        tvQuerenwancheng.setOnClickListener(new SetOnClick());
        tvQuerenshouhuo.setOnClickListener(new SetOnClick());
    }


    private class SetOnClick implements View.OnClickListener {
        Dialog dialog;
        TextView text;

        public SetOnClick() {
            dialog = new Dialog(Activity_OrderDetailsSp.this, R.style.AlertDialogStyle);
            dialog.getWindow().setContentView(R.layout.dialog_cancel_order);
            dialog.setCanceledOnTouchOutside(false);

            text = (TextView) dialog.getWindow().findViewById(R.id.tv_content);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_quzhifu:
                    Intent intent = new Intent(Activity_OrderDetailsSp.this, Activity_OrderPaymentOrder.class);
                    intent.putExtra("id", 1);
                    intent.putExtra("orderCode", spFindOrderDetailBean.getData().get(0).getOrder_code() + "");
                    startActivity(intent);
                    break;
                case R.id.tv_shenqing:
                    if (spFindOrderDetailBean.getData().get(0).getIs_not_return()==1){//0不是  1是
                        DialogSeafood dialogSeafood=new DialogSeafood(Activity_OrderDetailsSp.this,R.style.AlertDialogStyle);
                        dialogSeafood.show();
                    }else {
                        intent = new Intent(getApplicationContext(), Activity_ApplyRefund.class);
                        intent.putExtra("id", 1);
                        intent.putExtra("orderState", spFindOrderDetailBean.getData().get(0).getOrder_state() + "");
                        intent.putExtra("orderCode", spFindOrderDetailBean.getData().get(0).getOrder_code() + "");
                        startActivity(intent);
                    }
                    break;
                case R.id.tv_quxiao:
                   /* DialogMyCancelOrder dialogMyCancelOrder = new DialogMyCancelOrder(Activity_OrderDetailsSp.this, R.style.AlertDialogStyle, 7, getIntent().getIntExtra("groupPosition", 0), 0);
                    dialogMyCancelOrder.show();*/
                    text.setText("确定取消该订单？");
                    dialog.show();

                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            finish();
                            MyOrderSpFragment2 fragment = (MyOrderSpFragment2) Activity_MyOrder.sp_1;
                            fragment.adapter.deleteMyOrder(getIntent().getIntExtra("groupPosition", 0), 0);
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog.dismiss();
                        }
                    });
                    break;
                case R.id.tv_shanchu:
                    text.setText("确定删除该订单？");
                    dialog.show();

                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            deleteMyOrder();
                            finish();
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog.dismiss();
                        }
                    });

                    break;
                case R.id.tv_querenwancheng:
                    DialogMyOrderComplete dialogMyOrderComplete = new DialogMyOrderComplete(Activity_OrderDetailsSp.this, R.style.AlertDialogStyle, 2, getIntent().getIntExtra("groupPosition", -1), 0);
                    dialogMyOrderComplete.show();
                    break;
                case R.id.tv_querenshouhuo:
                    DialogMyOrderCollect dialogMyOrderCollect = new DialogMyOrderCollect(Activity_OrderDetailsSp.this, R.style.AlertDialogStyle, 2, getIntent().getIntExtra("groupPosition", -1), 0);
                    dialogMyOrderCollect.show();
                    break;
            }
        }
    }

    //删除订单  取消订单
    public void deleteMyOrder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpName.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderState", spFindOrderDetailBean.getData().get(0).getOrder_state() + "");
        params.addQueryStringParameter("orderCode", spFindOrderDetailBean.getData().get(0).getOrder_code() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.deleteMyOrder, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int status = header.getInt("status");
                            if (status == 0) {
                                if (getIntent().getIntExtra("mState", -1) == 1) {
                                    MyOrderSpFragment2 myOrderSpFragment = (MyOrderSpFragment2) Activity_MyOrder.sp_1;
                                    myOrderSpFragment.mPage = 1;
                                    myOrderSpFragment.list.clear();
                                    myOrderSpFragment.findOrder();
                                } else if (getIntent().getIntExtra("mState", -1) == 4) {
                                    MyOrderSpFragment2 myOrderSpFragment4 = (MyOrderSpFragment2) Activity_MyOrder.sp_4;
                                    myOrderSpFragment4.mPage = 1;
                                    myOrderSpFragment4.list.clear();
                                    myOrderSpFragment4.findOrder();
                                }

                            } else if (status == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_OrderDetailsSp.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
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
}
