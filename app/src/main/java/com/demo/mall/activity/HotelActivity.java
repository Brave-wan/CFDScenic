package com.demo.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.demo.demo.myapplication.R;
import com.demo.mall.adapter.HotelChatAdapter;
import com.demo.mall.bean.FindHotelBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.myListView.XListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/8/6 0006 15:41
 */
public class HotelActivity extends Activity implements XListView.IXListViewListener {
    @Bind(R.id.im_hotel_back)
    ImageView imHotelBack;
    @Bind(R.id.lv_aty_hotel)
    XListView lvAtyHotel;

    public int mPage = 1;
    boolean judge_Refresh = true;
    HotelChatAdapter hotelChatAdapter;
    List<FindHotelBean.DataBean.RowsBean> list;

    public String start;
    public String end;
    public String total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_hotel);
        ButterKnife.bind(this);

        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        total = getIntent().getStringExtra("total");

        mPage = 1;
        list = new ArrayList<>();
        hotelChatAdapter = new HotelChatAdapter(HotelActivity.this, list);
        lvAtyHotel.setAdapter(hotelChatAdapter);

        //启用或禁用上拉加载更多功能。
        lvAtyHotel.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        //mListView.setPullRefreshEnable(true);

        lvAtyHotel.setXListViewListener(this);

        findHotel();

    }

    @OnClick({R.id.im_hotel_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_hotel_back:
                finish();
                break;
        }
    }


    private void findHotel() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("startDate", getIntent().getStringExtra("start"));
        params.addQueryStringParameter("endDate", getIntent().getStringExtra("end"));
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 10 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.findHotelTwo, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(HotelActivity.this,R.style.AlertDialogStyle);
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
                            FindHotelBean findHotelBean = new Gson().fromJson(responseInfo.result, FindHotelBean.class);
                            int i = findHotelBean.getHeader().getStatus();
                            if (i == 0) {
                                if (findHotelBean.getData().getRows() != null) {
                                    list.addAll(findHotelBean.getData().getRows());
                                    hotelChatAdapter.notifyDataSetChanged();

                                    //分页
                                    onLoad();
                                    if (mPage == findHotelBean.getData().getLastPage()) {
                                        judge_Refresh = false;
                                        lvAtyHotel.setFooterTextView("已加载显示完全部内容");
                                    }
                                } else {
                                    judge_Refresh = false;
                                    lvAtyHotel.setFooterTextView("已加载显示完全部内容");
                                }


                            } else {
                                ToastUtil.show(getApplicationContext(), findHotelBean.getHeader().getMsg());
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

    @Override
    public void onRefresh() {
        mPage = 1;
        list.clear();
        judge_Refresh = true;
        findHotel();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvAtyHotel.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新

        if (judge_Refresh) {
            mPage = mPage + 1;
            findHotel();
        } else {
            onLoad();
            lvAtyHotel.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvAtyHotel.stopRefresh();
        lvAtyHotel.stopLoadMore();
    }
}
