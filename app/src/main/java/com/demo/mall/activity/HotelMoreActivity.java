package com.demo.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.amusement.activity.Activity_AmusementGraphic;
import com.demo.demo.myapplication.R;
import com.demo.mall.adapter.HotelOrderAdapter;
import com.demo.mall.bean.FindHotelDetailBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.DoubleDatePickerDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/8/8 0008 11:27
 */
public class HotelMoreActivity extends Activity implements GradationScrollView.ScrollViewListener {

    @Bind(R.id.iv_aty_hotelmore_bigimg)
    ImageView ivAtyHotelmoreBigimg;
    @Bind(R.id.btn_aty_hotelmore_tuji)
    Button btnAtyHotelmoreTuji;
    @Bind(R.id.tv_aty_hotelmore_title)
    TextView tvAtyHotelmoreTitle;
    @Bind(R.id.tv_aty_hotelmore_location)
    TextView tvAtyHotelmoreLocation;
    @Bind(R.id.tv_aty_hotelmore_call)
    ImageView tvAtyHotelmoreCall;
    @Bind(R.id.kuandai)
    LinearLayout kuandai;
    @Bind(R.id.yushi)
    LinearLayout yushi;
    @Bind(R.id.bianlisheshi)
    LinearLayout bianlisheshi;
    @Bind(R.id.meiti)
    LinearLayout meiti;
    @Bind(R.id.shipin)
    LinearLayout shipin;
    @Bind(R.id.rl_aty_hotelmore_common)
    RelativeLayout rlAtyHotelmoreCommon;
    @Bind(R.id.tv_aty_hotelmore_rili)
    TextView tvAtyHotelmoreRili;
    @Bind(R.id.tv_aty_hotelmore_total)
    TextView tvAtyHotelmoreTotal;
    @Bind(R.id.rl_aty_hotelmore_date)
    RelativeLayout rlAtyHotelmoreDate;
    @Bind(R.id.tv_aty_hotelmore_ordernum)
    TextView tvAtyHotelmoreOrdernum;
    @Bind(R.id.slv_aty_hotelmore)
    NoScrollViewListView slvAtyHotelmore;
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


    HotelOrderAdapter hotelOrderAdapter;
    Intent intent = new Intent();
    String begin;
    String over;
    String call;

    String dates;//当天日期
    String id;//店铺ID
    String start;
    String end;
    String total;
    long m_intervalday = 1;//初始化时间间隔的值为0
    FindHotelDetailBean findHotelDetailBean;
    List<FindHotelDetailBean.DataBean.ShopGoodsBean> bean;
    List<String> list = new ArrayList<>();

    private int height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_hotelmore);
        ButterKnife.bind(this);

        init();

        //详情接口
        findHotelDetail();

        //上滑渐变
        initListeners();
        ivBack.setAlpha(0);

        ivAtyHotelmoreBigimg.setFocusable(true);
        ivAtyHotelmoreBigimg.setFocusableInTouchMode(true);
        ivAtyHotelmoreBigimg.requestFocus();
    }

    private void init() {

        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        total = getIntent().getStringExtra("total");
        tvAtyHotelmoreRili.setText("入住：" + start + "    离店:" + end);
        tvAtyHotelmoreTotal.setText("共" + total + "晚");

        id = getIntent().getStringExtra("id");

        slvAtyHotelmore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bean.get(position).getIsReservation() == 1) {
                    return;
                }
                if (m_intervalday < 0 || m_intervalday == 0) {
                    ToastUtil.show(getApplication(), "选择的日期有误");
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), HotelSureOrderActivity.class);
                intent.putExtra("start", start);
                intent.putExtra("end", end);
                intent.putExtra("total", total);
                intent.putExtra("yuanjia", bean.get(position).getPrice() + "");
                intent.putExtra("xianjia", bean.get(position).getNew_price() + "");
                intent.putExtra("jiuName", findHotelDetailBean.getData().getHotelDetail().getName());
                intent.putExtra("fangName", bean.get(position).getGoods_name());
                intent.putExtra("image", bean.get(position).getDescribe_img());
                intent.putExtra("goodsId", bean.get(position).getId() + "");
                intent.putExtra("shopInformationId", bean.get(position).getShop_information_id() + "");
                intent.putExtra("fangmiaoshu", bean.get(position).getGoods_describe());
                intent.putExtra("stock", bean.get(position).getStock());
                startActivity(intent);
            }
        });

    }

    @OnClick({R.id.btn_aty_hotelmore_tuji, R.id.tv_aty_hotelmore_call,
            R.id.rl_aty_hotelmore_common, R.id.rl_aty_hotelmore_date, R.id.iv_aty_sciencemore_back, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_aty_sciencemore_back:
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_aty_hotelmore_tuji://图集
                intent = new Intent(HotelMoreActivity.this, Activity_AmusementGraphic.class);
                String[] array = new String[list.size()];
                for (int index = 0; index < list.size(); index++) {
                    array[index] = list.get(index);
                }
                intent.putExtra("array", array);
                startActivity(intent);
                break;
            case R.id.tv_aty_hotelmore_call:    //电话跳转
                if(call.equals("")){
                    ToastUtil.show(HotelMoreActivity.this, "暂无电话");
                }else{
                    Intent intents = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + call));
                    intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intents);
                }

                break;
            case R.id.rl_aty_hotelmore_common:
                intent.setClass(HotelMoreActivity.this, RestaurantMessageActivity.class);
                intent.putExtra("tt", 1);
                intent.putExtra("id", id);
                intent.putExtra("startDate", start);
                intent.putExtra("endDate", end);
                startActivity(intent);
                break;
            case R.id.rl_aty_hotelmore_date:
                Calendar start_calendar = null;
                Calendar end_calendar = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date start_date = sdf.parse(start);
                    start_calendar = Calendar.getInstance();
                    start_calendar.setTime(start_date);

                    Date end_date=sdf.parse(end);
                    end_calendar = Calendar.getInstance();
                    end_calendar.setTime(end_date);
                } catch (Exception e) {
                }

                int start_year = start_calendar.get(Calendar.YEAR);
                int start_month = start_calendar.get(Calendar.MONTH);
                int start_day = start_calendar.get(Calendar.DAY_OF_MONTH);

                int end_year = end_calendar.get(Calendar.YEAR);
                int end_month = end_calendar.get(Calendar.MONTH);
                int end_day = end_calendar.get(Calendar.DAY_OF_MONTH);


                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                DoubleDatePickerDialog doubleDatePickerDialog = new DoubleDatePickerDialog(HotelMoreActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear,
                                          int endMonthOfYear, int endDayOfMonth) {
                        String textString = String.format("入住：%d-%d-%d  离店：%d-%d-%d", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        tvAtyHotelmoreRili.setText(textString);
                        begin = String.format(startYear + "-" + (startMonthOfYear + 1) + "-" + startDayOfMonth);
                        over = String.format(endYear + "-" + (endMonthOfYear + 1) + "-" + endDayOfMonth);
                        start = begin;
                        end = over;

                        //使用的时间格式为yyyy-MM-dd
                        SimpleDateFormat m_simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            //当天日期
                            dates = m_simpledateformat.format(new Date());
                            Date date = m_simpledateformat.parse(dates);
                            //创建两个Date对象
                            Date date1 = m_simpledateformat.parse(begin);
                            Date date2 = m_simpledateformat.parse(over);
                            m_intervalday = date2.getTime() - date1.getTime();//计算所得为微秒数
                            m_intervalday = m_intervalday / 1000 / 60 / 60 / 24;//计算所得的天数

                            total = m_intervalday + "";
                            tvAtyHotelmoreTotal.setText("共" + m_intervalday + "晚");
                            //days=(int)m_intervalday;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, start_year, start_month, start_day, true);
                doubleDatePickerDialog.setStartMinDate(true, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
                doubleDatePickerDialog.setEndMinDate(true, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE) + 1);

                doubleDatePickerDialog.updateEndDate(end_year, end_month, end_day);
                doubleDatePickerDialog.show();
                break;
        }
    }

    //详情
    private void findHotelDetail() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("startDate", start);
        params.addQueryStringParameter("endDate", end);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.findHotelDetail, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(HotelMoreActivity.this,R.style.AlertDialogStyle);
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
                            findHotelDetailBean = new Gson().fromJson(responseInfo.result, FindHotelDetailBean.class);
                            int i = findHotelDetailBean.getHeader().getStatus();
                            if (i == 0) {
                                if (findHotelDetailBean.getData() == null || findHotelDetailBean.getData().equals("")) {

                                } else {
                                    if (findHotelDetailBean.getData().getHotelPic().size() == 0) {

                                    } else {
                                        ImageLoader.getInstance().displayImage(findHotelDetailBean.getData().getHotelPic().get(0), ivAtyHotelmoreBigimg);
                                    }
                                    tvAtyHotelmoreTitle.setText(findHotelDetailBean.getData().getHotelDetail().getName());
                                    tvAtyHotelmoreLocation.setText(findHotelDetailBean.getData().getHotelDetail().getAddress());
                                    call = findHotelDetailBean.getData().getHotelDetail().getPhone() + "";
                                    list = findHotelDetailBean.getData().getHotelPic();
                                    if (findHotelDetailBean.getData().getHotelDetail().getIs_blss() == 0) {
                                        bianlisheshi.setVisibility(View.GONE);
                                    }
                                    if (findHotelDetailBean.getData().getHotelDetail().getIs_food() == 0) {
                                        shipin.setVisibility(View.GONE);
                                    }
                                    if (findHotelDetailBean.getData().getHotelDetail().getIs_media() == 0) {
                                        meiti.setVisibility(View.GONE);
                                    }
                                    if (findHotelDetailBean.getData().getHotelDetail().getIs_wifi() == 0) {
                                        kuandai.setVisibility(View.GONE);
                                    }
                                    if (findHotelDetailBean.getData().getHotelDetail().getIs_yushi() == 0) {
                                        yushi.setVisibility(View.GONE);
                                    }

                                    bean = findHotelDetailBean.getData().getShopGoods();
                                    tvAtyHotelmoreOrdernum.setText("预定（" + bean.size() + "）");

                                    hotelOrderAdapter = new HotelOrderAdapter(HotelMoreActivity.this, bean);
                                    slvAtyHotelmore.setAdapter(hotelOrderAdapter);
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), findHotelDetailBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), e.getMessage());
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

        ViewTreeObserver vto = ivAtyHotelmoreBigimg.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = ivAtyHotelmoreBigimg.getHeight();

                svScience.setScrollViewListener(HotelMoreActivity.this);
            }
        });
    }
}
