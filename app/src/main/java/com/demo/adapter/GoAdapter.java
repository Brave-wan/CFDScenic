package com.demo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.amusement.bean.GoBean;
import com.demo.demo.myapplication.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 景区活动--活动推荐
 * Created by Administrator on 2016/8/2 0002.
 */
public class GoAdapter extends BaseAdapter {
    Context mContext;
    List<GoBean.DataBean.RowsBean> list;

    public GoAdapter(Context mContext, List<GoBean.DataBean.RowsBean> goList) {
        this.mContext = mContext;
        this.list = goList;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_go, null);
//            cyzMode.ll_image= (LinearLayout) convertView.findViewById(R.id.ll_image);
            cyzMode.iv_imagebackgro = (ImageView) convertView.findViewById(R.id.iv_imagebackgro);
            cyzMode.iv_Prompt = (ImageView) convertView.findViewById(R.id.iv_Prompt);
            cyzMode.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            cyzMode.tv_ScenicSpot = (TextView) convertView.findViewById(R.id.tv_ScenicSpot);
            cyzMode.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            cyzMode.tv_Purchase = (TextView) convertView.findViewById(R.id.tv_Purchase);
            cyzMode.tv_End = (TextView) convertView.findViewById(R.id.tv_End);
            cyzMode.tv_present = (TextView) convertView.findViewById(R.id.tv_present);
            cyzMode.tv_primary = (TextView) convertView.findViewById(R.id.tv_primary);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getHead_img(), cyzMode.iv_imagebackgro);
        cyzMode.tv_title.setText(list.get(position).getName());
        cyzMode.tv_ScenicSpot.setText("活动景点：" + list.get(position).getAddress());
        String begin = list.get(position).getStart_valid();
        String[] b = begin.split(" ");
        String[] e = list.get(position).getEnd_valid().split(" ");
        cyzMode.tv_time.setText("活动时间：" + b[0] + "--" + e[0]);
        cyzMode.tv_Purchase.setText(list.get(position).getBuynumber() + "人");//已购人数
        cyzMode.tv_End.setText("(活动截止人数：" + list.get(position).getNumber() + "人)");
        cyzMode.tv_present.setText("￥" + list.get(position).getNew_price() + "");
        cyzMode.tv_primary.setText("￥" + list.get(position).getPrice() + "");
        cyzMode.tv_primary.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //判断是否显示已结束，已售空  0否 1是(图片显示)
        if (list.get(position).getSoldOut() == 1) {
            cyzMode.iv_Prompt.setVisibility(View.VISIBLE);
            cyzMode.iv_Prompt.setImageResource(R.mipmap.ylq_yishoukong);
        } else {
            if (list.get(position).getDateEnd() == 1) {
                cyzMode.iv_Prompt.setVisibility(View.VISIBLE);
                cyzMode.iv_Prompt.setImageResource(R.mipmap.ylq_yijieshu);
            } else {
                cyzMode.iv_Prompt.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    private class CyzMode {
        //        LinearLayout ll_image;
        ImageView iv_imagebackgro;
        ImageView iv_Prompt;
        TextView tv_title;
        TextView tv_ScenicSpot;
        TextView tv_time;
        TextView tv_Purchase;
        TextView tv_End;
        TextView tv_present;
        TextView tv_primary;
    }
}
