package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.demo.myapplication.R;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogSendFail extends Dialog implements View.OnClickListener{
    public DialogSendFail(Context context) {
        super(context);
    }

    public DialogSendFail(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogSendFail(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_send_fail);

        LinearLayout cancelSend= (LinearLayout) findViewById(R.id.ll_CancelSend);
        LinearLayout reSend= (LinearLayout) findViewById(R.id.ll_ReSend);

        cancelSend.setOnClickListener(this);
        reSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_CancelSend:
                dismiss();
            break;
            case R.id.ll_ReSend:
                //重新发送
                break;
        }
    }
}
