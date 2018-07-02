package com.demo.scenicspot.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：宋玉磊 on 2016/8/5 0005 14:31
 */
public class LookPictureGridviewActivity extends Activity {
    @Bind(R.id.im_left_lookpicture)
    ImageView imLeftLookpicture;
    @Bind(R.id.tv_center_lookpicture)
    TextView tvCenterLookpicture;
    @Bind(R.id.tv_adapter_my_bottomline)
    TextView tvAdapterMyBottomline;
    @Bind(R.id.gv_lookpicture)
    GridView gvLookpicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_picture_gridview);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.im_left_lookpicture)
    public void onClick() {
        finish();
    }
}
