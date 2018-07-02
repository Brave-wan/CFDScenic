package com.demo.my.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.GetConsumerInfoBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogProgressbar;
import com.google.gson.Gson;
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
 * 我的钱包--忘记支付密码
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_Identity extends Activity {

    @Bind(R.id.bt_Submit)
    Button btSubmit;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.et_cardNumber)
    EditText etCardNumber;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_Send)
    TextView tvSend;
    @Bind(R.id.et_code)
    EditText etCode;

    boolean sendState = false;//判断验证码是否发送   默认没
    int i=1;//用来计数，同时当销毁Activity的时候停止线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        payPassWord();
    }

    //判断有没有支付密码
    public void payPassWord() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.payPassWord, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_Identity.this, R.style.AlertDialogStyle);

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
                                getConsumerInfo();
                            } else if (i == 4) {
                                final Dialog dialog = new Dialog(Activity_Identity.this, R.style.AlertDialogStyle);
                                dialog .getWindow().setContentView(R.layout.dialog_cancel_order);
                                dialog .setCanceledOnTouchOutside(false);
                                dialog .show();
                                TextView text = (TextView) dialog .getWindow().findViewById(R.id.tv_content);
                                text.setText("您还没有设置支付密码");
                                TextView tv_confirm= (TextView) dialog.getWindow().findViewById(R.id.bt_confirm);
                                tv_confirm.setText("去设置");
                                dialog .getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {  //确定
                                        Intent intent=new Intent(Activity_Identity.this, Activity_PaymentPassword.class);
                                        startActivity(intent);
                                        dialog .dismiss();
                                    }
                                });
                                dialog .getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {  //取消

                                        dialog .dismiss();
                                    }
                                });
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_Identity.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }

    @OnClick({R.id.tv_Send, R.id.bt_Submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_Send:
                if (sendState){
                    return;
                }
                if (!tvPhone.getText().toString().equals("")){
                    send();
                }else {
                    ToastUtil.show(getApplicationContext(),"您还未绑定手机号，请去个人资料绑定唯一手机号");
                }
                break;
            case R.id.bt_Submit:
               if (etCode.getText().toString().equals("")){
                   ToastUtil.show(getApplicationContext(),"请输入验证码");
                   return;
               }
                if (etCardNumber.getText().toString().length()<18){
                    ToastUtil.show(getApplicationContext(),"身份证格式错误");
                    return;
                }
                authentication();
                break;
        }
    }



    //发送验证码
    private void send() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", tvPhone.getText().toString());
        HttpUtils http = new HttpUtils();
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
                            } else if (i==3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_Identity.this);
                            }else {
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

    //获取手机号和姓名
    private void getConsumerInfo() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET,
                URL.getConsumerInfo,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            GetConsumerInfoBean getConsumerInfoBean=new Gson().fromJson(responseInfo.result, GetConsumerInfoBean.class);
                            int i=getConsumerInfoBean.getHeader().getStatus();
                            if (i==0){
                                String name=getConsumerInfoBean.getData().getRealName();
                                String newName=new String();
                                for (int x=0;i<name.length()-1;i++){
                                    newName=newName+"*";
                                }
                                newName=newName+name.substring(name.length()-1,name.length());
                                tvName.setText(newName);
                                tvPhone.setText(getConsumerInfoBean.getData().getTelphone());
                            }else if(i== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_Identity.this);
                            }else{
                                ToastUtil.show(getApplication(),getConsumerInfoBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplication(), s);
                    }
                });
    }
    private void authentication() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("verification",etCode.getText().toString());
        params.addQueryStringParameter("idCard",etCardNumber.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.authentication, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                Intent intent = new Intent(getApplicationContext(), Activity_SetPaymentPassword.class);
                                intent.putExtra("idCard",data.getString("idCard"));
                                intent.putExtra("realName",data.getString("realName"));
                                startActivity(intent);
                                finish();
                            } else if(i== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_Identity.this);
                            }else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }

    private class SendCode extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPreExecute() {
            tvSend.setBackgroundResource(R.mipmap.bt_hui);
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (i = 60; i >= 0; i--) {
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
            tvSend.setText("重新获取（" + values[0] + "）");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            sendState = false;
            tvSend.setBackgroundResource(R.mipmap.bt_lan);
            tvSend.setText("重新发送");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        i=61;
    }
}
