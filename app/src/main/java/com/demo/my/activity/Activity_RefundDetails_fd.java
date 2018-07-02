package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.adapter.RefundDetailsAdapter;
import com.demo.my.bean.FdDpFindOrderDetailBean;
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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 退款订单--订单详情--饭店
 * Created by Administrator on 2016/8/6 0006.
 */
public class Activity_RefundDetails_fd extends Activity {

    @Bind(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.lv_listView)
    NoScrollViewListView lvListView;
    @Bind(R.id.tv_create)
    TextView tvCreate;
    @Bind(R.id.tv_paymentTime)
    TextView tvPaymentTime;
    @Bind(R.id.tv_payment)
    TextView tvPayment;
    @Bind(R.id.tv_Number)
    TextView tvNumber;
    @Bind(R.id.tv_jiucan)
    TextView tvJiucan;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_zonge)
    TextView tvZonge;
    @Bind(R.id.ll_add)
    LinearLayout llAdd;

    List<FdDpFindOrderDetailBean.DataBean> list;
    FdDpFindOrderDetailBean findOrderDetailBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_details_fd);
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
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_RefundDetails_fd.this,R.style.AlertDialogStyle);
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

                            findOrderDetailBean = new Gson().fromJson(responseInfo.result, FdDpFindOrderDetailBean.class);
                            int i = findOrderDetailBean.getHeader().getStatus();
                            if (i == 0) {
                                tvOrderNumber.setText("订单号：" + findOrderDetailBean.getData().get(0).getOrder_code());//订单号
                                //tvYouxiaoqi.setText(findOrderDetailBean.getData().get(0).getStart_date().substring(0, 10) + findOrderDetailBean.getData().get(0).getEnd_date().substring(0, 10));
                                tvNumber.setText("数量：" + findOrderDetailBean.getData().get(0).getQuantity());
                                tvJiucan.setText(findOrderDetailBean.getData().get(0).getEat_date());

                                list=findOrderDetailBean.getData();
                                lvListView.setAdapter(new RefundDetailsAdapter(Activity_RefundDetails_fd.this,list));

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
                                //tvIntegral.setText("所获积分：" + findOrderDetailBean.getData().get(0).getReal_price());
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

                                state(findOrderDetailBean.getData().get(0).getOrder_state());
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_RefundDetails_fd.this);
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
        if (state == 1) {
            tvState.setText("代付款");
        } else if (state == 2) {
            tvState.setText("已付款");
        }
    }
}
