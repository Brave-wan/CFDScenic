package com.demo.scenicspot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.scenicspot.activity.UserCommentActivity;
import com.demo.scenicspot.adapter.CommentAdapter;
import com.demo.scenicspot.bean.ScenicSpotCommentBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.NoScrollViewListView;
import com.demo.view.myListView.XNoScrollListView;
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
 * 作者： on 2016/8/1 0001 14:16
 */
public class CommentFragment extends Fragment  {
    @Bind(R.id.tv_allComment)
    TextView tvAllComment;
    CommentAdapter adapter;
    List<ScenicSpotCommentBean.DataBean> databean;
    @Bind(R.id.lv_fragmentAll)
    NoScrollViewListView lvFragmentAll;
    public static String num;
    public static String good;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container,
                false);
        ButterKnife.bind(this, view);
        num=getArguments().getString("num");
        good=getArguments().getString("good");
        databean = new ArrayList<>();
        adapter = new CommentAdapter(getActivity(), databean);
        lvFragmentAll.setAdapter(adapter);

        scenicSpotComment();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_allComment)
    public void onClick() {
        Intent intent = new Intent(getContext(), UserCommentActivity.class);
        intent.putExtra("id", getArguments().getString("id") + "");
        startActivity(intent);
    }


    private void scenicSpotComment() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", getArguments().getString("id") + "");
        params.addQueryStringParameter("page", 1 + "");
        params.addQueryStringParameter("rows", 10 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.scenicSpotComment, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            ScenicSpotCommentBean scenicSpotCommentBean = new Gson().fromJson(responseInfo.result, ScenicSpotCommentBean.class);
                            int i = scenicSpotCommentBean.getHeader().getStatus();
                            if (i == 0) {
                                if (scenicSpotCommentBean.getData() != null) {
                                    if (scenicSpotCommentBean.getData() != null) {
                                        databean.addAll(scenicSpotCommentBean.getData());
                                        adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    tvAllComment.setVisibility(View.GONE);
                                }
                            } else {
                                ToastUtil.show(getContext(), scenicSpotCommentBean.getHeader().getMsg());
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


}
