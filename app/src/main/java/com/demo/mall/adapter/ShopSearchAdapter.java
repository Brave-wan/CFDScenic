package com.demo.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.bean.FindFeatureShopBean;

import java.util.List;

/**
 * 作者：sonnerly on 2016/10/17 0017 16:54
 */
public class ShopSearchAdapter extends BaseAdapter {
    Context context;
    List<FindFeatureShopBean.DataBean> list;

    public ShopSearchAdapter(Context context, List<FindFeatureShopBean.DataBean> list) {
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
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(context).inflate(R.layout.science_adapter_search, null);
            cyzMode.comment = (TextView) convertView.findViewById(R.id.tv_adp_science_search);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }
        cyzMode.comment.setText(list.get(position).getGoods_name());
        return convertView;
    }

    private class CyzMode{
        TextView comment;
    }

}
