package com.demo.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.demo.demo.myapplication.R;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.SysUtils;
import com.demo.utils.ToastUtil;
import com.demo.view.DialogAgainSignIn;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {
    private static Fragment myfragment;
    Fragment currentFragment, homefragment, amusementfragment, shoppingfragment, scenicfragment;
    @Bind(R.id.rb_home_fragment)
    RadioButton rbHomeFragment;
    @Bind(R.id.rb_scenic_fragment)
    RadioButton rbScenicFragment;
    @Bind(R.id.rb_amusement_fragment)
    RadioButton rbAmusementFragment;
    @Bind(R.id.rb_shopping_fragment)
    RadioButton rbShoppingFragment;
    @Bind(R.id.rb_my_fragment)
    RadioButton rbMyFragment;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;

    private long exitTime = 0;

    private RadioButton rb_home, rb_amusement, rb_my, rb_shopping, rb_scenic;
    private RadioGroup rbg;

    int a = 0;

    public static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setTranslucentStatus();
        initUi();
        initFragment();

        //购物车跳转
        if (getIntent().getIntExtra("jump", -1) == 3) {
            jump_shopping();
        }

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (index == 1) {
            index = 0;
            jump_home();
        }
    }


    private void initUi() {
        rb_home = (RadioButton) findViewById(R.id.rb_home_fragment);
        rb_amusement = (RadioButton) findViewById(R.id.rb_amusement_fragment);
        rb_my = (RadioButton) findViewById(R.id.rb_my_fragment);
        rb_shopping = (RadioButton) findViewById(R.id.rb_shopping_fragment);
        rb_scenic = (RadioButton) findViewById(R.id.rb_scenic_fragment);
        rbg = (RadioGroup) findViewById(R.id.rg_main);


        rbg.setOnCheckedChangeListener(new MyOnCheckedChange());
    }

    //radioGrop单击事件
    class MyOnCheckedChange implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup arg0, int arg1) {

            if (rb_home.isChecked()) {//首页
                if (homefragment == null) {
                    homefragment = new HomeFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), homefragment);
            } else if (rb_amusement.isChecked()) {//游乐圈
                if (amusementfragment == null) {
                    amusementfragment = new AmusementFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), amusementfragment);
            } else if (rb_my.isChecked()) { //我的
                if (myfragment == null) {
                    myfragment = new MyFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), myfragment);
            } else if (rb_shopping.isChecked()) {   //商城
                if (shoppingfragment == null) {
                    shoppingfragment = new ShoppingFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), shoppingfragment);
            } else if (rb_scenic.isChecked()) {//景区
                if (scenicfragment == null) {
                    scenicfragment = new ScenicFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), scenicfragment);
            }
        }
    }

    /**
     * 初始化显示的第一个fragment
     */
    private void initFragment() {
        if (homefragment == null) {
            homefragment = new HomeFragment();
        }

        if (!homefragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ll_main_fragment, homefragment).commit();
            // 记录当前Fragment
            currentFragment = homefragment;
        }
    }

    //H服务fragment优化
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.ll_main_fragment, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

    //登录去首页
    public void jump_home() {
        rb_home.setChecked(true);
        if (homefragment == null) {
            homefragment = new HomeFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), homefragment);
    }

    //首页点击商城更多跳转商城界面
    public void jump_shopping() {
        rb_shopping.setChecked(true);
        if (shoppingfragment == null) {
            shoppingfragment = new ShoppingFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), shoppingfragment);
    }

    //状态3  异地登录
    public static void state_Three(Context mContext) {
        DialogAgainSignIn dialogAgainSignIn = new DialogAgainSignIn(mContext, R.style.AlertDialogStyle);
        dialogAgainSignIn.show();

        //删除token  修改昵称 性别  头像
        SpUtil.putString(mContext, SpName.token, "");
        SpUtil.putString(mContext, SpName.userName, "去登录");
    }


    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.show(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
