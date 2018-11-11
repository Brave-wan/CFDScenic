package com.demo.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_MyCollection;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_MyTicket;
import com.demo.my.activity.Activity_MyWallet;
import com.demo.my.activity.Activity_News;
import com.demo.my.activity.Activity_PersonalData;
import com.demo.my.activity.Activity_PersonalData2;
import com.demo.my.activity.Activity_ReceiptAddress;
import com.demo.my.activity.Activity_Recreation;
import com.demo.my.activity.Activity_RefundOrder;
import com.demo.my.activity.Activity_ShoppingCart;
import com.demo.my.activity.Activity_SignIn;
import com.demo.my.activity.Activity_set;
import com.demo.my.bean.GetDatum;
import com.demo.my.bean.GetWaitPayBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyLinearLayoutItem;
import com.demo.view.RoundImageView;
import com.google.gson.Gson;
import com.jauker.widget.BadgeView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 我的
 * Created by Administrator on 2016/7/20 0020.
 */
public class MyFragment extends Fragment {
    Intent intent;

    @Bind(R.id.ll_my_whole)
    LinearLayout llMyWhole;
    @Bind(R.id.ll_my_payment)
    LinearLayout llMyPayment;
    @Bind(R.id.ll_my_use)
    LinearLayout llMyUse;
    @Bind(R.id.ll_my_evaluate)
    LinearLayout llMyEvaluate;
    @Bind(R.id.ll_my_overdue)
    LinearLayout llMyOverdue;
    @Bind(R.id.view_MyOrder)
    LinearLayout viewMyOrder;
    @Bind(R.id.view_refund)
    MyLinearLayoutItem viewRefund;
    @Bind(R.id.view_Cart)
    MyLinearLayoutItem viewCart;
    @Bind(R.id.view_goods)
    MyLinearLayoutItem viewGoods;
    @Bind(R.id.view_wallet)
    MyLinearLayoutItem viewWallet;
    @Bind(R.id.view_recreation)
    MyLinearLayoutItem viewRecreation;
    @Bind(R.id.view_collection)
    MyLinearLayoutItem viewCollection;
    @Bind(R.id.view_Setup)
    MyLinearLayoutItem viewSetup;
    @Bind(R.id.bt_my_share)
    Button btMyShare;
    @Bind(R.id.im_my_bianji)
    ImageView imMyBianji;
    @Bind(R.id.im_my_xiaoxi)
    ImageView imMyXiaoxi;

    @Bind(R.id.tv_NickNameOrSignIn)
    TextView tvNickNameOrSignIn;
    @Bind(R.id.iv_Gender)
    ImageView ivGender;
    @Bind(R.id.ll_my_tuik)
    LinearLayout llMyTuik;
    @Bind(R.id.iv_daizhifu)
    ImageView ivDaizhifu;
    @Bind(R.id.iv_weishiyong)
    ImageView ivWeishiyong;
    @Bind(R.id.im_my_touxiang)
    RoundImageView imMyTouxiang;

    private PopupWindow mPopWindow;
    BadgeView badgeView_dzf;
    BadgeView badgeView_wsy;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, view);
        intent = new Intent();

        badgeView_dzf = new BadgeView(getActivity());
        badgeView_dzf.setTargetView(ivDaizhifu);
        badgeView_dzf.setTextSize(7);
        badgeView_dzf.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);

        badgeView_wsy = new BadgeView(getActivity());
        badgeView_wsy.setTargetView(ivWeishiyong);
        badgeView_wsy.setTextSize(7);
        badgeView_wsy.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //我的门票角标
        if (SpUtil.getString(getActivity(), SpName.token, "").equals("")) {
            badgeView_dzf.setBadgeCount(0);
            badgeView_wsy.setBadgeCount(0);

            ivGender.setVisibility(View.GONE);
            imMyTouxiang.setImageResource(R.mipmap.wd_weidenglu);
            tvNickNameOrSignIn.setText(SpUtil.getString(getContext(), SpName.userName, "去登录"));
        } else {
            getDatum();
            getWaitPay();
        }


        //判断男女
       /* if (SpUtil.getString(getActivity(), SpName.Gender, "").equals("0")) {
            ivGender.setVisibility(View.VISIBLE);
            ivGender.setImageResource(R.mipmap.wd1_tubiao_nan);
        } else if (SpUtil.getString(getActivity(), SpName.Gender, "").equals("1")) {
            ivGender.setVisibility(View.VISIBLE);
            ivGender.setImageResource(R.mipmap.wd1_tubiao_nv);
        } else {
            ivGender.setVisibility(View.GONE);
        }*/
        //下载头像
        //ImageLoader.getInstance().displayImage(SpUtil.getString(getActivity(), SpName.headimg, ""), imMyTouxiang);
        //修改昵称
        //tvNickNameOrSignIn.setText(SpUtil.getString(getContext(), SpName.userName, "去登录"));
    }


    @OnClick({R.id.ll_my_whole, R.id.ll_my_payment, R.id.ll_my_use, R.id.ll_my_evaluate, R.id.ll_my_overdue, R.id.view_MyOrder, R.id.view_refund, R.id.view_Cart, R.id.view_goods
            , R.id.view_wallet, R.id.view_recreation, R.id.view_collection, R.id.view_Setup, R.id.bt_my_share, R.id.im_my_touxiang, R.id.im_my_xiaoxi, R.id.im_my_bianji,
            R.id.tv_NickNameOrSignIn, R.id.ll_my_tuik})
    public void onClick(View view) {
        if (SpUtil.getString(getContext(), SpName.token, "").equals("")) {
            intent.setClass(getContext(), Activity_SignIn.class);
            intent.putExtra("index", 1);
            startActivityForResult(intent, 0x001);
            return;
        }
        switch (view.getId()) {
            case R.id.ll_my_whole://我的门票--全部
                intent.setClass(getContext(), Activity_MyTicket.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
            case R.id.ll_my_payment://待支付
                intent.setClass(getContext(), Activity_MyTicket.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.ll_my_use://未使用
                intent.setClass(getContext(), Activity_MyTicket.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.ll_my_evaluate://待评价
                intent.setClass(getContext(), Activity_MyTicket.class);
                intent.putExtra("type", "3");
                startActivity(intent);
                break;
            case R.id.ll_my_overdue://已过期
                intent.setClass(getContext(), Activity_MyTicket.class);
                intent.putExtra("type", "4");
                startActivity(intent);
                break;
            case R.id.ll_my_tuik://退款
                intent.setClass(getContext(), Activity_MyTicket.class);
                intent.putExtra("type", "5");
                startActivity(intent);
                break;
            case R.id.view_MyOrder://我的订单
                intent.setClass(getContext(), Activity_MyOrder.class);
                startActivity(intent);
                break;
            case R.id.view_refund://退款订单
                intent.setClass(getContext(), Activity_RefundOrder.class);
                startActivity(intent);
                break;
            case R.id.view_Cart://购物车
                intent.setClass(getContext(), Activity_ShoppingCart.class);
                startActivity(intent);
                break;
            case R.id.view_goods://收货地址
                intent.setClass(getContext(), Activity_ReceiptAddress.class);
                startActivity(intent);
                break;
            case R.id.view_wallet://我的钱包
                intent.setClass(getContext(), Activity_MyWallet.class);
                startActivity(intent);
                break;
            case R.id.view_recreation://我的游乐圈
                intent.setClass(getContext(), Activity_Recreation.class);
                startActivity(intent);
                break;
            case R.id.view_collection://我的收藏
                intent.setClass(getContext(), Activity_MyCollection.class);
                startActivity(intent);
                break;
            case R.id.view_Setup://设置
                intent.setClass(getContext(), Activity_set.class);
                startActivity(intent);
                break;
            case R.id.bt_my_share://分享给APP链接给好友
                show_popupWindow();
                break;
            case R.id.im_my_touxiang://头像--个人资料
                intent.setClass(getContext(), Activity_PersonalData.class);
                startActivityForResult(intent, 0x001);
                break;
            case R.id.im_my_bianji://编辑
                //原先
                intent.setClass(getContext(), Activity_PersonalData.class);
                startActivityForResult(intent, 0x001);
                /*intent.setClass(getContext(), Activity_PersonalData2.class);
                startActivity(intent);*/
                break;
            case R.id.im_my_xiaoxi://消息
                intent.setClass(getContext(), Activity_News.class);
                startActivity(intent);
                break;
            case R.id.tv_NickNameOrSignIn://用户名  登录
                if (!SpUtil.getString(getContext(), SpName.token, "").equals("")) {
                    return;
                }
                intent.setClass(getContext(), Activity_SignIn.class);
                startActivityForResult(intent, 0x001);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x001 && resultCode == 0x001) {
            if (data.getBooleanExtra("ok", true)) {
                getDatum();
            }
        }
    }

    //显示popupWindow
    private void show_popupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popup_my_share, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        LinearLayout ll_qq = (LinearLayout) contentView.findViewById(R.id.ll_share_qq);
        LinearLayout ll_weibo = (LinearLayout) contentView.findViewById(R.id.ll_share_weibo);
        LinearLayout ll_weixin = (LinearLayout) contentView.findViewById(R.id.ll_share_weixin);
        LinearLayout ll_pengyouquan = (LinearLayout) contentView.findViewById(R.id.ll_share_pengyouquan);
        LinearLayout ll_popup = (LinearLayout) contentView.findViewById(R.id.ll_popup);
        ll_qq.setOnClickListener(new Share_Click());
        ll_popup.setOnClickListener(new Share_Click());
        ll_weibo.setOnClickListener(new Share_Click());
        ll_weixin.setOnClickListener(new Share_Click());
        ll_pengyouquan.setOnClickListener(new Share_Click());

        //显示PopupWindow
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.popup_my_share, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }


    //分享的单击事件
    private class Share_Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
//            ShareSDK.initSDK(getActivity());
            switch (v.getId()) {
                case R.id.ll_share_qq://qq
                    QQ.ShareParams sp = new QQ.ShareParams();
                    sp.setTitle("下载智慧湿地游，畅游曹妃甸");
                    sp.setTitleUrl("https://www.pgyer.com/cfdsd"); // 标题的超链接
                    sp.setText("我最近在使用“智慧湿地游”客户端。你也来看看吧！");
                    sp.setImageUrl("http://139.129.167.238:88/app/logo.png");
                    sp.setSite("智慧湿地游");
                    sp.setSiteUrl("https://www.pgyer.com/cfdsd");
                    Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    qq.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                            initShare();
                        }

                        @Override
                        public void onError(Platform platform, int i, Throwable throwable) {
                            ToastUtil.show(getActivity(), "分享失败");
                        }

                        @Override
                        public void onCancel(Platform platform, int i) {
                            ToastUtil.show(getActivity(), "取消分享");
                        }
                    });
                    qq.share(sp);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_share_weibo://微博
                    break;
                case R.id.ll_share_weixin://微信

                    OnekeyShare oks = new OnekeyShare();
                    oks.disableSSOWhenAuthorize();//关闭sso授权
//                    oks.setTitle("下载智慧湿地游，畅游曹妃甸");
//                    oks.setUrl("http://www.pgyer.com/cfdsd");
//                    oks.setText("我最近在使用“智慧湿地游”客户端。你也来看看吧！");
////                    oks.setImageUrl("http://sdy.cfdsd.com/cdfImage/logo.png");
//                    oks.setSiteUrl("https://www.pgyer.com/cfdsd");
//                    oks.setTitleUrl("http://sharesdk.cn");

                    oks.setTitle("下载智慧湿地游，畅游曹妃甸");
                    oks.setTitleUrl("https://www.pgyer.com/cfdsd");
                    oks.setText("我最近在使用“智慧湿地游”客户端。你也来看看吧！");
                    oks.setImageUrl("http://sdy.cfdsd.com/cfdImage/logo.png");
                    oks.setUrl("https://www.pgyer.com/cfdsd");
                    oks.setSite("智慧湿地游");
                    oks.setSiteUrl("https://www.pgyer.com/cfdsd");
                    oks.setPlatform(Wechat.NAME);
                    oks.show(getActivity());
                    break;
                case R.id.ll_share_pengyouquan://朋友圈
                    WechatMoments.ShareParams sp3 = new WechatMoments.ShareParams();
                    sp3.setTitle("下载智慧湿地游，畅游曹妃甸");
                    sp3.setTitleUrl("https://www.pgyer.com/cfdsd");
                    sp3.setText("我最近在使用“智慧湿地游”客户端。你也来看看吧！");
                    sp3.setImageUrl("http://sdy.cfdsd.com/cfdImage/logo.png");
                    sp3.setUrl("https://www.pgyer.com/cfdsd");
                    sp3.setSite("智慧湿地游");
                    sp3.setSiteUrl("https://www.pgyer.com/cfdsd");
                    sp3.setShareType(Platform.SHARE_WEBPAGE);
                    Platform wechatmoments = ShareSDK.getPlatform(WechatMoments.NAME);
                    wechatmoments.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                            initShare();
                        }

                        @Override
                        public void onError(Platform platform, int i, Throwable throwable) {
                            ToastUtil.show(getActivity(), "分享失败");
                        }

                        @Override
                        public void onCancel(Platform platform, int i) {
                            ToastUtil.show(getActivity(), "取消分享");
                        }
                    });
                    wechatmoments.share(sp3);
                    break;
                case R.id.ll_popup:
                    mPopWindow.dismiss();
                    break;
            }
        }
    }


    private void getWaitPay() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getContext(), SpName.token, ""));
        //params.addQueryStringParameter("id",);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getWaitPay, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            GetWaitPayBean getWaitPayBean = new Gson().fromJson(responseInfo.result, GetWaitPayBean.class);
                            int i = getWaitPayBean.getHeader().getStatus();
                            if (i == 0) {
                                badgeView_dzf.setBadgeCount(getWaitPayBean.getData().getWaitPay());
                                badgeView_wsy.setBadgeCount(getWaitPayBean.getData().getNoUse());
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getContext());
                            } else {
                                ToastUtil.show(getContext(), getWaitPayBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), "连接网络失败");
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    //获取
    private void getDatum() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getActivity(), SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.getDatum, params,
                new RequestCallBack<String>() {
                    /* DialogProgressbar dialogProgressbar=new DialogProgressbar(getActivity(),R.style.AlertDialogStyle);
                     @Override
                     public void onStart() {
                         super.onStart();
                         dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                         dialogProgressbar.show();
                     }

                     @Override
                     public void onSuccess(ResponseInfo<String> responseInfo) {
                         dialogProgressbar.dismiss();*/
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            GetDatum getDatum = new Gson().fromJson(responseInfo.result, GetDatum.class);
                            int i = getDatum.getHeader().getStatus();
                            if (i == 0) {
                                if (getDatum.getData() != null) {
                                    if (getDatum.getData().getGender() == 0) {
                                        SpUtil.putString(getActivity(), SpName.Gender, "0");
                                        ivGender.setVisibility(View.VISIBLE);
                                        ivGender.setImageResource(R.mipmap.wd1_tubiao_nan);
                                    } else if (getDatum.getData().getGender() == 1) {
                                        SpUtil.putString(getActivity(), SpName.Gender, "1");
                                        ivGender.setVisibility(View.VISIBLE);
                                        ivGender.setImageResource(R.mipmap.wd1_tubiao_nv);
                                    }
                                    ImageLoader.getInstance().displayImage(getDatum.getData().getHeadImg(), imMyTouxiang);
                                    if (!getDatum.getData().getNickName().equals("")) {
                                        tvNickNameOrSignIn.setText(getDatum.getData().getNickName());
                                        SpUtil.putString(getActivity(), SpName.userName, getDatum.getData().getNickName());
                                    } else {
                                        tvNickNameOrSignIn.setText(getDatum.getData().getMobileNo() + "");
                                        SpUtil.putString(getActivity(), SpName.userName, getDatum.getData().getMobileNo());
                                    }
                                }
                            } else {
                                ToastUtil.show(getActivity(), getDatum.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getActivity(), "解析数据失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getActivity(), "连接网络失败");
                    }
                });

    }

}
