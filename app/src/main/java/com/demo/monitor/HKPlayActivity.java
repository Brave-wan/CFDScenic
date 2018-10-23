package com.demo.monitor;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.demo.demo.myapplication.R;

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


        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(connect));
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(HKPlayActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
            }
        });
        hk_play_back.setOnClickListener(this);

    }

    private void initView() {
        name = getIntent().getStringExtra("name");
        connect = getIntent().getStringExtra("conn");
        hk_play_back = (ImageView) findViewById(R.id.hk_play_back);
        videoView = (VideoView) findViewById(R.id.big_screen);
        tx_play_title = (TextView) findViewById(R.id.tx_play_title);
        tx_play_title.setText(name);
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
}
