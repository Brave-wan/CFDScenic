package com.demo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.my.activity.Activity_RefundOrder;
import com.demo.demo.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class RefundOrderAdapter extends BaseAdapter {
    private boolean state = false;    //用来判断是否处于编辑状态
    private ArrayList<Boolean> list;    //存放是否选中
    int num = 0;//选中个数
    Context mContext;
    Activity_RefundOrder activity_refundOrder;

    public RefundOrderAdapter(Context mContext) {
        this.mContext = mContext;
        activity_refundOrder = (Activity_RefundOrder) mContext;
    }

    @Override
    public int getCount() {
        return 3;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_refund_order_sp, null);
            cyzMode.tv_upLine= (TextView) convertView.findViewById(R.id.tv_upLine);

            cyzMode.ll_fandian = (LinearLayout) convertView.findViewById(R.id.ll_fandian);
            cyzMode.ll_jiudian = (LinearLayout) convertView.findViewById(R.id.ll_jiudian);
            cyzMode.ll_shangpin = (LinearLayout) convertView.findViewById(R.id.ll_shangpin);
            cyzMode.ll_jiudianyuan = (LinearLayout) convertView.findViewById(R.id.ll_jiudian_yuan);
            cyzMode.ll_fandianyuan = (LinearLayout) convertView.findViewById(R.id.ll_fandian_yuan);
            cyzMode.ll_shangpinyuan = (LinearLayout) convertView.findViewById(R.id.ll_shangpin_yuan);
            cyzMode.iv_fandian = (ImageView) convertView.findViewById(R.id.iv_fandian);
            cyzMode.iv_jiudian = (ImageView) convertView.findViewById(R.id.iv_jiudian);
            cyzMode.iv_shangpin = (ImageView) convertView.findViewById(R.id.iv_shangpin);

            cyzMode.iv_ImageF= (ImageView) convertView.findViewById(R.id.iv_ImageF);
            cyzMode.tv_OrderNumberF= (TextView) convertView.findViewById(R.id.tv_OrderNumberF);
            cyzMode.tv_nameF= (TextView) convertView.findViewById(R.id.tv_nameF);
            cyzMode.tv_typeF= (TextView) convertView.findViewById(R.id.tv_typeF);
            cyzMode.tv_youxiaoqiF= (TextView) convertView.findViewById(R.id.tv_youxiaoqiF);
            cyzMode.tv_TotalF= (TextView) convertView.findViewById(R.id.tv_TotalF);
            cyzMode.tv_tuikuanzhongF= (TextView) convertView.findViewById(R.id.tv_tuikuanzhongF);
            cyzMode.tv_yituikuanF= (TextView) convertView.findViewById(R.id.tv_yituikuanF);

            cyzMode.iv_ImageJ= (ImageView) convertView.findViewById(R.id.iv_ImageJ);
            cyzMode.tv_OrderNumberJ= (TextView) convertView.findViewById(R.id.tv_OrderNumberJ);
            cyzMode.tv_nameJ= (TextView) convertView.findViewById(R.id.tv_nameJ);
            cyzMode.tv_typeJ= (TextView) convertView.findViewById(R.id.tv_typeJ);
            cyzMode.tv_youxiaoqiJ= (TextView) convertView.findViewById(R.id.tv_youxiaoqiJ);
            cyzMode.tv_TotalJ= (TextView) convertView.findViewById(R.id.tv_TotalJ);
            cyzMode.tv_tuikuanzhongJ= (TextView) convertView.findViewById(R.id.tv_tuikuanzhongJ);
            cyzMode.tv_yituikuanJ= (TextView) convertView.findViewById(R.id.tv_yituikuanJ);

            cyzMode.iv_ImageSx= (ImageView) convertView.findViewById(R.id.iv_ImageSx);
            cyzMode.iv_ImageSd= (ImageView) convertView.findViewById(R.id.iv_ImageSd);
            cyzMode.tv_shangjiaName= (TextView) convertView.findViewById(R.id.tv_shangjiaName);
            cyzMode.tv_nameS= (TextView) convertView.findViewById(R.id.tv_nameS);
            cyzMode.tv_present= (TextView) convertView.findViewById(R.id.tv_present);
            cyzMode.tv_primary= (TextView) convertView.findViewById(R.id.tv_primary);
            cyzMode.tv_Distribution= (TextView) convertView.findViewById(R.id.tv_Distribution);
            cyzMode.tv_total= (TextView) convertView.findViewById(R.id.tv_total);
            cyzMode.tv_Number= (TextView) convertView.findViewById(R.id.tv_Number);
            cyzMode.tv_tuikuanzhongS= (TextView) convertView.findViewById(R.id.tv_tuikuanzhongS);
            cyzMode.tv_yituikuanS= (TextView) convertView.findViewById(R.id.tv_yituikuanS);


            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }

        cyzMode.tv_upLine.setVisibility(View.VISIBLE);
        if (position==0)
            cyzMode.tv_upLine.setVisibility(View.GONE);

        //判断是什么类型的，商品，饭店，酒店
        if (position == 0) {
            cyzMode.ll_jiudian.setVisibility(View.GONE);
            cyzMode.ll_fandian.setVisibility(View.GONE);
            cyzMode.ll_shangpin.setVisibility(View.VISIBLE);
        }
        if (position == 1) {
            cyzMode.ll_jiudian.setVisibility(View.VISIBLE);
            cyzMode.ll_fandian.setVisibility(View.GONE);
            cyzMode.ll_shangpin.setVisibility(View.GONE);
        }
        if (position == 2) {
            cyzMode.ll_jiudian.setVisibility(View.GONE);
            cyzMode.ll_fandian.setVisibility(View.VISIBLE);
            cyzMode.ll_shangpin.setVisibility(View.GONE);
        }

        //饭店
        cyzMode.iv_ImageF.setImageResource(R.mipmap.cheshi_shoucang);
        cyzMode.tv_OrderNumberF.setText("订单号："+156489);
        cyzMode.tv_nameF.setText("休闲度假饭庄");
        cyzMode.tv_typeF.setText("双人餐");
        cyzMode.tv_youxiaoqiF.setText("有效期:");
        cyzMode.tv_TotalF.setText("￥");
        //酒店
        cyzMode.iv_ImageJ.setImageResource(R.mipmap.cheshi_shoucang);
        cyzMode.tv_OrderNumberJ.setText("订单号："+12547868);
        cyzMode.tv_nameJ.setText("休闲度假酒店");
        cyzMode.tv_typeJ.setText("双人床");
        cyzMode.tv_youxiaoqiJ.setText("有效期:");
        cyzMode.tv_TotalJ.setText("￥");
        //商品
        cyzMode.iv_ImageSx.setImageResource(R.mipmap.cheshi_shoucang);
        cyzMode.iv_ImageSd.setImageResource(R.mipmap.cheshi_shoucang);
        cyzMode.tv_shangjiaName.setText("商家名称");
        cyzMode.tv_nameS.setText("食品名");
        cyzMode.tv_present.setText("￥150");
        cyzMode.tv_primary.setText("￥200");
        cyzMode.tv_primary.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
        cyzMode.tv_Distribution.setText("配送费：￥"+10.00);
        cyzMode.tv_total.setText("￥"+150);
        cyzMode.tv_Number.setText("X2");
        return convertView;
    }

    private class CyzMode {
        TextView tv_upLine;

        LinearLayout ll_fandian;    //饭店的布局
        LinearLayout ll_jiudian;    //酒店的布局
        LinearLayout ll_shangpin;   //商品的布局
        ImageView iv_fandian;       //选中图片
        ImageView iv_jiudian;
        ImageView iv_shangpin;
        LinearLayout ll_fandianyuan;    //选中图片所在的布局
        LinearLayout ll_shangpinyuan;
        LinearLayout ll_jiudianyuan;

        ImageView iv_ImageF;
        TextView tv_OrderNumberF;
        TextView tv_nameF;
        TextView tv_typeF;
        TextView tv_youxiaoqiF;
        TextView tv_TotalF;
        TextView tv_tuikuanzhongF;
        TextView tv_yituikuanF;

        ImageView iv_ImageJ;
        TextView tv_OrderNumberJ;
        TextView tv_nameJ;
        TextView tv_typeJ;
        TextView tv_youxiaoqiJ;
        TextView tv_TotalJ;
        TextView tv_tuikuanzhongJ;
        TextView tv_yituikuanJ;

        ImageView iv_ImageSx;
        ImageView iv_ImageSd;
        TextView tv_nameS;
        TextView tv_present;
        TextView tv_primary;
        TextView tv_Distribution;
        TextView tv_total;
        TextView tv_Number;
        TextView tv_tuikuanzhongS;
        TextView tv_yituikuanS;
        TextView tv_shangjiaName;
    }

}
