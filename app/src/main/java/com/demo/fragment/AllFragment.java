package com.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.adapter.AmusementListviewAdapter;
import com.demo.demo.myapplication.R;
import com.demo.scenicspot.adapter.CommentAdapter;
import com.demo.scenicspot.bean.ScenicSpotCommentBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
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
 * 作者： on 2016/8/2 0002 14:56
 */
public class AllFragment extends Fragment implements XListView.IXListViewListener{

    CommentAdapter adapter;
    List<ScenicSpotCommentBean.DataBean> databean;
    @Bind(R.id.lv_frag_amap)
    XListView lvFragAmap;
    int mPage = 1;//页数
    boolean judge_Refresh = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.science_fragment_amap, container,
                false);
        ButterKnife.bind(this, view);
        mPage = 1;
        databean = new ArrayList<>();
        adapter = new CommentAdapter(getActivity(), databean);
        lvFragAmap.setAdapter(adapter);


        //启用或禁用上拉加载更多功能。
        lvFragAmap.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        lvFragAmap.setPullRefreshEnable(true);

        lvFragAmap.setXListViewListener(this);

        scenicSpotComment();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void scenicSpotComment() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", getArguments().getString("id") + "");
        params.addQueryStringParameter("page", 1 + "");
        params.addQueryStringParameter("rows", 10 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.scenicSpotComment, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            ScenicSpotCommentBean scenicSpotCommentBean = new Gson().fromJson(responseInfo.result, ScenicSpotCommentBean.class);
                            int i = scenicSpotCommentBean.getHeader().getStatus();
                            if (i == 0) {
//                                databean = scenicSpotCommentBean.getData();
//                                adapter = new CommentAdapter(getActivity(), databean);
//                                lvFragAmap.setAdapter(adapter);

                                databean.addAll(scenicSpotCommentBean.getData());
                                adapter.notifyDataSetChanged();
                                //分页
                                onLoad();
                                if (scenicSpotCommentBean.getData().size() < 10) {
                                    judge_Refresh = false;
                                    lvFragAmap.setFooterTextView("已加载显示完全部内容");
                                }


                            } else {
                                ToastUtil.show(getContext(), scenicSpotCommentBean.getHeader().getMsg());
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
        databean.clear();
        judge_Refresh = true;
        lvFragAmap.setFooterTextView("显示更多");
        scenicSpotComment();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvFragAmap.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新
        if (judge_Refresh) {
            mPage = mPage + 1;
            scenicSpotComment();
        } else {
            onLoad();
            lvFragAmap.setFooterTextView("已加载显示完全部内容");
        }
    }
    private void onLoad() {
        lvFragAmap.stopRefresh();
        lvFragAmap.stopLoadMore();
    }
}
