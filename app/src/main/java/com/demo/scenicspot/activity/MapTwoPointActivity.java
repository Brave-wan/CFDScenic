package com.demo.scenicspot.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.demo.anvi.AMapUtil;
import com.demo.anvi.DrivingRouteOverlay;
import com.demo.bean.WayInfoBean;
import com.demo.demo.myapplication.R;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.permission.PermissionsActivity;
import com.demo.view.permission.PermissionsChecker;
import com.google.gson.Gson;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：sonnerly on 2016/11/23 0023 16:34
 */
public class MapTwoPointActivity extends Activity implements AMap.OnMarkerClickListener, RouteSearch.OnRouteSearchListener, LocationSource, AMapLocationListener {
    @Bind(R.id.map_twopoint)
    MapView mapTwopoint;
    @Bind(R.id.btn_twopoint_navi)
    TextView btnTwopointNavi;
    private AMap aMap;
    private LatLonPoint mStartPoint = new LatLonPoint(39.942295, 116.335891);//起点，39.942295,116.335891
    private LatLonPoint mEndPoint = new LatLonPoint(39.995576, 116.481288);//终点，39.995576,116.481288
    private RouteSearch mRouteSearch;
    private final int ROUTE_TYPE_DRIVE = 2;
    private DriveRouteResult mDriveRouteResult;
    PermissionsChecker mPermissionsChecker;


    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Polygon polygon;
    boolean isflag; //是否在景点内
    SpeechSynthesizer ss;
    List<WayInfoBean.DataBean> list = new ArrayList<>();
    private UiSettings mUiSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_twopoint_activity);
        ButterKnife.bind(this);
        //播报用   APPID需要更换  已改
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=57d8b737");
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        ss = SpeechSynthesizer.createSynthesizer(getApplicationContext(), null);
        mPermissionsChecker = new PermissionsChecker(this);
        mapTwopoint.onCreate(savedInstanceState);// 此方法必须重写
        getwayInfo();
        if (aMap == null) {

            aMap = mapTwopoint.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
            mUiSettings.setScaleControlsEnabled(true);//显示比例尺控件
            aMap.setLocationSource(this);// 设置定位监听
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

            aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        }
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if(aMap.getCameraPosition().zoom>15.6f)  aMap.moveCamera(CameraUpdateFactory.zoomTo(15.6f));
            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                if(aMap.getCameraPosition().zoom>15.6f)  aMap.moveCamera(CameraUpdateFactory.zoomTo(15.6f));
            }
        });
        mEndPoint = new LatLonPoint(getIntent().getDoubleExtra("lng",39.205725),getIntent().getDoubleExtra("lat",118.361903));
        isInMap();


    }

    /**
     * 获取景点信息
     **/
    private void getwayInfo() {
        RequestParams params = new RequestParams();
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.getwayInfo, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                            WayInfoBean wayInfoBean = new Gson().fromJson(responseInfo.result, WayInfoBean.class);
                            int i = wayInfoBean.getHeader().getStatus();
                            if (i == 0) {
                                list.addAll(wayInfoBean.getData());


                            } else {
                                ToastUtil.show(getApplicationContext(), "获取失败");
                            }
                        } catch (Exception e) {
                            ToastUtil.show(MapTwoPointActivity.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(MapTwoPointActivity.this, e.getMessage());
                    }
                });
    }

    private void isInMap() {
        // 绘制一个长方形
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.205725,
                118.361903), 12));//设置中心点
//        LatLngBounds bounds = new LatLngBounds.Builder()
//                .include(new LatLng(39.213080, 118.342089))
//                .include(new LatLng(39.162971, 118.389145)).build();
//        aMap.addGroundOverlay(new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory
//                        .fromResource(R.mipmap.groundoverlay))
//                .positionFromBounds(bounds));
        Addground();
        PolygonOptions pOption = new PolygonOptions();
        pOption.add(new LatLng(39.213080, 118.342089));
        pOption.add(new LatLng(39.162971, 118.389145));
        polygon = aMap.addPolygon(pOption.strokeWidth(0));


    }


    private int[] R_map1 = {R.drawable.lpcniu_0
            , R.drawable.lpcniu_1, R.drawable.lpcniu_2, R.drawable.lpcniu_3, R.drawable.lpcniu_4
    };
    //
    private int[] R_map2 = { R.drawable.lpcniu_5, R.drawable.lpcniu_6,
            R.drawable.lpcniu_7, R.drawable.lpcniu_8, R.drawable.lpcniu_9};

    /**
     * 添加瓦片图层
     **/
    public void Addground() {


        for (int i = 0; i <R_map1.length; i++) {
//            LatLngBounds bounds = new LatLngBounds.Builder()
//                    .include(new LatLng(39.26975243, 118.41303362 - ((19 - i) * 0.00489666)))     5  0.019586626000000003
//                    .include(new LatLng(39.15506772 + (0.11468471 / 2), 118.31510049 + (i * 0.00489666))).build();  10  0.010337393333333333
            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(new LatLng(39.26975243, 118.41303362 - ((4 - i) * 0.019586626000000003)))
                    .include(new LatLng(39.15506772 + (0.11468471 / 2), 118.31510049 + (i * 0.019586626000000003))).build();
            aMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory
                            .fromResource(R_map1[i]))
                    .positionFromBounds(bounds));

        }

        for (int i = 0; i <R_map2.length; i++) {

            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(new LatLng(39.26975243 - (0.11468471 / 2), 118.41303362 - ((4 - i) * 0.019586626000000003)))
                    .include(new LatLng(39.15506772, 118.31510049 + (i * 0.019586626000000003))).build();
            aMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory
                            .fromResource(R_map2[i]))
                    .positionFromBounds(bounds));
        }


    }

    private void setfromandtoMarker() {
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        aMap.setOnMarkerClickListener(this);
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start)));
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.end)));
        btnTwopointNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapTwoPointActivity.this, MapGuideActivity.class);
                intent.putExtra("lat", mStartPoint.getLatitude());
                intent.putExtra("lng", mStartPoint.getLongitude());
                intent.putExtra("latend", mEndPoint.getLatitude());
                intent.putExtra("lngend", mEndPoint.getLongitude());
                startActivity(intent);
            }
        });
    }

//    public void searchRouteResult(int routeType, int mode) {
//        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
//                mStartPoint, mEndPoint);
//        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
//            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
//                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
//            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
//        }
//    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (mPermissionsChecker.getAndroidSDKVersion() >= 23) {
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS))
                startPermissionsActivity();
        }
        mapTwopoint.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapTwopoint.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapTwopoint.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapTwopoint.onDestroy();
    }

    // 所需权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static int REQUEST_CODE = 123456;

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED)
                finish();
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        aMap.clear();// 清理地图上的所有覆盖物
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            MapTwoPointActivity.this, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
//                    mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
//                    mRotueTimeDes.setText(des);
//                    mRouteDetailDes.setVisibility(View.VISIBLE);
//                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
//                    mRouteDetailDes.setText("打车约"+taxiCost+"元");
//                    mBottomLayout.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(mContext,
//                                    DriveRouteDetailActivity.class);
//                            intent.putExtra("drive_path", drivePath);
//                            intent.putExtra("drive_result",
//                                    mDriveRouteResult);
//                            startActivity(intent);
//                        }
//                    });

                } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
                    ToastUtil.show(this, "driveRouteResult");
                }

            } else {
                ToastUtil.show(this, "driveRouteResult");
            }
        } else {
            ToastUtil.show(this.getApplicationContext(), i + "");
        }

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    /**
     * 激活定位
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    boolean noinflag;  //不在园区内是否播报过； false是没有，true是播报过
    boolean bobaoflag;  //在园区内时，路线导航是否播报过
    boolean injingdian;  //如果再景点内，不管

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点

                double lat = amapLocation.getLatitude();//获取纬度
                double lng = amapLocation.getLongitude();//获取经度
                mStartPoint = new LatLonPoint(lat, lng);
                LatLng latLng = new LatLng(lat, lng);


//                    searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
                if (!polygon.contains(latLng)) {
                    if (!noinflag) {
                        read("您没有在景区内,请移步到景区内");
                        noinflag = true;
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.205725,
                                118.361903), 12));//设置中心点
                    }

                } else if (AMapUtils.calculateLineDistance(latLng, new LatLng(mEndPoint.getLatitude(), mEndPoint.getLongitude())) < 20) {
                    injingdian=true;

                } else if(!injingdian){

                    if (mDriveRouteResult == null){
                        setfromandtoMarker();
                        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                                mStartPoint, mEndPoint);
                        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
                                null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
                        mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询

                    }
//                    noinflag = false;

                    if (mDriveRouteResult != null && mDriveRouteResult.getPaths().get(0) != null) {

                        for (int i = 0; i < list.size(); i++) {

                            if (list.get(i).getBobaoflag() == 1) {
                                LatLng temp = new LatLng(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude()));
                                if (AMapUtils.calculateLineDistance(latLng, temp) < 20)
                                    return;
                                else {
                                    list.get(i).setBobaoflag(0);
                                    break;
                                }

                            }
                        }



                        for (int i = 0; i < mDriveRouteResult.getPaths()
                                .get(0).getSteps().size(); i++) {


                            LatLng temp = new LatLng(Double.parseDouble(list.get(i).getLatitude()),Double.parseDouble(list.get(i).getLongitude()));
                            if (AMapUtils.calculateLineDistance(latLng, temp) < 20) {

                                list.get(i).setBobaoflag(1);
//
                                read("您即将到达的是" + list.get(i).getName() + "," + list.get(i).getVisitors_describe());
//
                                return;
                            }

                        }


                    }


                }


            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }


    private void read(String text) {
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        //设置发音人（更多在线发音人，用户可参见 附录13.2
        ss.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        ss.setParameter(SpeechConstant.SPEED, "50");//设置语速
        ss.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        ss.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //3.开始合成
        ss.startSpeaking(text, mSynListener);
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
            if (error == null) {

            }
        }

        //缓冲进度回调
//percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }


        //播放进度回调
//percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };



}
