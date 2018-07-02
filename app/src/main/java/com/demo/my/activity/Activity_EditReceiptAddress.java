package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.fragment.MainActivity;
import com.demo.my.bean.AddressListBean;
import com.demo.demo.myapplication.R;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAdress;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyTopBar;
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
 * 编辑收货地址
 * Created by Administrator on 2016/7/23 0023.
 */
public class Activity_EditReceiptAddress extends Activity {

    @Bind(R.id.ll_LocalArea)
    LinearLayout llLocalArea;
    @Bind(R.id.view_mytopBar)
    MyTopBar viewMytopBar;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.et_userName)
    EditText etUserName;
    @Bind(R.id.et_telphone)
    EditText etTelphone;
    @Bind(R.id.et_detailAddress)
    EditText etDetailAddress;
    @Bind(R.id.et_postcode)
    EditText etPostcode;

    AddressListBean.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_receipt_address);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        dataBean= (AddressListBean.DataBean) intent.getSerializableExtra("data");
        etUserName.setText(dataBean.getUser_name());
        etTelphone.setText(dataBean.getTelphone());
        tvAddress.setText(dataBean.getPlace_address());
        etDetailAddress.setText(dataBean.getDetail_address());
        if (dataBean.getPostcode()==0){
            etPostcode.setText("");
        }else {
            etPostcode.setText(dataBean.getPostcode()+"");
        }


        viewMytopBar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                http();
            }
        });

    }


    @OnClick(R.id.ll_LocalArea)
    public void onClick() {
        final DialogAdress dialog = new DialogAdress(Activity_EditReceiptAddress.this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(true);
        dialog.setConfim(new DialogAdress.confirmListener()

                         {
                             @Override
                             public void onClick() {
                                 //Toast.makeText(Activity_EditReceiptAddress.this, dialog.getprovince() + dialog.getAdress() + dialog.gettown(), Toast.LENGTH_SHORT).show();
                                 tvAddress.setText(dialog.getprovince() + dialog.getAdress() +dialog.gettown());
                             }
                         }
        );
        dialog.show();
    }


    private void http(){
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", dataBean.getAddressId() + "");
        params.addQueryStringParameter("userName", etUserName.getText().toString());
        params.addQueryStringParameter("telphone", etTelphone.getText().toString());
        params.addQueryStringParameter("placeAddress", tvAddress.getText().toString());
        params.addQueryStringParameter("detailAddress", etDetailAddress.getText().toString());
        params.addQueryStringParameter("postcode", etPostcode.getText().toString());

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.editAddress, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_EditReceiptAddress.this,R.style.AlertDialogStyle);
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
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if (i==0){
                                ToastUtil.show(getApplicationContext(), "修改成功");
                                finish();
                            }else if(i == 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_EditReceiptAddress.this);
                            }/*else if (i==2){
                                ToastUtil.show(getApplicationContext(),"您有订单正在使用该收货地址\n不能删除");
                            }*/else {
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
