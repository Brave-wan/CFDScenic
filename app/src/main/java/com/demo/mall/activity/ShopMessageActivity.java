package com.demo.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.bean.SelectByIdBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/8/6 0006 13:51
 */
public class ShopMessageActivity extends Activity {
    @Bind(R.id.im_left_shopmessage)
    ImageView imLeftShopmessage;
    @Bind(R.id.iv_aty_shopmessage_beijing)
    ImageView ivAtyShopmessageBeijing;
    @Bind(R.id.iv_aty_shopmessage_icon)
    ImageView ivAtyShopmessageIcon;
    @Bind(R.id.tv_aty_shopmessage_title)
    TextView tvAtyShopmessageTitle;
    @Bind(R.id.tv_aty_shopmessage_location)
    TextView tvAtyShopmessageLocation;
    @Bind(R.id.iv_aty_shopmessage_call)
    ImageView ivAtyShopmessageCall;
    @Bind(R.id.tv_aty_shopmessage_call)
    TextView tvAtyShopmessageCall;
    @Bind(R.id.tv_aty_shopmessage_connent)
    TextView tvAtyShopmessageConnent;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_shopmessage);
        ButterKnife.bind(this);

        id=getIntent().getStringExtra("id");
        selectById();
    }

    @OnClick({R.id.im_left_shopmessage, R.id.iv_aty_shopmessage_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left_shopmessage:
                finish();
                break;
            case R.id.iv_aty_shopmessage_call:
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + tvAtyShopmessageCall.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }


     private void selectById(){
        RequestParams params = new RequestParams();
//        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("informationId", id);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.selectById, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            SelectByIdBean selectByIdBean=new Gson().fromJson(responseInfo.result,SelectByIdBean.class);
                            int i=selectByIdBean.getHeader().getStatus();
                            if (i==0){

                                BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
                                bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
                                bitmapUtils.display(ivAtyShopmessageIcon, selectByIdBean.getData().getHead_img());

                                tvAtyShopmessageLocation.setText(selectByIdBean.getData().getAddress());
                                tvAtyShopmessageCall.setText(selectByIdBean.getData().getPhone());
                                tvAtyShopmessageTitle.setText(selectByIdBean.getData().getName());
                            }else {
                                ToastUtil.show(getApplicationContext(),selectByIdBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }
}
