package com.demo.scenicspot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.demo.myapplication.R;
import com.demo.utils.ZxingUtil;
import com.google.zxing.WriterException;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class ZxingFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_image,null);
        ImageView imageView= (ImageView) view.findViewById(R.id.iv_image);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;
        //BitmapUtil.createQRCode(uri, mScreenWidth);
        try {
            imageView.setImageBitmap(ZxingUtil.createQRCode(getArguments().getString("Id"), mScreenWidth));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return view;

    }
}
