package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
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
 * 注册
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_Register extends Activity {

    @Bind(R.id.im_close)
    ImageView imClose;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.ll_clause)
    LinearLayout llClause;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_MemberClause)
    TextView tvMemberClause;
    @Bind(R.id.tv_Send)
    TextView tvSend;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_Code)
    EditText etCode;
    @Bind(R.id.et_Password)
    EditText etPassword;
    @Bind(R.id.tv_Agree)
    TextView tvAgree;

    boolean state = true;//判断是否勾选协议     默认选中
    boolean sendState=false;//判断验证码是否发送   默认没

    int i=1;//用来计数，同时当销毁Activity的时候停止线程
    SendCode sendCode;

    private CharSequence temp;
    private int editStart;
    private int editEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        etPassword.addTextChangedListener(watcher);
    }

    @OnClick({R.id.im_close, R.id.button, R.id.ll_clause, R.id.tv_MemberClause, R.id.tv_Send})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.im_close:
                finish();
                break;
            case R.id.button:   //注册并登录
                if (etPhone.getText().toString().length()>11){
                    ToastUtil.show(getApplicationContext(),"手机号不能少于11位");
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
                if (etPassword.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"密码不能为空");
                    return;
                }
                if (etPassword.getText().length()<6){
                    ToastUtil.show(getApplicationContext(),"密码不能少于6位");
                    return;
                }
                if (!state){
                    ToastUtil.show(getApplicationContext(),"请先同意会员条款");
                    return;
                }
                register();
                break;
            case R.id.ll_clause:
                if (state) {
                    state = false;
                    ivImage.setImageResource(R.mipmap.zhuce_duigou_huise);
                } else {
                    state = true;
                    ivImage.setImageResource(R.mipmap.zhuce_duigou);
                }
                break;
            case R.id.tv_MemberClause:  //会员条款
                intent = new Intent(getApplicationContext(), Activity_MemberClause.class);
                startActivity(intent);
                break;
            case R.id.tv_Send:
                if (etPhone.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"请输入手机号");
                    return;
                }
                if (etPhone.getText().toString().length()<11){
                    ToastUtil.show(getApplicationContext(),"手机号格式错误");
                    return;
                }
                if (sendState){
                    return;
                }
                if (state){
                    send();
                }else {
                    ToastUtil.show(getApplicationContext(),"请同意会员服务条款");
                }
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
        http.send(HttpRequest.HttpMethod.POST, URL.SendCode, params, new RequestCallBack<String>() {
            DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_Register.this,R.style.AlertDialogStyle);
            @Override
            public void onStart() {
                super.onStart();
                dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                dialogProgressbar.show();
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                dialogProgressbar.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if (i==0){
                                ToastUtil.show(getApplicationContext(), "发送成功");
                                sendCode=new SendCode();
                                sendCode.execute();
                                sendState = true;
                            }else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplication(), "网络连接失败");
                    }
                });
    }

    //注册
    private void register() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("mobileNo", etPhone.getText().toString());
        params.addBodyParameter("checkcode", etCode.getText().toString());
        params.addBodyParameter("password", etPassword.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.Register, params,
                new RequestCallBack<String>() {
                     DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_Register.this,R.style.AlertDialogStyle);
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                ToastUtil.show(getApplicationContext(), "注册成功，请登录");
                                finish();
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplication(), "网络连接失败");
                    }
                });
    }

    private class SendCode extends AsyncTask<Void,String,Void>{

        @Override
        protected void onPreExecute() {
            tvSend.setBackgroundResource(R.mipmap.bt_hui);
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (i=60;i>0;i--){
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
            tvSend.setText("重新获取("+values[0]+")");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            sendState=false;
            tvSend.setBackgroundResource(R.mipmap.bt_lan);
            tvSend.setText("获取验证码");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = etPassword.getSelectionStart();
            editEnd = etPassword.getSelectionEnd();
            if (temp.length() > 16) {//限制长度
                ToastUtil.show(getApplicationContext(),"请输入16位以内的密码");
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                etPassword.setText(s);
                etPassword.setSelection(tempSelection);
            }
        }
    };

}
