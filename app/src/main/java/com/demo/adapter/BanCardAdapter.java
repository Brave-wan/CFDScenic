package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.my.bean.GetBankBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class BanCardAdapter  extends BaseAdapter{
    Context mContext;
    List<GetBankBean.DataBean> list;

    public BanCardAdapter(Context mContext, List<GetBankBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_bank_card,null);
            cyzMode.tv_BankCard= (TextView) convertView.findViewById(R.id.tv_BankCard);
            cyzMode.tv_weihao= (TextView) convertView.findViewById(R.id.tv_weihao);
            cyzMode.tv_type= (TextView) convertView.findViewById(R.id.tv_type);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        cyzMode.tv_BankCard.setText(list.get(position).getBankname());
        String bankCode=list.get(position).getBankcode();
        if (bankCode.length()>4){
            cyzMode.tv_weihao.setText(bankCode.substring(bankCode.length() - 4,bankCode.length()));
        }

        if (list.get(position).getType()==0){
            cyzMode.tv_type.setText("储蓄卡 ");
        }else {
            cyzMode.tv_type.setText("信用卡");
        }

        return convertView;
    }

    private class CyzMode{
        TextView tv_BankCard;
        TextView tv_weihao;
        TextView tv_type;
    }
}
