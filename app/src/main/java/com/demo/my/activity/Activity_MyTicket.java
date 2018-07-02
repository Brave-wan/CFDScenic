package com.demo.my.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.demo.adapter.MyTicketAdapter;
import com.demo.demo.myapplication.R;
import com.demo.my.bean.GetMyTicketsBean;
import com.demo.scenicspot.activity.PayResultActivity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的门票--查看全部
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_MyTicket extends Activity {

    @Bind(R.id.lv_MyTicket)
    ListView lvMyTicket;
    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;


    public MyTicketAdapter myTicketAdapter;
    List<GetMyTicketsBean.DataBean> list=new ArrayList<>();

    String type = "0";
    @Bind(R.id.ll_wudingdan)
    public LinearLayout llWudingdan;
    public static boolean myTicket=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        ButterKnife.bind(this);
        myTicket=true;
        type = getIntent().getStringExtra("type");
        getMyTickets();
        lvMyTicket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Activity_MyTicketDetails.class);
                intent.putExtra("orderCode", list.get(position).getTicketslist().get(0).getOrder_code() + "");
                //startActivity(intent);
                startActivityForResult(intent, 0x0001);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myTicket){
            myTicket=false;
            getMyTickets();
        }
    }

    private void getMyTickets() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpName.token, ""));
        params.addQueryStringParameter("type", type);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getMyTickets, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_MyTicket.this,R.style.AlertDialogStyle);
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111",responseInfo.result);
                        dialogProgressbar.dismiss();
                        try {
                            GetMyTicketsBean getMyTicketsBean = new Gson().fromJson(responseInfo.result, GetMyTicketsBean.class);
                            int i = getMyTicketsBean.getHeader().getStatus();
                            if (i == 0) {
                                list = getMyTicketsBean.getData();
                                if (list == null) {
                                    llWudingdan.setVisibility(View.VISIBLE);
                                    lvMyTicket.setVisibility(View.GONE);
                                } else {
                                    if (list.size() > 0) {
                                        lvMyTicket.setVisibility(View.VISIBLE);
                                        myTicketAdapter = new MyTicketAdapter(type,Activity_MyTicket.this, list);
                                        lvMyTicket.setAdapter(myTicketAdapter);
                                    } else {
                                        llWudingdan.setVisibility(View.VISIBLE);
                                        lvMyTicket.setVisibility(View.GONE);
                                    }
                                }

                            } else {
                                ToastUtil.show(getApplicationContext(), getMyTicketsBean.getHeader().getMsg());
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
