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
import android.widget.ImageView;

import com.demo.demo.myapplication.R;
import com.demo.scenicspot.bean.ScenicSpotParticularsBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
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

/**
 * 景点详情
 * 作者： on 2016/8/1 0001 14:11
 */
public class MoreFragment extends Fragment {
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.webView)
    WebView webView;
//    @Bind(R.id.iv_tail)
//    ImageView ivTail;

    String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, null);
        ButterKnife.bind(this, view);


        id = getArguments().getString("id");

        scenicSpotParticulars(id);
        return view;
    }


    private void scenicSpotParticulars(String ids) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", ids + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.scenicSpotParticulars, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                        ScenicSpotParticularsBean bean = new Gson().fromJson(responseInfo.result, ScenicSpotParticularsBean.class);
                        int i = bean.getHeader().getStatus();
                        if (i == 0) {
                            //启用支持javascript
                            WebSettings settings = webView.getSettings();
                            settings.setJavaScriptEnabled(true);
                            webView.loadUrl(bean.getData().getDetailHtml());
                            webView.setWebViewClient(new WebViewClient() {
                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return true;
                                }
                            });
                        } else {
                            ToastUtil.show(getActivity(), bean.getHeader().getMsg());
                        }
                        } catch (Exception e) {
                            ToastUtil.show(getContext(), "数据解析失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getActivity(), "连接网络失败");
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
