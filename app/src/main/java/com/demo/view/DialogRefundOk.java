package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogRefundOk extends Dialog implements View.OnClickListener{
    Context mContext;
    public DialogRefundOk(Context context) {
        super(context);
        mContext=context;
    }

    public DialogRefundOk(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    protected DialogRefundOk(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_refund_ok);

        LinearLayout ll_cancel= (LinearLayout) findViewById(R.id.ll_ok);
        ll_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_ok:
                dismiss();
                break;

        }
    }
}
