package com.demo.amusement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.amusement.bean.GoBean;
import com.demo.amusement.bean.ThreeBridBean;
import com.demo.demo.myapplication.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：sonnerly on 2017/1/11 0011 09:21
 */
public class ThreeBridAdapter extends BaseAdapter{

    Context mContext;
    List<ThreeBridBean.DataBean> list;
    public ThreeBridAdapter(Context mContext,List<ThreeBridBean.DataBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_brid,null);
            cyzMode.iv_backgro= (ImageView) convertView.findViewById(R.id.adp_ivbrid);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getImg_url(), cyzMode.iv_backgro);


        return convertView;
    }

    private class CyzMode{
        //        LinearLayout ll_Image;
        ImageView iv_backgro;

    }
}
