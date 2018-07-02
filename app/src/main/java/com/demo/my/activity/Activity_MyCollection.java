package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.adapter.MyCollectionAdapter;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.activity.PurchaseDetailsActivity;
import com.demo.my.bean.SelectBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyTopBar;
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
 * 我的--我的收藏
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_MyCollection extends Activity implements XListView.IXListViewListener {

    boolean state = false;    //判断是否处于编辑状态
    SelectBean selectBean;
    MyCollectionAdapter myCollectionAdapter;
    public List<SelectBean.DataBean.RowsBean> list;
    public int mPage = 1;
    boolean judge_Refresh = true;

    @Bind(R.id.topbar)
    MyTopBar topbar;
    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;
    @Bind(R.id.lv_my_collection)
    XListView lvMyCollection;
    @Bind(R.id.tv_delete)
    public TextView tvDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);

        topbar.setRightTextColor("#f95600");


        topbar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state) {
                    tvDelete.setVisibility(View.GONE);
                    topbar.setRightTextColor("#f95600");
                    topbar.setRightText("管理");
                    myCollectionAdapter.setVisibility(false);
                    state = false;
                } else {
                    if (selectBean == null) {
                        return;
                    }
                    if (selectBean.getData().getRows() == null) {
                        return;
                    }
                    if (selectBean.getData().getRows().size() == 0) {
                        return;
                    }
                    tvDelete.setVisibility(View.VISIBLE);
                    topbar.setRightTextColor("#4bc4fb");
                    topbar.setRightText("完成");
                    tvDelete.setText("删除");
                    myCollectionAdapter.initList();
                    myCollectionAdapter.setVisibility(true);
                    state = true;
                }
            }
        });


        lvMyCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (state) {
                    myCollectionAdapter.setImageView(position-1);
                } else {
                    Intent intent = new Intent(Activity_MyCollection.this, PurchaseDetailsActivity.class);
                    intent.putExtra("id", list.get(position-1).getGoods_id() + "");
                    startActivity(intent);
                }
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCollectionAdapter.num==0){
                    ToastUtil.show(getApplicationContext(),"请选择商品");
                    return;
                }
                myCollectionAdapter.deleteAll();
            }
        });

        mPage = 1;
        list = new ArrayList<>();
        myCollectionAdapter = new MyCollectionAdapter(Activity_MyCollection.this, list);
        lvMyCollection.setAdapter(myCollectionAdapter);

        //启用或禁用上拉加载更多功能。
        lvMyCollection.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        //mListView.setPullRefreshEnable(true);

        lvMyCollection.setXListViewListener(this);
    }

    public void initView() {
        tvDelete.setVisibility(View.GONE);
        topbar.setRightTextColor("#f95600");
        topbar.setRightText("管理");
        myCollectionAdapter.setVisibility(false);
        state = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        mPage = 1;
        select();
    }

    public void select() {

        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpName.token, ""));
        params.addQueryStringParameter("page", mPage + "");
        params.addQueryStringParameter("rows", 10 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.select, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_MyCollection.this,R.style.AlertDialogStyle);
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
                            selectBean = new Gson().fromJson(responseInfo.result, SelectBean.class);
                            int i = selectBean.getHeader().getStatus();
                            if (i == 0) {
                                if (selectBean.getData().getRows() != null) {
                                    llWudingdan.setVisibility(View.GONE);
                                    lvMyCollection.setVisibility(View.VISIBLE);
                                    list.addAll(selectBean.getData().getRows());
                                    myCollectionAdapter.notifyDataSetChanged();
                                    //分页
                                    onLoad();
                                    if (list.size() < 10) {
                                        judge_Refresh = false;
                                        lvMyCollection.setFooterTextView("已加载显示完全部内容");
                                    }
                                } else {
                                    llWudingdan.setVisibility(View.VISIBLE);
                                    lvMyCollection.setVisibility(View.GONE);
                                    judge_Refresh = false;
                                    lvMyCollection.setFooterTextView("已加载显示完全部内容");
                                }
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_MyCollection.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), selectBean.getHeader().getMsg());
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
        select();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvMyCollection.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新

        if (judge_Refresh) {
            mPage = mPage + 1;
            select();
        } else {
            onLoad();
            lvMyCollection.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvMyCollection.stopRefresh();
        lvMyCollection.stopLoadMore();
    }
}
