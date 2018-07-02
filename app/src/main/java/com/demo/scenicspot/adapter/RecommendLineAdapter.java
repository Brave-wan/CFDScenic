package com.demo.scenicspot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.scenicspot.bean.AnviRecommendBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/12/12 0012 09:45
 */
public class RecommendLineAdapter extends BaseAdapter{
    Context context;
    List<AnviRecommendBean.DataBean.RowsBean> list;


    public RecommendLineAdapter(Context context,List<AnviRecommendBean.DataBean.RowsBean> list){
        this.context=context;
        this.list=list;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode = null;
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_line, null);
            cyzMode.reline_title = (TextView) convertView.findViewById(R.id.tv_adp_reline_title);
            cyzMode.reline= (TextView) convertView.findViewById(R.id.tv_adp_reline);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }
        cyzMode.reline_title.setText(list.get(position).getGroup_name());
        if(list.get(position).getListData().size()>0){
            List<String> listString=new ArrayList<>();
            for(int i=0;i<list.get(position).getListData().size();i++){
                String item=list.get(position).getListData().get(i).getName();
                if(list.get(position).getListData().get(i).getType()==1){
                    listString.add(item);
                    cyzMode.reline.setText(listToString(listString));
                }
            }

        }

        return convertView;
    }
    public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        boolean flag=false;
        StringBuilder result=new StringBuilder();
        for (String string : stringList) {
            if (flag) {
                result.append("-");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
    private class CyzMode{
        TextView reline_title;
        TextView reline;
    }
}
