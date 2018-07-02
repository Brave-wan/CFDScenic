package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.adapter.MyTicketAdapter;
import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_MyTicket;
import com.demo.my.activity.Activity_MyTicketDetails;
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

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogCancelOrder extends Dialog implements View.OnClickListener{
    Context mContext;
    int mPosition;
    int mId;
    public DialogCancelOrder(Context context,int position,int id) {
        super(context);
        mContext=context;
        mPosition=position;
        mId=id;
    }

    public DialogCancelOrder(Context context, int themeResId,int position,int id) {
        super(context, themeResId);
        mContext=context;
        mPosition=position;
        mId=id;
    }

    protected DialogCancelOrder(Context context, boolean cancelable, OnCancelListener cancelListener,int position,int id) {
        super(context, cancelable, cancelListener);
        mContext=context;
        mPosition=position;
        mId=id;
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
                if (mId==0){
                    Activity_MyTicket myTicket= (Activity_MyTicket) mContext;
                    myTicket.myTicketAdapter.deleteMyTickets(mPosition);
                }else if (mId==1){
                    Activity_MyTicketDetails myTicketDetails= (Activity_MyTicketDetails) mContext;
                    myTicketDetails.deleteMyTickets(mPosition);
                }

                dismiss();
                break;
        }
    }



}
