package com.demo.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.bean.MallSearchHotBean;

import java.util.List;

/**
 * 作者：sonnerly on 2016/9/14 0014 09:28
 */
public class MallSearchHotAdapter extends BaseAdapter{
    Context context;
    private List<MallSearchHotBean.DataBean> list;

    public MallSearchHotAdapter(Context context, List<MallSearchHotBean.DataBean> list) {
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
        ViewHolaer holder;
        if (convertView==null){
            holder=new ViewHolaer();
            convertView= LayoutInflater.from(context).inflate(R.layout.science_adapter_search,null);
            holder.comment= (TextView) convertView.findViewById(R.id.tv_adp_science_search);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolaer) convertView.getTag();
        }
        holder.comment.setText(list.get(position).getGoods_name());
        return convertView;
    }
    private class ViewHolaer{

        TextView comment;
    }
}
