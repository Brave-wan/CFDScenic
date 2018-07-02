package com.demo.mall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.MallLastShowBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyTopBar;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.protocol.HTTP;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商城--特产--支付结果
 * Created by Administrator on 2016/8/8 0008.
 */
public class PaymentResultTcActivity extends Activity {

    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.tv_get_name)
    TextView tvGetName;
    @Bind(R.id.tv_get_phone)
    TextView tvGetPhone;
    @Bind(R.id.tv_get_address)
    TextView tvGetAddress;
    @Bind(R.id.tv_get_mall_name)
    TextView tvGetMallName;
    @Bind(R.id.tv_get_ordecode)
    TextView tvGetOrdecode;
    @Bind(R.id.tv_get_build)
    TextView tvGetBuild;
    @Bind(R.id.tv_get_paytime)
    TextView tvGetPaytime;
    @Bind(R.id.ll_ReceiptAddress)
    LinearLayout llReceiptAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result_tc);
        ButterKnife.bind(this);

        viewTopbar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initResult();
    }

    private void initResult() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(PaymentResultTcActivity.this, SpName.token, ""));
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.mallLastShow, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(PaymentResultTcActivity.this, R.style.AlertDialogStyle);

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
                            MallLastShowBean mallLastShowBean = new Gson().fromJson(responseInfo.result, MallLastShowBean.class);
                            if (mallLastShowBean.getHeader().getStatus() == 0) {
                                if (mallLastShowBean.getData().get(0).getIs_deliver_fee() == 0) {
                                    if(mallLastShowBean.getData().get(0).getUser_name().equals("")||mallLastShowBean.getData().get(0).getUser_name()==null){
                                        llReceiptAddress.setVisibility(View.GONE);
                                    }else{
                                        llReceiptAddress.setVisibility(View.VISIBLE);
                                        tvGetName.setText(mallLastShowBean.getData().get(0).getUser_name());
                                        tvGetPhone.setText(mallLastShowBean.getData().get(0).getTelphone());
                                        tvGetAddress.setText(mallLastShowBean.getData().get(0).getPlace_address());
                                    }
                                    tvGetMallName.setText(mallLastShowBean.getData().get(0).getName());
                                    tvGetOrdecode.setText(mallLastShowBean.getData().get(0).getOrder_code());
                                    tvGetBuild.setText(mallLastShowBean.getData().get(0).getCreate_time());
                                    tvGetPaytime.setText(mallLastShowBean.getData().get(0).getPay_time());
                                } else {

                                }
                                if (mallLastShowBean.getData().get(1).getIs_deliver_fee() == 0) {
                                    if(mallLastShowBean.getData().get(1).getUser_name().equals("")||mallLastShowBean.getData().get(1).getUser_name()==null){
                                        llReceiptAddress.setVisibility(View.GONE);
                                    }else{
                                        llReceiptAddress.setVisibility(View.VISIBLE);
                                        tvGetName.setText(mallLastShowBean.getData().get(1).getUser_name());
                                        tvGetPhone.setText(mallLastShowBean.getData().get(1).getTelphone());
                                        tvGetAddress.setText(mallLastShowBean.getData().get(1).getPlace_address());
                                    }
                                    tvGetName.setText(mallLastShowBean.getData().get(1).getUser_name());
                                    tvGetPhone.setText(mallLastShowBean.getData().get(1).getTelphone());
                                    tvGetAddress.setText(mallLastShowBean.getData().get(1).getPlace_address());
                                    tvGetMallName.setText(mallLastShowBean.getData().get(1).getName());
                                    tvGetOrdecode.setText(mallLastShowBean.getData().get(1).getOrder_code());
                                    tvGetBuild.setText(mallLastShowBean.getData().get(1).getCreate_time());
                                    tvGetPaytime.setText(mallLastShowBean.getData().get(1).getPay_time());
                                } else {

                                }
                            } else if (mallLastShowBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(PaymentResultTcActivity.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), mallLastShowBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(PaymentResultTcActivity.this, "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(PaymentResultTcActivity.this, "连接网络失败");
                    }
                });
    }


}
