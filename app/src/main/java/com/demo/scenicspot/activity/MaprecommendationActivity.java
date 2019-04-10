package com.demo.scenicspot.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
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
import com.demo.fragment.MyApplication;
import com.demo.monitor.GroupListGetTask;
import com.demo.monitor.GroupListManager;
import com.demo.monitor.MonitorBean;
import com.demo.monitor.RealPlayActivity;
import com.demo.monitor.TreeNode;
import com.demo.scenicspot.bean.AnviRecommendBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.permission.PermissionsActivity;
import com.demo.view.permission.PermissionsChecker;
import com.dh.DpsdkCore.IDpsdkCore;
import com.dh.DpsdkCore.Login_Info_t;
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
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/11/18 0018 14:34
 */
public class MaprecommendationActivity extends Activity implements LocationSource, AMapLocationListener, AMap.InfoWindowAdapter, AMap.OnMarkerClickListener, AMap.OnMapClickListener, RouteSearch.OnRouteSearchListener {
    @Bind(R.id.map_recommend)
    MapView mapRecommend;
    @Bind(R.id.ll_recommend)
    LinearLayout llRecommend;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.ll_navi)
    LinearLayout llNavi;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    ArrayList<Marker> saveMarkerList = new ArrayList<Marker>();
    double lat = 0;//纬度
    double lng = 0;//经度
    Intent intent = new Intent();
    private Polygon polygon;
    private Marker marker;
    private UiSettings mUiSettings;
    PermissionsChecker mPermissionsChecker;
    MonitorBean monitorBean = null;
    SpeechSynthesizer ss;
    LatLng latLng;
    String ip = "192.168.1.195";
    String port = "9000";
    String user = "admin";
    String password = "dahua2006";
    private MyApplication mAPP = MyApplication.get();
    private GroupListManager mGroupListManager = null;
    // 获取的树信息
    private TreeNode root = null;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private LatLonPoint mStartPoint;//起点，39.942295,116.335891
    private LatLonPoint mEndPoint = null;//终点，39.995576,116.481288
    Marker mm;
    int luxianflag; //1,推荐路线，2导航路线

    //景点信息

    List<WayInfoBean.DataBean> list = new ArrayList<>();

    //List<AnviRecommendBean.DataBean.RowsBean.ListDataBean> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_recommend_activity);
        ButterKnife.bind(this);
        mAPP.initApp();
        //播报用   APPID需要更换  已改
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=57d8b737");
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        ss = SpeechSynthesizer.createSynthesizer(getApplicationContext(), null);
        mGroupListManager = GroupListManager.getInstance();
        getip();
        mPermissionsChecker = new PermissionsChecker(this);
        mapRecommend.onCreate(savedInstanceState);// 此方法必须重写
        getwayInfo();
        if (aMap == null) {
            aMap = mapRecommend.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
            mUiSettings.setScaleControlsEnabled(true);//显示比例尺控件
            setUpMap();
            isInMap();

            initGet();

        }

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (aMap.getCameraPosition().zoom > 15.6f)
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(15.6f));
            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                if (aMap.getCameraPosition().zoom > 15.6f)
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(15.6f));
            }
        });
    }

    //    getwayInfo
    private void initGet() {
        RequestParams params = new RequestParams();
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.realTime, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                            monitorBean = new Gson().fromJson(responseInfo.result, MonitorBean.class);
                            int i = monitorBean.getHeader().getStatus();
                            if (i == 0) {
                                if (monitorBean.getData() != null) {
                                    for (int index = 0; index < monitorBean.getData().getRows().size(); index++) {
                                        if (!monitorBean.getData().getRows().equals("")) {
                                            MarkerOptions markerOptions = new MarkerOptions();
                                            Double accuracy = Double.parseDouble(monitorBean.getData().getRows().get(index).getX_point());
                                            Double latitude = Double.parseDouble(monitorBean.getData().getRows().get(index).getY_point());
                                            markerOptions.position(new LatLng(latitude, accuracy));
                                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.datouzhen));
//                                            markerOptions.title(monitorBean.getData().get(index).getName());
                                            Marker marker = aMap.addMarker(markerOptions);
                                            marker.setObject(monitorBean.getData().getRows());
                                            saveMarkerList.add(marker);

                                        }
                                    }

                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), monitorBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(MaprecommendationActivity.this, "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(MaprecommendationActivity.this, "网络连接失败");
                    }
                });
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
                            ToastUtil.show(MaprecommendationActivity.this, "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(MaprecommendationActivity.this, "网络连接失败");
                    }
                });

    }

    /**
     * 获取登录ip地址
     **/
    public void getip() {
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.getshexiangtouid, null,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject head = jsonObject.getJSONObject("header");
                            int i = head.getInt("status");
                            if (i == 0) {
                                ip = jsonObject.getJSONObject("data").getString("ip");
                                port = jsonObject.getJSONObject("data").getString("port_number");
                                user = jsonObject.getJSONObject("data").getString("user");
                                password = jsonObject.getJSONObject("data").getString("password");
                                new LoginTask().execute();
                            } else {
//                                ToastUtil.show(MaprecommendationActivity.this, head.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(MaprecommendationActivity.this, "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(MaprecommendationActivity.this, "连接网络失败");
                    }
                });

    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    DrivingRouteOverlay drivingRouteOverlay;

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    drivingRouteOverlay = new DrivingRouteOverlay(
                            MaprecommendationActivity.this, aMap, drivePath,
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

    public class LoginTask extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected Integer doInBackground(Void... arg0) {               //在此处处理UI会导致异常
            Login_Info_t loginInfo = new Login_Info_t();
            Integer error = Integer.valueOf(0);
            loginInfo.szIp = ip.getBytes();
            String strPort = port.trim();
            loginInfo.nPort = Integer.parseInt(strPort);
            loginInfo.szUsername = user.getBytes();
            loginInfo.szPassword = password.getBytes();
            loginInfo.nProtocol = 2;
//            saveLoginInfo();
            int nRet = IDpsdkCore.DPSDK_Login(mAPP.getDpsdkCreatHandle(), loginInfo, 30000);
            return nRet;
        }

        @Override
        protected void onPostExecute(Integer result) {

            super.onPostExecute(result);
            if (result == 0) {
                Log.d("DpsdkLogin success:", result + "");
                IDpsdkCore.DPSDK_SetCompressType(mAPP.getDpsdkCreatHandle(), 0);
                mAPP.setLoginHandler(1);
                getGroupList();

            } else {
                Log.d("DpsdkLogin failed:", result + "");
                Toast.makeText(getApplicationContext(), "login failed" + result, Toast.LENGTH_SHORT).show();
                mAPP.setLoginHandler(0);

                //m_loginHandle = 0;
                //jumpToContentListActivity();
            }
        }

    }

    /**
     * <p>
     * 获取组织列表
     * </p>
     *
     * @author fangzhihua 2014-5-12 上午9:56:14
     */
    private void getGroupList() {
        root = mGroupListManager.getRootNode();
        if (root == null) {
//            mWattingPb.setVisibility(View.VISIBLE);
        }

        if (mGroupListManager.getTask() != null) {
//            mGroupListManager.setGroupListGetListener(mIOnSuccessListener);
        }
        if (mGroupListManager.isFinish() && root != null) {
            if (root.getChildren().size() == 0) {
                mGroupListManager.startGroupListGetTask();
            }
            Log.i("mmmmmm", "getGroupList finished---" + root.getChildren().size());
//            sendMessage(mHandler, MSG_GROUPLIST_UPDATELIST, 0, 0);
            return;
        } else if (root == null) {
            if (mGroupListManager.getTask() == null) {
                // 获取组织树任务
                Log.i("mmmmmm", "开始 执行GroupListGetTask");
                mGroupListManager.startGroupListGetTask();
                mGroupListManager.setGroupListGetListener(mIOnSuccessListener);
            }
        }

    }

    GroupListGetTask.IOnSuccessListener mIOnSuccessListener = new GroupListGetTask.IOnSuccessListener() {
        @Override
        public void onSuccess(final boolean success, final int errCode) {
            if (success) {
                root = mGroupListManager.getRootNode();
                Log.e("root", root + "");

            } else {
                ToastUtil.show(MaprecommendationActivity.this, "获取组织列表失败，异常代码：" + errCode);
            }

        }
    };

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        aMap.setOnMarkerClickListener(this);

        //自定义infowindows
        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);
        // aMap.setMyLocationType()
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
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

    @OnClick({R.id.ll_recommend, R.id.ll_search, R.id.ll_navi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_recommend:
                intent.setClass(MaprecommendationActivity.this, MapRecommendLineActivity.class);
                startActivityForResult(intent, 654321);
                break;
            case R.id.ll_search:
                intent.setClass(MaprecommendationActivity.this, MapSearchActivity.class);
                startActivityForResult(intent, 123123);
                break;
            case R.id.ll_navi:
                if (lat == 0 || lng == 0) {
                    ToastUtil.show(MaprecommendationActivity.this, "定位失败");
                } else {
                    intent.setClass(MaprecommendationActivity.this, MapGuideActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    intent.putExtra("latend", mEndPoint.getLatitude());
                    intent.putExtra("lngend", mEndPoint.getLongitude());
                    startActivity(intent);
                }
                break;
        }
    }

    private void isInMap() {
        aMap.setOnMapClickListener(this);
        // 绘制一个长方形
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.205725,
                118.361903), 14));//设置中心点
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
    private int[] R_map2 = {R.drawable.lpcniu_5, R.drawable.lpcniu_6,
            R.drawable.lpcniu_7, R.drawable.lpcniu_8, R.drawable.lpcniu_9};

    /**
     * 添加瓦片图层
     **/
    public void Addground() {


        for (int i = 0; i < R_map1.length; i++) {
            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(new LatLng(39.26975243, 118.41303362 - ((4 - i) * 0.019586626000000003)))
                    .include(new LatLng(39.15506772 + (0.11468471 / 2), 118.31510049 + (i * 0.019586626000000003))).build();
            aMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory
                            .fromResource(R_map1[i]))
                    .positionFromBounds(bounds));

        }

        for (int i = 0; i < R_map2.length; i++) {
            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(new LatLng(39.26975243 - (0.11468471 / 2), 118.41303362 - ((4 - i) * 0.019586626000000003)))
                    .include(new LatLng(39.15506772, 118.31510049 + (i * 0.019586626000000003))).build();
            aMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory
                            .fromResource(R_map2[i]))
                    .positionFromBounds(bounds));
        }


    }


    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_infowindow, null);
        TextView tv_info = (TextView) view.findViewById(R.id.tv_infowindow);
        tv_info.setText(marker.getTitle());
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_infowindow, null);
        return view;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        intent = new Intent(MaprecommendationActivity.this, RealPlayActivity.class);
        MonitorBean.DataBean.RowsBean bean = (MonitorBean.DataBean.RowsBean) marker.getObject();
        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;
//        int pos = 0;
//        for (int i = 0; i < monitorBean.getData().getRows().size(); i++) {
//            double latitude1 = 0, longitude1 = 0;
//            String aa = "", bb = "";
//            try {
//                latitude1 = Double.parseDouble(monitorBean.getData().getRows().get(i).getY_point());
//                longitude1 = Double.parseDouble(monitorBean.getData().getRows().get(i).getX_point());
//                DecimalFormat df = new DecimalFormat("######0.000000");
//                aa = df.format(latitude1);
//                bb = df.format(longitude1);
//            } catch (Exception e) {
//
//            }
//
//            if (latitude == Double.parseDouble(aa) && Double.parseDouble(bb) == longitude) {
//                pos = i;
//                break;
//            }
//        }

//        if (root == null) {
//            ToastUtil.show(MaprecommendationActivity.this, "未知异常");
//            return false;
//        }
//        String channelId = null;
//        for (int ii = 0; ii < root.getChildren().size(); ii++) {
//            if (root.getChildren().get(ii).getValue().equals(monitorBean.getData().getRows().get(pos).getCode())) {
//                channelId = root.getChildren().get(ii).getChildren().get(0).getValue();
//
//            }
//        }
//        if (channelId == null) {
//            ToastUtil.show(MaprecommendationActivity.this, "未找到摄像头");
//            return false;
//        }

        intent.putExtra("channelId", "");
        intent.putExtra("name", bean.getName());
        intent.putExtra("conn", bean.getContent());
        startActivity(intent);
        return false;
    }

    public void Logout() {
        if (mAPP.getLoginHandler() == 0) {
            return;
        }
        int nRet = IDpsdkCore.DPSDK_Logout(mAPP.getDpsdkCreatHandle(), 30000);

        if (0 == nRet) {
            //m_loginHandle = 0;
            mAPP.setLoginHandler(0);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapRecommend.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapRecommend.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ss != null) {
            ss.pauseSpeaking();
            ss.destroy();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapRecommend.onDestroy();
        if (ss != null) {
            ss.pauseSpeaking();
            ss.destroy();
        }
        R_map2 = null;
        R_map1 = null;
//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        Logout();
//
//        IDpsdkCore.DPSDK_Destroy(mAPP.getDpsdkCreatHandle());
    }

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
        mapRecommend.onResume();

    }

    /**
     * 定位成功后回调函数
     */
    boolean noinflag;  //不在园区内是否播报过； false是没有，true是播报过
    boolean bobaoflag;  //在园区内时，路线导航是否播报过

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点

                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度
                mStartPoint = new LatLonPoint(lat, lng);
                latLng = new LatLng(lat, lng);
                if (!polygon.contains(latLng)) {
                    if (!noinflag) {
                        read("您没有在景区内,请移步到景区内");
                        noinflag = true;
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.205725, 118.361903), 14));//设置中心点
//                        llRecommend.setVisibility(View.INVISIBLE);
//                        llSearch.setVisibility(View.INVISIBLE);
                    }

                } else {
                    if (llRecommend.getVisibility() == View.INVISIBLE)
                        llRecommend.setVisibility(View.INVISIBLE);
                    if (llSearch.getVisibility() == View.INVISIBLE)
                        llSearch.setVisibility(View.INVISIBLE);
                    noinflag = false;

//                    int luxianflag; //1,推荐路线，2导航路线
                    if (luxianflag == 1) {
                        if (lili.size() != 0) {

                            for (int i = 0; i < lili.size(); i++) {
                                if (lili.get(i).getBobaoflag() == 1) {
                                    LatLng temp = new LatLng(Double.parseDouble(lili.get(i).getY_point()), Double.parseDouble(lili.get(i).getX_point()));
                                    if (AMapUtils.calculateLineDistance(latLng, temp) < 20)
                                        return;
                                    else {
                                        lili.get(i).setBobaoflag(0);
                                        break;
                                    }

                                }

                            }

                            for (int i = 0; i < lili.size(); i++) {

                                LatLng temp = new LatLng(Double.parseDouble(lili.get(i).getY_point()), Double.parseDouble(lili.get(i).getX_point()));
                                if (lili.get(i).getType() == 1 && AMapUtils.calculateLineDistance(latLng, temp) < 20) {

                                    lili.get(i).setBobaoflag(1);
                                    read("您即将到达的是" + lili.get(i).getName() + "," + lili.get(i).getInfo());

                                    return;
                                }

                            }
                        }

//                            bobaoflag = false;
//                }

//
                    } else if (luxianflag == 2) {
                        if (mDriveRouteResult != null && mDriveRouteResult.getPaths().get(0) != null) {

                            for (int i = 0; i < list.size(); i++) {

                                if (list.get(i).getBobaoflag() == 1) {
                                    LatLng temp = new LatLng(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude()));
//                                LatLng temp = new LatLng(Double.parseDouble(list.get(i).getY_point()), Double.parseDouble(list.get(i).getX_point()));
                                    if (AMapUtils.calculateLineDistance(latLng, temp) < 20)
                                        return;
                                    else {
                                        list.get(i).setBobaoflag(0);
                                        break;
                                    }

                                }
                            }

                            for (int i = 0; i < list.size(); i++) {
                                LatLng temp = new LatLng(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude()));
//                            LatLng temp = new LatLng(Double.parseDouble(list.get(i).getY_point()),Double.parseDouble(list.get(i).getX_point()));
                                if (AMapUtils.calculateLineDistance(latLng, temp) < 20) {

                                    list.get(i).setBobaoflag(1);
                                    read("您即将到达的是" + list.get(i).getName() + "," + list.get(i).getVisitors_describe());
//                                read("您即将到达的是" + list.get(i).getName() + "," + list.get(i).getInfo());

                                    return;


                                }
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


    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static int REQUEST_CODE = 123456;

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    List<AnviRecommendBean.DataBean.RowsBean.ListDataBean> lili = new ArrayList<>();
    Polyline polyline;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123123) {
            if (data == null)
                return;
            luxianflag = 2;
            if (polyline != null)
                polyline.remove();
            if (drivingRouteOverlay != null)
                drivingRouteOverlay.removeFromMap();
//            llNavi.setVisibility(View.VISIBLE);
            mRouteSearch = new RouteSearch(this);
            mRouteSearch.setRouteSearchListener(this);
            mEndPoint = new LatLonPoint(data.getDoubleExtra("lat", 39.205725), data.getDoubleExtra("lng", 118.361903));
//                 aMap.addMarker(new MarkerOptions()
//                        .position(AMapUtil.convertToLatLng(mEndPoint))
//                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.end)));
            final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                    mStartPoint, mEndPoint);
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else if (requestCode == 654321) {
            if (data == null)
                return;
            if (polyline != null)
                polyline.remove();
            if (drivingRouteOverlay != null)
                drivingRouteOverlay.removeFromMap();
            lili.clear();
            lili.addAll((List<AnviRecommendBean.DataBean.RowsBean.ListDataBean>) (data.getSerializableExtra("line")));
            List<LatLng> latLngs = new ArrayList<LatLng>();
            for (int i = 0; i < lili.size(); i++) {
                latLngs.add(new LatLng(Double.parseDouble(lili.get(i).getY_point()), Double.parseDouble(lili.get(i).getX_point())));
            }
            luxianflag = 1;
            polyline = aMap.addPolyline(new PolylineOptions().
                    addAll(latLngs).width(10).color(Color.argb(255, 75, 196, 251)));
        } else if (requestCode == REQUEST_CODE) {
            // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED)
                finish();
        }
    }

}