package com.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.Toast;
import android.widget.VideoView;

import com.demo.amusement.activity.Activity_AmusementGraphic;
import com.demo.amusement.activity.MyPlay;
import com.demo.amusement.bean.CircleCommentBean;
import com.demo.amusement.bean.DatadataBean;
import com.demo.bean.OpenBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.NullBean;
import com.demo.my.activity.Activity_AllComments_ylq;
import com.demo.scenicspot.activity.Activity_ScenicspotGraphic;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.MyRoundImageView;
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
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class OtherReleaseAdapter extends BaseAdapter {
    Context mContext;
    private PopupWindow mPopWindow;
    private List<CircleCommentBean.DataBean.RowsBean> list;
    String[] s;
    private  final int VIDEO_CONTENT_DESC_MAX_LINE = 7;// 默认展示最大行数3行
    private  final int SHRINK_UP_STATE = 1;// 收起状态
    private  final int SPREAD_STATE = 2;// 展开状态
    String picUrl="";

    public OtherReleaseAdapter(Context mContext,List<CircleCommentBean.DataBean.RowsBean> list) {
        this.mContext = mContext;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_other_release,null);
            cyzMode.ll_shipin= (LinearLayout) convertView.findViewById(R.id.ll_shipin_release);

            cyzMode.ll_fenxiang = (LinearLayout) convertView.findViewById(R.id.ll_fenxiang);
            cyzMode.ll_pinglun = (LinearLayout) convertView.findViewById(R.id.ll_zan);
            cyzMode.ll_zan=(LinearLayout) convertView.findViewById(R.id.ll_pinglun);
            cyzMode.iv_pic= (ImageView) convertView.findViewById(R.id.iv_pic_release);
            cyzMode.tv_tuji= (TextView) convertView.findViewById(R.id.tv_tujie_release);
            cyzMode.vv= (VideoView) convertView.findViewById(R.id.mVideoView_release);
            cyzMode.iv_beijing= (ImageView) convertView.findViewById(R.id.iv_beijing_release);
            cyzMode.iv_play= (ImageView) convertView.findViewById(R.id.iv_play_release);
            cyzMode.tv_time= (TextView) convertView.findViewById(R.id.tv_fenzhong_release);
            cyzMode.fl_tujie = (FrameLayout) convertView.findViewById(R.id.fl_tujie_release);
            cyzMode.fl_shipin = (FrameLayout) convertView.findViewById(R.id.fl_shipin_release);
            cyzMode.tv_contentS= (TextView) convertView.findViewById(R.id.tv_contentS_release);
            cyzMode.tv_moreS= (TextView) convertView.findViewById(R.id.tv_MoreS_release);
            cyzMode.tv_titleS= (TextView) convertView.findViewById(R.id.tv_titleS_release);
            cyzMode.address= (TextView) convertView.findViewById(R.id.comment_location);
            cyzMode.zan= (TextView) convertView.findViewById(R.id.tv_zan);
            cyzMode.fengxiang= (TextView) convertView.findViewById(R.id.tv_share);
            cyzMode.comment= (TextView) convertView.findViewById(R.id.tv_comment);
            cyzMode.iv_zan= (ImageView) convertView.findViewById(R.id.iv_zan);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        /*if (position==0){
            cyzMode.ll_shipin.setVisibility(View.VISIBLE);
            cyzMode.ll_tupian.setVisibility(View.GONE);
        }else {
            cyzMode.ll_shipin.setVisibility(View.GONE);
            cyzMode.ll_tupian.setVisibility(View.VISIBLE);
        }*/
        cyzMode.tv_titleS.setText(list.get(position).getTitle());
        cyzMode.tv_contentS.setText(list.get(position).getContent());
        if(list.get(position).getContent().length()>=168){
            cyzMode.tv_moreS.setVisibility(View.VISIBLE);
        }else{
            cyzMode.tv_moreS.setVisibility(View.GONE);
        }
        cyzMode.address.setText(list.get(position).getTravel_name());
        cyzMode.zan.setText(list.get(position).getFavorCount()+"");
        cyzMode.comment.setText(list.get(position).getCommentCount() + "");
        cyzMode.fengxiang.setText(list.get(position).getShareCount() + "");
        s=list.get(position).getCreatedate().split(" ");
        //0否 1是
        if(list.get(position).getIsFavor()==0){
            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
        }else if(list.get(position).getIsFavor()==1){
            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
        }
        if (list.get(position).getTravel_type()==2){//图片
            cyzMode.fl_shipin.setVisibility(View.GONE);
            cyzMode.fl_tujie.setVisibility(View.VISIBLE);
            if(list.get(position).getPicList().size()==0){

            }else{
                ImageLoader.getInstance().displayImage(list.get(position).getPicList().get(0), cyzMode.iv_pic);
                for(int i=0;i<list.get(position).getPicList().size();i++){
                    if (i == list.get(position).getPicList().size() - 1)
                        picUrl += list.get(position).getPicList().get(i);
                    else picUrl += list.get(position).getPicList().get(i) + ",";
                }
            }
        }else if(list.get(position).getTravel_type()==1){//视频
            cyzMode.fl_shipin.setVisibility(View.VISIBLE);
            cyzMode.fl_tujie.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(list.get(position).getTravel_img(), cyzMode.iv_beijing);
            setvoidotime(cyzMode.tv_time, list.get(position).getTravel_video());
        }

        if (list.get(position).getState() == SPREAD_STATE) {
            cyzMode.tv_contentS.setMaxLines(Integer.MAX_VALUE);
            cyzMode.tv_contentS.requestLayout();
            list.get(position).setState(SPREAD_STATE);
            cyzMode.tv_moreS.setText("收起");
        } else if (list.get(position).getState() == SHRINK_UP_STATE) {
            cyzMode.tv_contentS.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
            cyzMode.tv_contentS.requestLayout();
            list.get(position).setState(SHRINK_UP_STATE);
            cyzMode.tv_moreS.setText("...展开更多");
        }
        cyzMode.tv_moreS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getState() == SPREAD_STATE) {
                    cyzMode.tv_contentS.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    cyzMode.tv_contentS.requestLayout();
                    list.get(position).setState(SHRINK_UP_STATE);
                    cyzMode.tv_moreS.setText("...展开更多");
                } else if (list.get(position).getState() == SHRINK_UP_STATE) {
                    cyzMode.tv_contentS.setMaxLines(Integer.MAX_VALUE);
                    cyzMode.tv_contentS.requestLayout();
                    list.get(position).setState(SPREAD_STATE);
                    cyzMode.tv_moreS.setText("收起");
                }
            }
        });


        cyzMode.fl_tujie.setOnClickListener(new setOnClick(cyzMode,position));
        cyzMode.ll_fenxiang.setOnClickListener(new setOnClick(cyzMode,position));
        cyzMode.ll_pinglun.setOnClickListener(new setOnClick(cyzMode,position));
        cyzMode.ll_zan.setOnClickListener(new setOnClick(cyzMode,position));
        cyzMode.tv_tuji.setOnClickListener(new setOnClick(cyzMode,position));
        cyzMode.iv_play.setOnClickListener(new setOnClick(cyzMode,position));
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
        LinearLayout ll_zan;
        LinearLayout ll_fenxiang ;
        LinearLayout ll_pinglun ;
        LinearLayout ll_shipin;
        LinearLayout ll_tupian;

        FrameLayout fl_tujie;
        FrameLayout fl_shipin;
        ImageView iv_pic;
        TextView tv_tuji;
        VideoView vv;
        ImageView iv_beijing;
        ImageView iv_play;
        TextView tv_time;
        TextView address;
        TextView tv_contentS;
        TextView tv_moreS;
        TextView tv_titleS;
        ImageView iv_zan;
        TextView fengxiang;
        TextView zan;
        TextView comment;
    }

    public class setOnClick implements View.OnClickListener {
        CyzMode cyzMode;
        int position;
        public setOnClick(CyzMode cyzMode,int position) {
            this.cyzMode = cyzMode;
            this.position=position;
        }
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.ll_fenxiang:
                    show_popupWindow(position,cyzMode);
                    break;
                case R.id.ll_pinglun:
                    intent = new Intent(mContext, Activity_AllComments_ylq.class);
                    intent.putExtra("id",list.get(position).getId()+"");
                    intent.putExtra("type",list.get(position).getTravel_type()+"");
                    intent.putExtra("head_img",list.get(position).getHead_img());
                    intent.putExtra("nickname",list.get(position).getNick_name());
                    intent.putExtra("date",s[0]);
                    if(list.get(position).getTravel_type()==2){
                        intent.putExtra("img", list.get(position).getPicList().get(0));
                        intent.putExtra("time",cyzMode.tv_time.getText().toString());
                    }else{
                        intent.putExtra("img", "");
                    }
                    intent.putExtra("video",list.get(position).getTravel_video());
                    intent.putExtra("videoimg", list.get(position).getTravel_img());
                    intent.putExtra("title",list.get(position).getTitle());
                    intent.putExtra("content", list.get(position).getContent());
                    intent.putExtra("location",list.get(position).getTravel_name());
                    intent.putExtra("zan",list.get(position).getFavorCount()+"");
                    intent.putExtra("cocount",list.get(position).getCommentCount()+"");
                    intent.putExtra("sharecount",list.get(position).getShareCount()+"");
                    intent.putExtra("favor",list.get(position).getIsFavor());
                    mContext.startActivity(intent);
                    break;
                case R.id.ll_zan:
                    initZan(position,cyzMode);
                    break;
                case R.id.iv_play_release:
                    if (cyzMode.vv != null) {
                        cyzMode.vv.pause();//暂停播放
                    }
                    //跳转播放页面
                    Intent i = new Intent(mContext, MyPlay.class);
                    Bundle bd = new Bundle();
                    bd.putString("uri", list.get(position).getTravel_video());
                    i.putExtras(bd);
                    mContext.startActivity(i);
                    break;
                case R.id.tv_tujie_release: //图解
//                    intent = new Intent(mContext, Activity_ScenicspotGraphic.class);
//                    mContext.startActivity(intent);

                    intent = new Intent(mContext, Activity_AmusementGraphic.class);
                    String[] array=new String[list.get(position).getPicList().size()];
                    for (int index=0;index<list.get(position).getPicList().size();index++){
                        array[index]=list.get(position).getPicList().get(index);
                    }
                    intent.putExtra("array",array);
                    mContext.startActivity(intent);
                    break;
            }
        }
    }
    private void initZan(final int pos,final CyzMode cyzMode) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("linkId",list.get(pos).getId()+"");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.circleZan, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            DatadataBean dataBean=new Gson().fromJson(responseInfo.result, DatadataBean.class);
                            if(dataBean.getHeader().getStatus()==0){
                                if(dataBean.getData()==0){
                                    cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
                                    cyzMode.zan.setText((Integer.parseInt(cyzMode.zan.getText().toString()) - 1) + "");
                                    list.get(pos).setIsFavor(0);
                                }else{
                                    cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
                                    cyzMode.zan.setText((Integer.parseInt(cyzMode.zan.getText().toString()) + 1)+"");
                                    list.get(pos).setIsFavor(1);
                                }
                            }else if(dataBean.getHeader().getStatus()== 3){
                                ToastUtil.show(mContext, "请登录");
                            }else {
                                ToastUtil.show(mContext, dataBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });

    }

    //显示分享popupWindow
    private void show_popupWindow(int pos,CyzMode cyzMode) {
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
                case R.id.ll_share_kongjian://qq空间
                    QZone.ShareParams sp = new QZone.ShareParams();
                    sp.setTitle(list.get(pos).getTitle());
                    sp.setText(list.get(pos).getContent());
                    if(list.get(pos).getTravel_type()==2){//图片
                        // 标题的超链接
                        sp.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
                        sp.setImageUrl(list.get(pos).getPicList().get(0));
                        sp.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle() +"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
                    }else if(list.get(pos).getTravel_type()==1){
                        sp.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                        sp.setImageUrl(list.get(pos).getTravel_img());
                        sp.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                    }
                    sp.setSite("智慧湿地游");
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
                case R.id.ll_share_weibo://微博
                    SinaWeibo.ShareParams sp1 = new SinaWeibo.ShareParams();
                    sp1.setTitle(list.get(pos).getTitle());
                    sp1.setText(list.get(pos).getContent());
                    if(list.get(pos).getTravel_type()==2){//图片
                        // 标题的超链接
                        sp1.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
                        sp1.setImageUrl(list.get(pos).getPicList().get(0));
                        sp1.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle() +"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
                    }else if(list.get(pos).getTravel_type()==1){
                        sp1.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                        sp1.setImageUrl(list.get(pos).getTravel_img());
                        sp1.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                    }
                    sp1.setSite("智慧湿地游");
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
                case R.id.ll_share_pengyouquan://朋友圈
                    WechatMoments.ShareParams sp3 = new WechatMoments.ShareParams();
                    sp3.setTitle(list.get(pos).getTitle());
                    sp3.setText(list.get(pos).getContent());
                    sp3.setUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
                    if(list.get(pos).getTravel_type()==2){//图片
                        // 标题的超链接
                        sp3.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
                        sp3.setImageUrl(list.get(pos).getPicList().get(0));
                        sp3.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle() +"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
                    }else if(list.get(pos).getTravel_type()==1){
                        sp3.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                        sp3.setImageUrl(list.get(pos).getTravel_img());
                        sp3.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&video="+list.get(pos).getTravel_video()+"&shareType="+"1");
                    }
                    sp3.setSite("智慧湿地游");
                    sp3.setShareType(Platform.SHARE_WEBPAGE);
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
                                cyzMode.fengxiang.setText((Integer.parseInt(cyzMode.fengxiang.getText().toString()) + 1) + "");
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
