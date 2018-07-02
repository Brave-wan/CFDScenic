package com.demo.demo.myapplication.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.demo.chat.Constants;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_MyTicket;
import com.demo.my.activity.Activity_MyTicketDetails;
import com.demo.scenicspot.activity.ChosePaywayActivity;
import com.demo.scenicspot.activity.PayResultActivity;
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
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	public static boolean pay=false;
	Intent intent = new Intent();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

		pay=false;

    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
//		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		String result = "支付失败";
		pay=false;
		finish();
		if (resp.errCode == 0) {
			result = "支付成功";
			pay=true;
			//payOrder();

		}
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			builder.setMessage(getString(R.string.pay_result_callback_msg, result));
			builder.show();
		}
	}
	private void payOrder(){
		RequestParams params = new RequestParams();
		params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
		params.addQueryStringParameter("orderCode", ChosePaywayActivity.getVisitorsOrderByIdBean.getData().getOrder_code());
		params.addQueryStringParameter("balance",  ChosePaywayActivity.getVisitorsOrderByIdBean.getData().getBalance() + "");
		params.addQueryStringParameter("price", ChosePaywayActivity.getVisitorsOrderByIdBean.getData().getPrice() + "");
		params.addQueryStringParameter("payType", 2+"");
		params.addQueryStringParameter("passWord", "");
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0*1000);
		http.send(HttpRequest.HttpMethod.GET, URL.payOrder, params,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						try {
							JSONObject jsonObject = new JSONObject(responseInfo.result);
							JSONObject header = jsonObject.getJSONObject("header");
							int i = header.getInt("status");
							if (i == 0) {
								if (ChosePaywayActivity.entrance == 0) {//景区进入

								} else if (ChosePaywayActivity.entrance == 1) {//我的门票进入
									Activity_MyTicket.myTicket=true;
								} else if ( ChosePaywayActivity.entrance== 2) {//我的门票详情进入
									Activity_MyTicketDetails.myTicketDetails=true;
									Activity_MyTicket.myTicket=true;
								}
								intent = new Intent(getApplicationContext(), PayResultActivity.class);
								intent.putExtra("data", jsonObject.getLong("data"));
								startActivity(intent);
								finish();
							} else if (i == 3) {
								//异地登录对话框，必须传.this  不能传Context
								MainActivity.state_Three(WXPayEntryActivity.this);
							} else if (i == 4) {
								ToastUtil.show(getApplicationContext(), "余额不足");
							} else if (i == 5) {
								ToastUtil.show(getApplicationContext(), "密码错误");
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