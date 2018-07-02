package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
 * 订单详情--纪念品
 * Created by Administrator on 2016/8/5 0005.
 */
public class Activity_OrderDetailsKeepsake extends Activity {
    @Bind(R.id.order_code)
    TextView orderCode;
    @Bind(R.id.head_img)
    ImageView headImg;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_integra)
    TextView tvIntegra;
    @Bind(R.id.ll_ProductDetails)
    LinearLayout llProductDetails;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.tv_telphone)
    TextView tvTelphone;
    @Bind(R.id.tv_Address)
    TextView tvAddress;
    @Bind(R.id.tv_chuangjian)
    TextView tvChuangjian;
    @Bind(R.id.tv_fukuan)
    TextView tvFukuan;
    @Bind(R.id.tv_zhifu)
    TextView tvZhifu;
    @Bind(R.id.tv_peisong)
    TextView tvPeisong;
    @Bind(R.id.tv_zhonge)
    TextView tvZhonge;
    @Bind(R.id.ll_address)
    LinearLayout llAddress;


    String id;
    selectVisitorsOrderDetailBean selectVisitorsOrderDetailBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_keepsake);
        ButterKnife.bind(this);

        id = getIntent().getStringExtra("id");
        selectVisitorsOrderDetail();
    }

    @OnClick(R.id.ll_ProductDetails)
    public void onClick() {
        Intent intent = new Intent(getApplicationContext(), Activity_ExchangeDetails.class);
        intent.putExtra("id", id);
        intent.putExtra("type", 1);
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
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_OrderDetailsKeepsake.this,R.style.AlertDialogStyle);
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
                                BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
                                bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);//加载失败
                                bitmapUtils.configDefaultLoadingImage(R.mipmap.zhanwei);//加载过程中
                                bitmapUtils.display(headImg, selectVisitorsOrderDetailBean.getData().getHead_img());
                                tvName.setText(selectVisitorsOrderDetailBean.getData().getName());
                                tvUserName.setText(selectVisitorsOrderDetailBean.getData().getUser_name());
                                tvTelphone.setText(selectVisitorsOrderDetailBean.getData().getTelphone() + "");
                                tvIntegra.setText(selectVisitorsOrderDetailBean.getData().getIntegra_price() + "积分");
                                tvAddress.setText(selectVisitorsOrderDetailBean.getData().getPlace_address() + selectVisitorsOrderDetailBean.getData().getDetail_address());
                                tvChuangjian.setText("创建时间：" + selectVisitorsOrderDetailBean.getData().getCreate_time());
                                tvFukuan.setText("付款方式：" + selectVisitorsOrderDetailBean.getData().getPay_time());
                                tvZhifu.setText("支付方式：" + "在线支付");
                                if (selectVisitorsOrderDetailBean.getData().getIs_mention() == 1) {
                                    tvPeisong.setText("配送方式：" + "自提");
                                    llAddress.setVisibility(View.GONE);
                                } else if (selectVisitorsOrderDetailBean.getData().getIs_mention() == 0) {
                                    tvPeisong.setText("配送方式：" + "送货上门");
                                    llAddress.setVisibility(View.VISIBLE);
                                }

                                tvZhonge.setText("总额：￥" + selectVisitorsOrderDetailBean.getData().getIntegra_price());
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_OrderDetailsKeepsake.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), selectVisitorsOrderDetailBean.getHeader().getMsg());
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
