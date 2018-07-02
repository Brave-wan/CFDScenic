package com.demo.scenicspot.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.AllFragment;
import com.demo.fragment.AmapFragment;
import com.demo.fragment.MainActivity;
import com.demo.fragment.SystemBarTintManager;
import com.demo.scenicspot.fragment.CommentFragment;
import com.demo.scenicspot.fragment.MoreFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： on 2016/8/2 0002 13:59
 */
public class UserCommentActivity extends FragmentActivity {
    @Bind(R.id.tv_usercomment_percent)
    TextView tvUsercommentPercent;
    @Bind(R.id.tv_usercomment_num)
    TextView tvUsercommentNum;
    @Bind(R.id.tv_user_all)
    TextView tvUserAll;
    @Bind(R.id.rl_user_all)
    RelativeLayout rlUserAll;
    @Bind(R.id.tv_user_youtu)
    TextView tvUserYoutu;
    @Bind(R.id.rl_user_youtu)
    RelativeLayout rlUserYoutu;
    @Bind(R.id.frag_user)
    FrameLayout fragUser;

    private Fragment allfragment, amapfragment ,currentFragment;
    Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.science_activity_usercomment);
        ButterKnife.bind(this);
        tvUsercommentPercent.setText(CommentFragment.good);
        tvUsercommentNum.setText(CommentFragment.num);
        initTab();

    }

    @OnClick({R.id.rl_user_all, R.id.rl_user_youtu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_user_all:
                tvUserAll.setBackgroundResource(R.mipmap.kuangcheng);
                tvUserAll.setTextColor(Color.parseColor("#ffffff"));
                tvUserYoutu.setBackgroundResource(R.mipmap.kuanghui);
                tvUserYoutu.setTextColor(this.getResources().getColor(R.color.text_black));
                if (allfragment == null) {
                    allfragment = new AllFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", getIntent().getStringExtra("id")+"");
                    allfragment.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), allfragment);
                break;
            case R.id.rl_user_youtu:
                tvUserAll.setBackgroundResource(R.mipmap.kuanghui);
                tvUserAll.setTextColor(this.getResources().getColor(R.color.text_black));
                tvUserYoutu.setBackgroundResource(R.mipmap.kuangcheng);
                tvUserYoutu.setTextColor(Color.parseColor("#ffffff"));
                if (amapfragment == null) {
                    amapfragment = new AmapFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", getIntent().getStringExtra("id") + "");
                    amapfragment.setArguments(bundle);

                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), amapfragment);
                break;
        }
    }
    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (allfragment == null) {
            allfragment = new AllFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", getIntent().getStringExtra("id")+"");
            allfragment.setArguments(bundle);
        }

        if (!allfragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frag_user, allfragment).commit();

            // 记录当前Fragment
            currentFragment = allfragment;

        }

    }


    /**
     * 添加或�?显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.frag_user, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }
}
