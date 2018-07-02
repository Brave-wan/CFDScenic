package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.demo.fragment.MainActivity;
import com.demo.my.fragment.MyOrderSpFragment;
import com.demo.my.fragment.MyOrderSpFragment2;
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

import org.json.JSONException;
import org.json.JSONObject;
import com.demo.demo.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 原因
 * Created by Administrator on 2016/9/26 0026.
 */
public class Activity_ApplyRefund extends Activity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_telphone)
    EditText etTelphone;
    @Bind(R.id.et_Reason)
    EditText etReason;
    @Bind(R.id.bt_tijiao)
    Button btTijiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_refund);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.bt_tijiao)
    public void onClick() {
        if (etName.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"请填写姓名");
            return;
        }
        if (etTelphone.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"请填写手机号");
            return;
        }
        if (etReason.getText().toString().equals("")){
            ToastUtil.show(getApplicationContext(),"请填写退款原因");
            return;
        }
        refundCauseInfo();
    }

    private void refundCauseInfo(){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("orderState", "6");
        params.addQueryStringParameter("orderCode",getIntent().getStringExtra("orderCode"));
        params.addQueryStringParameter("name",etName.getText().toString());
        params.addQueryStringParameter("telPhone",etTelphone.getText().toString());
        params.addQueryStringParameter("cause",etReason.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.refundCauseInfo, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ApplyRefund.this,R.style.AlertDialogStyle);
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
                                MyOrderSpFragment2 myOrderSpFragment= (MyOrderSpFragment2) Activity_MyOrder.sp_3;
                                myOrderSpFragment.list.clear();
                                myOrderSpFragment.findOrder();
                                finish();
                                if (getIntent().getIntExtra("id",-1)==1){
                                    Activity_OrderDetailsSp.bState = true;
                                }
                            }else if( status== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ApplyRefund.this);
                            }else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(),"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }
}
