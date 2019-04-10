package com.demo.monitor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.demo.demo.myapplication.R;
import com.demo.monitor.bean.MonitorVideosBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.CustomVideoView;
import com.google.gson.Gson;
import com.hikvision.sdk.RealPlayManagerEx;
import com.hikvision.sdk.consts.SDKConstant;
import com.hikvision.sdk.net.business.OnVMSNetSDKBusiness;
import com.hikvision.sdk.utils.FileUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.protocol.HTTP;

public class HKPlayActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback {
    protected SurfaceView surfaceView;
    protected Button captureView;
    protected Button recordView;
    protected Button soundView;
    private ImageView back;
    /* 是否正在录像     */
    private boolean mIsRecord;
    /*音频是否开启 */
    private boolean mIsAudioOpen;
    /**
     * 播放窗口1
     */
    private int PLAY_WINDOW_ONE = 1;
    private String playUrl = "rtsp://222.180.169.101:554/pag://192.168.128.3:7302:858369ac56754528925d191e448b0688:0:SUB:TCP?streamform=rtp";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hk_play);
        initView();
        //等待surfaceview创建完毕
    }

    public void initVideos(final String url) {
        surfaceView.post(new Runnable() {
            @Override
            public void run() {
                RealPlayManagerEx.getInstance().startRealPlay(PLAY_WINDOW_ONE, url, surfaceView, new OnVMSNetSDKBusiness() {
                    @Override
                    public void onFailure() {
                        super.onFailure();
                        Toast.makeText(HKPlayActivity.this, "播放失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStatusCallback(int status) {
                        super.onStatusCallback(status);
                    }

                    @Override
                    public void onSuccess(Object obj) {
                        super.onSuccess(obj);
                        Toast.makeText(HKPlayActivity.this, "播放成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }

    }

    String name;
    String id;

    private void initView() {
        //初始化view
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        captureView = (Button) findViewById(R.id.capture_view);
        captureView.setOnClickListener(HKPlayActivity.this);
        recordView = (Button) findViewById(R.id.record_view);
        recordView.setOnClickListener(HKPlayActivity.this);
        soundView = (Button) findViewById(R.id.sound_view);
        findViewById(R.id.back).setOnClickListener(this);
        soundView.setOnClickListener(HKPlayActivity.this);
        surfaceView.getHolder().addCallback(this);
        getMonitorVideos();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //页面销毁时停止预览
        boolean stopLiveResult = RealPlayManagerEx.getInstance().stopRealPlay(PLAY_WINDOW_ONE);
        if (stopLiveResult) {
            ToastUtil.show(this, "live_stop_success");
        }
    }

    public void getMonitorVideos() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("cameraUuid", id);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.POST, URL.monitorVideos, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                            MonitorVideosBean monitorBean = new Gson().fromJson(responseInfo.result, MonitorVideosBean.class);
                            int i = monitorBean.getHeader().getStatus();
                            if (i == 0) {
                                initVideos(monitorBean.getData().getUrl());
                            } else {
                                ToastUtil.show(HKPlayActivity.this, monitorBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(HKPlayActivity.this, e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(HKPlayActivity.this, e.getMessage());
                    }
                });
    }

}
