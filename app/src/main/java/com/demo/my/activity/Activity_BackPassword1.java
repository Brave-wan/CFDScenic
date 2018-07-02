package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码1
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_BackPassword1 extends Activity {

    @Bind(R.id.im_close)
    ImageView imClose;
    @Bind(R.id.bt_password)
    Button btPassword;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_Code)
    EditText etCode;
    @Bind(R.id.tv_get)
    TextView tvGet;

    boolean sendState = false;//判断验证码是否发送   默认没


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpassword);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.im_close, R.id.tv_get, R.id.bt_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_close:
                finish();
                break;
            case R.id.tv_get:
                if (etPhone.getText().toString().length()>11){
                    ToastUtil.show(getApplicationContext(),"手机号格式错误");
                    return;
                }
                if (etPhone.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"手机号不能为空");
                    return;
                }
                if (sendState){
                    return;
                }else {
                    send();
                }
                break;
            case R.id.bt_password:
                if (etPhone.getText().toString().length()>11){
                    ToastUtil.show(getApplicationContext(),"手机号格式错误");
                    return;
                }
                if (etPhone.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"手机号不能为空");
                    return;
                }
                if (etCode.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"验证码不能为空");
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), Activity_BackPassword2.class);
                intent.putExtra("phone",etPhone.getText().toString());
                intent.putExtra("code",etCode.getText().toString());
                startActivity(intent);
                finish();
                break;
        }
    }


    //发送验证码
    private void send() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", etPhone.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST,
                URL.SendCode,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                ToastUtil.show(getApplicationContext(), "发送成功");
                                new SendCode().execute();
                                sendState = true;
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplication(), s);
                    }
                });
    }

    private class SendCode extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 60; i>0; i--) {
                try {
                    Thread.sleep(1000);
                    publishProgress(i + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            tvGet.setTextColor(Color.parseColor("#333333"));
            tvGet.setText("重新获取（" + values[0] + "）");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            sendState = false;
            tvGet.setTextColor(Color.parseColor("#FF5400"));
            tvGet.setText("获取验证码");
        }
    }
}
