package com.demo.amusement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.demo.adapter.CommentImageAdapter;
import com.demo.amusement.activity.Activity_AmusementGraphic;
import com.demo.amusement.bean.CommentToOtherBean;
import com.demo.demo.myapplication.R;
import com.demo.my.bean.MyCommentBean;
import com.demo.scenicspot.activity.Activity_ScenicspotGraphic;
import com.demo.scenicspot.activity.ScienceMoreActivity;

import java.util.List;

/**
 * 作者：sonnerly on 2016/10/13 0013 17:47
 */
public class CommentToOtherAdapter extends BaseAdapter{

    Context mContext;
    List<MyCommentBean.DataBean> list;//CommentToOtherBean.DataBean.RowsBean

    public CommentToOtherAdapter(Context mContext, List<MyCommentBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_evaluate,null);
            cyzMode.tv_comment_comment= (TextView) convertView.findViewById(R.id.tv_comment_comment);
            cyzMode.tv_ScenicSpot= (TextView) convertView.findViewById(R.id.tv_ScenicSpot);
            cyzMode.tv_comment_date= (TextView) convertView.findViewById(R.id.tv_comment_date);
            cyzMode.gv_imageview= (GridView) convertView.findViewById(R.id.gv_imageview);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        cyzMode.tv_ScenicSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ScienceMoreActivity.class);
                intent.putExtra("id", list.get(position).getVisitorsId() + "");
                mContext.startActivity(intent);
            }
        });
        cyzMode.tv_ScenicSpot.setText(list.get(position).getVisitorsName());
        cyzMode.tv_comment_comment.setText(list.get(position).getContent());
        String[] s=list.get(position).getCreateTime().split(" ");
        cyzMode.tv_comment_date.setText(s[0]);
        if (list.get(position).getPicList().size()>0){
            cyzMode.gv_imageview.setAdapter(new CommentImageAdapter(mContext,list.get(position).getPicList()));
            cyzMode.gv_imageview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                   /* Intent intent = new Intent(mContext, Activity_ScenicspotGraphic.class);
                    intent.putExtra("id", list.get(position).getId() + "");
                    mContext.startActivity(intent);*/

                    Intent intent = new Intent(mContext, Activity_AmusementGraphic.class);
                    String[] array=new String[list.get(position).getPicList().size()];
                    for (int index=0;index<list.get(position).getPicList().size();index++){
                        array[index]=list.get(position).getPicList().get(index).getImgUrl();
                    }
                    intent.putExtra("array",array);
                    mContext.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    private class CyzMode{
        TextView tv_ScenicSpot;
        TextView tv_comment_comment;
        TextView tv_comment_date;
        GridView gv_imageview;
    }
}
