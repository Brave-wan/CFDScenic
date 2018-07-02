package com.demo.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.demo.myapplication.R;
import com.demo.mall.activity.RestaurantMoreActivity;
import com.demo.mall.activity.SingleSureOrderActivity;
import com.demo.mall.adapter.PackageAdapter;
import com.demo.mall.adapter.SingleAdapter;
import com.demo.mall.bean.FindAllRestaurantDetailBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.NoScrollViewListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/8/10 0010 09:18
 */
public class SingleFragment extends Fragment {

    SingleAdapter singleAdapter;
    @Bind(R.id.slv_frag_single)
    NoScrollViewListView slvFragSingle;
    @Bind(R.id.ll_frag_single_sure)
    LinearLayout llFragSingleSure;

    List<FindAllRestaurantDetailBean.DataBean.ShopGoodsBean> list;
    String[] time;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mall_fragment_singlelist, container,
                false);
        ButterKnife.bind(this, view);


        findAllRestaurantDetail();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.ll_frag_single_sure)
    public void onClick() {
        //getArguments().getString("date")==null||getArguments().getString("date").equals("")
        RestaurantMoreActivity activity= (RestaurantMoreActivity) getActivity();
        if(activity.tvAtyResmoreRili.getText().toString().equals("")){
            ToastUtil.show(getActivity(),"请选择就餐时间");
        }else{
            int xy=0;
            for (int index=0;index<singleAdapter.getNumList().size();index++){
                if (singleAdapter.getNumList().get(index)!=0){
                    xy=xy+1;
                    break;
                }
            }
            if (xy==0){
                ToastUtil.show(getActivity(), "请选择商品");
                return;
            } else{
                time=activity.tvAtyResmoreRili.getText().toString().split(" ");
                Intent intent=new Intent(getActivity(),SingleSureOrderActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("list", (Serializable) singleAdapter.getList());
                bundle.putIntegerArrayList("num", singleAdapter.getNumList());
                bundle.putString("date", time[0]);
                bundle.putString("time",time[1]);
                bundle.putString("name", getArguments().getString("name"));
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }


    }


    //详情
    private void findAllRestaurantDetail() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", getArguments().getString("id"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findAllRestaurantDetail, params,
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
                            FindAllRestaurantDetailBean findAllRestaurantBean = new Gson().fromJson(responseInfo.result, FindAllRestaurantDetailBean.class);
                            int i = findAllRestaurantBean.getHeader().getStatus();
                            if (i == 0) {
                                if(findAllRestaurantBean.getData().getShopGoods().size()==0){
                                    llFragSingleSure.setVisibility(View.GONE);
                                }else {
                                    llFragSingleSure.setVisibility(View.VISIBLE);
                                    list=findAllRestaurantBean.getData().getShopGoods();
                                    singleAdapter = new SingleAdapter(getActivity(),list);
                                    slvFragSingle.setAdapter(singleAdapter);
                                }

                            } else {
                                ToastUtil.show(getContext(), findAllRestaurantBean.getHeader().getMsg());
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
}
