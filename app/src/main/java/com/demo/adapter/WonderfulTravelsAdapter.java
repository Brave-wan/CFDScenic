package com.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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

import com.demo.amusement.activity.Activity_AmusementGraphic;
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
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class WonderfulTravelsAdapter extends BaseAdapter {
    Context mContext;
    private PopupWindow mPopWindow;
    private List<CircleCommentBean.DataBean.RowsBean> list;

    private  final int VIDEO_CONTENT_DESC_MAX_LINE = 7;// 默认展示最大行数3行
    private  final int SHRINK_UP_STATE = 1;// 收起状态
    private  final int SPREAD_STATE = 2;// 展开状态
    String[] s;
    String picUrl="";
    public WonderfulTravelsAdapter(Context mContext,List<CircleCommentBean.DataBean.RowsBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_wonderful_travels,null);
            cyzMode.ll_fenxiang = (LinearLayout) convertView.findViewById(R.id.ll_fenxiang);
            cyzMode.ll_pinglun = (LinearLayout) convertView.findViewById(R.id.ll_pinglun);
            cyzMode.ll_zan= (LinearLayout) convertView.findViewById(R.id.ll_zan);
            cyzMode.tv_zan= (TextView) convertView.findViewById(R.id.tv_zan);
            cyzMode.iv_zan= (ImageView) convertView.findViewById(R.id.iv_zan);
            cyzMode.tv_location= (TextView) convertView.findViewById(R.id.comment_location);//定位
            cyzMode.tv_share= (TextView) convertView.findViewById(R.id.tv_share);
            cyzMode.tv_comment= (TextView) convertView.findViewById(R.id.tv_comment);
            cyzMode.view_touxiangT= (RoundImageView) convertView.findViewById(R.id.view_touxiangTravel);
            cyzMode.tuji= (TextView) convertView.findViewById(R.id.tv_tuji_wonder);
            cyzMode.tv_usernameT= (TextView) convertView.findViewById(R.id.tv_usernameTravel);
            cyzMode.tv_dateT= (TextView) convertView.findViewById(R.id.tv_dateTravel);
            cyzMode.iv_imageT= (ImageView) convertView.findViewById(R.id.iv_imageTravel);
            cyzMode.tv_titleT= (TextView) convertView.findViewById(R.id.tv_titleTravel);
            cyzMode.tv_contentT= (TextView) convertView.findViewById(R.id.tv_contentTravel);
            cyzMode.tv_moreT= (TextView) convertView.findViewById(R.id.tv_MoreTravel);
cyzMode.fl_tujie= (FrameLayout) convertView.findViewById(R.id.fl_tujie);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        //展开更多判断
        ImageLoader.getInstance().displayImage(list.get(position).getHead_img(), cyzMode.view_touxiangT);
        cyzMode.tv_usernameT.setText(list.get(position).getNick_name());
         s=list.get(position).getCreatedate().split(" ");
        cyzMode.tv_dateT.setText(s[0]);
        if(list.get(position).getPicList().size()==0){

        }else{
            ImageLoader.getInstance().displayImage(list.get(position).getPicList().get(0), cyzMode.iv_imageT);
            for(int i=0;i<list.get(position).getPicList().size();i++){
                if (i == list.get(position).getPicList().size() - 1)
                    picUrl += list.get(position).getPicList().get(i);
                else picUrl += list.get(position).getPicList().get(i) + ",";
            }
        }
        cyzMode.tv_titleT.setText(list.get(position).getTitle());
        cyzMode.tv_contentT.setText(list.get(position).getContent());
        if(list.get(position).getContent().length()>=168){
            cyzMode.tv_moreT.setVisibility(View.VISIBLE);
        }else{
            cyzMode.tv_moreT.setVisibility(View.GONE);
        }
        cyzMode.tv_location.setText(list.get(position).getTravel_name());
        cyzMode.tv_zan.setText(list.get(position).getFavorCount()+"");
        cyzMode.tv_comment.setText(list.get(position).getCommentCount() + "");
        cyzMode.tv_share.setText(list.get(position).getShareCount() + "");
        cyzMode.tv_zan.setText(list.get(position).getFavorCount()+"");
        if (list.get(position).getState() == SPREAD_STATE) {
            cyzMode.tv_contentT.setMaxLines(Integer.MAX_VALUE);
            cyzMode.tv_contentT.requestLayout();
            list.get(position).setState(SPREAD_STATE);
            cyzMode.tv_moreT.setText("收起");
        } else if (list.get(position).getState() == SHRINK_UP_STATE) {
            cyzMode.tv_contentT.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
            cyzMode.tv_contentT.requestLayout();
            list.get(position).setState(SHRINK_UP_STATE);
            cyzMode.tv_moreT.setText("...展开更多");
        }
        cyzMode.tv_moreT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getState() == SPREAD_STATE) {
                    cyzMode.tv_contentT.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    cyzMode.tv_contentT.requestLayout();
                    list.get(position).setState(SHRINK_UP_STATE);
                    cyzMode.tv_moreT.setText("...展开更多");
                } else if (list.get(position).getState() == SHRINK_UP_STATE) {
                    cyzMode.tv_contentT.setMaxLines(Integer.MAX_VALUE);
                    cyzMode.tv_contentT.requestLayout();
                    list.get(position).setState(SPREAD_STATE);
                    cyzMode.tv_moreT.setText("收起");
                }
            }
        });

        cyzMode.fl_tujie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(mContext, Activity_AmusementGraphic.class);
                String[] array=new String[list.get(position).getPicList().size()];
                for (int index=0;index<list.get(position).getPicList().size();index++){
                    array[index]=list.get(position).getPicList().get(index);
                }
                intent.putExtra("array",array);
                mContext.startActivity(intent);
            }
        });
        //是否点赞
        if (list.get(position).getIsFavor()==0){
            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
        }else if(list.get(position).getIsFavor()==1) {
            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
        }

        cyzMode.ll_fenxiang.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.ll_pinglun.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.ll_zan.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.view_touxiangT.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.tuji.setOnClickListener(new SetOnClick(cyzMode,position));
        return convertView;
    }

    private class CyzMode{
        LinearLayout ll_fenxiang ;
        LinearLayout ll_pinglun ;
        LinearLayout ll_zan;
        TextView tv_zan;
        ImageView iv_zan;

        TextView tv_location;
        TextView tv_comment;
        TextView tv_share;
        TextView tv_contentT;
        TextView tv_moreT;
        RoundImageView view_touxiangT;
        TextView tv_usernameT;
        TextView tv_dateT;
        TextView tv_titleT;
        ImageView iv_imageT;
        TextView tuji;
        FrameLayout fl_tujie;
    }

    public class SetOnClick implements View.OnClickListener {
        CyzMode cyzMode;
        int position;

        public SetOnClick(CyzMode cyzMode,int position) {
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
                    intent.putExtra("type","2");
                    intent.putExtra("head_img",list.get(position).getHead_img());
                    intent.putExtra("nickname",list.get(position).getNick_name());
                    intent.putExtra("date",s[0]);
                    intent.putExtra("img", list.get(position).getPicList().get(0));
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
                case R.id.view_touxiangTravel:    //点击头像进入他人信息
                    intent = new Intent(mContext, Activity_OtherInformation.class);
                    intent.putExtra("head_img",list.get(position).getHead_img());
                    intent.putExtra("nickname",list.get(position).getNick_name());
                    intent.putExtra("id",list.get(position).getUserId());
                    mContext.startActivity(intent);
                    break;
                case R.id.tv_tuji_wonder: //图解  Activity_ScenicspotGraphic
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
                        Log.e("1111", responseInfo.result);
                        try {
                            DatadataBean dataBean=new Gson().fromJson(responseInfo.result, DatadataBean.class);
                            if(dataBean.getHeader().getStatus()==0){
                                if(dataBean.getData()==0){
                                    cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
                                    cyzMode.tv_zan.setText((Integer.parseInt(cyzMode.tv_zan.getText().toString()) - 1) + "");
                                    list.get(pos).setIsFavor(0);
                                }else{
                                    cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
                                    cyzMode.tv_zan.setText((Integer.parseInt(cyzMode.tv_zan.getText().toString()) + 1)+"");
                                    list.get(pos).setIsFavor(1);
                                }
                            }else if(dataBean.getHeader().getStatus()== 3){
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
                case R.id.ll_share_kongjian:
                    QZone.ShareParams sp = new QZone.ShareParams();
                    sp.setTitle(list.get(pos).getTitle());
                     // 标题的超链接
                    sp.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
                    sp.setText("这篇游记很棒！好东西一定要分享给你~");
                    sp.setImageUrl(list.get(pos).getPicList().get(0));
                    sp.setSite("智慧湿地游");
                    sp.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
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

                    mPopWindow.dismiss();
                    break;
                case R.id.ll_share_pengyouquan:
                    WechatMoments.ShareParams sp3 = new WechatMoments.ShareParams();
                    sp3.setTitle(list.get(pos).getTitle());
                    // 标题的超链接
                    sp3.setTitleUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
                    sp3.setText("这篇游记很棒！好东西一定要分享给你~");
                    sp3.setImageUrl(list.get(pos).getPicList().get(0));//分享网络图片
                    sp3.setUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
                    sp3.setSite("智慧湿地游");
                    sp3.setSiteUrl(URL.circleSharePoint+"?headImg="+list.get(pos).getHead_img()+"&name="+list.get(pos).getNick_name()+"&date="+s+"&title="+list.get(pos).getTitle()+"&content="+list.get(pos).getContent()+"&pic="+picUrl+"&shareType="+"0");
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
                        Log.e("1111", responseInfo.result);
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
