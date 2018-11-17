package com.demo.my.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.demo.adapter.AllCommentsYlqAdapter;
import com.demo.amusement.bean.CommentBean;
import com.demo.amusement.bean.DatadataBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.NullBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.NoScrollViewListView;
import com.demo.view.RoundImageView;
import com.demo.view.VideoUtils;
import com.demo.view.myListView.XListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.protocol.HTTP;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 全部评论--游乐圈
 * Created by Administrator on 2016/8/2 0002.
 */
public class Activity_AllComments_ylq extends Activity {


    @Bind(R.id.view_touxiangT_comment)
    RoundImageView viewTouxiangTComment;
    @Bind(R.id.tv_usernameT_comment)
    TextView tvUsernameTComment;
    @Bind(R.id.tv_dateT_comment)
    TextView tvDateTComment;
    @Bind(R.id.iv_imageT_comment)
    ImageView ivImageTComment;
    @Bind(R.id.iv_tuji_comment)
    TextView ivTujiComment;
    @Bind(R.id.fl_tujie_comment)
    FrameLayout flTujieComment;
    @Bind(R.id.mVideoView_comment)
    VideoView mVideoViewComment;
    @Bind(R.id.iv_back_comment)
    ImageView ivBackComment;
    @Bind(R.id.iv_paus_comment)
    ImageView ivPausComment;
    @Bind(R.id.tv_minute_comment)
    TextView tvMinuteComment;
    @Bind(R.id.fl_shipin_comment)
    FrameLayout flShipinComment;
    @Bind(R.id.tv_titleT_comment)
    TextView tvTitleTComment;
    @Bind(R.id.tv_contentT_comment)
    TextView tvContentTComment;
    @Bind(R.id.tv_MoreT_comment)
    TextView tvMoreTComment;
    @Bind(R.id.comment_location)
    TextView commentLocation;
    @Bind(R.id.iv_zan)
    ImageView ivZan;
    @Bind(R.id.tv_zan)
    TextView tvZan;
    @Bind(R.id.ll_zan)
    LinearLayout llZan;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.ll_pinglun)
    LinearLayout llPinglun;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.ll_fenxiang)
    LinearLayout llFenxiang;
    @Bind(R.id.tv_all_ylq)
    TextView tvAllYlq;
    @Bind(R.id.lv_all_ylq)
    NoScrollViewListView lvAllYlq;
    @Bind(R.id.sv_all)
    ScrollView svAll;
    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.tv_release_comment)
    TextView tvReleaseComment;
    private PopupWindow mPopWindow;
    private final int VIDEO_CONTENT_DESC_MAX_LINE = 7;// 默认展示最大行数3行
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;//默认收起状态
    boolean aBoolean = false;//判断是否回复他人   true是，false否
    CommentBean commentBean = new CommentBean();
    int tyty = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comments_ylq);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ImageLoader.getInstance().displayImage(getIntent().getStringExtra("head_img"), viewTouxiangTComment);
        tvUsernameTComment.setText(getIntent().getStringExtra("nickname"));
        tvDateTComment.setText(getIntent().getStringExtra("date"));
        tvTitleTComment.setText(getIntent().getStringExtra("title"));
        tvContentTComment.setText(getIntent().getStringExtra("content"));
        commentLocation.setText(getIntent().getStringExtra("location"));
        tvZan.setText(getIntent().getStringExtra("zan") + "");
        //tvComment.setText(getIntent().getStringExtra("cocount") + "");

        if (getIntent().getStringExtra("content").length() >= 168) {
            tvMoreTComment.setVisibility(View.VISIBLE);
        } else {
            tvMoreTComment.setVisibility(View.GONE);
        }
        if (getIntent().getIntExtra("favor", 0) == 0) {
            ivZan.setImageResource(R.mipmap.dianzan_false);
        } else if (getIntent().getIntExtra("favor", 0) == 1) {
            ivZan.setImageResource(R.mipmap.dianzan_true);

        }
        tvShare.setText(getIntent().getStringExtra("sharecount") + "");
        if (getIntent().getStringExtra("type").equals("1")) {//视频
            flTujieComment.setVisibility(View.GONE);
            flShipinComment.setVisibility(View.VISIBLE);
            //mVideoViewComment.setVideoPath(getIntent().getStringExtra("video"));//获取视频
            ImageLoader.getInstance().displayImage(getIntent().getStringExtra("videoimg"), ivBackComment);//获取第一张图片
            // setvoidotime(tvMinuteComment,getIntent().getStringExtra("video"));//获取视频时长
            tvMinuteComment.setText(getIntent().getStringExtra("time"));
        } else if (getIntent().getStringExtra("type").equals("2")) {
            flTujieComment.setVisibility(View.VISIBLE);
            flShipinComment.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(getIntent().getStringExtra("img"), ivImageTComment);
        }
        initComment();
//
        //点击item，回复他人信息
        lvAllYlq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aBoolean = true;
                etComment.setText("回复" + commentBean.getData().get(position).getNick_name() + ":");
                etComment.setSelection(etComment.length());
                etComment.setFocusable(true);
                etComment.setFocusableInTouchMode(true);
                etComment.requestFocus();
                tyty = 1;

                InputMethodManager inputManager =
                        (InputMethodManager) etComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etComment, 0);
            }
        });

        svAll.smoothScrollTo(0, 0);
    }

    private void initComment() {
//        params.addQueryStringParameter("page", 1 + "");
//        params.addQueryStringParameter("rows", 20 + "");
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_AllComments_ylq.this, SpName.token, ""));
        params.addQueryStringParameter("id", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.circleComment, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String s = responseInfo.result;
                        try {
                            commentBean = new Gson().fromJson(responseInfo.result, CommentBean.class);
                            if (commentBean.getHeader().getStatus() == 0) {

                                tvComment.setText(commentBean.getData().size() + "");
                                tvAllYlq.setText("全部评论（" + commentBean.getData().size() + "）");
                                lvAllYlq.setAdapter(new AllCommentsYlqAdapter(Activity_AllComments_ylq.this, commentBean.getData()));

                            } else if (commentBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_AllComments_ylq.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), commentBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_AllComments_ylq.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_AllComments_ylq.this, s);
                    }
                });

    }

    private void setvoidotime(final TextView textView, final String urlint) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String time = VideoUtils.getsplength(urlint);
                textView.post(new Runnable() {
                    public void run() {
                        textView.setText(time);
                    }
                });

            }
        }).start();

    }

    @OnClick({R.id.ll_fenxiang, R.id.tv_MoreT_comment, R.id.tv_release_comment, R.id.ll_zan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fenxiang:
                show_popupWindow();
                break;
            case R.id.tv_MoreT_comment:
                if (mState == SPREAD_STATE) {
                    tvContentTComment.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    tvContentTComment.requestLayout();
                    mState = SHRINK_UP_STATE;
                    tvMoreTComment.setText("...展开更多");
                } else if (mState == SHRINK_UP_STATE) {
                    tvContentTComment.setMaxLines(Integer.MAX_VALUE);
                    tvContentTComment.requestLayout();
                    mState = SPREAD_STATE;
                    tvMoreTComment.setText("收起");
                }
                break;
            case R.id.tv_release_comment://发布
                if (etComment.getText().toString().equals("")) {
                    ToastUtil.show(Activity_AllComments_ylq.this, "发布内容不能为空");
                } else {
                    initRelease(etComment.getText().toString());
                }
                break;
            case R.id.ll_zan:
                initZan();
                break;
        }
    }

    private void initZan() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_AllComments_ylq.this, SpName.token, ""));
        params.addQueryStringParameter("linkId", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.circleZan, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            DatadataBean dataBean = new Gson().fromJson(responseInfo.result, DatadataBean.class);
                            if (dataBean.getHeader().getStatus() == 0) {
                                if (dataBean.getData() == 0) {
                                    ivZan.setImageResource(R.mipmap.dianzan_false);
                                    int num = (Integer.parseInt(tvZan.getText().toString()));
                                    tvZan.setText(num > 0 ? num - 1 + "" : num + "");
//                                    bean.get(pos).setIsFavor(0);
                                } else {
                                    ivZan.setImageResource(R.mipmap.dianzan_true);
                                    tvZan.setText((Integer.parseInt(tvZan.getText().toString()) + 1) + "");
//                                    bean.get(pos).setIsFavor(1);
                                }
                            } else if (dataBean.getHeader().getStatus() == 3) {
                                ToastUtil.show(Activity_AllComments_ylq.this, "请登录");
                            } else {
                                ToastUtil.show(Activity_AllComments_ylq.this, dataBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_AllComments_ylq.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_AllComments_ylq.this, e.getMessage());
                    }
                });
    }

    private void initRelease(String s) {
        //0是评论1是回复
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_AllComments_ylq.this, SpName.token, ""));
        params.addQueryStringParameter("linkId", getIntent().getStringExtra("id"));
        params.addQueryStringParameter("contentType", tyty + "");
        params.addQueryStringParameter("content", s);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.circleCommentOther, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.e("55555", responseInfo.result);
                        String s = responseInfo.result;
                        try {
                            NullBean nullBean = new Gson().fromJson(responseInfo.result, NullBean.class);
                            if (nullBean.getHeader().getStatus() == 0) {
                                etComment.setText("");
                                initComment();
                            } else if (nullBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_AllComments_ylq.this);
                            } else {
                                ToastUtil.show(Activity_AllComments_ylq.this, nullBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_AllComments_ylq.this, "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_AllComments_ylq.this, "连接网络失败");
                    }
                });
    }

    //显示分享popupWindow
    private void show_popupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(Activity_AllComments_ylq.this).inflate(R.layout.popup_ylq_share, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
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
        View rootview = LayoutInflater.from(Activity_AllComments_ylq.this).inflate(R.layout.popup_my_share, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    //分享的单击事件
    private class Share_Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ShareSDK.initSDK(Activity_AllComments_ylq.this);
            switch (v.getId()) {
                case R.id.ll_share_kongjian:
                    QZone.ShareParams sp = new QZone.ShareParams();
                    sp.setTitle(getIntent().getStringExtra("title"));
                    sp.setText(getIntent().getStringExtra("content"));
                    sp.setSite("智慧湿地游");
                    if (getIntent().getStringExtra("type").equals("1")) {//视频
                        sp.setImageUrl(getIntent().getStringExtra("videoimg"));
                        sp.setTitleUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&video=" + getIntent().getStringExtra("video") + "&shareType=" + "1"); // 标题的超链接
                        sp.setSiteUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&video=" + getIntent().getStringExtra("video") + "&shareType=" + "1");
                        sp.setUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&video=" + getIntent().getStringExtra("video") + "&shareType=" + "1");
                        sp.setShareType(Platform.SHARE_VIDEO);
                    } else if (getIntent().getStringExtra("type").equals("2")) {
                        sp.setImageUrl(getIntent().getStringExtra("img"));
                        sp.setTitleUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&pic=" + getIntent().getStringExtra("img") + "&shareType=" + "0"); // 标题的超链接
                        sp.setSiteUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&pic=" + getIntent().getStringExtra("img") + "&shareType=" + "0");
                        sp.setUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&pic=" + getIntent().getStringExtra("img") + "&shareType=" + "0");
                    }
                    Platform qq = ShareSDK.getPlatform(QZone.NAME);
                    qq.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                            initShare();
                        }

                        @Override
                        public void onError(Platform platform, int i, Throwable throwable) {
                            ToastUtil.show(Activity_AllComments_ylq.this, "分享失败");
                        }

                        @Override
                        public void onCancel(Platform platform, int i) {
                            ToastUtil.show(Activity_AllComments_ylq.this, "取消分享");
                        }
                    });
                    qq.share(sp);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_share_pengyouquan:
                    WechatMoments.ShareParams sp3 = new WechatMoments.ShareParams();
                    sp3.setTitle(getIntent().getStringExtra("title"));
                    sp3.setText(getIntent().getStringExtra("content"));
                    if (getIntent().getStringExtra("type").equals("1")) {//视频
                        sp3.setImageUrl(getIntent().getStringExtra("videoimg"));
                        sp3.setTitleUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&video=" + getIntent().getStringExtra("video") + "&shareType=" + "1"); // 标题的超链接
                        sp3.setSiteUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&video=" + getIntent().getStringExtra("video") + "&shareType=" + "1");
                        sp3.setUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&video=" + getIntent().getStringExtra("video") + "&shareType=" + "1");
                        sp3.setShareType(Platform.SHARE_VIDEO);
                    } else if (getIntent().getStringExtra("type").equals("2")) {
                        sp3.setImageUrl(getIntent().getStringExtra("img"));
                        sp3.setTitleUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&pic=" + getIntent().getStringExtra("img") + "&shareType=" + "0"); // 标题的超链接
                        sp3.setSiteUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&pic=" + getIntent().getStringExtra("img") + "&shareType=" + "0");
                        sp3.setUrl(URL.circleSharePoint + "?headImg=" + getIntent().getStringExtra("head_img") + "&name=" + getIntent().getStringExtra("nickname") + "&date=" + getIntent().getStringExtra("date") + "&title=" + getIntent().getStringExtra("title") + "&content=" + getIntent().getStringExtra("content") + "&pic=" + getIntent().getStringExtra("img") + "&shareType=" + "0");
                        sp3.setShareType(Platform.SHARE_WEBPAGE);
                    }
                    sp3.setSite("智慧湿地游");
                    Platform wechatmoments = ShareSDK.getPlatform(WechatMoments.NAME);
                    wechatmoments.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                            initShare();
                        }

                        @Override
                        public void onError(Platform platform, int i, Throwable throwable) {
                            ToastUtil.show(Activity_AllComments_ylq.this, "分享失败");
                        }

                        @Override
                        public void onCancel(Platform platform, int i) {
                            ToastUtil.show(Activity_AllComments_ylq.this, "取消分享");
                        }
                    });
                    wechatmoments.share(sp3);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_popup:
                    mPopWindow.dismiss();
                    break;
            }
        }
    }

    private void initShare() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_AllComments_ylq.this, SpName.token, ""));
        params.addQueryStringParameter("linkId", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.circleShare, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            NullBean nullBean = new Gson().fromJson(responseInfo.result, NullBean.class);
                            if (nullBean.getHeader().getStatus() == 0) {
                                tvShare.setText((Integer.parseInt(tvShare.getText().toString()) + 1) + "");
                            } else if (nullBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_AllComments_ylq.this);
                            } else {
                                ToastUtil.show(Activity_AllComments_ylq.this, nullBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_AllComments_ylq.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_AllComments_ylq.this, e.getMessage());
                    }
                });

    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i("1111", event.getKeyCode() + "");
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(Activity_AllComments_ylq.this.getCurrentFocus().getWindowToken(), 0);
            }

            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    aBoolean = false;
//                    etComment.setText("");
                    etComment.setHint("写下你想说的吧...");
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //监听返回键  ，当隐藏输入法的时候  显示回复帖子
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (aBoolean) {
                aBoolean = false;
                etComment.setHint("写下你想说的吧...");
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (aBoolean){
            aBoolean = false;
            editText.setHint("写下你想说的吧...");
            return;
        }else {
            finish();
        }
    }*/
}
