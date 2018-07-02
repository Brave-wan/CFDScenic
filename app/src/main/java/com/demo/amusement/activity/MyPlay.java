package com.demo.amusement.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.demo.demo.myapplication.R;
import com.demo.view.MyVideoView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：sonnerly on 2016/9/20 0020 17:57
 */
public class MyPlay extends Activity {
    @Bind(R.id.myVideoView)
    MyVideoView myVideoView;
    //private String path = "http://yajiankang.zhongjiankeda.com:8080/yajiankang/resource/wdy8.mp4";
    String path = "";

    Uri mUri = null;
    MediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
//		Vitamio.isInitialized(getApplicationContext());
        setContentView(R.layout.myplayer);
        ButterKnife.bind(this);
        path = this.getIntent().getStringExtra("uri");
        mUri = Uri.parse(path);
        initVideoView();


    }

    private void initVideoView() {

        //显示加载框
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.mProgressBar);
        mProgress.setVisibility(View.VISIBLE);

//        mVideo = (VideoView) findViewById(R.id.myVideoView);
        //Create media controller，组件可以控制视频的播放，暂停，回复，seek等操作，不需要你实现
        mMediaController = new MediaController(this);
        myVideoView.setMediaController(mMediaController);
        // Play Video
        myVideoView.setVideoURI(mUri);
        myVideoView.start();


        //视频缓冲时调用
        myVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {

            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    mProgress.setVisibility(View.VISIBLE);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    //此接口每次回调完START就回调END,若不加上判断就会出现缓冲图标一闪一闪的卡顿现象
                    if (mp.isPlaying()) {
                        mProgress.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });


        //开始播放时调用
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mProgress.setVisibility(View.GONE);//缓冲完成就隐藏
            }
        });

        //播放停止时
        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                finish();

            }
        });


    }


}
