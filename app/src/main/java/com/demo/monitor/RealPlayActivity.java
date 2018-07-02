package com.demo.monitor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.company.PlaySDK.IPlaySDK;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MyApplication;
import com.demo.view.MyTopBar;
import com.dh.DpsdkCore.Enc_Channel_Info_Ex_t;
import com.dh.DpsdkCore.Get_RealStream_Info_t;
import com.dh.DpsdkCore.IDpsdkCore;
import com.dh.DpsdkCore.Return_Value_Info_t;
import com.dh.DpsdkCore.fMediaDataCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：sonnerly on 2016/11/22 0022 10:17
 */
public class RealPlayActivity extends Activity {
    @Bind(R.id.sv_player)
    SurfaceView svPlayer;
    @Bind(R.id.monitor)
    MyTopBar monitor;
    @Bind(R.id.tv_mo_name)
    TextView tvMoName;
    @Bind(R.id.tv_mo_comment)
    TextView tvMoComment;
    private int m_nPort = 0;
    private int mTimeOut = 10 * 1000;
    private int m_nSeq = 0;

    private MyApplication mAPP = MyApplication.get();
    private byte[] m_szCameraId = null;
    private int m_pDLLHandle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
        ButterKnife.bind(this);
        tvMoName.setText(getIntent().getStringExtra("name"));
        tvMoComment.setText(getIntent().getStringExtra("conn"));
        m_pDLLHandle = MyApplication.get().getDpsdkHandle();
        m_szCameraId = getIntent().getStringExtra("channelId").getBytes();
        m_nPort = IPlaySDK.PLAYGetFreePort();
        SurfaceHolder holder = svPlayer.getHolder();
        holder.addCallback(new Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d("xss", "surfaceCreated");
                IPlaySDK.InitSurface(m_nPort, svPlayer);
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
                Log.d("xss", "surfaceChanged");
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("xss", "surfaceDestroyed");
            }
        });
        inti();

    }


    private void inti() {
        monitor.setLeftImageOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final fMediaDataCallback fm = new fMediaDataCallback() {
            @Override
            public void invoke(int nPDLLHandle, int nSeq, int nMediaType,
                               byte[] szNodeId, int nParamVal, byte[] szData, int nDataLen) {

                int ret = IPlaySDK.PLAYInputData(m_nPort, szData, nDataLen);
                if (ret == 1) {
                    Log.e("xss", "playing success=" + nSeq + " package size=" + nDataLen);
                } else {
                    Log.e("xss", "playing failed=" + nSeq + " package size=" + nDataLen);
                }
            }
        };
        if (!StartRealPlay()) {
            Log.e("xss", "StartRealPlay failed!");
            Toast.makeText(getApplicationContext(), "Open video failed!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Return_Value_Info_t retVal = new Return_Value_Info_t();
            Get_RealStream_Info_t getRealStreamInfo = new Get_RealStream_Info_t();
            //m_szCameraId = etCam.getText().toString().getBytes();
            System.arraycopy(m_szCameraId, 0, getRealStreamInfo.szCameraId, 0, m_szCameraId.length);
            //getRealStreamInfo.szCameraId = "1000096$1$0$0".getBytes();
            getRealStreamInfo.nMediaType = 1;
            getRealStreamInfo.nRight = 0;
            getRealStreamInfo.nStreamType = 1;
            getRealStreamInfo.nTransType = 1;
            Enc_Channel_Info_Ex_t ChannelInfo = new Enc_Channel_Info_Ex_t();
            IDpsdkCore.DPSDK_GetChannelInfoById(m_pDLLHandle, m_szCameraId, ChannelInfo);
            int ret = IDpsdkCore.DPSDK_GetRealStream(m_pDLLHandle, retVal, getRealStreamInfo, fm, mTimeOut);
            if (ret == 0) {
//                btOpenVideo.setEnabled(false);
//                btCloseVideo.setEnabled(true);
                m_nSeq = retVal.nReturnValue;
                Log.e("xss  success!", ret + "");
//                Toast.makeText(getApplicationContext(), "Open video success!", Toast.LENGTH_SHORT).show();
            } else {
                StopRealPlay();
                Log.e("xss  failed!", ret + "");
                Toast.makeText(getApplicationContext(), "Open video failed!", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Log.e("xss", e.toString());
        }
    }

    public void StopRealPlay() {
        try {
            IPlaySDK.PLAYStopSoundShare(m_nPort);
            IPlaySDK.PLAYStop(m_nPort);
            IPlaySDK.PLAYCloseStream(m_nPort);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean StartRealPlay() {
        if (svPlayer == null)
            return false;

        boolean bOpenRet = IPlaySDK.PLAYOpenStream(m_nPort, null, 0, 1500 * 1024) == 0 ? false : true;
        if (bOpenRet) {
            boolean bPlayRet = IPlaySDK.PLAYPlay(m_nPort, svPlayer) == 0 ? false : true;
            Log.i("StartRealPlay", "StartRealPlay1");
            if (bPlayRet) {
                boolean bSuccess = IPlaySDK.PLAYPlaySoundShare(m_nPort) == 0 ? false : true;

                Log.i("StartRealPlay", "StartRealPlay2");
                if (!bSuccess) {
                    IPlaySDK.PLAYStop(m_nPort);
                    IPlaySDK.PLAYCloseStream(m_nPort);
                    Log.i("StartRealPlay", "StartRealPlay3");
                    return false;
                }
            } else {
                IPlaySDK.PLAYCloseStream(m_nPort);
                Log.i("StartRealPlay", "StartRealPlay4");
                return false;
            }
        } else {
            Log.i("StartRealPlay", "StartRealPlay5");
            return false;
        }

        return true;
    }



    @Override
    protected void onDestroy() {


        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();

    }


}
