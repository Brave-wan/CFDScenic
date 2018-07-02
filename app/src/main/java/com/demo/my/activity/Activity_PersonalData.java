package com.demo.my.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.bean.ImageBean;
import com.demo.my.bean.GetDatum;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.RoundImageView;
import com.demo.view.permission.PermissionsActivity;
import com.demo.view.permission.PermissionsChecker;
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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 我的--个人资料
 * Created by Administrator on 2016/7/22 0022.
 */
public class Activity_PersonalData extends Activity implements SelectImagePopupWindow.OnCompleteListener {


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
    ImageBean imageBean = new ImageBean();
    PermissionsChecker mPermissionsChecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldata);
        ButterKnife.bind(this);
        mPermissionsChecker = new PermissionsChecker(this);
        SelectphotoPPW = new SelectImagePopupWindow(this);
        SelectphotoPPW.addOnCompleteListener(this);
        getDatum();
        intent = new Intent();
    }


    @OnClick({R.id.ll_personaldata_touxiang, R.id.ll_personaldata_nickname, R.id.ll_personaldata_gender, R.id.ll_personaldata_telephone, R.id.bt_personaldata_baocun})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_personaldata_touxiang:
                SelectphotoPPW.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//                show_popupWindow();
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
                if (!getDatum.getData().getOpenId().equals("")){
                    if (getDatum.getData().getMobileNo().equals("")){
                        intent.setClass(getApplicationContext(), Activity_ContactPhone.class);
                        intent.putExtra("ppp", tvTelephone.getText().toString());
                        startActivityForResult(intent, 22222);
                    }else{
                        ToastUtil.show(Activity_PersonalData.this, "已绑定的手机号不可修改");
                    }

                }

                break;
            case R.id.bt_personaldata_baocun:
                editDatum();
                break;
        }
    }

    //显示popuwindow 性别
    private void show_gender() {
        //设置contentView
        View contentView = LayoutInflater.from(Activity_PersonalData.this).inflate(R.layout.popup_gender, null);
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
        View rootview = LayoutInflater.from(Activity_PersonalData.this).inflate(R.layout.popup_gender, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onSelectImage(List<Uri> uris) {
        SelectphotoPPW.toCropPhoto(uris.get(0));
    }

    List<String> pathlist = new ArrayList<>();

    @Override
    public void onCropImage(List<String> urlist) {
        pathlist = urlist;
        for (int i = 0; i < urlist.size(); i++) {

            try {
                ImageLoader.getInstance().displayImage(geturi(urlist.get(i)), viewImageView);
//                    pathlist.set(0, FileTools.getRealFilePath(this, urlist.get(i)) + ".jpg");
//                    listbitmap.set(0, image);
                pathlist.set(0, urlist.get(i));

            } catch (Exception e) {

            }
        }

//        for (int i=0;i<urlist.size();i++){
//
//            try {
//                Bitmap image = FileTools.getbitmap1(urlist.get(i));
////                setUri(uri)
////               filepath = FileTools.getRealFilePath(this, urlist.get(0)) + ".jpg";
//                filepath=urlist.get(i);
//                viewImageView.setImageBitmap(image);
//            } catch (Exception e) {
////                            KLog.e(e);
//            }
//        }

//        filepath = FileTools.getRealFilePath(this, SelectphotoPPW.getUri()) + ".jpg";
//        imDataPersonal.setImageBitmap(bitmap);
    }

    @Override
    public void onCaptureImage(Uri uri) {
        SelectphotoPPW.toCropPhoto(uri);
    }

    @Override
    public void onCaptureVidio(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11111) {
            if (data == null)
                return;
            tvNickname.setText(data.getStringExtra("nick"));
        } else if (requestCode == 22222) {
            if (data == null)
                return;
            tvTelephone.setText(data.getStringExtra("phone"));
        }else if (requestCode == REQUEST_CODE) {
            // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED)
                finish();
        } else
        SelectphotoPPW.onActivityResult(requestCode, resultCode, data);
    }
    // 所需权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static int REQUEST_CODE = 123456;

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
    public String geturi(String path) {

        Uri uri = null;

        path = path.trim();
        if (path != null) {
            path = Uri.decode(path);
            path = path.trim();
            ContentResolver cr = this.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.ImageColumns._ID},
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur
                    .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                // set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
                //do nothing
                uri = SelectphotoPPW.getUri();
            } else {
                Uri uri_temp = Uri
                        .parse("content://media/external/images/media/"
                                + index);
                if (uri_temp != null) {
                    uri = uri_temp;
                }
            }
        }
        return uri + "";

    }

    private class Click implements View.OnClickListener {

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
    }

    //获取
    private void getDatum() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_PersonalData.this, SpName.token, ""));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.getDatum, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_PersonalData.this,R.style.AlertDialogStyle);
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
                            getDatum = new Gson().fromJson(responseInfo.result, GetDatum.class);
                            int i = getDatum.getHeader().getStatus();
                            if (i == 0) {
                                if (getDatum.getData().getGender() == 0) {
                                    tvGender.setText("男");
                                    tvGender.setTextColor(Color.parseColor("#000000"));
                                    gender = 0;
                                } else {
                                    tvGender.setText("女");
                                    tvGender.setTextColor(Color.parseColor("#000000"));
                                    gender = 1;
                                }
//                                BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
//                                bitmapUtils.display(viewImageView, "http://bbs.lidroid.com/static/image/common/logo.png");
                                ImageLoader.getInstance().displayImage(getDatum.getData().getHeadImg(), viewImageView);
                                tvNickname.setText(getDatum.getData().getNickName());
                                tvTelephone.setText(getDatum.getData().getMobileNo() + "");
                            } else {
                                ToastUtil.show(getApplicationContext(), getDatum.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

    }

    //修改
    private void editDatum() {
        if (pathlist.size() == 0) {
            hahah("");
        } else {
            PostFormBuilder getBuilder = OkHttpUtils.post();
            getBuilder
                    .url(URL.myTickets_upload)
                    .addHeader("Authorization", SpUtil.getString(Activity_PersonalData.this, SpName.token, ""))
                    .addParams("type", "0")
                    .addFile("file", "", new File(pathlist.get(0)))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            ToastUtil.show(Activity_PersonalData.this, e.getMessage());
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            try {
                                imageBean = new Gson().fromJson(s, ImageBean.class);
                                if (imageBean.getHeader().getStatus() == 0) {
                                    hahah(imageBean.getData().get(0));
                                } else {
                                    ToastUtil.show(Activity_PersonalData.this, imageBean.getHeader().getMsg());
                                }

                            } catch (Exception e) {
                                ToastUtil.show(Activity_PersonalData.this, e.getMessage());
                            }

                        }
                    });

        }
    }

    private void hahah(String s) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_PersonalData.this, SpName.token, ""));
        params.addQueryStringParameter("id", SpUtil.getString(Activity_PersonalData.this, SpName.userId, ""));
        params.addQueryStringParameter("nickName", tvNickname.getText().toString());
        params.addQueryStringParameter("gender", gender + "");
        if (pathlist.size() == 0) {
            params.addQueryStringParameter("headImg", getDatum.getData().getHeadImg());//geturi(pathlist.get(0))
        } else {
            params.addQueryStringParameter("headImg", s);
        }
        params.addQueryStringParameter("mobileNo", tvTelephone.getText().toString());
        params.addQueryStringParameter("openId", getDatum.getData().getOpenId());
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.editDatum, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(Activity_PersonalData.this,R.style.AlertDialogStyle);
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
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
//                                SpUtil.putString(Activity_PersonalData.this, SpName.headimg, getDatum.getData().getHeadImg());
                                Intent intent=new Intent();
                                intent.putExtra("ok",true);
                                setResult(0x001,intent);
                                finish();
                            } else if (i==3){
                                ToastUtil.show(Activity_PersonalData.this, header.getString("msg"));
                            } else if (i==5){
                                ToastUtil.show(Activity_PersonalData.this, header.getString("msg"));
                                Intent intent=new Intent();
                                intent.putExtra("ok",true);
                                setResult(0x001,intent);
                                finish();
                            } else if (i==6){
                                ToastUtil.show(Activity_PersonalData.this, header.getString("msg"));
                                Intent intent=new Intent();
                                intent.putExtra("ok",true);
                                setResult(0x001,intent);
                                finish();
                            }else {
                                ToastUtil.show(Activity_PersonalData.this, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_PersonalData.this, "解析数据失败");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_PersonalData.this, "连接网络失败");
                    }
                });

    }

}
