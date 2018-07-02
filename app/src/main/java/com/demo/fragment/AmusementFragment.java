package com.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.adapter.AmusementGridAdapter;
import com.demo.adapter.AmusementListviewAdapter;
import com.demo.amusement.activity.Activity_DetailedList;
import com.demo.amusement.activity.Activity_ScenicActivities;
import com.demo.amusement.activity.Activity_VideoStrategy;
import com.demo.amusement.activity.Activity_WonderfulTravels;
import com.demo.amusement.activity.Activity_WriteBlogs;
import com.demo.amusement.bean.CircleCommentBean;
import com.demo.demo.myapplication.R;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
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
 * 游乐圈
 * Created by Administrator on 2016/7/20 0020.
 */
public class AmusementFragment extends Fragment implements XListView.IXListViewListener {


    CircleCommentBean circleCommentBean = new CircleCommentBean();
    int mPage = 1;//页数
    boolean judge_Refresh = true;
    List<CircleCommentBean.DataBean.RowsBean> list;
    AmusementListviewAdapter adapter;
    GridView gdAmusement;

    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.rl_topbar)
    RelativeLayout rlTopbar;
    @Bind(R.id.iv_frag_science_top)
    ImageView ivFragScienceTop;
    @Bind(R.id.lv_amusement)
    XListView lvAmusement;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amusement, null);
        ButterKnife.bind(this, view);

        setHeader();
        //gird单击事件和适配
        gdAmusement.setAdapter(new AmusementGridAdapter(getContext()));
        gdAmusement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0://景区活动
                        intent.setClass(getContext(), Activity_ScenicActivities.class);
                        startActivity(intent);
                        break;
                    case 1://视频攻略
                        intent.setClass(getContext(), Activity_VideoStrategy.class);
                        startActivity(intent);
                        break;
                    case 2://精彩游记
                        intent.setClass(getContext(), Activity_WonderfulTravels.class);
                        startActivity(intent);
                        break;
                    case 3://必游清单
                        intent.setClass(getContext(), Activity_DetailedList.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        tvRight.setOnClickListener(new SetOnClick());
        ivFragScienceTop.setOnClickListener(new SetOnClick());

        mPage = 1;
        list = new ArrayList<>();
        adapter = new AmusementListviewAdapter(getActivity(), list);
        lvAmusement.setAdapter(adapter);

        //启用或禁用上拉加载更多功能。
        lvAmusement.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        lvAmusement.setPullRefreshEnable(true);

        lvAmusement.setXListViewListener(this);

        lvAmusement.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (lvAmusement.getLastVisiblePosition() == (lvAmusement.getCount() - 1)) {
                        }
                        // 判断滚动到顶部
                        if (lvAmusement.getFirstVisiblePosition() == 0) {
                            ivFragScienceTop.setVisibility(View.GONE);
                        } else {
                            ivFragScienceTop.setVisibility(View.VISIBLE);
                        }

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        initAmuseFrag();
        return view;
    }

    private void setHeader() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.xlistview_amusement_header, null);
        lvAmusement.addHeaderView(view);
        gdAmusement = (GridView) view.findViewById(R.id.gd_amusement);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    //获取首页游记
    private void initAmuseFrag() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getActivity(), SpName.token, ""));
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 10 + "");
        params.addQueryStringParameter("type", 0 + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.circle_wondertravel, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            circleCommentBean = new Gson().fromJson(responseInfo.result, CircleCommentBean.class);
                            if (circleCommentBean.getHeader().getStatus() == 0) {
                                list.addAll(circleCommentBean.getData().getRows());
                                adapter.notifyDataSetChanged();
                                //分页
                                onLoad();
                                if (circleCommentBean.getData().getRows().size() < 10) {
                                    judge_Refresh = false;
                                    lvAmusement.setFooterTextView("已加载显示完全部内容");
                                }
                            } else if (circleCommentBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getActivity());
                            } else {
                                ToastUtil.show(getActivity(), circleCommentBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getContext(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), "连接网络失败");
                    }
                });
    }

    private class SetOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_frag_science_top://回到顶部
                    lvAmusement.smoothScrollToPosition(0);
                    break;
                case R.id.tv_right://写游记
                    if(SpUtil.getString(getActivity(), SpName.token, "").equals("")){
                        MainActivity.state_Three(getActivity());
                    }else{
                        Intent intent = new Intent(getActivity(), Activity_WriteBlogs.class);
                        startActivity(intent);
                    }

                    break;
            }
        }
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
        lvAmusement.setFooterTextView("显示更多");
        initAmuseFrag();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvAmusement.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新
        if (judge_Refresh) {
            mPage = mPage + 1;
            initAmuseFrag();
        } else {
            onLoad();
            lvAmusement.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvAmusement.stopRefresh();
        lvAmusement.stopLoadMore();
    }
}
