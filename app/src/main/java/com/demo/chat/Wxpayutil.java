package com.demo.chat;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class Wxpayutil {

    /**
     * 微信支付
     *
     * @num 支付金额
     * @hailiaobi 获取嗨聊币数量
     */
    public static String id = "";

    public static void pay(final Context context, int num) {
        String key = "";
//        private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
//            return key;
//        }
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        final String strhhh = sdf.format(date1);
        final IWXAPI api = WXAPIFactory.createWXAPI(context, "wx4c05486c196ff0d5");

        Toast.makeText(context, "获取订单中...", Toast.LENGTH_SHORT).show();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("body", "曹妃甸湿地游");
        params.addQueryStringParameter("orderCode", key);
        params.addQueryStringParameter("balance", (num*100) + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.WXPay, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            WxBean wxBean = new Gson().fromJson(responseInfo.result, WxBean.class);

                            if (wxBean.getHeader().getStatus() == 0) {
                                PayReq req = new PayReq();
//                                req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                                req.appId = Constants.APP_ID;
                                req.partnerId = wxBean.getData().getMch_id();//getString("partnerid");
                                req.prepayId = wxBean.getData().getPrepay_id();//.getString("prepayid");
                                req.nonceStr = wxBean.getData().getNonce_str();//json.getString("noncestr");
                                req.timeStamp = strhhh;//json.getString("timestamp");
                                req.packageValue = "Sign=WXPay";//json.getString("package");
//                                req.sign			= wxBean.getData().getSign();//json.getString("sign");
                                req.extData = "app data"; // optional
//                                req.extData			= "app data"; // optional
                                List<NameValuePair> signParams = new LinkedList<NameValuePair>();
                                signParams.add(new BasicNameValuePair("appid", req.appId));
                                signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
                                signParams.add(new BasicNameValuePair("package", req.packageValue));
                                signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
                                signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
                                signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
                                req.sign = genAppSign(signParams);
//                               Toast.makeText(context, "正常调起支付", Toast.LENGTH_SHORT).show();

                                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                api.registerApp(Constants.APP_ID);
                                api.sendReq(req);
                            }
                        } catch (Exception e) {
                            ToastUtil.show(context, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(context, e.getMessage());
                    }
                });


//        try{
//                                byte[] buf = Util.httpGet(url);
//                                if (buf != null && buf.length > 0) {
//                                    String content = new String(buf);
//                                    Log.e("get server pay params:",content);
//                                    JSONObject json = new JSONObject(content);
//                                    if(null != json && !json.has("retcode") ){
//                                        PayReq req = new PayReq();
//                                        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
//                                        req.appId			= json.getString("appid");
//                                        req.partnerId		= json.getString("partnerid");
//                                        req.prepayId		= json.getString("prepayid");
//                                        req.nonceStr		= json.getString("noncestr");
//                                        req.timeStamp		= json.getString("timestamp");
//                                        req.packageValue	= json.getString("package");
//                                        req.sign			= json.getString("sign");
//                                        req.extData			= "app data"; // optional
////                                        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
////                                        signParams.add(new BasicNameValuePair("appid", req.appId));
////                                        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
////                                        signParams.add(new BasicNameValuePair("package", req.packageValue));
////                                        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
////                                        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
////                                        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//                                       Toast.makeText(context, "正常调起支付", Toast.LENGTH_SHORT).show();
////                                       req.sign = genAppSign(signParams);
////									req.sign			= json.getString("sign");
//
//                                    req.extData			= "app data"; // optional
////                                    Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
//
//                                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//                                    api.registerApp(Constants.APP_ID);
//                                    api.sendReq(req);
//                                }else{
//                                    Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
////                                    Toast.makeText(PayActivity.this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
//                                }
//                            }else{
//                                Log.d("PAY_GET", "服务器请求错误");
////                                Toast.makeText(PayActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
//                            }
//                            }catch(Exception e){
//                            Log.e("PAY_GET", "异常："+e.getMessage());
//                            Toast.makeText(PayActivity.this, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
    }


    /**
     * 获取签名
     **/


    private static String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);


        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();

        return appSign;
    }
}
