package com.demo.my.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.demo.myapplication.R;
import com.demo.my.bean.GetDatum;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.RoundImageView;
import com.demo.view.photo.FileTools;
import com.demo.view.photo.SelectImagePopupWindow;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的--个人资料
 * Created by Administrator on 2016/7/22 0022.
 */
public class Activity_PersonalData2 extends Activity {


    Intent intent;
    @Bind(R.id.ll_personaldata_touxiang)
    LinearLayout llPersonaldataTouxiang;
    @Bind(R.id.ll_personaldata_nickname)
    LinearLayout llPersonaldataNickname;
    @Bind(R.id.ll_personaldata_gender)
    LinearLayout llPersonaldataGender;
    @Bind(R.id.ll_personaldata_telephone)
    LinearLayout llPersonaldataTelephone;
    @Bind(R.id.bt_personaldata_baocun)
    Button btPersonaldataBaocun;

    ImageView im_male;
    ImageView im_female;
    @Bind(R.id.tv_Gender)
    TextView tvGender;
    @Bind(R.id.view_ImageView)
    RoundImageView viewImageView;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_Telephone)
    TextView tvTelephone;
    GetDatum getDatum;
    private PopupWindow mPopWindow;

    private int gender = 0;//判断男女。0男
    private SelectImagePopupWindow SelectphotoPPW;


    /**
     * 定义三种状态
     */
    private static final int REQUESTCODE_PIC = 1;//相册
    private static final int REQUESTCODE_CAM = 2;//相机
    private static final int REQUESTCODE_CUT = 3;//图片裁剪

    private Bitmap mBitmap;
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_personaldata);
        ButterKnife.bind(this);
        getDatum();
        intent = new Intent();
    }


    @OnClick({R.id.ll_personaldata_touxiang, R.id.ll_personaldata_nickname, R.id.ll_personaldata_gender, R.id.ll_personaldata_telephone, R.id.bt_personaldata_baocun})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_personaldata_touxiang:
                //SelectphotoPPW.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                show_popupWindow();
                break;
            case R.id.ll_personaldata_nickname:
                intent.setClass(getApplicationContext(), Activity_NickName.class);
                intent.putExtra("pp", tvNickname.getText().toString());
                startActivityForResult(intent, 11111);
                break;
            case R.id.ll_personaldata_gender:
                show_gender();
                break;
            case R.id.ll_personaldata_telephone:
                intent.setClass(getApplicationContext(), Activity_ContactPhone.class);
                intent.putExtra("ppp", tvTelephone.getText().toString());
                startActivityForResult(intent, 22222);
                break;
            case R.id.bt_personaldata_baocun:
                editDatum();
                break;
        }
    }

    //显示popupWindow
    private void show_popupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(Activity_PersonalData2.this).inflate(R.layout.popup_personal_data, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        TextView tv_data_Photograph = (TextView) contentView.findViewById(R.id.tv_data_Photograph);
        TextView tv_data_Album = (TextView) contentView.findViewById(R.id.tv_data_Album);
        TextView tv_data_cancel = (TextView) contentView.findViewById(R.id.tv_data_cancel);
        LinearLayout ll_data_popup = (LinearLayout) contentView.findViewById(R.id.ll_data_popup);
        tv_data_Photograph.setOnClickListener(new Click());
        tv_data_Album.setOnClickListener(new Click());
        tv_data_cancel.setOnClickListener(new Click());
        ll_data_popup.setOnClickListener(new Click());

        //显示PopupWindow
        View rootview = LayoutInflater.from(Activity_PersonalData2.this).inflate(R.layout.popup_personal_data, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }


    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_data_Photograph:   //拍照
                    openCamera();
                    break;
                case R.id.tv_data_Album:        //相册
                    openPic();
                    break;
                case R.id.tv_data_cancel:
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_data_popup:
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_male:
                    im_male.setVisibility(View.VISIBLE);
                    im_female.setVisibility(View.GONE);
                    tvGender.setText("男");
                    tvGender.setTextColor(Color.parseColor("#000000"));
                    mPopWindow.dismiss();
                    gender = 0;
                    break;
                case R.id.ll_female:    //女
                    im_female.setVisibility(View.VISIBLE);
                    im_male.setVisibility(View.GONE);
                    tvGender.setText("女");
                    tvGender.setTextColor(Color.parseColor("#000000"));
                    mPopWindow.dismiss();
                    gender = 1;
                    break;
            }
        }
    }

    //显示popuwindow 性别
    private void show_gender() {
        //设置contentView
        View contentView = LayoutInflater.from(Activity_PersonalData2.this).inflate(R.layout.popup_gender, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);

        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        im_male = (ImageView) contentView.findViewById(R.id.im_male);
        im_female = (ImageView) contentView.findViewById(R.id.im_female);
        contentView.findViewById(R.id.ll_male).setOnClickListener(new Click());
        contentView.findViewById(R.id.ll_female).setOnClickListener(new Click());
        contentView.findViewById(R.id.ll_popup).setOnClickListener(new Click());

        if (gender == 0) {
            im_male.setVisibility(View.VISIBLE);
            im_female.setVisibility(View.GONE);
        } else {
            im_female.setVisibility(View.VISIBLE);
            im_male.setVisibility(View.GONE);
        }
        //显示PopupWindow
        View rootview = LayoutInflater.from(Activity_PersonalData2.this).inflate(R.layout.popup_gender, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11111) {
            if(data==null)
                return;
            tvNickname.setText(data.getStringExtra("nick"));
            super.onActivityResult(requestCode, resultCode, data);
        } else if(requestCode == 22222){
            if(data==null)
                return;
            tvTelephone.setText(data.getStringExtra("phone"));
        }


        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUESTCODE_CAM:
                    startPhotoZoom(Uri.fromFile(mFile));
                    break;
                case REQUESTCODE_PIC:

                    if (data == null || data.getData() == null){
                        return;
                    }
//                    mFile = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
                    startPhotoZoom(data.getData());

                    break;
                case REQUESTCODE_CUT:

                    if (data!= null){
                        setPicToView(data);
                    }
                    break;
            }
        }
    }

   /* private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_popup:
                    mPopWindow.dismiss();
                    break;
                case R.id.ll_male:
                    im_male.setVisibility(View.VISIBLE);
                    im_female.setVisibility(View.GONE);
                    tvGender.setText("男");
                    tvGender.setTextColor(Color.parseColor("#000000"));
                    mPopWindow.dismiss();
                    gender = 0;
                    break;
                case R.id.ll_female:    //女
                    im_female.setVisibility(View.VISIBLE);
                    im_male.setVisibility(View.GONE);
                    tvGender.setText("女");
                    tvGender.setTextColor(Color.parseColor("#000000"));
                    mPopWindow.dismiss();
                    gender = 1;
                    break;
            }
        }
    }*/

    //获取
    private void getDatum() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_PersonalData2.this, SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, URL.getDatum, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            getDatum = new Gson().fromJson(responseInfo.result, GetDatum.class);
                            int i = getDatum.getHeader().getStatus();
                            if (i == 0) {
                                if (getDatum.getData().getGender() == 0) {
                                    tvGender.setText("男");
                                    tvGender.setTextColor(Color.parseColor("#000000"));
                                    gender = 0;

                                    SpUtil.putString(getApplicationContext(), SpName.Gender, "0");
                                } else {
                                    tvGender.setText("女");
                                    tvGender.setTextColor(Color.parseColor("#000000"));
                                    gender = 1;

                                    SpUtil.putString(getApplicationContext(), SpName.Gender, "1");
                                }
//                                BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
//                                bitmapUtils.display(viewImageView, "http://bbs.lidroid.com/static/image/common/logo.png");
                                tvNickname.setText(getDatum.getData().getNickName());
                                tvTelephone.setText(getDatum.getData().getMobileNo() + "");
                                SpUtil.putString(getApplicationContext(), SpName.userName, getDatum.getData().getNickName());
                            } else {
                                ToastUtil.show(getApplicationContext(), getDatum.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });

    }

    //修改
    private void editDatum() {
        RequestParams params = new RequestParams();
        SpUtil.putString(getApplicationContext(), SpName.Gender,gender + "");
        SpUtil.putString(getApplicationContext(), SpName.userName, tvNickname.getText().toString());
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpName.token, ""));
        //params.addQueryStringParameter("id", SpUtil.getString(getApplicationContext(), SpName.userName,""));
        params.addQueryStringParameter("nickName", tvNickname.getText().toString());
        params.addQueryStringParameter("gender", gender + "");
        params.addQueryStringParameter("headImg", "");
        params.addQueryStringParameter("mobileNo", tvTelephone.getText().toString());
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, URL.editDatum, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                SpUtil.putString(getApplicationContext(), SpName.Gender, gender + "");
                                SpUtil.putString(getApplicationContext(), SpName.userName, tvNickname.getText().toString());
                                finish();
                                ToastUtil.show(getApplicationContext(), "保存成功");
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });

    }

    //上传图片
    private void upload(){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplicationContext(), SpName.token, ""));
        params.addBodyParameter("file","");
        //params.addQueryStringParameter("id",);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, URL.upload, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            } else {
                                ToastUtil.show(getApplicationContext(), header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });

    }



    /**
     * 打开相册
     */
    private void openPic() {
        Intent picIntent = new Intent(Intent.ACTION_PICK,null);
        picIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(picIntent, REQUESTCODE_PIC);
    }

    /**
     * 调用相机
     */
    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!file.exists()){
                file.mkdirs();
            }
            mFile = new File(file, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
            startActivityForResult(intent,REQUESTCODE_CAM);
        } else {
            Toast.makeText(this, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
        }
    }



    private void setPicToView(Intent data) {
        Bundle bundle =  data.getExtras();
        if (bundle != null){
            //这里也可以做文件上传
            mBitmap = bundle.getParcelable("data");
            viewImageView.setImageBitmap(mBitmap);

        }
    }

    /**
     * 打开系统图片裁剪功能
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop",true);
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",300);
        intent.putExtra("outputY",300);
        intent.putExtra("scale",true); //黑边
        intent.putExtra("scaleUpIfNeeded",true); //黑边
        intent.putExtra("return-data",true);
        intent.putExtra("noFaceDetection",true);
        //intent.putExtra("output", Uri.parse("file:/" + mFile.getAbsolutePath()));
        startActivityForResult(intent,REQUESTCODE_CUT);

    }

}
