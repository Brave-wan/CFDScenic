package com.demo.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.adapter.CartPaymentResultAdapter;
import com.demo.demo.myapplication.R;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyTopBar;
import com.demo.view.NoScrollViewListView;
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
 * 我的购物车--支付结果
 * Created by Administrator on 2016/8/8 0008.
 */
public class Activity_CartPaymentResult extends Activity {

    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.lv_cartPaymentResult)
    NoScrollViewListView lvCartPaymentResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_payment_result);
        ButterKnife.bind(this);

//        lvCartPaymentResult.setAdapter(new CartPaymentResultAdapter(getApplicationContext()));

        viewTopbar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initCart();
    }

    private void initCart() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("orderCode",getIntent().getStringExtra("data"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.mallCarLastShow, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_CartPaymentResult.this,R.style.AlertDialogStyle);
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框意外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        Log.e("1111", responseInfo.result);
                        try {
//                            if(){
//                            }else if( == 3){
//                                //异地登录对话框，必须传.this  不能传Context
//                                MainActivity.state_Three(mContext);
//                            }else {
//                                ToastUtil.show(getApplicationContext(), addressListBean.getHeader().getMsg());
//                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_CartPaymentResult.this,e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_CartPaymentResult.this, e.getMessage());
                    }
                });

    }


}
