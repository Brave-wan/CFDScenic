package com.demo.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.activity.RestaurantMoreActivity;
import com.demo.mall.activity.ShopActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：sonnerly on 2016/8/9 0009 10:45
 */
public class RestaurantChatAdapter extends BaseAdapter{
    private Context context;
    List<FindAllRestaurantBean.DataBean.RowsBean> list;

    public RestaurantChatAdapter(Context context, List<FindAllRestaurantBean.DataBean.RowsBean> list) {
        this.context = context;
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
            convertView=LayoutInflater.from(context).inflate(R.layout.mall_adapter_special, null);
            cyzMode.ll_adp_special= (LinearLayout) convertView.findViewById(R.id.ll_adp_special);
            cyzMode.ll_adp_special_price= (LinearLayout) convertView.findViewById(R.id.ll_adp_special_price);
            cyzMode.tv_adp_special_people= (TextView) convertView.findViewById(R.id.tv_adp_special_people);
            cyzMode.iv_adp_special_img= (ImageView) convertView.findViewById(R.id.iv_adp_special_img);
            cyzMode.tv_adp_special_title= (TextView) convertView.findViewById(R.id.tv_adp_special_title);
            cyzMode.tv_adp_special_connent= (TextView) convertView.findViewById(R.id.tv_adp_special_connent);
            cyzMode.tv_adp_special_price= (TextView) convertView.findViewById(R.id.tv_adp_special_price);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }
        cyzMode.ll_adp_special_price.setVisibility(View.VISIBLE);
        cyzMode.tv_adp_special_people.setText("/人");

        ImageLoader.getInstance().displayImage(list.get(position).getBackgroud_img(), cyzMode.iv_adp_special_img);
        cyzMode.tv_adp_special_title.setText(list.get(position).getName());
        cyzMode.tv_adp_special_connent.setText(list.get(position).getContent());
        cyzMode.tv_adp_special_price.setText("￥"+list.get(position).getConsumption()+"");
        return convertView;
    }

    private class CyzMode{
        LinearLayout ll_adp_special;
        LinearLayout ll_adp_special_price;
        TextView tv_adp_special_people;
        ImageView iv_adp_special_img;
        TextView tv_adp_special_price;
        TextView tv_adp_special_title;
        TextView tv_adp_special_connent;
    }
}
