package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.demo.fragment.MainActivity;
import com.demo.my.bean.AddressListBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogProgressbar;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import com.demo.adapter.ReceiptAddressAdapter;
import com.demo.demo.myapplication.R;
import com.demo.view.MyTopBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 收货地址
 * Created by Administrator on 2016/7/23 0023.
 */
public class Activity_ReceiptAddress extends Activity {

    @Bind(R.id.view_mytopBar)
    MyTopBar viewMytopBar;

    Intent intent;
    @Bind(R.id.ll_wudingdan)
    public LinearLayout llWudingdan;
    @Bind(R.id.lv_receiptaddress)
    ListView lvReceiptaddress;

    ReceiptAddressAdapter adapter;
    AddressListBean addressListBean;

    int id=-1;
    Intent intentGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiptaddress);
        ButterKnife.bind(this);

        intent = new Intent();

        viewMytopBar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getApplicationContext(), Activity_NewReceiptAddress.class);
                startActivity(intent);
            }
        });

        lvReceiptaddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断是从那个地方进入的，需要返回id
                intentGet=getIntent();
                id=intentGet.getIntExtra("id",-1);
                if (id==-1){
                    return;
                }else {
                    Intent intent=new Intent();
                    intent.putExtra("addressId",addressListBean.getData().get(position).getAddressId());
                    intent.putExtra("name",addressListBean.getData().get(position).getUser_name());
                    intent.putExtra("telphone",addressListBean.getData().get(position).getTelphone());
                    intent.putExtra("address",addressListBean.getData().get(position).getPlace_address()+addressListBean.getData().get(position).getDetail_address());
                    setResult(0x001, intent);
                    finish();
                }
            }
        });

    }

    private void addressList() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.addressList, params, new RequestCallBack<String>() {
            DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_ReceiptAddress.this, R.style.AlertDialogStyle);

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
                            Gson gson = new Gson();
                            addressListBean = gson.fromJson(responseInfo.result, AddressListBean.class);
                            if (addressListBean.getHeader().getStatus() == 0) {
                                if (addressListBean.getData()!=null){
                                    if (addressListBean.getData().size()==0) {
                                        llWudingdan.setVisibility(View.VISIBLE);
                                        lvReceiptaddress.setVisibility(View.GONE);
                                    } else {
                                        llWudingdan.setVisibility(View.GONE);
                                        lvReceiptaddress.setVisibility(View.VISIBLE);
                                        List<AddressListBean.DataBean> dataArray = addressListBean.getData();
                                        adapter=new ReceiptAddressAdapter(Activity_ReceiptAddress.this, dataArray);
                                        lvReceiptaddress.setAdapter(adapter);
                                    }
                                }else {
                                    llWudingdan.setVisibility(View.VISIBLE);
                                    lvReceiptaddress.setVisibility(View.GONE);
                                }

                            }else if(addressListBean.getHeader().getStatus() == 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ReceiptAddress.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), addressListBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "数据解析失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplication(), "连接网络失败");
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        addressList();
    }
}
