package com.demo.fragment;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.dh.DpsdkCore.IDpsdkCore;
import com.dh.DpsdkCore.Return_Value_Info_t;
import com.dh.DpsdkCore.RingInfo_t;
import com.dh.DpsdkCore.fDPSDKInviteVtCallParamCallBack;
import com.dh.DpsdkCore.fDPSDKRingInfoCallBack;
import com.dh.DpsdkCore.fDPSDKStatusCallback;
import com.hik.mcrsdk.MCRSDK;
import com.hik.mcrsdk.rtsp.RtspClient;
import com.hik.mcrsdk.talk.TalkClientSDK;
import com.hikvision.sdk.VMSNetSDK;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.demo.demo.myapplication.R;
import com.tencent.smtt.sdk.QbSdk;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class MyApplication extends Application {

    private static final String TAG = "AppApplication";
    private static final String LOG_PATH = Environment.getExternalStorageDirectory().getPath() + "/DPSDKlog.txt";

    private static MyApplication _instance;
    private int m_loginHandle = 0;   //标记登录是否成功   1登录成功   0登录失败
    private int m_nLastError = 0;
    private Return_Value_Info_t m_ReValue = new Return_Value_Info_t();

    public static synchronized MyApplication get() {
        return _instance;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        configImageLoader();
        ShareSDK.initSDK(this);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                        //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        initApp();
    }

    private void configImageLoader() {


        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.zhanwei) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.zhanwei) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.zhanwei) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.PNG, 70,null)//CompressFormat.PNG类型，70质量（0-100）
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(60 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();

        ImageLoader.getInstance().init(config);
    }
    public int getDpsdkHandle(){
        if(m_loginHandle == 1)  //登录成功，返回PDSDK_Creat时返回的 有效句柄
            return m_ReValue.nReturnValue;
        else
            return m_ReValue.nReturnValue;
    }

    public int getDpsdkCreatHandle(){  //仅用于获取DPSDK_login的句柄
        return m_ReValue.nReturnValue;
    }

    public void setLoginHandler(int loginhandler){
        this.m_loginHandle = loginhandler;
    }

    public int getLoginHandler(){
        return this.m_loginHandle;
    }




    /**
     * 全局初始化，在SplashActivity中调用
     */
    public void initApp() {

        //Creat DPSDK

        int nType = 1;
        m_nLastError = IDpsdkCore.DPSDK_Create(nType, m_ReValue);

        //set logPath
        m_nLastError = IDpsdkCore.DPSDK_SetLog(m_ReValue.nReturnValue, LOG_PATH.getBytes());
        Log.d("DPSDK_SetLog:", m_nLastError + "");

        int ret = IDpsdkCore.DPSDK_SetDPSDKStatusCallback(m_ReValue.nReturnValue, new fDPSDKStatusCallback() {

            @Override
            public void invoke(int nPDLLHandle, int nStatus) {
                Log.v("fDPSDKStatusCallback", "nStatus = " + nStatus);
            }
        });
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);

        MCRSDK.init();
        // 初始化RTSP
        RtspClient.initLib();
        MCRSDK.setPrint(1, null);
        // 初始化语音对讲
        TalkClientSDK.initLib();
        // SDK初始化
        VMSNetSDK.init(this);
    }


}