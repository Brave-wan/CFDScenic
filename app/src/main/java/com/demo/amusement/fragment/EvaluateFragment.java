package com.demo.amusement.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.amusement.activity.Activity_OtherInformation;
import com.demo.amusement.adapter.CommentToOtherAdapter;
import com.demo.amusement.bean.CommentToOtherBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.MyCommentBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.myListView.XListView;
import com.demo.view.myListView.XNoScrollListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.protocol.HTTP;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 点击头像--我评价的那些景点
 * Created by Administrator on 2016/8/2 0002.
 */
public class EvaluateFragment extends Fragment implements XListView.IXListViewListener {

    MyCommentBean commentToOtherBean = new MyCommentBean();

    int mPage = 1;//页数
    boolean judge_Refresh = true;
    List<MyCommentBean.DataBean> list;
    CommentToOtherAdapter adapter;

    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;
    @Bind(R.id.lv_fragmentAll)
    XListView lvFragmentAll;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        ButterKnife.bind(this, view);

//        lvFragmentAll.setAdapter(new EvaluateAdapter(getContext()));

        mPage = 1;
        list = new ArrayList<>();
        adapter = new CommentToOtherAdapter(getActivity(), list);
        lvFragmentAll.setAdapter(adapter);

        //启用或禁用上拉加载更多功能。
        lvFragmentAll.setPullLoadEnable(false);
        //启用或禁用下拉刷新功能。
        lvFragmentAll.setPullRefreshEnable(false);

        lvFragmentAll.setXListViewListener(this);

        initGetComment();
        return view;
    }

    private void initGetComment() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getActivity(), SpName.token, ""));
//        params.addQueryStringParameter("rows", 10 + "");
//        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("toUserId", Activity_OtherInformation.uu + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
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
                            commentToOtherBean = new Gson().fromJson(responseInfo.result, MyCommentBean.class);
                            if (commentToOtherBean.getHeader().getStatus() == 0) {
                                if (commentToOtherBean.getData()!=null){
                                    list.addAll(commentToOtherBean.getData());
                                    adapter.notifyDataSetChanged();
                                    //分页
                                    onLoad();
                                    if (commentToOtherBean.getData().size() < 10) {
                                        judge_Refresh = false;
                                        lvFragmentAll.setFooterTextView("已加载显示完全部内容");
                                    }
                                }else {
                                    judge_Refresh = false;
                                    lvFragmentAll.setFooterTextView("已加载显示完全部内容");
                                }

                            } else if (commentToOtherBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getActivity());
                            } else {
                                ToastUtil.show(getActivity(), "失败");
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getActivity(), "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getActivity(), "连接网络失败");
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        list.clear();
        judge_Refresh = true;
        lvFragmentAll.setFooterTextView("显示更多");
        initGetComment();
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
            initGetComment();
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
