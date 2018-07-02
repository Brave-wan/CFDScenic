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
 * 联系手机
 * Created by Administrator on 2016/7/23 0023.
 */
public class Activity_ContactPhone extends Activity {

    @Bind(R.id.editText4)
    EditText editText4;
    @Bind(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactphone);
        ButterKnife.bind(this);
        editText4.setText(getIntent().getStringExtra("ppp"));
        editText4.setSelection(editText4.length());
    }

    @OnClick(R.id.button2)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("phone", editText4.getText().toString());
        setResult(200, intent);
        finish();
    }
}
