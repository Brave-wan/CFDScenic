package com.demo.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.demo.fragment.MainActivity;
import com.demo.mall.activity.ShopActivity;
import com.demo.mall.bean.FindFeatureShopBean;
import com.demo.mall.bean.SelectByIdBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.demo.adapter.HomeGoodGridViewAdapter;
import com.demo.demo.myapplication.R;
import com.demo.mall.activity.PurchaseDetailsActivity;
import com.demo.view.NoScrollGridView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：sonnerly on 2016/8/6 0006 11:36
 * 店铺 销量 网格
 */
public class SaleFragment extends Fragment {
    @Bind(R.id.sgv_frag_shop)
    NoScrollGridView sgvFragShop;
    HomeGoodGridViewAdapter homeGoodGridViewAdapter;
//
//    String id="";
//    String businessName="";
//    String business="";
//    List<FindFeatureShopBean.DataBean.RowsBean> list;
   FindFeatureShopBean findFeatureShopBean;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mall_fragment_shop, container,
                false);
        ButterKnife.bind(this, view);
//        Bundle bundle=getArguments();
//        id=bundle.getString("id");
//        businessName=bundle.getString("businessName");
//        business=bundle.getString("business");

        findFeatureShop();
        sgvFragShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PurchaseDetailsActivity.class);
                intent.putExtra("id",findFeatureShopBean.getData().get(position).getShopGoodsId()+"");//商品ID  添加购物车用
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void findFeatureShop(){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getContext(), SpName.token, ""));
        params.addQueryStringParameter("page", 1 + "");
        params.addQueryStringParameter("rows", 20+"");
        params.addQueryStringParameter("informationId", ShopActivity.id);
        params.addQueryStringParameter("type","2");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findFeatureShop, params,
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

                        String s = responseInfo.result;
                        try {
                            findFeatureShopBean = new Gson().fromJson(responseInfo.result, FindFeatureShopBean.class);
                            int i = findFeatureShopBean.getHeader().getStatus();
                            if (i == 0) {
                                homeGoodGridViewAdapter = new HomeGoodGridViewAdapter(getActivity(), findFeatureShopBean.getData());
                                sgvFragShop.setAdapter(homeGoodGridViewAdapter);
                            } else if(i==3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getActivity());
                            }else {
                                ToastUtil.show(getContext(), findFeatureShopBean.getHeader().getMsg());
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
