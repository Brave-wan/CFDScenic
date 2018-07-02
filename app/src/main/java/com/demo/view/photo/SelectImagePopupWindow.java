package com.demo.view.photo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.demo.demo.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;


/**
 * 提供图片选择popwindow
 * <p/>
 * 使用方法：
 * 1. new SelectImagePopupWindow(Activity);
 * 2. 实现 {@link OnCompleteListener } 并  {@link #addOnCompleteListener(OnCompleteListener)}
 * 3. 在 activity 的 onActivityResult 方法中 调用 SelectImagePopupWindow {@link #onActivityResult(int, int, Intent)}
 * 4. 在 activity 的 onSaveInstanceState 方法中 调用 SelectImagePopupWindow {@link #onSaveInstanceState(Bundle)}
 * 5. 在 activity 的 onRestoreInstanceState 方法中 调用 SelectImagePopupWindow {@link #onRestoreInstanceState(Bundle)}
 * <p/>
 * <p/>
 * 注意：需要权限 去读取图片，
 * "android.permission.WRITE_EXTERNAL_STORAGE"
 * "android.permission.READ_EXTERNAL_STORAGE"
 */

public class SelectImagePopupWindow extends PPWindow implements View.OnClickListener {

    public final int REQUEST_CAPTURE_IMAGE = 0;//启动相机
    public final int REQUEST_SELECT_IMAGE = 1;//选择图片
    public final int REQUEST_CROP_IMAGE = 2;//选择图

    public  final  int  REQUEST_CAPTURE_VIDIO=3; //录视频
    public  final  int  REQUEST_SELECT_VIDIO=4; //选视频
    int photocount=1;

    public int isgetpohto() {
        return isgetpohto;
    }

    public void setIsgetpohto(int isgetpohto,int photocount) {
        this.isgetpohto = isgetpohto;
        this.photocount=photocount;

        if(isgetpohto==1){
            ((TextView) getContentView().findViewById(R.id.tv_chose)).setText("选择图片");
//            ((ImageView) getContentView().findViewById(R.id.BottomSelectCreate)).setImageResource(R.mipmap.ship);
//            ((ImageView) getContentView().findViewById(R.id.BottomSelectLocal)).setImageResource(R.mipmap.luzhi);

        }else {

//            ((ImageView) getContentView().findViewById(R.id.BottomSelectCreate)).setImageResource(R.mipmap.xiangce);
//            ((ImageView) getContentView().findViewById(R.id.BottomSelectLocal)).setImageResource(R.mipmap.paizhao);

        }


    }

    private int isgetpohto;   //0 拍照，1 为录制 默认为0

    private Activity mActivity;

    private Set<OnCompleteListener> mOnCompleteListeners = new HashSet<>();

    private Uri mCaptureTmpUri;

    public SelectImagePopupWindow(Activity activity) {
        super(activity, LayoutInflater.from(activity).inflate(R.layout.popup_personal_data, null));
        mActivity = activity;
        init();
    }

    void init() {

        getContentView().findViewById(R.id.tv_data_Album).setOnClickListener(this);//本地
        getContentView().findViewById(R.id.tv_data_Photograph).setOnClickListener(this);//拍照
        getContentView().findViewById(R.id.tv_data_cancel).setOnClickListener(this);//取消
    }

    /**
     * 操作接口
     */
    public interface OnCompleteListener {
        /**
         * 已选择图片
         *
         * @param uris
         */
        void onSelectImage(List<Uri> uris);

        /**
         * 裁剪图片
         *
         */
        void onCropImage(List<String> uris);

        /**
         * 拍照完成
         * 分两种情况返回有可能有些系统会 拍照返回uri,有些却返回 bitmap
         * <p/>
         *
         * @param uri
         */
        void onCaptureImage(Uri uri);


        void onCaptureVidio(Uri uri);

    }

    /**
     * 增加操作回调
     *
     * @param onCompleteListener
     */
    public void addOnCompleteListener(OnCompleteListener onCompleteListener) {
        mOnCompleteListeners.add(onCompleteListener);
    }

    /**
     * 增加操作回调
     *
     * @param onCompleteListener
     */
    public void removeOnCompleteListener(OnCompleteListener onCompleteListener) {
        mOnCompleteListeners.remove(onCompleteListener);
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode==REQUEST_CODE){
//            if (data==null)
//                return;
//            if(resultCode==PermissionsActivity.PERMISSIONS_DENIED)
//                dismiss();
//            else if(resultCode==PermissionsActivity.PERMISSIONS_GRANTED){
//                if( carmeorfile==1)
//                    toSelectImage(false);
//                else if(carmeorfile==2)
//                    toCapture();
//
//
//            }
//
//        }


        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECT_IMAGE:             //选择照片
//                    Uri selectedUri = data.getData();
                    List<Uri> uris = new ArrayList<>();
//                    if (selectedUri != null) {//单选
//                        uris.add(selectedUri);
//                    } else {
//                        ClipData clipData = data.getClipData();
//
//                        for (int i = 0; i < clipData.getItemCount(); i++) {
//                            ClipData.Item item = clipData.getItemAt(i);
//                            uris.add(item.getUri());
//                        }
//                    }
                    ArrayList<String> photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                    onCorpImage(photos);
                    break;
                case REQUEST_CROP_IMAGE:              //剪裁后的图片
                    Bitmap image = null;
                    //适应两种类型的图片返回
                    if (data.getData() != null) {
                        Uri uri = data.getData();
                        try {
                            image = ImageHelper.getImage(getActivity(), uri);
                            setUri(uri);
                        } catch (Exception e) {
//                            KLog.e(e);
                        }
                    } else if (data.getExtras() != null) {
                        image = data.getExtras().getParcelable("data");
                        Uri ursi=getTempFileUri();
                        FileTools.saveBitmap(image,FileTools.getRealFilePath(mActivity,ursi));
                        setUri(ursi);

                    }
                    break;
                case REQUEST_CAPTURE_IMAGE:                //拍照
//                    onCaptureImage(mCaptureTmpUri);
                    List<String> url=new ArrayList<>();

                    url.add(getRealFilePath(mCaptureTmpUri));
                    setUri(mCaptureTmpUri);
                    onCorpImage(url);
                    break;

                case REQUEST_CAPTURE_VIDIO://录视频
                    Uri  uri = mCaptureTmpUri;   //录制的视频的uri
                    onCaptureVidio(uri);
                    break;
                case  REQUEST_SELECT_VIDIO://选视频
                    Uri selectedUri = data.getData();
                    onCaptureVidio(selectedUri);
                    break;
                default:
                    break;
            }
        }
    }
    private Uri Photouri;
    public  void setUri(Uri uri){

        this.Photouri=uri;
    }
    public  Uri getUri(){
        return  Photouri;
    }

    private String KEY_CAPTURE_TEMP_URI = "capture_temp_uri";

    /**
     * 保存状态，防止activity 被销毁后 拍照的uri消失的问题
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        if (mCaptureTmpUri != null) {
            outState.putString(KEY_CAPTURE_TEMP_URI, mCaptureTmpUri.toString());
        }
    }

    /**
     * 还原状态，防止activity 被销毁后 拍照的uri消失的问题
     *
     * @param savedInstanceState
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(KEY_CAPTURE_TEMP_URI)) {
            String uriString = savedInstanceState.getString(KEY_CAPTURE_TEMP_URI);
            mCaptureTmpUri = Uri.parse(uriString);
        }
    }


    /**
     * 已选中图片后
     *
     * @param uris
     */
    public void onSelectImage(List<Uri> uris) {
        for (OnCompleteListener listener : mOnCompleteListeners) {
            listener.onSelectImage(uris);
        }
    }

    /**
     * 裁剪图片后
     *
     */
    public void onCorpImage(List<String> uris) {
        for (OnCompleteListener listener : mOnCompleteListeners) {
            listener.onCropImage(uris);
        }
    }



    /**
     * 拍照后
     *
     * @param uri
     */
    public void onCaptureImage(Uri uri) {
        for (OnCompleteListener listener : mOnCompleteListeners) {
            listener.onCaptureImage(uri);
        }
    }

    /**
     * 录制视频后
     * **/
    public void onCaptureVidio(Uri uri){
        for (OnCompleteListener listener : mOnCompleteListeners) {
            listener.onCaptureVidio(uri);
        }

    }


    int carmeorfile; //1本地2调取摄像头
    //popwindow onclick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_data_Album://本地
//                carmeorfile=1;
////                toSelectImage(false);
//                if(mPermissionsChecker.getAndroidSDKVersion()>=23){
//                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS))
//                        startPermissionsActivity();
//                    else toSelectImage(false);
//                }else
                    toSelectImage(false);

                dismiss();
                break;
            case R.id.tv_data_Photograph://拍照或录制
//                carmeorfile=2;
////                toCapture();
//                if(mPermissionsChecker.getAndroidSDKVersion()>=23){
//                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS))
//                        startPermissionsActivity();
//                    else toCapture();
//                } else
                isgetpohto=0;
                    toCapture();

                dismiss();
                break;
            case R.id.tv_data_cancel://取消
                dismiss();
                break;
        }
    }

    private Uri getTempFileUri() {
        String fileName = System.currentTimeMillis() + "";///storage/emulated/0/Android/data/com.higotravel/files/Pictures/1466132919646
        File tempFile=null;
        // 判断存储卡是否可以用，可用进行存储
        if (SDCardUtil.isSDCardEnable()) {
            if(isgetpohto==0)
                tempFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
            else if(isgetpohto==1)
                tempFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MOVIES), fileName);
        } else {
            tempFile = new File(getActivity().getFilesDir(), fileName);
        }
        return Uri.fromFile(tempFile);
    }

    /**
     * 调用系统相机拍照
     */
    private void toCapture() {
        // 激活相机
        if(isgetpohto==0){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            mCaptureTmpUri = getTempFileUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCaptureTmpUri);
//            intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, Configuration.ORIENTATION_PORTRAIT);
            mActivity.startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);

        }else {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            mCaptureTmpUri = getTempFileUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCaptureTmpUri);
//            intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, Configuration.ORIENTATION_PORTRAIT);
            mActivity.startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);


           /* Intent intent = new  Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            mCaptureTmpUri = getTempFileUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCaptureTmpUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
//            intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, Configuration.ORIENTATION_PORTRAIT);

            //限制时长 add by huangbowen
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,10);
            mActivity.startActivityForResult(intent, REQUEST_CAPTURE_VIDIO);*///修改
        }

    }


    /**
     * 进入系统相册选择
     *
     * @param isMulitiple 是否多选
     */
    private void toSelectImage(boolean isMulitiple) {
        // 缺少权限时, 进入权限配置页面

        if(isgetpohto==0){

            PhotoPickerIntent intent = new PhotoPickerIntent(mActivity);
            intent.setPhotoCount(photocount);

            intent.setShowCamera(false);
            intent.setShowGif(true);
            mActivity.startActivityForResult(intent, REQUEST_SELECT_IMAGE);
//            onSelectImage(uris);
//            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// ACTION_OPEN_DOCUMENT
//            //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            //intent.setType("image/*");
//            //intent.addCategory(Intent.CATEGORY_OPENABLE);
////            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, isMulitiple);// 不允许多选
//
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            mActivity.startActivityForResult(intent, REQUEST_SELECT_IMAGE);

        }else {
            PhotoPickerIntent intent = new PhotoPickerIntent(mActivity);
            intent.setPhotoCount(photocount);

            intent.setShowCamera(false);
            intent.setShowGif(true);
            mActivity.startActivityForResult(intent, REQUEST_SELECT_IMAGE);
           /* Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// ACTION_OPEN_DOCUMENT
            //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            //intent.setType("image*//*");
            //intent.addCategory(Intent.CATEGORY_OPENABLE);
            //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, isMulitiple);// 不允许多选
            intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,
                    10);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,10);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video*//*");
            mActivity.startActivityForResult(intent, REQUEST_SELECT_VIDIO);*/

        }


    }

    /**
     * 调用系统裁剪功能
     *
     * @param uri
     */

    public void toCropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempFileUri());//6.0必须有这个，与裁剪控件的有关
        intent.setDataAndType(uri, "image/*");
        // 开启裁剪功能
//        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        mActivity.startActivityForResult(intent, REQUEST_CROP_IMAGE);
    }

   


    /**
     *  * Try to return the absolute file path from the given Uri
     *  *
     *  * @param context
     *  * @param uri
     *  * @return the file path or null
     *
     */
    public  String getRealFilePath( final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = mActivity.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}

