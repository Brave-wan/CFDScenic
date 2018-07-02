package com.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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

import com.demo.bean.OpenBean;
import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_AllComments_wdylq;
import com.demo.scenicspot.activity.Activity_ScenicspotGraphic;

import java.util.ArrayList;

/**
 * 我的--我的游乐圈--只有图片
 * Created by Administrator on 2016/7/26 0026.
 */
public class MyRecreationJcAdapter extends BaseAdapter {
    private Context mContext;
    private PopupWindow mPopWindow;
    private ArrayList<OpenBean> list;

    private  final int VIDEO_CONTENT_DESC_MAX_LINE = 7;// 默认展示最大行数3行
    private  final int SHRINK_UP_STATE = 1;// 收起状态
    private  final int SPREAD_STATE = 2;// 展开状态

    public MyRecreationJcAdapter(Context mContext) {
        this.mContext = mContext;
        create_Bean(5);
    }



    @Override
    public int getCount() {
        return 5;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_my_recreation_jc, null);
            cyzMode.ll_zan= (LinearLayout) convertView.findViewById(R.id.ll_zan);
            cyzMode.tv_zan= (TextView) convertView.findViewById(R.id.tv_zan);
            cyzMode.iv_zan= (ImageView) convertView.findViewById(R.id.iv_zan);

            cyzMode.fl_tujie = (FrameLayout) convertView.findViewById(R.id.fl_tujie);
            cyzMode.ll_fenxiang = (LinearLayout) convertView.findViewById(R.id.ll_fenxiang);
            cyzMode.ll_pinglun = (LinearLayout) convertView.findViewById(R.id.ll_pinglun);
            cyzMode.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            cyzMode.tv_more= (TextView) convertView.findViewById(R.id.tv_More);
            convertView.setTag(cyzMode);
        }else{
            cyzMode= (CyzMode) convertView.getTag();
        }


        cyzMode.tv_content.setText(R.string.txt_info);

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
        //是否点赞
        if (list.get(position).getZan()){
            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
        }else {
            cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
        }
        cyzMode.tv_zan.setText(list.get(position).getZan_Number() + "");

        cyzMode.ll_fenxiang.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.ll_pinglun.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.ll_zan.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.fl_tujie.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.ll_fenxiang.setOnClickListener(new SetOnClick(cyzMode,position));
        cyzMode.ll_pinglun.setOnClickListener(new SetOnClick(cyzMode,position));


        return convertView;
    }

    private class CyzMode{
        FrameLayout fl_tujie ;
        LinearLayout ll_fenxiang ;
        LinearLayout ll_pinglun ;
        TextView tv_content;
        TextView tv_more;

        LinearLayout ll_zan;
        TextView tv_zan;
        ImageView iv_zan;
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
                case R.id.ll_fenxiang:
                    show_popupWindow();
                    break;
                case R.id.ll_pinglun:
                    intent = new Intent(mContext, Activity_AllComments_wdylq.class);
                    mContext.startActivity(intent);
                    break;
                case R.id.ll_zan:
                    if (list.get(position).getZan()){
                        cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_false);
                        list.get(position).setZan_Number(list.get(position).getZan_Number() - 1);
                        cyzMode.tv_zan.setText(list.get(position).getZan_Number()+"");
                        list.get(position).setZan(false);
                    }else {
                        cyzMode.iv_zan.setImageResource(R.mipmap.dianzan_true);
                        list.get(position).setZan_Number(list.get(position).getZan_Number()+1);
                        cyzMode.tv_zan.setText(list.get(position).getZan_Number()+"");
                        list.get(position).setZan(true);
                    }
                    break;
                case R.id.fl_tujie: //图解
                    intent = new Intent(mContext, Activity_ScenicspotGraphic.class);
                    mContext.startActivity(intent);
                    break;
            }
        }
    }

    //创建展开全文javaBean
    private void create_Bean(int i){
        list=new ArrayList<>();
        for (int x=0;x<i;x++){
            OpenBean openBean=new OpenBean();
            list.add(openBean);
        }
    }

    //显示分享popupWindow
    private void show_popupWindow() {
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
        ll_popup.setOnClickListener(new Share_Click());
        ll_weibo.setOnClickListener(new Share_Click());
        ll_kongjian.setOnClickListener(new Share_Click());
        ll_pengyouquan.setOnClickListener(new Share_Click());


        //显示PopupWindow
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.popup_my_share, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    //分享的单击事件
    private class Share_Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_share_kongjian:
                    Toast.makeText(mContext, "kongjian", Toast.LENGTH_LONG).show();
                    break;
                case R.id.ll_share_weibo:
                    Toast.makeText(mContext, "weibo", Toast.LENGTH_LONG).show();
                    break;
                case R.id.ll_share_pengyouquan:
                    Toast.makeText(mContext, "pengyouquan", Toast.LENGTH_LONG).show();
                    break;
                case R.id.ll_popup:
                    mPopWindow.dismiss();
                    break;
            }
        }
    }
}
