package com.demo.my.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.adapter.MyWalletTixianAdapter;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.WithDrawBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAddBankCard;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogPaymentError;
import com.demo.view.DialogProgressbar;
import com.demo.view.DialogSubmitSuccess;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 我的钱包-提现
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_MyWalletTixian extends Activity {
    @Bind(R.id.lv_Withdrawals)
    ListView lvWithdrawals;
    @Bind(R.id.et_input)
    EditText etInput;
    @Bind(R.id.bt_confirm)
    Button btConfirm;
    @Bind(R.id.tv_ketixian)
    TextView tvKetixian;
    private PopupWindow mPopWindow;

    int i=-1;//当前选中的那一项
    EditText et_password;
    List<WithDrawBean.DataBean.BankBean> list;
    MyWalletTixianAdapter myWalletTixianAdapter;
    WithDrawBean withDrawBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet_tixian);
        ButterKnife.bind(this);


        lvWithdrawals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i = position;
                myWalletTixianAdapter.setImageViewVisibility(position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        withdraw();
    }

    @OnClick(R.id.bt_confirm)
    public void onClick() {
        if (list.size()==0){
            ToastUtil.show(getApplication(), "请先添加银行卡");
            return;
        }
        int indexof = etInput.getText().toString().indexOf("0");
        if (indexof==0){
            ToastUtil.show(getApplicationContext(), "输入金额有误");
            return;
        }
        if (i==-1){
            ToastUtil.show(getApplicationContext(),"请选择提现银行卡");
            return;
        }
        if (etInput.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"请输入充值金额");
            return;
        }
        int kTixian=Integer.parseInt(tvKetixian.getText().toString());
        int Tixian=Integer.parseInt(etInput.getText().toString());
        if (kTixian<Tixian){
            ToastUtil.show(getApplicationContext(),"余额不足");
            return;
        }
        show_popupWindow();
    }

    //显示popupWindow
    private void show_popupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(Activity_MyWalletTixian.this).inflate(R.layout.popup_input_password, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        contentView.findViewById(R.id.ll_popup).setOnClickListener(new Click());
        contentView.findViewById(R.id.tv_forget).setOnClickListener(new Click());
        contentView.findViewById(R.id.tv_setPassword).setOnClickListener(new Click());
        TextView tv_recharge = (TextView) contentView.findViewById(R.id.tv_recharge);
        et_password= (EditText) contentView.findViewById(R.id.et_password);
        tv_recharge.setOnClickListener(new Click());
        tv_recharge.setText("提现");
        contentView.findViewById(R.id.im_left).setOnClickListener(new Click());
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //显示PopupWindow
        View rootview = LayoutInflater.from(Activity_MyWalletTixian.this).inflate(R.layout.popup_input_password, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    private class Click implements View.OnClickListener {
        Intent intent;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_popup:
                    mPopWindow.dismiss();
                    break;
                case R.id.tv_forget:
                    mPopWindow.dismiss();
                    intent = new Intent(getApplicationContext(), Activity_Identity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_setPassword:
                    mPopWindow.dismiss();
                    intent = new Intent(getApplicationContext(), Activity_PaymentPassword.class);
                    startActivity(intent);
                    break;
                case R.id.tv_recharge:  //支付成功
                    if (et_password.getText().toString().equals("")){
                        ToastUtil.show(getApplicationContext(),"请输入密码");
                        return;
                    }
                    isPassWord();
                    break;
                case R.id.im_left:
                    mPopWindow.dismiss();
                    break;
            }
        }
    }

    private void withdraw() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        //params.addQueryStringParameter("id",);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.withdraw, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_MyWalletTixian.this,R.style.AlertDialogStyle);
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
                            withDrawBean = new Gson().fromJson(responseInfo.result, WithDrawBean.class);
                            int i = withDrawBean.getHeader().getStatus();
                            if (i == 0) {
                                list = withDrawBean.getData().getBank();
                                if (list!=null){
                                    if (list.size() > 0) {
                                        myWalletTixianAdapter = new MyWalletTixianAdapter(getApplicationContext(), list);
                                        lvWithdrawals.setAdapter(myWalletTixianAdapter);
                                    }else if (list.size()==0){//没有银行卡
                                        DialogAddBankCard dialogAddBankCard=new DialogAddBankCard(Activity_MyWalletTixian.this,R.style.AlertDialogStyle);
                                        dialogAddBankCard.show();
                                    }
                                    tvKetixian.setText(withDrawBean.getData().getBalance().getBalance()+"");
                                }
                            }else if(i == 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_MyWalletTixian.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), withDrawBean.getHeader().getMsg());
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

    //提现
    private void isPassWord(){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("passWord", et_password.getText().toString());
        params.addQueryStringParameter("balance",etInput.getText().toString());
        params.addQueryStringParameter("name","提现到"+myWalletTixianAdapter.getBankName(i));
        params.addQueryStringParameter("balanceId",list.get(i).getId()+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(10 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(10 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.isPassWord, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_MyWalletTixian.this,R.style.AlertDialogStyle);
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
                                //密码正确
                                DialogSubmitSuccess dialogSubmitSuccess = new DialogSubmitSuccess(Activity_MyWalletTixian.this, R.style.AlertDialogStyle);
                                dialogSubmitSuccess.show();
                            }else if (i==4){
                                //密码错误
                                DialogPaymentError dialogPaymentError = new DialogPaymentError(Activity_MyWalletTixian.this, R.style.AlertDialogStyle);
                                dialogPaymentError.show();
                            }else {
                                ToastUtil.show(getApplicationContext(),header.getString("msg"));
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
