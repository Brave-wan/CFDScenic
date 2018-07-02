package com.demo.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.adapter.EvaluateAdapter;
import com.demo.adapter.VideoStrategyAdapter;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.MyCommentBean;
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
 * Created by Administrator on 2016/8/9 0009.
 */
public class MyRecreationJqFragment extends Fragment implements XListView.IXListViewListener {


    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;
    @Bind(R.id.lv_fragmentAll)
    XListView lvFragmentAll;

    int mPage = 1;//页数
    boolean judge_Refresh = true;
    List<MyCommentBean.DataBean> list;
    EvaluateAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        ButterKnife.bind(this, view);

        mPage = 1;
        list = new ArrayList<>();
        adapter = new EvaluateAdapter(getContext(), list);
        lvFragmentAll.setAdapter(adapter);
        myComment();

        //启用或禁用上拉加载更多功能。
        lvFragmentAll.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        //lvDetailedList.setPullRefreshEnable(false);

        lvFragmentAll.setXListViewListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void myComment() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getContext(), SpName.token, ""));
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 10 + "");
        params.addQueryStringParameter("toUserId", SpUtil.getString(getContext(), SpName.userId, ""));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.myComment, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(getActivity(),R.style.AlertDialogStyle);
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
                            MyCommentBean myCommentBean = new Gson().fromJson(responseInfo.result, MyCommentBean.class);
                            int i = myCommentBean.getHeader().getStatus();
                            if (i == 0) {
                                if (myCommentBean.getData() != null) {
                                    list.addAll(myCommentBean.getData());
                                    adapter.notifyDataSetChanged();
                                    //分页
                                    onLoad();
                                    if (myCommentBean.getData().size() < 10) {
                                        judge_Refresh = false;
                                        lvFragmentAll.setFooterTextView("已加载显示完全部内容");
                                    }
                                } else {
                                    //分页
                                    onLoad();
                                    judge_Refresh = false;
                                    lvFragmentAll.setFooterTextView("已加载显示完全部内容");
                                }

                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getContext());
                            } else {
                                ToastUtil.show(getContext(), myCommentBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getContext(), "解析数据错误");
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), "连接网络失败");
                    }
                });
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        list.clear();
        judge_Refresh = true;
        lvFragmentAll.setFooterTextView("显示更多");
        myComment();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvFragmentAll.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新

        if (judge_Refresh) {
            mPage = mPage + 1;
            myComment();
        } else {
            onLoad();
            lvFragmentAll.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvFragmentAll.stopRefresh();
        lvFragmentAll.stopLoadMore();
    }
}
