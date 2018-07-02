package com.demo.mall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.adapter.HotelOrderAdapter;
import com.demo.mall.bean.FindAllRestaurantDetailBean;
import com.demo.mall.bean.FindHotelDetailBean;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/8/8 0008 15:56
 */
public class RestaurantMessageActivity extends Activity {
    @Bind(R.id.im_left_resmess)
    ImageView imLeftResmess;
    @Bind(R.id.tv_center_resmess)
    TextView tvCenterResmess;
    @Bind(R.id.tv_left_resmess_equmient)
    TextView tvLeftResmessEqumient;
    @Bind(R.id.tv_left_resmess_introduce)
    TextView tvLeftResmessIntroduce;
    int tt;
    @Bind(R.id.ll_kuandai)
    LinearLayout llKuandai;
    @Bind(R.id.ll_yushi)
    LinearLayout llYushi;
    @Bind(R.id.sheshi)
    LinearLayout sheshi;
    @Bind(R.id.ll_keji)
    LinearLayout llKeji;
    @Bind(R.id.ll_yinpin)
    LinearLayout llYinpin;
    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_avtivity_restaurantmessage);
        ButterKnife.bind(this);

        tt = getIntent().getIntExtra("tt", 1);
        if (tt == 1) {
            tvLeftResmessEqumient.setText("酒店设施");
            tvLeftResmessIntroduce.setText("酒店介绍");
            findHotelDetail();
        } else {
            tvLeftResmessEqumient.setText("饭店设施");
            tvLeftResmessIntroduce.setText("饭店介绍");
            llYushi.setVisibility(View.GONE);
            findAllRestaurantDetail();
        }

    }

    @OnClick(R.id.im_left_resmess)
    public void onClick() {
        finish();
    }

    //饭店详情
    private void findAllRestaurantDetail() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.findAllRestaurantDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(RestaurantMessageActivity.this,R.style.AlertDialogStyle);
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
                            FindAllRestaurantDetailBean findAllRestaurantBean = new Gson().fromJson(responseInfo.result, FindAllRestaurantDetailBean.class);
                            int i = findAllRestaurantBean.getHeader().getStatus();
                            if (i == 0) {
                                tvCenterResmess.setText(findAllRestaurantBean.getData().getRestaurantDetail().getName());
                                if (findAllRestaurantBean.getData().getRestaurantDetail().getIs_blss() == 0) {
                                    sheshi.setVisibility(View.GONE);
                                }
                                if (findAllRestaurantBean.getData().getRestaurantDetail().getIs_food() == 0) {
                                    llYinpin.setVisibility(View.GONE);
                                }
                                if (findAllRestaurantBean.getData().getRestaurantDetail().getIs_media() == 0) {
                                    llKeji.setVisibility(View.GONE);
                                }
                                if (findAllRestaurantBean.getData().getRestaurantDetail().getIs_wifi() == 0) {
                                    llKuandai.setVisibility(View.GONE);
                                }
                                if (findAllRestaurantBean.getData().getRestaurantDetail().getIs_yushi() == 0) {
                                    llYushi.setVisibility(View.GONE);
                                }
                                //启用支持javascript
                                WebSettings settings = webView.getSettings();
                                settings.setJavaScriptEnabled(true);

                                webView.loadUrl(findAllRestaurantBean.getData().getRestaurantDetail().getDetailUrl());

                                webView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });

                            } else {
                                ToastUtil.show(getApplicationContext(), findAllRestaurantBean.getHeader().getMsg());
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

    //酒店详情
    private void findHotelDetail() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", getIntent().getStringExtra("id"));
        params.addQueryStringParameter("startDate", getIntent().getStringExtra("startDate"));
        params.addQueryStringParameter("endDate", getIntent().getStringExtra("endDate"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.findHotelDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(RestaurantMessageActivity.this,R.style.AlertDialogStyle);
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
                            FindHotelDetailBean findHotelDetailBean = new Gson().fromJson(responseInfo.result, FindHotelDetailBean.class);
                            int i = findHotelDetailBean.getHeader().getStatus();
                            if (i == 0) {
                                tvCenterResmess.setText(findHotelDetailBean.getData().getHotelDetail().getName());
                                if (findHotelDetailBean.getData().getHotelDetail().getIs_blss() == 0) {
                                    sheshi.setVisibility(View.GONE);
                                }
                                if (findHotelDetailBean.getData().getHotelDetail().getIs_food() == 0) {
                                    llYinpin.setVisibility(View.GONE);
                                }
                                if (findHotelDetailBean.getData().getHotelDetail().getIs_media() == 0) {
                                    llKeji.setVisibility(View.GONE);
                                }
                                if (findHotelDetailBean.getData().getHotelDetail().getIs_wifi() == 0) {
                                    llKuandai.setVisibility(View.GONE);
                                }
                                if (findHotelDetailBean.getData().getHotelDetail().getIs_yushi() == 0) {
                                    llYinpin.setVisibility(View.GONE);
                                }
                                //启用支持javascript
                                WebSettings settings = webView.getSettings();
                                settings.setJavaScriptEnabled(true);

                                webView.loadUrl(findHotelDetailBean.getData().getHotelDetail().getDetailUrl());

                                webView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });
                            } else {
                                ToastUtil.show(getApplicationContext(), findHotelDetailBean.getHeader().getMsg());
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
