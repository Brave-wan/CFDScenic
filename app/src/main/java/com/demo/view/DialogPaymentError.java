package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_Identity;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogPaymentError extends Dialog implements View.OnClickListener{
    Context mContext;
    public DialogPaymentError(Context context) {
        super(context);
        mContext=context;
    }

    public DialogPaymentError(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    protected DialogPaymentError(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_payment_error);

        LinearLayout ll_cancel= (LinearLayout) findViewById(R.id.ll_again);
        ll_cancel.setOnClickListener(this);
        LinearLayout ll_confirm= (LinearLayout) findViewById(R.id.ll_forget);
        ll_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_again:
                dismiss();
                break;
            case R.id.ll_forget:
                Intent intent=new Intent(getContext(), Activity_Identity.class);
                getContext().startActivity(intent);
                dismiss();
                break;
        }
    }
}
