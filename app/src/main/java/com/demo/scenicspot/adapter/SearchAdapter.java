package com.demo.scenicspot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.scenicspot.bean.ScenicSpotListBean;

import java.util.List;

/**
 * 作者：宋玉磊 on 2016/8/3 0003 09:55
 */
public class SearchAdapter extends BaseAdapter{
    Context context;
    private List<ScenicSpotListBean.DataBean> list;

    public SearchAdapter(Context context, List<ScenicSpotListBean.DataBean> list) {
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
        convertView=LayoutInflater.from(context).inflate(R.layout.science_adapter_search,null);
        TextView tv_adp_science_search= (TextView) convertView.findViewById(R.id.tv_adp_science_search);
        tv_adp_science_search.setText(list.get(position).getName());
        return convertView;
    }


}
