package com.demo.scenicspot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.scenicspot.bean.PressListBean;
import com.demo.demo.myapplication.R;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：宋玉磊 on 2016/8/1 0001 18:35
 */
public class NewsKnowAdapter extends BaseAdapter{
    List<PressListBean.DataBean.RowsBean> rowsBean;
    Context context;


    public NewsKnowAdapter(Context context,List<PressListBean.DataBean.RowsBean> rowsBean) {
        this.rowsBean = rowsBean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return rowsBean.size();
    }

    @Override
    public Object getItem(int position) {
        return rowsBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_newsknow,null);
            cyzMode.iv_adp_newsknow_pic= (ImageView) convertView.findViewById(R.id.iv_adp_newsknow_pic);
            cyzMode.tv_adp_newsknow_title= (TextView) convertView.findViewById(R.id.tv_adp_newsknow_title);
            cyzMode.tv_adp_newsknow_connent= (TextView) convertView.findViewById(R.id.tv_adp_newsknow_connent);
            cyzMode.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            cyzMode.tv_date= (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

       /* BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.display(cyzMode.iv_adp_newsknow_pic, rowsBean.get(position).getHeader_img());*/
        ImageLoader.getInstance().displayImage(rowsBean.get(position).getHeader_img(),cyzMode.iv_adp_newsknow_pic);
        cyzMode.tv_adp_newsknow_title.setText(rowsBean.get(position).getName());
        cyzMode.tv_adp_newsknow_connent.setText(rowsBean.get(position).getNews_describe());
        cyzMode.tv_name.setText(rowsBean.get(position).getCreator());
        String time=rowsBean.get(position).getCreate_time();
        cyzMode.tv_date.setText(time.substring(0,9));

        return convertView;
    }

    private class CyzMode{
        ImageView iv_adp_newsknow_pic;
        TextView tv_adp_newsknow_title;
        TextView tv_adp_newsknow_connent;
        TextView tv_name;
        TextView tv_date;

    }
}
