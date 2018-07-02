package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.utils.BankInfo;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.StringUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogProgressbar;
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
 * 添加银行卡
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_BankCardAdd extends Activity {

    @Bind(R.id.bt_confirm)
    Button btConfirm;

    int i = -1;//判断有没有支付密码
    @Bind(R.id.et_realname)
    EditText etRealname;
    @Bind(R.id.et_idcard)
    EditText etIdcard;
    @Bind(R.id.et_bankcode)
    EditText etBankcode;
    @Bind(R.id.et_bankcode2)
    EditText etBankcode2;
    @Bind(R.id.et_type)
    EditText etType;
    @Bind(R.id.et_bankname)
    EditText etBankname;

    int type=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_add);
        ButterKnife.bind(this);

        //判断是否有支付密码 没有则跳转
        //payPassWord();

        //etBankcode.addTextChangedListener(watcher);
    }

    @OnClick(R.id.bt_confirm)
    public void onClick() {
        if (!StringUtil.IDCardValidate(etIdcard.getText().toString())){
            ToastUtil.show(getApplicationContext(),"身份证格式错误");
            return;
        }
        if (etIdcard.getText().toString().length()<18){
            ToastUtil.show(getApplicationContext(),"身份证格式错误");
            return;
        }
        if (etBankcode.getText().toString().length()<16){
            ToastUtil.show(getApplicationContext(),"银行卡卡号错误");
            return;
        }

        if (etBankname.getText().toString().equals("输入卡号有误")){
            ToastUtil.show(getApplicationContext(),"银行卡卡号错误");
            return;
        }

        if (etType.getText().toString().equals("储蓄卡")){
            type=0;
        }else if(etType.getText().toString().equals("信用卡")){
            type=1;
        }else{
            ToastUtil.show(getApplication(),"请输入卡类型储蓄卡或信用卡");
            return;
        }

        if (!etBankcode.getText().toString().equals(etBankcode2.getText().toString())) {
            ToastUtil.show(getApplicationContext(), "卡号不一致");
            return;
        }
        if (etBankname.getText().toString().equals("银行名称")){
            ToastUtil.show(getApplicationContext(), "银行卡错误");
            return;
        }

        saveBank();
    }

    public void payPassWord() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.payPassWord, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            i = header.getInt("status");
                            if (i == 0) {

                            } else if(i == 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_BankCardAdd.this);
                            }/*else if (i == 4) {
                                ToastUtil.showLong(getApplicationContext(), "为了方便您的使用请先设置支付密码");
                                finish();
                                Intent intent = new Intent(getApplicationContext(), Activity_PaymentPassword.class);
                                startActivity(intent);
                            } */else {
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

    private void saveBank() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("bankName", etBankname.getText().toString());
        params.addQueryStringParameter("bankCode", etBankcode2.getText().toString());
        //params.addQueryStringParameter("bankaccount", etban);
        params.addQueryStringParameter("realName", etRealname.getText().toString());
        params.addQueryStringParameter("idCard", etIdcard.getText().toString());
        params.addQueryStringParameter("type", type+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.saveBank, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_BankCardAdd.this,R.style.AlertDialogStyle);
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
                            i = header.getInt("status");
                            if (i == 0) {
                                finish();
                            } else if(i == 3){
                                DialogAgainSignIn dialogAgainSignIn=new DialogAgainSignIn(getApplicationContext
                                        (),R.style.AlertDialogStyle);
                                dialogAgainSignIn.show();
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
    /*//TextView监听事件
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

            if (start==6){
                String tr=s.toString();
                char[] cardNumber = new char[tr.length()];
                for(int i=0;i<tr.length();i++){
                    cardNumber[i] = tr.charAt(i);
                }
                tvBankname.setText(BankInfo.getNameOfBank(cardNumber, 0));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
           *//* char[] cardNumber = {};//卡号
            Sring name = BankInfo.getNameOfBank(cardNumber, 0);//获取银行卡的信息*//*
        }
    };*/
}
