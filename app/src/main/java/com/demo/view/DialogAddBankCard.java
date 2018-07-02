package com.demo.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_BankCardAdd;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.fragment.MyOrderSpFragment2;

/**
 * 我的订单--取消订单
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogAddBankCard extends Dialog implements View.OnClickListener{
    Context mContext;

    public DialogAddBankCard(Context context) {
        super(context);
        mContext=context;
    }

    public DialogAddBankCard(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    protected DialogAddBankCard(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancel_order);

        TextView textView= (TextView) findViewById(R.id.tv_content);
        textView.setText("您还没有添加银行卡");

        LinearLayout ll_cancel= (LinearLayout) findViewById(R.id.ll_cancel);
        ll_cancel.setOnClickListener(this);
        LinearLayout ll_confirm= (LinearLayout) findViewById(R.id.ll_confirm);
        ll_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_cancel:
                Activity activity= (Activity) mContext;
                activity.finish();
                dismiss();
                break;
            case R.id.ll_confirm:
                Intent intent=new Intent(mContext, Activity_BankCardAdd.class);
                mContext.startActivity(intent);
                dismiss();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Activity activity= (Activity) mContext;
            activity.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
