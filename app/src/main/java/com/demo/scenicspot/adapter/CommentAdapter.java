package com.demo.scenicspot.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.adapter.CommentImageAdapter;
import com.demo.amusement.activity.Activity_AmusementGraphic;
import com.demo.amusement.adapter.ConnentAdpater;
import com.demo.scenicspot.bean.ScenicSpotCommentBean;
import com.demo.demo.myapplication.R;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：宋玉磊 on 2016/8/2 0002 16:23
 */
public class CommentAdapter extends BaseAdapter{
    Context mContext;
    List<ScenicSpotCommentBean.DataBean> databean;

    public CommentAdapter(Context mContext, List<ScenicSpotCommentBean.DataBean> databean) {
        this.mContext = mContext;
        this.databean = databean;
    }

    @Override
    public int getCount() {
        return databean.size();
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
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.science_adapter_comment,null);
            cyzMode.iv_userImage= (ImageView) convertView.findViewById(R.id.iv_userImage);
            cyzMode.tv_adp_comment_nickname= (TextView) convertView.findViewById(R.id.tv_adp_comment_nickname);
            cyzMode.tv_comment_comment= (TextView) convertView.findViewById(R.id.tv_comment_comment);
            cyzMode.tv_comment_date= (TextView) convertView.findViewById(R.id.tv_comment_date);
            cyzMode.gv_ImageView= (GridView) convertView.findViewById(R.id.gv_ImageView);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        //判断有没有图片
        if (databean.get(position).getHave_img()==0){
            cyzMode.gv_ImageView.setVisibility(View.GONE);
        }else {
            cyzMode.gv_ImageView.setVisibility(View.VISIBLE);
            List<String> listBeans=databean.get(position).getPic();
            cyzMode.gv_ImageView.setAdapter(new ConnentAdpater(mContext,listBeans));
            cyzMode.gv_ImageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                    Intent intent = new Intent(mContext, Activity_AmusementGraphic.class);
                    String[] array = new String[databean.get(position).getPic().size()];
                    for (int index = 0; index < databean.get(position).getPic().size(); index++) {
                        array[index] = databean.get(position).getPic().get(index);
                    }
                    intent.putExtra("array", array);
                    mContext.startActivity(intent);
                }
            });
        }



        ImageLoader.getInstance().displayImage(databean.get(position).getHead_img(), cyzMode.iv_userImage);

        cyzMode.tv_adp_comment_nickname.setText(databean.get(position).getNick_name());
        cyzMode.tv_comment_comment.setText(databean.get(position).getContent());
        String time=databean.get(position).getCreate_time().substring(0,10);
        cyzMode.tv_comment_date.setText(time);

        return convertView;
    }

    private class CyzMode{
        ImageView iv_userImage;
        TextView tv_adp_comment_nickname;
        TextView tv_comment_comment;
        TextView tv_comment_date;
        GridView gv_ImageView;
    }
}
