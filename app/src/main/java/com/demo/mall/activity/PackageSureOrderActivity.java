package com.demo.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.demo.view.BuyTimesPicker;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/8/9 0009 16:08
 * 套餐 确认支付
 */
public class PackageSureOrderActivity extends Activity {
    @Bind(R.id.tv_aty_packagesureorder_title)
    TextView tvAtyPackagesureorderTitle;
    @Bind(R.id.iv_aty_hotelsureorder_img)
    ImageView ivAtyHotelsureorderImg;
    @Bind(R.id.tv_aty_packagesureorder_type)
    TextView tvAtyPackagesureorderType;
    @Bind(R.id.tv_aty_packagesureorder_ruzhu)
    TextView tvAtyPackagesureorderRuzhu;
    @Bind(R.id.tv_aty_packagesureorder_likai)
    TextView tvAtyPackagesureorderLikai;
    @Bind(R.id.tv_aty_packagesureorder_price)
    TextView tvAtyPackagesureorderPrice;
    @Bind(R.id.et_aty_packagesureorder_phone)
    EditText etAtyPackagesureorderPhone;
    @Bind(R.id.tv_aty_packagesureorder_total)
    TextView tvAtyPackagesureorderTotal;
    @Bind(R.id.tv_aty_packagesureorder_sumit)
    TextView tvAtyPackagesureorderSumit;
    @Bind(R.id.iv_reduce)
    ImageView ivReduce;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.iv_plus)
    ImageView ivPlus;

    int num = 1;//记录数量
    double price = 0;//单价
    AlipayBean alipayBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_packagesureorder);
        ButterKnife.bind(this);
        alipayBean=new AlipayBean();
        init();
        num = Integer.parseInt(tvNum.getText().toString());
        getAlipay();
    }
    private void getAlipay() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("siId",getIntent().getStringExtra("shopInformationId"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getAlipay, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                            alipayBean= new Gson().fromJson(responseInfo.result, AlipayBean.class);
                            if(alipayBean.getHeader().getStatus()==0){

                            }else if(alipayBean.getHeader().getStatus()==3){

                            } else {
                                ToastUtil.show(PackageSureOrderActivity.this, alipayBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(PackageSureOrderActivity.this,"解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(PackageSureOrderActivity.this, "连接网络失败");
                    }
                });

    }
    private void init() {
        tvAtyPackagesureorderTitle.setText(getIntent().getStringExtra("Fname"));
        tvAtyPackagesureorderType.setText(getIntent().getStringExtra("Sname"));

        ImageLoader.getInstance().displayImage(getIntent().getStringExtra("imageView"), ivAtyHotelsureorderImg);

        String date = getIntent().getStringExtra("date");
        tvAtyPackagesureorderRuzhu.setText("就餐时间:" + date.substring(5));
        tvAtyPackagesureorderLikai.setText("就餐时段:" + getIntent().getStringExtra("time"));
        tvAtyPackagesureorderPrice.setText("￥" + getIntent().getStringExtra("price"));
        price = Integer.parseInt(getIntent().getStringExtra("price"));
        tvAtyPackagesureorderTotal.setText("￥" + num * price);
    }

    @OnClick({R.id.tv_aty_packagesureorder_sumit, R.id.iv_reduce, R.id.iv_plus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_reduce:
                if (num == 1) {
                    return;
                }
                if (num - 1 == 1) {
                    num = num - 1;
                    tvNum.setText(num + "");
                    ivReduce.setImageResource(R.mipmap.jian);
                    tvAtyPackagesureorderTotal.setText("￥" + num * price);
                    return;
                }
                num = num - 1;
                tvNum.setText(num + "");
                tvAtyPackagesureorderTotal.setText("￥" + num * price);
                break;
            case R.id.iv_plus:
                num = num + 1;
                if (num > 1) {
                    ivReduce.setImageResource(R.mipmap.jian_lan);
                }
                tvNum.setText(num + "");
                tvAtyPackagesureorderTotal.setText("￥" + num * price);
                break;
            case R.id.tv_aty_packagesureorder_sumit:
                if (etAtyPackagesureorderPhone.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"手机号不能为空");
                    return;
                }
                if (!StringUtil.checkPhone(etAtyPackagesureorderPhone.getText().toString())){
                    ToastUtil.show(getApplicationContext(),"手机号格式错误");
                    return;
                }
                saveRestaurantOrder();
                break;
        }
    }


    private void saveRestaurantOrder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        String now = null;
        stringBuffer.append("{\"name\"" + ":\"" + getIntent().getStringExtra("Sname") + "\",");
        stringBuffer.append("\"orderDescribe\"" + ":\"" + getIntent().getStringExtra("Sname") + "\",");
        stringBuffer.append("\"price\"" + ":" + price + ",");
        try {
            Date date = new SimpleDateFormat("yyyy年MM月dd日").parse(getIntent().getStringExtra("date"));
            now = new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        stringBuffer.append("\"eatDate\"" + ":\"" + now+" "+getIntent().getStringExtra("time") + "\",");
        stringBuffer.append("\"quantity\"" + ":" + num + ",");
        stringBuffer.append("\"goodsId\"" + ":" + getIntent().getStringExtra("goodsId") + ",");
        stringBuffer.append("\"shopInformationId\"" + ":" + getIntent().getStringExtra("shopInformationId") + ",");
        stringBuffer.append("\"realPrice\"" + ":" + num * price + ",");
        stringBuffer.append("\"telphone\"" + ":\"" + etAtyPackagesureorderPhone.getText().toString() + "\",");
        stringBuffer.append("\"goodsType\"" + ":" + 1 + ",");
        stringBuffer.append("\"isBalance\"" + ":" + 0 + "},");

        String json = stringBuffer.toString();
        json = json.substring(0, json.length() - 1);
        json = json + "]";
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        params.addQueryStringParameter("restaurantOrderJson",json);
        http.send(HttpRequest.HttpMethod.GET, URL.saveRestaurantOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(PackageSureOrderActivity.this,R.style.AlertDialogStyle);
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
                            SaveRestaurantOrderBean saveRestaurantOrderBean=new Gson().fromJson(responseInfo.result, SaveRestaurantOrderBean.class);
                            int i=saveRestaurantOrderBean.getHeader().getStatus();
                            if(i==0){
                                Intent intent = new Intent(getApplicationContext(), ChoossePaymentFdTcActivity.class);
                                intent.putExtra("data", saveRestaurantOrderBean.getData().getOrderCode() + "");
                                intent.putExtra("shopInformationId", saveRestaurantOrderBean.getData().getShopInformationId() + "");
                                if(alipayBean.getData()==null||alipayBean.getData().equals("")){
                                   // ToastUtil.show(PackageSureOrderActivity.this,"支付宝暂时无法支付");
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
                                MainActivity.state_Three(PackageSureOrderActivity.this);
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
