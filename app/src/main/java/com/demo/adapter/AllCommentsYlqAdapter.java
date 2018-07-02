package com.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.amusement.activity.Activity_OtherInformation;
import com.demo.amusement.bean.CommentBean;
import com.demo.demo.myapplication.R;
import com.demo.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.sharesdk.onekeyshare.themes.classic.RotateImageView;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class AllCommentsYlqAdapter extends BaseAdapter {
    Context mContext;
    List<CommentBean.DataBean> list;
    public AllCommentsYlqAdapter(Context mContext,List<CommentBean.DataBean> list) {
        this.mContext = mContext;
        this.list=list;
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
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_all_comments_ylq,null);
            cyzMode.tv_allcomment_nickname= (TextView) convertView.findViewById(R.id.tv_allcomment_nickname);
            cyzMode.tv_allcomment_time= (TextView) convertView.findViewById(R.id.tv_allcomment_time);
            cyzMode.tv_allcomment_comment= (TextView) convertView.findViewById(R.id.tv_allcomment_comment);
            cyzMode.riv_allcomment_headimg= (RoundImageView) convertView.findViewById(R.id.riv_allcomment_headimg);
            convertView.setTag(cyzMode);
        }else{
            cyzMode= (CyzMode) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getHead_img(),cyzMode.riv_allcomment_headimg);
        cyzMode.tv_allcomment_nickname.setText(list.get(position).getNick_name());
        cyzMode.tv_allcomment_time.setText(list.get(position).getCreate_time());
        cyzMode.tv_allcomment_comment.setText(list.get(position).getContent());
        cyzMode.riv_allcomment_headimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Activity_OtherInformation.class);
                intent.putExtra("head_img",list.get(position).getHead_img());
                intent.putExtra("nickname",list.get(position).getNick_name());
                intent.putExtra("id",list.get(position).getUserId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    private class CyzMode {
       RoundImageView riv_allcomment_headimg;
        TextView tv_allcomment_nickname;
        TextView tv_allcomment_time;
        TextView tv_allcomment_comment;
    }
}
