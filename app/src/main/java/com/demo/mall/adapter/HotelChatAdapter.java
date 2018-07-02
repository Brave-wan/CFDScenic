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
import com.demo.mall.activity.HotelActivity;
import com.demo.mall.activity.HotelMoreActivity;
import com.demo.mall.activity.RestaurantMoreActivity;
import com.demo.mall.bean.FindHotelBean;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：sonnerly on 2016/8/9 0009 17:06
 */
public class HotelChatAdapter extends BaseAdapter{
    private Context mContext;
    List<FindHotelBean.DataBean.RowsBean> list;

    public HotelChatAdapter(Context mContext, List<FindHotelBean.DataBean.RowsBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.mall_adapter_special,null);
            cyzMode.iv_adp_special_img= (ImageView) convertView.findViewById(R.id.iv_adp_special_img);
            cyzMode.tv_adp_special_connent= (TextView) convertView.findViewById(R.id.tv_adp_special_connent);
            cyzMode.tv_adp_special_title= (TextView) convertView.findViewById(R.id.tv_adp_special_title);
            cyzMode.tv_adp_special_price= (TextView) convertView.findViewById(R.id.tv_adp_special_price);
            cyzMode.ll_adp_special_price= (LinearLayout) convertView.findViewById(R.id.ll_adp_special_price);
            cyzMode.ll_adp_special= (LinearLayout) convertView.findViewById(R.id.ll_adp_special);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        cyzMode.ll_adp_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotelActivity activity= (HotelActivity) mContext;
                Intent intent = new Intent(mContext, HotelMoreActivity.class);
                intent.putExtra("id", list.get(position).getId() + "");
                intent.putExtra("start", activity.start);
                intent.putExtra("end", activity.end);
                intent.putExtra("total", activity.total);
                intent.putExtra("image", list.get(position).getBackgroud_img() + "");
               mContext.startActivity(intent);
            }
        });
        cyzMode.ll_adp_special_price.setVisibility(View.VISIBLE);

        ImageLoader.getInstance().displayImage(list.get(position).getBackgroud_img(), cyzMode.iv_adp_special_img);

        cyzMode.tv_adp_special_title.setText(list.get(position).getName());
        cyzMode.tv_adp_special_connent.setText(list.get(position).getContent());
        cyzMode.tv_adp_special_price.setText("￥"+list.get(position).getConsumption());

        return convertView;
    }


    private class CyzMode{
        ImageView iv_adp_special_img;
        TextView tv_adp_special_title;
        TextView tv_adp_special_connent;
        TextView tv_adp_special_price;
        LinearLayout ll_adp_special_price;
        LinearLayout ll_adp_special;
    }
}
