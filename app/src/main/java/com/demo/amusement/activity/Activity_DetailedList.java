package com.demo.amusement.activity;

import android.app.Activity;
import android.os.Bundle;

import com.demo.adapter.WonderfulTravelsAdapter;
import com.demo.amusement.bean.CircleCommentBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
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
 * 游乐圈--必游清单
 * Created by Administrator on 2016/7/26 0026.
 */
public class Activity_DetailedList extends Activity implements XListView.IXListViewListener{

    @Bind(R.id.lv_detailedList)
    XListView lvDetailedList;
    CircleCommentBean circleCommentBean = new CircleCommentBean();

    int mPage = 1;//页数
    boolean judge_Refresh = true;
    List<CircleCommentBean.DataBean.RowsBean> list;
    WonderfulTravelsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_list);
        ButterKnife.bind(this);

        mPage = 1;
        list = new ArrayList<>();
        adapter=new WonderfulTravelsAdapter(Activity_DetailedList.this, list);
        lvDetailedList.setAdapter(adapter);
        initDetail();

        //启用或禁用上拉加载更多功能。
        lvDetailedList.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        //lvDetailedList.setPullRefreshEnable(false);

        lvDetailedList.setXListViewListener(this);
    }

    private void initDetail() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_DetailedList.this, SpName.token, ""));
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 10 + "");
        params.addQueryStringParameter("type", 2 + "");
        params.addQueryStringParameter("isAll", 0 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.configResponseTextCharset("UTF_8");
        http.send(HttpRequest.HttpMethod.GET, URL.circleAllGrid, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_DetailedList.this,R.style.AlertDialogStyle);
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        String s = responseInfo.result;
                        try {
                            circleCommentBean = new Gson().fromJson(responseInfo.result, CircleCommentBean.class);
                            if (circleCommentBean.getHeader().getStatus() == 0) {
                                list.addAll(circleCommentBean.getData().getRows());
                                adapter.notifyDataSetChanged();
                                //分页
                                onLoad();
                                if (circleCommentBean.getData().getRows().size() < 10) {
                                    judge_Refresh = false;
                                    lvDetailedList.setFooterTextView("已加载显示完全部内容");
                                }

                            } else if (circleCommentBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_DetailedList.this);
                            } else {
                                ToastUtil.show(Activity_DetailedList.this, circleCommentBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_DetailedList.this, "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_DetailedList.this, "连接网络失败");
                    }
                });
    }


    @Override
    public void onRefresh() {
        mPage = 1;
        list.clear();
        judge_Refresh = true;
        lvDetailedList.setFooterTextView("显示更多");
        initDetail();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvDetailedList.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新

        if (judge_Refresh) {
            mPage = mPage + 1;
            initDetail();
        } else {
            onLoad();
            lvDetailedList.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvDetailedList.stopRefresh();
        lvDetailedList.stopLoadMore();
    }
}
