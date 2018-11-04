package com.demo.monitor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.protocol.HTTP;

public class HKPlayActivity extends Activity implements View.OnClickListener {
    CustomVideoView videoView;
    ImageView hk_play_back;
    TextView tx_play_title;

    String name;
    String id;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hk_play);
        initView();
        hk_play_back.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在解析视频,请稍后");
        dialog.show();
    }

    public void initVideos(String url) {
//        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(HKPlayActivity.this, "视频解析失败，请重试!", Toast.LENGTH_SHORT).show();
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            }
        });

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                } else {
                    dialog.dismiss();
                }
                return true;
            }
        });
    }

    private void initView() {
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        hk_play_back = (ImageView) findViewById(R.id.hk_play_back);
        videoView = (CustomVideoView) findViewById(R.id.big_screen);
        tx_play_title = (TextView) findViewById(R.id.tx_play_title);
        tx_play_title.setText(name);
        getMonitorVideos();
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
                                initVideos(monitorBean.getData());
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
