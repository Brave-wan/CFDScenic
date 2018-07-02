package com.demo.amusement.activity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.amusement.bean.ActivityMoreBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;
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
 * 游乐圈--活动推荐--活动详情
 * Created by Administrator on 2016/8/2 0002.
 */
public class Activity_ActivityDetailsTj extends Activity {

    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_ActivityTitle)
    TextView tvActivityTitle;
    @Bind(R.id.tv_ScenicSpot)
    TextView tvScenicSpot;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.webview_recommend)
    WebView webviewRecommend;
    ActivityMoreBean activityMoreBean = new ActivityMoreBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_details_tj);
        ButterKnife.bind(this);
        initRecommend();

    }

    private void initRecommend() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(this, SpName.token, ""));
        params.addQueryStringParameter("id", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.circleScenicMore, params,
                new RequestCallBack<String>() {

                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ActivityDetailsTj.this,R.style.AlertDialogStyle);
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        String s = responseInfo.result;
                        try {
                            activityMoreBean = new Gson().fromJson(responseInfo.result, ActivityMoreBean.class);
                            if (activityMoreBean.getHeader().getStatus() == 0) {
                                ImageLoader.getInstance().displayImage(activityMoreBean.getData().getHead_img(), ivImage);
//                                ivImage.setImageResource(R.mipmap.ceshi_fengjing);
                                ivImage.setImageResource(R.mipmap.ceshi_fengjing);
                                tvActivityTitle.setText(activityMoreBean.getData().getName());
                                tvScenicSpot.setText("活动景点："+activityMoreBean.getData().getAddress());
                                String[] b = activityMoreBean.getData().getStart_valid().split(" ");
                                String[] e = activityMoreBean.getData().getEnd_valid().split(" ");
                                tvTime.setText("活动时间：" + b[0] + "--" + e[0]);
                                WebSettings settings = webviewRecommend.getSettings();
                                settings.setJavaScriptEnabled(true);
                        /*//自适应屏幕
//                                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//                                settings.setLoadWithOverviewMode(true);
//                                settings.setBuiltInZoomControls(true);
//                                settings.setSupportZoom(true);*/
                                webviewRecommend.loadUrl(activityMoreBean.getData().getDetailUrl());
                                webviewRecommend.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });
                            } else if (activityMoreBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ActivityDetailsTj.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), activityMoreBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_ActivityDetailsTj.this, "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_ActivityDetailsTj.this, "连接网络失败");
                    }
                });
    }
}

