package com.demo.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_RefundOrder;
import com.demo.my.bean.RefundOrderJdBean;
import com.demo.my.fragment.MyOrderJdFragment;
import com.demo.my.fragment.MyRefundJdFragment;
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

import org.json.JSONObject;

import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_OrderDetailsJd;
import com.demo.demo.myapplication.R;
import com.demo.view.DialogRefund;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class MyRefundJdAdapter extends BaseAdapter {
    Context mContext;
    List<RefundOrderJdBean.DataBean.OrderListBean.RowsBean> list;

    public MyRefundJdAdapter(Context mContext,     List<RefundOrderJdBean.DataBean.OrderListBean.RowsBean> list) {
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
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_refund_order_jd,null);
            cyzMode.ll_delete= (LinearLayout) convertView.findViewById(R.id.ll_jiudian);
            cyzMode.iv_delete= (ImageView) convertView.findViewById(R.id.iv_jiudian);

            cyzMode.iv_nameImage= (ImageView) convertView.findViewById(R.id.iv_nameImage);
            cyzMode.tv_OrderNumber= (TextView) convertView.findViewById(R.id.tv_OrderNumber);
            cyzMode.tv_type= (TextView) convertView.findViewById(R.id.tv_type);
            cyzMode.tv_youxiaoqi= (TextView) convertView.findViewById(R.id.tv_youxiaoqi);
            cyzMode.tv_total= (TextView) convertView.findViewById(R.id.tv_Total);
            cyzMode.tv_name= (TextView) convertView.findViewById(R.id.tv_name);

            cyzMode.tv_tuikuanzhong= (TextView) convertView.findViewById(R.id.tv_tuikuanzhong);
            cyzMode.tv_yituikuan= (TextView) convertView.findViewById(R.id.tv_yituikuan);
            cyzMode.tv_shanchu= (TextView) convertView.findViewById(R.id.tv_shanchu);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        initView(cyzMode);

        ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), cyzMode.iv_nameImage);
        //cyzMode.iv_nameImage.setImageResource(R.mipmap.cheshi_shoucang);
        cyzMode.tv_OrderNumber.setText("订单号："+list.get(position).getOrder_code());

        if (list.get(position).getOrder_state()==3){
            cyzMode.tv_tuikuanzhong.setVisibility(View.VISIBLE);
        }else if (list.get(position).getOrder_state()==6){
            cyzMode.tv_yituikuan.setVisibility(View.VISIBLE);
            cyzMode.tv_shanchu.setVisibility(View.VISIBLE);
        }


        cyzMode.tv_type.setText(list.get(position).getGoodsName());
        cyzMode.tv_youxiaoqi.setText("有效期：" + list.get(position).getEnd_date().substring(0, 10));
        cyzMode.tv_total.setText("￥" + list.get(position).getReal_price());
        cyzMode.tv_name.setText(list.get(position).getName());

        cyzMode.tv_shanchu.setOnClickListener(new SetOnClick(position,cyzMode));
        return convertView;
    }



    private class SetOnClick implements View.OnClickListener{
        int position;
        CyzMode cyzMode;

        public SetOnClick(int position, CyzMode cyzMode) {
            this.position = position;
            this.cyzMode = cyzMode;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_shanchu:
                    DialogMyOrderDelete dialogMyOrderDelete=new DialogMyOrderDelete(mContext,R.style.AlertDialogStyle,6,position,0);
                    dialogMyOrderDelete.show();
                    break;
            }
        }
    }
    //删除订单
    public void deleteMyTickets(final int position){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("type", 1 + "");
        params.addQueryStringParameter("orderState",list.get(position).getOrder_state()+"");
        params.addQueryStringParameter("orderCode",list.get(position).getOrder_code()+"");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.deleteMyOrder, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int status=header.getInt("status");
                            if(status==0){
                                MyRefundJdFragment myRefundJdFragment= (MyRefundJdFragment) Activity_RefundOrder.jd_1;
                                myRefundJdFragment.mPage=1;
                                myRefundJdFragment.list.clear();
                                myRefundJdFragment.refundOrder();
                                ToastUtil.show(mContext, "删除成功");
                            }else if( status== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            }else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext,"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
    }

    private void initView(CyzMode cyzMode){
        cyzMode.tv_tuikuanzhong.setVisibility(View.GONE);
        cyzMode.tv_yituikuan.setVisibility(View.GONE);
        cyzMode.tv_shanchu.setVisibility(View.GONE);
    }

    private class CyzMode {
        TextView tv_tuikuanzhong;
        TextView tv_yituikuan;

        LinearLayout ll_delete;
        ImageView   iv_delete;

        ImageView   iv_nameImage;
        TextView tv_OrderNumber;
        TextView tv_type;
        TextView tv_youxiaoqi;
        TextView tv_total;
        TextView tv_name;
        TextView tv_shanchu;
    }
}
