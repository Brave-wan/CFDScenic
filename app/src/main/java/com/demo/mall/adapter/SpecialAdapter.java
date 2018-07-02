package com.demo.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.activity.ShopActivity;
import com.demo.mall.activity.ShopMessageActivity;
import com.demo.mall.bean.FindFeatureBean;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：sonnerly on 2016/8/6 0006 09:49
 */
public class SpecialAdapter extends BaseAdapter{
    private Context context;
    List<FindFeatureBean.DataBean> rowsBeans;

    public SpecialAdapter(Context context, List<FindFeatureBean.DataBean> rowsBeans) {
        this.context = context;
        this.rowsBeans = rowsBeans;
    }

    @Override
    public int getCount() {
        return rowsBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode = null;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView=LayoutInflater.from(context).inflate(R.layout.mall_adapter_special,null);
            cyzMode.ll_adp_special= (LinearLayout) convertView.findViewById(R.id.ll_adp_special);
            cyzMode.iv_adp_special_img= (ImageView) convertView.findViewById(R.id.iv_adp_special_img);
            cyzMode.tv_adp_special_connent= (TextView) convertView.findViewById(R.id.tv_adp_special_connent);
            cyzMode.tv_adp_special_title= (TextView) convertView.findViewById(R.id.tv_adp_special_title);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(rowsBeans.get(position).getHead_img(),cyzMode.iv_adp_special_img);
        cyzMode.tv_adp_special_title.setText(rowsBeans.get(position).getName());
        cyzMode.tv_adp_special_connent.setText(rowsBeans.get(position).getContent());
        cyzMode.ll_adp_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopActivity.class);
                intent.putExtra("id",rowsBeans.get(position).getId()+"");
                intent.putExtra("name",rowsBeans.get(position).getName());
               intent.putExtra("ImageUrl",rowsBeans.get(position).getHead_img());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class CyzMode{
        ImageView iv_adp_special_img;
        TextView tv_adp_special_title;
        TextView tv_adp_special_connent;
        LinearLayout ll_adp_special;
    }
}
