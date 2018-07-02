package com.demo.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogProgressbar extends Dialog {
    Context mContext;
    public DialogProgressbar(Context context) {
        super(context);
        mContext=context;
    }

    public DialogProgressbar(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    protected DialogProgressbar(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load_progressbar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
