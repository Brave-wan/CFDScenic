package com.demo.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.demo.my.adapter.MyOrderFdAdapterLv;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_OrderDetailsFdDp;
import com.demo.my.activity.Activity_OrderDetailsFdTc;
import com.demo.my.bean.FdFindOrderBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的订单-饭店  原先版本  UI图上的
 * Created by Administrator on 2016/8/6 0006.
 */
public class MyOrderFdFragment extends Fragment {





    public MyOrderFdAdapterLv adapter;
    int state;
    public List<List<FdFindOrderBean.DataBean.OrderListBean.RowsBean>> list;
    @Bind(R.id.lv_fragmentAll)
    public ListView lvFragmentAll;
    @Bind(R.id.ll_wudingdan)
    public LinearLayout llWudingdan;

    int corner=0;//角标

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        ButterKnife.bind(this, view);

        state = getArguments().getInt("state");

        //elExpandableListView.setGroupIndicator(null);



        lvFragmentAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (list.get(position).get(0).getGoods_type() == 1) {
                    intent = new Intent(getContext(), Activity_OrderDetailsFdTc.class);
                } else {
                    intent = new Intent(getContext(), Activity_OrderDetailsFdDp.class);
                }
                intent.putExtra("position", position);
                intent.putExtra("type", 2 + "");
                intent.putExtra("orderId", list.get(position).get(0).getOrder_code() + "");
                startActivity(intent);
            }
        });

        findOrder();
        return view;
    }

    public void findOrder() {
        list = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getContext(), SpName.token, ""));
        params.addQueryStringParameter("type", 2 + "");
        params.addQueryStringParameter("status", state + "");
        params.addQueryStringParameter("page", 1 + "");
        params.addQueryStringParameter("rows", 10 + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findOrder, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            FdFindOrderBean findOrderBean = new Gson().fromJson(responseInfo.result, FdFindOrderBean.class);
                            int i = findOrderBean.getHeader().getStatus();
                            if (i == 0) {
                                //角标
                                if (state == 1) {
                                    corner = findOrderBean.getData().getOrderCount();
                                    Activity_MyOrder activity_myOrder = (Activity_MyOrder) getContext();
                                    activity_myOrder.setCorner(corner);
                                }
                                if (findOrderBean.getData() != null) {
                                    list = findOrderBean.getData().getOrderList().getRows();
                                    if (list.size() > 0) {

                                        lvFragmentAll.setVisibility(View.VISIBLE);
                                        llWudingdan.setVisibility(View.GONE);

                                        adapter = new MyOrderFdAdapterLv(getContext(), list);
                                        lvFragmentAll.setAdapter(adapter);

                                    } else {
                                        lvFragmentAll.setVisibility(View.GONE);
                                        llWudingdan.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    lvFragmentAll.setVisibility(View.GONE);
                                    llWudingdan.setVisibility(View.VISIBLE);
                                }

                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getActivity());
                            } else {
                                ToastUtil.show(getContext(), findOrderBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), s);
                    }
                });

    }

    public void visibilityCorner(){
        Log.i("1111visibilityCorner", corner + "");
        Activity_MyOrder activity_myOrder= (Activity_MyOrder) getContext();
        if (corner!=0){
            activity_myOrder.setCorner(corner);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
