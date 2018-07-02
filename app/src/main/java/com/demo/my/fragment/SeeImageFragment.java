package com.demo.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.utils.ZxingUtil;
import com.google.zxing.WriterException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class SeeImageFragment extends Fragment {

    @Bind(R.id.tv_Title)
    TextView tvTitle;
    @Bind(R.id.iv_Image)
    ImageView ivImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_see_image, null);
        ButterKnife.bind(this, view);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;
        //BitmapUtil.createQRCode(uri, mScreenWidth);
        try {
            ivImage.setImageBitmap(ZxingUtil.createQRCode(getArguments().getString("Id"), mScreenWidth));
            tvTitle.setText(getArguments().getString("name"));
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
