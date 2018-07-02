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
import com.demo.mall.activity.ShopActivity;
import com.demo.mall.bean.FindFeatureBean;
import com.demo.mall.bean.MallSearchBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：sonnerly on 2016/9/14 0014 18:50
 */
public class MallSearchUserAdapter extends BaseAdapter{
    private Context context;
    List<MallSearchBean.DataBean.ShopUserMapBean> mapBeans;

    public MallSearchUserAdapter(Context context, List<MallSearchBean.DataBean.ShopUserMapBean> mapBeans) {
        this.context = context;
        this.mapBeans = mapBeans;
    }

    @Override
    public int getCount() {
        return mapBeans.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode = null;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(context).inflate(R.layout.mall_adapter_special,null);
            cyzMode.ll_adp_special= (LinearLayout) convertView.findViewById(R.id.ll_adp_special);
            cyzMode.iv_adp_special_img= (ImageView) convertView.findViewById(R.id.iv_adp_special_img);
            cyzMode.tv_adp_special_connent= (TextView) convertView.findViewById(R.id.tv_adp_special_connent);
            cyzMode.tv_adp_special_title= (TextView) convertView.findViewById(R.id.tv_adp_special_title);
            cyzMode.ll_adp_special_price= (LinearLayout) convertView.findViewById(R.id.ll_adp_special_price);
            cyzMode.tv_adp_special_price= (TextView) convertView.findViewById(R.id.tv_adp_special_price);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(mapBeans.get(position).getBackgroudImg(), cyzMode.iv_adp_special_img);
        cyzMode.tv_adp_special_title.setText(mapBeans.get(position).getName());
        cyzMode.tv_adp_special_connent.setText(mapBeans.get(position).getContent());

        if(mapBeans.get(position).getShopGroupId()==3||mapBeans.get(position).getShopGroupId()==4){
            cyzMode.ll_adp_special_price.setVisibility(View.GONE);
        }else{
            cyzMode.ll_adp_special_price.setVisibility(View.VISIBLE);
            cyzMode.tv_adp_special_price.setText("￥"+mapBeans.get(position).getConsumption());
        }
//        cyzMode.ll_adp_special.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ShopActivity.class);
//                intent.putExtra("id",rowsBeans.get(position).getId()+"");
//                intent.putExtra("name",rowsBeans.get(position).getName());
//                intent.putExtra("ImageUrl",rowsBeans.get(position).getHead_img());
//                context.startActivity(intent);
//            }
//        });

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
