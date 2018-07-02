package com.demo.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;
import com.demo.mall.adapter.CategoryAdapter;
import com.demo.mall.bean.GetTypeBean;
import com.demo.my.bean.GetBankBean;
import com.demo.view.DialogProgressbar;
import com.demo.view.NoScrollGridView;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者： on 2016/8/5 0005 19:39
 */
public class CategoryActivity extends Activity {
    @Bind(R.id.sgv_fenlei_jiudian)
    NoScrollGridView sgvFenleiJiudian;
    @Bind(R.id.sgv_fenlei_canyin)
    NoScrollGridView sgvFenleiCanyin;
    @Bind(R.id.sgv_fenlei_xiaochi)
    NoScrollGridView sgvFenleiXiaochi;
    @Bind(R.id.sgv_fenlei_techan)
    NoScrollGridView sgvFenleiTechan;
    CategoryAdapter adapter;
    CategoryAdapter adapter1;
    CategoryAdapter adapter2;
    CategoryAdapter adapter3;
    List<GetTypeBean.DataBean> list = new ArrayList<>();
    List<GetTypeBean.DataBean> list1 = new ArrayList<>();
    List<GetTypeBean.DataBean> list2 = new ArrayList<>();
    List<GetTypeBean.DataBean> list3 = new ArrayList<>();
    Intent intent = new Intent();
    GetTypeBean getTypeBean = new GetTypeBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_category);
        ButterKnife.bind(this);
        adapter = new CategoryAdapter(CategoryActivity.this, list);
        sgvFenleiJiudian.setAdapter(adapter);

        adapter1 = new CategoryAdapter(CategoryActivity.this, list1);
        sgvFenleiCanyin.setAdapter(adapter1);

        adapter2 = new CategoryAdapter(CategoryActivity.this, list2);//特产
        sgvFenleiTechan.setAdapter(adapter2);
        adapter3 = new CategoryAdapter(CategoryActivity.this, list3);
        sgvFenleiXiaochi.setAdapter(adapter3);
        getType();
        initCat();
    }

    private void initCat() {
        sgvFenleiJiudian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String time = format.format(date);
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, 1);
                String time1 = format.format(c.getTime());
                intent.setClass(CategoryActivity.this, HotelMoreActivity.class);//酒店
                intent.putExtra("id", list.get(position).getId()+"");
                intent.putExtra("start", time);
                intent.putExtra("end", time1);
                intent.putExtra("total", "1");
                startActivity(intent);
            }
        });
        sgvFenleiCanyin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.setClass(CategoryActivity.this,RestaurantMoreActivity.class);//餐饮
                intent.putExtra("id", list1.get(position).getId()+ "");
                intent.putExtra("date","");
                startActivity(intent);
            }
        });
        sgvFenleiXiaochi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.setClass(CategoryActivity.this,ShopActivity.class);//小吃
                intent.putExtra("id", list3.get(position).getId() + "");
                intent.putExtra("name",list3.get(position).getName());
                intent.putExtra("ImageUrl",list3.get(position).getHead_img());
                startActivity(intent);
            }
        });
        sgvFenleiTechan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.setClass(CategoryActivity.this, ShopActivity.class);//特产
                intent.putExtra("id", list2.get(position).getId() + "");
                intent.putExtra("name",list2.get(position).getName());
                intent.putExtra("ImageUrl",list2.get(position).getHead_img());
                startActivity(intent);
            }
        });
    }

    // 1酒店 2饭店 3特产 4小吃
    private void getType() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        //params.addQueryStringParameter("id",);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getType, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(CategoryActivity.this,R.style.AlertDialogStyle);
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
                            getTypeBean = new Gson().fromJson(responseInfo.result, GetTypeBean.class);
                            int i = getTypeBean.getHeader().getStatus();
                            if (i == 0) {
//                                adapter=new CategoryAdapter(CategoryActivity.this, getTypeBean.getData());
                                for(int ii=0;ii<getTypeBean.getData().size();ii++){
                                   if(getTypeBean.getData().get(ii).getType()==1){
//                                       sgvFenleiJiudian.setAdapter(adapter);
                                       list.add(getTypeBean.getData().get(ii));
                                   } else if(getTypeBean.getData().get(ii).getType()==2){
//                                        sgvFenleiCanyin.setAdapter(adapter);
                                       list1.add(getTypeBean.getData().get(ii));
                                    }else  if(getTypeBean.getData().get(ii).getType()==3){
//                                        sgvFenleiXiaochi.setAdapter(adapter);
                                       list2.add(getTypeBean.getData().get(ii));
                                    } else if(getTypeBean.getData().get(ii).getType()==4){
//                                        sgvFenleiTechan.setAdapter(adapter);
                                       list3.add(getTypeBean.getData().get(ii));
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                adapter1.notifyDataSetChanged();
                                adapter2.notifyDataSetChanged();
                                adapter3.notifyDataSetChanged();

                            } else {
                                ToastUtil.show(getApplicationContext(), getTypeBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }
}
