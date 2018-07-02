package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.amusement.activity.Activity_WriteBlogs;
import com.demo.demo.myapplication.R;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogExitEdit extends Dialog implements View.OnClickListener{
    Context mContext;
    public DialogExitEdit(Context context) {
        super(context);
        mContext=context;
    }

    public DialogExitEdit(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    protected DialogExitEdit(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit_edit);

        LinearLayout signOut= (LinearLayout) findViewById(R.id.ll_SignOut);
        LinearLayout cancel= (LinearLayout) findViewById(R.id.ll_cancel);

        signOut.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_cancel:
                dismiss();
            break;
            case R.id.ll_SignOut:
                Activity_WriteBlogs activity= (Activity_WriteBlogs) mContext;
                activity.finish();
                break;
        }
    }
}
