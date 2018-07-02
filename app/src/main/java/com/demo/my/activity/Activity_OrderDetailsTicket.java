package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.selectVisitorsOrderDetailBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
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
 * 订单详情--门票
 * Created by Administrator on 2016/8/5 0005.
 */
public class Activity_OrderDetailsTicket extends Activity {

    @Bind(R.id.ll_ProductDetails)
    LinearLayout llProductDetails;
    @Bind(R.id.order_code)
    TextView orderCode;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_integra)
    TextView tvIntegra;
    @Bind(R.id.tv_youxiaoqi)
    TextView tvYouxiaoqi;
    @Bind(R.id.tv_chuangjian)
    TextView tvChuangjian;
    @Bind(R.id.tv_duihuan)
    TextView tvDuihuan;


    String id;
    selectVisitorsOrderDetailBean selectVisitorsOrderDetailBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_ticket);
        ButterKnife.bind(this);

        id = getIntent().getStringExtra("id");
        selectVisitorsOrderDetail();
    }

    @OnClick(R.id.ll_ProductDetails)
    public void onClick() {
        Intent intent = new Intent(getApplicationContext(), Activity_ExchangeDetails.class);
        startActivity(intent);
    }


    //兑换列表详情
    private void selectVisitorsOrderDetail() {
        RequestParams params = new RequestParams();
        //params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("id", id);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.selectVisitorsOrderDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_OrderDetailsTicket.this,R.style.AlertDialogStyle);
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
                            selectVisitorsOrderDetailBean = new Gson().fromJson(responseInfo.result, selectVisitorsOrderDetailBean.class);
                            int i = selectVisitorsOrderDetailBean.getHeader().getStatus();
                            if (i == 0) {
                                orderCode.setText("订单号：" + selectVisitorsOrderDetailBean.getData().getId());
                                String start=selectVisitorsOrderDetailBean.getData().getStart_valid().substring(0,10);
                                String end=selectVisitorsOrderDetailBean.getData().getEnd_valid().substring(0,10);
                                tvYouxiaoqi.setText("有效期：" + start + "~" + end);
                                BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
                                bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);//加载失败
                                bitmapUtils.configDefaultLoadingImage(R.mipmap.zhanwei);//加载过程中
                                bitmapUtils.display(ivImage, selectVisitorsOrderDetailBean.getData().getHead_img());
                                tvName.setText(selectVisitorsOrderDetailBean.getData().getName());
                                tvIntegra.setText(selectVisitorsOrderDetailBean.getData().getIntegra_price() + "积分");
                                tvChuangjian.setText("创建时间：" + selectVisitorsOrderDetailBean.getData().getCreate_time());
                                tvDuihuan.setText("兑换时间："+selectVisitorsOrderDetailBean.getData().getPay_time());
                                tvIntegra.setText(selectVisitorsOrderDetailBean.getData().getIntegra_price()+"积分");
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_OrderDetailsTicket.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), selectVisitorsOrderDetailBean.getHeader().getMsg());
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
