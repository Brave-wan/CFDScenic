package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_OrderDetailsFdTc;
import com.demo.my.activity.Activity_OrderDetailsJd;
import com.demo.my.fragment.MyOrderFdFragment;
import com.demo.my.fragment.MyOrderJdFragment;
import com.demo.my.fragment.MyOrderSpFragment;
import com.demo.my.fragment.MyOrderSpFragment2;
import com.demo.utils.ToastUtil;

/**
 * 我的订单--取消订单
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogMyCancelOrder extends Dialog implements View.OnClickListener{
    Context mContext;
    int mJudge;//判断是酒店还是饭店  1酒店  2酒店订单详情  3饭店  4饭店详情套餐  5饭店详情单品  6商品  7商品详情
    int mPosition;//对那个操作
    int mChildPosition;//饭店 商品需要用到

    public DialogMyCancelOrder(Context context,int judge,int position,int childPosition) {
        super(context);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    public DialogMyCancelOrder(Context context, int themeResId,int judge,int position,int childPosition) {
        super(context, themeResId);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    protected DialogMyCancelOrder(Context context, boolean cancelable, OnCancelListener cancelListener,int judge,int position,int childPosition) {
        super(context, cancelable, cancelListener);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancel_order);

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
                }else if(mJudge==2){
                }else if(mJudge==3){
                }else if(mJudge==4){
                }else if (mJudge==5){

                }else if (mJudge==6){
                    Activity_MyOrder activity_myOrder= (Activity_MyOrder) mContext;
                    MyOrderSpFragment2 fragment= (MyOrderSpFragment2) activity_myOrder.sp_1;
                    fragment.adapter.deleteMyOrder(mPosition,mChildPosition);
                }else if (mJudge==7){

                }
                dismiss();
                break;
        }
    }
}
