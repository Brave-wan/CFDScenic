package com.demo.amusement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.demo.demo.myapplication.R;
import com.demo.my.bean.MyCommentBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：sonnerly on 2016/11/5 0005 13:52
 */
public class ConnentAdpater extends BaseAdapter {

    Context mContext;
    List<String> listBeans;

    public ConnentAdpater(Context mContext, List<String> listBeans) {
        this.mContext = mContext;
        this.listBeans = listBeans;
    }

    @Override
    public int getCount() {
        if (listBeans.size()>5){
            return 5;
        }
        return listBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeans.get(position);
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_image,null);
            cyzMode.imageView= (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(listBeans.get(position),cyzMode.imageView);


        return convertView;
    }

    private class CyzMode{
        ImageView imageView;
    }
}
