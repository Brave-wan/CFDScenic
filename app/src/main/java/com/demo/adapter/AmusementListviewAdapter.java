package com.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.VideoView;

import com.demo.amusement.activity.Activity_AmusementGraphic;
import com.demo.amusement.activity.MyPlay;
import com.demo.amusement.bean.CircleCommentBean;
import com.demo.amusement.bean.DatadataBean;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.NullBean;
import com.demo.my.activity.Activity_AllComments_ylq;
import com.demo.amusement.activity.Activity_OtherInformation;
import com.demo.demo.myapplication.R;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.CustomVideoView;
import com.demo.view.RoundImageView;
import com.demo.view.VideoUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 游乐圈的适配器   图片和视频都有    视频未完成
 * Created by Administrator on 2016/7/26 0026.
 */
public class AmusementListviewAdapter extends BaseAdapter {
    private Context mContext;
    private PopupWindow mPopWindow;
    //    private ArrayList<OpenBean> list;
    private List<CircleCommentBean.DataBean.RowsBean> bean;
    String[] s;
    private final int VIDEO_CONTENT_DESC_MAX_LINE = 5;// 默认展示最大行数3行
    private final int SHRINK_UP_STATE = 1;// 收起状态
    private final int SPREAD_STATE = 2;// 展开状态
    String picUrl = "";

    public AmusementListviewAdapter(Context mContext, List<CircleCommentBean.DataBean.RowsBean> bean) {
        this.mContext = mContext;
        this.bean = bean;
        //create_Bean(5);
    }


    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CyzMode cyzMode;
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_ylq, null);
            cyzMode.ll_tupian = (LinearLayout) convertView.findViewById(R.id.ll_tupian_ylq);//图片布局
//            cyzMode.ll_shipin= (LinearLayout) convertView.findViewById(R.id.ll_shipin_ylq);//视频布局
            cyzMode.ll_zan = (LinearLayout) convertView.findViewById(R.id.ll_zan);//赞布局
            cyzMode.iv_zan = (ImageView) convertView.findViewById(R.id.iv_zan);//赞图片
            cyzMode.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);//赞数
            cyzMode.ll_pinglun = (LinearLayout) convertView.findViewById(R.id.ll_pinglun);//评论布局
            cyzMode.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            cyzMode.ll_fenxiang = (LinearLayout) convertView.findViewById(R.id.ll_fenxiang);//分享布局
            cyzMode.tv_share = (TextView) convertView.findViewById(R.id.tv_share);
            cyzMode.tv_location = (TextView) convertView.findViewById(R.id.comment_location);//定位
            cyzMode.mVideoView = (CustomVideoView) convertView.findViewById(R.id.mVideoView_ylq);//播放器
            //图片
            cyzMode.fl_tujie = (FrameLayout) convertView.findViewById(R.id.fl_tujie_ylq);//图片和图集
            cyzMode.iv_imageT = (ImageView) convertView.findViewById(R.id.iv_imageT_ylq);//图片
            cyzMode.tv_tuji = (TextView) convertView.findViewById(R.id.tv_tujiylq_ylq);//图集按钮
            cyzMode.view_touxiangT = (RoundImageView) convertView.findViewById(R.id.view_touxiangT_ylq);//头像
            cyzMode.tv_contentT = (TextView) convertView.findViewById(R.id.tv_contentT_ylq);//评论
            cyzMode.tv_moreT = (TextView) convertView.findViewById(R.id.tv_MoreT_ylq);//展开更多
            cyzMode.tv_titleT = (TextView) convertView.findViewById(R.id.tv_titleT_ylq);//标题
            cyzMode.tv_usernameT = (TextView) convertView.findViewById(R.id.tv_usernameT_ylq);//昵称
            cyzMode.tv_dateT = (TextView) convertView.findViewById(R.id.tv_dateT_ylq);//日期
            //视频
            cyzMode.fl_shipin = (FrameLayout) convertView.findViewById(R.id.fl_shipin_ylq);//视频加图片加分钟布局
            cyzMode.iv_image_video_ylq = (ImageView) convertView.findViewById(R.id.iv_image_video_ylq);
            cyzMode.iv_image_video_play = (ImageView) convertView.findViewById(R.id.iv_image_video_play);
            cyzMode.tv_minute_play = (TextView) convertView.findViewById(R.id.tv_minute_play);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }


        ImageLoader.getInstance().displayImage(bean.get(position).getHead_img(), cyzMode.view_touxiangT);
        cyzMode.tv_usernameT.setText(bean.get(position).getNick_name());
        s = bean.get(position).getCreatedate().split(" ");
        cyzMode.tv_dateT.setText(s[0]);
        cyzMode.tv_titleT.setText(bean.get(position).getTitle());
        cyzMode.tv_contentT.setText(bean.get(position).getContent());

        if (bean.get(position).getContent().length() >= 160) {
            cyzMode.tv_moreT.setVisibility(View.VISIBLE);
        } else {
            cyzMode.tv_moreT.setVisibility(View.GONE);
        }

        cyzMode.tv_location.setText(bean.get(position).getTravel_name());
        cyzMode.tv_zan.setText(bean.get(position).getFavorCount() + "");
        cyzMode.tv_comment.setText(bean.get(position).getCommentCount() + "");
        cyzMode.tv_share.setText(bean.get(position).getShareCount() + "");

        //0否 1是
        if (bean.get(position).getIsFavor() == 0) {
            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
        } else if (bean.get(position).getIsFavor() == 1) {
            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
        }

        //判断是图片还是视频
        if (bean.get(position).getTravel_type() == 2) {//图片
            cyzMode.fl_shipin.setVisibility(View.GONE);
            cyzMode.fl_tujie.setVisibility(View.VISIBLE);

            if (bean.get(position).getPicList().size() == 0) {

            } else {
                ImageLoader.getInstance().displayImage(bean.get(position).getPicList().get(0), cyzMode.iv_imageT);
                for (int i = 0; i < bean.get(position).getPicList().size(); i++) {
                    if (i == bean.get(position).getPicList().size() - 1)
                        picUrl += bean.get(position).getPicList().get(i);
                    else picUrl += bean.get(position).getPicList().get(i) + ",";
                }
            }
        } else if (bean.get(position).getTravel_type() == 1) {//视频
            cyzMode.fl_shipin.setVisibility(View.VISIBLE);
            cyzMode.fl_tujie.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(bean.get(position).getTravel_img(), cyzMode.iv_image_video_ylq);
            setvoidotime(cyzMode.tv_minute_play, bean.get(position).getTravel_video());
        }
        //展开更多
        if (bean.get(position).getState() == SPREAD_STATE) {
            cyzMode.tv_contentT.setMaxLines(Integer.MAX_VALUE);
            cyzMode.tv_contentT.requestLayout();
            bean.get(position).setState(SPREAD_STATE);
            cyzMode.tv_moreT.setText("收起");
        } else if (bean.get(position).getState() == SHRINK_UP_STATE) {
            cyzMode.tv_contentT.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
            cyzMode.tv_contentT.requestLayout();
            bean.get(position).setState(SHRINK_UP_STATE);
            cyzMode.tv_moreT.setText("...展开更多");
        }
        cyzMode.tv_moreT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.get(position).getState() == SPREAD_STATE) {
                    cyzMode.tv_contentT.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    cyzMode.tv_contentT.requestLayout();
                    bean.get(position).setState(SHRINK_UP_STATE);
                    cyzMode.tv_moreT.setText("...展开更多");
                } else if (bean.get(position).getState() == SHRINK_UP_STATE) {
                    cyzMode.tv_contentT.setMaxLines(Integer.MAX_VALUE);
                    cyzMode.tv_contentT.requestLayout();
                    bean.get(position).setState(SPREAD_STATE);
                    cyzMode.tv_moreT.setText("收起");
                }
            }
        });


        cyzMode.iv_image_video_play.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.fl_tujie.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.ll_fenxiang.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.ll_pinglun.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.ll_zan.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.view_touxiangT.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.tv_tuji.setOnClickListener(new SetOnClick(cyzMode, position));
        return convertView;
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

    private class CyzMode {
        LinearLayout ll_shipin;
        LinearLayout ll_tupian;
        LinearLayout ll_fenxiang;
        LinearLayout ll_pinglun;
        LinearLayout ll_zan;
        TextView tv_zan;
        ImageView iv_zan;
        TextView tv_comment;
        TextView tv_share;
        TextView tv_location;

        FrameLayout fl_tujie;
        TextView tv_tuji;
        ImageView iv_imageT;
        TextView tv_contentT;
        TextView tv_moreT;
        RoundImageView view_touxiangT;
        TextView tv_usernameT;
        TextView tv_dateT;
        TextView tv_titleT;

        FrameLayout fl_shipin;
        ImageView iv_image_video_ylq;
        ImageView iv_image_video_play;
        TextView tv_minute_play;

        CustomVideoView mVideoView;
    }

    public class SetOnClick implements View.OnClickListener {
        CyzMode cyzMode;
        int position;

        public SetOnClick(CyzMode cyzMode, int position) {
            this.cyzMode = cyzMode;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.iv_image_video_play:
                    if (cyzMode.mVideoView != null) {
                        cyzMode.mVideoView.pause();//暂停播放
                    }
                    //跳转播放页面
                    Intent i = new Intent(mContext, MyPlay.class);
                    Bundle bd = new Bundle();
                    bd.putString("uri", bean.get(position).getTravel_video());
                    i.putExtras(bd);
                    mContext.startActivity(i);
                    break;
                case R.id.ll_fenxiang:
                    show_popupWindow(position, cyzMode);
                    break;
                case R.id.ll_pinglun://评论
                    intent = new Intent(mContext, Activity_AllComments_ylq.class);
                    intent.putExtra("id", bean.get(position).getId() + "");
                    intent.putExtra("type", bean.get(position).getTravel_type() + "");
                    intent.putExtra("head_img", bean.get(position).getHead_img());
                    intent.putExtra("nickname", bean.get(position).getNick_name());
                    intent.putExtra("date", s[0]);
                    if (bean.get(position).getTravel_type() == 2) {
                        intent.putExtra("img", bean.get(position).getPicList().get(0));
                        intent.putExtra("time", cyzMode.tv_minute_play.getText().toString());
                    } else {
                        intent.putExtra("img", "");
                    }
                    intent.putExtra("time", cyzMode.tv_minute_play.getText().toString());
                    intent.putExtra("video", bean.get(position).getTravel_video());
                    intent.putExtra("videoimg", bean.get(position).getTravel_img());
                    intent.putExtra("title", bean.get(position).getTitle());
                    intent.putExtra("content", bean.get(position).getContent());
                    intent.putExtra("location", bean.get(position).getTravel_name());
                    intent.putExtra("zan", bean.get(position).getFavorCount() + "");
                    intent.putExtra("cocount", bean.get(position).getCommentCount() + "");
                    intent.putExtra("sharecount", bean.get(position).getShareCount() + "");
                    intent.putExtra("favor", bean.get(position).getIsFavor());
                    mContext.startActivity(intent);
                    break;
                case R.id.ll_zan:
                    initZan(position, cyzMode);
                    break;
                case R.id.view_touxiangT_ylq:    //点击头像进入他人信息
                    intent = new Intent(mContext, Activity_OtherInformation.class);
                    intent.putExtra("head_img", bean.get(position).getHead_img());
                    intent.putExtra("nickname", bean.get(position).getNick_name());
                    intent.putExtra("id", bean.get(position).getUserId());
                    mContext.startActivity(intent);
                    break;
                case R.id.tv_tujiylq_ylq: //图解
                    intent = new Intent(mContext, Activity_AmusementGraphic.class);
                    String[] array = new String[bean.get(position).getPicList().size()];
                    for (int index = 0; index < bean.get(position).getPicList().size(); index++) {
                        array[index] = bean.get(position).getPicList().get(index);
                    }
                    intent.putExtra("array", array);
                    mContext.startActivity(intent);
                    break;
                case R.id.fl_tujie_ylq:
                    intent = new Intent(mContext, Activity_AmusementGraphic.class);
                    String[] arrays = new String[bean.get(position).getPicList().size()];
                    for (int index = 0; index < bean.get(position).getPicList().size(); index++) {
                        arrays[index] = bean.get(position).getPicList().get(index);
                    }
                    intent.putExtra("array", arrays);
                    mContext.startActivity(intent);
                    break;


            }
        }
    }

    private void initZan(final int pos, final CyzMode cyzMode) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("linkId", bean.get(pos).getId() + "");
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
                                    cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
                                    cyzMode.tv_zan.setText((Integer.parseInt(cyzMode.tv_zan.getText().toString()) - 1) + "");
                                    bean.get(pos).setIsFavor(0);
                                } else {
                                    cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
                                    cyzMode.tv_zan.setText((Integer.parseInt(cyzMode.tv_zan.getText().toString()) + 1) + "");
                                    bean.get(pos).setIsFavor(1);
                                }
                            } else if (dataBean.getHeader().getStatus() == 3) {
                                ToastUtil.show(mContext, "请登录");
                            } else {
                                ToastUtil.show(mContext, dataBean.getHeader().getMsg());
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

    //创建展开全文javaBean
    private void create_Bean(int i) {
        bean = new ArrayList<>();
        for (int x = 0; x < i; x++) {
            CircleCommentBean.DataBean.RowsBean openBean = new CircleCommentBean.DataBean.RowsBean();
            bean.add(openBean);
        }
    }

    //显示分享popupWindow
    private void show_popupWindow(int pos, CyzMode cyzMode) {
        //设置contentView
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_ylq_share, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        LinearLayout ll_weibo = (LinearLayout) contentView.findViewById(R.id.ll_share_weibo);
        LinearLayout ll_kongjian = (LinearLayout) contentView.findViewById(R.id.ll_share_kongjian);
        LinearLayout ll_pengyouquan = (LinearLayout) contentView.findViewById(R.id.ll_share_pengyouquan);
        LinearLayout ll_popup = (LinearLayout) contentView.findViewById(R.id.ll_popup);
        ll_popup.setOnClickListener(new Share_Click(pos, cyzMode));
        ll_weibo.setOnClickListener(new Share_Click(pos, cyzMode));
        ll_kongjian.setOnClickListener(new Share_Click(pos, cyzMode));
        ll_pengyouquan.setOnClickListener(new Share_Click(pos, cyzMode));


        //显示PopupWindow
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.popup_my_share, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    //分享的单击事件
    class Share_Click implements View.OnClickListener {
        int pos;
        CyzMode cyzMode;

        Share_Click(int pos, CyzMode cyzMode) {
            this.pos = pos;
            this.cyzMode = cyzMode;
        }

        @Override
        public void onClick(View v) {
            ShareSDK.initSDK(mContext);
            switch (v.getId()) {
                case R.id.ll_share_kongjian://qq空间
                    QZone.ShareParams sp = new QZone.ShareParams();
                    sp.setTitle(bean.get(pos).getTitle());
                    sp.setText(bean.get(pos).getContent());
                    if (bean.get(pos).getTravel_type() == 2) {//图片
                        // 标题的超链接
                        sp.setTitleUrl(URL.circleSharePoint + "?headImg=" + bean.get(pos).getHead_img() + "&name=" + bean.get(pos).getNick_name() + "&date=" + s + "&title=" + bean.get(pos).getTitle() + "&content=" + bean.get(pos).getContent() + "&pic=" + picUrl + "&shareType=" + "0");
                        sp.setImageUrl(bean.get(pos).getPicList().get(0));
                        sp.setSiteUrl(URL.circleSharePoint + "?headImg=" + bean.get(pos).getHead_img() + "&name=" + bean.get(pos).getNick_name() + "&date=" + s + "&title=" + bean.get(pos).getTitle() + "&content=" + bean.get(pos).getContent() + "&pic=" + picUrl + "&shareType=" + "0");
                    } else if (bean.get(pos).getTravel_type() == 1) {
                        sp.setTitleUrl(URL.circleSharePoint + "?headImg=" + bean.get(pos).getHead_img() + "&name=" + bean.get(pos).getNick_name() + "&date=" + s + "&title=" + bean.get(pos).getTitle() + "&content=" + bean.get(pos).getContent() + "&video=" + bean.get(pos).getTravel_video() + "&shareType=" + "1");
                        sp.setImageUrl(bean.get(pos).getTravel_img());
                        sp.setSiteUrl(URL.circleSharePoint + "?headImg=" + bean.get(pos).getHead_img() + "&name=" + bean.get(pos).getNick_name() + "&date=" + s + "&title=" + bean.get(pos).getTitle() + "&content=" + bean.get(pos).getContent() + "&video=" + bean.get(pos).getTravel_video() + "&shareType=" + "1");
                    }
                    sp.setSite("智慧湿地游");
                    Platform qq = ShareSDK.getPlatform(QZone.NAME);
                    qq.setPlatformActionListener(new PlatformActionListener() {//String headImg,String name,String date,String title,String content,String pic,String video,Integer shareType

                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                            initShare(pos, cyzMode);
                        }

                        @Override
                        public void onError(Platform platform, int i, Throwable throwable) {
                            ToastUtil.show(mContext, "分享失败");
                        }

                        @Override
                        public void onCancel(Platform platform, int i) {
                            ToastUtil.show(mContext, "取消分享");
                        }
                    });
                    qq.share(sp);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_share_weibo://微博

                    mPopWindow.dismiss();
                    break;
                case R.id.ll_share_pengyouquan://朋友圈
                    WechatMoments.ShareParams sp3 = new WechatMoments.ShareParams();
                    sp3.setTitle(bean.get(pos).getTitle());
                    sp3.setText(bean.get(pos).getContent());
                    if (bean.get(pos).getTravel_type() == 2) {//图片
                        sp3.setTitleUrl(URL.circleSharePoint + "?headImg=" + bean.get(pos).getHead_img() + "&name=" + bean.get(pos).getNick_name() + "&date=" + s + "&title=" + bean.get(pos).getTitle() + "&content=" + bean.get(pos).getContent() + "&pic=" + picUrl + "&shareType=" + "0");
                        sp3.setImageUrl(bean.get(pos).getPicList().get(0));//分享网络图片
                        sp3.setSiteUrl(URL.circleSharePoint + "?headImg=" + bean.get(pos).getHead_img() + "&name=" + bean.get(pos).getNick_name() + "&date=" + s + "&title=" + bean.get(pos).getTitle() + "&content=" + bean.get(pos).getContent() + "&pic=" + picUrl + "&shareType=" + "0");
                    } else if (bean.get(pos).getTravel_type() == 1) {
                        sp3.setTitleUrl(URL.circleSharePoint + "?headImg=" + bean.get(pos).getHead_img() + "&name=" + bean.get(pos).getNick_name() + "&date=" + s + "&title=" + bean.get(pos).getTitle() + "&content=" + bean.get(pos).getContent() + "&video=" + bean.get(pos).getTravel_video() + "&shareType=" + "1");
                        sp3.setImageUrl(bean.get(pos).getTravel_img());
                        sp3.setSiteUrl(URL.circleSharePoint + "?headImg=" + bean.get(pos).getHead_img() + "&name=" + bean.get(pos).getNick_name() + "&date=" + s + "&title=" + bean.get(pos).getTitle() + "&content=" + bean.get(pos).getContent() + "&video=" + bean.get(pos).getTravel_video() + "&shareType=" + "1");
                    }
                    sp3.setUrl("https://www.baidu.com/");
                    sp3.setSite("智慧湿地游");
                    sp3.setShareType(Platform.SHARE_WEBPAGE);
                    Platform wechatmoments = ShareSDK.getPlatform(WechatMoments.NAME);
                    wechatmoments.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                            initShare(pos, cyzMode);
                        }

                        @Override
                        public void onError(Platform platform, int i, Throwable throwable) {
                            ToastUtil.show(mContext, "分享失败");
                        }

                        @Override
                        public void onCancel(Platform platform, int i) {
                            ToastUtil.show(mContext, "取消分享");
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

    private void initShare(final int pos, final CyzMode cyzMode) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("linkId", bean.get(pos).getId() + "");
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
                                cyzMode.tv_share.setText((Integer.parseInt(cyzMode.tv_share.getText().toString()) + 1) + "");
                            } else if (nullBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            } else {
                                ToastUtil.show(mContext, nullBean.getHeader().getMsg());
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
