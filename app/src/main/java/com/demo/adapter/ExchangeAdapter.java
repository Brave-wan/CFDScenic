package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.my.bean.IntegralShopGoodsBean;
import com.demo.demo.myapplication.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class ExchangeAdapter extends BaseAdapter {
    Context mContext;
    List<IntegralShopGoodsBean.DataBean.GoodsListBean> list;

    public ExchangeAdapter(Context mContext, List<IntegralShopGoodsBean.DataBean.GoodsListBean> list) {
        this.mContext = mContext;
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_exchange,null);
            cyzMode.tv_exchange= (TextView) convertView.findViewById(R.id.tv_exchange);
            cyzMode.tv_Title= (TextView) convertView.findViewById(R.id.tv_Title);
            cyzMode.tv_integral= (TextView) convertView.findViewById(R.id.tv_integral);
            cyzMode.iv_exchange= (ImageView) convertView.findViewById(R.id.iv_exchange);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        //判断是门票还是纪念品
        if (list.get(position).getType()==5){
            cyzMode.tv_exchange.setText("门票");
        }else if (list.get(position).getType()==4){
            cyzMode.tv_exchange.setText("纪念品");
        }
        BitmapUtils bitmapUtils = new BitmapUtils(mContext);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);//加载失败
        bitmapUtils.configDefaultLoadingImage(R.mipmap.zhanwei);//加载过程中
        bitmapUtils.display(cyzMode.iv_exchange, list.get(position).getHead_img());
        cyzMode.tv_Title.setText(list.get(position).getName());
        cyzMode.tv_integral.setText(list.get(position).getIntegral()+"积分");


        return convertView;
    }

    private class CyzMode{
        TextView tv_exchange;
        TextView tv_integral;//积分;
        TextView tv_Title;//标题
        ImageView iv_exchange;
    }
}
