package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.utils.ToastUtil;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
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
 * 我的钱包--重置支付密码
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_Reset extends Activity {

    @Bind(R.id.et_ExistingPassword)
    EditText etExistingPassword;
    @Bind(R.id.et_NewPassword)
    EditText etNewPassword;
    @Bind(R.id.et_NewPassword2)
    EditText etNewPassword2;
    @Bind(R.id.bt_confirm)
    Button btConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.bt_confirm)
    public void onClick() {
        if (etExistingPassword.getText().toString().length()<6){
            ToastUtil.show(getApplicationContext(),"您输入的密码少于6位");
            return;
        }

        if (etNewPassword.getText().toString().equals(etNewPassword2.getText().toString())){
            resetPayPassWord();
        }else {
            ToastUtil.show(getApplicationContext(),"您输入的密码不一致");
        }
    }

    private void resetPayPassWord(){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("oldPassWord", etExistingPassword.getText().toString());
        params.addQueryStringParameter("newPassWord",etNewPassword2.getText().toString());
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, URL.resetPayPassWord, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if (i==0){
                                ToastUtil.show(Activity_Reset.this,header.getString("重置成功"));
                                finish();
                            }else if (i==3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_Reset.this);
                            }else {
                                ToastUtil.show(Activity_Reset.this,header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_Reset.this,e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }
}
