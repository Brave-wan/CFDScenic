package com.demo.Zxing;

import android.Manifest;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.demo.demo.myapplication.R;
import com.demo.mall.activity.ConfirmOrderTcActivity;
import com.demo.scenicspot.activity.ChosePaywayActivity;
import com.demo.utils.ToastUtil;
import com.demo.view.permission.PermissionsActivity;
import com.demo.view.permission.PermissionsChecker;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class Activity_Zxing extends AppCompatActivity implements QRCodeView.Delegate {
    private static final String TAG = Activity_Zxing.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    @Bind(R.id.iv_aty_sciencemore_back)
    ImageView ivAtySciencemoreBack;

    private QRCodeView mQRCodeView;
    PermissionsChecker mPermissionsChecker;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        ButterKnife.bind(this);
        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
        mPermissionsChecker=new PermissionsChecker(this);


        ivAtySciencemoreBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    public void onScanQRCodeSuccess(String result) {
        finish();
        ToastUtil.show(getApplicationContext(),"该功能未完善");
//        Intent intent = new Intent(Activity_Zxing.this, PaymentOrderZxing.class);
//        startActivity(intent);
        mQRCodeView.startSpot();
    }

    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    // 所需权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static int REQUEST_CODE = 123456;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED)
                finish();
        }/* else
            SelectphotoPPW.onActivityResult(requestCode, resultCode, data);*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPermissionsChecker.getAndroidSDKVersion() >= 23) {
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS))
                startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }
}