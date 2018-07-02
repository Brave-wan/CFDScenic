package com.demo.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.demo.my.activity.Activity_logistics;
import com.demo.my.bean.RefundOrderSpBean;
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
public class MyRefundSpAdapterEL extends BaseExpandableListAdapter {
    Context mContext;
    List<List<RefundOrderSpBean.DataBean.OrderListBean.RowsBean>> list;

    public MyRefundSpAdapterEL(Context mContext, List<List<RefundOrderSpBean.DataBean.OrderListBean.RowsBean>> list) {
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
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_refund_order_sp_xia,null);
            cyzMode.ll_state= (LinearLayout) convertView.findViewById(R.id.ll_state);
            cyzMode.ll_delete= (LinearLayout) convertView.findViewById(R.id.ll_jiudian);
            cyzMode.iv_delete= (ImageView) convertView.findViewById(R.id.iv_jiudian);
            cyzMode.iv_commodity= (ImageView) convertView.findViewById(R.id.iv_commodity);
            cyzMode.tv_commodityName= (TextView) convertView.findViewById(R.id.tv_commodityName);
            cyzMode.tv_present= (TextView) convertView.findViewById(R.id.tv_present);
            cyzMode.tv_primary= (TextView) convertView.findViewById(R.id.tv_primary);
            cyzMode.tv_Distribution= (TextView) convertView.findViewById(R.id.tv_Distribution);
            cyzMode.tv_total= (TextView) convertView.findViewById(R.id.tv_total);
            cyzMode.tv_Number= (TextView) convertView.findViewById(R.id.tv_Number);
            cyzMode.ll_context= (LinearLayout) convertView.findViewById(R.id.ll_context);

            cyzMode.tv_beibohui= (TextView) convertView.findViewById(R.id.tv_beibohui);
            cyzMode.tv_tianxiewuliu= (TextView) convertView.findViewById(R.id.tv_tianxiewuliu);
            cyzMode.tv_tuikuanzhong= (TextView) convertView.findViewById(R.id.tv_tuikuanzhong);
            cyzMode.tv_yituikuan= (TextView) convertView.findViewById(R.id.tv_yituikuan);
            cyzMode.tv_yifahuo= (TextView) convertView.findViewById(R.id.tv_yifahuo);
            cyzMode.tv_shenhezhong= (TextView) convertView.findViewById(R.id.tv_shenhezhong);
            cyzMode.tv_shanchu= (TextView) convertView.findViewById(R.id.tv_shanchu);

            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        initView(cyzMode);
        //状态值判断
        if (list.get(groupPosition).get(childPosition).getOrder_state()==6){
            cyzMode.tv_shenhezhong.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==7){
            cyzMode.tv_tianxiewuliu.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==8){
            cyzMode.tv_tuikuanzhong.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==9){
            cyzMode.tv_yituikuan.setVisibility(View.VISIBLE);
            cyzMode.tv_shanchu.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==10){
            cyzMode.tv_beibohui.setVisibility(View.VISIBLE);
        }else if (list.get(groupPosition).get(childPosition).getOrder_state()==11){
            cyzMode.tv_yifahuo.setVisibility(View.VISIBLE);
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

        cyzMode.tv_tianxiewuliu.setOnClickListener(new SetOnClick(groupPosition,childPosition));
        cyzMode.tv_shanchu.setOnClickListener(new SetOnClick(groupPosition,childPosition));
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
                case R.id.tv_tianxiewuliu:
                    intent=new Intent(mContext, Activity_logistics.class);
                    intent.putExtra("orderState",list.get(groupPosition).get(childPosition).getOrder_state()+"");
                    intent.putExtra("orderCode",list.get(groupPosition).get(childPosition).getOrder_code()+"");
                    mContext.startActivity(intent);
                    break;
                case R.id.tv_shanchu:
                    DialogMyOrderDelete dialogMyOrderDelete=new DialogMyOrderDelete(mContext,R.style.AlertDialogStyle,8,groupPosition,childPosition);
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
                                }else if (list.get(groupPosition).get(childPosition).getOrder_state()==9){
                                    MyOrderSpFragment myOrderSpFragment= (MyOrderSpFragment) Activity_MyOrder.sp_4;
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
        cyzMode.tv_beibohui.setVisibility(View.GONE);
        cyzMode.tv_tianxiewuliu.setVisibility(View.GONE);
        cyzMode.tv_tuikuanzhong.setVisibility(View.GONE);
        cyzMode.tv_yituikuan.setVisibility(View.GONE);
        cyzMode.tv_yifahuo.setVisibility(View.GONE);
        cyzMode.tv_shenhezhong.setVisibility(View.GONE);
        cyzMode.tv_shanchu.setVisibility(View.GONE);
    }


    private class CyzMode {
        LinearLayout ll_delete;
        LinearLayout ll_state;
        LinearLayout ll_context;
        ImageView iv_delete;
        TextView tv_code;
        TextView tv_businessName;
        ImageView iv_business;
        ImageView iv_commodity;

        TextView tv_commodityName;
        TextView tv_present;
        TextView tv_primary;
        TextView tv_Distribution;
        TextView tv_total;
        TextView tv_Number;

        TextView tv_beibohui;
        TextView tv_tianxiewuliu;
        TextView tv_tuikuanzhong;
        TextView tv_yituikuan;
        TextView tv_yifahuo;
        TextView tv_shenhezhong;
        TextView tv_shanchu;
    }
}
