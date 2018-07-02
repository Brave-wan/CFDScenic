package com.demo.amusement.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import com.demo.amusement.adapter.WriteBlogAdapter;
import com.demo.amusement.bean.WriteBlogBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.ImageBean;
import com.demo.mall.bean.NullBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.utils.VideoUtils;
import com.demo.view.DateTimePickViewDialog;
import com.demo.view.DialogExitEdit;
import com.demo.view.DialogProgressbar;
import com.demo.view.DialogSendFail;
import com.demo.view.MyLinearLayoutItem;
import com.demo.view.MyRadioButton;
import com.demo.view.NoScrollGridView;
import com.demo.view.NoScrollViewListView;
import com.demo.view.permission.PermissionsActivity;
import com.demo.view.permission.PermissionsChecker;
import com.demo.view.photo.FileTools;
import com.demo.view.photo.SelectImagePopupWindow2;
import com.google.gson.Gson;
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

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 游乐圈--写游记
 * Created by Administrator on 2016/7/26 0026.
 */
public class Activity_WriteBlogs extends Activity implements SelectImagePopupWindow2.OnCompleteListener {


    @Bind(R.id.tv_close)
    TextView tvClose;
    @Bind(R.id.tv_Release)
    TextView tvRelease;
    @Bind(R.id.et_write_title)
    EditText etWriteTitle;
    @Bind(R.id.et_write_comment)
    EditText etWriteComment;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.view_ll_riqi)
    LinearLayout viewLlRiqi;
    @Bind(R.id.view_ll_youwan)
    MyLinearLayoutItem viewLlYouwan;
    /* @Bind(R.id.view_yugu)
     MyRadioButton viewYugu;
     @Bind(R.id.view_zhongxin)
     MyRadioButton viewZhongxin;
     @Bind(R.id.view_gongyuan)
     MyRadioButton viewGongyuan;
     @Bind(R.id.view_julebu)
     MyRadioButton viewJulebu;
     @Bind(R.id.view_chanyeyuan)
     MyRadioButton viewChanyeyuan;*/
    @Bind(R.id.ll_layout1)
    LinearLayout llLayout1;
    @Bind(R.id.view_ll_youji)
    MyLinearLayoutItem viewLlYouji;
    @Bind(R.id.view_shipin)
    MyRadioButton viewShipin;
    @Bind(R.id.view_jingcai)
    MyRadioButton viewJingcai;
    @Bind(R.id.view_biqu)
    MyRadioButton viewBiqu;
    @Bind(R.id.ll_layout3)
    LinearLayout llLayout3;
    @Bind(R.id.scrollView)
    ScrollView scrollView;


    @Bind(R.id.gv_write_pic)
    NoScrollGridView gvWritePic;
    @Bind(R.id.tv_write_prompt)
    TextView tvWritePrompt;
    @Bind(R.id.slv_write_blogs)
    NoScrollViewListView slvWriteBlogs;

    int record_YouJi = 4;    //记录游记类型选中的第几个
    List<String> pngString = new ArrayList<>();//图片集
    Uri videourl;  //视频地址
    @Bind(R.id.iv_selectVideo)
    ImageView ivSelectVideo;
    @Bind(R.id.iv_selactplay)
    ImageView ivSelactplay;
    @Bind(R.id.rl_select)
    RelativeLayout rlSelect;

    private SelectImagePopupWindow2 mSelectAvatarPPW2;
    int photo = 9;//上传最多图片数量
    ImageAdapter ia;//上传图片Adapter
    int chose;
    WriteBlogAdapter adapter;
    WriteBlogBean writeBlogBeannew = new WriteBlogBean();
    String haha = "";//游玩景点ID
    String vidiopath="";//视频路径
    String palyPlace;//游玩地点
    ImageBean imageBean=new ImageBean();
    ImageBean imageBean1=new ImageBean();
    PermissionsChecker mPermissionsChecker;//android 6.0所需权限
    DialogProgressbar dialogProgressbar;//加载框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_blogs);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialogProgressbar=new DialogProgressbar(Activity_WriteBlogs.this,R.style.AlertDialogStyle);
        mPermissionsChecker = new PermissionsChecker(this);
        mSelectAvatarPPW2 = new SelectImagePopupWindow2(this);
        mSelectAvatarPPW2.addOnCompleteListener(this);
        ia = new ImageAdapter(this, pngString,false);
        gvWritePic.setAdapter(ia);
        initGet();
        initWrite();
    }
    //获取游玩景点列表
    private void initGet() {
        RequestParams params = new RequestParams();
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.circleGetWrite, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            writeBlogBeannew = new Gson().fromJson(responseInfo.result, WriteBlogBean.class);
                            if (writeBlogBeannew.getHeader().getStatus() == 0) {
                                adapter = new WriteBlogAdapter(Activity_WriteBlogs.this, writeBlogBeannew.getData());
                                slvWriteBlogs.setAdapter(adapter);
                            } else if (writeBlogBeannew.getHeader().getStatus() == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_WriteBlogs.this);
                            } else {
                                ToastUtil.show(Activity_WriteBlogs.this, writeBlogBeannew.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_WriteBlogs.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_WriteBlogs.this, e.getMessage());
                    }
                });
    }
int point =1;
    private void initWrite() {
        //所选游玩景点，点击事件
        slvWriteBlogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setState(position);
                haha = writeBlogBeannew.getData().get(position).getId() + "";
                palyPlace = writeBlogBeannew.getData().get(position).getName();

            }
        });
        //上传图片或视频点击事件
        gvWritePic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == (pngString.size())) {
                    if (pngString.size() < 9) {
                        mSelectAvatarPPW2.setIsgetpohto(0, photo - (pngString.size()));
                        mSelectAvatarPPW2.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                    } else {
                        ToastUtil.show(Activity_WriteBlogs.this, "最多上传9张！");
                    }
                } else if (position >= 0 && position < (pngString.size())) {//删除图片后的刷新
                    if (point == 1) {
                        refreshre(true);
                        point = 2;
                    } else {
                        refreshre(false);
                        point = 1;
                    }
                }
            }
        });


    }
    //刷新Adapter
    public  void refreshre(boolean chacha) {
        ia.listview(chacha);
    }
    //游玩景点，游记类型点击事件 , R.id.iv_write_camera  R.id.view_yugu, R.id.view_zhongxin, R.id.view_gongyuan, R.id.view_julebu,R.id.view_chanyeyuan,
    @OnClick({R.id.view_ll_youwan, R.id.view_ll_youji, R.id.view_shipin, R.id.view_jingcai, R.id.view_biqu, R.id.tv_close, R.id.view_ll_riqi,
            R.id.tv_Release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_ll_youwan://游玩景点
                if (llLayout1.getVisibility() == View.VISIBLE) {
                    viewLlYouwan.setRightImage(R.mipmap.xiangyoujiantou);
                    llLayout1.setVisibility(View.GONE);
                } else {
                    viewLlYouwan.setRightImage(R.mipmap.xiangxiajiantou);
                    llLayout1.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.view_ll_youji://游记类型
                if (llLayout3.getVisibility() == View.VISIBLE) {
                    viewLlYouji.setRightImage(R.mipmap.xiangyoujiantou);
                    llLayout3.setVisibility(View.GONE);
                } else {
                    viewLlYouji.setRightImage(R.mipmap.xiangxiajiantou);
                    llLayout3.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.view_shipin:
                setLeiView();
                viewShipin.setStatus(true);
                record_YouJi = 0;
                break;
            case R.id.view_jingcai:
                setLeiView();
                viewJingcai.setStatus(true);
                record_YouJi = 1;
                break;
            case R.id.view_biqu:
                setLeiView();
                viewBiqu.setStatus(true);
                record_YouJi = 2;
                break;
            case R.id.view_ll_riqi://游玩日期
                Calendar c = Calendar.getInstance();
                DateTimePickViewDialog timepick = new DateTimePickViewDialog(Activity_WriteBlogs.this, tvTime, "请选择时间", c, "-");
                timepick.show();
                break;
            case R.id.tv_Release://提交
                if (etWriteTitle.getText().toString().equals("")) {
                    ToastUtil.show(Activity_WriteBlogs.this, "请填写标题");
                    return;
                }
                if (etWriteComment.getText().toString().equals("")) {
                    ToastUtil.show(Activity_WriteBlogs.this, "请填写内容");
                    return;
                }
                if (vidiopath.equals("")&&pngString.size()==0){
                    ToastUtil.show(Activity_WriteBlogs.this, "请选择上传是视频或图片");
                    return;
                }
                if (tvTime.getText().toString().equals("")) {
                    ToastUtil.show(Activity_WriteBlogs.this, "请选择游玩日期");
                    return;
                }
                if (haha.equals("")) {
                    ToastUtil.show(Activity_WriteBlogs.this, "请选择游玩景点");
                    return;
                }
                if (record_YouJi == 4) {
                    ToastUtil.show(Activity_WriteBlogs.this, "请选择游记类型");
                    return;
                }

                upLoad();


                break;
            case R.id.tv_close://退出编辑
                DialogExitEdit dialogExitEdit = new DialogExitEdit(Activity_WriteBlogs.this, R.style.AlertDialogStyle);
                dialogExitEdit.show();
                break;

        }
    }


    private void upLoad() {

            dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
            dialogProgressbar.show();
        if(pngString.size()==0){//视频
            PostFormBuilder getBuilder = OkHttpUtils.post();
            getBuilder
                    .url(URL.myTickets_upload)
                    .addHeader("Authorization", SpUtil.getString(Activity_WriteBlogs.this, SpName.token, ""))
                    .addParams("type", "0")
                    .addFile("file","",new File(vidiopath))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            ToastUtil.show(Activity_WriteBlogs.this, e.getMessage());
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            Log.i("1111",s);
                            try {
                                imageBean1  = new Gson().fromJson(s, ImageBean.class);//视频
                                if(imageBean1.getHeader().getStatus()==0){
                                    loadFrist();
                                }else{
                                    ToastUtil.show(Activity_WriteBlogs.this, imageBean1.getHeader().getMsg());
                                }

                            } catch (Exception e) {
                                ToastUtil.show(Activity_WriteBlogs.this, e.getMessage());
                            }

                        }
                    });


        }else {
            PostFormBuilder getBuilder = OkHttpUtils.post();
            for (int i = 0; i < pngString.size(); i++) {
                if (i != 9) {
                    getBuilder.addFile("file", "image" + i + ".jpg", new File(pngString.get(i)));
                    Log.e("hhhhh",new File(pngString.get(i)).toString());
                }
//            else if(i!=4) params.addBodyParameter("file", new File(pathlist.get(i)),"image"+i+".jpg","image/png");
            }
        getBuilder
                .url(URL.myTickets_upload)
                .addHeader("Authorization", SpUtil.getString(Activity_WriteBlogs.this, SpName.token, ""))
                .addParams("type", "0")
//                .addFile("file","",new File(picUrl))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtil.show(Activity_WriteBlogs.this, e.getMessage());
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        Log.i("1111",s);
                        try {
                            imageBean  = new Gson().fromJson(s, ImageBean.class);
                            if(imageBean.getHeader().getStatus()==0){
                                 String picUrl = "";
                                for (int j = 0; j < imageBean.getData().size(); j++) {
                                    if (j == imageBean.getData().size() - 1)
                                        picUrl += imageBean.getData().get(j);
                                    else picUrl += imageBean.getData().get(j) + ",";
                                }

                                haha(picUrl);

                            }else{
                                ToastUtil.show(Activity_WriteBlogs.this, imageBean.getHeader().getMsg());
                            }

                        } catch (Exception e) {
                            ToastUtil.show(Activity_WriteBlogs.this, e.getMessage());
                        }

                    }
                });

        }



     }
    //获取视频的第一张图片
    private void loadFrist() {
        PostFormBuilder getBuilder = OkHttpUtils.post();
        getBuilder
                .url(URL.myTickets_upload)
                .addHeader("Authorization", SpUtil.getString(Activity_WriteBlogs.this, SpName.token, ""))
                .addParams("type", "0")
                .addFile("file","",new File(firstPng.get(0)))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtil.show(Activity_WriteBlogs.this, e.getMessage());
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        Log.i("1111",s);
                        try {
                            imageBean  = new Gson().fromJson(s, ImageBean.class);//第一帧图片
                            if(imageBean.getHeader().getStatus()==0){
                                haha(imageBean.getData().get(0));
                            }else{
                                ToastUtil.show(Activity_WriteBlogs.this, imageBean.getHeader().getMsg());
                            }

                        } catch (Exception e) {
                            ToastUtil.show(Activity_WriteBlogs.this, e.getMessage());
                        }

                    }
                });



    }

    private void haha(String s){
    Date date = new Date();
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = format.format(date);
    RequestParams params = new RequestParams();
    params.addHeader("Authorization", SpUtil.getString(Activity_WriteBlogs.this, SpName.token, ""));
    params.addQueryStringParameter("title", etWriteTitle.getText().toString());
    params.addQueryStringParameter("content", etWriteComment.getText().toString());
    if(pngString.size()==0){
        params.addQueryStringParameter("travelVideo",imageBean1.getData().get(0));
        params.addQueryStringParameter("travelImg",s);
    }else{
        params.addQueryStringParameter("images", s);
    }
    params.addQueryStringParameter("travelId", haha + "");
    params.addQueryStringParameter("travelDate", tvTime.getText().toString());
    params.addQueryStringParameter("travelName",palyPlace);//游玩景点
    params.addQueryStringParameter("createdate", time);
    params.addQueryStringParameter("type", record_YouJi + "");// 0  1  2
    params.addQueryStringParameter("travelType", chose + "");//视频1 图片2

    HttpUtils http = new HttpUtils();
    http.configResponseTextCharset(HTTP.UTF_8);
    http.configCurrentHttpCacheExpiry(0 * 1000);
    http.send(HttpRequest.HttpMethod.GET, URL.circleWrite, params,
            new RequestCallBack<String>() {
//                DialogProgressbar dialogProgressbar1=new DialogProgressbar(Activity_WriteBlogs.this,R.style.AlertDialogStyle);
//                @Override
//                public void onStart() {
//                    super.onStart();
//                    dialogProgressbar1.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
//                    dialogProgressbar1.show();
//                }
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    try {
                        NullBean nullBean = new Gson().fromJson(responseInfo.result, NullBean.class);
                        if (nullBean.getHeader().getStatus() == 0) {
                            finish();
                            dialogProgressbar.dismiss();
                        } else {
                            dialogProgressbar.dismiss();
                            //发送失败
                            DialogSendFail dialogSendFail = new DialogSendFail(Activity_WriteBlogs.this, R.style.AlertDialogStyle);
                            dialogSendFail.show();
                        }
                    } catch (Exception e) {
                        ToastUtil.show(Activity_WriteBlogs.this, e.getMessage());
                    }

                }

                @Override
                public void onFailure(HttpException e, String s) {
                    ToastUtil.show(Activity_WriteBlogs.this, s);
                }
            });
}
    //图片uri转路径
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
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

    @Override
    public void onSelectImage(List<Uri> uris) {
        if (uris.size() > 1) {
            for (int i = 0; i < uris.size(); i++) {
                pngString.add(getRealFilePath(Activity_WriteBlogs.this, uris.get(i)) + ".jpg");
            }
        } else
            mSelectAvatarPPW2.toCropPhoto(uris.get(0));


    }

    @Override
    public void onCorpImage(List<String> uris) {//选择图片的回调方法
        rlSelect.setVisibility(View.GONE);
        gvWritePic.setVisibility(View.VISIBLE);
        viewShipin.setVisibility(View.GONE);
        chose=2;
        for (int i = 0; i < uris.size(); i++) {

            try {
//          Bitmap      image = ImageHelper.getImage(ChatPublishActivity.this, urlist.get(i));
//                Bitmap image = ImageLoader.getInstance().loadImageSync(urlist.get(i));
//                Bitmap image = FileTools.getbitmap1(urlist.get(i));
//                setUri(uri);
//                arrImage.add(0, image);
//                pngString.add(getRealFilePath(Activity_WriteBlogs.this, mSelectAvatarPPW2.getUri()) + ".jpg");
                pngString.add(0, uris.get(i));
            } catch (Exception e) {

//                            KLog.e(e);
            }
        }
//        ia.notifyDataSetChanged();
        ia = new ImageAdapter(Activity_WriteBlogs.this, pngString,false);
        gvWritePic.setAdapter(ia);
//        arrImage.add(0, bitmap);
//        ia.notifyDataSetChanged();
//        pngString.add(getRealFilePath(ChatPublishActivity.this, mSelectAvatarPPW.getUri()) + ".jpg");
    }

    @Override
    public void onCaptureImage(Uri uri) {
        mSelectAvatarPPW2.toCropPhoto(uri);

    }
    List<String> firstPng=new ArrayList<>();
    @Override
    public void onCaptureVidio(final Uri uri) {//视频回调
        videourl = uri;
        vidiopath = getRealFilePath(Activity_WriteBlogs.this, uri);
      /*  if (VideoUtils.getsplength1(vidiopath) > 30 * 1000) {
            videourl = null;
            vidiopath = null;
            ToastUtil.show(Activity_WriteBlogs.this, "您好，选择的视频不能超过30秒");
//            final Dialog dialog1 = new Dialog(ChatPublishActivity.this, R.style.AlertDialogStyle);
//            dialog1.getWindow().setContentView(R.layout.dialog_caiguo);
//            dialog1.setCanceledOnTouchOutside(false);
//            dialog1.show();
//            TextView text = (TextView) dialog1.getWindow().findViewById(R.id.caiguo_text);
//            text.setText("您好，选择的视频不能超过30秒");
//            dialog1.getWindow().findViewById(R.id.ll_caiguo).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    ChatPublishActivity.this.finish();
//                    dialog1.dismiss();
//                }
//            });


        } else */

        if (new File(vidiopath).length() > 1024 * 1024 * 20) {

            videourl = null;
            vidiopath = null;
            ToastUtil.show(Activity_WriteBlogs.this, "您好，选择的视频不能大于20M");
//            final Dialog dialog1 = new Dialog(ChatPublishActivity.this, R.style.AlertDialogStyle);
//            dialog1.getWindow().setContentView(R.layout.dialog_caiguo);
//            dialog1.setCanceledOnTouchOutside(false);
//            dialog1.show();
//            TextView text = (TextView) dialog1.getWindow().findViewById(R.id.caiguo_text);
//            text.setText("您好，选择的视频不能大于20M");
//            dialog1.getWindow().findViewById(R.id.ll_caiguo).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    ChatPublishActivity.this.finish();
//                    dialog1.dismiss();
//                }
//            });
        } else {

            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(getRealFilePath(Activity_WriteBlogs.this, uri));

            Bitmap bitmap = media.getFrameAtTime();
            rlSelect.setVisibility(View.VISIBLE);
            gvWritePic.setVisibility(View.GONE);
            ivSelectVideo.setImageBitmap(bitmap);
            ivSelactplay.setVisibility(View.VISIBLE);
            viewShipin.setVisibility(View.VISIBLE);
            viewJingcai.setVisibility(View.GONE);
            viewBiqu.setVisibility(View.GONE);
            chose=1;
//        vidioString.add(getRealFilePath(Activity_WriteBlogs.this, uri));
            firstPng.add(FileTools.saveMyBitmap1(bitmap, "Frame"));
//            type = 1;
        }


        rlSelect.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent i=new Intent(Activity_WriteBlogs.this, MyPlay.class);
               Bundle bd=new Bundle();
               bd.putString("uri",uri+"");
               i.putExtras(bd);
               startActivity(i);

           }
       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED)
                finish();
        } else
        mSelectAvatarPPW2.onActivityResult(requestCode, resultCode, data);
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

   /* private void setYouView() {
        viewYugu.setStatus(false);
        viewZhongxin.setStatus(false);
        viewGongyuan.setStatus(false);
        viewJulebu.setStatus(false);
        viewChanyeyuan.setStatus(false);
    }*/

    private void setLeiView() {
        viewShipin.setStatus(false);
        viewJingcai.setStatus(false);
        viewBiqu.setStatus(false);
    }

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private List<String> btpath;
        boolean cha=false;
        public ImageAdapter(Context mContext, List<String> btpath,boolean cha) {
            this.mContext = mContext;
            this.btpath = btpath;
            this.cha=cha;
//            this.btpath=pngString;
        }

        @Override
        public int getCount() {
            return btpath.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ItemChatPublish icd;
            if (convertView == null) {
                icd = new ItemChatPublish();
                //获得组件，实例化组件
                convertView = LayoutInflater.from(Activity_WriteBlogs.this).inflate(R.layout.imageitem, null);

                icd.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                icd.delete = (ImageView) convertView.findViewById(R.id.item_shanchu);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                Activity_WriteBlogs.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int high = displayMetrics.widthPixels / 5;

                ViewGroup.LayoutParams params = icd.image.getLayoutParams();
                params.width = high;
                params.height = high;
                icd.image.setLayoutParams(params);
                convertView.setTag(icd);
            } else {
                icd = (ItemChatPublish) convertView.getTag();

            }
            if (position < btpath.size()) {
                ImageLoader.getInstance().displayImage(geturi(btpath.get(position)), icd.image);
            }
            if (position == btpath.size()) icd.image.setImageResource(R.mipmap.paizhaoand);
//            if(position<btpath.size()){
//                if(cha){
                    if (position != btpath.size() && position != btpath.size() + 1) {
                        icd.delete.setVisibility(View.VISIBLE);
                        icd.delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btpath.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    } else icd.delete.setVisibility(View.GONE);

//                } else {
//                    icd.delete.setVisibility(View.GONE);

                   /* icd.delete.setVisibility(View.VISIBLE);
                    icd.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btpath.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                }else{
                    icd.delete.setVisibility(View.GONE);*/
//               }
//            }
            return convertView;
        }
        //刷新listview
        public void listview(boolean cha){
            this.cha=cha;
            notifyDataSetChanged();
        }
        public class ItemChatPublish {
            public ImageView image;
            public ImageView delete;
        }


    }

    public String geturi(String path) {
        Uri uri = null;
        if (path != null) {
            path = Uri.decode(path);
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
                uri = mSelectAvatarPPW2.getUri();

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            DialogExitEdit dialogExitEdit = new DialogExitEdit(Activity_WriteBlogs.this, R.style.AlertDialogStyle);
            dialogExitEdit.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
