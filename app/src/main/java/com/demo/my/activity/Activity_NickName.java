package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.demo.demo.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 昵称
 * Created by Administrator on 2016/7/23 0023.
 */
public class Activity_NickName extends Activity {

    @Bind(R.id.ed_nicheng)
    EditText edNicheng;
    @Bind(R.id.bt_queren)
    Button btQueren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        ButterKnife.bind(this);
        edNicheng.setText(getIntent().getStringExtra("pp"));
        edNicheng.setSelection(edNicheng.length());
    }

    @OnClick(R.id.bt_queren)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("nick", edNicheng.getText().toString());
        setResult(100, intent);
        finish();
    }
}
