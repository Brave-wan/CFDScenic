package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.demo.demo.myapplication.R;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.StringUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAgainSignIn;
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
 * 意见反馈
 * Created by Administrator on 2016/7/23 0023.
 */
public class Activity_Feedback extends Activity {
    @Bind(R.id.et_decribe)
    EditText etDecribe;
    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.bt_tijiao)
    Button btTijiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }

    @OnClick(R.id.bt_tijiao)
    public void onClick() {
        if (etDecribe.getText().toString().equals("")) {
            ToastUtil.show(getApplicationContext(), "请输入您的意见");
            return;
        }

        if (!etEmail.getText().toString().equals("")){
            if (!StringUtil.isEmail(etEmail.getText().toString())){
                ToastUtil.show(getApplicationContext(), "请输入正确的邮箱");
                return;
            }
        }
        opinion();
    }

    private void opinion() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("decribe", etDecribe.getText().toString());
        params.addQueryStringParameter("email", etEmail.getText().toString());
        params.addQueryStringParameter("memo", "");//备注
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.opinion, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                ToastUtil.show(getApplicationContext(), "发送成功");
                                finish();
                            } else if (i == 3) {
                                DialogAgainSignIn dialogAgainSignIn = new DialogAgainSignIn(getApplicationContext
                                        (), R.style.AlertDialogStyle);
                                dialogAgainSignIn.show();
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
}