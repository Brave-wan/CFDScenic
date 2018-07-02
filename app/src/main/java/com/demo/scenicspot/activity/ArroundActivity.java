package com.demo.scenicspot.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.GetSurroundingbean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.permission.PermissionsActivity;
import com.demo.view.permission.PermissionsChecker;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
/*import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;*/
import com.demo.demo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者： on 2016/8/3 0003 16:11
 */
public class ArroundActivity extends Activity implements LocationSource, AMapLocationListener ,AMap.InfoWindowAdapter,AMap.OnMarkerClickListener{
    @Bind(R.id.frag_arround_map)
    FrameLayout fragArroundMap;
    @Bind(R.id.rb_map_jingdian)
    RadioButton rbMapJingdian;
    @Bind(R.id.rb_map_jiudian)
    RadioButton rbMapJiudian;
    @Bind(R.id.rb_map_fandian)
    RadioButton rbMapFandian;
    @Bind(R.id.rb_map_xiaochi)
    RadioButton rbMapXiaochi;
    @Bind(R.id.rb_map_techan)
    RadioButton rbMapTechan;
    @Bind(R.id.rb_map_xishoujian)
    RadioButton rbMapXishoujian;
    @Bind(R.id.rb_map_tingchechang)
    RadioButton rbMapTingchechang;
    @Bind(R.id.rb_map_chongzhi)
    RadioButton rbMapChongzhi;
    @Bind(R.id.rg_arround_map)
    RadioGroup rgArroundMap;


    int position = 0;
    private AMap aMap;
    private MapView mapView;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    ArrayList<Marker> saveMarkerList = new ArrayList<Marker>();

    PermissionsChecker mPermissionsChecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.science_activity_arround);
        ButterKnife.bind(this);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        position = getIntent().getIntExtra("position", 0);

        mPermissionsChecker=new PermissionsChecker(this);
        init();
        getSurrounding(position);
    }

    // 所需权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static int REQUEST_CODE = 123456;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED)
                finish();
        } /*else
            SelectphotoPPW.onActivityResult(requestCode, resultCode, data);*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPermissionsChecker.getAndroidSDKVersion() >= 23) {
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS))
                startPermissionsActivity();
        }
        mapView.onResume();
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    private void init() {
        switch (position) {
            case 1:
                rbMapJingdian.setChecked(true);
                break;
            case 2:
                rbMapJiudian.setChecked(true);
                break;
            case 3:
                rbMapFandian.setChecked(true);
                break;
            case 4:
                rbMapXiaochi.setChecked(true);
                break;
            case 5:
                rbMapTechan.setChecked(true);
                break;
            case 6:
                rbMapXishoujian.setChecked(true);
                break;
            case 7:
                rbMapTingchechang.setChecked(true);
                break;
            case 8:
                rbMapChongzhi.setChecked(true);
                break;
        }

        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }

        rgArroundMap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbMapJingdian.isChecked()){
                    getSurrounding(1);
                }
                if (rbMapJiudian.isChecked()){
                    getSurrounding(2);
                }
                if (rbMapFandian.isChecked()){
                    getSurrounding(3);
                }
                if (rbMapXiaochi.isChecked()){
                    getSurrounding(4);
                }
                if (rbMapTechan.isChecked()){
                    getSurrounding(5);
                }
                if (rbMapXishoujian.isChecked()){
                    getSurrounding(6);
                }
                if (rbMapTingchechang.isChecked()){
                    getSurrounding(7);
                }
                if (rbMapChongzhi.isChecked()){
                    getSurrounding(8);
                }

            }
        });
    }



    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.j_datouzhen));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 75, 196, 251));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(Color.argb(100, 75, 196, 251));
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));//放大
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

        //自定义infowindows
        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);
        // aMap.setMyLocationType()
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
//                amapLocation.getLatitude();//获取纬度
//                amapLocation.getLongitude();//获取经度

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
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


    //访问网络
    private void getSurrounding(int type){
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("type",type+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.getSurrounding, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            GetSurroundingbean getSurroundingbean=new Gson().fromJson(responseInfo.result,GetSurroundingbean.class);
                            int i=getSurroundingbean.getHeader().getStatus();
                            if(i==0){
                                if (saveMarkerList!=null){
                                    for (int position=0;position<saveMarkerList.size();position++){
                                        Marker marker=saveMarkerList.get(position);
                                        marker.remove();
                                    }
                                }
                                if (getSurroundingbean.getData()!=null){
                                    for (int index=0;index<getSurroundingbean.getData().size();index++){
                                        if (!getSurroundingbean.getData().get(index).getLongitude().equals("")){
                                            if (!getSurroundingbean.getData().get(index).getLatitude().equals("")){
                                                MarkerOptions markerOptions=new MarkerOptions();
                                                Double accuracy=Double.parseDouble(getSurroundingbean.getData().get(index).getLongitude());
                                                Double latitude=Double.parseDouble(getSurroundingbean.getData().get(index).getLatitude());
                                                markerOptions.position(new LatLng(accuracy, latitude));
                                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.j_ditu));
                                                markerOptions.title(getSurroundingbean.getData().get(index).getName());
                                                Marker marker= aMap.addMarker(markerOptions);
                                                saveMarkerList.add(marker);
                                            }
                                        }
                                    }

                                }
                            }else {
                                ToastUtil.show(getApplicationContext(), getSurroundingbean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(),"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_infowindow,null);
        TextView tv_info= (TextView) view.findViewById(R.id.tv_infowindow);
        tv_info.setText(marker.getTitle());
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_infowindow,null);
        return view;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }
}
