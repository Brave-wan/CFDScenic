package com.demo.scenicspot.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.demo.demo.myapplication.R;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.json.JSONObject;
import org.jsoup.select.Elements;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：sonnerly on 2017/1/9 0009 13:39
 */
public class WatchBridActivity extends Activity {

    @Bind(R.id.webView_brid)
    WebView webViewBrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brid);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.threeInclude, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                webViewBrid.loadUrl(jsonObject.getString("data"));
                                //支持javascript
                                webViewBrid.getSettings().setJavaScriptEnabled(true);
                                // 设置可以支持缩放
                                webViewBrid.getSettings().setSupportZoom(true);
                                // 设置出现缩放工具
                                webViewBrid.getSettings().setBuiltInZoomControls(true);
                                //扩大比例的缩放
                                webViewBrid.getSettings().setUseWideViewPort(true);
                                //自适应屏幕
                                webViewBrid.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                                webViewBrid.getSettings().setLoadWithOverviewMode(true);
                                webViewBrid.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });


                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
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
