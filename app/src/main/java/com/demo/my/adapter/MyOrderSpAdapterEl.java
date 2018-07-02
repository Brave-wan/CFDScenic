package com.demo.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.demo.my.fragment.MyOrderJdFragment;
import com.demo.my.fragment.MyOrderSpFragment;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogCancelOrder;
import com.demo.view.DialogMyCancelOrder;
import com.demo.view.DialogMyOrderCollect;
import com.demo.view.DialogMyOrderComplete;
import com.demo.view.DialogMyOrderDelete;
import com.demo.view.DialogRefund;
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
public class MyOrderSpAdapterEl extends BaseExpandableListAdapter {
    Context mContext;
    List<List<SpFindOrderBean.DataBean.OrderListBean.RowsBean>> list;

    public MyOrderSpAdapterEl(Context mContext, List<List<SpFindOrderBean.DataBean.OrderListBean.RowsBean>> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_my_order_shang,null);
            cyzMode.iv_business= (ImageView) convertView.findViewById(R.id.iv_business);
            cyzMode.tv_businessName= (TextView) convertView.findViewById(R.id.tv_businessName);
            cyzMode.tv_code= (TextView) convertView.findViewById(R.id.tv_code);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        cyzMode.tv_businessName.setText(list.get(groupPosition).get(0).getShopName());
        cyzMode.tv_code.setText(list.get(groupPosition).get(0).getOrder_code());
        ImageLoader.getInstance().displayImage(list.get(groupPosition).get(0).getHead_img(), cyzMode.iv_business);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_my_order_xia,null);
            cyzMode.ll_state= (LinearLayout) convertView.findViewById(R.id.ll_state);
            cyzMode.tv_shenqing= (TextView) convertView.findViewById(R.id.tv_shenqing);
            cyzMode.tv_quxiao= (TextView) convertView.findViewById(R.id.tv_quxiao);
            cyzMode.tv_quzhifu= (TextView) convertView.findViewById(R.id.tv_quzhifu);
            cyzMode.tv_querenwancheng= (TextView) convertView.findViewById(R.id.tv_querenwancheng);
            cyzMode.tv_jiaoyiwancheng= (TextView) convertView.findViewById(R.id.tv_jiaoyiwancheng);
            cyzMode.ll_delete= (LinearLayout) convertView.findViewById(R.id.ll_jiudian);
            cyzMode.iv_delete= (ImageView) convertView.findViewById(R.id.iv_jiudian);
            cyzMode.iv_commodity= (ImageView) convertView.findViewById(R.id.iv_commodity);
            cyzMode.tv_commodityName= (TextView) convertView.findViewById(R.id.tv_commodityName);
            cyzMode.tv_present= (TextView) convertView.findViewById(R.id.tv_present);
            cyzMode.tv_primary= (TextView) convertView.findViewById(R.id.tv_primary);
            cyzMode.tv_Distribution= (TextView) convertView.findViewById(R.id.tv_Distribution);
            cyzMode.tv_total= (TextView) convertView.findViewById(R.id.tv_total);
            cyzMode.tv_Number= (TextView) convertView.findViewById(R.id.tv_Number);
            cyzMode.tv_querenshouhuo= (TextView) convertView.findViewById(R.id.tv_querenshouhuo);
            cyzMode.tv_fahuozhong= (TextView) convertView.findViewById(R.id.tv_fahuozhong);
            cyzMode.ll_context= (LinearLayout) convertView.findViewById(R.id.ll_context);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        initView(cyzMode);
        //状态值判断
        if (list.get(groupPosition).get(childPosition).getOrder_state()==1){
            cyzMode.tv_quzhifu.setVisibility(View.VISIBLE);
            cyzMode.tv_quxiao.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==2){
            cyzMode.tv_fahuozhong.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==3){
            cyzMode.tv_querenshouhuo.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==4){
            cyzMode.tv_shenqing.setVisibility(View.VISIBLE);
            cyzMode.tv_querenwancheng.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==5){
            cyzMode.tv_jiaoyiwancheng.setVisibility(View.VISIBLE);
        }



        ImageLoader.getInstance().displayImage(list.get(groupPosition).get(childPosition).getDescribe_img(), cyzMode.iv_commodity);
        cyzMode.tv_commodityName.setText(list.get(groupPosition).get(childPosition).getGoodsName());

        cyzMode.tv_total.setText(list.get(groupPosition).get(childPosition).getReal_price()+"");

        cyzMode.tv_Number.setText("X"+list.get(groupPosition).get(childPosition).getQuantity());
        cyzMode.tv_present.setText("￥"+list.get(groupPosition).get(childPosition).getNew_price());
        cyzMode.tv_primary.setText("￥" + list.get(groupPosition).get(childPosition).getPrice());
        cyzMode.tv_primary.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        cyzMode.tv_Distribution.setText("配送费："+list.get(groupPosition).get(childPosition).getDeliver_fee());


        //判断is_deliver_fee
        if (list.get(groupPosition).get(childPosition).getIs_deliver_fee()==1){
            cyzMode.ll_context.setVisibility(View.GONE);
            cyzMode.ll_state.setVisibility(View.GONE);
            //return convertView;
        }else {
            cyzMode.ll_context.setVisibility(View.VISIBLE);
            cyzMode.ll_state.setVisibility(View.VISIBLE);
        }

        //判断是否最后一个
        if (childPosition==getChildrenCount(groupPosition)-1){
            cyzMode.ll_state.setVisibility(View.VISIBLE);
        }else {
            cyzMode.ll_state.setVisibility(View.GONE);
        }

        cyzMode.tv_quxiao.setOnClickListener(new SetOnClick(groupPosition, childPosition));
        cyzMode.tv_quzhifu.setOnClickListener(new SetOnClick(groupPosition, childPosition));
        cyzMode.tv_shenqing.setOnClickListener(new SetOnClick(groupPosition, childPosition));
        cyzMode.tv_querenshouhuo.setOnClickListener(new SetOnClick(groupPosition, childPosition));
        cyzMode.tv_querenwancheng.setOnClickListener(new SetOnClick(groupPosition, childPosition));


        return convertView;
    }

    private class SetOnClick implements View.OnClickListener{
        int groupPosition;
        int childPosition;

        public SetOnClick(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.tv_shenqing:
                   /* DialogRefundOk dialogRefundOk=new DialogRefundOk(mContext,R.style.AlertDialogStyle);
                    dialogRefundOk.show();
                    DialogRefundNo dialogRefundNo=new DialogRefundNo(mContext,R.style.AlertDialogStyle);
                    dialogRefundNo.show();*/
                   /* DialogRefund dialogRefund=new DialogRefund(mContext,R.style.AlertDialogStyle,3,groupPosition,childPosition);
                    dialogRefund.show();*/
                    /*DialogRefund dialogRefund=new DialogRefund(mContext,R.style.AlertDialogStyle,3,groupPosition,childPosition);
                    dialogRefund.show();*/
                    intent=new Intent(mContext,Activity_ApplyRefund.class);
                    intent.putExtra("orderState",list.get(groupPosition).get(childPosition).getOrder_state()+"");
                    intent.putExtra("orderCode",list.get(groupPosition).get(childPosition).getOrder_code()+"");
                    mContext.startActivity(intent);
                    break;
                case R.id.tv_qushiyong:

                    break;
                case R.id.tv_quxiao:
                    DialogMyCancelOrder  dialogMyCancelOrder=new DialogMyCancelOrder(mContext,R.style.AlertDialogStyle,6,groupPosition,childPosition);
                    dialogMyCancelOrder.show();
                    break;
                case R.id.tv_quzhifu:
                    intent=new Intent(mContext,Activity_OrderPaymentOrder.class);
                    intent.putExtra("orderCode",list.get(groupPosition).get(childPosition).getOrder_code()+"");
                    mContext.startActivity(intent);
                    break;
                case R.id.tv_querenwancheng:
                    DialogMyOrderComplete dialogMyOrderComplete=new DialogMyOrderComplete(mContext,R.style.AlertDialogStyle,1,groupPosition,childPosition);
                    dialogMyOrderComplete.show();
                    break;
                case R.id.tv_querenshouhuo:
                    DialogMyOrderCollect dialogMyOrderCollect=new DialogMyOrderCollect(mContext,R.style.AlertDialogStyle,1,groupPosition,childPosition);
                    dialogMyOrderCollect.show();
                    break;
                case R.id.tv_shanchu:
                    DialogMyOrderDelete dialogMyOrderDelete=new DialogMyOrderDelete(mContext,R.style.AlertDialogStyle,3,groupPosition,childPosition);
                    dialogMyOrderDelete.show();
                    break;
            }
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    //万能接口
    public void backMoney(int orderState,final int groupPosition,int childPosition){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderState", orderState + "");
        params.addQueryStringParameter("orderCode", list.get(groupPosition).get(0).getOrder_code() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.backMoney, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int status = header.getInt("status");
                            if (status == 0) {
                                MyOrderSpFragment myOrderSpFragment= (MyOrderSpFragment) Activity_MyOrder.sp_3;
                                myOrderSpFragment.findOrder();
                                if (Activity_MyOrder.sp_4!=null){
                                    MyOrderSpFragment myOrderSpFragment4= (MyOrderSpFragment) Activity_MyOrder.sp_4;
                                    myOrderSpFragment4.findOrder();
                                }
                            } else if (status == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            } else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext, "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
    }
    //删除订单  取消订单
    public void deleteMyOrder(final int groupPosition, final int childPosition){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderState", list.get(groupPosition).get(childPosition).getOrder_state() + "");
        params.addQueryStringParameter("orderCode", list.get(groupPosition).get(childPosition).getOrder_code() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.deleteMyOrder, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int status = header.getInt("status");
                            if (status == 0) {
                                if (list.get(groupPosition).get(childPosition).getOrder_state()==1){
                                    MyOrderSpFragment myOrderSpFragment= (MyOrderSpFragment) Activity_MyOrder.sp_1;
                                    myOrderSpFragment.findOrder();
                                }
                            } else if (status == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            } else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext, "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
    }


    private void initView(CyzMode cyzMode){
        cyzMode.tv_quzhifu.setVisibility(View.GONE);
        cyzMode.tv_shenqing.setVisibility(View.GONE);
        cyzMode.tv_quxiao.setVisibility(View.GONE);
        cyzMode.tv_querenwancheng.setVisibility(View.GONE);
        cyzMode.tv_jiaoyiwancheng.setVisibility(View.GONE);
        cyzMode.tv_querenshouhuo.setVisibility(View.GONE);
        cyzMode.tv_fahuozhong.setVisibility(View.GONE);
    }


    private class CyzMode {
        TextView tv_quzhifu;
        TextView tv_shenqing;
        TextView tv_quxiao;
        TextView tv_jiaoyiwancheng;
        TextView tv_querenwancheng;
        LinearLayout ll_delete;
        LinearLayout ll_state;
        LinearLayout ll_context;
        ImageView iv_delete;
        TextView tv_code;

        ImageView iv_business;
        ImageView iv_commodity;
        TextView tv_businessName;
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
