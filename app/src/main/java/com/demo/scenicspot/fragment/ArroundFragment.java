package com.demo.scenicspot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.demo.demo.myapplication.R;
import com.demo.scenicspot.activity.ArroundActivity;
import com.demo.scenicspot.adapter.ArroundAdapter;
import com.demo.view.NoScrollGridView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：宋玉磊 on 2016/8/1 0001 14:14
 */
public class ArroundFragment extends Fragment {
    @Bind(R.id.sgv_frag_arround)
    NoScrollGridView sgvFragArround;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.science_fragment_arround_gridview, container,
                false);
        ButterKnife.bind(this, view);
        init();
        return view;

    }

    private void init() {
        sgvFragArround.setAdapter(new ArroundAdapter(getActivity()));
        sgvFragArround.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), ArroundActivity.class);
                intent.putExtra("position",position+1);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
