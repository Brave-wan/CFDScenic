package com.demo.amusement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.amusement.bean.WriteBlogBean;
import com.demo.demo.myapplication.R;
import com.demo.view.MyRadioButton;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：sonnerly on 2016/9/30 0030 14:57
 */
public class WriteBlogAdapter extends BaseAdapter{
    private Context context;
    private List<WriteBlogBean.DataBean>list;
    boolean point=true;
    public WriteBlogAdapter(Context context, List<WriteBlogBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
       final CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView= LayoutInflater.from(context).inflate(R.layout.writewrite,null);
            cyzMode.myRadioButton= (MyRadioButton) convertView.findViewById(R.id.mrv_radiobutton);
//            cyzMode.iv_pic= (ImageView) convertView.findViewById(R.id.im_MyRadioButton);
            cyzMode.ll_button= (LinearLayout) convertView.findViewById(R.id.ll_myll);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();

        }

        if (list.get(position).getPoint()){
            cyzMode.myRadioButton.setStatus(true);
        }else {
            cyzMode.myRadioButton.setStatus(false);
        }

        cyzMode.myRadioButton.setTextView(list.get(position).getName());
        return convertView;
    }
//    public void
    public void setState(int position){
        for (int i=0;i<list.size();i++){
            if (i==position){
                list.get(i).setPoint(true);
            }else {
                list.get(i).setPoint(false);
            }
        }
        notifyDataSetChanged();
    }
    private class CyzMode {
        LinearLayout ll_button;
//        TextView tv_name;
//        ImageView iv_pic;
        MyRadioButton myRadioButton;
    }
}
