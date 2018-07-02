package com.demo.scenicspot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.demo.demo.myapplication.R;
import com.demo.scenicspot.adapter.NewsKnowAdapter;
import com.demo.scenicspot.bean.PressListBean;
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

/**
 * 作者： on 2016/8/1 0001 17:17
 */
public class NewsMustknowActivity extends Activity implements XListView.IXListViewListener {
    @Bind(R.id.lv_newsknow)
    XListView lvNewsknow;

    List<PressListBean.DataBean.RowsBean> rowsBean;
    NewsKnowAdapter newsKnowAdapter;
    public int mPage = 1;//当前页数
    boolean judge_Refresh = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsknow);
        ButterKnife.bind(this);

        mPage = 1;
        rowsBean = new ArrayList<>();
        newsKnowAdapter = new NewsKnowAdapter(NewsMustknowActivity.this, rowsBean);
        lvNewsknow.setAdapter(newsKnowAdapter);

        pressList();
        lvNewsknow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsMustknowActivity.this, NewsDetailsActivity.class);
                intent.putExtra("id", rowsBean.get(position-1).getId()+"");
                startActivity(intent);
            }
        });

        //启用或禁用上拉加载更多功能。
        lvNewsknow.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        //mListView.setPullRefreshEnable(true);

        lvNewsknow.setXListViewListener(this);
    }

    private void pressList() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 10 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.pressList, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(NewsMustknowActivity.this,R.style.AlertDialogStyle);
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
                            PressListBean pressListBean = new Gson().fromJson(responseInfo.result, PressListBean.class);
                            int i = pressListBean.getHeader().getStatus();
                            if (i == 0) {
                                rowsBean.addAll(pressListBean.getData().getRows());
                                newsKnowAdapter.notifyDataSetChanged();
                                //分页
                                onLoad();
                                if (pressListBean.getData().getRows().size() < 10) {
                                    judge_Refresh = false;
                                    lvNewsknow.setFooterTextView("已加载显示完全部内容");
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), pressListBean.getHeader().getMsg());
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
        rowsBean.clear();
        judge_Refresh = true;
        pressList();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvNewsknow.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新

        if (judge_Refresh) {
            mPage = mPage + 1;
            pressList();
        } else {
            onLoad();
            lvNewsknow.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvNewsknow.stopRefresh();
        lvNewsknow.stopLoadMore();
    }
}
