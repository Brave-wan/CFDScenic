package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.demo.demo.myapplication.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 图解适配器
 * Created by Administrator on 2016/8/2 0002.
 */
public class GraphicAdapter extends BaseAdapter{
    Context mContext;
    List<String> data;

    public GraphicAdapter(Context mContext, List<String> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_gv_graphic,null);
        ImageView im= (ImageView) convertView.findViewById(R.id.im_graphic);
        ImageLoader.getInstance().displayImage(data.get(position),im);
        return convertView;
    }
}
