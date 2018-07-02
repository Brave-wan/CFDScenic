package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.view.MyTopBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的--充值成功
 * Created by Administrator on 2016/8/3 0003.
 */
public class Activity_RechargeOk extends Activity {

    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.tv_money)
    TextView tvMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_ok);
        ButterKnife.bind(this);

        viewTopbar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvMoney.setText(getIntent().getStringExtra("money"));
    }
}
