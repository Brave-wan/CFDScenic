package com.demo.scenicspot.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.view.RouteOverLay;
import com.demo.demo.myapplication.R;
import com.demo.fragment.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：sonnerly on 2016/11/17 0017 17:23
 */
public class MapGuideActivity extends BaseActivity {

   public static double lat;
    public static double lng;
    public static double latend;
    public static double lngend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_guide_activity);
        ButterKnife.bind(this);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.mnv_map);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
//        RouteOverLay routeOverlay = new RouteOverLay(mAMapNaviView.getMap(), mAMapNavi.getNaviPath(), this);
////设置起点的图标
//        routeOverlay.setStartPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.start));
////设置终点的图标
//        routeOverlay.setEndPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.end));
        lat=getIntent().getDoubleExtra("lat",0.0);
        lng=getIntent().getDoubleExtra("lng",0.0);
        latend=getIntent().getDoubleExtra("latend",0.0);
        lngend=getIntent().getDoubleExtra("lngend",0.0);

    }
    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);

    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
