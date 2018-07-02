package com.demo.my.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.my.fragment.SeeImageFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class Activity_SeeImage extends FragmentActivity {


    ArrayList<Fragment> fragmentList = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    String name;

    @Bind(R.id.iv_finish)
    ImageView ivFinish;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.pager)
    ViewPager vpImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_image);
        ButterKnife.bind(this);

        list = getIntent().getStringArrayListExtra("id");
        name=getIntent().getStringExtra("name");

        initFragment();
        tvNum.setText(1+"/"+list.size());
        vpImage.setOverScrollMode(View.OVER_SCROLL_NEVER);
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvNum.setText((position+1)+"/"+list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFragment() {
        for (int i = 0; i < list.size(); i++) {
            SeeImageFragment viewFragment = new SeeImageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Id", list.get(i) + "");
            bundle.putString("name", name + "");
            viewFragment.setArguments(bundle);
            fragmentList.add(viewFragment);
        }
        vpImage.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
