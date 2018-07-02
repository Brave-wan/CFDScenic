package com.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.adapter.HomeSeienceListViewadapter;
import com.demo.adapter.ScenicGridAdapter;
import com.demo.demo.myapplication.R;
import com.demo.scenicspot.activity.BriefIntroductionActivity;
import com.demo.scenicspot.activity.NewsMustknowActivity;
import com.demo.scenicspot.activity.PlanActivity;
import com.demo.scenicspot.activity.ScenicSpotSearchActivity;
import com.demo.scenicspot.activity.ScienceMoreActivity;
import com.demo.scenicspot.activity.ScienceSpotActivity;
import com.demo.scenicspot.bean.CarouselImgBean;
import com.demo.scenicspot.bean.ScenicSpotListBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.NoScrollGridView;
import com.demo.view.banner.Banner;
import com.demo.view.banner.BannerConfig;
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
 * 景区
 * Created by Administrator on 2016/7/20 0020.
 */
public class ScenicFragment extends Fragment implements XListView.IXListViewListener {


    List<ScenicSpotListBean.DataBean> list;
    CarouselImgBean carouselImgBean;
    int mPage = 1;//页数
    boolean judge_Refresh = true;
    Intent intent=new Intent();
    HomeSeienceListViewadapter homeSeienceListViewadapter;
    NoScrollGridView gdScenic;
    Banner banner;

    @Bind(R.id.iv_frag_science_search)
    ImageView ivFragScienceSearch;
    @Bind(R.id.lv_homefrag_science_hotspot)
    XListView lvHomefragScienceHotspot;
    @Bind(R.id.iv_frag_science_top)
    ImageView ivFragScienceTop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenic, null);
        ButterKnife.bind(this, view);

        setHeader();

        mPage = 1;
        list = new ArrayList<>();
        homeSeienceListViewadapter = new HomeSeienceListViewadapter(getContext(), list);
        lvHomefragScienceHotspot.setAdapter(homeSeienceListViewadapter);

        init();

        //启用或禁用上拉加载更多功能。
        lvHomefragScienceHotspot.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        lvHomefragScienceHotspot.setPullRefreshEnable(true);

        lvHomefragScienceHotspot.setXListViewListener(this);


        lvHomefragScienceHotspot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ScienceMoreActivity.class);
                intent.putExtra("id", list.get(position).getId() + "");
                startActivity(intent);
            }
        });

        carouselImg();//首页轮播图
        scenicSpotList();//列表


        lvHomefragScienceHotspot.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (lvHomefragScienceHotspot.getLastVisiblePosition() == (lvHomefragScienceHotspot.getCount() - 1)) {
                        }
                        // 判断滚动到顶部
                        if(lvHomefragScienceHotspot.getFirstVisiblePosition() == 0){
                            ivFragScienceTop.setVisibility(View.GONE);
                        }else {
                            ivFragScienceTop.setVisibility(View.VISIBLE);
                        }

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        return view;
    }

    private void setHeader() {
        View viewHeader = LayoutInflater.from(getActivity()).inflate(R.layout.xlistview_scenic_header, null);
        lvHomefragScienceHotspot.addHeaderView(viewHeader);

        banner = (Banner) viewHeader.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setDelayTime(3500);
        //轮播图单击事件
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ScienceMoreActivity.class);
                intent.putExtra("id", carouselImgBean.getData().get(position - 1).getLink_id() + "");
                startActivity(intent);
            }
        });

        gdScenic = (NoScrollGridView) viewHeader.findViewById(R.id.gd_scenic);

        LinearLayout ll_frag_science_more = (LinearLayout) viewHeader.findViewById(R.id.ll_frag_science_more);
        ll_frag_science_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getActivity(), ScienceSpotActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        gdScenic.setAdapter(new ScenicGridAdapter(getContext()));
        gdScenic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://景点纵览
                        intent.setClass(getActivity(), ScienceSpotActivity.class);
                        startActivity(intent);
                        break;
                    case 1://新闻须知
                        intent.setClass(getContext(), NewsMustknowActivity.class);
                        startActivity(intent);
                        break;
                    case 2://路线规划
                        intent.setClass(getContext(), PlanActivity.class);
                        startActivity(intent);
                        break;
                    case 3://湿地简介
                        intent.setClass(getContext(), BriefIntroductionActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_frag_science_search, /*R.id.ll_frag_science_more,*/ R.id.iv_frag_science_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_frag_science_search://景点搜索
                intent.setClass(getActivity(), ScenicSpotSearchActivity.class);
                startActivity(intent);
                break;
           /* case R.id.ll_frag_science_more:
                intent.setClass(getActivity(), ScienceSpotActivity.class);
                startActivity(intent);
                break;*/
            case R.id.iv_frag_science_top://回到顶部
                //svScience.scrollTo(0, 0);
                lvHomefragScienceHotspot.smoothScrollToPosition(0);
                break;
        }
    }

    //首页轮播图
    private void carouselImg() {
        RequestParams params = new RequestParams();
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.carouselImg, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            carouselImgBean = new Gson().fromJson(responseInfo.result, CarouselImgBean.class);
                            int i = carouselImgBean.getHeader().getStatus();
                            if (i == 0) {
                                String[] image = new String[carouselImgBean.getData().size()];
                                for (int x = 0; x < carouselImgBean.getData().size(); x++) {
                                    image[x] = carouselImgBean.getData().get(x).getImg_url();
                                }
                                banner.setImages(image);
                            } else {
                                ToastUtil.show(getContext(), carouselImgBean.getHeader().getMsg());
                            }

                        } catch (Exception e) {
                            ToastUtil.show(getContext(), e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                    }
                });

    }


    //景点列表
    private void scenicSpotList() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 4 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.scenicSpotList, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            ScenicSpotListBean scenicSpotListBean = new Gson().fromJson(responseInfo.result, ScenicSpotListBean.class);
                            int i = scenicSpotListBean.getHeader().getStatus();
                            if (i == 0) {
                                list.addAll(scenicSpotListBean.getData());
                                homeSeienceListViewadapter.notifyDataSetChanged();
                                //分页
                                onLoad();
                                if (scenicSpotListBean.getData().size() < 10) {
                                    judge_Refresh = false;
                                    lvHomefragScienceHotspot.setFooterTextView("已加载显示完全部内容");
                                }
                            } else {
                                ToastUtil.show(getContext(), scenicSpotListBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getContext(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), s);
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
        lvHomefragScienceHotspot.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新

        if (judge_Refresh) {
            mPage = mPage + 1;
            scenicSpotList();
        } else {
            onLoad();
            lvHomefragScienceHotspot.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvHomefragScienceHotspot.stopRefresh();
        lvHomefragScienceHotspot.stopLoadMore();
    }



}
