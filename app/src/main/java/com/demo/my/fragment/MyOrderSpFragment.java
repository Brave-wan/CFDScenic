package com.demo.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_OrderDetailsSp;
import com.demo.my.adapter.MyOrderSpAdapterEl;
import com.demo.my.bean.SpFindOrderBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的订单-商品
 * Created by Administrator on 2016/8/6 0006.
 */
public class MyOrderSpFragment extends Fragment{

    public MyOrderSpAdapterEl adapter;

    int state;//判断状态值

    @Bind(R.id.el_ExpandableListView)
    ExpandableListView elExpandableListView;

    @Bind(R.id.ll_wudingdan)
    LinearLayout llWudingdan;


    List<List<SpFindOrderBean.DataBean.OrderListBean.RowsBean>> list;
    int corner=0;//角标

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expandable_listview, null);
        ButterKnife.bind(this, view);

        state = getArguments().getInt("state");

        elExpandableListView.setGroupIndicator(null);

        elExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), Activity_OrderDetailsSp.class);
                intent.putExtra("groupPosition", groupPosition);
                intent.putExtra("childPosition", childPosition);
                intent.putExtra("type", 3 + "");
                intent.putExtra("orderId", list.get(groupPosition).get(childPosition).getOrder_code() + "");
                startActivity(intent);
                return true;
            }
        });
        findOrder();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void findOrder() {
        list=new ArrayList<>();
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getContext(), SpName.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("status", state + "");
        params.addQueryStringParameter("page", 1 + "");
        params.addQueryStringParameter("rows", 2 + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findOrder, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111",responseInfo.result);
                        try {
                            SpFindOrderBean spFindOrderBean = new Gson().fromJson(responseInfo.result, SpFindOrderBean.class);
                            int i = spFindOrderBean.getHeader().getStatus();
                            if (i == 0) {
                                //角标
                                if (state == 1) {
                                    corner = spFindOrderBean.getData().getOrderCount();
                                    Activity_MyOrder activity_myOrder = (Activity_MyOrder) getContext();
                                    activity_myOrder.setCorner(corner);
                                }

                                if (spFindOrderBean.getData() != null) {
                                    list = spFindOrderBean.getData().getOrderList().getRows();
                                    if (list.size() > 0) {
                                        elExpandableListView.setVisibility(View.VISIBLE);
                                        llWudingdan.setVisibility(View.GONE);
                                        adapter = new MyOrderSpAdapterEl(getContext(), list);
                                        elExpandableListView.setAdapter(adapter);
                                        //全部展开
                                        for (int zk = 0; zk < adapter.getGroupCount(); zk++) {
                                            elExpandableListView.expandGroup(zk);
                                        }
                                        //不能收缩
                                        elExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                                            @Override
                                            public boolean onGroupClick(ExpandableListView parent, View v,
                                                                        int groupPosition, long id) {
                                                return true;
                                            }
                                        });
                                    } else {
                                        elExpandableListView.setVisibility(View.GONE);
                                        llWudingdan.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    elExpandableListView.setVisibility(View.GONE);
                                    llWudingdan.setVisibility(View.VISIBLE);
                                }
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getActivity());
                            } else {
                                ToastUtil.show(getActivity(), spFindOrderBean.getHeader().getMsg());
                            }

                        } catch (Exception e) {
                            ToastUtil.show(getActivity(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getActivity(), s);
                    }
                });

    }


}
