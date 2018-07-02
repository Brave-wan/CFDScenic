package com.demo.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_ApplyRefund;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_OrderPaymentOrder;
import com.demo.my.bean.SpFindOrderBean;
import com.demo.my.fragment.MyOrderSpFragment;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogMyCancelOrder;
import com.demo.view.DialogMyOrderCollect;
import com.demo.view.DialogMyOrderComplete;
import com.demo.view.DialogMyOrderDelete;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24 0024.
 */
public class MyOrderSpAdapterLv2 extends BaseAdapter {
    Context mContext;
    List<SpFindOrderBean.DataBean.OrderListBean.RowsBean> list;


    public MyOrderSpAdapterLv2(Context mContext, List<SpFindOrderBean.DataBean.OrderListBean.RowsBean> list) {
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
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_my_order_sp_zhong,null);
            cyzMode.ll_all= (LinearLayout) convertView.findViewById(R.id.ll_all);

            cyzMode.iv_commodity= (ImageView) convertView.findViewById(R.id.iv_commodity);
            cyzMode.tv_commodityName= (TextView) convertView.findViewById(R.id.tv_commodityName);
            cyzMode.tv_present= (TextView) convertView.findViewById(R.id.tv_present);
            cyzMode.tv_primary= (TextView) convertView.findViewById(R.id.tv_primary);
            cyzMode.tv_Distribution= (TextView) convertView.findViewById(R.id.tv_Distribution);
            cyzMode.tv_total= (TextView) convertView.findViewById(R.id.tv_total);
            cyzMode.tv_Number= (TextView) convertView.findViewById(R.id.tv_Number);
            cyzMode.tv_querenshouhuo= (TextView) convertView.findViewById(R.id.tv_querenshouhuo);
            cyzMode.tv_fahuozhong= (TextView) convertView.findViewById(R.id.tv_fahuozhong);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), cyzMode.iv_commodity);
        cyzMode.tv_commodityName.setText(list.get(position).getGoodsName());

        cyzMode.tv_total.setText(list.get(position).getReal_price()+"");

        cyzMode.tv_Number.setText("X"+list.get(position).getQuantity());
        cyzMode.tv_present.setText("￥"+list.get(position).getNew_price());
        cyzMode.tv_primary.setText("￥" + list.get(position).getPrice());
        cyzMode.tv_primary.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        cyzMode.tv_Distribution.setText("配送费："+list.get(position).getDeliver_fee());

        //判断is_deliver_fee
        if (list.get(position).getIs_deliver_fee()==1){
            cyzMode.ll_all.setVisibility(View.GONE);
        }else {
            cyzMode.ll_all.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    private class CyzMode{
        LinearLayout ll_all;

        ImageView iv_commodity;
        TextView tv_commodityName;
        TextView tv_present;
        TextView tv_primary;
        TextView tv_Distribution;
        TextView tv_total;
        TextView tv_Number;
        TextView tv_querenshouhuo;
        TextView tv_fahuozhong;
    }
}
