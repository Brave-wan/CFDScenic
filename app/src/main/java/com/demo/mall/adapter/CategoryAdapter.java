package com.demo.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.bean.GetTypeBean;

import java.util.List;

/**
 * 作者：宋玉磊 on 2016/8/5 0005 19:40
 */
public class CategoryAdapter extends BaseAdapter{
    private Context context;
    List<GetTypeBean.DataBean> bean;


    public CategoryAdapter(Context context, List<GetTypeBean.DataBean> bean) {
        this.bean = bean;
        this.context = context;
//        this.type = type;
    }

    @Override
    public int getCount() {
        return bean.size();
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
            convertView=LayoutInflater.from(context).inflate(R.layout.mall_adapter_category,null);
            cyzMode.tv_adp_mall_fenlei= (TextView) convertView.findViewById(R.id.tv_adp_mall_fenlei);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }


            cyzMode.tv_adp_mall_fenlei.setText(bean.get(position).getName());

//            cyzMode.tv_adp_mall_fenlei.setVisibility();

        return convertView;
    }


    private class CyzMode{
        TextView tv_adp_mall_fenlei;
    }

}
