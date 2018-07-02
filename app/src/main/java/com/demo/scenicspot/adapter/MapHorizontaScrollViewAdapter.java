package com.demo.scenicspot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;

/**
 * 作者：宋玉磊 on 2016/8/3 0003 16:28
 */
public class MapHorizontaScrollViewAdapter extends BaseAdapter{
    private Context mContext;
    public MapHorizontaScrollViewAdapter(Context context) {
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode=null;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_myhome_grid,null);
            cyzMode.imageView= (ImageView) convertView.findViewById(R.id.imageView);
            cyzMode.textView= (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(cyzMode);
        }else{
            cyzMode= (CyzMode) convertView.getTag();
        }

//        cyzMode.textView.setText(mTextArray[position]);
//        cyzMode.imageView.setImageResource(mImageArray[position]);
        return convertView;
    }

    private class CyzMode{
        TextView textView;
        ImageView imageView;
    }

}
