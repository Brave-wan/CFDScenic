package com.demo.my.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.OrderDetailBean;
import com.demo.scenicspot.activity.ChosePaywayActivity;
import com.demo.scenicspot.fragment.ZxingFragment;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogApplyRefund;
import com.demo.view.DialogCancelOrder;
import com.demo.view.DialogProgressbar;
import com.demo.view.DialogRefundOk;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的门票--订单详情
 * Created by Administrator on 2016/8/6 0006.
 */
public class Activity_MyTicketDetails extends FragmentActivity {


    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.iv_ScenicSpot)
    ImageView ivScenicSpot;
    @Bind(R.id.tv_ScenicSpotName)
    TextView tvScenicSpotName;
    @Bind(R.id.tv_youxiaoqi)
    TextView tvYouxiaoqi;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_create)
    TextView tvCreate;
    @Bind(R.id.tv_paymentTime)
    TextView tvPaymentTime;
    @Bind(R.id.tv_payment)
    TextView tvPayment;
    @Bind(R.id.tv_Number)
    TextView tvNumber;
    @Bind(R.id.tv_zonge)
    TextView tvZonge;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.tv_sqtk)
    TextView tvSqtk;
    @Bind(R.id.tv_ckewm)
    TextView tvCkewm;
    @Bind(R.id.tv_qxdd)
    TextView tvQxdd;
    @Bind(R.id.tv_scdd)
    TextView tvScdd;
    @Bind(R.id.tv_qzf)
    TextView tvQzf;
    @Bind(R.id.tv_qpj)
    TextView tvQpj;
    @Bind(R.id.tv_zcgm)
    TextView tvZcgm;
    @Bind(R.id.tv_ypj)
    TextView tvYpj;
    @Bind(R.id.tv_tkz)
    TextView tvTkz;
    @Bind(R.id.tv_tkcg)
    TextView tvTkcg;
    @Bind(R.id.tv_tksb)
    TextView tvTksb;
    @Bind(R.id.tv_bxpz)
    TextView tvBxpz;
    @Bind(R.id.tv_yjfq)
    TextView tvYjfq;
    @Bind(R.id.tv_sjrxm)
    TextView tvSjrxm;
    @Bind(R.id.tv_szdq)
    TextView tvSzdq;
    @Bind(R.id.tv_xxdz)
    TextView tvXxdz;
    @Bind(R.id.tv_sjhm)
    TextView tvSjhm;
    @Bind(R.id.ll_yjfq)
    LinearLayout llYjfq;
    @Bind(R.id.ll_sjrxm)
    LinearLayout llSjrxm;
    @Bind(R.id.ll_szdq)
    LinearLayout llSzdq;
    @Bind(R.id.ll_xxdz)
    LinearLayout llXxdz;
    @Bind(R.id.ll_sjhm)
    LinearLayout llSjhm;
    @Bind(R.id.vp_image)
    ViewPager vpImage;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.ll_erweima)
    LinearLayout llErweima;

    OrderDetailBean orderDetailBean=new OrderDetailBean();
    public static boolean myTicketDetails = false;//判断是否需要刷新界面  支付成功 调用
    ArrayList<Fragment> fragmentList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_details);
        ButterKnife.bind(this);
        myTicketDetails = true;

        vpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvNum.setText((position + 1) + "/" + fragmentList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myTicketDetails) {
            myTicketDetails = false;
            orderDetail();
        }
    }


    private void orderDetail() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF-8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.orderDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_MyTicketDetails.this,R.style.AlertDialogStyle);
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        orderDetailBean = new Gson().fromJson(responseInfo.result, OrderDetailBean.class);
                        int i = orderDetailBean.getHeader().getStatus();
                        try {
                            if (i == 0) {
                                initFragment();
                                ImageLoader.getInstance().displayImage(orderDetailBean.getData().get(0).getHead_img(), ivScenicSpot);
                                tvOrderNumber.setText("订单号：" + orderDetailBean.getData().get(0).getOrder_code());
                                //订单状态
                                order_state(orderDetailBean.getData().get(0).getOrder_state());

                                tvScenicSpotName.setText(orderDetailBean.getData().get(0).getName());
                                tvYouxiaoqi.setText("截止时间：" + orderDetailBean.getData().get(0).getEnd_valid().substring(0, 10));
                                tvTotal.setText("￥" + orderDetailBean.getData().get(0).getPrice());
                                tvCreate.setText("创建时间：" + orderDetailBean.getData().get(0).getCreate_time());
                                if (orderDetailBean.getData().get(0).getPay_time() == null) {
                                    tvPaymentTime.setVisibility(View.GONE);
                                } else {
                                    tvPaymentTime.setText("付款时间：" + orderDetailBean.getData().get(0).getPay_time());
                                }


                                if (orderDetailBean.getData().get(0).getPay_way() == 0) {
                                    tvPayment.setVisibility(View.GONE);
                                } else if (orderDetailBean.getData().get(0).getPay_way() == 1) {
                                    tvPayment.setText("支付方式：余额支付");
                                } else if (orderDetailBean.getData().get(0).getPay_way() == 2) {
                                    tvPayment.setText("支付方式：支付宝支付");
                                } else if (orderDetailBean.getData().get(0).getPay_way() == 3) {
                                    tvPayment.setText("支付方式：微信支付");
                                }
                                if (orderDetailBean.getData().get(0).getIs_invoice() == 0) {
                                    tvBxpz.setText("不需要");
                                } else {
                                    if (orderDetailBean.getData().get(0).getIs_mention() == 0) {
                                        tvBxpz.setText("自提");
                                    } else if (orderDetailBean.getData().get(0).getIs_mention() == 1) {
                                        tvBxpz.setText("邮寄");
                                        llYjfq.setVisibility(View.VISIBLE);
                                        llSjrxm.setVisibility(View.VISIBLE);
                                        llSzdq.setVisibility(View.VISIBLE);
                                        llXxdz.setVisibility(View.VISIBLE);
                                        llSjhm.setVisibility(View.VISIBLE);

                                        tvYjfq.setText("￥" + orderDetailBean.getData().get(0).getDeliver_fee());
                                        tvSjrxm.setText(orderDetailBean.getData().get(0).getUser_name());
                                        tvSzdq.setText(orderDetailBean.getData().get(0).getPlace_address());
                                        tvXxdz.setText(orderDetailBean.getData().get(0).getDetail_address());
                                        tvSjhm.setText(orderDetailBean.getData().get(0).getTelphone());
                                    }
                                }

                                tvNumber.setText("数量：" + orderDetailBean.getData().size());
                                tvZonge.setText("总额：￥" + orderDetailBean.getData().get(0).getReal_price());
                                tvIntegral.setText("所获积分：" + orderDetailBean.getData().get(0).getReal_price());
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_MyTicketDetails.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), orderDetailBean.getHeader().getMsg());
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

    private void order_state(int order_state) {
        tvSqtk.setVisibility(View.GONE);
        tvCkewm.setVisibility(View.GONE);
        tvQxdd.setVisibility(View.GONE);
        tvScdd.setVisibility(View.GONE);
        tvQzf.setVisibility(View.GONE);
        tvQpj.setVisibility(View.GONE);
        tvZcgm.setVisibility(View.GONE);
        tvYpj.setVisibility(View.GONE);
        tvTkz.setVisibility(View.GONE);
        tvTkcg.setVisibility(View.GONE);
        tvTksb.setVisibility(View.GONE);


        if (order_state == 1) {
            llErweima.setVisibility(View.GONE);
            tvPaymentTime.setVisibility(View.GONE);
            tvPayment.setVisibility(View.VISIBLE);
            tvState.setText("待支付");
            tvQxdd.setVisibility(View.VISIBLE);
            tvQzf.setVisibility(View.VISIBLE);
        } else if (order_state == 2) {
            llErweima.setVisibility(View.VISIBLE);
            tvState.setText("未使用");
            tvSqtk.setVisibility(View.VISIBLE);
        } else if (order_state == 3) {
            llErweima.setVisibility(View.GONE);
            tvState.setText("待评价");
            tvScdd.setVisibility(View.VISIBLE);
            tvQpj.setVisibility(View.VISIBLE);
        } else if (order_state == 4) {
            llErweima.setVisibility(View.GONE);
            tvState.setText("交易完成");
            tvScdd.setVisibility(View.VISIBLE);
            tvYpj.setVisibility(View.VISIBLE);
        } else if (order_state == 5) {
            llErweima.setVisibility(View.GONE);
            tvState.setText("退款中");
            tvTkz.setVisibility(View.VISIBLE);
        } else if (order_state == 6) {
            llErweima.setVisibility(View.GONE);
            tvState.setText("申请失败");
            tvTksb.setVisibility(View.VISIBLE);
        } else if (order_state == 7) {
            llErweima.setVisibility(View.GONE);
            tvState.setText("申请成功");
            tvScdd.setVisibility(View.VISIBLE);
            tvTkcg.setVisibility(View.VISIBLE);
        } else if (order_state == 8) {
            llErweima.setVisibility(View.GONE);
            tvState.setText("已过期");
            tvScdd.setVisibility(View.VISIBLE);
            //tvZcgm.setVisibility(View.VISIBLE);
        }

        tvQxdd.setOnClickListener(new SetOnClick(0));
        tvSqtk.setOnClickListener(new SetOnClick(0));
        tvQpj.setOnClickListener(new SetOnClick(0));
        tvQzf.setOnClickListener(new SetOnClick(0));
        tvScdd.setOnClickListener(new SetOnClick(0));
    }


    private class SetOnClick implements View.OnClickListener {
        int position;

        public SetOnClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.tv_qxdd:
                    DialogCancelOrder dialogCancelOrder = new DialogCancelOrder(Activity_MyTicketDetails.this, R.style.AlertDialogStyle, position, 1);
                    dialogCancelOrder.show();
                    break;
                case R.id.tv_qzf:
                    intent = new Intent(Activity_MyTicketDetails.this, ChosePaywayActivity.class);
                    intent.putExtra("entrance", 2);
                    intent.putExtra("data", orderDetailBean.getData().get(position).getOrder_code() + "");
                    intent.putExtra("youxiaoqi", orderDetailBean.getData().get(position).getEnd_valid());
                    if (orderDetailBean.getData().get(position).getIs_mention() == 1) {
                        intent.putExtra("invoice", 2);//判断是否需要发票    邮寄
                        intent.putExtra("deliver_fee", orderDetailBean.getData().get(position).getDeliver_fee() + "");
                        intent.putExtra("name", orderDetailBean.getData().get(position).getName());
                        intent.putExtra("telphone", orderDetailBean.getData().get(position).getTelphone());
                        intent.putExtra("address", orderDetailBean.getData().get(position).getPlace_address() + orderDetailBean.getData().get(position).getDetail_address());
                    } else if (orderDetailBean.getData().get(position).getIs_mention() == 0) {
                        intent.putExtra("invoice", 1);//判断是否需要发票    自提
                    }
                    startActivity(intent);
                    break;
                case R.id.tv_sqtk:
                    //可以申请
                    DialogApplyRefund dialogApplyRefund = new DialogApplyRefund(Activity_MyTicketDetails.this, R.style.AlertDialogStyle, position, 1);
                    dialogApplyRefund.show();
                   /* //不可以申请
                    DialogRefundNo dialogRefundNo = new Dia logRefundNo(mContext, R.style.AlertDialogStyle);
                    dialogRefundNo.show();*/
                    break;
                case R.id.tv_ckewm:

                    break;
                case R.id.tv_scdd:
                    final Dialog dialog = new Dialog(Activity_MyTicketDetails.this, R.style.AlertDialogStyle);
                    dialog .getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog .setCanceledOnTouchOutside(false);
                    dialog .show();
                    TextView text = (TextView) dialog .getWindow().findViewById(R.id.tv_content);
                    text.setText("确定删除该订单？");
                    dialog .getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            deleteMyTickets(position);
                            dialog .dismiss();
                        }
                    });
                    dialog .getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog.dismiss();
                        }
                    });
                    break;
                case R.id.tv_qpj:
                    intent = new Intent(Activity_MyTicketDetails.this, Activity_ToEvaluate.class);
                    intent.putExtra("entrance", 2);
                    /*intent.putExtra("id",list.get(position).getTicketslist().get(0).getVisitorsId()+"");
                    intent.putExtra("orderCode",list.get(position).getTicketslist().get(0).getOrder_code()+"");
                    intent.putExtra("image",list.get(position).getTicketslist().get(0).getHead_img()+"");
                    intent.putExtra("name",list.get(position).getTicketslist().get(0).getName()+"");
                    intent.putExtra("date",list.get(position).getTicketslist().get(0).getEnd_valid()+"");
                    intent.putExtra("price",list.get(position).getTicketslist().get(0).getReal_price()+"");*/
                    intent.putExtra("id", orderDetailBean.getData().get(0).getId() + "");
                    intent.putExtra("orderCode", orderDetailBean.getData().get(0).getOrder_code() + "");
                    intent.putExtra("image", orderDetailBean.getData().get(0).getHead_img() + "");
                    intent.putExtra("name", orderDetailBean.getData().get(0).getName() + "");
                    intent.putExtra("date", orderDetailBean.getData().get(0).getEnd_valid() + "");
                    intent.putExtra("price", orderDetailBean.getData().get(0).getReal_price() + "");
                    startActivity(intent);
                    break;
                case R.id.tv_zcgm:
                    intent = new Intent(Activity_MyTicketDetails.this, ChosePaywayActivity.class);
                    intent.putExtra("entrance", 2);
                    intent.putExtra("data", orderDetailBean.getData().get(position).getOrder_code() + "");
                    intent.putExtra("youxiaoqi", orderDetailBean.getData().get(position).getEnd_valid());
                    if (orderDetailBean.getData().get(position).getIs_mention() == 1) {
                        intent.putExtra("invoice", 2);//判断是否需要发票    邮寄
                        intent.putExtra("deliver_fee", orderDetailBean.getData().get(position).getDeliver_fee() + "");
                        intent.putExtra("name", orderDetailBean.getData().get(position).getName());
                        intent.putExtra("telphone", orderDetailBean.getData().get(position).getTelphone());
                        intent.putExtra("address", orderDetailBean.getData().get(position).getPlace_address() + orderDetailBean.getData().get(position).getDetail_address());
                    } else if (orderDetailBean.getData().get(position).getIs_mention() == 0) {
                        intent.putExtra("invoice", 1);//判断是否需要发票    自提
                    }
                    startActivity(intent);
                    break;
            }
        }
    }

    //删除订单
    public void deleteMyTickets(final int position) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpName.token, ""));
        params.addQueryStringParameter("orderCode", orderDetailBean.getData().get(position).getOrder_code() + "");
        params.addQueryStringParameter("orderState", orderDetailBean.getData().get(position).getOrder_state() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF-8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.deleteMyTickets, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_MyTicketDetails.this,R.style.AlertDialogStyle);
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
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                finish();
                                Activity_MyTicket.myTicket = true;
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_MyTicketDetails.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
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

    //申请退款
    public void refund(final int position) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpName.token, ""));
        params.addQueryStringParameter("orderCode", orderDetailBean.getData().get(position).getOrder_code() + "");
        params.addQueryStringParameter("endValid", orderDetailBean.getData().get(position).getEnd_valid() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF-8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.refund, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_MyTicketDetails.this,R.style.AlertDialogStyle);
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
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                orderDetail();
                                Activity_MyTicket.myTicket = true;
                                DialogRefundOk dialogRefundOk = new DialogRefundOk(Activity_MyTicketDetails.this, R.style.AlertDialogStyle);
                                dialogRefundOk.show();
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_MyTicketDetails.this);
                            } else if (i == 4) {
                                ToastUtil.show(getApplicationContext(), "不能申请退款");
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
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


    private void initFragment() {
        for (int i = 0; i < orderDetailBean.getData().size(); i++) {
            ZxingFragment viewFragment = new ZxingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Id", orderDetailBean.getData().get(i).getId() + "");
            viewFragment.setArguments(bundle);
            fragmentList.add(viewFragment);
        }
        tvNum.setText((0 + 1) + "/" + fragmentList.size());
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
