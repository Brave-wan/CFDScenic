package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.MessageCenterBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 消息中心--详情页
 * Created by Administrator on 2016/7/28 0028.
 */
public class  Activity_MessageDetails extends Activity {

    @Bind(R.id.wv_message)
    WebView wvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        ButterKnife.bind(this);
        initWeb();
    }

    private void initWeb() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_MessageDetails.this, SpName.token, ""));
        params.addQueryStringParameter("detailId", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.messageCenterDetail, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                if (jsonObject.getString("data") != null) {
                                    wvMessage.loadUrl(jsonObject.getString("data"));
                                    wvMessage.setWebViewClient(new WebViewClient(){
                                        @Override
                                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                            // TODO Auto-generated method stub
                                            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                                            view.loadUrl(url);
                                            return true;
                                        }
                                    });

                                }
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_MessageDetails.this);
                            } else {
                                ToastUtil.show(Activity_MessageDetails.this, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_MessageDetails.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_MessageDetails.this, e.getMessage());
                    }
                });

    }
}
