package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class ScenicGridAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mImageArray=new int[]{R.mipmap.jq_tubiao1,R.mipmap.jq_tubiao2,R.mipmap.jq_tubiao3,R.mipmap.jq_tubiao4};
    private String[] mTextArray1=new String[]{"景点纵览","新闻•须知","路线规划","湿地简介"};
    private String[] mTextArray2=new String[]{"众多景点任你挑选","景区新闻早知道","景区路线规划","景区简介"};

    public ScenicGridAdapter(Context context) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_scenic_gird,null);
            cyzMode.ll_science= (LinearLayout) convertView.findViewById(R.id.ll_frag_science);
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
        LinearLayout ll_science;

    }
}
