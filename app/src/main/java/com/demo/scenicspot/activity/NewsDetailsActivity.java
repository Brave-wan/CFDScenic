package com.demo.scenicspot.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.scenicspot.bean.PressDetailsBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.GradationScrollView;
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
import butterknife.OnClick;

/**
 * 新闻须知详情
 * Created by Administrator on 2016/8/12 0012.
 */
public class NewsDetailsActivity extends Activity implements GradationScrollView.ScrollViewListener {

    @Bind(R.id.iv_sciencemore_bigpic)
    ImageView ivSciencemoreBigpic;
    @Bind(R.id.iv_aty_sciencemore_back)
    ImageView ivAtySciencemoreBack;
    @Bind(R.id.tv_Title)
    TextView tvTitle;
    @Bind(R.id.tv_creator)
    TextView tvCreator;
    @Bind(R.id.tv_create_time)
    TextView tvCreateTime;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_goodTitle)
    TextView tvGoodTitle;
    @Bind(R.id.tv_bottomline)
    TextView tvBottomline;
    @Bind(R.id.ll_title)
    RelativeLayout llTitle;
    @Bind(R.id.sv_science)
    GradationScrollView svScience;

    String id = "";
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        pressDetails();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        String url=getIntent().getStringExtra("url");
        webView.loadUrl(url);
        //上滑渐变
        initListeners();
        ivBack.setAlpha(0);
    }

    @OnClick(R.id.iv_aty_sciencemore_back)
    public void onClick() {
        finish();
    }

    private void pressDetails() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", id);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.pressDetails, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            PressDetailsBean pressDetailsBean = new Gson().fromJson(responseInfo.result, PressDetailsBean.class);
                            int i = pressDetailsBean.getHeader().getStatus();
                            if (i == 0) {
                                ImageLoader.getInstance().displayImage(pressDetailsBean.getData().getHeader_img(), ivSciencemoreBigpic);
                                tvTitle.setText(pressDetailsBean.getData().getName());
                                tvCreator.setText(pressDetailsBean.getData().getCreator());
                                String time = pressDetailsBean.getData().getCreate_time();
                                tvCreateTime.setText(time.substring(0, 10));
                                //启用支持javascript


                                webView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });
                            } else {
                                ToastUtil.show(getApplicationContext(), pressDetailsBean.getHeader().getMsg());
                            }

                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "数据解析出错");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }


    /**
     * 滑动监听
     */
    public void onScrollChanged(GradationScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            llTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            tvBottomline.setBackgroundColor(Color.argb((int) 0, 205, 205, 205));
            tvGoodTitle.setTextColor(Color.argb((int) 0, 51, 51, 51));
            ivBack.setAlpha(0);
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            tvGoodTitle.setTextColor(Color.argb((int) alpha, 51, 51, 51));
            llTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            tvBottomline.setBackgroundColor(Color.argb((int) alpha, 205, 205, 205));
            ivBack.setAlpha((int) alpha);
        } else {    //滑动到banner下面设置普通颜色
            llTitle.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
        }
    }


    private void initListeners() {

        ViewTreeObserver vto = ivSciencemoreBigpic.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = ivSciencemoreBigpic.getHeight();

                svScience.setScrollViewListener(NewsDetailsActivity.this);
            }
        });
    }
}
