package com.demo.my.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.my.fragment.MyPointsFragment;
import com.demo.my.fragment.MyWelletFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的钱包
 * Created by Administrator on 2016/7/25 0025.
 */
public class Activity_MyWallet extends FragmentActivity {
    Fragment myWellet, myPonits, currentFragment;

    @Bind(R.id.my_Wallet_qianbao)
    TextView myWalletQianbao;
    @Bind(R.id.my_Wallet_jifen)
    TextView myWalletJifen;
    @Bind(R.id.my_Wallet_qianbaoxian)
    TextView myWalletQianbaoxian;
    @Bind(R.id.my_Wallet_jifenxian)
    TextView myWalletJifenxian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);



        initTab();
    }


    @OnClick({R.id.my_Wallet_qianbao, R.id.my_Wallet_jifen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_Wallet_qianbao:
                changeView(1);
                if (myWellet == null) {
                    myWellet = new MyWelletFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), myWellet);
                break;
            case R.id.my_Wallet_jifen:
                changeView(2);
                if (myPonits == null) {
                    myPonits = new MyPointsFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), myPonits);
                break;
        }
    }


    //单击选中改变界面
    private void changeView(int i){
        if (i==1){
            //修改图片
            Drawable drawable1 = getResources().getDrawable(R.mipmap.wd_qianbao_qianbaotrue);
            /// 这一步必须要做,否则不会显示.
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            myWalletQianbao.setCompoundDrawables(drawable1, null, null, null);

            Drawable drawable2 = getResources().getDrawable(R.mipmap.wd_qianbao_jifenfalse);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            myWalletJifen.setCompoundDrawables(drawable2, null, null, null);

            myWalletQianbao.setTextColor(Color.parseColor("#FF5400"));
            myWalletJifen.setTextColor(Color.parseColor("#333333"));

            myWalletJifenxian.setVisibility(View.INVISIBLE);
            myWalletQianbaoxian.setVisibility(View.VISIBLE);
        }else if (i==2){
            Drawable drawable3 = getResources().getDrawable(R.mipmap.wd_qianbao_qianbaofalse);
            drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
            myWalletQianbao.setCompoundDrawables(drawable3, null, null, null);
            
            Drawable drawable4 = getResources().getDrawable(R.mipmap.wd_qianbao_jifentrue);
            drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
            myWalletJifen.setCompoundDrawables(drawable4, null, null, null);

            myWalletQianbao.setTextColor(Color.parseColor("#333333"));
            myWalletJifen.setTextColor(Color.parseColor("#FF5400"));

            myWalletQianbaoxian.setVisibility(View.INVISIBLE);
            myWalletJifenxian.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化显示的第一个fragment
     */
    private void initTab() {
        if (myWellet == null) {
            myWellet = new MyWelletFragment();
        }

        if (!myWellet.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ll_my_wallet_fragment, myWellet).commit();
            // 记录当前Fragment
            currentFragment = myWellet;
        }
    }

    //fragment优化
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.ll_my_wallet_fragment, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }
}
