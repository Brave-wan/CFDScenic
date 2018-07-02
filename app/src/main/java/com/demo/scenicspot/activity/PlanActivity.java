package com.demo.scenicspot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.demo.demo.myapplication.R;
import com.demo.scenicspot.adapter.RecommendLineAdapter;
import com.demo.scenicspot.bean.AnviRecommendBean;
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

import org.apache.http.protocol.HTTP;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 景区--路线规划
 * Created by Administrator on 2016/8/12 0012.
 */
public class PlanActivity extends Activity implements XListView.IXListViewListener{

    @Bind(R.id.lv_relineplan)
    XListView lvRelineplan;
    AnviRecommendBean anviRecommendBean = new AnviRecommendBean();
    RecommendLineAdapter adapter;
    int mPage = 1;//页数
    boolean judge_Refresh = true;
    List<AnviRecommendBean.DataBean.RowsBean> listll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ButterKnife.bind(this);

        mPage = 1;
        listll = new ArrayList<>();
        adapter=new RecommendLineAdapter(PlanActivity.this, listll);
        lvRelineplan.setAdapter(adapter);
        getPlanningOrIntroduce();
        //启用或禁用上拉加载更多功能。
        lvRelineplan.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        //lvDetailedList.setPullRefreshEnable(false);
        lvRelineplan.setXListViewListener(this);
        lvRelineplan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                for (int i = 0; i < anviRecommendBean.getData().getRows().get(position-1).getListData().size(); i++) {
//                    HashMap<String, Object> hashMap = new HashMap<>();
//                    hashMap.put("xPoint", anviRecommendBean.getData().getRows().get(position-1).getListData().get(i).getX_point());
//                    hashMap.put("yPoint", anviRecommendBean.getData().getRows().get(position-1).getListData().get(i).getY_point());
//                    list.add(hashMap);
//                }
                List<AnviRecommendBean.DataBean.RowsBean.ListDataBean> list = new ArrayList<>();
                list=anviRecommendBean.getData().getRows().get(position-1).getListData();
                Intent intent = new Intent(PlanActivity.this,RootActivity.class);
                Bundle bd = new Bundle();
                bd.putSerializable("line", (Serializable)list);
                intent.putExtras(bd);
                startActivity(intent);

            }
        });
    }

    private void getPlanningOrIntroduce() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 10 + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getScenceRecommend, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String s = responseInfo.result;//0不是景点 1播报
                        Log.i("hhhhhhhh", s);
                        try {
                            anviRecommendBean = new Gson().fromJson(responseInfo.result, AnviRecommendBean.class);
                            int i = anviRecommendBean.getHeader().getStatus();
                            if (i == 0) {
                                listll.addAll(anviRecommendBean.getData().getRows());
                                adapter.notifyDataSetChanged();
                                //分页
                                onLoad();
                                if (anviRecommendBean.getData().getRows().size() < 10) {
                                    judge_Refresh = false;
                                    lvRelineplan.setFooterTextView("已加载显示完全部内容");
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), anviRecommendBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), e.getMessage());
                    }
                });

    }
    @Override
    public void onRefresh() {
        mPage = 1;
        listll.clear();
        judge_Refresh = true;
        lvRelineplan.setFooterTextView("显示更多");
        getPlanningOrIntroduce();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvRelineplan.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新
        if (judge_Refresh) {
            mPage = mPage + 1;
            getPlanningOrIntroduce();
        } else {
            onLoad();
            lvRelineplan.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvRelineplan.stopRefresh();
        lvRelineplan.stopLoadMore();
    }

}
