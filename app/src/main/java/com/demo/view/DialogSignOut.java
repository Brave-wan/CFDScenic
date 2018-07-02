package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_SignIn;
import com.demo.my.activity.Activity_set;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;

import java.util.Set;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DialogSignOut extends Dialog implements View.OnClickListener {
    @Bind(R.id.ll_confirm)
    LinearLayout llConfirm;
    @Bind(R.id.ll_cancel)
    LinearLayout llCancel;

    Context mContext;

    public DialogSignOut(Context context) {
        super(context);
        mContext = context;
    }

    public DialogSignOut(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected DialogSignOut(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_out);

        LinearLayout ll_cancel = (LinearLayout) findViewById(R.id.ll_cancel);
        ll_cancel.setOnClickListener(this);
        LinearLayout ll_confirm = (LinearLayout) findViewById(R.id.ll_confirm);
        ll_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cancel:
                dismiss();
                break;
            case R.id.ll_confirm:
                JPushInterface.setAlias(mContext, "", null);
                SpUtil.putString(getContext(), SpName.Gender, "");
                SpUtil.putString(getContext(), SpName.token, "");
                SpUtil.putString(getContext(), SpName.userName, "去登录");
                SpUtil.putString(getContext(), SpName.headimg, "");
                Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
                dismiss();

                Activity_set activity_set = (Activity_set) mContext;
                activity_set.finish();

                Intent intent=new Intent(mContext, Activity_SignIn.class);
                intent.putExtra("index",1);
                mContext.startActivity(intent);
                break;
        }
    }
}
