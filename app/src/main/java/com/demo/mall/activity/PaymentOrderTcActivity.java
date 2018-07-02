package com.demo.mall.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.ParseException;
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
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.NullBean;
import com.demo.mall.bean.SmallPayShowBean;
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
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class PaymentOrderTcActivity extends Activity {

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
    @Bind(R.id.bt_confirm)
    Button btConfirm;
    @Bind(R.id.tv_Price)
    TextView tvPrice;
    @Bind(R.id.tv_jifen)
    TextView tvJifen;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_jine)
    TextView tvJine;

    String names;
    String nums;
    String jines;
    String id;
    String single;//单价
    //int Total;
    int payWay = 1;
    PopupWindow mPopWindow;
    EditText et_password;
    SmallPayShowBean showBean = new SmallPayShowBean();
    @Bind(R.id.tv_payment_yue)
    TextView tvPaymentYue;
    String pass = "";

    boolean bClick = true;//判断是否可以点击


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
                        Toast.makeText(PaymentOrderTcActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        updatePayOrder();
//                        payOrder();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PaymentOrderTcActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PaymentOrderTcActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

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
        setContentView(R.layout.activity_payment_order_tc);
        ButterKnife.bind(this);

        PARTNER=getIntent().getStringExtra("partner");
        SELLER=getIntent().getStringExtra("seller");
        RSA_PRIVATE=getIntent().getStringExtra("privateKey");

        names = getIntent().getStringExtra("name");
        nums = getIntent().getStringExtra("num");
        jines = getIntent().getStringExtra("Price");//总价
        id = getIntent().getStringExtra("id");
        single=getIntent().getStringExtra("singleprice");
        tvName.setText(names);
        tvNum.setText(nums + "份");
        tvPrice.setText("￥" +single);// jines

        int num = Integer.parseInt(nums);
        int jine = Integer.parseInt(jines);
        //Total = num * jine;
        tvJifen.setText(" " + jines + " ");
        tvJine.setText("￥" + jines + "");
        init();
    }

    private void init() {
        RequestParams params = new RequestParams();
        //params.addQueryStringParameter("id",);
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.smallPayShow, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(PaymentOrderTcActivity.this, R.style.AlertDialogStyle);

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
                            showBean = new Gson().fromJson(responseInfo.result, SmallPayShowBean.class);
                            if (showBean.getHeader().getStatus() == 0) {
                                tvPaymentYue.setText("(余额：￥" + showBean.getData().get(0).getBalance() + ")");
                            } else if (showBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(PaymentOrderTcActivity.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), showBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(PaymentOrderTcActivity.this, "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(PaymentOrderTcActivity.this, s);
                    }
                });

    }

    @OnClick({R.id.ll_payment_yue, R.id.ll_payment_zhifubao, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_payment_yue://余额支付
                initView();
                ivPaymentYue.setImageResource(R.mipmap.bt_yuan_chengse_true);
                payWay = 1;
                break;
            case R.id.ll_payment_zhifubao://支付宝支付
                initView();
                ivPaymentZhifubao.setImageResource(R.mipmap.bt_yuan_chengse_true);
                payWay = 2;
                break;
           /* case R.id.ll_payment_weixin:
                initView();
                ivPaymentWeixin.setImageResource(R.mipmap.bt_yuan_chengse_true);
                payWay = 3;
                break;*/
            case R.id.bt_confirm:
                if (payWay == 1) {
                    if (showBean.getData().get(0).getBalance() < showBean.getData().get(0).getReal_price()) {
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
                                        //finish();
                                    }
                                }).show();
                        return;
                    }
                    try {
                    String orderInfo = getOrderInfo(names, "商品预定", jines);//jines
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
                            PayTask alipay = new PayTask(PaymentOrderTcActivity.this);
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
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    private void initView() {
//        ivPaymentWeixin.setImageResource(R.mipmap.bt_yuan_chengse_false);
        ivPaymentYue.setImageResource(R.mipmap.bt_yuan_chengse_false);
        ivPaymentZhifubao.setImageResource(R.mipmap.bt_yuan_chengse_false);
    }


    private void updatePayOrder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("id", showBean.getData().get(0).getId() + "");
        params.addQueryStringParameter("payType", payWay + "");
        params.addQueryStringParameter("orderType", 1 + "");
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        params.addQueryStringParameter("balance", showBean.getData().get(0).getBalance() + "");
        params.addQueryStringParameter("price", jines);
        params.addQueryStringParameter("passWord", pass);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.updatePayOrder, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(PaymentOrderTcActivity.this, R.style.AlertDialogStyle);

                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        String s = responseInfo.result;
                        try {
                            NullBean nullBean = new Gson().fromJson(responseInfo.result, NullBean.class);
                            if (nullBean.getHeader().getStatus() == 0) {
                                Intent intent = new Intent(getApplicationContext(), PaymentResultTcActivity.class);
                                intent.putExtra("orderCode", getIntent().getStringExtra("orderCode"));
                                startActivity(intent);
                                finish();
                            } else if (nullBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(PaymentOrderTcActivity.this);
                            }else if (nullBean.getHeader().getStatus() == 4){
                                ToastUtil.show(PaymentOrderTcActivity.this, "密码错误");
                            }else {
                                ToastUtil.show(PaymentOrderTcActivity.this, nullBean.getHeader().getMsg());
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

    private void show_popupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(PaymentOrderTcActivity.this).inflate(R.layout.popup_input_password, null);
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
        View rootview = LayoutInflater.from(PaymentOrderTcActivity.this).inflate(R.layout.popup_input_password, null);
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
                case R.id.tv_forget://忘记密码
                    mPopWindow.dismiss();
                    intent = new Intent(getApplicationContext(), Activity_Identity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_setPassword://设置支付密码
                    mPopWindow.dismiss();
                    intent = new Intent(getApplicationContext(), Activity_PaymentPassword.class);
                    startActivity(intent);
                    break;
                case R.id.tv_recharge:  //支付成功
                    pass = et_password.getText().toString();
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
