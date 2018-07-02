package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.my.activity.Activity_MyTicket;
import com.demo.demo.myapplication.R;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogDeleteOrder extends Dialog implements View.OnClickListener{
    Context mContext;
    public DialogDeleteOrder(Context context) {
        super(context);
        mContext=context;
    }

    public DialogDeleteOrder(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    protected DialogDeleteOrder(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_order);

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
                Activity_MyTicket activity_myTicket= (Activity_MyTicket) mContext;
                dismiss();
                break;
        }
    }
}
