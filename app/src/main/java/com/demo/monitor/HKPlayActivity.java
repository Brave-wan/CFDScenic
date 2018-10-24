package com.demo.monitor;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.demo.demo.myapplication.R;
import com.demo.monitor.bean.MonitorVideosBean;
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

public class HKPlayActivity extends Activity implements View.OnClickListener {
    VideoView videoView;
    ImageView hk_play_back;
    TextView tx_play_title;

    String name;
    String connect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hk_play);
        initView();
        hk_play_back.setOnClickListener(this);
    }

    public void initVideos(String url) {
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(HKPlayActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        name = getIntent().getStringExtra("name");
        connect = getIntent().getStringExtra("conn");
        hk_play_back = (ImageView) findViewById(R.id.hk_play_back);
        videoView = (VideoView) findViewById(R.id.big_screen);
        tx_play_title = (TextView) findViewById(R.id.tx_play_title);
        tx_play_title.setText(name);
        getMonitorVideos();
    }

    public void getMonitorVideos() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("cameraUuid", "c1aca375df204c109e040c5ec824020d");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hk_play_back:
                finish();
                videoView.destroyDrawingCache();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.destroyDrawingCache();
    }
}
