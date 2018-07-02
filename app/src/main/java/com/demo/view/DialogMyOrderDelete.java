package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.my.fragment.MyOrderFdFragment2;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_OrderDetailsFdDp;
import com.demo.my.activity.Activity_OrderDetailsFdTc;
import com.demo.my.activity.Activity_OrderDetailsJd;
import com.demo.my.activity.Activity_RefundOrder;
import com.demo.my.fragment.MyOrderFdFragment;
import com.demo.my.fragment.MyOrderJdFragment;
import com.demo.my.fragment.MyRefundFdFragment;
import com.demo.my.fragment.MyRefundJdFragment;
import com.demo.my.fragment.MyRefundSpFragment;
import com.demo.utils.ToastUtil;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogMyOrderDelete extends Dialog implements View.OnClickListener{
    Context mContext;
    int mJudge;//判断
    int mPosition;//对那个操作
    int mChildPosition;//饭店 商品需要用到

    public DialogMyOrderDelete(Context context,int judge,int position,int childPosition) {
        super(context);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    public DialogMyOrderDelete(Context context, int themeResId,int judge,int position,int childPosition) {
        super(context, themeResId);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    protected DialogMyOrderDelete(Context context, boolean cancelable, OnCancelListener cancelListener,int judge,int position,int childPosition) {
        super(context, cancelable, cancelListener);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_my_order_delete);

        LinearLayout ll_cancel= (LinearLayout) findViewById(R.id.ll_cancel);
        ll_cancel.setOnClickListener(this);
        LinearLayout ll_confirm= (LinearLayout) findViewById(R.id.ll_confirm);
        ll_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_cancel:
                dismiss();
                break;
            case R.id.ll_confirm:
                //判断是酒店还是饭店  1酒店  2酒店订单详情  3饭店  4饭店详情套餐  5饭店详情单品  6我的退款酒店列表  7饭店列表  8商品列表  9酒店订单详情，已过期
                //  10酒店列表已过期详情
                if (mJudge==1){
                    MyOrderJdFragment fragment= (MyOrderJdFragment)Activity_MyOrder.jd_2;
                    fragment.adapter.deleteMyTickets(mPosition);
                }else if(mJudge==2){
                    Activity_OrderDetailsJd activity_orderDetailsJd= (Activity_OrderDetailsJd) mContext;
                    activity_orderDetailsJd.finish();

                    MyOrderJdFragment fragment= (MyOrderJdFragment)Activity_MyOrder.jd_2;
                    fragment.adapter.deleteMyTickets(mPosition);
                }else if(mJudge==3){
                    MyOrderFdFragment2 myOrderFdFragment= (MyOrderFdFragment2) Activity_MyOrder.fd_2;
                    myOrderFdFragment.adapter.deleteMyOrder(mPosition, mChildPosition);
                }else if(mJudge==4){
                    Activity_OrderDetailsFdTc activity_orderDetailsFdTc= (Activity_OrderDetailsFdTc) mContext;
                    activity_orderDetailsFdTc.finish();

                    MyOrderFdFragment2 myOrderFdFragment= (MyOrderFdFragment2) Activity_MyOrder.fd_2;
                    myOrderFdFragment.adapter.deleteMyOrder(mPosition, mChildPosition);
                }else if (mJudge==5){
                    Activity_OrderDetailsFdDp activity_orderDetailsFdDp= (Activity_OrderDetailsFdDp) mContext;
                    activity_orderDetailsFdDp.finish();

                    MyOrderFdFragment2 myOrderFdFragment= (MyOrderFdFragment2) Activity_MyOrder.fd_2;
                    myOrderFdFragment.adapter.deleteMyOrder(mPosition,mChildPosition);
                }else if (mJudge==6){
                    MyRefundJdFragment myRefundJdFragment= (MyRefundJdFragment) Activity_RefundOrder.jd_1;
                    myRefundJdFragment.adapter.deleteMyTickets(mPosition);
                }else if (mJudge==7){
                    MyRefundFdFragment myRefundFdFragment= (MyRefundFdFragment) Activity_RefundOrder.fd_1;
                    myRefundFdFragment.adapter.deleteMyOrder(mPosition,0);
                }else if (mJudge==8){
                    MyRefundSpFragment myRefundSpFragment= (MyRefundSpFragment) Activity_RefundOrder.sp_4;
                    myRefundSpFragment.adapter.deleteMyOrder(mPosition,0);
                }else if (mJudge==9){
                    Activity_OrderDetailsJd activity_orderDetailsJd= (Activity_OrderDetailsJd) mContext;
                    activity_orderDetailsJd.finish();

                    MyOrderJdFragment fragment= (MyOrderJdFragment)Activity_MyOrder.jd_3;
                    fragment.adapter.deleteMyTickets(mPosition);
                }else if (mJudge==10){
                    //getPrePayId();
                    MyOrderJdFragment fragment= (MyOrderJdFragment)Activity_MyOrder.jd_3;
                    fragment.adapter.deleteMyTickets(mPosition);
                }
                dismiss();
                break;
        }
    }


    //测试用  可删
    private void getPrePayId(){

        RequestParams params = new RequestParams();

        params.addQueryStringParameter("body",1+"");
        params.addQueryStringParameter("balance",1+"");
        params.addQueryStringParameter("orderCode","654987654321654");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, "http://192.168.1.149/cfdScenic/interFace/WxPayController/getPrePayId", params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                        } catch (Exception e) {
                            ToastUtil.show(getContext(),"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), s);
                    }
                });

    }
}
