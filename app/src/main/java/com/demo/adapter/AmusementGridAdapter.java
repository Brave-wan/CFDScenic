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
 * Created by Administrator on 2016/7/26 0026.
 */
public class AmusementGridAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mImageArray=new int[]{R.mipmap.ylq_tubiao1,R.mipmap.ylq_tubiao2,R.mipmap.ylq_tubiao3,R.mipmap.ylq_tubiao4};
    private String[] mTextArray1=new String[]{"景区活动","视频攻略","精彩游记","必游清单"};
    private String[] mTextArray2=new String[]{"经济实惠等你来","更直观感受美景","透过文字美图看我们","精彩景点推荐"};

    public AmusementGridAdapter(Context context) {
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
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_amusement_gird,null);
            cyzMode.tv_1= (TextView) convertView.findViewById(R.id.tv_shang);
            cyzMode.tv_2= (TextView) convertView.findViewById(R.id.tv_xia);
            cyzMode.imageView= (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(cyzMode);
        }else{
            cyzMode= (CyzMode) convertView.getTag();
        }

        cyzMode.tv_1.setText(mTextArray1[position]);
        cyzMode.tv_2.setText(mTextArray2[position]);
        cyzMode.imageView.setImageResource(mImageArray[position]);
        return convertView;
    }

    private class CyzMode {
        TextView tv_1;
        TextView tv_2;
        ImageView imageView;
    }
}
