package com.demo.mall.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.demo.alipay.PayResult;
import com.demo.alipay.SignUtils;
import com.demo.demo.myapplication.R;
import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.NullBean;
import com.demo.mall.bean.PayFinshShowBean;
import com.demo.my.activity.Activity_Identity;
import com.demo.my.activity.Activity_PaymentPassword;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商城--支付订单--酒店
 * Created by Administrator on 2016/8/8 0008.
 */
public class ChoossePaymentJdActivity extends Activity {

    @Bind(R.id.tv_jiuName)
    TextView tvJiuName;
    @Bind(R.id.tv_fangName)
    TextView tvFangName;
    @Bind(R.id.tv_ruzhu)
    TextView tvRuzhu;
    @Bind(R.id.tv_lidian)
    TextView tvLidian;
    @Bind(R.id.tv_wan)
    TextView tvWan;
    @Bind(R.id.tv_tian)
    TextView tvTian;
    @Bind(R.id.tv_fangjianshu)
    TextView tvFangjianshu;
    @Bind(R.id.tv_xingming1)
    TextView tvXingming1;
    @Bind(R.id.ll_ruzhu1)
    LinearLayout llRuzhu1;
    @Bind(R.id.tv_xingming2)
    TextView tvXingming2;
    @Bind(R.id.ll_ruzhu2)
    LinearLayout llRuzhu2;
    @Bind(R.id.tv_xingming3)
    TextView tvXingming3;
    @Bind(R.id.ll_ruzhu3)
    LinearLayout llRuzhu3;
    @Bind(R.id.tv_xingming4)
    TextView tvXingming4;
    @Bind(R.id.ll_ruzhu4)
    LinearLayout llRuzhu4;
    @Bind(R.id.tv_xingming5)
    TextView tvXingming5;
    @Bind(R.id.ll_ruzhu5)
    LinearLayout llRuzhu5;
    @Bind(R.id.tv_xingming6)
    TextView tvXingming6;
    @Bind(R.id.ll_ruzhu6)
    LinearLayout llRuzhu6;
    @Bind(R.id.tv_xingming7)
    TextView tvXingming7;
    @Bind(R.id.ll_ruzhu7)
    LinearLayout llRuzhu7;
    @Bind(R.id.tv_xingming8)
    TextView tvXingming8;
    @Bind(R.id.ll_ruzhu8)
    LinearLayout llRuzhu8;
    @Bind(R.id.iv_payment_yue)
    ImageView ivPaymentYue;
    @Bind(R.id.ll_payment_yue)
    LinearLayout llPaymentYue;
    @Bind(R.id.iv_payment_zhifubao)
    ImageView ivPaymentZhifubao;
    @Bind(R.id.ll_payment_zhifubao)
    LinearLayout llPaymentZhifubao;
   /* @Bind(R.id.iv_payment_weixin)
    ImageView ivPaymentWeixin;
    @Bind(R.id.ll_payment_weixin)
    LinearLayout llPaymentWeixin;*/
    @Bind(R.id.tv_zong)
    TextView tvZong;
    @Bind(R.id.tv_jifen)
    TextView tvJifen;
    @Bind(R.id.bt_confirm)
    Button btConfirm;

    String id;
    int payWay = 1;
    EditText et_password;
    PopupWindow mPopWindow;
    PayFinshShowBean payFinshShowBean;

    ArrayList<String> stringList = new ArrayList<>();
    ArrayList<LinearLayout> llArray = new ArrayList<>();
    ArrayList<TextView> tvArray = new ArrayList<>();
    @Bind(R.id.tv_yue)
    TextView tvYue;


    // 商户PID  2088421796384561
     String PARTNER = "";
    // 商户收款账号  caofeidianshidi10@163.com
     String SELLER = "";
    // 商户私钥，pkcs8格式
     String RSA_PRIVATE = "";
            //"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMR48f+UVKTHExiPT4ywPtKUKJRWK3ArsQ+ewAlhxqy90G0KlyVK1VXxMkYFcGsvvjhpbmtZ9hv0fRBsEAsoiHiKtwVKIHDysMGuUjAftyrRmP48VIk7GXNPHe5vVEClAMQQIxWXzKR0Lne7CXL6lb9YB+cK6kCCMdZfEfgUkkbxAgMBAAECgYEAionN+q4ZUWeeq37CVS4h3lLimlZ4osvsOltyhisP8NaXlYwWMBGohMVi8cT8FFjCBP0zAzeYNvYbCe1pErUuJLUdgHF9GVKX0ZjKR2nWirtyXBrJaQuItvJS5IeJf4tm22UDk4dpyHAoJymFoF25KwOAgKilKNuHNoUfxMnZ8AECQQDnNXy0NzsTwjxlCRaGqXMEuWz/wspB/17/nf9qpJTvjKmnOBhV1lxrux1n5TRqJaU6iWIz6zPVXOw5UMn6XT9BAkEA2Yn3ppwxdoNtJusDSIo/OTk5Xu3cX1rQPoQVbZi91GVfz86DJXsUXscd3c6Ach3BiwFjN/mcv1skFzpbDbHLsQJBANFvRO/mG9CRIK4Q5mPC+Jot8QtYgmf4EDCSCTyrqvG3RDJiAME4dO1tSHzFRY5lXV9B9T+8bW53Rs/AW8U7VEECQFCG5gni39yDIjC55mk/48+HA1nEq0MRFu1Nb7E2TW8GZ8VBKQMC4LOE+eVVv+5+XVrblKLv37pmPqHqlDIpksECQC+/IdZP9U1aEhFoBbTa/2tMoMX/ZDhTCnHDGNOH765CvNkwlBzQ4zcdHSHIqe43kYR8rQVWMGB32k+5TcJ3A2k=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;
    Intent intent = new Intent();
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(ChoossePaymentJdActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        updatePayOrder();
//                        payOrder();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(ChoossePaymentJdActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(ChoossePaymentJdActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment_jd);
        ButterKnife.bind(this);
        //init();
        PARTNER=getIntent().getStringExtra("partner");
        SELLER=getIntent().getStringExtra("seller");
        RSA_PRIVATE=getIntent().getStringExtra("privateKey");
        payFinshShow();
    }

    private void init() {
        stringList = getIntent().getStringArrayListExtra("StringList");
        llArray.add(llRuzhu1);
        tvArray.add(tvXingming1);
        llArray.add(llRuzhu2);
        tvArray.add(tvXingming2);
        llArray.add(llRuzhu3);
        tvArray.add(tvXingming3);
        llArray.add(llRuzhu4);
        tvArray.add(tvXingming4);
        llArray.add(llRuzhu5);
        tvArray.add(tvXingming5);
        llArray.add(llRuzhu6);
        tvArray.add(tvXingming6);
        llArray.add(llRuzhu7);
        tvArray.add(tvXingming7);
        llArray.add(llRuzhu8);
        tvArray.add(tvXingming8);

        for (int i = 0; i < stringList.size(); i++) {
            llArray.get(i).setVisibility(View.VISIBLE);
            tvArray.get(i).setText(stringList.get(i));
        }

        id = getIntent().getStringExtra("id");
    }
//R.id.ll_payment_weixin,
    @OnClick({R.id.ll_payment_yue, R.id.ll_payment_zhifubao,  R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_payment_yue:
                initView();
                payWay=1;
                ivPaymentYue.setImageResource(R.mipmap.bt_yuan_chengse_true);
                break;
            case R.id.ll_payment_zhifubao:
                initView();
                payWay=2;
                ivPaymentZhifubao.setImageResource(R.mipmap.bt_yuan_chengse_true);
                break;
           /* case R.id.ll_payment_weixin:
                initView();
                payWay=3;
                ivPaymentWeixin.setImageResource(R.mipmap.bt_yuan_chengse_true);
                break;*/
            case R.id.bt_confirm:
                if (payWay == 1) {
                    if (payFinshShowBean.getData().getBalance() < payFinshShowBean.getData().getReal_price()) {
                        ToastUtil.show(getApplicationContext(), "余额不足");
                        return;
                    }
                    show_popupWindow();
                } else {
                    if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
                        new AlertDialog.Builder(this).setTitle("警告").setMessage("暂不支持支付宝支付，请用余额支付")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int i) {
                                        //
                                       // finish();
                                    }
                                }).show();
                        return;
                    }
                    try {
                    String orderInfo = getOrderInfo(payFinshShowBean.getData().getOrderName(), "酒店房间预定", payFinshShowBean.getData().getReal_price()+"");// payFinshShowBean.getData().getPrice()+""
/*
                    *
                     * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
                     */
                    String sign = sign(orderInfo);
                    try {
                      /*  *
                         * 仅需对sign 做URL编码
                         */
                        sign = URLEncoder.encode(sign, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                   /* *
                     * 完整的符合支付宝参数规范的订单信息
                     */
                    final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            // 构造PayTask 对象
                            PayTask alipay = new PayTask(ChoossePaymentJdActivity.this);
                            // 调用支付接口，获取支付结果
                            String result = alipay.pay(payInfo, true);

                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };

                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
//                    updatePayOrder();
                    } catch (Exception e) {
                        ToastUtil.show(getApplicationContext(), "支付宝信息错误，无法支付");
                    }
                }

                break;
        }
    }
    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        return orderInfo;
    }
    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
    private void initView() {
//        ivPaymentWeixin.setImageResource(R.mipmap.bt_yuan_chengse_false);
        ivPaymentYue.setImageResource(R.mipmap.bt_yuan_chengse_false);
        ivPaymentZhifubao.setImageResource(R.mipmap.bt_yuan_chengse_false);
    }

    private void payFinshShow() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("hotelOrderId", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.payFinshShow, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(ChoossePaymentJdActivity.this,R.style.AlertDialogStyle);
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
                            payFinshShowBean = new Gson().fromJson(responseInfo.result, PayFinshShowBean.class);
                            int i = payFinshShowBean.getHeader().getStatus();
                            if (i == 0) {
                                tvJiuName.setText(payFinshShowBean.getData().getOrderName());
                                tvFangName.setText(payFinshShowBean.getData().getGoods_name());
                                tvRuzhu.setText("入住：" + payFinshShowBean.getData().getStart_date().substring(0, 10));
                                tvLidian.setText("离店：" + payFinshShowBean.getData().getEnd_date().substring(0, 10));
                                tvWan.setText(payFinshShowBean.getData().getCheck_days() + "晚");
                                tvTian.setText("￥" + payFinshShowBean.getData().getPrice());
                                tvFangjianshu.setText(payFinshShowBean.getData().getQuantity()+"间");
                                tvXingming1.setText(payFinshShowBean.getData().getPersonName());
                                tvYue.setText("(余额：￥"+payFinshShowBean.getData().getBalance()+")");
                                tvZong.setText("¥"+payFinshShowBean.getData().getReal_price());
                                tvJifen.setText(payFinshShowBean.getData().getReal_price()+"");
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(ChoossePaymentJdActivity.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), payFinshShowBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络出错");
                    }
                });
    }

    private void updatePayOrder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("id", getIntent().getStringExtra("id")+"");
        params.addQueryStringParameter("payType", payWay + "");
        params.addQueryStringParameter("orderType", 2 + "");
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode")+"");
        params.addQueryStringParameter("balance", payFinshShowBean.getData().getBalance() + "");
        params.addQueryStringParameter("price", payFinshShowBean.getData().getReal_price() + "");
        if (et_password==null){
            params.addQueryStringParameter("passWord", "");
        }else {
            params.addQueryStringParameter("passWord", et_password.getText().toString());
        }
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.updatePayOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(ChoossePaymentJdActivity.this,R.style.AlertDialogStyle);
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
                            NullBean nullBean = new Gson().fromJson(responseInfo.result, NullBean.class);
                            if (nullBean.getHeader().getStatus() == 0) {
                                Intent intent = new Intent(getApplicationContext(), PaymentResultJdActivity.class);
                                intent.putExtra("data", getIntent().getStringExtra("id") + "");
                                startActivity(intent);
                                finish();
                            } else if (nullBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(ChoossePaymentJdActivity.this);
                            } else if (nullBean.getHeader().getStatus() == 4) {
                                ToastUtil.show(getApplicationContext(), "密码错误");
                            } else {
                                ToastUtil.show(ChoossePaymentJdActivity.this, nullBean.getHeader().getMsg());
                            }

                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        dialogProgressbar.dismiss();
                        ToastUtil.show(getApplicationContext(), "连接网络出错");
                    }
                });

    }

    private void show_popupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(ChoossePaymentJdActivity.this).inflate(R.layout.popup_input_password, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        contentView.findViewById(R.id.ll_popup).setOnClickListener(new Click());
        contentView.findViewById(R.id.tv_forget).setOnClickListener(new Click());
        contentView.findViewById(R.id.tv_setPassword).setOnClickListener(new Click());
        TextView tv_recharge = (TextView) contentView.findViewById(R.id.tv_recharge);
        et_password = (EditText) contentView.findViewById(R.id.et_password);
        tv_recharge.setOnClickListener(new Click());
        tv_recharge.setText("支付");
        contentView.findViewById(R.id.im_left).setOnClickListener(new Click());
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //显示PopupWindow
        View rootview = LayoutInflater.from(ChoossePaymentJdActivity.this).inflate(R.layout.popup_input_password, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    private class Click implements View.OnClickListener {
        Intent intent;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_popup:
                    mPopWindow.dismiss();
                    break;
                case R.id.tv_forget:
                    mPopWindow.dismiss();
                    intent = new Intent(getApplicationContext(), Activity_Identity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_setPassword:
                    mPopWindow.dismiss();
                    intent = new Intent(getApplicationContext(), Activity_PaymentPassword.class);
                    startActivity(intent);
                    break;
                case R.id.tv_recharge:  //支付成功
                    if (et_password.getText().toString().equals("")) {
                        ToastUtil.show(getApplicationContext(), "请输入密码");
                        return;
                    }
                    updatePayOrder();
                    break;
                case R.id.im_left:
                    mPopWindow.dismiss();
                    break;
            }
        }
    }

}
