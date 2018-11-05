package com.demo.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.Zxing.Activity_Zxing;
import com.demo.adapter.HomeGridAdapter;
import com.demo.adapter.HomeSeienceListViewadapter;
import com.demo.adapter.HomeThreeAdapter;
import com.demo.amusement.activity.Activity_ActivityDetailsJb;
import com.demo.amusement.activity.Activity_ActivityDetailsTj;
import com.demo.amusement.activity.Activity_ScenicActivities;
import com.demo.bean.TwoImgBean;
import com.demo.demo.myapplication.R;
import com.demo.mall.activity.ChoseDateActivity;
import com.demo.mall.activity.HotelMoreActivity;
import com.demo.mall.activity.PurchaseDetailsActivity;
import com.demo.mall.activity.RestaurantMoreActivity;
import com.demo.mall.activity.SnackActivity;
import com.demo.mall.activity.SpecialActivity;
import com.demo.mall.adapter.TcHomeGridViewAdapter;
import com.demo.mall.bean.SelectRecommendBean;
import com.demo.monitor.RealPlayActivity;
import com.demo.monitor.TestDpsdkCoreActivity;
import com.demo.my.activity.Activity_MyWallet;
import com.demo.my.activity.Activity_ShoppingCart;
import com.demo.my.activity.Activity_ToEvaluate;
import com.demo.scenicspot.activity.InvesActivity;
import com.demo.scenicspot.activity.MaprecommendationActivity;
import com.demo.scenicspot.activity.PlanActivity;
import com.demo.scenicspot.activity.ScenicSpotSearchActivity;
import com.demo.scenicspot.activity.ScienceMoreActivity;
import com.demo.scenicspot.activity.ScienceSpotActivity;
import com.demo.scenicspot.activity.SeeAroundActivity;
import com.demo.scenicspot.activity.ShidiActivity;
import com.demo.scenicspot.activity.VirtualTourActivity;
import com.demo.scenicspot.activity.WatchBridActivity;
import com.demo.scenicspot.bean.CarouselImgBean;
import com.demo.scenicspot.bean.ScenicSpotListBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.ImageCycleView;
import com.demo.view.NoScrollGridView;
import com.demo.view.NoScrollViewListView;
import com.demo.view.ObservableScrollView;
import com.demo.view.banner.Banner;
import com.demo.view.banner.BannerConfig;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.viewbadger.BadgeView;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class HomeFragment extends Fragment {


    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.gv_home)
    NoScrollGridView gvHome;
    @Bind(R.id.gridview_three)
    GridView gridviewThree;
    @Bind(R.id.content)
    HorizontalScrollView content;
    @Bind(R.id.ll_shopping)
    LinearLayout llShopping;
    @Bind(R.id.tv_adapter_my_bottomline)
    TextView tvAdapterMyBottomline;
    @Bind(R.id.iv_jiudian)
    ImageView ivJiudian;
    @Bind(R.id.iv_techan)
    ImageView ivTechan;
    @Bind(R.id.iv_fandian)
    ImageView ivFandian;
    @Bind(R.id.iv_xiaochi)
    ImageView ivXiaochi;
    @Bind(R.id.iv_homfrg_hehua)
    ImageView ivHomfrgHehua;
    @Bind(R.id.lv_homefrag_hotspot)
    NoScrollViewListView lvHomefragHotspot;
    @Bind(R.id.iv_homfrag_shidi)
    ImageView ivHomfragShidi;
    @Bind(R.id.iv_homfrag_gv_good)
    NoScrollGridView ivHomfragGvGood;
    @Bind(R.id.scrollview)
    ObservableScrollView scrollview;
    @Bind(R.id.saoyisao)
    ImageView saoyisao;
    @Bind(R.id.et_sousuo)
    TextView etSousuo;
    @Bind(R.id.iv_yunyin)
    ImageView ivYunyin;
    @Bind(R.id.ll_home_frag)
    LinearLayout llHomeFrag;
    @Bind(R.id.gouwuche)
    ImageView gouwuche;
    @Bind(R.id.ll_main_search)
    LinearLayout llMainSearch;
    @Bind(R.id.tv_jdgd)
    TextView tvJdgd;


    CarouselImgBean carouselImgBean = new CarouselImgBean();
    SelectRecommendBean selectRecommendBean = new SelectRecommendBean();
    //为您推荐gridview
    TcHomeGridViewAdapter homeGridViewAdapter;
    ScenicSpotListBean scenicSpotListBean = new ScenicSpotListBean();
    HomeThreeAdapter homeThreeAdapter;
    BadgeView badgeView;
    HomeSeienceListViewadapter homeSeienceListViewadapter;
    Intent intent = new Intent();
    //2个广告位
    TwoImgBean twoImgBean = new TwoImgBean();

    private int height;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        carouselImgHome();
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setDelayTime(3500);
        //轮播图单击事件
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                //1酒店，2饭店，3特产，4小吃，5景点详情，6景区详情
                if (carouselImgBean.getData().get(position - 1).getAdvertisement_type() == 1) {
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String time = format.format(date);
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    String time1 = format.format(c.getTime());
                    intent.setClass(getActivity(), HotelMoreActivity.class);//酒店
                    intent.putExtra("id", carouselImgBean.getData().get(position - 1).getLink_id() + "");
                    intent.putExtra("start", time);
                    intent.putExtra("end", time1);
                    intent.putExtra("total", "1");
                    startActivity(intent);
                } else if (carouselImgBean.getData().get(position - 1).getAdvertisement_type() == 2) {
                    intent.setClass(getActivity(), RestaurantMoreActivity.class);//饭店
                    intent.putExtra("id", carouselImgBean.getData().get(position - 1).getLink_id() + "");
                    intent.putExtra("date", "");
                    startActivity(intent);
                } else if (carouselImgBean.getData().get(position - 1).getAdvertisement_type() == 3) {
                    intent.setClass(getActivity(), PurchaseDetailsActivity.class);//特产
                    intent.putExtra("id", carouselImgBean.getData().get(position - 1).getLink_id() + "");
                    startActivity(intent);

                } else if (carouselImgBean.getData().get(position - 1).getAdvertisement_type() == 4) {
                    intent.setClass(getActivity(), PurchaseDetailsActivity.class);//小吃
                    intent.putExtra("id", carouselImgBean.getData().get(position - 1).getLink_id() + "");
                    startActivity(intent);
                } else if (carouselImgBean.getData().get(position - 1).getAdvertisement_type() == 5) {
                    intent.setClass(getActivity(), ScienceMoreActivity.class);//景区详情
                    intent.putExtra("id", carouselImgBean.getData().get(position - 1).getLink_id() + "");
                    startActivity(intent);
                } else if (carouselImgBean.getData().get(position - 1).getAdvertisement_type() == 6) {
                    intent.setClass(getActivity(), Activity_ActivityDetailsTj.class);//活动详情
                    intent.putExtra("id", carouselImgBean.getData().get(position - 1).getLink_id() + "");
                    startActivity(intent);
                } else if (carouselImgBean.getData().get(position - 1).getAdvertisement_type() == 7) {
                    intent.setClass(getActivity(), WatchBridActivity.class);//观鸟景区
                    intent.putExtra("id", carouselImgBean.getData().get(position - 1).getLink_id() + "");
                    startActivity(intent);
                } else if (carouselImgBean.getData().get(position - 1).getAdvertisement_type() == 8) {
                    intent.setClass(getActivity(), ShidiActivity.class);//湿地保护
                    intent.putExtra("id", carouselImgBean.getData().get(position - 1).getLink_id() + "");
                    startActivity(intent);
                } else if (carouselImgBean.getData().get(position - 1).getAdvertisement_type() == 9) {
                    intent.setClass(getActivity(), InvesActivity.class);//招商信息
                    intent.putExtra("id", carouselImgBean.getData().get(position - 1).getLink_id() + "");
                    startActivity(intent);
                }

            }
        });

        gvHome.setAdapter(new HomeGridAdapter(getContext()));
        //gridView单击事件
        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://特色景点
                        intent.setClass(getActivity(), ScienceSpotActivity.class);
                        startActivity(intent);
                        break;
                    case 1://景区活动
                        intent.setClass(getActivity(), Activity_ScenicActivities.class);
                        startActivity(intent);
                        break;
                    case 2://虚拟游
//                        intent.setClass(getActivity(), VirtualTourActivity.class);
//                        startActivity(intent);
                        //34c1fb9d5fb73db9800143babcdeb153
                        Uri uri = Uri.parse("https://720yun.com/t/793jeOhvza8");
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
//                        finish();
                        break;
                    case 3://商城特产
                        intent.setClass(getActivity(), SpecialActivity.class);
                        startActivity(intent);
                        break;
                    case 4://我的钱包
                        intent.setClass(getActivity(), Activity_MyWallet.class);
                        startActivity(intent);
                        break;
                    case 5://智能导游
                        intent.setClass(getActivity(), MaprecommendationActivity.class);
                        startActivity(intent);
                        break;
                    case 6://查看周边
                        intent.setClass(getActivity(), SeeAroundActivity.class);
                        startActivity(intent);
                        break;
                    case 7://在线购票
                        intent.setClass(getActivity(), ScienceSpotActivity.class);
                        startActivity(intent);
                        break;
                    case 8://路线规划
                        intent.setClass(getActivity(), PlanActivity.class);
                        startActivity(intent);
                        break;
                    case 9://实时动态
                        intent.setClass(getActivity(), TestDpsdkCoreActivity.class);
                        startActivity(intent);
//                        ToastUtil.show(getContext(), "实时动态");
                        break;
                }
            }
        });


        //initListeners();

        etSousuo.setFocusable(true);
        etSousuo.setFocusableInTouchMode(true);
        etSousuo.requestFocus();

        //为您推荐你商品
        ivHomfragGvGood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.setClass(getActivity(), PurchaseDetailsActivity.class);
                intent.putExtra("id", selectRecommendBean.getData().get(position).getId() + "");
                startActivity(intent);
            }
        });
        //3个推荐景区
        gridviewThree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.setClass(getActivity(), ScienceMoreActivity.class);
                intent.putExtra("id", scenicSpotListBean.getData().get(position).getId() + "");
                startActivity(intent);
            }
        });


        badgeView = new BadgeView(getContext());

        //上滑渐变
        initListeners();
        return view;
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SpUtil.getString(getActivity(), SpName.token, "").equals("")) {

        } else {
            shopCar();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    //R.id.iv_leyuan, R.id.iv_migong, R.id.iv_baohuqu,
    @OnClick({R.id.ll_shopping, R.id.iv_jiudian, R.id.iv_techan, R.id.iv_fandian, R.id.iv_xiaochi, R.id.ll_home_frag, R.id.gouwuche, R.id.saoyisao, R.id.et_sousuo, R.id.tv_jdgd})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_shopping://商城
                MainActivity activity = (MainActivity) getActivity();
                activity.jump_shopping();
                break;
            case R.id.iv_jiudian://酒店
                intent.setClass(getActivity(), ChoseDateActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.iv_techan://特产
                intent.setClass(getActivity(), SpecialActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_fandian://饭店
                intent.setClass(getActivity(), ChoseDateActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.iv_xiaochi://小吃
                intent.setClass(getActivity(), SnackActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_home_frag://搜索
                intent.setClass(getActivity(), ScenicSpotSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.et_sousuo://搜索
                intent.setClass(getActivity(), ScenicSpotSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.gouwuche://购物车
                intent.setClass(getActivity(), Activity_ShoppingCart.class);
                startActivity(intent);
                break;
            case R.id.saoyisao://扫一扫
                intent.setClass(getActivity(), Activity_Zxing.class);
                startActivity(intent);
                break;
            case R.id.tv_jdgd://更多景点
                intent.setClass(getActivity(), ScienceSpotActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void shopCar() {
        //购物车脚标
        RequestParams params1 = new RequestParams();
        params1.addHeader("Authorization", SpUtil.getString(getActivity(), SpName.token, ""));
        HttpUtils http1 = new HttpUtils();
        http1.configCurrentHttpCacheExpiry(0 * 1000);
        http1.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http1.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
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
                                badgeView.setTextSize(10f);
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
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getActivity());
                            } else {
                                ToastUtil.show(getActivity(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getActivity(), "数据解析出错");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getActivity(), "连接网络失败");
                    }
                });
    }

    private void carouselImgHome() {
        //轮播图http://a.hiphotos.baidu.com/image/h%3D360/sign=61cc75aeab014c08063b2ea33a7a025b/359b033b5bb5c9ea9d26fbe4d739b6003bf3b3e4.jpg
        RequestParams params = new RequestParams();
        //params.addQueryStringParameter("id",);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.carouselImgHome, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            carouselImgBean = new Gson().fromJson(responseInfo.result, CarouselImgBean.class);
                            int i = carouselImgBean.getHeader().getStatus();
                            if (i == 0) {
                                //homefragmentCycle.setImageResources(carouselImgBean.getData(), mAdCycleViewListener);
                                String[] image = new String[carouselImgBean.getData().size()];
                                for (int x = 0; x < carouselImgBean.getData().size(); x++) {
                                    image[x] = carouselImgBean.getData().get(x).getImg_url();
                                }
                                banner.setImages(image);
                            } else {
                                ToastUtil.show(getActivity(), carouselImgBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getActivity(), "数据解析错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getActivity(), "连接网络失败");
                    }
                });

        //为您推荐
        RequestParams params1 = new RequestParams();
        //params.addQueryStringParameter("id",);
        HttpUtils http1 = new HttpUtils();
        http1.configResponseTextCharset(HTTP.UTF_8);
        http1.configCurrentHttpCacheExpiry(0 * 1000);
        http1.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http1.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http1.send(HttpRequest.HttpMethod.GET, URL.homeRecommend, params1,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            selectRecommendBean = new Gson().fromJson(responseInfo.result, SelectRecommendBean.class);
                            int i = selectRecommendBean.getHeader().getStatus();
                            if (i == 0) {
                                if (selectRecommendBean.getData().size() > 0) {
                                    homeGridViewAdapter = new TcHomeGridViewAdapter(getContext(), selectRecommendBean.getData());
                                    ivHomfragGvGood.setAdapter(homeGridViewAdapter);
                                }
                            } else {
                                ToastUtil.show(getActivity(), selectRecommendBean.getHeader().getMsg());
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
        //热门景区
        RequestParams params2 = new RequestParams();
        //params.addQueryStringParameter("id",);
        HttpUtils http2 = new HttpUtils();
        http2.configResponseTextCharset(HTTP.UTF_8);
        http2.configCurrentHttpCacheExpiry(0 * 1000);
        http2.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http2.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http2.send(HttpRequest.HttpMethod.GET, URL.homeHotSe, params2,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            scenicSpotListBean = new Gson().fromJson(responseInfo.result, ScenicSpotListBean.class);
                            int i = scenicSpotListBean.getHeader().getStatus();
                            if (i == 0) {
                                if (scenicSpotListBean.getData() == null || scenicSpotListBean.getData().equals("")) {

                                } else {
                                    homeSeienceListViewadapter = new HomeSeienceListViewadapter(getContext(), scenicSpotListBean.getData());
                                    lvHomefragHotspot.setAdapter(homeSeienceListViewadapter);
                                }
                            } else {
                                ToastUtil.show(getActivity(), scenicSpotListBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getActivity(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                    }
                });
        //3个推荐景区
        RequestParams params3 = new RequestParams();
        //params.addQueryStringParameter("id",);
        HttpUtils http3 = new HttpUtils();
        http3.configResponseTextCharset(HTTP.UTF_8);
        http3.configCurrentHttpCacheExpiry(0 * 1000);
        http3.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http3.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http3.send(HttpRequest.HttpMethod.GET, URL.homeThree, params3,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            scenicSpotListBean = new Gson().fromJson(responseInfo.result, ScenicSpotListBean.class);
                            int i = scenicSpotListBean.getHeader().getStatus();
                            if (i == 0) {
                                if (scenicSpotListBean.getData() == null || scenicSpotListBean.getData().equals("")) {

                                } else {
                                    homeThreeAdapter = new HomeThreeAdapter(getContext(), scenicSpotListBean.getData());
                                    gridviewThree.setAdapter(homeThreeAdapter);
                                }

                            } else {
                                ToastUtil.show(getActivity(), scenicSpotListBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getActivity(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                    }
                });
        //2个广告位
        RequestParams params4 = new RequestParams();
        //params.addQueryStringParameter("id",);
        HttpUtils http4 = new HttpUtils();
        http4.configResponseTextCharset(HTTP.UTF_8);
        http4.configCurrentHttpCacheExpiry(0 * 1000);
        http4.send(HttpRequest.HttpMethod.GET, URL.twoImg, params4,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            twoImgBean = new Gson().fromJson(responseInfo.result, TwoImgBean.class);
                            if (twoImgBean.getHeader().getStatus() == 0) {
                                if (twoImgBean.getData().get(0) == null || twoImgBean.getData().get(0).equals("")) {

                                } else {
                                    ImageLoader.getInstance().displayImage(twoImgBean.getData().get(0).getImg_url(), ivHomfrgHehua);
                                }
                                if (twoImgBean.getData().get(1) == null || twoImgBean.getData().get(1).equals("")) {

                                } else {
                                    ImageLoader.getInstance().displayImage(twoImgBean.getData().get(1).getImg_url(), ivHomfragShidi);
                                }
                            } else {
                                ToastUtil.show(getActivity(), twoImgBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getContext(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                    }
                });
        lvHomefragHotspot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ScienceMoreActivity.class);
                intent.putExtra("id", scenicSpotListBean.getData().get(position).getId() + "");
                startActivity(intent);
            }
        });
        //2个广告位点击事件
        ivHomfrgHehua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1酒店，2饭店，3特产，4小吃，5景点详情，6景区详情
                if (twoImgBean.getData().get(0).getAdvertisement_type() == 1) {
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String time = format.format(date);
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    String time1 = format.format(c.getTime());
                    intent.setClass(getActivity(), HotelMoreActivity.class);//酒店
                    intent.putExtra("id", twoImgBean.getData().get(0).getLink_id() + "");
                    intent.putExtra("start", time);
                    intent.putExtra("end", time1);
                    intent.putExtra("total", "1");
                    startActivity(intent);
                } else if (twoImgBean.getData().get(0).getAdvertisement_type() == 2) {
                    intent.setClass(getActivity(), RestaurantMoreActivity.class);//饭店
                    intent.putExtra("id", twoImgBean.getData().get(0).getLink_id() + "");
                    intent.putExtra("date", "");
                    startActivity(intent);
                } else if (twoImgBean.getData().get(0).getAdvertisement_type() == 3) {
                    intent.setClass(getActivity(), PurchaseDetailsActivity.class);//小吃
                    intent.putExtra("id", twoImgBean.getData().get(0).getLink_id() + "");
                    startActivity(intent);

                } else if (twoImgBean.getData().get(0).getAdvertisement_type() == 4) {
                    intent.setClass(getActivity(), PurchaseDetailsActivity.class);//特产
                    intent.putExtra("id", twoImgBean.getData().get(0).getLink_id() + "");
                    startActivity(intent);
                } else if (twoImgBean.getData().get(0).getAdvertisement_type() == 5) {
                    intent.setClass(getActivity(), ScienceMoreActivity.class);//景区详情
                    intent.putExtra("id", twoImgBean.getData().get(0).getLink_id() + "");
                    startActivity(intent);
                } else if (twoImgBean.getData().get(0).getAdvertisement_type() == 6) {
                    intent.setClass(getActivity(), Activity_ActivityDetailsTj.class);//活动推荐
                    intent.putExtra("id", twoImgBean.getData().get(0).getLink_id() + "");
                    startActivity(intent);
                }
            }
        });
        ivHomfragShidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1酒店，2饭店，3特产，4小吃，5景点详情，6景区详情
                if (twoImgBean.getData().get(1).getAdvertisement_type() == 1) {
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String time = format.format(date);
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    String time1 = format.format(c.getTime());
                    intent.setClass(getActivity(), HotelMoreActivity.class);//酒店
                    intent.putExtra("id", twoImgBean.getData().get(1).getLink_id() + "");
                    intent.putExtra("start", time);
                    intent.putExtra("end", time1);
                    intent.putExtra("total", "1");
                    startActivity(intent);
                } else if (twoImgBean.getData().get(1).getAdvertisement_type() == 2) {
                    intent.setClass(getActivity(), RestaurantMoreActivity.class);//饭店
                    intent.putExtra("id", twoImgBean.getData().get(1).getLink_id() + "");
                    intent.putExtra("date", "");
                    startActivity(intent);
                } else if (twoImgBean.getData().get(1).getAdvertisement_type() == 3) {
                    intent.setClass(getActivity(), PurchaseDetailsActivity.class);//小吃
                    intent.putExtra("id", twoImgBean.getData().get(1).getLink_id() + "");
                    startActivity(intent);

                } else if (twoImgBean.getData().get(1).getAdvertisement_type() == 4) {
                    intent.setClass(getActivity(), PurchaseDetailsActivity.class);//特产
                    intent.putExtra("id", twoImgBean.getData().get(1).getLink_id() + "");
                    startActivity(intent);
                } else if (twoImgBean.getData().get(1).getAdvertisement_type() == 5) {
                    intent.setClass(getActivity(), ScienceMoreActivity.class);//景区详情
                    intent.putExtra("id", twoImgBean.getData().get(1).getLink_id() + "");
                    startActivity(intent);
                } else if (twoImgBean.getData().get(1).getAdvertisement_type() == 6) {
                    intent.setClass(getActivity(), Activity_ActivityDetailsTj.class);//
                    intent.putExtra("id", twoImgBean.getData().get(1).getLink_id() + "");
                    startActivity(intent);
                }
            }
        });
    }


    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {


        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            BitmapUtils bitmapUtils = new BitmapUtils(getContext());
            // 加载网络图片
            bitmapUtils.display(imageView, imageURL);
        }

        @Override
        public void onImageClick(CarouselImgBean.DataBean info, int postion, View imageView) {

        }
    };


    /**
     * 滑动监听
     */
    private void initListeners() {

        ViewTreeObserver vto = banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llMainSearch.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = banner.getHeight();

                scrollview.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                        if (y <= 0) {   //设置标题的背景颜色
                            llMainSearch.setBackgroundColor(Color.argb((int) 113, 0, 0, 0));
                        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            float scale = (float) y / height;
                            float alpha = (255 * scale);
                            if (y < 40) {
                                llMainSearch.setBackgroundColor(Color.argb((int) y, 0, 0, 0));
                            } else {
                                llMainSearch.setBackgroundColor(Color.argb((int) alpha, 75, 196, 251));
                            }


                        } else {    //滑动到banner下面设置普通颜色
                            llMainSearch.setBackgroundColor(Color.argb((int) 255, 75, 196, 251));
                        }
                    }
                });
            }
        });
    }

}
