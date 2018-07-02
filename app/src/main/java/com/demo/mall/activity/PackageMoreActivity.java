package com.demo.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.amusement.activity.Activity_AmusementGraphic;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.FindPackageByPackageIdBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.GradationScrollView;
import com.demo.view.NoScrollViewListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/8/10 0010 09:55
 */
public class PackageMoreActivity extends Activity implements GradationScrollView.ScrollViewListener{

    @Bind(R.id.iv_aty_packagemore_img)
    ImageView ivAtyPackagemoreImg;
    @Bind(R.id.btn_aty_packagemore_tuji)
    Button btnAtyPackagemoreTuji;
    @Bind(R.id.tv_aty_hotelmore_title)
    TextView tvAtyHotelmoreTitle;
    @Bind(R.id.tv_aty_hotelmore_oedernow)
    TextView tvAtyHotelmoreOedernow;
    @Bind(R.id.tv_aty_packagemore_location)
    TextView tvAtyPackagemoreLocation;
    @Bind(R.id.tv_aty_packagemore_call)
    ImageView tvAtyPackagemoreCall;
    @Bind(R.id.ll_add)
    LinearLayout llAdd;
    @Bind(R.id.slv_aty_packagemore)
    NoScrollViewListView slvAtyPackagemore;
    @Bind(R.id.wv_aty_packagemore_mustknow)
    WebView wvAtyPackagemoreMustknow;
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
    @Bind(R.id.svScience)
    GradationScrollView svScience;


    Intent intent=new Intent();
    String tel = "";
    FindPackageByPackageIdBean findPackageByPackageIdBean = new FindPackageByPackageIdBean();
    List<String> list = new ArrayList<>();

    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_packagemore);
        ButterKnife.bind(this);

        findPackageByPackageId();
        //上滑渐变
        initListeners();
        ivBack.setAlpha(0);
    }

    @OnClick({R.id.tv_aty_hotelmore_oedernow, R.id.tv_aty_packagemore_call, R.id.iv_aty_sciencemore_back, R.id.btn_aty_packagemore_tuji, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_aty_hotelmore_oedernow://立即预定
                intent.setClass(PackageMoreActivity.this, PackageSureOrderActivity.class);
//                intent.putExtra("image", findPackageByPackageIdBean.getData().getPicList().get(0));
                intent.putExtra("Sname", findPackageByPackageIdBean.getData().getName());
                intent.putExtra("Fname", getIntent().getStringExtra("Fname"));
                intent.putExtra("date", getIntent().getStringExtra("date"));
                intent.putExtra("time", getIntent().getStringExtra("time"));
                intent.putExtra("price", getIntent().getStringExtra("price"));
                intent.putExtra("goodsId", findPackageByPackageIdBean.getData().getPackageId() + "");
                intent.putExtra("shopInformationId", getIntent().getStringExtra("shopInformationId") + "");
                if (findPackageByPackageIdBean.getData().getPicList()!=null&&findPackageByPackageIdBean.getData().getPicList().size()>0){
                    intent.putExtra("imageView",  findPackageByPackageIdBean.getData().getPicList().get(0)+ "");
                }
                startActivity(intent);
                break;
            case R.id.tv_aty_packagemore_call://打电话
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.iv_aty_sciencemore_back:
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_aty_packagemore_tuji:
                intent = new Intent(PackageMoreActivity.this, Activity_AmusementGraphic.class);
                String[] array = new String[list.size()];
                for (int index = 0; index < list.size(); index++) {
                    array[index] = list.get(index);
                }
                intent.putExtra("array", array);
                startActivity(intent);
                break;
        }
    }

    private void findPackageByPackageId() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", getIntent().getStringExtra("id"));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findPackageByPackageId, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(PackageMoreActivity.this,R.style.AlertDialogStyle);
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
                            findPackageByPackageIdBean = new Gson().fromJson(responseInfo.result, FindPackageByPackageIdBean.class);
                            int i = findPackageByPackageIdBean.getHeader().getStatus();
                            if (i == 0) {
                                if (findPackageByPackageIdBean.getData().getPicList().size() == 0) {

                                } else {
                                    ImageLoader.getInstance().displayImage(findPackageByPackageIdBean.getData().getPicList().get(0), ivAtyPackagemoreImg);
                                }
                                tvAtyHotelmoreTitle.setText(findPackageByPackageIdBean.getData().getName());
                                tvAtyPackagemoreLocation.setText(findPackageByPackageIdBean.getData().getAddress());
                                tel = findPackageByPackageIdBean.getData().getPhone();
                                list = findPackageByPackageIdBean.getData().getPicList();
                                //启用支持javascript
                                WebSettings settings = wvAtyPackagemoreMustknow.getSettings();
                                settings.setJavaScriptEnabled(true);
                                wvAtyPackagemoreMustknow.loadUrl(findPackageByPackageIdBean.getData().getHtml_url());
                                wvAtyPackagemoreMustknow.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });
                                for (int index = 0; index < findPackageByPackageIdBean.getData().getGoods().size(); index++) {
                                    View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_package_content, null);
                                    TextView name = (TextView) view.findViewById(R.id.name);
                                    TextView num = (TextView) view.findViewById(R.id.num);
                                    TextView price = (TextView) view.findViewById(R.id.price);
                                    name.setText(findPackageByPackageIdBean.getData().getGoods().get(index).getGoods_name() + ",");
                                    num.setText("1份" + ",");
                                    price.setText(findPackageByPackageIdBean.getData().getGoods().get(index).getNew_price() + "元");
                                    llAdd.addView(view);
                                }
                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(PackageMoreActivity.this);
                            } else {
                                ToastUtil.show(getApplicationContext(), findPackageByPackageIdBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
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
            llTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            tvBottomline.setBackgroundColor(Color.argb((int) 0, 205, 205, 205));
            tvGoodTitle.setTextColor(Color.argb((int) 0, 51, 51, 51));
            ivBack.setAlpha(0);
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

        ViewTreeObserver vto = ivAtyPackagemoreImg.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = ivAtyPackagemoreImg.getHeight();

                svScience.setScrollViewListener(PackageMoreActivity.this);
            }
        });
    }
}
