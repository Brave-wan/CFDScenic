package com.demo.my.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.MessageCenterBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
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
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.protocol.HTTP;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 消息中心
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_News extends Activity implements XListView.IXListViewListener{

    List<MessageCenterBean.DataBean.RowsBean> list;
    MessageCenterBean messageCenterBean = new MessageCenterBean();
    MyAdapter myAdapter;
    @Bind(R.id.lv_news)
    XListView lvNews;
    int mPage = 1;//页数
    boolean judge_Refresh = true;
   /* @Bind(R.id.lv_news)
    ListView lvNews;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        list=new ArrayList<>();
        myAdapter = new MyAdapter(Activity_News.this, list);
        lvNews.setAdapter(myAdapter);
        ///*lv_news.setAdapter(new MyAdapter());
        mPage = 1;
        //启用或禁用上拉加载更多功能。
        lvNews.setPullLoadEnable(true);
        //启用或禁用下拉刷新功能。
        lvNews.setPullRefreshEnable(true);

        lvNews.setXListViewListener(this);

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_News.this, Activity_MessageDetails.class);
                intent.putExtra("id", messageCenterBean.getData().getRows().get(position-1).getDetailId() + "");
                startActivity(intent);
            }
        });
        initMessage();


    }

    private void initMessage() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_News.this, SpName.token, ""));
        params.addQueryStringParameter("userType", "1");
        params.addQueryStringParameter("page", mPage+"");
        params.addQueryStringParameter("rows", "10");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.messageCenter, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            messageCenterBean = new Gson().fromJson(responseInfo.result, MessageCenterBean.class);
                            if (messageCenterBean.getHeader().getStatus() == 0) {
                                list.addAll(messageCenterBean.getData().getRows());
                                myAdapter.notifyDataSetChanged();
                                //分页
                                onLoad();
                                if (messageCenterBean.getData().getRows().size() < 10) {
                                    judge_Refresh = false;
                                    lvNews.setFooterTextView("已加载显示完全部内容");
                                }
//                                myAdapter = new MyAdapter(Activity_News.this, messageCenterBean.getData().getRows());
//                                lvNews.setAdapter(myAdapter);
                            } else if (messageCenterBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_News.this);
                            } else {
                                ToastUtil.show(Activity_News.this, messageCenterBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_News.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_News.this, "连接网络失败");
                    }
                });

    }

    @Override
    public void onRefresh() {
        mPage = 1;
        list.clear();
        judge_Refresh = true;
        initMessage();
        //更新上方日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lvNews.setRefreshTime(str);
    }

    @Override
    public void onLoadMore() {
        //判断是否到底部  还是否需要刷新

        if (judge_Refresh) {
            mPage = mPage + 1;
            initMessage();
        } else {
            onLoad();
            lvNews.setFooterTextView("已加载显示完全部内容");
        }
    }

    private void onLoad() {
        lvNews.stopRefresh();
        lvNews.stopLoadMore();
    }

    private class MyAdapter extends BaseAdapter {
        private Context context;
        private List<MessageCenterBean.DataBean.RowsBean> list;

        public MyAdapter(Context context, List<MessageCenterBean.DataBean.RowsBean> list) {
            this.context = context;
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CyzMode cyzMode;
            if (convertView == null) {
                cyzMode = new CyzMode();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_news, null);
                cyzMode.iv_meaage = (ImageView) convertView.findViewById(R.id.iv_pic_message);
                cyzMode.tv_message = (TextView) convertView.findViewById(R.id.tv_title_message);
                cyzMode.tv_time = (TextView) convertView.findViewById(R.id.tv_time_message);
                convertView.setTag(cyzMode);
            } else {
                cyzMode = (CyzMode) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(list.get(position).getImage(), cyzMode.iv_meaage);
            cyzMode.tv_message.setText(list.get(position).getTitle());
            String[] s = list.get(position).getCreateDate().split(" ");
            cyzMode.tv_time.setText(s[0]);
            return convertView;
        }

        private class CyzMode {
            ImageView iv_meaage;
            TextView tv_message;
            TextView tv_time;
        }

    }
}
