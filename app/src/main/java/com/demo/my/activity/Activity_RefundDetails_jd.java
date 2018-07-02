package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.FindOrderDetailBean;
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
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 退款订单--订单详情--酒店
 * Created by Administrator on 2016/8/6 0006.
 */
public class Activity_RefundDetails_jd extends Activity {

    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_youxiaoqi)
    TextView tvYouxiaoqi;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_adapter_my_bottomline)
    TextView tvAdapterMyBottomline;
    @Bind(R.id.tv_create)
    TextView tvCreate;
    @Bind(R.id.tv_paymentTime)
    TextView tvPaymentTime;
    @Bind(R.id.tv_payment)
    TextView tvPayment;
    @Bind(R.id.tv_ruzhuren)
    TextView tvRuzhuren;
    @Bind(R.id.tv_fangjianshu)
    TextView tvFangjianshu;
    @Bind(R.id.tv_shijian)
    TextView tvShijian;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_zonge)
    TextView tvZonge;

    FindOrderDetailBean findOrderDetailBean;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.iv_image)
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_details_jd);
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
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_RefundDetails_jd.this,R.style.AlertDialogStyle);
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
                            findOrderDetailBean = new Gson().fromJson(responseInfo.result, FindOrderDetailBean.class);
                            int i = findOrderDetailBean.getHeader().getStatus();
                            if (i == 0) {
                                tvOrderNumber.setText("订单号：" + findOrderDetailBean.getData().get(0).getOrder_code());//订单号
                                ImageLoader.getInstance().displayImage(findOrderDetailBean.getData().get(0).getDescribe_img(), ivImage);
                                tvTotal.setText("￥" + findOrderDetailBean.getData().get(0).getReal_price());
                                tvYouxiaoqi.setText(findOrderDetailBean.getData().get(0).getStart_date().substring(0, 10) + findOrderDetailBean.getData().get(0).getEnd_date().substring(0, 10));
                                tvCreate.setText("创建时间：" + findOrderDetailBean.getData().get(0).getCreate_time());

                                tvPaymentTime.setText("付款时间：" + findOrderDetailBean.getData().get(0).getPay_time());
                                //判断支付方式
                                if (findOrderDetailBean.getData().get(0).getPay_way() == 1) {
                                    tvPayment.setText("支付方式：" + "余额支付");
                                } else if (findOrderDetailBean.getData().get(0).getPay_way() == 2) {
                                    tvPayment.setText("支付方式：" + "支付宝支付");
                                } else if (findOrderDetailBean.getData().get(0).getPay_way() == 3) {
                                    tvPayment.setText("支付方式：" + "微信支付");
                                }

                                tvName.setText(findOrderDetailBean.getData().get(0).getName());
                                tvType.setText(findOrderDetailBean.getData().get(0).getGoods_name());

                                tvYouxiaoqi.setText("有效期："+findOrderDetailBean.getData().get(0).getEnd_date().substring(0, 10));
                                tvRuzhuren.setText("入住人：" + findOrderDetailBean.getData().get(0).getPersonName());
                                tvShijian.setText("入住——" + findOrderDetailBean.getData().get(0).getStart_date().substring(0, 10) +
                                        "\n离店——" + findOrderDetailBean.getData().get(0).getEnd_date().substring(0, 10) + "；" + findOrderDetailBean.getData().get(0).getCheck_days() + "晚");
                                tvFangjianshu.setText("房间数：" + findOrderDetailBean.getData().get(0).getQuantity());
                                tvPhone.setText("联系电话：" + findOrderDetailBean.getData().get(0).getTelphone());
                                tvZonge.setText("总额：" + findOrderDetailBean.getData().get(0).getReal_price());

                                state(findOrderDetailBean.getData().get(0).getOrder_state());
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_RefundDetails_jd.this);
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


    private void state(int state){
        if (state == 3) {
            tvState.setText("退款中");
        } else if (state == 6) {
            tvState.setText("已退款");
        }
    }
}

