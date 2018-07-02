package com.demo.amusement.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.amusement.bean.ActivityMoreBean;
import com.demo.amusement.bean.GoBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;
import com.demo.scenicspot.activity.SureOrderActivity;
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
public class Activity_ActivityDetailsJb extends Activity {

    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_ScenicSpot)
    TextView tvScenicSpot;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_present)
    TextView tvPresent;
    @Bind(R.id.tv_primary)
    TextView tvPrimary;
    @Bind(R.id.tv_Purchase)
    TextView tvPurchase;
    @Bind(R.id.tv_End)
    TextView tvEnd;
    @Bind(R.id.tv_Ticket)
    TextView tvTicket;//在线购票
    @Bind(R.id.iv_Prompt)
    ImageView ivPrompt;//是否售完
    @Bind(R.id.webView_circle)
    WebView webViewCircle;//加载web
    ActivityMoreBean activityMoreBean = new ActivityMoreBean();
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_details_jb);
        ButterKnife.bind(this);
        initGoMore();

                //tvTicket.setVisibility(View.VISIBLE);
                tvTicket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                ToastUtil.show(getApplicationContext(), "在线购票");
//                intent.putExtra("orderDescribe",activityMoreBean.getData().getVisitors_describe());
                        if (activityMoreBean.getData().getSoldOut() == 1) {
                            ToastUtil.show(getApplicationContext(), "票已售完");
                        }else{
                            if (activityMoreBean.getData().getDateEnd() == 1) {
                                ToastUtil.show(getApplicationContext(), "活动已结束");
                            }else {
                        intent.setClass(Activity_ActivityDetailsJb.this, SureOrderActivity.class);
                        intent.putExtra("visitorsId", activityMoreBean.getData().getId() + "");
                        intent.putExtra("userId", SpUtil.getString(getApplication(), SpName.userId, ""));
                        intent.putExtra("price", activityMoreBean.getData().getNew_price());
                        intent.putExtra("name", activityMoreBean.getData().getName());
                        intent.putExtra("deliver_fee", activityMoreBean.getData().getDeliver_fee() + "");
                        startActivity(intent);
                            }
                    }
                    }
                });




    }

    private void initGoMore() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(this, SpName.token, ""));
        params.addQueryStringParameter("id", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.circleScenicMore, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ActivityDetailsJb.this,R.style.AlertDialogStyle);
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
                                tvTitle.setText(activityMoreBean.getData().getName());
                                tvScenicSpot.setText("活动景区：" + activityMoreBean.getData().getName());
                                String[] b = activityMoreBean.getData().getStart_valid().split(" ");
                                String[] e = activityMoreBean.getData().getEnd_valid().split(" ");
                                tvTime.setText("活动时间：" + b[0] + "--" + e[0]);
                                tvPresent.setText("￥" + activityMoreBean.getData().getNew_price() + "");
                                tvPrimary.setText("原价：￥" + activityMoreBean.getData().getPrice() + "");
                                tvPrimary.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tvPurchase.setText(activityMoreBean.getData().getNumber() + "人");
                                tvEnd.setText("(活动截止人数：" + activityMoreBean.getData().getPersonNumber() + "人)");
                                if(activityMoreBean.getData().getNew_price()==0){
                                    tvTicket.setText("参加");
                                }else{
                                    tvTicket.setText("在线购票");
                                }
                                if (activityMoreBean.getData().getSoldOut() == 1) {
//                                    tvTicket.setVisibility(View.GONE);
                                    ivPrompt.setVisibility(View.VISIBLE);
                                    ivPrompt.setImageResource(R.mipmap.ylq_yishoukong);
                                }else{
                                    if (activityMoreBean.getData().getDateEnd() == 1) {
//                                        tvTicket.setVisibility(View.GONE);
                                        ivPrompt.setVisibility(View.VISIBLE);
                                        ivPrompt.setImageResource(R.mipmap.ylq_yijieshu);
                                    }else {
                                        //tvTicket.setVisibility(View.VISIBLE);
                                        ivPrompt.setVisibility(View.GONE);
                                    }
                                }

                                WebSettings settings = webViewCircle.getSettings();
                                settings.setJavaScriptEnabled(true);
                        /*//自适应屏幕
//                                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//                                settings.setLoadWithOverviewMode(true);
//                                settings.setBuiltInZoomControls(true);
//                                settings.setSupportZoom(true);*/
                                webViewCircle.loadUrl(activityMoreBean.getData().getDetailUrl());
                                webViewCircle.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });
                            } else if (activityMoreBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ActivityDetailsJb.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), activityMoreBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_ActivityDetailsJb.this, "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_ActivityDetailsJb.this, "连接网络失败");
                    }
                });
    }
}
