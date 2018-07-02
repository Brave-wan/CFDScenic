package com.demo.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.bean.AlipayBean;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.SaveRestaurantOrderBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.StringUtil;
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

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import com.demo.demo.myapplication.R;
import com.demo.mall.bean.FindAllRestaurantDetailBean;
import com.demo.view.MyTopBar;
import com.demo.view.NoScrollViewListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/8/9 0009 16:33
 */
public class SingleSureOrderActivity extends Activity {
    @Bind(R.id.tv_aty_singlesureorder_title)
    TextView tvAtySinglesureorderTitle;
    @Bind(R.id.tv_aty_singlesureorder_date)
    TextView tvAtySinglesureorderDate;
    @Bind(R.id.tv_aty_singlesureorder_time)
    TextView tvAtySinglesureorderTime;
    @Bind(R.id.et_aty_singlesureorder_phone)
    EditText etAtySinglesureorderPhone;
    @Bind(R.id.slv_aty_singlesureorder)
    LinearLayout slvAtySinglesureorder;
    @Bind(R.id.tv_aty_singlesureorder_total)
    TextView tvAtySinglesureorderTotal;
    @Bind(R.id.tv_aty_singlesureorder_sumit)
    TextView tvAtySinglesureorderSumit;
    @Bind(R.id.topbartitle)
    MyTopBar topbartitle;

    List<FindAllRestaurantDetailBean.DataBean.ShopGoodsBean> list;
    private ArrayList<Integer> numList;
    double total=0;
    AlipayBean alipayBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_singlesureorder);
        ButterKnife.bind(this);
        alipayBean=new AlipayBean();
        tvAtySinglesureorderTitle.setText(getIntent().getStringExtra("name"));
        tvAtySinglesureorderDate.setText("就餐时间:" + getIntent().getStringExtra("date"));
        tvAtySinglesureorderTime.setText("就餐时段:"+getIntent().getStringExtra("time"));

        list = (List<FindAllRestaurantDetailBean.DataBean.ShopGoodsBean>) getIntent().getSerializableExtra("list");
        numList = getIntent().getIntegerArrayListExtra("num");

        for (int i=0;i<list.size();i++){
            if (numList.get(i)>0){
                View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_package_content,null);
                TextView name= (TextView) view.findViewById(R.id.name);
                TextView num= (TextView) view.findViewById(R.id.num);
                TextView price= (TextView) view.findViewById(R.id.price);

                name.setText(list.get(i).getGoods_name()+",");
                num.setText(numList.get(i)+"份,");
                price.setText(list.get(i).getNew_price() * numList.get(i) + "元");
                slvAtySinglesureorder.addView(view);
                total= total+list.get(i).getNew_price()*numList.get(i);
            }
        }
        tvAtySinglesureorderTotal.setText("￥"+total);
        getAlipay();
    }
    private void getAlipay() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("siId", getIntent().getStringExtra("shopInformationId"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getAlipay, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                            alipayBean = new Gson().fromJson(responseInfo.result, AlipayBean.class);
                            if (alipayBean.getHeader().getStatus() == 0) {
                            }else if(alipayBean.getHeader().getStatus()==3){
                            } else {
                                ToastUtil.show(SingleSureOrderActivity.this, alipayBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(SingleSureOrderActivity.this, "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(SingleSureOrderActivity.this, "连接网络失败");
                    }
                });

    }
    @OnClick(R.id.tv_aty_singlesureorder_sumit)
    public void onClick() {
        if (etAtySinglesureorderPhone.getText().toString().equals("")){
            ToastUtil.show(SingleSureOrderActivity.this,"手机号不能为空");
            return;
        }
        if (!StringUtil.checkPhone(etAtySinglesureorderPhone.getText().toString())){
            ToastUtil.show(SingleSureOrderActivity.this,"手机号格式有误");
            return;
        }
        saveRestaurantOrder();
    }


    private void saveRestaurantOrder(){

        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpName.token, ""));
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("[");
        for (int position=0;position<list.size();position++){
            if (numList.get(position)>0){
                String now = null;
                stringBuffer.append("{\"name\""+":\""+list.get(position).getGoods_name()+"\",");
                stringBuffer.append("\"orderDescribe\""+":\""+list.get(position).getGoods_describe()+"\",");
                stringBuffer.append("\"price\""+":"+list.get(position).getNew_price()*numList.get(position)+",");
                try {
                    Date date = new SimpleDateFormat("yyyy年MM月dd日").parse(getIntent().getStringExtra("date"));
                    now = new SimpleDateFormat("yyyy-MM-dd").format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                stringBuffer.append("\"eatDate\""+":\""+now+" "+getIntent().getStringExtra("time")+"\",");
                stringBuffer.append("\"quantity\""+":"+numList.get(position)+",");
                stringBuffer.append("\"goodsId\""+":"+list.get(position).getId()+",");
                stringBuffer.append("\"shopInformationId\""+":"+list.get(position).getShop_information_id()+",");
                stringBuffer.append("\"realPrice\""+":"+list.get(position).getNew_price()*numList.get(position)+",");
                stringBuffer.append("\"telphone\""+":\""+etAtySinglesureorderPhone.getText().toString()+"\",");
                stringBuffer.append("\"goodsType\""+":"+0+",");
                stringBuffer.append("\"isBalance\""+":"+0+"},");
            }
        }
        String json=stringBuffer.toString();
        json=json.substring(0,json.length()-1);
        json=json+"]";
        params.addQueryStringParameter("restaurantOrderJson",json);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.saveRestaurantOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(SingleSureOrderActivity.this,R.style.AlertDialogStyle);
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
                            SaveRestaurantOrderBean saveRestaurantOrderBean=new Gson().fromJson(responseInfo.result,SaveRestaurantOrderBean.class);
                            int i=saveRestaurantOrderBean.getHeader().getStatus();
                            if(i==0){
                                Intent intent = new Intent(getApplicationContext(), ChoossePaymentFdDpActivity.class);
                                intent.putExtra("data",saveRestaurantOrderBean.getData().getOrderCode()+"");
                                intent.putExtra("shopInformationId", saveRestaurantOrderBean.getData().getShopInformationId() + "");
                                if(alipayBean.getData()==null||alipayBean.getData().equals("")){
                                    //ToastUtil.show(SingleSureOrderActivity.this,"支付宝暂时无法支付");
                                    startActivity(intent);
                                }else{
                                    intent.putExtra("partner",alipayBean.getData().getPartner());
                                    intent.putExtra("seller",alipayBean.getData().getSeller());
                                    intent.putExtra("privateKey",alipayBean.getData().getPrivateKey());
                                    startActivity(intent);
                                }
                                finish();
                            }else if( i== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(SingleSureOrderActivity.this);
                            }else {
                                ToastUtil.show(getApplicationContext(), saveRestaurantOrderBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }
}
