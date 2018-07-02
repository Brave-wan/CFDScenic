package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.my.bean.MyBalanceBean;
import com.demo.demo.myapplication.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class DetailedAdapter extends BaseAdapter {
    Context mContext;
    List<MyBalanceBean.DataBean.TradeLogListBean> data;


    public DetailedAdapter(Context mContext, List<MyBalanceBean.DataBean.TradeLogListBean> data) {
        this.mContext = mContext;
        this.data = data;
        Collections.reverse(data);
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
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_detailed,null);
            cyzMode.tv_BankCard= (TextView) convertView.findViewById(R.id.tv_BankCard);
            cyzMode.tv_tixian= (TextView) convertView.findViewById(R.id.tv_tixian);
            cyzMode.tv_Surplus= (TextView) convertView.findViewById(R.id.tv_Surplus);
            cyzMode.tv_date= (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        cyzMode.tv_BankCard.setText(data.get(position).getName());
        if (data.get(position).getType()==0){
            cyzMode.tv_tixian.setText("+"+data.get(position).getPrice());
        }else {
            cyzMode.tv_tixian.setText("-"+data.get(position).getPrice());
        }

        cyzMode.tv_Surplus.setText("余额："+data.get(position).getBalance());
        cyzMode.tv_date.setText(data.get(position).getCreateTime()+"");


        return convertView;
    }

    private class CyzMode{
        TextView tv_BankCard;
        TextView tv_tixian;
        TextView tv_Surplus;
        TextView tv_date;
    }
}
