package com.demo.my.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.ImageBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.NoScrollGridView;
import com.demo.view.permission.PermissionsActivity;
import com.demo.view.permission.PermissionsChecker;
import com.demo.view.photo.SelectImagePopupWindow;
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

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 游乐圈--我要评论
 * Created by Administrator on 2016/8/3 0003.
 */
public class Activity_ToEvaluate extends Activity implements SelectImagePopupWindow.OnCompleteListener {

    @Bind(R.id.tv_commit)
    TextView tvCommit;
    @Bind(R.id.iv_imagevew)
    ImageView ivImagevew;
    /* @Bind(R.id.iv_camera)
     ImageView ivCamera;*/
    @Bind(R.id.tv_content)
    EditText tvContent;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.sgv_evaluate)
    NoScrollGridView sgvEvaluate;

    int entrance = -1;//判断从那个地方进入的，需要刷新界面
    @Bind(R.id.iv_evaluate_back)
    ImageView ivEvaluateBack;
    private SelectImagePopupWindow mSelectAvatarPPW;
    List<String> pngString = new ArrayList<>();//图片集
    int photo = 9;
    ImageAdapter ia;//上传图片Adapter
    ImageBean imageBean = new ImageBean();
    int chose = 0;
    PermissionsChecker mPermissionsChecker;
    DialogProgressbar dialogProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_evaluate);
        ButterKnife.bind(this);
        mSelectAvatarPPW = new SelectImagePopupWindow(this);
        mSelectAvatarPPW.addOnCompleteListener(this);
        mPermissionsChecker = new PermissionsChecker(this);
        dialogProgressbar = new DialogProgressbar(Activity_ToEvaluate.this, R.style.AlertDialogStyle);
        ia = new ImageAdapter(this, urlString);
        sgvEvaluate.setAdapter(ia);
        init();

        sgvEvaluate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == (urlString.size())) {
                    if (urlString.size() < 9) {
                        mSelectAvatarPPW.setIsgetpohto(1, photo - (urlString.size()));
                        mSelectAvatarPPW.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                    } else {
                        ToastUtil.show(Activity_ToEvaluate.this, "最多上传9张！");
                    }
                } else if (position >= 0 && position < (urlString.size())) {
                    refreshre();

                }
            }
        });
        ivEvaluateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //刷新Adapter
    public void refreshre() {
        ia.listview();
    }

    private void init() {
        entrance = getIntent().getIntExtra("entrance", -1);
        tvName.setText(getIntent().getStringExtra("name"));
        tvDate.setText("截止时间：" + getIntent().getStringExtra("date").substring(0, 10));
        tvPrice.setText(getIntent().getStringExtra("price"));
        ImageLoader.getInstance().displayImage(getIntent().getStringExtra("image"), ivImagevew);

    }

    @OnClick({R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                if (tvContent.getText().toString().equals("")) {
                    ToastUtil.show(Activity_ToEvaluate.this, "留点想说的话吧！");
                    return;
                }

                myTickets_upload();
                break;

        }
    }


    private void myTickets_upload() {
        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
        dialogProgressbar.show();
        if (pngString.size() == 0) {
            evaluate("");
        } else {
            PostFormBuilder getBuilder = OkHttpUtils.post();
            for (int i = 0; i < pngString.size(); i++) {
                if (i != 9) {
                    getBuilder.addFile("file", "image" + i + ".jpg", new File(pngString.get(i)));
                }
//            else if(i!=4) params.addBodyParameter("file", new File(pathlist.get(i)),"image"+i+".jpg","image/png");
            }
            getBuilder
                    .url(URL.myTickets_upload)
                    .addHeader("Authorization", SpUtil.getString(Activity_ToEvaluate.this, SpName.token, ""))
                    .addParams("type", "0")
//                .addFile("file","",new File(picUrl))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            ToastUtil.show(Activity_ToEvaluate.this, e.getMessage());
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            Log.i("1111", s);
                            try {
                                imageBean = new Gson().fromJson(s, ImageBean.class);
                                if (imageBean.getHeader().getStatus() == 0) {
                                    String picUrl = "";
                                    for (int j = 0; j < imageBean.getData().size(); j++) {
                                        if (j == imageBean.getData().size() - 1)
                                            picUrl += imageBean.getData().get(j);
                                        else picUrl += imageBean.getData().get(j) + ",";
                                    }
                                    evaluate(picUrl);

                                } else {
                                    ToastUtil.show(Activity_ToEvaluate.this, imageBean.getHeader().getMsg());
                                }

                            } catch (Exception e) {
                                ToastUtil.show(Activity_ToEvaluate.this, e.getMessage());
                            }

                        }
                    });
        }
    }


    private void evaluate(String s) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(Activity_ToEvaluate.this, SpName.token, ""));
        params.addQueryStringParameter("linkId", getIntent().getStringExtra("id"));
        params.addQueryStringParameter("comment_type", 1 + "");
        params.addQueryStringParameter("satisfy_state", 1 + "");
        params.addQueryStringParameter("is_travels", 0 + "");
        params.addQueryStringParameter("content_type", 0 + "");
        params.addQueryStringParameter("orderCode", getIntent().getStringExtra("orderCode"));
        params.addQueryStringParameter("content", tvContent.getText().toString());
        params.addQueryStringParameter("haveImg", chose + "");//0没有  1有
        params.addQueryStringParameter("imgUrl", s);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.evaluate, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                if (entrance == 1) {//判断从那个进入的  1我的门票  2我的门票详情
                                    Activity_MyTicket.myTicket = true;
                                } else if (entrance == 2) {
                                    Activity_MyTicket.myTicket = true;
                                    Activity_MyTicketDetails.myTicketDetails = true;
                                }
                                dialogProgressbar.dismiss();
                                finish();
                            } else if (i == 3) {
                                dialogProgressbar.dismiss();
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(Activity_ToEvaluate.this);
                            } else {
                                dialogProgressbar.dismiss();
                                ToastUtil.show(Activity_ToEvaluate.this, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(Activity_ToEvaluate.this, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(Activity_ToEvaluate.this, s);
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
            mSelectAvatarPPW.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onSelectImage(List<Uri> uris) {

        if (uris.size() > 1) {
            for (int i = 0; i < uris.size(); i++) {
                pngString.add(getRealFilePath(Activity_ToEvaluate.this, uris.get(i)) + ".jpg");
            }
        } else
            mSelectAvatarPPW.toCropPhoto(uris.get(0));


    }

    List<String> urlString = new ArrayList<>();

    @Override
    public void onCropImage(List<String> uris) {
        chose = 1;
        for (int i = 0; i < uris.size(); i++) {

            try {
//          Bitmap      image = ImageHelper.getImage(ChatPublishActivity.this, urlist.get(i));
//                Bitmap image = ImageLoader.getInstance().loadImageSync(urlist.get(i));
//                Bitmap image = FileTools.getbitmap1(urlist.get(i));
//                setUri(uri);
//                arrImage.add(0, image);
//                pngString.add(getRealFilePath(Activity_WriteBlogs.this, mSelectAvatarPPW2.getUri()) + ".jpg");
                Log.i("1111", uris.get(i));
                pngString.add(0, uris.get(i));
                for (int index = 0; index < uris.size(); index++) {

                }
            } catch (Exception e) {

//                            KLog.e(e);
            }
        }
        for (int i = 0; i < uris.size(); i++) {
            urlString.add(0, geturi(uris.get(i)));
        }
        ia = new ImageAdapter(Activity_ToEvaluate.this, urlString);
        sgvEvaluate.setAdapter(ia);
//        ia.notifyDataSetChanged();
//        ia = new ImageAdapter(Activity_ToEvaluate.this, pngString,false);
//        sgvEvaluate.setAdapter(ia);
    }


    @Override
    public void onCaptureImage(Uri uri) {
        mSelectAvatarPPW.toCropPhoto(uri);

    }

    @Override
    public void onCaptureVidio(Uri uri) {

    }

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

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private List<String> btpath;

        public ImageAdapter(Context mContext, List<String> btpath) {
            this.mContext = mContext;
            this.btpath = btpath;
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
                convertView = LayoutInflater.from(Activity_ToEvaluate.this).inflate(R.layout.imageitem, null);

                icd.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                icd.delete = (ImageView) convertView.findViewById(R.id.item_shanchu);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                Activity_ToEvaluate.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
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
//            ImageLoader.getInstance().displayImage(btpath.get(btpath.size()-position), icd.image);
                ImageLoader.getInstance().displayImage(btpath.get(position), icd.image);
            }
            if (position == btpath.size()) icd.image.setImageResource(R.mipmap.paizhaoand);

//            if(position<btpath.size()){
            //if (cha) {
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

//        } else {
//            icd.delete.setVisibility(View.GONE);

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
            //}
            return convertView;
        }

        //刷新listview
       /* if (btpath.size()>0){
            ImageLoader.getInstance().displayImage(geturi(btpath.get(position)), icd.image);
        }*/

    //刷新listview
    public void listview() {
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
                uri = mSelectAvatarPPW.getUri();

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
}
