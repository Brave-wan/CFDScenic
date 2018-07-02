package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.demo.demo.myapplication.R;
import com.demo.scenicspot.bean.ScenicSpotListBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：sonnerly on 2016/9/26 0026 18:22
 */
public class HomeThreeAdapter extends BaseAdapter{
    Context context;
    List<ScenicSpotListBean.DataBean> list;
    public HomeThreeAdapter(Context context, List<ScenicSpotListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
            return list.size();

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(context).inflate(R.layout.frag_home_three, null);
            cyzMode.picture = (ImageView) convertView.findViewById(R.id.iv_frag_home_three);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(list.get(position).getHead_img(), cyzMode.picture);

        return convertView;
    }
    private class CyzMode {
        ImageView picture;

    }
}
