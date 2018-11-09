package com.demo.monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.demo.myapplication.R;
import com.demo.monitor.bean.VideoListBean;
import com.demo.monitor.present.TestDpsdkCorePresent;
import com.demo.monitor.view.ITestDpsdkCoreView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoListActivity extends Activity implements ITestDpsdkCoreView {
    @Bind(R.id.rv_video_list)
    RecyclerView rv_video_list;
    TestDpsdkCorePresent present;
    BaseQuickAdapter<VideoListBean.DataBean, BaseViewHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);
        present = new TestDpsdkCorePresent(this, this);
        initView();
    }

    private void initView() {
        present.getVideoList();
    }

    @Override
    public void OnMonitorBean(VideoListBean monitorBean) {
        initAdapter(monitorBean.getData());
    }

    private void initAdapter(List<VideoListBean.DataBean> data) {
        rv_video_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<VideoListBean.DataBean, BaseViewHolder>(R.layout.item_video_list, data) {
            @Override
            protected void convert(BaseViewHolder helper, VideoListBean.DataBean item) {
                TextView title = (TextView) helper.itemView.findViewById(R.id.tx_video_title);
                title.setText(item.getCameraName());
            }
        };
        rv_video_list.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoListBean.DataBean bean = (VideoListBean.DataBean) adapter.getItem(position);
                Intent intent = new Intent(VideoListActivity.this, HKPlayActivity.class);
                intent.putExtra("id", bean.getCameraUuid());
                intent.putExtra("name", bean.getCameraName());
                startActivity(intent);
            }
        });
    }

}
