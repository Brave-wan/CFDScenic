package com.demo.amusement.activity;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.amusement.fragment.EvaluateFragment;
import com.demo.amusement.fragment.ReleaseFragment;
import com.demo.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 点击头像进入他人详情
 * <p/>
 * Created by Administrator on 2016/8/2 0002.
 */
public class Activity_OtherInformation extends FragmentActivity {

    @Bind(R.id.tv_fabu)
    TextView tvFabu;
    @Bind(R.id.tv_pingjia)
    TextView tvPingjia;

    @Bind(R.id.im_return)
    ImageView imReturn;
    @Bind(R.id.fl_fragment)
    FrameLayout flFragment;
    @Bind(R.id.riv_inform_headimg)
    RoundImageView rivInformHeadimg;
    @Bind(R.id.tv_inform_nickname)
    TextView tvInformNickname;
    Fragment currentFragment, releaseFragment, evaluateFragment;

    public static long uu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_information);
        ButterKnife.bind(this);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        uu=getIntent().getLongExtra("id",0);
        ImageLoader.getInstance().displayImage(getIntent().getStringExtra("head_img"), rivInformHeadimg);
        tvInformNickname.setText(getIntent().getStringExtra("nickname"));
        initFragment();
        tvFabu.setOnClickListener(new SetOnClick());
        tvPingjia.setOnClickListener(new SetOnClick());
        imReturn.setOnClickListener(new SetOnClick());


        imReturn.setFocusable(true);
        imReturn.setFocusableInTouchMode(true);
        imReturn.requestFocus();

        //svOther.smoothScrollTo(0, 0);
        //svOther.scrollTo(0, 0);
    }


    private class SetOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_fabu://发布的游记
                    tvFabu.setTextColor(Color.parseColor("#FF5400"));
                    tvPingjia.setTextColor(Color.parseColor("#666666"));
                    if (releaseFragment == null) {
                        releaseFragment = new ReleaseFragment();
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), releaseFragment);
                    break;
                case R.id.tv_pingjia://评论的景区
                    tvPingjia.setTextColor(Color.parseColor("#FF5400"));
                    tvFabu.setTextColor(Color.parseColor("#666666"));
                    if (evaluateFragment == null) {
                        evaluateFragment = new EvaluateFragment();
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), evaluateFragment);
                    break;
                case R.id.im_return:
                    finish();
                    break;
            }
        }
    }

    /**
     * 初始化显示的第一个fragment
     */
    private void initFragment() {
        if (releaseFragment == null) {
            releaseFragment = new ReleaseFragment();
        }

        if (!releaseFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, releaseFragment).commit();
            // 记录当前Fragment
            currentFragment = releaseFragment;
        }
    }

    //H服务fragment优化
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.fl_fragment, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

}
