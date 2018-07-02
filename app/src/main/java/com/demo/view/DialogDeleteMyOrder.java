package com.demo.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;
import com.demo.my.fragment.MyOrderFdFragment;
import com.demo.my.fragment.MyOrderJdFragment;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogDeleteMyOrder extends Dialog implements View.OnClickListener{
    Context mContext;
    Activity activity;
    Fragment fragment;
    int i=0; //判断是那个fragment

    public DialogDeleteMyOrder(Context context,Fragment fragment,int i) {
        super(context);
        this.fragment=fragment;
        this.i=i;
        mContext=context;
    }

    public DialogDeleteMyOrder(Context context, int themeResId,Fragment fragment,int i) {
        super(context, themeResId);
        this.fragment=fragment;
        this.i=i;
        mContext=context;
    }

    protected DialogDeleteMyOrder(Context context, boolean cancelable, OnCancelListener cancelListener,Fragment fragment,int i) {
        super(context, cancelable, cancelListener);
        this.fragment=fragment;
        this.i=i;
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
                switch (i) {
                    case 1:
                        MyOrderJdFragment jdFragment = (MyOrderJdFragment) fragment;
                        //jdFragment.delete();
                        dismiss();
                        break;
                    case 2:
                        MyOrderFdFragment fdFragment = (MyOrderFdFragment) fragment;
                        //fdFragment.delete();
                        dismiss();
                        break;
                    case 3:
                        break;
                }
                break;
        }
    }
}
