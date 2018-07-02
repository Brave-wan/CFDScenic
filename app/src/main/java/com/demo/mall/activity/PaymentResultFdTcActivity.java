package com.demo.mall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.ShowOrderBean;
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
 * 商城--饭店单品--支付结果
 * Created by Administrator on 2016/8/8 0008.
 */
public class PaymentResultFdTcActivity extends Activity {

    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.tv_dingdanhao)
    TextView tvDingdanhao;
    @Bind(R.id.tv_Sname)
    TextView tvSname;
    @Bind(R.id.tv_Fname)
    TextView tvFname;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_num)
    TextView tvNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result_fd_tc);
        ButterKnife.bind(this);

        viewTopbar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showOrder();
    }


    private void showOrder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("data"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.showOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(PaymentResultFdTcActivity.this,R.style.AlertDialogStyle);
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
                            ShowOrderBean showOrderBean = new Gson().fromJson(responseInfo.result, ShowOrderBean.class);
                            int i = showOrderBean.getHeader().getStatus();
                            if (i == 0) {
                                tvDingdanhao.setText("订单号："+showOrderBean.getData().getOrder_code() + "");
                                tvSname.setText(showOrderBean.getData().getName());
                                tvDate.setText("就餐时间：" + showOrderBean.getData().getEat_date());
                                tvNum.setText("购买数量："+showOrderBean.getData().getQuantity()+"份");
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(PaymentResultFdTcActivity.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), showOrderBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }
}
