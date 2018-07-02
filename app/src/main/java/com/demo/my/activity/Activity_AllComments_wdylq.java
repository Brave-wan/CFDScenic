package com.demo.my.activity;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.demo.adapter.AllCommentsYlqAdapter;
import com.demo.demo.myapplication.R;
import com.demo.view.NoScrollViewListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class Activity_AllComments_wdylq extends Activity {

    @Bind(R.id.iv_imageT)
    ImageView ivImageT;
    @Bind(R.id.fl_tujie)
    FrameLayout flTujie;
    @Bind(R.id.tv_titleT)
    TextView tvTitleT;
    @Bind(R.id.tv_contentT)
    TextView tvContentT;
    @Bind(R.id.tv_MoreT)
    TextView tvMoreT;
    @Bind(R.id.ll_tupian)
    LinearLayout llTupian;
    @Bind(R.id.mVideoView)
    VideoView mVideoView;
    @Bind(R.id.tv_minute)
    TextView tvMinute;
    @Bind(R.id.fl_shipin)
    FrameLayout flShipin;
    @Bind(R.id.tv_titleS)
    TextView tvTitleS;
    @Bind(R.id.tv_contentS)
    TextView tvContentS;
    @Bind(R.id.tv_MoreS)
    TextView tvMoreS;
    @Bind(R.id.ll_shipin)
    LinearLayout llShipin;
    @Bind(R.id.ll_zan)
    LinearLayout llZan;
    @Bind(R.id.ll_pinglun)
    LinearLayout llPinglun;
    @Bind(R.id.ll_fenxiang)
    LinearLayout llFenxiang;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.tv_all_ylq)
    TextView tvAllYlq;
    @Bind(R.id.lv_all_ylq)
    NoScrollViewListView lvAllYlq;
    @Bind(R.id.sv_all)
    ScrollView svAll;
    private PopupWindow mPopWindow;

    private final int VIDEO_CONTENT_DESC_MAX_LINE = 7;// 默认展示最大行数3行
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;//默认收起状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comments_wdylq);
        ButterKnife.bind(this);

        tvContentT.setText(R.string.txt_info);
        tvContentS.setText(R.string.txt_info);

//        lvAllYlq.setAdapter(new AllCommentsYlqAdapter(getApplicationContext()));

        svAll.smoothScrollTo(0, 0);
    }

    @OnClick({R.id.ll_pinglun, R.id.ll_fenxiang, R.id.tv_MoreS, R.id.tv_MoreT})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fenxiang:
                show_popupWindow();
                break;
            case R.id.tv_MoreS:
                if (mState == SPREAD_STATE) {
                    tvContentS.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    tvContentS.requestLayout();
                    mState = SHRINK_UP_STATE;
                    tvMoreS.setText("...展开更多");
                } else if (mState == SHRINK_UP_STATE) {
                    tvContentS.setMaxLines(Integer.MAX_VALUE);
                    tvContentS.requestLayout();
                    mState = SPREAD_STATE;
                    tvMoreS.setText("收起");
                }
                break;
            case R.id.tv_MoreT:
                if (mState == SPREAD_STATE) {
                    tvContentT.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    tvContentT.requestLayout();
                    mState = SHRINK_UP_STATE;
                    tvMoreT.setText("...展开更多");
                } else if (mState == SHRINK_UP_STATE) {
                    tvContentT.setMaxLines(Integer.MAX_VALUE);
                    tvContentT.requestLayout();
                    mState = SPREAD_STATE;
                    tvMoreT.setText("收起");
                }
                break;
        }
    }

    //显示分享popupWindow
    private void show_popupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(Activity_AllComments_wdylq.this).inflate(R.layout.popup_ylq_share, null);
        mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        LinearLayout ll_weibo = (LinearLayout) contentView.findViewById(R.id.ll_share_weibo);
        LinearLayout ll_kongjian = (LinearLayout) contentView.findViewById(R.id.ll_share_kongjian);
        LinearLayout ll_pengyouquan = (LinearLayout) contentView.findViewById(R.id.ll_share_pengyouquan);
        LinearLayout ll_popup = (LinearLayout) contentView.findViewById(R.id.ll_popup);
        ll_popup.setOnClickListener(new Share_Click());
        ll_weibo.setOnClickListener(new Share_Click());
        ll_kongjian.setOnClickListener(new Share_Click());
        ll_pengyouquan.setOnClickListener(new Share_Click());


        //显示PopupWindow
        View rootview = LayoutInflater.from(Activity_AllComments_wdylq.this).inflate(R.layout.popup_my_share, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    //分享的单击事件
    private class Share_Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_share_kongjian:
                    Toast.makeText(Activity_AllComments_wdylq.this, "kongjian", Toast.LENGTH_LONG).show();
                    break;
                case R.id.ll_share_weibo:
                    Toast.makeText(Activity_AllComments_wdylq.this, "weibo", Toast.LENGTH_LONG).show();
                    break;
                case R.id.ll_share_pengyouquan:
                    Toast.makeText(Activity_AllComments_wdylq.this, "pengyouquan", Toast.LENGTH_LONG).show();
                    break;
                case R.id.ll_popup:
                    mPopWindow.dismiss();
                    break;
            }
        }
    }
}
