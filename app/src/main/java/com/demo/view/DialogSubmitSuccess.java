package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_MyWalletTixian;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogSubmitSuccess extends Dialog implements View.OnClickListener{
    Context mContext;
    public DialogSubmitSuccess(Context context) {
        super(context);
        mContext=context;
    }

    public DialogSubmitSuccess(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    protected DialogSubmitSuccess(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_submit_success);

        LinearLayout ll_cancel= (LinearLayout) findViewById(R.id.ll_ok);
        ll_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_ok:
                dismiss();
                Activity_MyWalletTixian activity_myWalletTixian= (Activity_MyWalletTixian) mContext;
                activity_myWalletTixian.finish();
                break;

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            dismiss();
            Activity_MyWalletTixian activity_myWalletTixian= (Activity_MyWalletTixian) mContext;
            activity_myWalletTixian.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
