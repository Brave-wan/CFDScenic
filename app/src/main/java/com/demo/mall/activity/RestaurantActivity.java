package com.demo.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.demo.demo.myapplication.R;
import com.demo.mall.adapter.FindAllRestaurantBean;
import com.demo.mall.adapter.RestaurantChatAdapter;
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
 * 饭店列表
 * 作者：sonnerly on 2016/8/6 0006 15:02
 */
public class RestaurantActivity extends Activity implements XListView.IXListViewListener {
    @Bind(R.id.im_restaurant_back)
    ImageView imRestaurantBack;
    @Bind(R.id.lv_aty_restaurant)
    XListView lvAtyRestaurant;

    RestaurantChatAdapter restaurantChatAdapter;
    List<FindAllRestaurantBean.DataBean.RowsBean> list;

    public int mPage = 1;
    boolean judge_Refresh = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_restaurant);
        ButterKnife.bind(this);

        mPage = 1;
        list = new ArrayList<>();
        restaurantChatAdapter = new RestaurantChatAdapter(RestaurantActivity.this, list);
        lvAtyRestaurant.setAdapter(restaurantChatAdapter);

        findAllRestaurant();

        lvAtyRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RestaurantMoreActivity.class);
                intent.putExtra("date", getIntent().getStringExtra("date"));
                intent.putExtra("time", getIntent().getStringExtra("time"));
                intent.putExtra("id", list.get(position-1).getId() + "");
                startActivity(intent);
            }
        });

        //启用或禁用上拉加载更多功能。
        lvAtyRestaurant.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        //mListView.setPullRefreshEnable(true);

        lvAtyRestaurant.setXListViewListener(this);
    }


    @OnClick({R.id.im_restaurant_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_restaurant_back:
                finish();
                break;
        }
    }

    private void findAllRestaurant() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 10 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.findAllRestaurant, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(RestaurantActivity.this,R.style.AlertDialogStyle);
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
                            FindAllRestaurantBean findAllRestaurantBean = new Gson().fromJson(responseInfo.result, FindAllRestaurantBean.class);
                            int i = findAllRestaurantBean.getHeader().getStatus();
                            if (i == 0) {
                               list.addAll(findAllRestaurantBean.getData().getRows());
                                restaurantChatAdapter.notifyDataSetChanged();

                                //分页
                                onLoad();
                                if (mPage == findAllRestaurantBean.getData().getLastPage()) {
                                    judge_Refresh = false;
                                    lvAtyRestaurant.setFooterTextView("已加载显示完全部内容");
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), findAllRestaurantBean.getHeader().getMsg());
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
        findAllRestaurant();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvAtyRestaurant.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新

        if (judge_Refresh) {
            mPage = mPage + 1;
            findAllRestaurant();
        } else {
            onLoad();
            lvAtyRestaurant.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvAtyRestaurant.stopRefresh();
        lvAtyRestaurant.stopLoadMore();
    }
}
