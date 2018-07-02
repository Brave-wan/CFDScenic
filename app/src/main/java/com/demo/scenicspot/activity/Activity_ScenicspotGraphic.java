package com.demo.scenicspot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;
import com.demo.scenicspot.bean.FindAtlasByVisitorsIdBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.seeImage.ImagePagerActivity;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.demo.adapter.GraphicAdapter;
import com.demo.demo.myapplication.R;
import com.demo.view.MyTopBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 图解
 * Created by Administrator on 2016/8/2 0002.
 */
public class Activity_ScenicspotGraphic extends Activity {

    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.gv_graphic)
    GridView gvGraphic;


    String[] strings;
    List<String> data;
    FindAtlasByVisitorsIdBean findAtlasByVisitorsIdBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);
        ButterKnife.bind(this);

        findAtlasByVisitorsId();
        gvGraphic.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageBrower(position,strings);
            }
        });
    }


    private void imageBrower(int position, String[] urls) {
        Intent intent = new Intent(getApplicationContext(), ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra("image_urls", urls);
        intent.putExtra("image_index", position);
        startActivity(intent);
    }

    private void findAtlasByVisitorsId(){
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("visitorsId",getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findAtlasByVisitorsId, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            findAtlasByVisitorsIdBean =new Gson().fromJson(responseInfo.result,FindAtlasByVisitorsIdBean.class);
                            int i=findAtlasByVisitorsIdBean.getHeader().getStatus();
                            if(i==0){
                                data=findAtlasByVisitorsIdBean.getData();
                                if (data!=null){
                                    viewTopbar.setCenterTextView("共"+data.size()+"张图片");
                                    gvGraphic.setAdapter(new GraphicAdapter(getApplicationContext(), data));

                                    strings=new String[findAtlasByVisitorsIdBean.getData().size()];
                                    for (int index=0;index<strings.length;index++){
                                        strings[index]=findAtlasByVisitorsIdBean.getData().get(index);
                                    }
                                }


                            }else {
                                ToastUtil.show(getApplicationContext(), findAtlasByVisitorsIdBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(),"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

    }
}
