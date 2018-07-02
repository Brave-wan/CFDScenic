package com.demo.mall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.amusement.activity.Activity_AmusementGraphic;
import com.demo.demo.myapplication.R;
import com.demo.mall.bean.FindAllRestaurantDetailBean;
import com.demo.mall.fragment.PackageFragment;
import com.demo.mall.fragment.SingleFragment;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DateTimePickDialogUtil;
import com.demo.view.DialogProgressbar;
import com.demo.view.GradationScrollView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 饭店详情
 * 作者：sonnerly on 2016/8/8 0008 11:26
 */
public class RestaurantMoreActivity extends FragmentActivity implements GradationScrollView.ScrollViewListener {


    @Bind(R.id.iv_aty_resmore_bigimg)
    ImageView ivAtyResmoreBigimg;
    @Bind(R.id.btn_aty_resmore_tuji)
    Button btnAtyResmoreTuji;
    @Bind(R.id.tv_aty_resmore_title)
    TextView tvAtyResmoreTitle;
    @Bind(R.id.tv_aty_resmore_location)
    TextView tvAtyResmoreLocation;
    @Bind(R.id.tv_aty_resmore_call)
    ImageView tvAtyResmoreCall;
    @Bind(R.id.ll_kuandai)
    LinearLayout llKuandai;
    @Bind(R.id.ll_sheshi)
    LinearLayout llSheshi;
    @Bind(R.id.ll_keji)
    LinearLayout llKeji;
    @Bind(R.id.ll_yinpin)
    LinearLayout llYinpin;
    @Bind(R.id.rl_aty_resmore_common)
    RelativeLayout rlAtyResmoreCommon;
    @Bind(R.id.tv_aty_resmore_rili)
    public TextView tvAtyResmoreRili;
    @Bind(R.id.iv_aty_resmore_package)
    ImageView ivAtyResmorePackage;
    @Bind(R.id.tv_aty_resmore_package)
    TextView tvAtyResmorePackage;
    @Bind(R.id.ll_aty_resmore_package)
    LinearLayout llAtyResmorePackage;
    @Bind(R.id.iv_aty_resmore_single)
    ImageView ivAtyResmoreSingle;
    @Bind(R.id.tv_aty_resmore_single)
    TextView tvAtyResmoreSingle;
    @Bind(R.id.ll_aty_resmore_single)
    LinearLayout llAtyResmoreSingle;
    @Bind(R.id.frag_aty_resmore)
    FrameLayout fragAtyResmore;
    @Bind(R.id.sv_science)
    GradationScrollView svScience;
    @Bind(R.id.iv_aty_sciencemore_back)
    ImageView ivAtySciencemoreBack;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_goodTitle)
    TextView tvGoodTitle;
    @Bind(R.id.tv_bottomline)
    TextView tvBottomline;
    @Bind(R.id.ll_title)
    RelativeLayout llTitle;


    private Fragment currentFragment, packagefragment, singlefragment;
    String tel = "";
    Intent intent = new Intent();
    private String initEndDateTime = ""; // 初始化结束时间
    String now;//转换成年月日
    FindAllRestaurantDetailBean findAllRestaurantBean = new FindAllRestaurantDetailBean();
    List<String> list = new ArrayList<>();

    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_restaurantmore);
        ButterKnife.bind(this);


        ivAtyResmoreBigimg.setFocusable(true);
        ivAtyResmoreBigimg.setFocusableInTouchMode(true);
        ivAtyResmoreBigimg.requestFocus();

        if (getIntent().getStringExtra("date").equals("")) {
            tvAtyResmoreRili.setText("");
        } else {
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(getIntent().getStringExtra("date"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
            tvAtyResmoreRili.setText(now + " " + getIntent().getStringExtra("time"));
        }


        findAllRestaurantDetail();
        initTab();

        //上滑渐变
        initListeners();
        ivBack.setAlpha(0);

    }

    @OnClick({R.id.btn_aty_resmore_tuji, R.id.tv_aty_resmore_call, R.id.rl_aty_resmore_common,
            R.id.tv_aty_resmore_rili, R.id.ll_aty_resmore_package, R.id.ll_aty_resmore_single
            , R.id.iv_aty_sciencemore_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_aty_resmore_tuji:
                intent = new Intent(RestaurantMoreActivity.this, Activity_AmusementGraphic.class);
                String[] array = new String[list.size()];
                for (int index = 0; index < list.size(); index++) {
                    array[index] = list.get(index);
                }
                intent.putExtra("array", array);
                startActivity(intent);
                break;
            case R.id.iv_aty_sciencemore_back:
                finish();
                break;
            case R.id.tv_aty_resmore_call:
                Intent intents = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intents);
                break;
            case R.id.rl_aty_resmore_common:
                intent.setClass(RestaurantMoreActivity.this, RestaurantMessageActivity.class);
                intent.putExtra("tt", 2);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                break;
            case R.id.tv_aty_resmore_rili:
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        RestaurantMoreActivity.this, tvAtyResmoreRili.getText().toString());
                dateTimePicKDialog.dateTimePicKDialog(tvAtyResmoreRili);
                break;
            case R.id.ll_aty_resmore_package:
                ivAtyResmorePackage.setImageResource(R.mipmap.dinglv);
                tvAtyResmorePackage.setTextColor(Color.parseColor("#ff5203"));
                ivAtyResmoreSingle.setImageResource(R.mipmap.dinghui);
                tvAtyResmoreSingle.setTextColor(Color.parseColor("#999999"));
                if (packagefragment == null) {
                    packagefragment = new PackageFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", getIntent().getStringExtra("id"));
                    bundle.putString("Fname", tvAtyResmoreTitle.getText().toString());
                    bundle.putString("date", now);
                    bundle.putString("time", getIntent().getStringExtra("time"));
                    packagefragment.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), packagefragment);
                break;
            case R.id.ll_aty_resmore_single:
                ivAtyResmorePackage.setImageResource(R.mipmap.dinghui);
                tvAtyResmorePackage.setTextColor(Color.parseColor("#999999"));
                ivAtyResmoreSingle.setImageResource(R.mipmap.dinglv);
                tvAtyResmoreSingle.setTextColor(Color.parseColor("#ff5203"));
                if (singlefragment == null) {
                    singlefragment = new SingleFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", getIntent().getStringExtra("id"));
                    bundle.putString("name", tvAtyResmoreTitle.getText().toString());
                    bundle.putString("date", now);
                    bundle.putString("time", getIntent().getStringExtra("time"));
                    singlefragment.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), singlefragment);
                break;
        }
    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (packagefragment == null) {
            packagefragment = new PackageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", getIntent().getStringExtra("id"));
            bundle.putString("Fname", tvAtyResmoreTitle.getText().toString());
            bundle.putString("date", now);
            bundle.putString("time", getIntent().getStringExtra("time"));
            packagefragment.setArguments(bundle);
        }

        if (!packagefragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frag_aty_resmore, packagefragment).commit();

            // 记录当前Fragment
            currentFragment = packagefragment;

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
                    .add(R.id.frag_aty_resmore, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }


    //详情
    private void findAllRestaurantDetail() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.findAllRestaurantDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(RestaurantMoreActivity.this,R.style.AlertDialogStyle);
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
                            findAllRestaurantBean = new Gson().fromJson(responseInfo.result, FindAllRestaurantDetailBean.class);
                            int i = findAllRestaurantBean.getHeader().getStatus();
                            if (i == 0) {
                                if (findAllRestaurantBean.getData().getRestaurantPic().size() == 0) {

                                } else {
                                    ImageLoader.getInstance().displayImage(findAllRestaurantBean.getData().getRestaurantPic().get(0), ivAtyResmoreBigimg);
                                }
                                tel = findAllRestaurantBean.getData().getRestaurantDetail().getPhone();

                                tvAtyResmoreTitle.setText(findAllRestaurantBean.getData().getRestaurantDetail().getName());
                                tvAtyResmoreLocation.setText(findAllRestaurantBean.getData().getRestaurantDetail().getAddress());
                                list = findAllRestaurantBean.getData().getRestaurantPic();
                                if (findAllRestaurantBean.getData().getRestaurantDetail().getIs_blss() == 0) {
                                    llSheshi.setVisibility(View.GONE);
                                }
                                if (findAllRestaurantBean.getData().getRestaurantDetail().getIs_food() == 0) {
                                    llYinpin.setVisibility(View.GONE);
                                }
                                if (findAllRestaurantBean.getData().getRestaurantDetail().getIs_media() == 0) {
                                    llKeji.setVisibility(View.GONE);
                                }
                                if (findAllRestaurantBean.getData().getRestaurantDetail().getIs_wifi() == 0) {
                                    llKuandai.setVisibility(View.GONE);
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), findAllRestaurantBean.getHeader().getMsg());
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

    /**
     * 滑动监听
     */
    public void onScrollChanged(GradationScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            tvGoodTitle.setTextColor(Color.argb((int) 0, 51, 51, 51));
            llTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            tvBottomline.setBackgroundColor(Color.argb((int) 0, 205, 205, 205));
            ivBack.setAlpha((int) 0);
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            tvGoodTitle.setTextColor(Color.argb((int) alpha, 51, 51, 51));
            llTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            tvBottomline.setBackgroundColor(Color.argb((int) alpha, 205, 205, 205));
            ivBack.setAlpha((int) alpha);
        } else {    //滑动到banner下面设置普通颜色
            llTitle.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
        }
    }


    private void initListeners() {

        ViewTreeObserver vto = ivAtyResmoreBigimg.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = ivAtyResmoreBigimg.getHeight();

                svScience.setScrollViewListener(RestaurantMoreActivity.this);
            }
        });
    }
}
