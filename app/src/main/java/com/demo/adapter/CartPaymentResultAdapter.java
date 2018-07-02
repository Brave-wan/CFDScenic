package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.demo.myapplication.R;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class CartPaymentResultAdapter extends BaseAdapter {
    Context mContext;

    public CartPaymentResultAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 3;
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
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_payment_result_cart,null);
            cyzMode.tv_Title= (TextView) convertView.findViewById(R.id.tv_Title);
            cyzMode.tv_number= (TextView) convertView.findViewById(R.id.tv_number);
            cyzMode.tv_Establish= (TextView) convertView.findViewById(R.id.tv_Establish);
            cyzMode.tv_payment= (TextView) convertView.findViewById(R.id.tv_payment);
            convertView.setTag(cyzMode);
        }else{
            cyzMode= (CyzMode) convertView.getTag();
        }

        return convertView;
    }

   private  class CyzMode{
        TextView tv_Title;
        TextView tv_number;
        TextView tv_Establish;
        TextView tv_payment;

    }
}
