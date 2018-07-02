package com.demo.amusement.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.demo.adapter.GraphicAdapter;
import com.demo.adapter.GraphicStringAdapter;
import com.demo.demo.myapplication.R;
import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;
import com.demo.scenicspot.bean.FindAtlasByVisitorsIdBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.MyTopBar;
import com.demo.view.seeImage.ImagePagerActivity;
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
 * 图解
 * Created by Administrator on 2016/8/2 0002.
 */
public class Activity_AmusementGraphic extends Activity {

    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.gv_graphic)
    GridView gvGraphic;


    String[] strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);
        ButterKnife.bind(this);
        strings=getIntent().getStringArrayExtra("array");
        if (strings!=null){
            if (strings.length>0){
                viewTopbar.setCenterTextView("共"+strings.length+"张图片");
                gvGraphic.setAdapter(new GraphicStringAdapter(getApplicationContext(),strings));
                gvGraphic.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        imageBrower(position,strings);
                    }
                });
            }
        }

    }


    private void imageBrower(int position, String[] urls) {
        Intent intent = new Intent(getApplicationContext(), ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra("image_urls", urls);
        intent.putExtra("image_index", position);
        startActivity(intent);
    }

}
