package com.demo.scenicspot.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;
import com.demo.scenicspot.bean.GetPlanningOrIntroduceBean;
import com.demo.demo.myapplication.R;
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
 * 景区--湿地简介
 * Created by Administrator on 2016/8/12 0012.
 */
public class BriefIntroductionActivity extends Activity {

    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brief_introduction);
        ButterKnife.bind(this);
        getPlanningOrIntroduce();
    }

    private void getPlanningOrIntroduce(){
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("type",1+"");//1  2
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getPlanningOrIntroduce, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(BriefIntroductionActivity.this,R.style.AlertDialogStyle);
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
                            GetPlanningOrIntroduceBean getPlanningOrIntroduceBean=new Gson().fromJson(responseInfo.result,GetPlanningOrIntroduceBean.class);
                            int i=getPlanningOrIntroduceBean.getHeader().getStatus();
                            if (i==0){
                                //启用支持javascript
                                WebSettings settings = webView.getSettings();
                                settings.setJavaScriptEnabled(true);

                                webView.loadUrl(getPlanningOrIntroduceBean.getData());
                                webView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });

                            }else {
                                ToastUtil.show(getApplicationContext(),getPlanningOrIntroduceBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(),"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }
}
