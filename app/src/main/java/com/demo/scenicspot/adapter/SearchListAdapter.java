package com.demo.scenicspot.adapter;

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
import com.demo.scenicspot.activity.ScienceMoreActivity;
import com.demo.scenicspot.bean.ObscureSelectBean;
import com.demo.scenicspot.bean.ScenicSpotListBean;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class SearchListAdapter extends BaseAdapter {
    Context context;
    Intent intent = new Intent();
    List<ObscureSelectBean.DataBean> list;

    public SearchListAdapter(Context context, List<ObscureSelectBean.DataBean> list) {
        this.context = context;
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
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_home_listview, null);
            cyzMode.iv_adp_home_pic = (ImageView) convertView.findViewById(R.id.iv_adp_home_pic);
            cyzMode.tv_adp_home_english = (TextView) convertView.findViewById(R.id.tv_adp_home_english);
            cyzMode.tv_adp_home_chinese = (TextView) convertView.findViewById(R.id.tv_adp_home_chinese);
            cyzMode.tv_adp_home_content = (TextView) convertView.findViewById(R.id.tv_adp_home_content);
            cyzMode.tv_adp_home_price = (TextView) convertView.findViewById(R.id.tv_adp_home_price);
            cyzMode.ll = (LinearLayout) convertView.findViewById(R.id.ll_adp_home_science);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }

        BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
        bitmapUtils.display(cyzMode.iv_adp_home_pic, list.get(position).getHead_img());

        cyzMode.tv_adp_home_english.setText(list.get(position).getName_en());
        cyzMode.tv_adp_home_chinese.setText(list.get(position).getName());
        cyzMode.tv_adp_home_content.setText(list.get(position).getVisitors_describe());
        cyzMode.tv_adp_home_price.setText("￥" + list.get(position).getNew_price() + "");

        /**
         * 找到item布局文件中对应的控件
         */
        cyzMode.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(context, ScienceMoreActivity.class);
                intent.putExtra("id", list.get(position).getId() + "");
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class CyzMode {
        ImageView iv_adp_home_pic;
        TextView tv_adp_home_english;
        TextView tv_adp_home_chinese;
        TextView tv_adp_home_content;
        TextView tv_adp_home_price;
        LinearLayout ll;
    }
}