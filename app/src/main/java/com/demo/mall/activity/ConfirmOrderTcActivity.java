package com.demo.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.bean.AlipayBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.AddressBean;
import com.demo.mall.bean.CreateOrderBean;
import com.demo.my.activity.Activity_ReceiptAddress;
import com.demo.my.bean.AddressListBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
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

import org.apache.http.protocol.HTTP;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class ConfirmOrderTcActivity extends Activity {

    @Bind(R.id.ll_Address)
    LinearLayout llAddress;
    @Bind(R.id.iv_business)
    ImageView ivBusiness;
    @Bind(R.id.tv_businessName)
    TextView tvBusinessName;
    @Bind(R.id.iv_commodity)
    ImageView ivCommodity;
    @Bind(R.id.tv_commodityName)
    TextView tvCommodityName;
    @Bind(R.id.tv_present)//商品价格
            TextView tvPresent;
    @Bind(R.id.tv_primary)
    TextView tvPrimary;
    @Bind(R.id.tv_Number)
    TextView tvNumber;
    @Bind(R.id.ll_false)
    LinearLayout llFalse;
    @Bind(R.id.view_ziti)
    MyRadioButton viewZiti;
    @Bind(R.id.view_songhuo)
    MyRadioButton viewSonghuo;
    @Bind(R.id.tv_gongshangpin)
    TextView tvGongshangpin;
    @Bind(R.id.tv_Distribution)
    TextView tvDistribution;//配送费
    @Bind(R.id.tv_Total)
    TextView tvTotal;
    @Bind(R.id.tv_Total2)
    TextView tvTotal2;
    @Bind(R.id.tv_ToPay)
    TextView tvToPay;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.telphone)
    TextView telphone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.tv_ChoiceDate)
    TextView tvChoiceDate;
    @Bind(R.id.ll_psf)
    LinearLayout llPsf;
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


    String json;

    String goodsId = "";
    String businessName = "";
    String business = "";
    int isMention = -1;//是否自提 0否1是
    long addressId = 0;//收货地址ID
    String commodityName;
    String primarys;
    String presents;
    String shopInformationId;
    String Numbers;
    String commodity;
    String distributions;
    String orderDescribe;
    int peisongI;//配送费
    int total;//总价

    boolean bClick = true;//判断是否可以点击
    public static String time = "";
    AlipayBean alipayBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order_tc);
        ButterKnife.bind(this);
        alipayBean=new AlipayBean();

        goodsId = getIntent().getStringExtra("id");
        businessName = getIntent().getStringExtra("businessName");
        business = getIntent().getStringExtra("business");
        commodityName = getIntent().getStringExtra("commodityName");
        primarys = getIntent().getStringExtra("primary");
        shopInformationId = getIntent().getStringExtra("shopInformationId");
        Numbers = getIntent().getStringExtra("Number");
        commodity = getIntent().getStringExtra("commodity");
        presents = getIntent().getStringExtra("present");
        distributions = getIntent().getStringExtra("Distribution");
        orderDescribe = getIntent().getStringExtra("orderDescribe");

        BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
        bitmapUtils.display(ivBusiness, business);
        tvBusinessName.setText(businessName);

        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
        bitmapUtils.display(ivCommodity, commodity);
        tvCommodityName.setText(commodityName);//商品名称
        tvPresent.setText("￥" + presents);//现价
        tvPrimary.setText("￥" + primarys);//原件
        tvPrimary.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvNumber.setText("X" + Numbers);//数量
        tvDistribution.setText("￥" + distributions);//配送费
        tvGongshangpin.setText("共"+Numbers+"件商品");//显示多少件商品
        int presentI = Integer.parseInt(presents);
        int numI = Integer.parseInt(Numbers);
        peisongI = Integer.parseInt(distributions);
        total = presentI * numI;
        tvTotal.setText("￥" + total);
        tvTotal2.setText(total + "");
        //获取支付宝信息
        getAlipay();

    }

    private void getAlipay() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("siId",getIntent().getStringExtra("shopInformationId"));
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
                            alipayBean= new Gson().fromJson(responseInfo.result, AlipayBean.class);
                            if(alipayBean.getHeader().getStatus()==0){
                            }else if(alipayBean.getHeader().getStatus()==3){
                            }else {
                                ToastUtil.show(ConfirmOrderTcActivity.this, alipayBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(ConfirmOrderTcActivity.this,"解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(ConfirmOrderTcActivity.this, "连接网络失败");
                    }
                });

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
            DialogProgressbar dialogProgressbar=new DialogProgressbar(ConfirmOrderTcActivity.this,R.style.AlertDialogStyle);
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

    @OnClick({R.id.ll_Address, R.id.tv_ToPay, R.id.view_ziti, R.id.view_songhuo})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_Address:
                intent = new Intent(getApplicationContext(), Activity_ReceiptAddress.class);
                intent.putExtra("id", 0);
                startActivityForResult(intent, 0x001);
                break;
            case R.id.tv_ToPay:
                if (isMention == -1) {
                    ToastUtil.show(getApplicationContext(), "请选择收货方式");
                    return;
                }
                if (isMention == 0) {
                    if (addressId == 0) {
                        ToastUtil.show(getApplicationContext(), "请选择收货地址");
                        return;
                    }
                }
                createGoodsOrder();
                break;
            case R.id.view_ziti:
                llPsf.setVisibility(View.GONE);
                viewZiti.setStatus(true);
                viewSonghuo.setStatus(false);
                isMention = 1;
                tvTotal.setText("￥" + total);
                tvTotal2.setText(total + "");
                break;
            case R.id.view_songhuo:
                if (addressId == 0) {
                    address();
                }
                llPsf.setVisibility(View.VISIBLE);
                viewZiti.setStatus(false);
                viewSonghuo.setStatus(true);
                isMention = 0;
                tvTotal.setText("￥" + (total + peisongI));
                tvTotal2.setText((total + peisongI) + "");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x001 && resultCode == 0x001) {
            addressId = data.getLongExtra("addressId", 0);
            userName.setText(data.getStringExtra("name"));
            telphone.setText(data.getStringExtra("telphone"));
            address.setText(data.getStringExtra("address"));
        }
    }

    //提交订单
    private void createGoodsOrder() {
        // HH:mm:ss
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        time = format.format(date);
        // 商品名字 商品描述 原价 创建时间 数量 总价 id 店铺ID 配送费 地址id
        if (isMention == 1) {
            addressId = 0;
        }
        json = "[{\"name\":\"" + businessName + "\"," +
                "\"orderDescribe\":\"" + orderDescribe + "\"," +
                "\"price\":" + primarys + "," +
                "\"deliverDate\":" + time + "," +
                "\"quantity\":" + Numbers + "," +
                "\"realPrice\":" + tvTotal2.getText().toString() + "," +
                "\"goodsId\":" + goodsId + "," +
                "\"shopInformationId\":" + shopInformationId + "," +
                "\"deliverFee\":" + distributions + "," +
                "\"addressId\":" + addressId + "," +
                "\"isPickup\":" + isMention + "}]";//是否自提 0否1是
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("goodsOrderJson", json);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.createGoodsOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(ConfirmOrderTcActivity.this, R.style.AlertDialogStyle);

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
                            CreateOrderBean createOrderBean = new Gson().fromJson(responseInfo.result, CreateOrderBean.class);
                            int i = createOrderBean.getHeader().getStatus();
                            if (i == 0) {
                                Intent intent = new Intent(getApplicationContext(), PaymentOrderTcActivity.class);
                                intent.putExtra("name", commodityName);//createOrderBean.getData().get(0).get(0).getName()
                                intent.putExtra("singleprice",presents);
                                intent.putExtra("num", Numbers);
                                intent.putExtra("Price", tvTotal2.getText().toString());
                                intent.putExtra("id", createOrderBean.getData().get(0).get(0).getId() + "");
                                intent.putExtra("orderCode", createOrderBean.getData().get(0).get(0).getOrderCode());
                                if(alipayBean.getData()==null||alipayBean.getData().equals("")){
                                   // ToastUtil.show(ConfirmOrderTcActivity.this,"支付宝暂时无法支付");
                                   startActivity(intent);
                                }else{
                                    intent.putExtra("partner",alipayBean.getData().getPartner());
                                    intent.putExtra("seller",alipayBean.getData().getSeller());
                                    intent.putExtra("privateKey",alipayBean.getData().getPrivateKey());
                                    startActivity(intent);
                                }
                                finish();
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(ConfirmOrderTcActivity.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), createOrderBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplication(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

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
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
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
                                MainActivity.state_Three(ConfirmOrderTcActivity.this);
                            } else if (i == 4) {
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
