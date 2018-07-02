package com.demo.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_SignIn;
import com.demo.utils.ToastUtil;

/**
 * 我的订单--取消订单
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogAgainSignIn extends Dialog implements View.OnClickListener{
    Context mContext;
    public DialogAgainSignIn(Context context) {
        super(context);
        mContext=context;
    }

    public DialogAgainSignIn(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    protected DialogAgainSignIn(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_again_signin);

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
                Intent intent=new Intent(getContext(), Activity_SignIn.class);
                intent.putExtra("index",0);
                getContext().startActivity(intent);
                dismiss();
                break;
        }
    }
}
