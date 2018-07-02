package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogProgressbar;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 积分兑换--确认订单--门票
 * Created by Administrator on 2016/8/3 0003.
 */
public class Activity_ExchangeOkTicket extends Activity {


    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_jifen)
    TextView tvJifen;
    @Bind(R.id.tv_youxiaoqi)
    TextView tvYouxiaoqi;
    @Bind(R.id.tv_zognjifen)
    TextView tvZognjifen;

    String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_ok_ticket);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        BitmapUtils bitmapUtils = new BitmapUtils(getApplication());
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);//加载失败
        bitmapUtils.configDefaultLoadingImage(R.mipmap.zhanwei);//加载过程中
        bitmapUtils.display(ivImage, getIntent().getStringExtra("image"));

        tvName.setText(getIntent().getStringExtra("name"));
        tvJifen.setText(getIntent().getStringExtra("integral") + "积分");
        tvYouxiaoqi.setText(getIntent().getStringExtra("start") + "~" + getIntent().getStringExtra("end"));
        tvZognjifen.setText(getIntent().getStringExtra("integral") + "积分");
    }

    @OnClick(R.id.tv_confirm)
    public void onClick() {
        saveIntegralOrder();
    }


    //确认兑换
    private void saveIntegralOrder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("name", getIntent().getStringExtra("name") + "");
        params.addQueryStringParameter("visitorsId", getIntent().getStringExtra("id") + "");
        params.addQueryStringParameter("orderDescribe", "订单描述");
        params.addQueryStringParameter("addressed", "");
        params.addQueryStringParameter("integraPrice", getIntent().getStringExtra("integral") + "");
        params.addQueryStringParameter("isMention", "");
        params.addQueryStringParameter("price", getIntent().getStringExtra("deliver_fee") + "");
        params.addQueryStringParameter("pay_way", "4");
        params.addQueryStringParameter("startValid", getIntent().getStringExtra("start") + "");
        params.addQueryStringParameter("endValid", getIntent().getStringExtra("end") + "");
       /* params.addQueryStringParameter("quantity", "1");
        params.addQueryStringParameter("visitorsId", getIntent().getStringExtra("id") + "");*/
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.saveIntegralOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ExchangeOkTicket.this,R.style.AlertDialogStyle);
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
                                orderId=jsonObject.getLong("data")+"";
                                confirmPayment();

                            } else if (i == 3) {
                                DialogAgainSignIn dialogAgainSignIn = new DialogAgainSignIn(getApplicationContext(), R.style.AlertDialogStyle);
                                dialogAgainSignIn.show();
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

    //确认支付
    private void confirmPayment() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("orderId", orderId);
        params.addQueryStringParameter("balance", "");
        params.addQueryStringParameter("price", "");
        params.addQueryStringParameter("integration", getIntent().getStringExtra("integral")+ "");
        params.addQueryStringParameter("payType","4");
        params.addQueryStringParameter("name", getIntent().getStringExtra("name"));
        params.addQueryStringParameter("passWord", "");
        //params.addQueryStringParameter("integration", integration);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.confirmPayment, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ExchangeOkTicket.this,R.style.AlertDialogStyle);
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
                                Intent intent = new Intent(getApplicationContext(), Activity_ExchangeResultesTicket.class);
                                intent.putExtra("id",  orderId);
                                startActivity(intent);
                                finish();
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ExchangeOkTicket.this);
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
}
