package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.FdDpFindOrderDetailBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogMyOrderDelete;
import com.demo.view.DialogRefund;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的订单--订单详情--饭店--单品  UI图版本
 * Created by Administrator on 2016/8/6 0006.
 */
public class Activity_OrderDetailsFdDp2 extends Activity {

    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.iv_business)
    ImageView ivBusiness;
    @Bind(R.id.tv_businessName)
    TextView tvBusinessName;
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
    @Bind(R.id.tv_jiucan)
    TextView tvJiucan;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_zonge)
    TextView tvZonge;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.ll_add)
    LinearLayout llAdd;
    @Bind(R.id.tv_shenqing)
    TextView tvShenqing;
    @Bind(R.id.tv_yiguoqi)
    TextView tvYiguoqi;
    @Bind(R.id.tv_wancheng)
    TextView tvWancheng;
    @Bind(R.id.tv_shanchu)
    TextView tvShanchu;
    @Bind(R.id.tv_zaici)
    TextView tvZaici;
    @Bind(R.id.ll_state)
    LinearLayout llState;

    FdDpFindOrderDetailBean findOrderDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_fddp);
        ButterKnife.bind(this);


        findOrderDetail();
    }


    private void findOrderDetail() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("type", getIntent().getStringExtra("type"));
        params.addQueryStringParameter("orderId", getIntent().getStringExtra("orderId"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findOrderDetail, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            findOrderDetailBean = new Gson().fromJson(responseInfo.result, FdDpFindOrderDetailBean.class);
                            int i = findOrderDetailBean.getHeader().getStatus();
                            if (i == 0) {
                                tvOrderNumber.setText("订单号：" + findOrderDetailBean.getData().get(0).getOrder_code());//订单号
                                ImageLoader.getInstance().displayImage(findOrderDetailBean.getData().get(0).getDescribe_img(), ivBusiness);
                                tvBusinessName.setText(findOrderDetailBean.getData().get(0).getName());
                                tvTotal.setText("￥" + findOrderDetailBean.getData().get(0).getReal_price());
                                //tvYouxiaoqi.setText(findOrderDetailBean.getData().get(0).getStart_date().substring(0, 10) + findOrderDetailBean.getData().get(0).getEnd_date().substring(0, 10));
                                state(findOrderDetailBean.getData().get(0).getOrder_state());
                                tvJiucan.setText(findOrderDetailBean.getData().get(0).getEat_date());
                                //判断支付方式
                                if (findOrderDetailBean.getData().get(0).getPay_way() == 1) {
                                    tvPayment.setText("支付方式：" + "余额支付");
                                } else if (findOrderDetailBean.getData().get(0).getPay_way() == 2) {
                                    tvPayment.setText("支付方式：" + "支付宝支付");
                                } else if (findOrderDetailBean.getData().get(0).getPay_way() == 3) {
                                    tvPayment.setText("支付方式：" + "微信支付");
                                }
                                tvCreate.setText("创建时间：" + findOrderDetailBean.getData().get(0).getCreate_time());
                                tvPaymentTime.setText("付款时间：" + findOrderDetailBean.getData().get(0).getPay_time());
                                tvPhone.setText("联系电话：" + findOrderDetailBean.getData().get(0).getTelphone());
                                tvIntegral.setText("所获积分：" + findOrderDetailBean.getData().get(0).getReal_price());
                                tvZonge.setText("总额：" + findOrderDetailBean.getData().get(0).getReal_price());

                                for (int index = 0; index < findOrderDetailBean.getData().size(); index++) {
                                    View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_package_content, null);
                                    TextView textView1 = (TextView) view.findViewById(R.id.name);
                                    TextView textView2 = (TextView) view.findViewById(R.id.num);
                                    TextView textView3 = (TextView) view.findViewById(R.id.price);

                                    textView1.setText(findOrderDetailBean.getData().get(index).getName() + ",");
                                    textView2.setText("1份,");
                                    textView3.setText(findOrderDetailBean.getData().get(index).getPrice() + "");
                                    llAdd.addView(view);
                                }
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_OrderDetailsFdDp2.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), findOrderDetailBean.getHeader().getMsg());
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

    private void state(int state) {
        if (state == 2) {
            tvShenqing.setVisibility(View.VISIBLE);
            tvShenqing.setOnClickListener(new SetOnClick());
            tvState.setText("未使用");
        } else if (state == 3) {
            tvState.setText("申请退款");
        } else if (state == 4) {
            tvWancheng.setVisibility(View.VISIBLE);
            tvShanchu.setVisibility(View.VISIBLE);
            tvState.setText("已使用");
            tvShanchu.setOnClickListener(new SetOnClick());
        } else if (state == 5) {
            tvZaici.setVisibility(View.VISIBLE);
            tvShanchu.setVisibility(View.VISIBLE);
            tvState.setText("已过期");
            tvShanchu.setOnClickListener(new SetOnClick());
        } else if (state == 6) {

        } else if (state == 7) {

        }
    }


    private class SetOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_shenqing:
                    DialogRefund dialogRefund = new DialogRefund(Activity_OrderDetailsFdDp2.this, R.style.AlertDialogStyle, 5,getIntent().getIntExtra("position",-1), 0);
                    dialogRefund.show();
                    break;
                case R.id.tv_zaici:

                    break;
                case R.id.tv_shanchu:
                    DialogMyOrderDelete dialogMyOrderDelete = new DialogMyOrderDelete(Activity_OrderDetailsFdDp2.this, R.style.AlertDialogStyle, 5,getIntent().getIntExtra("position",-1), 0);
                    dialogMyOrderDelete.show();
                    break;
            }
        }
    }
}
