package com.demo.my.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.adapter.BanCardAdapter;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.GetBankBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyTopBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.List;


/**
 * 银行卡
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_BankCard extends Activity {


    @Bind(R.id.bt_bankCard_add)
    Button btBankCardAdd;
    @Bind(R.id.lv_bankCard)
    ListView lvBankCard;
    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;

    List<GetBankBean.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);
        ButterKnife.bind(this);


        viewTopbar.setFocusable(true);
        viewTopbar.setFocusableInTouchMode(true);
        viewTopbar.requestFocus();

    }

    @Override
    protected void onResume() {
        super.onResume();
        payPassWord();
    }

    @OnClick(R.id.bt_bankCard_add)
    public void onClick() {
        Intent intent = new Intent(getApplicationContext(), Activity_BankCardAdd.class);
        startActivity(intent);
    }

    private  void getBank(){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getBank, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_BankCard.this,R.style.AlertDialogStyle);
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
                            GetBankBean getBankBean = new Gson().fromJson(responseInfo.result, GetBankBean.class);
                            int i = getBankBean.getHeader().getStatus();
                            if (i == 0) {
                                list = getBankBean.getData();
                                if (getBankBean.getData() != null) {
                                    if (list.size() > 0) {
                                        lvBankCard.setAdapter(new BanCardAdapter(getApplicationContext(), list));
                                    }
                                }

                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_BankCard.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), getBankBean.getHeader().getMsg());
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

    //判断有没有支付密码
    public void payPassWord() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
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
                            int i = header.getInt("status");
                            if (i == 0) {
                                getBank();
                            } else if (i == 4) {
                                /*ToastUtil.show(getApplication(),"请先设置支付密码");
                                Intent intent=new Intent(getApplication(),Activity_PaymentPassword.class);
                                startActivity(intent);*/

                                final Dialog dialog = new Dialog(Activity_BankCard.this, R.style.AlertDialogStyle);
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
                                        Intent intent=new Intent(Activity_BankCard.this, Activity_PaymentPassword.class);
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
                            } else if (i==3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_BankCard.this);
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
}
