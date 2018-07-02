package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.adapter.ConfirmOrderAdapter;
import com.demo.adapter.ShoppingCartAdapter;
import com.demo.bean.AlipayBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.activity.ConfirmOrderTcActivity;
import com.demo.mall.bean.AddressBean;
import com.demo.mall.bean.CreateOrderBean;
import com.demo.my.bean.GetShopCartByUserIdBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyTopBar;
import com.google.gson.Gson;
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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的购物车--确认订单
 * Created by Administrator on 2016/8/8 0008.
 */
public class Activity_ConfirmOrder extends Activity {

    @Bind(R.id.ll_Address)
    LinearLayout llAddress;
    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.tv_payment)
    TextView tvPayment;
    @Bind(R.id.lv_confirmOrder)
    ExpandableListView lvConfirmOrder;

    String totalMoney;
    ConfirmOrderAdapter adapter;
    List<GetShopCartByUserIdBean.DataBean> list;
    long addressId = -1;//收货地址ID
    @Bind(R.id.tv_totalMoney)
    public TextView tvTotalMoney;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.telphone)
    TextView telphone;
    @Bind(R.id.address)
    TextView address;

    public Double bottomTotal = 0.0;//下方总价
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        viewTopbar.setFocusable(true);
        viewTopbar.setFocusableInTouchMode(true);
        viewTopbar.requestFocus();

        list = ShoppingCartAdapter.list;
        adapter = new ConfirmOrderAdapter(Activity_ConfirmOrder.this, list);
        //计算下方总额
        for (int group = 0; group < adapter.getGroupCount(); group++) {
            Double total = 0.0;
            int fee = 0;
            for (int child = 0; child < adapter.getChildrenCount(group); child++) {
                if (list.get(group).getGoodsList().get(child).getState() == 0) {
                    fee = fee + list.get(group).getGoodsList().get(child).getDeliverFee();
                    total = total + list.get(group).getGoodsList().get(child).getNumber() * list.get(group).getGoodsList().get(child).getNew_price();
                }
            }
            bottomTotal = bottomTotal + total + fee;
        }


        lvConfirmOrder.setAdapter(adapter);


        //全部展开
        for (int zk = 0; zk < adapter.getGroupCount(); zk++) {
            lvConfirmOrder.expandGroup(zk);
        }
        //不能收缩
        lvConfirmOrder.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
        lvConfirmOrder.setGroupIndicator(null);


        tvTotalMoney.setText("￥" + bottomTotal);
        address();
    }

    @OnClick({R.id.ll_Address, R.id.tv_payment})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_Address:
                intent = new Intent(getApplicationContext(), Activity_ReceiptAddress.class);
                intent.putExtra("id", 0);
                startActivityForResult(intent, 0x001);
                break;
            case R.id.tv_payment:
                int group;
                boolean b = true;
                for (group = 0; group < adapter.getGroupCount(); group++) {
                    if (list.get(group).getIsPickup() == 0) {
                        if (addressId == -1) {
                            ToastUtil.show(getApplicationContext(), "请选择收货地址");
                            b = false;
                            group = adapter.getGroupCount();
                        }
                    }
                }
                if (b) {
                    createGoodsOrder();
                    return;
                }
                /*if (addressId==-1){
                    ToastUtil.show(getApplicationContext(),"请选择收货地址");
                    return;
                }*/
                break;
        }
    }


    //提交订单
    private void createGoodsOrder() {
        // HH:mm:ss
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer scIds = new StringBuffer();
        stringBuffer.append("[");
        for (int groupPosition = 0; groupPosition < adapter.getGroupCount(); groupPosition++) {
            for (int childPosition = 0; childPosition < adapter.getChildrenCount(groupPosition); childPosition++) {
                if (list.get(groupPosition).getGoodsList().get(childPosition).getState() == 0) {
                    stringBuffer.append("{\"name\":\"" + list.get(groupPosition).getGoodsList().get(childPosition).getGoods_name() + "\",");
                    stringBuffer.append("\"orderDescribe\":\"" + list.get(groupPosition).getGoodsList().get(childPosition).getGoods_describe() + "\",");
                    stringBuffer.append("\"price\":" + list.get(groupPosition).getGoodsList().get(childPosition).getNew_price() + ",");
                    //stringBuffer.append("{\"deliverDate:\""+list.get(groupPosition).getGoodsList().get(childPosition).getGoods_name());//发货时间
                    stringBuffer.append("\"quantity\":" + list.get(groupPosition).getGoodsList().get(childPosition).getNumber() + ",");
                    if (list.get(groupPosition).getIsPickup() == 0) {//0否1是
                        stringBuffer.append("\"realPrice\":" + list.get(groupPosition).getGoodsList().get(childPosition).getNumber() * list.get(groupPosition).getGoodsList().get(childPosition).getNew_price() + ",");
                    } else {
                        stringBuffer.append("\"realPrice\":" + (list.get(groupPosition).getGoodsList().get(childPosition).getNumber() * list.get(groupPosition).getGoodsList().get(childPosition).getNew_price() + list.get(groupPosition).getGoodsList().get(childPosition).getDeliverFee()) + ",");
                    }
                    stringBuffer.append("\"goodsId\":" + list.get(groupPosition).getGoodsList().get(childPosition).getShopGoodsId() + ",");
                    stringBuffer.append("\"shopInformationId\":" + list.get(groupPosition).getGoodsList().get(childPosition).getShopInformationId() + ",");
                    stringBuffer.append("\"deliverFee\":" + list.get(groupPosition).getGoodsList().get(childPosition).getDeliverFee() + ",");
                    stringBuffer.append("\"addressId\":" + addressId + ",");
                    stringBuffer.append("\"isPickup\":" + list.get(groupPosition).getIsPickup() + "},");

                    scIds.append(list.get(groupPosition).getGoodsList().get(childPosition).getShopCartId() + ",");
                }
            }
        }
        String json = stringBuffer.toString();
        json = json.substring(0, json.length() - 1);

        String scId = scIds.toString();
        scId = scId.substring(0, scId.length() - 1);
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("goodsOrderJson", json + "]");
        params.addQueryStringParameter("scIds", scId);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.createGoodsOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_ConfirmOrder.this, R.style.AlertDialogStyle);

                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框意外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        try {
                            CreateOrderBean createOrderBean = new Gson().fromJson(responseInfo.result, CreateOrderBean.class);
                            int i = createOrderBean.getHeader().getStatus();
                            if (i == 0) {
                                finish();
                                Activity_ShoppingCart.bRefresh = true;
                                Intent intent = new Intent(getApplicationContext(), Activity_CartPaymentOrder.class);
                                intent.putExtra("json", responseInfo.result);

                                startActivity(intent);
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ConfirmOrder.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), createOrderBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplication(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
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
                                MainActivity.state_Three(Activity_ConfirmOrder.this);
                            } else if (i == 4) {
                            } else {
                                ToastUtil.show(getApplicationContext(), addressBean.getHeader().getMsg());
                            }

                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), e.getMessage());
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

}
