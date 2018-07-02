package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.demo.adapter.ExchangeAdapter;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.IntegralShopGoodsBean;
import com.demo.demo.myapplication.R;
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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的钱包--兑换积分
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_Exchange extends Activity {

    @Bind(R.id.gv_exchange)
    GridView gvExchange;
    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.tv_Integral)
    TextView tvIntegral;

    IntegralShopGoodsBean integralShopGoodsBean;
    List<IntegralShopGoodsBean.DataBean.GoodsListBean> list;
    int i = 1;//页数

    Intent intentGet;
    String integral="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        ButterKnife.bind(this);



        gvExchange.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Activity_ExchangeDetails.class);
                intent.putExtra("id", list.get(position).getId()+"");
                intent.putExtra("type", list.get(position).getType());
                intent.putExtra("integral",integralShopGoodsBean.getData().getIntegration()+"");
                startActivity(intent);
            }
        });

        viewTopbar.setFocusable(true);
        viewTopbar.setFocusableInTouchMode(true);
        viewTopbar.requestFocus();

    }

    @Override
    protected void onStart() {
        super.onStart();
        integralShopGoods();
    }



    private void integralShopGoods() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
       /* params.addQueryStringParameter("page", i + "");
        params.addQueryStringParameter("rows", 8 + "");*/
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.integralShopGoods, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_Exchange.this,R.style.AlertDialogStyle);
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
                            integralShopGoodsBean = new Gson().fromJson(responseInfo.result, IntegralShopGoodsBean.class);
                            int i = integralShopGoodsBean.getHeader().getStatus();
                            if (i == 0) {
                                if (integralShopGoodsBean.getData().getGoodsList()!=null){
                                    list = integralShopGoodsBean.getData().getGoodsList();
                                    if (list.size()>0){
                                        gvExchange.setAdapter(new ExchangeAdapter(getApplicationContext(), list));
                                    }
                                }
                                tvIntegral.setText(integralShopGoodsBean.getData().getIntegration()+"");
                            }else if(i== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_Exchange.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), integralShopGoodsBean.getHeader().getMsg());
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
