package com.demo.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.adapter.WonderfulTravelsAdapter;
import com.demo.amusement.bean.CircleCommentBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.adapter.MyWonderfulTravelsAdapter;
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
 * 我的--我的游乐圈--精彩游记
 * Created by Administrator on 2016/8/8 0008.
 */
public class MyRecreationJcFragment extends Fragment implements XListView.IXListViewListener {


    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;
    @Bind(R.id.lv_fragmentAll)
    XListView lvFragmentAll;

    List<CircleCommentBean.DataBean.RowsBean> list;
    MyWonderfulTravelsAdapter adapter;
    int mPage = 1;//页数
    boolean judge_Refresh = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        ButterKnife.bind(this, view);

        mPage = 1;
        list = new ArrayList<>();
        adapter = new MyWonderfulTravelsAdapter(getActivity(), list);
        lvFragmentAll.setAdapter(adapter);
        getTraveLogs();
        //lvFragmentAll.setAdapter(new MyRecreationJcAdapter(getContext()));

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


    private void getTraveLogs() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getContext(), SpName.token, ""));
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 10 + "");
        params.addQueryStringParameter("type", 1 + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.circle_wondertravel, params,
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
                            CircleCommentBean circleCommentBean = new Gson().fromJson(responseInfo.result, CircleCommentBean.class);
                            if (circleCommentBean.getHeader().getStatus() == 0) {
                                if (circleCommentBean.getData().getRows() != null) {
                                    list.addAll(circleCommentBean.getData().getRows());
                                    adapter.notifyDataSetChanged();
                                    //分页
                                    onLoad();
                                    if (circleCommentBean.getData().getRows().size() < 10) {
                                        judge_Refresh = false;
                                        lvFragmentAll.setFooterTextView("已加载显示完全部内容");
                                    }
                                } else {
                                    //分页
                                    onLoad();
                                    judge_Refresh = false;
                                    lvFragmentAll.setFooterTextView("已加载显示完全部内容");
                                }
                            } else if (circleCommentBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getContext());
                            } else {
                                ToastUtil.show(getContext(), circleCommentBean.getHeader().getMsg());
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
        getTraveLogs();
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
            getTraveLogs();
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
