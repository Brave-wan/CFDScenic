package com.demo.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.demo.demo.myapplication.R;
import com.demo.mall.activity.PackageMoreActivity;
import com.demo.mall.activity.RestaurantMoreActivity;
import com.demo.mall.adapter.PackageAdapter;
import com.demo.mall.bean.FindAllRestaurantDetailBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.myListView.XNoScrollListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：sonnerly on 2016/8/10 0010 09:17
 */
public class PackageFragment extends Fragment {
    PackageAdapter packageAdapter;
    Bundle bundle;
    String id;
    FindAllRestaurantDetailBean findAllRestaurantBean;
    List<FindAllRestaurantDetailBean.DataBean.PackageListBean> list;

    @Bind(R.id.lv_fragmentAll)
    XNoScrollListView lvFragmentAll;
    String[] time;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scroll_list, container,
                false);
        ButterKnife.bind(this, view);
        bundle = getArguments();
        id = bundle.getString("id");

        lvFragmentAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestaurantMoreActivity activity= (RestaurantMoreActivity) getActivity();
                time=activity.tvAtyResmoreRili.getText().toString().split(" ");
                Intent intent = new Intent(getContext(), PackageMoreActivity.class);
                intent.putExtra("id", list.get(position-1).getId() + "");
                intent.putExtra("shopInformationId", findAllRestaurantBean.getData().getRestaurantDetail().getId() + "");
                intent.putExtra("Fname", findAllRestaurantBean.getData().getRestaurantDetail().getName());
                intent.putExtra("price", list.get(position-1).getNew_price() + "");
                if(activity.tvAtyResmoreRili.getText().toString().equals("")){
                    ToastUtil.show(getActivity(),"请选择就餐时间");
                }else{
                    intent.putExtra("date", time[0]);
                    intent.putExtra("time", time[1]);
                    startActivity(intent);
                }
            }
        });



        findAllRestaurantDetail();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void findAllRestaurantDetail() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", id);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findAllRestaurantDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(getActivity(), R.style.AlertDialogStyle);

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
                            findAllRestaurantBean = new Gson().fromJson(responseInfo.result, FindAllRestaurantDetailBean.class);
                            int i = findAllRestaurantBean.getHeader().getStatus();
                            if (i == 0) {
                                list = findAllRestaurantBean.getData().getPackageList();
                                packageAdapter = new PackageAdapter(getActivity(), list);
                                lvFragmentAll.setAdapter(packageAdapter);
                            } else {
                                ToastUtil.show(getContext(), findAllRestaurantBean.getHeader().getMsg());
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

}
