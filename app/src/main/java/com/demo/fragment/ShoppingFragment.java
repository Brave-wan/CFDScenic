package com.demo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.amusement.activity.Activity_ActivityDetailsTj;
import com.demo.demo.myapplication.R;
import com.demo.mall.activity.CategoryActivity;
import com.demo.mall.activity.ChoseDateActivity;
import com.demo.mall.activity.HotelMoreActivity;
import com.demo.mall.activity.MallSearchActivity;
import com.demo.mall.activity.PurchaseDetailsActivity;
import com.demo.mall.activity.RestaurantMoreActivity;
import com.demo.mall.activity.SnackActivity;
import com.demo.mall.activity.SpecialActivity;
import com.demo.mall.adapter.TcHomeGridViewAdapter;
import com.demo.mall.bean.FindPicBean;
import com.demo.mall.bean.SelectRecommendBean;
import com.demo.my.activity.Activity_ShoppingCart;
import com.demo.scenicspot.activity.ScenicSpotSearchActivity;
import com.demo.scenicspot.activity.ScienceMoreActivity;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.NoScrollGridView;
import com.demo.view.banner.Banner;
import com.demo.view.banner.BannerConfig;
import com.demo.view.permission.PermissionsChecker;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商城
 * Created by Administrator on 2016/7/20 0020.
 */
public class ShoppingFragment extends Fragment {

    @Bind(R.id.im_left_searchlan)
    ImageView imLeftSearchlan;
    @Bind(R.id.iv_right_cart)
    ImageView ivRightCart;
    @Bind(R.id.ll_frag_mall_jiudian)
    LinearLayout llFragMallJiudian;
    @Bind(R.id.ll_frag_mall_fandian)
    LinearLayout llFragMallFandian;
    @Bind(R.id.ll_frag_mall_xiaochi)
    LinearLayout llFragMallXiaochi;
    @Bind(R.id.ll_frag_mall_techan)
    LinearLayout llFragMallTechan;
    @Bind(R.id.ll_frag_mall_quanbu)
    LinearLayout llFragMallQuanbu;
    @Bind(R.id.iv_mallfrag_gv_good)
    NoScrollGridView ivMallfragGvGood;

    @Bind(R.id.banner)
    Banner banner;
    //为您推荐bean
    SelectRecommendBean selectRecommendBean=new SelectRecommendBean();
    //为您推荐gridview
    TcHomeGridViewAdapter homeGridViewAdapter;
    //轮播图图片信息
    FindPicBean findPicBean=new FindPicBean();
    Intent intent = new Intent();
    BadgeView badgeView ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, null);
        ButterKnife.bind(this, view);


        //轮播图
        findPic();
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setDelayTime(3500);
        //轮播图单击事件
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
//                ToastUtil.show(getContext(), position + "");
                //1酒店2饭店3特产4小吃
                if(findPicBean.getData().get(position-1).getAdvertisement_type()==1){
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String time = format.format(date);
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    String time1=format.format(c.getTime());
                    intent.setClass(getActivity(),HotelMoreActivity.class);//酒店
                    intent.putExtra("id", findPicBean.getData().get(position-1).getLink_id()+"");
                    intent.putExtra("start", time);
                    intent.putExtra("end",time1);
                    intent.putExtra("total", "1");
                    startActivity(intent);
                }else if(findPicBean.getData().get(position-1).getAdvertisement_type()==2){
                    intent.setClass(getActivity(),RestaurantMoreActivity.class);//饭店
                    intent.putExtra("id", findPicBean.getData().get(position-1).getLink_id()+"");
                    intent.putExtra("date","");
                    startActivity(intent);
                }else if(findPicBean.getData().get(position-1).getAdvertisement_type()==3){
                    intent.setClass(getActivity(),PurchaseDetailsActivity.class);//小吃
                    intent.putExtra("id", findPicBean.getData().get(position-1).getLink_id() + "");
                    startActivity(intent);
                }else if(findPicBean.getData().get(position-1).getAdvertisement_type()==4){
                    intent.setClass(getActivity(),PurchaseDetailsActivity.class);//特产
                    intent.putExtra("id", findPicBean.getData().get(position-1).getLink_id() + "");
                    startActivity(intent);
                }else if (findPicBean.getData().get(position-1).getAdvertisement_type() == 5) {
                    intent.setClass(getActivity(), ScienceMoreActivity.class);//景区详情
                    intent.putExtra("id", findPicBean.getData().get(position-1).getLink_id() + "");
                    startActivity(intent);
                } else if (findPicBean.getData().get(position-1).getAdvertisement_type() == 6) {
                    intent.setClass(getActivity(), Activity_ActivityDetailsTj.class);//活动推荐
                    intent.putExtra("id", findPicBean.getData().get(position-1).getLink_id() + "");
                    startActivity(intent);
                }

            }
        });


        badgeView = new BadgeView(getActivity(), ivRightCart);



        //为你推荐
        selectRecommend();

        //购物车角标
        banner.setFocusable(true);
        banner.setFocusableInTouchMode(true);
        banner.requestFocus();
        ivMallfragGvGood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent.setClass(getActivity(),PurchaseDetailsActivity.class);
                    intent.putExtra("id",selectRecommendBean.getData().get(position).getId()+"");
                    startActivity(intent);
            }
        });

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        if(SpUtil.getString(getContext(), SpName.token, "").equals("")){

        }else{
            shopCar();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    @OnClick({R.id.im_left_searchlan, R.id.iv_right_cart, R.id.ll_frag_mall_jiudian,
            R.id.ll_frag_mall_fandian, R.id.ll_frag_mall_xiaochi, R.id.ll_frag_mall_techan,
            R.id.ll_frag_mall_quanbu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left_searchlan://搜索框
                intent.setClass(getActivity(), MallSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_right_cart://购物车
                intent.setClass(getActivity(), Activity_ShoppingCart.class);
                startActivity(intent);
                break;
            case R.id.ll_frag_mall_jiudian://酒店
                intent.setClass(getActivity(), ChoseDateActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.ll_frag_mall_fandian://饭店
                intent.setClass(getActivity(), ChoseDateActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.ll_frag_mall_xiaochi://小吃
                intent.setClass(getActivity(), SnackActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_frag_mall_techan://特产
                intent.setClass(getActivity(), SpecialActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_frag_mall_quanbu://全部分类
                intent.setClass(getActivity(), CategoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    //轮播图
    private void findPic() {
        RequestParams params = new RequestParams();
        //params.addQueryStringParameter("id",);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findPic, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            findPicBean=new Gson().fromJson(responseInfo.result,FindPicBean.class);
                            int i=findPicBean.getHeader().getStatus();
                            if (i==0){
                                if (findPicBean.getData()!=null){
                                    String[] image = new String[findPicBean.getData().size()];
                                    for (int x = 0; x < findPicBean.getData().size(); x++) {
                                        image[x] = findPicBean.getData().get(x).getImg_url();
                                    }
                                    banner.setImages(image);
                                }

                            }else {
                                ToastUtil.show(getContext(), findPicBean.getHeader().getMsg());
                            }


                        } catch (Exception e) {
                            ToastUtil.show(getContext(), "解析数据失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), "连接网络失败");
                    }
                });
    }

    //为你推荐
    private void selectRecommend() {
        RequestParams params = new RequestParams();
        //params.addQueryStringParameter("id",);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.selectRecommend, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                             selectRecommendBean=new Gson().fromJson(responseInfo.result,SelectRecommendBean.class);
                            int i=selectRecommendBean.getHeader().getStatus();
                            if (i==0){
                                if (selectRecommendBean.getData().size()>0){
                                    homeGridViewAdapter=new TcHomeGridViewAdapter(getContext(),selectRecommendBean.getData());
                                    ivMallfragGvGood.setAdapter(homeGridViewAdapter);
                                }
                            }else {
                                ToastUtil.show(getContext(),selectRecommendBean.getHeader().getMsg());
                            }

                        } catch (Exception e) {
                            ToastUtil.show(getContext(), "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), "连接网络失败");
                    }
                });


    }
    private void shopCar() {
        //购物车脚标
        RequestParams params1 = new RequestParams();
        params1.addHeader("Authorization", SpUtil.getString(getActivity(), SpName.token, ""));
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
                                badgeView.setTextSize(10f);
                                badgeView.setBackgroundResource(R.mipmap.dayuanchengse);
                                badgeView.setTextColor(Color.WHITE);
                                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//                                badgeView.setAlpha(1f);
                                badgeView.setBadgeMargin(0, 0);
                                if(jsonObject.getString("data").equals("0")){
                                    badgeView.hide();
                                }else {
                                    badgeView.show();
                                }
                            }/* else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(getActivity());
                            }*/ else {
                                ToastUtil.show(getActivity(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getActivity(), "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getContext(), "连接网络失败");
                    }
                });
    }
}
