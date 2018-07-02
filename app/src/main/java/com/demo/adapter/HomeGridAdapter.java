package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class HomeGridAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mImageArray=new int[]{R.mipmap.tubiao1_jingdian,R.mipmap.tubiao2_huodong,R.mipmap.tubiao3_xuniyou,R.mipmap.tubiao4_techan
            ,R.mipmap.tubiao5_qianbao,R.mipmap.tubiao6_daoyou,R.mipmap.tubiao7_zhoubian,R.mipmap.tubiao8_goupiao,R.mipmap.tubiao9_guihua,R.mipmap.tubiao10_dongtai};
    private String[] mTextArray=new String[]{"特色景点","景区活动","虚拟游","商城特产","我的钱包","智能导游","查看周边","在线购票","路线规划","实时动态"};

    public HomeGridAdapter(Context context) {
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
        return position;
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
