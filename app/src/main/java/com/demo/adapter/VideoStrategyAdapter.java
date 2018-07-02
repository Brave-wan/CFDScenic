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

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 我的游乐圈--视频攻略
 * Created by Administrator on 2016/8/9 0009.
 */
public class VideoStrategyAdapter extends BaseAdapter {
    Context mContext;
    private PopupWindow mPopWindow;
    private List<CircleCommentBean.DataBean.RowsBean> list;
//    VideoView mVideo = null;//视频播放器
    private  final int VIDEO_CONTENT_DESC_MAX_LINE = 7;// 默认展示最大行数3行
    private  final int SHRINK_UP_STATE = 1;// 收起状态
    private  final int SPREAD_STATE = 2;// 展开状态
    String[] s;
    public VideoStrategyAdapter(Context mContext,List<CircleCommentBean.DataBean.RowsBean> list) {
        this.mContext = mContext;
        this.list=list;
//        create_Bean(5);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_video_strategy,null);

            cyzMode.ll_fenxiang = (LinearLayout) convertView.findViewById(R.id.ll_fenxiang);
            cyzMode.ll_pinglun = (LinearLayout) convertView.findViewById(R.id.ll_pinglun);
            cyzMode.ll_zan= (LinearLayout) convertView.findViewById(R.id.ll_zan);
            cyzMode.tv_zan= (TextView) convertView.findViewById(R.id.tv_zan);
            cyzMode.iv_zan= (ImageView) convertView.findViewById(R.id.iv_zan);
            cyzMode.ll_shipin= (LinearLayout) convertView.findViewById(R.id.ll_shipin);
            cyzMode.mVideoView= (VideoView) convertView.findViewById(R.id.mVideoView);//视频
            cyzMode.iv_image_video= (ImageView) convertView.findViewById(R.id.iv_image_video);//第一帧图片
            cyzMode.iv_play_video= (ImageView) convertView.findViewById(R.id.iv_play_video);//播放按钮
            cyzMode.tv_location= (TextView) convertView.findViewById(R.id.comment_location);//定位
            cyzMode.tv_share= (TextView) convertView.findViewById(R.id.tv_share);
            cyzMode.tv_comment= (TextView) convertView.findViewById(R.id.tv_comment);
            cyzMode.fl_shipin = (FrameLayout) convertView.findViewById(R.id.fl_shipin);
            cyzMode.view_touxiang= (RoundImageView) convertView.findViewById(R.id.view_touxiang);
            cyzMode.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            cyzMode.tv_more= (TextView) convertView.findViewById(R.id.tv_More);
            cyzMode.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            cyzMode.tv_username= (TextView) convertView.findViewById(R.id.tv_username);
            cyzMode.tv_date= (TextView) convertView.findViewById(R.id.tv_date);
            cyzMode.tv_play_time= (TextView) convertView.findViewById(R.id.tv_play_time);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(list.get(position).getHead_img(), cyzMode.view_touxiang);
        //cyzMode.mVideoView.setVideoPath(list.get(position).getTravel_video());

        ImageLoader.getInstance().displayImage(list.get(position).getTravel_img(), cyzMode.iv_image_video);
        setvoidotime(cyzMode.tv_play_time, list.get(position).getTravel_video());
        cyzMode.tv_username.setText(list.get(position).getNick_name());
         s=list.get(position).getCreatedate().split(" ");
        cyzMode.tv_date.setText(s[0]);
        cyzMode.tv_title.setText(list.get(position).getTitle());
        cyzMode.tv_content.setText(list.get(position).getContent());
        if(list.get(position).getContent().length()<=168){
            cyzMode.tv_more.setVisibility(View.GONE);
        }else {
            cyzMode.tv_more.setVisibility(View.VISIBLE);
        }
        cyzMode.tv_location.setText(list.get(position).getTravel_name());
        cyzMode.tv_zan.setText(list.get(position).getFavorCount()+"");
        cyzMode.tv_comment.setText(list.get(position).getCommentCount() + "");
        cyzMode.tv_share.setText(list.get(position).getShareCount() + "");
        if (list.get(position).getState() == SPREAD_STATE) {
            cyzMode.tv_content.setMaxLines(Integer.MAX_VALUE);
            cyzMode.tv_content.requestLayout();
            list.get(position).setState(SPREAD_STATE);
            cyzMode.tv_more.setText("收起");
        } else if (list.get(position).getState() == SHRINK_UP_STATE) {
            cyzMode.tv_content.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
            cyzMode.tv_content.requestLayout();
            list.get(position).setState(SHRINK_UP_STATE);
            cyzMode.tv_more.setText("...展开更多");
        }
        cyzMode.tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getState() == SPREAD_STATE) {
                    cyzMode.tv_content.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    cyzMode.tv_content.requestLayout();
                    list.get(position).setState(SHRINK_UP_STATE);
                    cyzMode.tv_more.setText("...展开更多");
                } else if (list.get(position).getState() == SHRINK_UP_STATE) {
                    cyzMode.tv_content.setMaxLines(Integer.MAX_VALUE);
                    cyzMode.tv_content.requestLayout();
                    list.get(position).setState(SPREAD_STATE);
                    cyzMode.tv_more.setText("收起");
                }
            }
        });
        //0否 1是
        if(list.get(position).getIsFavor()==0){
            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
        }else if(list.get(position).getIsFavor()==1){
            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
        }
//        //是否点赞
//        if (list.get(position).getZan()){
//            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
//        }else {
//            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
//        }
        cyzMode.iv_play_video.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.ll_fenxiang.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.ll_pinglun.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.ll_zan.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.fl_shipin.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.view_touxiang.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.ll_fenxiang.setOnClickListener(new SetOnClick(cyzMode,position));

        return convertView;
    }
    private void setvoidotime(final TextView textView,final String urlint ){

        new Thread(new Runnable() {
            @Override
            public void run() {
                final   String time=   VideoUtils.getsplength(urlint);
                textView.post(new Runnable() {
                    public void run() {
                        textView.setText(time);
                    }
                });

            }
        }).start();

    }
    private class CyzMode{
        LinearLayout ll_fenxiang ;
        LinearLayout ll_pinglun ;
        LinearLayout ll_shipin;
        LinearLayout ll_zan;
        TextView tv_zan;
        ImageView iv_zan;
        TextView tv_location;
        TextView tv_comment;
        TextView tv_share;
       ImageView iv_image_video;
       ImageView iv_play_video;
        FrameLayout fl_shipin;
        TextView tv_content;
        TextView tv_more;
       RoundImageView view_touxiang;
        TextView tv_username;
        TextView tv_date;
        TextView tv_title;
        TextView tv_play_time;
        VideoView mVideoView;
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
                case R.id.iv_play_video://播放按钮
                    if (cyzMode.mVideoView != null) {
                        cyzMode.mVideoView.pause();//暂停播放
                    }
                    //跳转播放页面
                    Intent i = new Intent(mContext, MyPlay.class);
                    Bundle bd = new Bundle();
                    bd.putString("uri", list.get(position).getTravel_video());
                    i.putExtras(bd);
                    mContext.startActivity(i);
                    break;
                case R.id.ll_fenxiang:
                    show_popupWindow(cyzMode,position);
                    break;
                case R.id.ll_pinglun:
                    intent = new Intent(mContext, Activity_AllComments_ylq.class);
                    intent.putExtra("id",list.get(position).getId()+"");
                    intent.putExtra("type","1");
                    intent.putExtra("head_img",list.get(position).getHead_img());
                    intent.putExtra("nickname",list.get(position).getNick_name());
                    intent.putExtra("date",s[0]);
                    intent.putExtra("time",cyzMode.tv_play_time.getText().toString());
                    intent.putExtra("video",list.get(position).getTravel_video());
                    intent.putExtra("videoimg", list.get(position).getTravel_img());
                    intent.putExtra("time",cyzMode.tv_play_time.getText().toString());
                    intent.putExtra("title",list.get(position).getTitle());
                    intent.putExtra("content", list.get(position).getContent());
                    intent.putExtra("location",list.get(position).getTravel_name());
                    intent.putExtra("zan",list.get(position).getFavorCount()+"");
                    intent.putExtra("cocount",list.get(position).getCommentCount()+"");
                    intent.putExtra("sharecount", list.get(position).getShareCount() + "");
                    intent.putExtra("favor",list.get(position).getIsFavor());
                    mContext.startActivity(intent);
                    break;
                case R.id.ll_zan:
                    initZan(position,cyzMode);
                    break;
                case R.id.view_touxiang:    //点击头像进入他人信息
                    intent = new Intent(mContext, Activity_OtherInformation.class);
                    intent.putExtra("head_img",list.get(position).getHead_img());
                    intent.putExtra("nickname",list.get(position).getNick_name());
                    intent.putExtra("id",list.get(position).getUserId());
                    mContext.startActivity(intent);
                    break;

            }
        }
    }

    private void initZan(final int position,final CyzMode cyzMode) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("linkId",list.get(position).getId()+"");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.circleZan, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.e("1111", responseInfo.result);
                        try {
                            DatadataBean dataBean=new Gson().fromJson(responseInfo.result, DatadataBean.class);
                            if(dataBean.getHeader().getStatus()==0){
                                if(dataBean.getData()==0){
                                    cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
                                    cyzMode.tv_zan.setText((Integer.parseInt(cyzMode.tv_zan.getText().toString()) - 1) + "");
                                    list.get(position).setIsFavor(0);
                                }else{
                                    cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
                                    cyzMode.tv_zan.setText((Integer.parseInt(cyzMode.tv_zan.getText().toString()) + 1)+"");
                                    list.get(position).setIsFavor(1);
                                }
                            }else if(dataBean.getHeader().getStatus()== 2){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            }else {
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


    //显示分享popupWindow
    private void show_popupWindow(CyzMode cyzMode,int pos) {
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
        ll_popup.setOnClickListener(new Share_Click(pos,cyzMode));
        ll_weibo.setOnClickListener(new Share_Click(pos,cyzMode));
        ll_kongjian.setOnClickListener(new Share_Click(pos,cyzMode));
        ll_pengyouquan.setOnClickListener(new Share_Click(pos,cyzMode));


        //显示PopupWindow
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.popup_my_share, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    //分享的单击事件
    private class Share_Click implements View.OnClickListener {
        int pos;
        CyzMode cyzMode;
        Share_Click(int pos,CyzMode cyzMode) {
            this.pos = pos;
            this.cyzMode=cyzMode;
        }
        @Override
        public void onClick(View v) {
            ShareSDK.initSDK(mContext);
            switch (v.getId()) {
                case R.id.ll_share_kongjian:
                    QZone.ShareParams sp = new QZone.ShareParams();
                    sp.setTitle(list.get(pos).getTitle());
                     // 标题的超链接
                    sp.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                    sp.setText(list.get(pos).getContent());
                    sp.setImageUrl(list.get(pos).getTravel_img());
                    sp.setSite("智慧湿地游");
                    sp.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                    sp.setShareType(Platform.SHARE_VIDEO);
                    Platform qq = ShareSDK.getPlatform(QZone.NAME);
                    qq.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                            initShare(pos,cyzMode);
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
                case R.id.ll_share_weibo:
                    SinaWeibo.ShareParams sp1 = new SinaWeibo.ShareParams();
                    sp1.setTitle(list.get(pos).getTitle());
                     // 标题的超链接
                    sp1.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                    sp1.setText(list.get(pos).getContent());
                    sp1.setImageUrl(list.get(pos).getTravel_img());//分享网络图片
                    sp1.setSite("智慧湿地游");
                    sp1.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                    sp1.setShareType(Platform.SHARE_VIDEO);
                    Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                    weibo.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                            initShare(pos,cyzMode);
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
                    weibo.share(sp1);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_share_pengyouquan:
                    WechatMoments.ShareParams sp3 = new WechatMoments.ShareParams();
                    sp3.setTitle(list.get(pos).getTitle());
                    // 标题的超链接
                    sp3.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                    sp3.setText(list.get(pos).getContent());
                    sp3.setImageUrl(list.get(pos).getTravel_img());//分享网络图片
                    sp3.setSite("智慧湿地游");
                    sp3.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                    sp3.setShareType(Platform.SHARE_VIDEO);
                    Platform wechatmoments = ShareSDK.getPlatform(WechatMoments.NAME);
                    wechatmoments.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                            initShare(pos,cyzMode);
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
    private void initShare(final int pos,final CyzMode cyzMode) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("linkId",list.get(pos).getId()+"");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.circleShare, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            NullBean nullBean=new Gson().fromJson(responseInfo.result, NullBean.class);
                            if(nullBean.getHeader().getStatus()==0){
                                cyzMode.tv_share.setText((Integer.parseInt(cyzMode.tv_share.getText().toString()) + 1) + "");
                            }else if( nullBean.getHeader().getStatus()== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            }else {
                                ToastUtil.show(mContext, nullBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext,e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, e.getMessage());
                    }
                });

    }
}
