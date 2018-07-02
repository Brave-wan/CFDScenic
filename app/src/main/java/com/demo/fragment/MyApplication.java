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
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.demo.demo.myapplication.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
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

//        //设置设备拨号监听器
//        ret = IDpsdkCore.DPSDK_SetRingCallback(m_ReValue.nReturnValue, new fDPSDKRingInfoCallBack() {
//
//            @Override
//            public void invoke(int nPDLLHandle, RingInfo_t param) {
//                //获取拨号信息
//                Log.e(TAG, "fDPSDKRingInfoCallBack RingInfo_t info"
//                        +"      callId : "+ param.callId);
//
//                //界面跳转
//                Intent intent = new Intent(MyApplication.this, AutoVtActivity.class);
//                Bundle bundle = new Bundle();
//
//                bundle.putByteArray("szUserId", param.szUserId);
//                bundle.putInt("callId", param.callId);
//                bundle.putInt("dlgId", param.dlgId);
//                bundle.putInt("tid", param.tid);
//
//                intent.putExtras(bundle);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);      //若在不同的进程需要添加flag,
//                startActivity(intent);
//            }
//        });
//
//        //设置可视对讲呼叫邀请参数回调
//        ret = IDpsdkCore.DPSDK_SetVtCallInviteCallback(m_ReValue.nReturnValue, new fDPSDKInviteVtCallParamCallBack() {
//
//            @Override
//            public void invoke(int nPDLLHandle, InviteVtCallParam_t param) {
//
//                //通过设备呼叫号码，查找设备id，再查找设备的编码通道
//                String strcallnum = new String(param.szUserId).trim();
//
//                List<Device_Info_Ex_t> devlist = GroupListManager.getInstance().getDeviceExList();
//                List<Enc_Channel_Info_Ex_t> channellist;
//                Device_Info_Ex_t deviceInfoEx;
//                byte[] szId = new byte[dpsdk_constant_value.DPSDK_CORE_DEV_ID_LEN];
//                Enc_Channel_Info_Ex_t encChannelInfoEx = new Enc_Channel_Info_Ex_t();
//                String channelname = "";
//
//                for (int i = 0; i < devlist.size(); i++) {
//                    deviceInfoEx = devlist.get(i);
//                    String szCallNum = new String(deviceInfoEx.szCallNum).trim();
//                    if (deviceInfoEx != null && strcallnum.equals(szCallNum)) { //匹配设备呼叫号码
//                        //channellist = GroupListManager.getInstance().getChannelsByDeviceId(deviceInfoEx.szId);  //通过设备id查找编码通道
//
////						if(channellist != null && channellist.size()>0){
////							encChannelInfoEx = channellist.get(0);   //取编码通道中的第一个
////						}
//                        byte[] bt = (new String(deviceInfoEx.szId).trim() + "$1$0$0").getBytes();
//                        System.arraycopy(bt, 0, szId, 0, bt.length);
//
//                        channelname = new String(szId).trim();
//                        Log.e(TAG, "****************channelid****************" + "           " + channelname);
//                    }
//                }
//
//                //界面跳转
//                Intent intent = new Intent(AppApplication.this, AutoVtActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putByteArray("szUserId", param.szUserId);
//                bundle.putInt("audioType", param.audioType);
//                bundle.putInt("audioBit", param.audioBit);
//                bundle.putInt("sampleRate", param.sampleRate);
//                bundle.putByteArray("rtpServIP", param.rtpServIP);
//                bundle.putInt("rtpAPort", param.rtpAPort);
//                bundle.putInt("rtpVPort", param.rtpVPort);
//                bundle.putInt("nCallType", param.nCallType);
//                bundle.putInt("tid", param.tid);
//                bundle.putInt("callId", param.callId);
//                bundle.putInt("dlgId", param.dlgId);
//
////				bundle.putByteArray("channelid", encChannelInfoEx.szId);
////				bundle.putByteArray("channelname", encChannelInfoEx.szName);
//                bundle.putByteArray("channelid", szId);
//                bundle.putByteArray("channelname", szId);
//
//                intent.putExtras(bundle);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);      //若在不同的进程需要添加flag,
//                startActivity(intent);
//
//            }
//        });
    }


}