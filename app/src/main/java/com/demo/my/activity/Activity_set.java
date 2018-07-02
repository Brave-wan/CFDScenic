package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.demo.myapplication.R;
import com.demo.view.DialogSignOut;
import com.demo.view.MyLinearLayoutItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 * Created by Administrator on 2016/7/23 0023.
 */
public class Activity_set extends Activity {

    @Bind(R.id.view_guanyuwomen)
    MyLinearLayoutItem viewGuanyuwomen;
    @Bind(R.id.view_yijianfankui)
    MyLinearLayoutItem viewYijianfankui;
    @Bind(R.id.view_xiugaimima)
    MyLinearLayoutItem viewXiugaimima;
    @Bind(R.id.bt_tuichu)
    Button btTuichu;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);

        intent=new Intent();
    }

    @OnClick({R.id.view_guanyuwomen, R.id.view_yijianfankui, R.id.view_xiugaimima, R.id.bt_tuichu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_guanyuwomen:
                intent.setClass(getApplicationContext(),Activity_About.class);
                startActivity(intent);
                break;
            case R.id.view_yijianfankui:
                intent.setClass(getApplicationContext(),Activity_Feedback.class);
                startActivity(intent);
                break;
            case R.id.view_xiugaimima:
                intent.setClass(getApplicationContext(),Activity_ModifyPassword.class);
                startActivity(intent);
                break;
            case R.id.bt_tuichu:
                DialogSignOut dialogSignOut=new DialogSignOut(Activity_set.this,R.style.AlertDialogStyle);
                dialogSignOut.show();
                break;
        }
    }
}
