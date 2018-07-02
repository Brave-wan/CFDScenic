package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.my.bean.WithDrawBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class MyWalletTixianAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Boolean> list;    //存放所有对勾
    List<WithDrawBean.DataBean.BankBean> data;

    int upper=0;//上一次选中项

    public MyWalletTixianAdapter(Context mContext, List<WithDrawBean.DataBean.BankBean> data) {
        this.mContext = mContext;
        this.data = data;
        list=new ArrayList<>();
        for (int i=0;i<data.size();i++){
            Boolean b=new Boolean(false);
            list.add(b);
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_my_wallet_tixian,null);
            cyzMode.tv_Bank= (TextView) convertView.findViewById(R.id.tv_Bank);
            cyzMode.tv_weihao= (TextView) convertView.findViewById(R.id.tv_weihao);
            cyzMode.iv_Bank= (ImageView) convertView.findViewById(R.id.iv_Bank);
            cyzMode.tv_style= (TextView) convertView.findViewById(R.id.tv_style);
            convertView.setTag(cyzMode);
        }else{
            cyzMode= (CyzMode) convertView.getTag();
        }

        if (list.get(position)){
            cyzMode.iv_Bank.setVisibility(View.VISIBLE);
        }else{
            cyzMode.iv_Bank.setVisibility(View.GONE);
        }

        cyzMode.tv_Bank.setText(data.get(position).getBankname());
        String bankCode=data.get(position).getBankcode();
        if (bankCode.length()>4){
            cyzMode.tv_weihao.setText(bankCode.substring(bankCode.length() - 4,bankCode.length()));
        }
        if (data.get(position).getType()==0){
            cyzMode.tv_style.setText("储蓄卡 ");
        }else {
            cyzMode.tv_style.setText("信用卡");
        }

        return convertView;
    }

    public void setImageViewVisibility(int position){
        list.set(upper,false);
        list.set(position, true);
        upper=position;
        notifyDataSetChanged();
    }

    public String getBankName(int position){
        return data.get(position).getBankname();
    }

    private class CyzMode{
        private TextView tv_Bank;
        private TextView tv_weihao;
        private ImageView iv_Bank;
        private TextView tv_style;
    }


}
