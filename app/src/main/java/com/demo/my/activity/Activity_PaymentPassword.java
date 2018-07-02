package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.StringUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyLinearLayoutItem;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的钱包--支付密码
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_PaymentPassword extends Activity {

    @Bind(R.id.view_Reset)
    MyLinearLayoutItem viewReset;
    @Bind(R.id.view_forget)
    MyLinearLayoutItem viewForget;
    @Bind(R.id.ll_setPayment)
    LinearLayout llSetPayment;
    @Bind(R.id.ll_OkPayment)
    LinearLayout llOkPayment;
    @Bind(R.id.bt_confirm)
    Button btConfirm;
    @Bind(R.id.et_Password)
    EditText etPassword;
    @Bind(R.id.et_Password2)
    EditText etPassword2;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_nameNumber)
    EditText etNameNumber;
    @Bind(R.id.et_nameNumber2)
    EditText etNameNumber2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_password);
        ButterKnife.bind(this);

        payPassWord();
    }

    @OnClick({R.id.view_Reset, R.id.view_forget, R.id.bt_confirm})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.view_Reset:   //重置密码
                intent.setClass(getApplicationContext(), Activity_Reset.class);
                startActivity(intent);
                break;
            case R.id.view_forget:  //忘记密码
                intent.setClass(getApplicationContext(), Activity_Identity.class);
                startActivity(intent);
                break;
            case R.id.bt_confirm:  //保存
                if (etPassword.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"支付密码不能为空");
                    return;
                }
                if (etPassword.getText().toString().length()<6){
                    ToastUtil.show(getApplicationContext(),"支付密码不能少于6位");
                    return;
                }
                if (!etPassword.getText().toString().equals(etPassword2.getText().toString())){
                    ToastUtil.show(getApplicationContext(),"您输入的密码不一致");
                    return;
                }
                if (etName.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"姓名不能为空");
                    return;
                }
                if (etNameNumber.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"身份证号码不能为空");
                    return;
                }
                if (!StringUtil.IDCardValidate(etNameNumber.getText().toString())){
                    ToastUtil.show(getApplicationContext(),"身份证号码格式不对");
                    return;
                }
                if (etNameNumber.getText().toString().length()<18){
                    ToastUtil.show(getApplicationContext(),"身份证号码格式不对");
                    return;
                }
                if (!etNameNumber.getText().toString().equals(etNameNumber2.getText().toString())){
                    ToastUtil.show(getApplicationContext(),"您输入的身份证号码不一致");
                    return;
                }
                updatePayPassWord();
                break;
        }
    }

    //判断有没有支付密码
    public void payPassWord() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.payPassWord, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_PaymentPassword.this,R.style.AlertDialogStyle);
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
                                llOkPayment.setVisibility(View.VISIBLE);
                                llSetPayment.setVisibility(View.GONE);
                            } else if (i == 4) {
                                llOkPayment.setVisibility(View.GONE);
                                llSetPayment.setVisibility(View.VISIBLE);
                            } else if (i==3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_PaymentPassword.this);
                            }else {
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

    //设置支付密码
    private void updatePayPassWord() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("passWord", etPassword2.getText().toString());
        params.addQueryStringParameter("realName", etName.getText().toString());
        params.addQueryStringParameter("idCard", etNameNumber.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.updatePayPassWord, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_PaymentPassword.this,R.style.AlertDialogStyle);
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
                                ToastUtil.show(getApplication(),"设置成功");
                                finish();
                            }else if (i==3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_PaymentPassword.this);
                            }else {
                                ToastUtil.show(getApplicationContext(),header.getString("msg"));
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
}
