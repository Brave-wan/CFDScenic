package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_OrderDetailsSp;
import com.demo.my.fragment.MyOrderSpFragment;
import com.demo.my.fragment.MyOrderSpFragment2;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogMyOrderComplete extends Dialog implements View.OnClickListener{
    Context mContext;
    int mJudge;//判断
    int mPosition;//对那个操作
    int mChildPosition;//饭店 商品需要用到

    public DialogMyOrderComplete(Context context, int judge, int position, int childPosition) {
        super(context);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    public DialogMyOrderComplete(Context context, int themeResId, int judge, int position, int childPosition) {
        super(context, themeResId);
        mContext=context;
        mJudge=judge;
        mPosition=position;
        mChildPosition=childPosition;
    }

    protected DialogMyOrderComplete(Context context, boolean cancelable, OnCancelListener cancelListener, int judge, int position, int childPosition) {
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

        TextView textView= (TextView) findViewById(R.id.tv_content);
        textView.setText("确认完成？");

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
                if (mJudge==1){ //商品列表   //商品详情
                    MyOrderSpFragment2 myOrderSpFragment3= (MyOrderSpFragment2) Activity_MyOrder.sp_3;
                    myOrderSpFragment3.adapter.backMoney(5,mPosition,mChildPosition);
                    if (Activity_MyOrder.sp_4!=null){
                        MyOrderSpFragment2 myOrderSpFragment4= (MyOrderSpFragment2) Activity_MyOrder.sp_4;
                        myOrderSpFragment4.list.clear();
                        myOrderSpFragment4.mPage=1;
                        myOrderSpFragment4.findOrder();
                    }
                }else if (mJudge==2){
                    Activity_OrderDetailsSp activity_orderDetailsSp= (Activity_OrderDetailsSp) mContext;
                    activity_orderDetailsSp.finish();

                    MyOrderSpFragment2 myOrderSpFragment= (MyOrderSpFragment2) Activity_MyOrder.sp_3;
                    myOrderSpFragment.adapter.backMoney(5,mPosition,mChildPosition);
                }
                dismiss();
                break;
        }
    }
}
