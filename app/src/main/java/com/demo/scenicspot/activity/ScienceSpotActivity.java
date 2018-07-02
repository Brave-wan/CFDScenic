package com.demo.scenicspot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.adapter.HomeSeienceListViewadapter;
import com.demo.demo.myapplication.R;
import com.demo.scenicspot.adapter.ScienceSpotLvAdapter;
import com.demo.scenicspot.bean.FindVisitorListBean;
import com.demo.scenicspot.bean.ScenicSpotListBean;
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
 * 作者： on 2016/7/29 0029 16:03
 * 景点 listview
 */
public class ScienceSpotActivity extends Activity implements XListView.IXListViewListener {
    ScienceSpotLvAdapter adapter;

    @Bind(R.id.lv_aty_sciencepot)
    XListView lvAtySciencepot;

    List<FindVisitorListBean.DataBean.RowsBean> list;
    int i = 1;//页数
    public int mPage = 1;
    boolean judge_Refresh = true;

    @Bind(R.id.im_left_topbar)
    ImageView imLeftTopbar;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.iv_right)
    ImageView ivRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sciencespot);
        ButterKnife.bind(this);
        scenicSpotList();

        mPage = 1;
        list = new ArrayList<>();
        adapter = new ScienceSpotLvAdapter(ScienceSpotActivity.this, list);
        lvAtySciencepot.setAdapter(adapter);

        //启用或禁用上拉加载更多功能。
        lvAtySciencepot.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        //mListView.setPullRefreshEnable(true);

        lvAtySciencepot.setXListViewListener(this);

    }

    @OnClick({R.id.im_left_topbar, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left_topbar:
                finish();
                break;
            case R.id.iv_right:
                Intent intent = new Intent(getApplicationContext(), ScenicSpotSearchActivity.class);
                startActivity(intent);
                break;
        }
    }


    //景点列表
    private void scenicSpotList() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 10 + "");
        HttpUtils http = new HttpUtils();
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.findVisitorsList, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(ScienceSpotActivity.this,R.style.AlertDialogStyle);
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
                            FindVisitorListBean scenicSpotListBean = new Gson().fromJson(responseInfo.result, FindVisitorListBean.class);
                            int i = scenicSpotListBean.getHeader().getStatus();
                            if (i == 0) {
                                if (scenicSpotListBean.getData().getRows() != null) {
                                    list.addAll(scenicSpotListBean.getData().getRows());
                                    adapter.notifyDataSetChanged();

                                    //分页
                                    onLoad();
                                    if (scenicSpotListBean.getData().getRows().size() < 10) {
                                        judge_Refresh = false;
                                        lvAtySciencepot.setFooterTextView("已加载显示完全部内容");
                                    }
                                } else {
                                    judge_Refresh = false;
                                    lvAtySciencepot.setFooterTextView("已加载显示完全部内容");
                                }

                            } else {
                                ToastUtil.show(getApplicationContext(), scenicSpotListBean.getHeader().getMsg());
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
        scenicSpotList();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvAtySciencepot.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新

        if (judge_Refresh) {
            mPage = mPage + 1;
            scenicSpotList();
        } else {
            onLoad();
            lvAtySciencepot.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvAtySciencepot.stopRefresh();
        lvAtySciencepot.stopLoadMore();
    }

}
