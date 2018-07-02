package com.demo.mall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.fragment.PriceFragment;
import com.demo.mall.fragment.SaleFragment;
import com.demo.my.activity.Activity_ShoppingCart;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
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
 * 作者：sonnerly on 2016/8/6 0006 11:36
 */
public class ShopActivity extends FragmentActivity {
    @Bind(R.id.iv_aty_shop_back)
    ImageView ivAtyShopBack;
    @Bind(R.id.iv_aty_shop_yunyin)
    ImageView ivAtyShopYunyin;
    @Bind(R.id.iv_aty_shop_gouwuche)
    ImageView ivAtyShopGouwuche;
    @Bind(R.id.ll_main_search)
    LinearLayout llMainSearch;
    @Bind(R.id.iv_aty_shop_beijing)
    ImageView ivAtyShopBeijing;
    @Bind(R.id.iv_aty_shop_icon)
    ImageView ivAtyShopIcon;
    @Bind(R.id.tv_aty_shop_title)
    TextView tvAtyShopTitle;
    @Bind(R.id.iv_aty_shop_rightback)
    RelativeLayout ivAtyShopRightback;
    @Bind(R.id.tv_aty_shop_sale)
    TextView tvAtyShopSale;
    @Bind(R.id.ll_aty_shop_sale)
    LinearLayout llAtyShopSale;
    @Bind(R.id.tv_aty_shop_price)
    TextView tvAtyShopPrice;
    @Bind(R.id.iv_aty_shop_price)
    ImageView ivAtyShopPrice;
    @Bind(R.id.ll_aty_shop_price)
    LinearLayout llAtyShopPrice;
    @Bind(R.id.frag_shop)
    FrameLayout fragShop;
    @Bind(R.id.ll_shop_search)
    LinearLayout llShopSearch;//店铺搜索
    Intent intent = new Intent();
    @Bind(R.id.et_aty_shop_search)
    TextView etAtyShopSearch;
    @Bind(R.id.sv_shop)
    ScrollView svShop;
    private Fragment currentFragment, salefragment, pricefragment;
    boolean aBoolean = true;//判断价格是显示从高到低还是从低到高   true从低到高
    int one = 1;
    public static String id = "";
    public static String type;
    public static String name = "";
    public static String imageUrl = "";
    BadgeView badgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_shop);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        imageUrl = getIntent().getStringExtra("ImageUrl");
        tvAtyShopTitle.setText(name);
        badgeView = new BadgeView(ShopActivity.this, ivAtyShopGouwuche);
        BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
        bitmapUtils.display(ivAtyShopIcon, imageUrl);
        initTab();

        svShop.smoothScrollTo(0, 0);
        //购物车脚标
//        shopCart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SpUtil.getString(ShopActivity.this, SpName.token, "").equals("")) {
        } else {
            shopCart();
        }

    }

    @OnClick({R.id.iv_aty_shop_back, R.id.iv_aty_shop_yunyin, R.id.iv_aty_shop_gouwuche,
            R.id.iv_aty_shop_rightback, R.id.ll_aty_shop_sale, R.id.ll_aty_shop_price, R.id.ll_shop_search, R.id.et_aty_shop_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_aty_shop_back:
                finish();
                break;
            case R.id.iv_aty_shop_yunyin://转搜索
                intent.setClass(ShopActivity.this, ShopSearchActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.et_aty_shop_search://转搜索
                intent.setClass(ShopActivity.this, ShopSearchActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.iv_aty_shop_gouwuche://购物车
                intent = new Intent(getApplicationContext(), Activity_ShoppingCart.class);
                startActivity(intent);
                break;
            case R.id.iv_aty_shop_rightback://店铺信息
                intent.setClass(ShopActivity.this, ShopMessageActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.ll_aty_shop_sale://销量
                one = 1;
                tvAtyShopSale.setTextColor(Color.parseColor("#ff5203"));
                tvAtyShopPrice.setTextColor(Color.parseColor("#333333"));
                if (salefragment == null) {
                    salefragment = new SaleFragment();
                }

                addOrShowFragment(getSupportFragmentManager().beginTransaction(), salefragment);
                break;
            case R.id.ll_aty_shop_price://价格
                one = 2;
                tvAtyShopSale.setTextColor(Color.parseColor("#333333"));
                tvAtyShopPrice.setTextColor(Color.parseColor("#ff5203"));
                if (pricefragment == null) {
                    pricefragment = new PriceFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), pricefragment);

                if (aBoolean) {
                    aBoolean = false;
                    type = "0";
                    ivAtyShopPrice.setImageResource(R.mipmap.xiahui);
                    PriceFragment fragment = (PriceFragment) currentFragment;
                    fragment.findPrice();
                } else {
                    aBoolean = true;
                    type = "1";
                    ivAtyShopPrice.setImageResource(R.mipmap.shanghui);
                    PriceFragment fragment = (PriceFragment) currentFragment;
                    fragment.findPrice();

                }
                break;
            case R.id.ll_shop_search:
                intent.setClass(ShopActivity.this, ShopSearchActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
        }
    }

    private void shopCart() {//购物车下标
        RequestParams params1 = new RequestParams();
        params1.addHeader("Authorization", SpUtil.getString(ShopActivity.this, SpName.token, ""));
        HttpUtils http1 = new HttpUtils();
        http1.configCurrentHttpCacheExpiry(0 * 1000);
        http1.send(HttpRequest.HttpMethod.GET, URL.getNumber, params1,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {

                                badgeView.setText(jsonObject.getString("data"));
                                badgeView.setTextSize(9f);
                                badgeView.setBackgroundResource(R.mipmap.dayuanchengse);
                                badgeView.setTextColor(Color.WHITE);
                                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//                                badgeView.setAlpha(1f);
                                badgeView.setBadgeMargin(0, 0);
                                if (jsonObject.getString("data").equals("0")) {
                                    badgeView.hide();
                                } else {
                                    badgeView.show();
                                }
                            } /*else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(ShopActivity.this);
                            } else {
                                ToastUtil.show(ShopActivity.this, header.getString("msg"));
                            }*/
                        } catch (Exception e) {
                            ToastUtil.show(ShopActivity.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(ShopActivity.this, e.getMessage());
                    }
                });
    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (salefragment == null) {
            salefragment = new SaleFragment();
//            Bundle bundle=new Bundle( );
//            bundle.putString("id",id);
//            bundle.putString("businessName",name);
//            bundle.putString("business",imageUrl);
//            salefragment.setArguments(bundle);
        }

        if (!salefragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frag_shop, salefragment).commit();

            // 记录当前Fragment
            currentFragment = salefragment;

        }

    }

    /**
     * 添加或�?显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.frag_shop, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }
}
