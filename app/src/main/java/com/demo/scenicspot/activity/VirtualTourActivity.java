package com.demo.scenicspot.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.demo.demo.myapplication.R;
import com.demo.scenicspot.bean.GetPlanningOrIntroduceBean;
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

/**
 * 作者：sonnerly on 2016/11/29 0029 09:45
 * 虚拟游
 */
public class VirtualTourActivity extends Activity {
    @Bind(R.id.webView_tour)
    WebView webViewTour;
    WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualtour);
        ButterKnife.bind(this);
        webSettings = webViewTour.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        //自动缩放
        webSettings.setSupportZoom(true);
        //设置webview的插件转状态
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //允许与js交互
        //设置默认的字符编码
        webSettings.setDefaultTextEncodingName("utf-8");
        webViewTour.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        getTour();
    }

    private void getTour() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("type", 2 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getPlanningOrIntroduce, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(VirtualTourActivity.this, R.style.AlertDialogStyle);

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
                            GetPlanningOrIntroduceBean getPlanningOrIntroduceBean = new Gson().fromJson(responseInfo.result, GetPlanningOrIntroduceBean.class);
                            int i = getPlanningOrIntroduceBean.getHeader().getStatus();
                            if (i == 0) {
                                //启用支持javascript
                                WebSettings settings = webViewTour.getSettings();
                                settings.setJavaScriptEnabled(true);

//                                webViewTour.loadUrl(getPlanningOrIntroduceBean.getData());
                                webViewTour.loadUrl("https://720yun.com/t/793jeOhvza8");
                                webViewTour.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });

                            } else {
                                ToastUtil.show(getApplicationContext(), getPlanningOrIntroduceBean.getHeader().getMsg());
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
