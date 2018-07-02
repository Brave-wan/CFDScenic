package com.demo.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.my.bean.FdDpFindOrderDetailBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class MyOrderDetailsFdDpAdapter extends BaseAdapter {
    Context mContext;
    List<FdDpFindOrderDetailBean.DataBean> list;

    public MyOrderDetailsFdDpAdapter(Context mContext, List<FdDpFindOrderDetailBean.DataBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_order_fddp, null);
            cyzMode.iv_business = (ImageView) convertView.findViewById(R.id.iv_business);
            cyzMode.tv_businessName = (TextView) convertView.findViewById(R.id.tv_businessName);
            cyzMode.tv_total = (TextView) convertView.findViewById(R.id.tv_total);
            cyzMode.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            cyzMode.iv_xyjt= (ImageView) convertView.findViewById(R.id.iv_xyjt);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }

        cyzMode.iv_xyjt.setVisibility(View.INVISIBLE);
        ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), cyzMode.iv_business);
        cyzMode.tv_businessName.setText(list.get(position).getName());
        cyzMode.tv_total.setText("￥" + list.get(position).getReal_price());
        cyzMode.tv_type.setText(list.get(position).getOrder_describe());
        return convertView;
    }

    private class CyzMode {
        ImageView iv_business;
        TextView tv_businessName;
        TextView tv_total;
        TextView tv_type;
        ImageView iv_xyjt;
    }
}
