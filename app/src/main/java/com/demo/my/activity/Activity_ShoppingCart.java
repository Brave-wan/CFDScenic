package com.demo.my.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.adapter.ShoppingCartAdapter;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.bean.GetShopCartByUserIdBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyRadioButton;
import com.demo.view.MyTopBar;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购物车
 * Created by Administrator on 2016/8/8 0008.
 */
public class Activity_ShoppingCart extends Activity {


    @Bind(R.id.view_all)
    public MyRadioButton viewAll;
    @Bind(R.id.tv_Settlement)
    public TextView tvSettlement;
    @Bind(R.id.view_topbar)
    MyTopBar viewTopbar;
    @Bind(R.id.ll_Settlement)
    LinearLayout llSettlement;
    @Bind(R.id.tv_Favorites)
    TextView tvFavorites;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    @Bind(R.id.ll_delete)
    LinearLayout llDelete;
    @Bind(R.id.ll_null)
    LinearLayout llNull;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.lv_ShoppingCart)
    ExpandableListView lvShoppingCart;
    @Bind(R.id.tv_totalMoney)
    public TextView tvTotalMoney;


    GetShopCartByUserIdBean getShopCartByUserIdBean;
    ShoppingCartAdapter adapter;
    List<GetShopCartByUserIdBean.DataBean> list;
    boolean state = false;//是否处于编辑状态。true是，false没有

    public static boolean bRefresh = false;//是否刷新数据


    @Override//http://blog.csdn.net/yaya_soft/article/details/24396357
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        viewTopbar.setRightTextColor("#FF5400");

        //设置topbar右侧文字点击事件
        viewTopbar.setRightTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state) {
                    viewTopbar.setRightText("编辑");
                    viewTopbar.setRightTextColor("#FF5400");
                    llDelete.setVisibility(View.GONE);
                    llSettlement.setVisibility(View.VISIBLE);
                    adapter.setState(false);
                    adapter.groupIndex = -1;
                    state = false;
                    adapter.initList();
                    adapter.notifyDataSetChanged();
                    viewAll.setStatus(false);
                    viewAll.setTextView("全选");
                } else {
                    if (getShopCartByUserIdBean == null) {
                        return;
                    }
                    if (getShopCartByUserIdBean.getData() == null) {
                        return;
                    }
                    if (getShopCartByUserIdBean.getData().size() == 0) {
                        return;
                    }
                    viewAll.setStatus(false);
                    viewAll.setTextView("全选");
                    viewTopbar.setRightText("完成");
                    viewTopbar.setRightTextColor("#4bc4fb");
                    llDelete.setVisibility(View.VISIBLE);
                    llSettlement.setVisibility(View.GONE);
                    adapter.setState(true);
                    adapter.groupIndex = -1;
                    state = true;
                    adapter.initList();
                    adapter.notifyDataSetChanged();
                }

            }
        });
        lvShoppingCart.setGroupIndicator(null);

        bRefresh = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bRefresh) {
            getShopCartByUserId();
            bRefresh = false;
        }
    }

    @OnClick({R.id.view_all, R.id.tv_Settlement, R.id.tv_Favorites, R.id.tv_delete})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.view_all:
                if (viewAll.getStatus()) {
                    adapter.setAllImageView(false);
                    viewAll.setStatus(false);
                    viewAll.setTextView("全选");
                   /* tvTotalMoney.setText("￥0.0");
                    tvSettlement.setText("结算（0）");*/
                } else {
                    adapter.setAllImageView(true);
                    viewAll.setStatus(true);
                    viewAll.setTextView("反选");
                }
                break;
            case R.id.tv_Settlement:
                if (adapter.totalPiece == 0) {
                    ToastUtil.show(getApplicationContext(), "请选择商品");
                    return;
                }
                intent = new Intent(getApplicationContext(), Activity_ConfirmOrder.class);
                intent.putExtra("totalMoney", adapter.totalMoney + "");
                startActivity(intent);
                break;
            case R.id.tv_Favorites:
                if (adapter.totalPiece == 0) {
                    ToastUtil.show(getApplicationContext(), "请选择商品");
                    return;
                }
                adapter.favorites();
                break;
            case R.id.tv_delete:
                if (adapter.totalPiece == 0) {
                    ToastUtil.show(getApplicationContext(), "请选择商品");
                    return;
                }
                adapter.deleteItem();
                break;
        }
    }


    //购物车列表
    public void getShopCartByUserId() {
        list = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        //params.addQueryStringParameter("id",);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getShopCartByUserId, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(Activity_ShoppingCart.this, R.style.AlertDialogStyle);

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
                            getShopCartByUserIdBean = new Gson().fromJson(responseInfo.result, GetShopCartByUserIdBean.class);
                            int i = getShopCartByUserIdBean.getHeader().getStatus();
                            if (i == 0) {
                                if (getShopCartByUserIdBean.getData() == null) {
                                    llNull.setVisibility(View.VISIBLE);
                                    llBottom.setVisibility(View.GONE);
                                    lvShoppingCart.setVisibility(View.GONE);
                                    return;
                                } else {
                                    if (getShopCartByUserIdBean.getData().size() == 0) {
                                        llNull.setVisibility(View.VISIBLE);
                                        llBottom.setVisibility(View.GONE);
                                        lvShoppingCart.setVisibility(View.GONE);
                                        return;
                                    }
                                    llNull.setVisibility(View.GONE);
                                    llBottom.setVisibility(View.VISIBLE);
                                    lvShoppingCart.setVisibility(View.VISIBLE);
                                    list = getShopCartByUserIdBean.getData();

                                    //初始化ArrayList
                                    adapter = new ShoppingCartAdapter(Activity_ShoppingCart.this, list);
                                    lvShoppingCart.setAdapter(adapter);
                                    //全部展开
                                    for (int zk = 0; zk < adapter.getGroupCount(); zk++) {
                                        lvShoppingCart.expandGroup(zk);
                                    }
                                    //不能收缩
                                    lvShoppingCart.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                                        @Override
                                        public boolean onGroupClick(ExpandableListView parent, View v,
                                                                    int groupPosition, long id) {
                                            return true;
                                        }
                                    });
                                }
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ShoppingCart.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), getShopCartByUserIdBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

    }


}
