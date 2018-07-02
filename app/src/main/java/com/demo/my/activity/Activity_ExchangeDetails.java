package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.demo.my.bean.IntegralGoodsDetailBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
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
 * 兑换商品  商品信息详情
 * Created by Administrator on 2016/8/3 0003.
 */
public class Activity_ExchangeDetails extends Activity {


    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.tv_exchange)
    TextView tvExchange;
    @Bind(R.id.ll_Image)
    LinearLayout llImage;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;

    IntegralGoodsDetailBean integralGoodsDetailBean;

    String id;
    int type;
    String integral = "";
    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_details);
        ButterKnife.bind(this);

        init();
        integralGoodsDetail();


    }

    public void init() {
        id = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", -1);
        integral = getIntent().getStringExtra("integral");
    }

    @OnClick({R.id.iv_return, R.id.tv_exchange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_exchange:
                //判断是纪念品 还是 门票
                int integralInt = Integer.parseInt(integral);
                if (integralInt < integralGoodsDetailBean.getData().getIntegral()) {
                    ToastUtil.show(getApplicationContext(), "积分不足");
                    return;
                }
                if (type == 4) {
                    //纪念品
                    Intent intent = new Intent(getApplicationContext(), Activity_ExchangeOkKeepsake.class);
                    intent.putExtra("visitorsId",integralGoodsDetailBean.getData().getId()+"");
                    intent.putExtra("id", integralGoodsDetailBean.getData().getId()+"");
                    intent.putExtra("image",integralGoodsDetailBean.getData().getHead_img());
                    intent.putExtra("name",integralGoodsDetailBean.getData().getName());
                    intent.putExtra("integral",integralGoodsDetailBean.getData().getIntegral()+"");
                    intent.putExtra("start",integralGoodsDetailBean.getData().getStart_valid());
                    intent.putExtra("end",integralGoodsDetailBean.getData().getEnd_valid());
                    intent.putExtra("deliver_fee",integralGoodsDetailBean.getData().getDeliver_fee()+"");
                    startActivity(intent);
                } else if (type == 5){
                    //门票
                    Intent intent = new Intent(getApplicationContext(), Activity_ExchangeOkTicket.class);
                    intent.putExtra("visitorsId",integralGoodsDetailBean.getData().getId()+"");
                    intent.putExtra("id", integralGoodsDetailBean.getData().getId()+"");
                    intent.putExtra("image",integralGoodsDetailBean.getData().getHead_img());
                    intent.putExtra("name",integralGoodsDetailBean.getData().getName());
                    intent.putExtra("integral",integralGoodsDetailBean.getData().getIntegral()+"");
                    intent.putExtra("start",integralGoodsDetailBean.getData().getStart_valid());
                    intent.putExtra("end",integralGoodsDetailBean.getData().getEnd_valid());
                    intent.putExtra("deliver_fee",integralGoodsDetailBean.getData().getDeliver_fee()+"");
                    startActivity(intent);
                }
                break;
        }
    }

    private void integralGoodsDetail() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("id", id + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.integralGoodsDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ExchangeDetails.this,R.style.AlertDialogStyle);
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
                            integralGoodsDetailBean = new Gson().fromJson(responseInfo.result, IntegralGoodsDetailBean.class);
                            int i = integralGoodsDetailBean.getHeader().getStatus();
                            if (i == 0) {
                                BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
                                bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);//加载失败
                                bitmapUtils.configDefaultLoadingImage(R.mipmap.zhanwei);//加载过程中
                                bitmapUtils.display(llImage, integralGoodsDetailBean.getData().getHead_img());
                                tvTitle.setText(integralGoodsDetailBean.getData().getName());
                                tvIntegral.setText(integralGoodsDetailBean.getData().getIntegral() + "积分");

                                //启用支持javascript
                                WebSettings settings = webView.getSettings();
                                settings.setJavaScriptEnabled(true);

                                webView.loadUrl(integralGoodsDetailBean.getData().getHtml_url());

                                webView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });
                            } else {
                                ToastUtil.show(getApplicationContext(), integralGoodsDetailBean.getHeader().getMsg());
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
