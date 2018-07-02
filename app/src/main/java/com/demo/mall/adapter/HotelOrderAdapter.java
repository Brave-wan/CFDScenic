package com.demo.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.activity.HotelSureOrderActivity;
import com.demo.mall.bean.FindHotelDetailBean;

import java.util.List;

/**
 * 作者：sonnerly on 2016/8/9 0009 10:29
 */
public class HotelOrderAdapter extends BaseAdapter{
    private Context context;
    List<FindHotelDetailBean.DataBean.ShopGoodsBean> bean;
    public HotelOrderAdapter(Context context, List<FindHotelDetailBean.DataBean.ShopGoodsBean> bean) {
        this.context = context;
        this.bean = bean;
    }
    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
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
            convertView=LayoutInflater.from(context).inflate(R.layout.mall_adapter_hotelmore,null);
            cyzMode.tv_adp_hotelmore_type= (TextView) convertView.findViewById(R.id.tv_adp_hotelmore_type);
            cyzMode.tv_adp_hotelmore_warn= (TextView) convertView.findViewById(R.id.tv_adp_hotelmore_warn);
            cyzMode.tv_adp_hotelmore_price= (TextView) convertView.findViewById(R.id.tv_adp_hotelmore_price);
            cyzMode.tv_adp_hotelmore_order= (TextView) convertView.findViewById(R.id.tv_adp_hotelmore_order);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }
       // 0可以预定，1不可以预订
        if (bean.get(position).getIsReservation()==0){
            cyzMode.tv_adp_hotelmore_order.setText("预订");
            cyzMode.tv_adp_hotelmore_order.setBackgroundResource(R.mipmap.ordercheng);
        }else if(bean.get(position).getIsReservation()==1){
            cyzMode.tv_adp_hotelmore_order.setText("订完");
            cyzMode.tv_adp_hotelmore_order.setBackgroundResource(R.mipmap.orderhui);
        }

        cyzMode.tv_adp_hotelmore_type.setText(bean.get(position).getGoods_name());
        cyzMode.tv_adp_hotelmore_price.setText("￥"+bean.get(position).getNew_price());
        return convertView;
    }

    private class CyzMode{
        TextView tv_adp_hotelmore_type;
        TextView tv_adp_hotelmore_warn;
        TextView tv_adp_hotelmore_price;
        TextView tv_adp_hotelmore_order;
    }
}
