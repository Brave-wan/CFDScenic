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
 * 作者：宋玉磊 on 2016/8/2 0002 19:17
 */
public class ArroundAdapter extends BaseAdapter{
    private Context mContext;
    private int[] mImageArray=new int[]{R.mipmap.jingdian,R.mipmap.jiudian,R.mipmap.fandian,
            R.mipmap.xiaochi,R.mipmap.techan,R.mipmap.xishoujian,R.mipmap.tingchechang,R.mipmap.chongzhi_cheng};
    private String[] mTextArray=new String[]{"景点","酒店","饭店","小吃","特产","洗手间","停车场","充值"};
    public ArroundAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mImageArray.length;
    }

    @Override
    public Object getItem(int position) {
        return mImageArray[position];
    }

    @Override
    public long getItemId(int position) {
        return  position;
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

        cyzMode.textView.setText(mTextArray[position]);
        cyzMode.imageView.setImageResource(mImageArray[position]);
        return convertView;
    }

    private class CyzMode{
        TextView textView;
        ImageView imageView;
    }

}
