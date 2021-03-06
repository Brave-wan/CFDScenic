package com.demo.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.bean.FindFeatureShopBean;
import com.demo.mall.bean.SelectRecommendBean;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class TcHomeGridViewAdapter  extends BaseAdapter{
    Context context;
    List<SelectRecommendBean.DataBean> list;

    public TcHomeGridViewAdapter(Context context, List<SelectRecommendBean.DataBean> list) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.adpater_home_gridview,null);
            cyzMode.iv_adp_home_gridview_pic= (ImageView) convertView.findViewById(R.id.iv_adp_home_gridview_pic);
            cyzMode.iv_adp_home_gridview_jieshao= (TextView) convertView.findViewById(R.id.iv_adp_home_gridview_jieshao);
            cyzMode.iv_adp_home_gridview_price= (TextView) convertView.findViewById(R.id.iv_adp_home_gridview_price);
            cyzMode.iv_adp_home_gridview_yuexiao= (TextView) convertView.findViewById(R.id.iv_adp_home_gridview_yuexiao);
            cyzMode.iv_adp_home_gridview_rexiao= (TextView) convertView.findViewById(R.id.iv_adp_home_gridview_rexiao);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), cyzMode.iv_adp_home_gridview_pic);

        cyzMode.iv_adp_home_gridview_jieshao.setText(list.get(position).getGoods_name());
        cyzMode.iv_adp_home_gridview_price.setText("￥"+list.get(position).getNew_price());
        cyzMode.iv_adp_home_gridview_yuexiao.setText("月销"+list.get(position).getMonthlySales()+"笔");
        if (list.get(position).getIs_hot()==0){//0 否 1是
            cyzMode.iv_adp_home_gridview_rexiao.setVisibility(View.GONE);
        }else {
            cyzMode.iv_adp_home_gridview_rexiao.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private class CyzMode{
        ImageView iv_adp_home_gridview_pic;
        TextView iv_adp_home_gridview_jieshao;
        TextView iv_adp_home_gridview_price;
        TextView iv_adp_home_gridview_yuexiao;
        TextView iv_adp_home_gridview_rexiao;
    }
}
