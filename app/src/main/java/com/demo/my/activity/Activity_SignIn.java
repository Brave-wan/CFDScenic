package com.demo.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.bean.ThirdLoginBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
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
import com.mob.tools.utils.UIHandler;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import android.os.Handler.Callback;

/**
 * 登录
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_SignIn extends Activity implements Callback,
        View.OnClickListener, PlatformActionListener{

    @Bind(R.id.im_guanbi)
    ImageView imGuanbi;
    @Bind(R.id.tv_signin_register)
    TextView tvSigninRegister;
    @Bind(R.id.tv_ForgetPassword)
    TextView tvForgetPassword;
    @Bind(R.id.bt_SignIn)
    Button btSignIn;
    @Bind(R.id.et_phoneNumber)
    EditText etPhoneNumber;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.iv_weixin)
    ImageView ivWeixin;
    @Bind(R.id.iv_qq)
    ImageView ivQq;
    @Bind(R.id.iv_weibo)
    ImageView ivWeibo;
    private static final int MSG_ACTION_CCALLBACK = 2;
    int so;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads().detectDiskWrites().detectNetwork()
//                .penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
//                .penaltyLog().penaltyDeath().build());


        if (!SpUtil.getString(getApplicationContext(),SpName.phoneNumber,"").equals("")){
            etPhoneNumber.setText(SpUtil.getString(getApplicationContext(),SpName.phoneNumber,""));
            etPhoneNumber.setSelection(etPhoneNumber.getText().length());
        }

    }

    @OnClick({R.id.im_guanbi, R.id.tv_signin_register, R.id.tv_ForgetPassword, R.id.bt_SignIn,R.id.iv_weixin, R.id.iv_qq, R.id.iv_weibo})
    public void onClick(View view) {
        Intent intent = new Intent();
        ShareSDK.initSDK(Activity_SignIn.this);
        switch (view.getId()) {
            case R.id.im_guanbi:
                finish();
                break;
            case R.id.tv_signin_register:
                intent.setClass(getApplicationContext(), Activity_Register.class);
                startActivity(intent);
                break;
            case R.id.tv_ForgetPassword:
                intent.setClass(getApplicationContext(), Activity_BackPassword1.class);
                startActivity(intent);
                break;
            case R.id.bt_SignIn:    //登录
                if (etPhoneNumber.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"手机号不能为空");
                    return;
                }
                if (etPassword.getText().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"密码不能为空");
                    return;
                }
                if (etPhoneNumber.getText().toString().length()<11){
                    ToastUtil.show(getApplicationContext(), "用户名错误");
                    return;
                }
                Slogin();
                break;
            case R.id.iv_weixin:
                so=2;
                Platform weixinfd = ShareSDK.getPlatform(Wechat.NAME);
                weixinfd.setPlatformActionListener(this);

                if (weixinfd.isValid ()){
                    weixinfd.removeAccount();
                }

                weixinfd.showUser(null);
                break;
            case R.id.iv_qq:
                so=1;
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.SSOSetting(false); // 设置false表示使用SSO授权方式
                qq.setPlatformActionListener(this); // 设置分享事件回调
                if (qq.isValid()) {
                    qq.removeAccount(true);
                }
                if (qq.isClientValid()) {
                    System.out.println("安装了QQ");
                } else {
                    System.out.println("没有安装了QQ");

                }

                qq.showUser(null);// 获取到用户信息
                // qq.authorize();//只是单独授权登录
                break;
            case R.id.iv_weibo:
                so=3;
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.setPlatformActionListener(this);
                weibo.authorize();//单独授权
                weibo.showUser(null);//授权并获取用户信息
                //authorize与showUser单独调用一个即可
                //移除授权
                //weibo.removeAccount(true);
                break;
        }
    }
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 成功
                Toast.makeText(Activity_SignIn.this, "登录成功", Toast.LENGTH_SHORT)
                        .show();

                Intent intent=new Intent();
                intent.putExtra("ok",true);
                setResult(0x001, intent);
            }
            break;
            case 2: {
                // 失败   "登录失败"+ msg.obj
                Toast.makeText(Activity_SignIn.this,"登录失败"+ msg.obj, Toast.LENGTH_SHORT).show();

            }
            break;
            case 3: {
                // 取消
                Toast.makeText(Activity_SignIn.this, "取消登录", Toast.LENGTH_SHORT)
                        .show();
            }
            break;
        }

        return false;
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message msg = new Message();
         msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = i;
        msg.obj = platform;

        UIHandler.sendMessage(msg, this);

        System.out.println(hashMap);

        // 获取资料
        platform.getDb().getUserName();// 获取用户名字
        platform.getDb().getUserIcon(); // 获取用户头像
        platform.getDb().getUserGender();
        platform.getDb().getUserId();
        if(platform.getDb().getUserGender().equals("f")){//0男1女
        gender="1";
        }else{
            gender="0";
        }

        getUser(platform.getDb().getUserName(), platform.getDb().getUserIcon(), gender, platform.getDb().getUserId());
        String userid = platform.getDb().getUserId();
        System.out.println("userid================" + userid);
    }

    private void getUser(final String userName,final String userIcon,final String gender, final String userId) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("openId",userId);
        params.addQueryStringParameter("nickName",userName);
        params.addQueryStringParameter("gender", gender);
        params.addQueryStringParameter("headImg", userIcon);
        params.addQueryStringParameter("source", so + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.thirdLoginin, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            ThirdLoginBean thirdLoginBean = new Gson().fromJson(responseInfo.result, ThirdLoginBean.class);
                            if (thirdLoginBean.getHeader().getStatus() == 0) {
                                SpUtil.putString(getApplication(), SpName.token, thirdLoginBean.getData().getToken());
                                SpUtil.putString(getApplication(), SpName.userId, thirdLoginBean.getData().getUserId());
                                SpUtil.putString(getApplicationContext(), SpName.Gender, gender);
                                SpUtil.putString(getApplicationContext(), SpName.headimg, userIcon);
                                SpUtil.putString(getApplicationContext(), SpName.userName, userName);
                                //极光推送设置标签别名
                                JPushInterface.setAlias(Activity_SignIn.this, thirdLoginBean.getData().getUserId() + "", new TagAliasCallback() {
                                    @Override
                                    public void gotResult(int i, String s, Set<String> set) {

                                    }
                                });

                                if (getIntent().getIntExtra("index",-1)==1){
                                    MainActivity.index=1;
                                }
                                finish();
//                                Intent intent = new Intent();
//                                intent.putExtra("tou", userIcon);
//                                intent.putExtra("ni",userName);
//                                setResult(100, intent);

                            } else if (thirdLoginBean.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_SignIn.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), thirdLoginBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_SignIn.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_SignIn.this, e.getMessage());
                    }
                });

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
//        Toast.makeText(Activity_SignIn.this,throwable.getMessage(),Toast.LENGTH_LONG).show();
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = i;
        msg.obj = throwable;
        UIHandler.sendMessage(msg, this);

        // 分享失败的统计
        ShareSDK.logDemoEvent(4, platform);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }
    //登录
    private void Slogin() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("mobileNo", etPhoneNumber.getText().toString());
        params.addBodyParameter("password", etPassword.getText().toString());
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.SignIn, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_SignIn.this, R.style.AlertDialogStyle);

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
                                ToastUtil.show(getApplicationContext(), "登录成功");

                                JSONObject data = jsonObject.getJSONObject("data");
                                //极光推送设置标签别名
                                JPushInterface.setAlias(Activity_SignIn.this, data.getString("userId"), null);

                                /*Intent intent=new Intent();
                                intent.putExtra("ok",true);
                                setResult(0x001, intent);*/

                                SpUtil.putString(getApplication(), SpName.token, data.getString("token"));
                                SpUtil.putString(getApplication(), SpName.userId, data.getString("userId"));
                                SpUtil.putString(getApplication(), SpName.userName, etPhoneNumber.getText().toString());
                                SpUtil.putString(getApplication(), SpName.phoneNumber, etPhoneNumber.getText().toString());

                                if (getIntent().getIntExtra("index",-1)==1){
                                    MainActivity.index=1;
                                }
                                finish();
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplication(), s);
                    }
                });
    }

}
