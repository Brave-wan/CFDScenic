package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAgainSignIn;
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
 * 设置支付密码
 * Created by Administrator on 2016/7/26 0026.
 */
public class Activity_SetPaymentPassword extends Activity {

    String realName;
    String idCard;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_password2)
    EditText etPassword2;
    @Bind(R.id.bt_confirm)
    Button btConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_payment_password);
        ButterKnife.bind(this);

        realName = getIntent().getStringExtra("realName");
        idCard = getIntent().getStringExtra("idCard");
    }
    @OnClick(R.id.bt_confirm)
    public void onClick() {
        if (!etPassword.getText().toString().equals(etPassword2.getText().toString())){
            ToastUtil.show(getApplicationContext(),"您输入的密码不一致");
            return;
        }
        if (etPassword.getText().toString().length()<6){
            ToastUtil.show(getApplicationContext(),"您输入的密码少于6位");
            return;
        }
        updatePayPassWord();
    }

    //设置支付密码
    private void updatePayPassWord() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("passWord", etPassword.getText().toString());
        params.addQueryStringParameter("realName", realName);
        params.addQueryStringParameter("idCard", idCard);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.updatePayPassWord, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                ToastUtil.show(getApplicationContext(), "设置成功");
                                finish();
                            } else if(i == 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_SetPaymentPassword.this);
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
