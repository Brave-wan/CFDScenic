package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demo.adapter.ExchangeRecordAdapter;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.SelectDealMessageBean;
import com.demo.demo.myapplication.R;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
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
 * 我的钱包--兑换记录
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_ExchangeRecord extends Activity {

    @Bind(R.id.lv_exchangeRecord)
    ListView lvExchangeRecord;

    int page=1;//页数
    List<SelectDealMessageBean.DataBean.ListBean> rowsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_record);
        ButterKnife.bind(this);

        selectDealMessage();


        lvExchangeRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                //判断
                if (rowsBean.get(position).getType()==5){
                    intent.setClass(getApplicationContext(),Activity_OrderDetailsTicket.class);//门票
                    intent.putExtra("id",rowsBean.get(position).getOrderId()+"");
                }else if (rowsBean.get(position).getType()==4){
                    intent.setClass(getApplicationContext(),Activity_OrderDetailsKeepsake.class);//纪念品
                    intent.putExtra("id",rowsBean.get(position).getOrderId()+"");
                }
                startActivity(intent);
            }
        });
    }


    //兑换列表
    private void selectDealMessage(){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("page", page + "");
        params.addQueryStringParameter("rows", 6 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.selectDealMessage, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ExchangeRecord.this,R.style.AlertDialogStyle);
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
                            SelectDealMessageBean selectDealMessageBean=new Gson().fromJson(responseInfo.result,SelectDealMessageBean.class);
                            int i=selectDealMessageBean.getHeader().getStatus();
                            if (i==0){
                                rowsBean=selectDealMessageBean.getData().getList();
                                lvExchangeRecord.setAdapter(new ExchangeRecordAdapter(getApplicationContext(),rowsBean));
                            }else if(i== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ExchangeRecord.this);
                            }else {
                                ToastUtil.show(getApplicationContext(),selectDealMessageBean.getHeader().getMsg());
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
