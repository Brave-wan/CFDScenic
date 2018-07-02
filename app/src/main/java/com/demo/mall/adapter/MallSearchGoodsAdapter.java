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
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：sonnerly on 2016/9/14 0014 18:29
 */
public class MallSearchGoodsAdapter extends BaseAdapter{
    private Context context;
    List<MallSearchBean.DataBean.ShopGoodsMapBean> goodBeans;

    public MallSearchGoodsAdapter(Context context, List<MallSearchBean.DataBean.ShopGoodsMapBean> goodBeans) {
        this.context = context;
        this.goodBeans = goodBeans;
    }

    @Override
    public int getCount() {
        return goodBeans.size();
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

        BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
        bitmapUtils.display(cyzMode.iv_adp_home_gridview_pic, goodBeans.get(position).getDescribe_img());

        cyzMode.iv_adp_home_gridview_jieshao.setText(goodBeans.get(position).getGoods_name());
        cyzMode.iv_adp_home_gridview_price.setText("￥"+goodBeans.get(position).getNew_price());
        cyzMode.iv_adp_home_gridview_yuexiao.setText("月销"+goodBeans.get(position).getMonthlySales()+"笔");
        if (goodBeans.get(position).getIs_hot()==0){//0否1是
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
