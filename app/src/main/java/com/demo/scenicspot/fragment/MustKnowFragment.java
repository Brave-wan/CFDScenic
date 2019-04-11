package com.demo.scenicspot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者： on 2016/8/1 0001 14:14
 */
public class MustKnowFragment extends Fragment {
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.ll_root)
    LinearLayout ll_root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, null);
        ButterKnife.bind(this, view);

        getVisitorsNotice();
        return view;
    }

    private void getVisitorsNotice() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("notice_id",getArguments().getString("id"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.getVisitorsNotice, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if(i==0){
                                //启用支持javascript
                                ll_root.removeAllViews();
                                String url=jsonObject.getString("data");
                                WebView webView=new WebView(getActivity());
                                WebSettings settings = webView.getSettings();
                                settings.setJavaScriptEnabled(true);
                                webView.loadUrl(url);
                                ll_root.addView(webView);
                            }else if( i== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getActivity());
                            }else {
                                ToastUtil.show(getContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getContext(), "数据解析失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), "连接网络失败");
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
