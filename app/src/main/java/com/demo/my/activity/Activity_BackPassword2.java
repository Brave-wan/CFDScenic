package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_BackPassword2 extends Activity {

    @Bind(R.id.im_close)
    ImageView imClose;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.et_newPassword)
    EditText etNewPassword;
    @Bind(R.id.et_againNewPassword)
    EditText etAgainNewPassword;

    String phone;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpassword2);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.im_close, R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_close:
                finish();
                break;
            case R.id.button:
                Intent intent=getIntent();
                phone =intent.getStringExtra("phone");
                code =intent.getStringExtra("code");
                if (etAgainNewPassword.getText().length()<6){
                    ToastUtil.show(getApplicationContext(),"密码不能少于6位");
                    return;
                }
                if (etNewPassword.getText().toString().equals("")||etAgainNewPassword.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"密码不能为空");
                    return;
                }
                if (etAgainNewPassword.getText().toString().equals(etNewPassword.getText().toString())){
                    retrievePassword();
                }else {
                    ToastUtil.show(getApplicationContext(),"密码不一致");
                }
                break;
        }
    }

    private void retrievePassword(){
        RequestParams params = new RequestParams();
        params.addBodyParameter("mobileNo", phone);
        params.addBodyParameter("checkcode",code);
        params.addBodyParameter("password", etAgainNewPassword.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.RetrievePassword, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if (i==0){
                                ToastUtil.show(Activity_BackPassword2.this, header.getString("msg"));
                                finish();
                            }else if (i==7){
                                Intent intent=new Intent(Activity_BackPassword2.this,Activity_BackPassword1.class);
                                startActivity(intent);
                                finish();
                            }else {
                                ToastUtil.show(Activity_BackPassword2.this, header.getString("msg"));
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
}
