package com.demo.my.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.AddressBean;
import com.demo.my.bean.SelectVisitorsOrderBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyRadioButton;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 积分兑换--确认订单--纪念品
 * Created by Administrator on 2016/8/3 0003.
 */
public class Activity_ExchangeOkKeepsake extends Activity {

    @Bind(R.id.tv_confirm)
    TextView tvConfirm;

    @Bind(R.id.ll_ReceiptAddress)
    LinearLayout llReceiptAddress;
    @Bind(R.id.tv_ChoiceDate)
    TextView tvChoiceDate;
    Calendar calendar;
    @Bind(R.id.view_ziti)
    MyRadioButton viewZiti;
    @Bind(R.id.view_songhuo)
    MyRadioButton viewSonghuo;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.telphone)
    TextView telphone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.integral)
    TextView integral;
    @Bind(R.id.tv_peisongfei)
    TextView tvPeisongfei;
    @Bind(R.id.tv_heji)
    TextView tvHeji;
    @Bind(R.id.ll_heji)
    LinearLayout llHeji;
    /**
     * 年变量
     */
    private int year;
    /**
     * 月份变量
     */
    private int month;
    /**
     * 天数变量
     */
    private int day;
    /**
     * 日期选择器
     */
    private DatePicker date;

    String id;//传过来的ID
    int isMention = -1;//是否自提 1是
    long addressId = -1;//收货地址ID
    String integralS = "";
    SelectVisitorsOrderBean selectVisitorsOrderBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_ok_keepsake);
        ButterKnife.bind(this);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        init();
    }

    private void init() {
        id = getIntent().getStringExtra("id");
        BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);//加载失败
        bitmapUtils.configDefaultLoadingImage(R.mipmap.zhanwei);//加载过程中
        bitmapUtils.display(img, getIntent().getStringExtra("image"));
        name.setText(getIntent().getStringExtra("name"));
        integral.setText(getIntent().getStringExtra("integral") + "积分");
        tvPeisongfei.setText("￥" + getIntent().getStringExtra("deliver_fee"));
        tvHeji.setText("￥" + getIntent().getStringExtra("deliver_fee"));
        address();
    }

    @OnClick({R.id.ll_ReceiptAddress, R.id.tv_confirm, R.id.tv_ChoiceDate, R.id.view_ziti, R.id.view_songhuo})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_ReceiptAddress:
                intent = new Intent(getApplicationContext(), Activity_ReceiptAddress.class);
                intent.putExtra("id", 0);
                startActivityForResult(intent, 0x001);
                break;
            case R.id.tv_confirm:
                if (isMention == -1) {
                    ToastUtil.show(getApplicationContext(), "请选择收货方式");
                    return;
                }
                if (isMention==0){
                    if (addressId == -1) {
                        ToastUtil.show(getApplicationContext(), "请选择收货地址");
                        return;
                    }
                }
                //判断是否是自提，不是则需要选择发货日期
                /*if (isMention==0){
                    if (tvChoiceDate.getText().toString().equals("（选择发货日期）")){
                        ToastUtil.show(getApplicationContext(),"请选择收货日期");
                        return;
                    }
                }

                SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
                String    date    =    sDateFormat.format(new java.util.Date());
                compare_date(tvChoiceDate.getText().toString(), date);*/
                saveIntegralOrder();
                break;
            case R.id.tv_ChoiceDate:
                new DatePickerDialog(Activity_ExchangeOkKeepsake.this, AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int years,
                                                  int monthOfYear, int dayOfMonth) {
                                tvChoiceDate.setText(years + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                        String monthStr = monthOfYear+1>=10?monthOfYear+1+"":"0"+(monthOfYear+1);
                                //获得更改时间后的数据
//                        getlist(years + "-" + monthStr + "-" + dayOfMonth);
                            }
                        }, year, month, day).show();
                break;
            case R.id.view_ziti:
                llHeji.setVisibility(View.INVISIBLE);
                viewZiti.setStatus(true);
                viewSonghuo.setStatus(false);
                isMention = 1;
                //tvChoiceDate.setVisibility(View.GONE);
                break;
            case R.id.view_songhuo:
                llHeji.setVisibility(View.VISIBLE);
                viewZiti.setStatus(false);
                viewSonghuo.setStatus(true);
                isMention = 0;
                //tvChoiceDate.setVisibility(View.VISIBLE);
                break;
        }
    }

    //计算日期，看选择的日期是当前日期往前的吗，是显示Toast
    private int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                saveIntegralOrder();
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                ToastUtil.show(getApplicationContext(), "请选择当前日期往后的日期");
                return -1;
            } else {
                saveIntegralOrder();
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    //获取默认地址
    private void address() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        //params.addQueryStringParameter("id",);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.address, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ExchangeOkKeepsake.this,R.style.AlertDialogStyle);
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
                            AddressBean addressBean = new Gson().fromJson(responseInfo.result, AddressBean.class);
                            int i = addressBean.getHeader().getStatus();
                            if (i == 0) {
                                if (addressBean.getData() != null) {
                                    userName.setText("收货人：" + addressBean.getData().getUser_name());
                                    telphone.setText(addressBean.getData().getTelphone() + "");
                                    address.setText(addressBean.getData().getPlace_address() + (addressBean.getData().getDetail_address()));
                                    addressId = addressBean.getData().getId();
                                }
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ExchangeOkKeepsake.this);
                            } else if (i == 4) {
                            } else {
                                ToastUtil.show(getApplicationContext(), addressBean.getHeader().getMsg());
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

    //确认兑换
    private void saveIntegralOrder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("visitorsId", getIntent().getStringExtra("id") + "");
        params.addQueryStringParameter("name", getIntent().getStringExtra("name") + "");
        params.addQueryStringParameter("orderDescribe", "订单描述");
        if (isMention==1){
            addressId=0;
        }
        params.addQueryStringParameter("addressId", addressId + "");
        params.addQueryStringParameter("integraPrice", getIntent().getStringExtra("integral") + "");
        params.addQueryStringParameter("isMention", isMention + "");
        params.addQueryStringParameter("price", getIntent().getStringExtra("deliver_fee") + "");
        params.addQueryStringParameter("pay_way", "4");
        params.addQueryStringParameter("startValid", getIntent().getStringExtra("start") + "");
        params.addQueryStringParameter("endValid", getIntent().getStringExtra("end") + "");
       /* params.addQueryStringParameter("quantity", "1");
        params.addQueryStringParameter("visitorsId", getIntent().getStringExtra("id") + "");*/
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.saveIntegralOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ExchangeOkKeepsake.this,R.style.AlertDialogStyle);
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
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {

                                if (isMention==1){
                                    selectVisitorsOrder(jsonObject.getString("data") + "");
                                }else {
                                    Intent intent = new Intent(getApplicationContext(), Activity_PaymentOrder.class);
                                    intent.putExtra("id", jsonObject.getString("data") + "");
                                    startActivity(intent);
                                    finish();
                                }
                            } else if (i == 3) {
                                DialogAgainSignIn dialogAgainSignIn = new DialogAgainSignIn(getApplicationContext(), R.style.AlertDialogStyle);
                                dialogAgainSignIn.show();
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x001 && resultCode == 0x001) {
            addressId = data.getLongExtra("addressId", -1);
            userName.setText(data.getStringExtra("name"));
            telphone.setText(data.getStringExtra("telphone"));
            address.setText(data.getStringExtra("address"));
        }
    }

    //订单回显
    private void selectVisitorsOrder(final String id) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("id", id);
        //params.addQueryStringParameter("integration", integration);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.selectVisitorsOrder, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            selectVisitorsOrderBean= new Gson().fromJson(responseInfo.result, SelectVisitorsOrderBean.class);
                            int i = selectVisitorsOrderBean.getHeader().getStatus();
                            if (i == 0) {
                                confirmPayment(id);
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ExchangeOkKeepsake.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), selectVisitorsOrderBean.getHeader().getMsg());
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


    //确认支付
    private void confirmPayment(final String id) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("orderId", id);
        params.addQueryStringParameter("balance", selectVisitorsOrderBean.getData().getBalance()+"");
        params.addQueryStringParameter("price", selectVisitorsOrderBean.getData().getPrice()+"");
        params.addQueryStringParameter("integration", selectVisitorsOrderBean.getData().getIntegra_price()+"");
        params.addQueryStringParameter("payType", "4");
        params.addQueryStringParameter("name", selectVisitorsOrderBean.getData().getName());
        params.addQueryStringParameter("passWord", "");
       /* if (payType==0){

        }else if(payType==1){
            params.addQueryStringParameter("name", "支付宝支付");
        }else if(payType==1){
            params.addQueryStringParameter("name", "微信支付");
        }*/

        //params.addQueryStringParameter("integration", integration);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.confirmPayment, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_ExchangeOkKeepsake.this,R.style.AlertDialogStyle);
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
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                Intent intent = new Intent(getApplicationContext(), Activity_ExchangeResultesKeepsake.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                                finish();
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ExchangeOkKeepsake.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
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
