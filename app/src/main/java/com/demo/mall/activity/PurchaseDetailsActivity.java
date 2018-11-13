package com.demo.mall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.amusement.activity.Activity_AmusementGraphic;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.FidDetailBean;
import com.demo.mall.bean.FindDetailByGoodsIdBean;
import com.demo.my.activity.Activity_ShoppingCart;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.GradationScrollView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购买详情
 * Created by Administrator on 2016/8/9 0009.
 */
public class PurchaseDetailsActivity extends Activity implements GradationScrollView.ScrollViewListener {


    @Bind(R.id.ll_back)
    ImageView llBack;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_new_price)
    TextView tvNewPrice;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_deliver_fee)
    TextView tvDeliverFee;
    @Bind(R.id.tv_Monthly)
    TextView tvMonthly;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.ll_Number)
    LinearLayout llNumber;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.ll_shop)
    LinearLayout llShop;
    @Bind(R.id.tv_Collection)
    ImageView tvCollection;
    @Bind(R.id.ll_Collection)
    LinearLayout llCollection;
    @Bind(R.id.tv_car)
    TextView tvCar;
    @Bind(R.id.tv_purchase)
    TextView tvPurchase;
    @Bind(R.id.iv_purchase_chart)
    ImageView ivPurchaseChart;
    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_goodTitle)
    TextView tvGoodTitle;
    @Bind(R.id.tv_bottomline)
    TextView tvBottomline;
    @Bind(R.id.ll_purchase_title)
    RelativeLayout llPurchaseTitle;
    @Bind(R.id.sv_purchase_all)
    GradationScrollView svPurchaseAll;
    @Bind(R.id.iv_right_cart)
    ImageView ivRightCart;
    @Bind(R.id.tv_purchase_ylq)
    TextView tvPurchaseYlq;


    private PopupWindow mPopWindow;
    FindDetailByGoodsIdBean findDetailByGoodsIdBean = null;
    FidDetailBean fidDetailBean = null;
    TextView tv_num;
    ImageView iv_reduce;
    ImageView iv_plus;

    String id = "";     //商品ID
    private int num = 1;//数量
    int countNum;//popup计数
    int collection = 0;//1关注，0未关注
    String shopInformationId = "";
    BadgeView badgeView;

    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);
        ButterKnife.bind(this);
        badgeView = new BadgeView(PurchaseDetailsActivity.this, ivPurchaseChart);
        id = getIntent().getStringExtra("id");

        //上滑渐变
        initListeners();
        ivBack.setAlpha(0);
        ivRightCart.setAlpha(0);

//        shopCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断是否登录
        if (SpUtil.getString(getApplication(), SpName.token, "").equals("")) {
            findDetailGoods();
        } else {
            findDetailByGoodsIdAndUid();
        }
    }

    private void initListeners() {

        ViewTreeObserver vto = llBack.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llPurchaseTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = llBack.getHeight();

                svPurchaseAll.setScrollViewListener(PurchaseDetailsActivity.this);
            }
        });
    }

    /**
     * 滑动监听
     */
    public void onScrollChanged(GradationScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            llPurchaseTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            tvBottomline.setBackgroundColor(Color.argb((int) 0, 205, 205, 205));
            tvGoodTitle.setTextColor(Color.argb((int) 0, 51, 51, 51));
            ivBack.setAlpha(0);
            ivRightCart.setAlpha(0);
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            tvGoodTitle.setTextColor(Color.argb((int) alpha, 51, 51, 51));
            llPurchaseTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            tvBottomline.setBackgroundColor(Color.argb((int) alpha, 205, 205, 205));
            ivBack.setAlpha((int) alpha);
            ivRightCart.setAlpha((int) alpha);
           /* if (y<=height/2){
                ivPurchaseChart.setAlpha(255-alpha*2);
            }else {
                ivRightCart.setAlpha((alpha-127)*2);
            }*/
        } else {    //滑动到banner下面设置普通颜色
            llPurchaseTitle.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
        }
    }

    @OnClick({R.id.ll_shop, R.id.ll_Collection, R.id.tv_car, R.id.tv_purchase, R.id.ll_Number, R.id.iv_return, R.id.iv_purchase_chart, R.id.tv_purchase_ylq})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_shop:
                intent = new Intent(getApplicationContext(), ShopActivity.class);
                intent.putExtra("id", fidDetailBean.getData().getDetail().getShop_information_id() + "");
                intent.putExtra("name", fidDetailBean.getData().getDetail().getInformationName());
                intent.putExtra("ImageUrl", fidDetailBean.getData().getDetail().getHead_img());
                startActivity(intent);
                break;
            case R.id.ll_Collection:
                if (collection == 0) {
                    save();
                } else if (collection == 1) {
                    delete();
                }
                break;
            case R.id.tv_car://添加购物车
                if (SpUtil.getString(getApplication(), SpName.token, "").equals("")) {
                    MainActivity.state_Three(PurchaseDetailsActivity.this);
                    return;
                }
//                if (tvNum.getText().toString().equals("")) {
                show_popupWindow();
//                    return;
//                }

                break;
            case R.id.tv_purchase://立即购买
                if (SpUtil.getString(getApplication(), SpName.token, "").equals("")) {
                    MainActivity.state_Three(PurchaseDetailsActivity.this);
                    return;
                }
                if (tvNum.getText().toString().equals("")) {
                    show_popupWindow();
                } else {
                    intent = new Intent(getApplicationContext(), ConfirmOrderTcActivity.class);
                    intent.putExtra("business", fidDetailBean.getData().getDetail().getHead_img());
                    intent.putExtra("businessName", fidDetailBean.getData().getDetail().getInformationName());
                    if (fidDetailBean.getData().getPic().size() > 0) {
                        intent.putExtra("commodity", fidDetailBean.getData().getPic().get(0));
                    } else {
                        intent.putExtra("commodity", "");
                    }
                    intent.putExtra("commodityName", fidDetailBean.getData().getDetail().getGoods_name() + "");
                    intent.putExtra("primary", fidDetailBean.getData().getDetail().getPrice() + "");
                    intent.putExtra("present", fidDetailBean.getData().getDetail().getNew_price() + "");
                    intent.putExtra("Number", num + "");
                    intent.putExtra("id", id + "");
                    intent.putExtra("shopInformationId", shopInformationId + "");
                    intent.putExtra("Distribution", fidDetailBean.getData().getDetail().getDeliver_fee() + "");
                    intent.putExtra("orderDescribe", fidDetailBean.getData().getDetail().getGoods_describe());

                    startActivity(intent);
                }
                break;
            case R.id.ll_Number:
                show_popupWindow();
                break;
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_purchase_chart:
                intent = new Intent(getApplicationContext(), Activity_ShoppingCart.class);
                startActivity(intent);
                break;
            case R.id.tv_purchase_ylq:
                if (SpUtil.getString(getApplication(), SpName.token, "").equals("")) {//未登陆状态
                    intent = new Intent(PurchaseDetailsActivity.this, Activity_AmusementGraphic.class);
                    String[] array = new String[findDetailByGoodsIdBean.getData().getPic().size()];
                    for (int index = 0; index < findDetailByGoodsIdBean.getData().getPic().size(); index++) {
                        array[index] = findDetailByGoodsIdBean.getData().getPic().get(index);
                    }
                    intent.putExtra("array", array);
                    startActivity(intent);

                } else {//登陆状态
                    intent = new Intent(PurchaseDetailsActivity.this, Activity_AmusementGraphic.class);
                    String[] array = new String[fidDetailBean.getData().getPic().size()];
                    for (int index = 0; index < fidDetailBean.getData().getPic().size(); index++) {
                        array[index] = fidDetailBean.getData().getPic().get(index);
                    }
                    intent.putExtra("array", array);
                    startActivity(intent);
                }
                break;
        }
    }


    private void show_popupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(PurchaseDetailsActivity.this).inflate(R.layout.popup_number, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        TextView tv_jiage = (TextView) contentView.findViewById(R.id.tv_jiage);
        ImageView iv_Imageview = (ImageView) contentView.findViewById(R.id.iv_Imageview);
        BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
        //登录没登录需要判断
        if (SpUtil.getString(getApplication(), SpName.token, "").equals("")) {
            tv_jiage.setText("￥" + findDetailByGoodsIdBean.getData().getDetail().getNew_price());
            if (findDetailByGoodsIdBean.getData().getPic().size() > 0)
                bitmapUtils.display(iv_Imageview, findDetailByGoodsIdBean.getData().getPic().get(0));
        } else {
            tv_jiage.setText("￥" + fidDetailBean.getData().getDetail().getNew_price());
            if (fidDetailBean.getData().getPic().size() > 0)
                bitmapUtils.display(iv_Imageview, fidDetailBean.getData().getPic().get(0));
        }

        LinearLayout ll_popup = (LinearLayout) contentView.findViewById(R.id.ll_popup);
        ImageView iv_dismiss = (ImageView) contentView.findViewById(R.id.iv_dismiss);
        iv_reduce = (ImageView) contentView.findViewById(R.id.iv_reduce);
        iv_plus = (ImageView) contentView.findViewById(R.id.iv_plus);
        TextView tv_Determine = (TextView) contentView.findViewById(R.id.tv_Determine);
        tv_num = (TextView) contentView.findViewById(R.id.tv_num);
        /*num = Integer.parseInt(tv_num.getText().toString());*/
        tv_num.setText(num + "");
        if (num > 1) {
            iv_reduce.setImageResource(R.mipmap.jian_lan);
        } else {
            iv_reduce.setImageResource(R.mipmap.jian);
        }
        countNum = num;
        ll_popup.setOnClickListener(new Popup_Click());
        iv_dismiss.setOnClickListener(new Popup_Click());
        iv_reduce.setOnClickListener(new Popup_Click());
        iv_plus.setOnClickListener(new Popup_Click());
        tv_Determine.setOnClickListener(new Popup_Click());


        //显示PopupWindow
        View rootview = LayoutInflater.from(PurchaseDetailsActivity.this).inflate(R.layout.popup_my_share, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }


    class Popup_Click implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_dismiss:
                    mPopWindow.dismiss();
                    break;
                case R.id.iv_reduce:
                    if (countNum == 1) {
                        return;
                    }
                    if (countNum == 2) {
                        countNum = countNum - 1;
                        tv_num.setText(countNum + "");
                        iv_reduce.setImageResource(R.mipmap.jian);
                        return;
                    }
                    countNum = countNum - 1;
                    tv_num.setText(countNum + "");
                    break;
                case R.id.iv_plus:
                    countNum = countNum + 1;
                    if (countNum > 1) {
                        iv_reduce.setImageResource(R.mipmap.jian_lan);
                    }
                    tv_num.setText(countNum + "");
                    break;
                case R.id.tv_Determine:
                    num = countNum;
                    tvNum.setText(num + "");
                    saveShopCart(PurchaseDetailsActivity.this, shopInformationId, num + "", id);
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_popup:
                    mPopWindow.dismiss();
                    break;
            }
        }
    }

    //没登录状态
    private void findDetailGoods() {
        RequestParams params = new RequestParams();
//        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("goodsId", id + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.findDetailByGoodsId, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(PurchaseDetailsActivity.this, R.style.AlertDialogStyle);

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
                            findDetailByGoodsIdBean = new Gson().fromJson(responseInfo.result, FindDetailByGoodsIdBean.class);
                            int i = findDetailByGoodsIdBean.getHeader().getStatus();
                            if (i == 0) {
                                shopInformationId = findDetailByGoodsIdBean.getData().getDetail().getShop_information_id() + "";
                                //启用支持javascript
                                WebSettings settings = webView.getSettings();
                                settings.setJavaScriptEnabled(true);
                                //自适应屏幕
//                                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//                                settings.setLoadWithOverviewMode(true);
//                                settings.setBuiltInZoomControls(true);
//                                settings.setSupportZoom(true);
                                webView.loadUrl(findDetailByGoodsIdBean.getData().getContentUrl());
                                webView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });

                                BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
                                bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
                                if (findDetailByGoodsIdBean.getData().getPic().size() != 0) {
                                    bitmapUtils.display(llBack, findDetailByGoodsIdBean.getData().getPic().get(0));//大图
                                }
                                tvName.setText(findDetailByGoodsIdBean.getData().getDetail().getGoods_name());
                                tvNewPrice.setText("￥" + findDetailByGoodsIdBean.getData().getDetail().getNew_price());
                                tvPrice.setText("原价：￥" + findDetailByGoodsIdBean.getData().getDetail().getPrice());
                                tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tvDeliverFee.setText("配送费:￥" + findDetailByGoodsIdBean.getData().getDetail().getDeliver_fee());
                                tvMonthly.setText("月销" + findDetailByGoodsIdBean.getData().getDetail().getMonthlySales() + "笔");
                            } else {
                                ToastUtil.show(getApplicationContext(), findDetailByGoodsIdBean.getHeader().getMsg());
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

    //登录状态
    private void findDetailByGoodsIdAndUid() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("goodsId", id + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.findDetailByGoodsIdAndUid, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(PurchaseDetailsActivity.this, R.style.AlertDialogStyle);

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
                            fidDetailBean = new Gson().fromJson(responseInfo.result, FidDetailBean.class);
                            int i = fidDetailBean.getHeader().getStatus();
                            if (i == 0) {
                                shopInformationId = fidDetailBean.getData().getDetail().getShop_information_id() + "";
                                //启用支持javascript
                                WebSettings settings = webView.getSettings();
                                settings.setJavaScriptEnabled(true);
//                                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//                                settings.setLoadWithOverviewMode(true);
//                                settings.setBuiltInZoomControls(true);
//                                settings.setSupportZoom(true);
                                webView.loadUrl(fidDetailBean.getData().getContentUrl());
                                webView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });

                                BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
                                bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
                                if (fidDetailBean.getData().getPic().size() != 0) {
                                    bitmapUtils.display(llBack, fidDetailBean.getData().getPic().get(0));//大图
                                }
                                tvName.setText(fidDetailBean.getData().getDetail().getGoods_name());
                                tvNewPrice.setText("￥" + fidDetailBean.getData().getDetail().getNew_price());
                                tvPrice.setText("原价：￥" + fidDetailBean.getData().getDetail().getPrice());
                                tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tvDeliverFee.setText("配送费:￥" + fidDetailBean.getData().getDetail().getDeliver_fee());
                                tvMonthly.setText("月销" + fidDetailBean.getData().getDetail().getMonthlySales() + "笔");
                                if (fidDetailBean.getData().getDetail().getIsCollect() == 1) {//1收藏  0为收藏
                                    collection = 1;
                                    tvCollection.setImageResource(R.mipmap.xingxing_cheng);
                                } else {
                                    collection = 0;
                                    tvCollection.setImageResource(R.mipmap.xingxinghui);
                                }
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(PurchaseDetailsActivity.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), findDetailByGoodsIdBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

    }

    //收藏
    private void save() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("id", id);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.save, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(PurchaseDetailsActivity.this, R.style.AlertDialogStyle);

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
                            int status = header.getInt("status");
                            if (status == 0) {
                                ToastUtil.show(getApplicationContext(), "收藏成功");
                                tvCollection.setImageResource(R.mipmap.xingxing_cheng);
                                collection = 1;
                            } else if (status == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(PurchaseDetailsActivity.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }

    //取消收藏
    private void delete() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("goodsId", id);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.delete, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(PurchaseDetailsActivity.this, R.style.AlertDialogStyle);

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
                            int status = header.getInt("status");
                            if (status == 0) {
                                ToastUtil.show(getApplicationContext(), "取消成功");
                                tvCollection.setImageResource(R.mipmap.xingxinghui);
                                collection = 0;
                            } else if (status == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getApplicationContext());
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
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

    //添加购物车     mContext必须 activity.this
    private void saveShopCart(final Context mContext, String shopInformationId, String num, String shopId) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("shopInformationId", shopInformationId + "");
        params.addQueryStringParameter("number", num + "");
        params.addQueryStringParameter("shopId", shopId + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.saveShopCart, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(PurchaseDetailsActivity.this, R.style.AlertDialogStyle);

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
                                ToastUtil.show(mContext, "添加成功");
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            } else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
    }
}
