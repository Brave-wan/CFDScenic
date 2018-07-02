package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.amusement.bean.GoBean;
import com.demo.demo.myapplication.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 景区活动--活动推荐
 * Created by Administrator on 2016/8/2 0002.
 */
public class RecommendAdapter  extends BaseAdapter{
    Context mContext;
    List<GoBean.DataBean.RowsBean> list;
    public RecommendAdapter(Context mContext,List<GoBean.DataBean.RowsBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_recommend,null);
//            cyzMode.ll_Image= (LinearLayout) convertView.findViewById(R.id.ll_Image);
            cyzMode.iv_backgro= (ImageView) convertView.findViewById(R.id.iv_backgro);
            cyzMode.tv_Title= (TextView) convertView.findViewById(R.id.tv_Title);
            cyzMode.tv_introduce= (TextView) convertView.findViewById(R.id.tv_introduce);
            cyzMode.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            cyzMode.tv_ScenicSpot= (TextView) convertView.findViewById(R.id.tv_ScenicSpot);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getHead_img(),cyzMode.iv_backgro);
        cyzMode.tv_Title.setText(list.get(position).getName());
        cyzMode.tv_introduce.setText(list.get(position).getVisitors_describe());
        String[] b=list.get(position).getStart_valid().split(" ");
        String[] e=list.get(position).getEnd_valid().split(" ");
        cyzMode.tv_time.setText("活动时间："+b[0]+"--"+e[0]);
        cyzMode.tv_ScenicSpot.setText("活动景点："+list.get(position).getAddress());

        return convertView;
    }

    private class CyzMode{
//        LinearLayout ll_Image;
        ImageView iv_backgro;
        TextView tv_Title;
        TextView tv_introduce;
        TextView tv_time;
        TextView tv_ScenicSpot;
    }
}
