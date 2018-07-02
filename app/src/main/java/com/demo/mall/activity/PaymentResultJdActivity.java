package com.demo.mall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.PayFinshShowBean;
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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商城--酒店--支付结果
 * Created by Administrator on 2016/8/8 0008.
 */
public class PaymentResultJdActivity extends Activity {

    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.tv_dingdanhao)
    TextView tvDingdanhao;
    @Bind(R.id.tv_Jname)
    TextView tvJname;
    @Bind(R.id.tv_Fname)
    TextView tvFname;
    @Bind(R.id.tv_ruzhu)
    TextView tvRuzhu;
    @Bind(R.id.tv_lidian)
    TextView tvLidian;
    @Bind(R.id.tv_wan)
    TextView tvWan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result_jd);
        ButterKnife.bind(this);

        viewTopbar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        payFinshShow();
    }


    private void payFinshShow() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("hotelOrderId", getIntent().getStringExtra("data"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.payFinshShow, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(PaymentResultJdActivity.this,R.style.AlertDialogStyle);
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框意外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        try {
                            PayFinshShowBean payFinshShowBean = new Gson().fromJson(responseInfo.result, PayFinshShowBean.class);
                            int i = payFinshShowBean.getHeader().getStatus();
                            if (i == 0) {
                                tvDingdanhao.setText("订单号："+payFinshShowBean.getData().getOrder_code()+"");
                                tvJname.setText(payFinshShowBean.getData().getOrderName());
                                tvFname.setText(payFinshShowBean.getData().getGoods_name());
                                tvRuzhu.setText("入住：" + payFinshShowBean.getData().getStart_date().substring(0, 10));
                                tvLidian.setText("离店：" + payFinshShowBean.getData().getEnd_date().substring(0, 10));
                                tvWan.setText(payFinshShowBean.getData().getCheck_days() + "晚");
                            }  else {
                                ToastUtil.show(getApplicationContext(), payFinshShowBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络出错");
                    }
                });
    }
}
