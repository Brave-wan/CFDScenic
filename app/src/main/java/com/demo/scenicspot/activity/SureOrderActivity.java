package com.demo.scenicspot.activity;

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
import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.AddressBean;
import com.demo.my.activity.Activity_ReceiptAddress;
import com.demo.my.bean.AddressListBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyRadioButton;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： on 2016/8/2 0002 10:30
 * 确认订单
 */
public class SureOrderActivity extends Activity {
    @Bind(R.id.tv_sureorder_title)
    TextView tvSureorderTitle;
    @Bind(R.id.tv_sureorder_price)
    TextView tvSureorderPrice;
    @Bind(R.id.tv_sureorder_date)
    TextView tvSureorderDate;
    @Bind(R.id.tv_sureorder_buttom_total)
    TextView tvSureorderButtomTotal;
    @Bind(R.id.tv_sureorder_sumit)
    TextView tvSureorderSumit;
    @Bind(R.id.iv_reduce)
    ImageView ivReduce;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.iv_plus)
    ImageView ivPlus;
    @Bind(R.id.view_xuyao)
    MyRadioButton viewXuyao;
    @Bind(R.id.view_ziti)
    MyRadioButton viewZiti;
    @Bind(R.id.view_youji)
    MyRadioButton viewYouji;
    @Bind(R.id.ll_ziti)
    LinearLayout llZiti;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.telphone)
    TextView telphone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.ll_youji)
    LinearLayout llYouji;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.ll_jine)
    LinearLayout llJine;
    @Bind(R.id.ll_Address)
    LinearLayout llAddress;
    @Bind(R.id.tv_youfei)
    TextView tvYoufei;
    @Bind(R.id.tv_youjifei)
    TextView tvYoujifei;
    @Bind(R.id.ll_date)
    LinearLayout llDate;


    int num = 1;//记录数量
    String currentDate;//当前日期
    String backDate;//7天后日期
    double price = 0;
    long addressId = 0;//收货地址ID
    int invoice = 0;//发票 1不需要，2自提，3邮寄
    Double deliver_fee = 0.0;//配送费


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sureorder);
        ButterKnife.bind(this);

        deliver_fee = Double.parseDouble(getIntent().getStringExtra("deliver_fee"));
        num = Integer.parseInt(tvNum.getText().toString());
        price = getIntent().getDoubleExtra("price", -1);
        tvSureorderTitle.setText(getIntent().getStringExtra("name"));
        tvSureorderPrice.setText("￥" + price);
        tvSureorderButtomTotal.setText("￥" + price * num);
        tvYoujifei.setText("￥" + deliver_fee + "");

    }

    @Override
    protected void onResume() {
        super.onResume();
        addressList();
    }

    private void addressList() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.addressList, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    AddressListBean addressListBean = new Gson().fromJson(responseInfo.result, AddressListBean.class);
                    if (addressListBean.getHeader().getStatus() == 0) {
                        if (addressListBean.getData() != null) {
                            if (addressListBean.getData().size() != 0) {
                                int index = 0;
                                for (index = 0; index < addressListBean.getData().size(); index++) {
                                    if (addressListBean.getData().get(index).getAddressId() == addressId) {
                                        break;
                                    }
                                }
                                if (index == addressListBean.getData().size()) {
                                    address();
                                }
                            }
                        } else {
                            userName.setText(getResources().getString(R.string.address_shang));
                            telphone.setText("");
                            address.setText(getResources().getString(R.string.address_xia));
                            addressId = 0;
                        }
                    }
                } catch (Exception e) {
                    ToastUtil.show(getApplicationContext(), "数据解析失败");
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                ToastUtil.show(getApplication(), "连接网络失败");
            }
        });
    }


    @OnClick({R.id.iv_reduce, R.id.iv_plus, R.id.tv_sureorder_sumit, R.id.view_xuyao, R.id.view_ziti, R.id.view_youji, R.id.ll_Address, R.id.ll_date})
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
                    if (invoice == 2) {
                        tvSureorderButtomTotal.setText("￥" + (price * num + deliver_fee));
                    } else {
                        tvSureorderButtomTotal.setText("￥" + price * num);
                    }
                    return;
                }
                num = num - 1;
                tvNum.setText(num + "");
                if (invoice == 2) {
                    tvSureorderButtomTotal.setText("￥" + (price * num + deliver_fee));
                } else {
                    tvSureorderButtomTotal.setText("￥" + price * num);
                }
                break;
            case R.id.iv_plus:
                num = num + 1;
                if (num > 1) {
                    ivReduce.setImageResource(R.mipmap.jian_lan);
                }
                tvNum.setText(num + "");
                if (invoice == 2) {
                    tvSureorderButtomTotal.setText("￥" + (price * num + deliver_fee));
                } else {
                    tvSureorderButtomTotal.setText("￥" + price * num);
                }
                break;
            case R.id.tv_sureorder_sumit:
                if (tvSureorderDate.getText().toString().equals("请选择游玩时间")) {
                    ToastUtil.show(getApplicationContext(), "请选择游玩时间");
                    return;
                }
                if (invoice == 2) {
                    if (addressId == 0) {
                        ToastUtil.show(getApplicationContext(), "请选择收货地址");
                        return;
                    }
                }
                affirmOrder();
                break;
            case R.id.view_xuyao:
                invoice = 0;
                deliver_fee = 0.0;
                viewXuyao.setStatus(true);
                viewZiti.setStatus(false);
                viewYouji.setStatus(false);
                tvSureorderButtomTotal.setText("￥" + price * num);
                llAddress.setVisibility(View.GONE);
                llZiti.setVisibility(View.GONE);
                llYouji.setVisibility(View.GONE);
                llJine.setVisibility(View.GONE);
                tvYoufei.setVisibility(View.GONE);
                break;
            case R.id.view_ziti:
                invoice = 1;
                deliver_fee = 0.0;
                viewXuyao.setStatus(false);
                viewZiti.setStatus(true);
                viewYouji.setStatus(false);
                tvSureorderButtomTotal.setText("￥" + price * num);
                llAddress.setVisibility(View.GONE);
                llZiti.setVisibility(View.VISIBLE);
                llYouji.setVisibility(View.GONE);
                llJine.setVisibility(View.GONE);
                tvYoufei.setVisibility(View.GONE);
                break;
            case R.id.view_youji:
                //address();
                invoice = 2;
                deliver_fee = Double.parseDouble(getIntent().getStringExtra("deliver_fee"));
                viewXuyao.setStatus(false);
                viewZiti.setStatus(false);
                viewYouji.setStatus(true);
                tvSureorderButtomTotal.setText("￥" + (price * num + deliver_fee));
                llAddress.setVisibility(View.VISIBLE);
                llZiti.setVisibility(View.GONE);
                llYouji.setVisibility(View.VISIBLE);
                llJine.setVisibility(View.VISIBLE);
                tvYoufei.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_Address:
                Intent intent = new Intent(getApplicationContext(), Activity_ReceiptAddress.class);
                intent.putExtra("id", 0);
                startActivityForResult(intent, 0x001);
                break;
            case R.id.ll_date:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(SureOrderActivity.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {

                        tvSureorderDate.setText(years + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                DatePicker datePicker = datePickerDialog.getDatePicker();
                Date date = new Date();//当前时间
                long time = date.getTime();
                //datePicker.setCalendarViewShown(false);
                datePicker.setMinDate(time - 1000);
                datePicker.setMaxDate(time + 30 * 24 * 3600 * 1000L);
                datePickerDialog.show();
                break;
        }
    }


    private void affirmOrder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("name", getIntent().getStringExtra("name") + "");
        params.addQueryStringParameter("orderDescribe", "订单描述");
        params.addQueryStringParameter("price", getIntent().getDoubleExtra("price", 0) + "");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sDateFormat.format(new java.util.Date());
        params.addQueryStringParameter("startValid", currentDate);
        params.addQueryStringParameter("endValid", tvSureorderDate.getText().toString());
        params.addQueryStringParameter("quantity", num + "");
        params.addQueryStringParameter("visitorsId", getIntent().getStringExtra("visitorsId"));//景区ID
        //判断是否需要邮寄费
        if (invoice == 2) {
            params.addQueryStringParameter("realPrice", (getIntent().getDoubleExtra("price", 0) * num + deliver_fee) + "");
        } else {
            params.addQueryStringParameter("realPrice", (getIntent().getDoubleExtra("price", 0) * num) + "");
        }

        if (invoice == 0) {
            params.addQueryStringParameter("isInvoice", 0 + "");//发票是否需要 0不要，1自提，2邮寄
            params.addQueryStringParameter("isMention", 0 + "");//发票是否需要 0不要，1自提，2邮寄
            params.addQueryStringParameter("addressId", 0 + "");
        } else if (invoice == 1) {
            params.addQueryStringParameter("isInvoice", 1 + "");//发票是否需要 0不要，1自提，2邮寄
            params.addQueryStringParameter("isMention", 0 + "");//发票是否需要 0不要，1自提，2邮寄
            params.addQueryStringParameter("addressId", 0 + "");
        } else if (invoice == 2) {
            params.addQueryStringParameter("isInvoice", 1 + "");//发票是否需要 0不要，1自提，2邮寄
            params.addQueryStringParameter("isMention", 1 + "");//发票是否需要 0不要，1自提，2邮寄
            params.addQueryStringParameter("addressId", addressId + "");
        }



        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.affirmOrder, params, new RequestCallBack<String>() {
            DialogProgressbar dialogProgressbar = new DialogProgressbar(SureOrderActivity.this, R.style.AlertDialogStyle);

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
                        Intent intent = new Intent(SureOrderActivity.this, ChosePaywayActivity.class);
                        intent.putExtra("data", jsonObject.getLong("data") + "");
                        intent.putExtra("invoice", invoice);
                        intent.putExtra("youxiaoqi", tvSureorderDate.getText().toString());
                        if (invoice == 2) {
                            intent.putExtra("deliver_fee", deliver_fee + "");
                            intent.putExtra("name", userName.getText().toString());
                            intent.putExtra("telphone", telphone.getText().toString());
                            intent.putExtra("address", address.getText().toString());
                        }
                        startActivity(intent);
                        finish();
                    } else if (i == 3) {
                        DialogAgainSignIn dialogAgainSignIn = new DialogAgainSignIn(SureOrderActivity.this, R.style.AlertDialogStyle);
                        dialogAgainSignIn.show();
                    } else if (i == 4) {
                        ToastUtil.show(getApplicationContext(), "请选择当前日期后的时间");
                    } else {
                        ToastUtil.show(getApplicationContext(), header.getString("msg"));
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

    //获取默认地址
    private void address() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        //params.addQueryStringParameter("id",);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.address, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(SureOrderActivity.this, R.style.AlertDialogStyle);

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
                                MainActivity.state_Three(SureOrderActivity.this);
                            } else if (i == 4) {//无默认收货地址
                                userName.setText(getResources().getString(R.string.address_shang));
                                telphone.setText("");
                                address.setText(getResources().getString(R.string.address_xia));
                                addressId = 0;
                            } else {
                                ToastUtil.show(getApplicationContext(), addressBean.getHeader().getMsg());
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
