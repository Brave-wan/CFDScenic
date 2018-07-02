package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.demo.demo.myapplication.R;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
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
 * 修改密码
 * Created by Administrator on 2016/7/23 0023.
 */
public class Activity_ModifyPassword extends Activity {
    @Bind(R.id.et_dangqian)
    EditText etDangqian;
    @Bind(R.id.et_xinmima)
    EditText etXinmima;
    @Bind(R.id.et_zaicixin)
    EditText etZaicixin;
    @Bind(R.id.bt_queren)
    Button btQueren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_queren)
    public void onClick() {
        if (etDangqian.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"当前密码不能为空");
            return;
        }
        if (etXinmima.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"新密码不能为空");
            return;
        }
        if (etZaicixin.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"再次输入新密码不能为空");
            return;
        }
        if (!etXinmima.getText().toString().equals(etZaicixin.getText().toString())){
            ToastUtil.show(getApplicationContext(),"您输入的密码不一致");
            return;
        }
        if (etZaicixin.getText().length()<6){
            ToastUtil.show(getApplicationContext(),"密码不能少于6位");
        }
        modifyPassword();
    }

    private void modifyPassword(){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addBodyParameter("oldPsw", etDangqian.getText().toString());
        params.addBodyParameter("newPsw", etZaicixin.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.ModifyPassword, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if (i==0){
                                ToastUtil.show(getApplicationContext(), "修改成功");
                                finish();
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
}