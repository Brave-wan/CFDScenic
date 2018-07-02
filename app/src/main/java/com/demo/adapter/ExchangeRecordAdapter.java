package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.my.bean.SelectDealMessageBean;
import com.demo.demo.myapplication.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class ExchangeRecordAdapter extends BaseAdapter {
    Context mContext;
    List<SelectDealMessageBean.DataBean.ListBean> rowsBean;

    public ExchangeRecordAdapter(Context mContext, List<SelectDealMessageBean.DataBean.ListBean> rowsBean) {
        this.mContext = mContext;
        this.rowsBean = rowsBean;
    }

    @Override
    public int getCount() {
        return rowsBean.size();
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_exchange_record,null);
            cyzMode.ll_ticket= (LinearLayout) convertView.findViewById(R.id.ll_Record_ticket);
            cyzMode.ll_Keepsake= (LinearLayout) convertView.findViewById(R.id.ll_Record_Keepsake);

            cyzMode.iv_ImageJ= (ImageView) convertView.findViewById(R.id.iv_ImageJ);
            cyzMode.iv_ImageM= (ImageView) convertView.findViewById(R.id.iv_ImageM);
            cyzMode.tv_nameM= (TextView) convertView.findViewById(R.id.tv_nameM);
            cyzMode.tv_integralM= (TextView) convertView.findViewById(R.id.tv_integralM);
            cyzMode.tv_integralJ= (TextView) convertView.findViewById(R.id.tv_integralJ);
            cyzMode.tv_nameJ= (TextView) convertView.findViewById(R.id.tv_nameJ);
            cyzMode.tv_integralJ= (TextView) convertView.findViewById(R.id.tv_integralJ);
            cyzMode.tv_youxiaoqi= (TextView) convertView.findViewById(R.id.tv_youxiaoqi);

            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        //判断
        BitmapUtils bitmapUtils = new BitmapUtils(mContext);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);//加载失败
        bitmapUtils.configDefaultLoadingImage(R.mipmap.zhanwei);//加载过程中
        if (rowsBean.get(position).getType()==4){
            cyzMode.ll_ticket.setVisibility(View.VISIBLE);
            cyzMode.ll_Keepsake.setVisibility(View.GONE);
            bitmapUtils.display(cyzMode.iv_ImageM, rowsBean.get(position).getHead_img());
            cyzMode.tv_nameM.setText(rowsBean.get(position).getName());
            cyzMode.tv_integralM.setText(rowsBean.get(position).getIntegral()+"积分");
            String start=rowsBean.get(position).getStart_valid().substring(0,10);
            String end=rowsBean.get(position).getEnd_valid().substring(0,10);
            cyzMode.tv_youxiaoqi.setText("有效期：" + start + "--" + end);
        }else if (rowsBean.get(position).getType()==5){
            cyzMode.ll_Keepsake.setVisibility(View.VISIBLE);
            cyzMode.ll_ticket.setVisibility(View.GONE);
            bitmapUtils.display(cyzMode.iv_ImageJ, rowsBean.get(position).getHead_img());
            cyzMode.tv_nameJ.setText(rowsBean.get(position).getName());
            cyzMode.tv_integralJ.setText(rowsBean.get(position).getIntegral()+"积分");
        }
        return convertView;
    }

    private class CyzMode{
        LinearLayout ll_Keepsake;
        LinearLayout ll_ticket;
        ImageView iv_ImageM;
        TextView tv_nameM;
        TextView tv_integralM;
        TextView tv_youxiaoqi;

        ImageView iv_ImageJ;
        TextView tv_nameJ;
        TextView tv_integralJ;
    }
}
