package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_OrderDetailsFdDp;
import com.demo.my.activity.Activity_OrderDetailsFdTc;
import com.demo.my.activity.Activity_OrderDetailsJd;
import com.demo.my.activity.Activity_OrderDetailsSp;
import com.demo.my.fragment.MyOrderFdFragment;
import com.demo.my.fragment.MyOrderFdFragment2;
import com.demo.my.fragment.MyOrderJdFragment;
import com.demo.my.fragment.MyOrderSpFragment;

/**
 * 我的订单--退款dialog
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogRefund extends Dialog implements View.OnClickListener{
    Context mContext;
    int mJudge;//判断
    int mPosition;//对那个操作
    int mChildPosition;//饭店需要用到

    public DialogRefund(Context context,int judge,int position,int childPosition ) {
        super(context);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    public DialogRefund(Context context, int themeResId,int judge,int position,int childPosition) {
        super(context, themeResId);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    protected DialogRefund(Context context, boolean cancelable, OnCancelListener cancelListener,int judge,int position,int childPosition) {
        super(context, cancelable, cancelListener);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_refund);

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
                if (mJudge==1){//判断是酒店还是饭店  1酒店  2酒店订单详情  3饭店  4饭店详情套餐  5饭店详情单品  6商品  7商品详情
                    dismiss();

                    MyOrderJdFragment myOrderJdFragment= (MyOrderJdFragment) Activity_MyOrder.jd_1;
                    myOrderJdFragment.adapter.backMoney(mPosition);
                }else if(mJudge==2){
                    Activity_OrderDetailsJd activity_orderDetailsJd= (Activity_OrderDetailsJd) mContext;
                    activity_orderDetailsJd.finish();

                    MyOrderJdFragment myOrderJdFragment= (MyOrderJdFragment) Activity_MyOrder.jd_1;
                    myOrderJdFragment.adapter.backMoney(mPosition);
                }else if(mJudge==3){
                    dismiss();

                    MyOrderFdFragment2 myOrderFdFragment= (MyOrderFdFragment2) Activity_MyOrder.fd_1;
                    myOrderFdFragment.adapter.backMoney(mPosition,mChildPosition);
                }else if(mJudge==4){
                    Activity_OrderDetailsFdTc activity_orderDetailsFdTc= (Activity_OrderDetailsFdTc) mContext;
                    activity_orderDetailsFdTc.finish();

                    MyOrderFdFragment2 myOrderFdFragment= (MyOrderFdFragment2) Activity_MyOrder.fd_1;
                    myOrderFdFragment.adapter.backMoney(mPosition,mChildPosition);
                }else if(mJudge==5){
                    Activity_OrderDetailsFdDp activity_orderDetailsFdDp= (Activity_OrderDetailsFdDp) mContext;
                    activity_orderDetailsFdDp.finish();

                    MyOrderFdFragment2 myOrderFdFragment= (MyOrderFdFragment2) Activity_MyOrder.fd_1;
                    myOrderFdFragment.adapter.backMoney(mPosition,mChildPosition);
                }else if(mJudge==6){

                }else if(mJudge==7){

                }
                dismiss();
                break;
        }
    }
}
