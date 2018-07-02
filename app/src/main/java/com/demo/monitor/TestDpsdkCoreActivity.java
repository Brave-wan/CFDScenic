package com.demo.monitor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MyApplication;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.permission.PermissionsActivity;
import com.demo.view.permission.PermissionsChecker;
import com.dh.DpsdkCore.Dep_Info_t;
import com.dh.DpsdkCore.Device_Info_Ex_t;
import com.dh.DpsdkCore.Enc_Channel_Info_Ex_t;
import com.dh.DpsdkCore.Get_Channel_Info_Ex_t;
import com.dh.DpsdkCore.Get_Dep_Count_Info_t;
import com.dh.DpsdkCore.Get_Dep_Info_t;
import com.dh.DpsdkCore.IDpsdkCore;
import com.dh.DpsdkCore.Login_Info_t;
import com.dh.DpsdkCore.Return_Value_Info_t;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：sonnerly on 2016/11/22 0022 16:57
 */
public class TestDpsdkCoreActivity extends Activity implements AMap.OnMarkerClickListener {
    @Bind(R.id.map_monitor_point)
    MapView mapMonitorPoint;
    String ip = "192.168.1.195";
    String port = "9000";
    String user = "admin";
    String password = "dahua2006";
    private MyApplication mAPP = MyApplication.get();
    private AMap aMap;
    PermissionsChecker mPermissionsChecker;
    ArrayList<Marker> saveMarkerList = new ArrayList<Marker>();
    private GroupListManager mGroupListManager = null;
    // 获取的树信息
    private TreeNode root = null;
    MonitorBean monitorBean = null;
    Intent intent = new Intent();
//    getshexiangtouid
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor_point_activity);
        ButterKnife.bind(this);
        mAPP.initApp();
        mGroupListManager = GroupListManager.getInstance();
        getip();
        mPermissionsChecker = new PermissionsChecker(this);
        mapMonitorPoint.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapMonitorPoint.getMap();
//            setUpMap();
            aMap.getUiSettings().setScaleControlsEnabled(true);//显示比例尺控件
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
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.205725,
                118.361903), 14));//设置中心点 //118.361903,39.205725
        Addground();
        aMap.setOnMarkerClickListener(this);
        initGet();
//         getGroupList();
    }

    private int[] R_map1 = {R.drawable.lpcniu_0
            , R.drawable.lpcniu_1, R.drawable.lpcniu_2, R.drawable.lpcniu_3, R.drawable.lpcniu_4
    };
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
                                            saveMarkerList.add(marker);

                                        }
                                    }

                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), monitorBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(TestDpsdkCoreActivity.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(TestDpsdkCoreActivity.this, e.getMessage());
                    }
                });
    }

    /**获取登录ip地址**/
    public  void getip(){
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.getshexiangtouid, null,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        try {
//                            monitorBean = new Gson().fromJson(responseInfo.result, MonitorBean.class);
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject head=jsonObject.getJSONObject("header");
                            int i = head.getInt("status");
                            if (i == 0) {
                                ip = jsonObject.getJSONObject("data").getString("ip");
                                port=jsonObject.getJSONObject("data").getString("port_number");
                                user=jsonObject.getJSONObject("data").getString("user");
                                password=jsonObject.getJSONObject("data").getString("password");
                                new LoginTask().execute();
                            } else {
                                ToastUtil.show(getApplicationContext(), head.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(TestDpsdkCoreActivity.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(TestDpsdkCoreActivity.this, e.getMessage());
                    }
                });

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
                Log.e("root",root+"");
//                        if (root != null) {
//                            mGroupListAdapter.clearDate();
//                            mGroupListAdapter.addNode(root);
//                            // 设置默认展开级别
//                            mGroupListAdapter.setExpandLevel(1);
//                            mGroupListAdapter.notifyDataSetChanged();
//                        } else {
//                            mGroupListAdapter.clearDate();
//                            mGroupListAdapter.notifyDataSetChanged();
//                        }
                //updateSelectChannels();
            } else {
                ToastUtil.show(TestDpsdkCoreActivity.this, "获取组织列表失败，异常代码："+errCode);
            }

//            mHandler.post(new Runnable() {
//
//                @Override
//                public void run() {
//                    // 清空任务
//                    mGroupListManager.setTask(null);
//
//
//                    if (success) {
//                        root = mGroupListManager.getRootNode();
//                        Log.e("root",root+"");
//                        if (root != null) {
//                            mGroupListAdapter.clearDate();
//                            mGroupListAdapter.addNode(root);
//                            // 设置默认展开级别
//                            mGroupListAdapter.setExpandLevel(1);
//                            mGroupListAdapter.notifyDataSetChanged();
//                        } else {
//                            mGroupListAdapter.clearDate();
//                            mGroupListAdapter.notifyDataSetChanged();
//                        }
//                        updateSelectChannels();
//                    } else {
//                        ToastUtil.show(TestDpsdkCoreActivity.this, "获取组织列表失败，异常代码："+errCode);
//                    }
//
//                }
//            });
        }
    };
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
        }
    }



    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        new LoginTask(marker).execute();

        intent = new Intent(TestDpsdkCoreActivity.this, RealPlayActivity.class);
        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;
        int pos=0;
        for (int i=0;i<monitorBean.getData().getRows().size();i++){
            double latitude1=0,longitude1=0;
            String aa="",bb="";
            try {
                latitude1=Double.parseDouble(monitorBean.getData().getRows().get(i).getY_point());
                longitude1=Double.parseDouble(monitorBean.getData().getRows().get(i).getX_point());
                DecimalFormat df   = new DecimalFormat("######0.000000");
                aa= df.format(latitude1);
                bb=df.format(longitude1);
            }catch (Exception e){

            }

            if(latitude==Double.parseDouble(aa)&&Double.parseDouble(bb)==longitude){
                pos=i;
                break;
            }
        }

        if(root==null){
            ToastUtil.show(TestDpsdkCoreActivity.this,"未知异常");
            return false;
        }
        String channelId = null;
          for(int ii=0;ii<root.getChildren().size();ii++){
              if(root.getChildren().get(ii).getValue().equals(monitorBean.getData().getRows().get(pos).getCode())){
                  channelId= root.getChildren().get(ii).getChildren().get(0).getValue();

              }
          }
        if(channelId==null){
            ToastUtil.show(TestDpsdkCoreActivity.this,"未找到摄像头");
            return false;
        }

        intent.putExtra("channelId", channelId);
        intent.putExtra("name", monitorBean.getData().getRows().get(pos).getName());
        intent.putExtra("conn", monitorBean.getData().getRows().get(pos).getContent());
        startActivity(intent);
        return false;
    }

    public class LoginTask extends AsyncTask<Void, Integer, Integer> {


        @Override
        protected Integer doInBackground(Void... arg0) {               //在此处处理UI会导致异常
//			if (mloginHandle != 0) {
//	    		IDpsdkCore.DPSDK_Logout(m_loginHandle, 30000);
//        		m_loginHandle = 0;
//	    	}
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
//                 jumpToItemListActivity();
//                intent.setClass(TestDpsdkCoreActivity.this, RealPlayActivity.class);
//
//                double latitude = marker.getPosition().latitude;
//                double longitude = marker.getPosition().longitude;
//                int pos=0;
//                for (int i=0;i<monitorBean.getData().getRows().size();i++){
//                    double latitude1=0,longitude1=0;
//                    String aa="",bb="";
//                    try {
//                        latitude1=Double.parseDouble(monitorBean.getData().getRows().get(i).getY_point());
//                        longitude1=Double.parseDouble(monitorBean.getData().getRows().get(i).getX_point());
//                        DecimalFormat df   = new DecimalFormat("######0.000000");
//                        aa= df.format(latitude1);
//                        bb=df.format(longitude1);
//                    }catch (Exception e){
//
//                    }
//
//                    if(latitude==Double.parseDouble(aa)&&longitude==Double.parseDouble(bb)){
//                        pos=i;
//                        break;
//                    }
//                }
//                intent.putExtra("name", monitorBean.getData().getRows().get(pos).getName());
//                intent.putExtra("conn", monitorBean.getData().getRows().get(pos).getContent());
//                startActivity(intent);
            } else {
                Log.d("DpsdkLogin failed:", result + "");
                Toast.makeText(getApplicationContext(), "login failed" + result, Toast.LENGTH_SHORT).show();
                mAPP.setLoginHandler(0);

                //m_loginHandle = 0;
                //jumpToContentListActivity();
            }
        }

    }
//    public void jumpToItemListActivity()
//    {

    //intent.setClass(this, ItemListActivity.class);
//        startActivityForResult(intent, 2);
//    }
    private void saveLoginInfo() {
        SharedPreferences sp = getSharedPreferences("LOGININFO", 0);
        SharedPreferences.Editor ed = sp.edit();
        StringBuilder sb = new StringBuilder();
        sb.append(ip).append(",").append(port).append(",")
                .append(password).append(",").append(user);
        ed.putString("INFO", sb.toString());
        ed.putString("ISFIRSTLOGIN", "false");
        ed.commit();
        Log.i("TestDpsdkCoreActivity", "saveLoginInfo" + sb.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPermissionsChecker.getAndroidSDKVersion() >= 23) {
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS))
                startPermissionsActivity();
        }
        mapMonitorPoint.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapMonitorPoint.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理


        Logout();

//        IDpsdkCore.DPSDK_Destroy(mAPP.getDpsdkCreatHandle());
//        mapMonitorPoint.onDestroy();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapMonitorPoint.onSaveInstanceState(outState);

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

}
