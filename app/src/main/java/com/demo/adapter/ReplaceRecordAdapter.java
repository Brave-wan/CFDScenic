package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.my.bean.MyIntegralBean;
import com.demo.demo.myapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class ReplaceRecordAdapter extends BaseAdapter {
    Context mContext;
    List<MyIntegralBean.DataBean.TradeLogListBean> list;

    public ReplaceRecordAdapter(Context mContext, List<MyIntegralBean.DataBean.TradeLogListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_replace_record,null);
            cyzMode.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            cyzMode.tv_Price= (TextView) convertView.findViewById(R.id.tv_Price);
            cyzMode.tv_balance= (TextView) convertView.findViewById(R.id.tv_balance);
            cyzMode.tv_date= (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        String time=list.get(position).getCreateTime().substring(0,10);
        cyzMode.tv_name.setText(list.get(position).getName());
        //支付类型（0(充值，1提现记录) 2 购买商品3积分购买4退款

        if (list.get(position).getType()==0){
            cyzMode.tv_Price.setText("+"+list.get(position).getTrade_integration());
        }else if (list.get(position).getType()==1){
            cyzMode.tv_Price.setText("-"+list.get(position).getTrade_integration());
        }else if (list.get(position).getType()==2){
            cyzMode.tv_Price.setText("+"+list.get(position).getTrade_integration());
        }else if (list.get(position).getType()==3){
            cyzMode.tv_Price.setText("-"+list.get(position).getTrade_integration());
        }

        cyzMode.tv_balance.setText("余额："+list.get(position).getIntegration());
        cyzMode.tv_date.setText(time);


        return convertView;
    }

    private class CyzMode{
        TextView tv_name;
        TextView tv_Price;
        TextView tv_balance;
        TextView tv_date;
    }
}
