package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_MyTicket;
import com.demo.my.activity.Activity_MyTicketDetails;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogApplyRefund extends Dialog implements View.OnClickListener{
    Context mContext;
    int mPosition;
    int mId=-1;
    public DialogApplyRefund(Context context, int position,int id) {
        super(context);
        mContext=context;
        mPosition=position;
        mId=id;
    }

    public DialogApplyRefund(Context context, int themeResId, int position,int id) {
        super(context, themeResId);
        mContext=context;
        mPosition=position;
        mId=id;
    }

    protected DialogApplyRefund(Context context, boolean cancelable, OnCancelListener cancelListener, int position,int id) {
        super(context, cancelable, cancelListener);
        mContext=context;
        mPosition=position;
        mId=id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancel_order);

        TextView textView= (TextView) findViewById(R.id.tv_content);
        textView.setText("确定申请退款？");

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
                if (mId==0){
                    Activity_MyTicket myTicket= (Activity_MyTicket) mContext;
                    myTicket.myTicketAdapter.refund(mPosition);
                }else if (mId==1){
                    Activity_MyTicketDetails myTicketDetails= (Activity_MyTicketDetails) mContext;
                    myTicketDetails.refund(mPosition);
                }

                dismiss();
                break;
        }
    }



}
