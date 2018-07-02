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
public class GraphicStringAdapter extends BaseAdapter{
    Context mContext;
    String[] strings;

    public GraphicStringAdapter(Context mContext, String[] strings) {
        this.mContext = mContext;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.length;
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
        ImageLoader.getInstance().displayImage(strings[position],im);
        return convertView;
    }
}
