package com.demo.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.activity.PackageMoreActivity;
import com.demo.mall.bean.FindAllRestaurantDetailBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：sonnerly on 2016/8/8 0008 19:07
 */
public class PackageAdapter extends BaseAdapter{
    private Context context;
    List<FindAllRestaurantDetailBean.DataBean.PackageListBean> list;

    public PackageAdapter(Context context, List<FindAllRestaurantDetailBean.DataBean.PackageListBean> list) {
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
       /* View view = mInflater.inflate(R.layout.mall_adapter_package,null);
        RelativeLayout rl_adp_pack= (RelativeLayout) view.findViewById(R.id.rl_adp_pack);
        rl_adp_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PackageMoreActivity.class);
                context.startActivity(intent);
            }
        });*/

        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView=LayoutInflater.from(context).inflate(R.layout.mall_adapter_package,null);
            cyzMode.iv_adp_pack_img= (ImageView) convertView.findViewById(R.id.iv_adp_pack_img);
            cyzMode.iv_adp_pack_title= (TextView) convertView.findViewById(R.id.iv_adp_pack_title);
            cyzMode.iv_adp_pack_price= (TextView) convertView.findViewById(R.id.iv_adp_pack_price);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getHead_img(), cyzMode.iv_adp_pack_img);
        cyzMode.iv_adp_pack_title.setText(list.get(position).getName());
        cyzMode.iv_adp_pack_price.setText("￥"+list.get(position).getNew_price());
        return convertView;
    }

    private class CyzMode{
        ImageView iv_adp_pack_img;
        TextView iv_adp_pack_title;
        TextView iv_adp_pack_price;
    }
}
