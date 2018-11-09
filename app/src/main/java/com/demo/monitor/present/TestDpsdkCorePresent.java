package com.demo.monitor.present;

import android.content.Context;
import android.util.Log;

import com.demo.monitor.MonitorBean;
import com.demo.monitor.bean.VideoListBean;
import com.demo.monitor.view.ITestDpsdkCoreView;
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

public class TestDpsdkCorePresent {

    private Context mContext;
    private ITestDpsdkCoreView view;

    public TestDpsdkCorePresent(Context mContext, ITestDpsdkCoreView view) {
        this.mContext = mContext;
        this.view = view;
    }

    public void getVideoList() {
        RequestParams params = new RequestParams();
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.POST, URL.videoList, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                            VideoListBean monitorBean = new Gson().fromJson(responseInfo.result, VideoListBean.class);
                            int i = monitorBean.getHeader().getStatus();
                            if (i == 0 && view != null) {
                                view.OnMonitorBean(monitorBean);
                            } else {
                                ToastUtil.show(mContext, monitorBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext, e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, e.getMessage());
                    }
                });
    }
}
