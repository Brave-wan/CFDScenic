package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.fragment.MyRefundSpFragment;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
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
 * Created by Administrator on 2016/9/27 0027.
 */
public class Activity_logistics extends Activity {

    @Bind(R.id.et_company)
    EditText etCompany;
    @Bind(R.id.et_Number)
    EditText etNumber;
    @Bind(R.id.bt_tijiao)
    Button btTijiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.bt_tijiao)
    public void onClick() {
        if (etCompany.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"请填写物流公司");
            return;
        }
        if (etNumber.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"请填写物流单号");
            return;
        }
        saveExpressOfUser();
    }

    private void saveExpressOfUser() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpName.token, ""));
        params.addQueryStringParameter("orderState", "11");
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        params.addQueryStringParameter("expressName", etCompany.getText().toString());
        params.addQueryStringParameter("expressCode", etNumber.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.saveExpressOfUser, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_logistics.this,R.style.AlertDialogStyle);
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
                            int status=header.getInt("status");
                            if(status==0){
                                finish();
                                MyRefundSpFragment myRefundSpFragment= (MyRefundSpFragment) Activity_RefundOrder.sp_2;
                                myRefundSpFragment.list.clear();
                                myRefundSpFragment.refundOrder();
                            }else if( status== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_logistics.this);
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
