package com.demo.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_RefundOrder;
import com.demo.my.bean.RefundOrderFdBean;
import com.demo.my.fragment.MyRefundFdFragment;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
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
 * Created by Administrator on 2016/8/6 0006.
 */
public class MyRefundFdAdapterN extends BaseAdapter {
    Context mContext;
    List<RefundOrderFdBean.DataBean.OrderListBean.RowsBean> list;


    public MyRefundFdAdapterN(Context mContext, List<RefundOrderFdBean.DataBean.OrderListBean.RowsBean> list) {
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
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_refund_order_fdn,null);
            cyzMode.ll_delete= (LinearLayout) convertView.findViewById(R.id.ll_jiudian);
            cyzMode.iv_delete= (ImageView) convertView.findViewById(R.id.iv_jiudian);

            cyzMode.iv_business= (ImageView) convertView.findViewById(R.id.iv_business);
            cyzMode.tv_businessName= (TextView) convertView.findViewById(R.id.tv_businessName);
            cyzMode.tv_type= (TextView) convertView.findViewById(R.id.tv_type);
            cyzMode.tv_youxiaoqi= (TextView) convertView.findViewById(R.id.tv_youxiaoqi);
            cyzMode.tv_Total= (TextView) convertView.findViewById(R.id.tv_Total);

            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }


        ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), cyzMode.iv_business);
        cyzMode.tv_businessName.setText(list.get(position).getInformationName());
        cyzMode.tv_type.setText(list.get(position).getName());


        cyzMode.tv_Total.setText("￥" + list.get(position).getReal_price());


        return convertView;
    }



    private class CyzMode {
        LinearLayout ll_delete;
        ImageView   iv_delete;

        ImageView iv_business;
        TextView tv_businessName;
        TextView tv_type;
        TextView tv_youxiaoqi;
        TextView tv_Total;

    }
}
