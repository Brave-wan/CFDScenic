package com.demo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.fragment.MainActivity;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.demo.my.activity.Activity_MyCollection;
import com.demo.demo.myapplication.R;
import com.demo.my.bean.SelectBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class MyCollectionAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Boolean> list;
    boolean state=false;
    Activity_MyCollection activity_myCollection;
    public int num=0;  //记录选中的个数
    List<SelectBean.DataBean.RowsBean> bean;

    public MyCollectionAdapter(Context mContext, List<SelectBean.DataBean.RowsBean> bean) {
        this.mContext = mContext;
        this.bean = bean;
        activity_myCollection= (Activity_MyCollection) mContext;
    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_my_collection,null);
            cyzMode.iv_delete= (ImageView) convertView.findViewById(R.id.iv_delete);

            cyzMode.iv_Image= (ImageView) convertView.findViewById(R.id.iv_Image);
            cyzMode.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            cyzMode.tv_brand= (TextView) convertView.findViewById(R.id.tv_brand);
            cyzMode.tv_primary= (TextView) convertView.findViewById(R.id.tv_primary);
            cyzMode.tv_present= (TextView) convertView.findViewById(R.id.tv_present);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        if (state){
            cyzMode.iv_delete.setVisibility(View.VISIBLE);
        }else{
            cyzMode.iv_delete.setVisibility(View.GONE);
        }

        //判读对勾是否选中
        if (list!=null){
            if (list.get(position)){
                cyzMode.iv_delete.setImageResource(R.mipmap.yuan_duigou_true);
            }else {
                cyzMode.iv_delete.setImageResource(R.mipmap.yuan_duigou_false);
            }
        }

        ImageLoader.getInstance().displayImage(bean.get(position).getDescribe_img(), cyzMode.iv_Image);
        cyzMode.tv_name.setText(bean.get(position).getGoods_name());
        cyzMode.tv_brand.setText(bean.get(position).getGoods_describe());
        cyzMode.tv_primary.setText("￥"+bean.get(position).getNew_price());
        cyzMode.tv_present.setText("￥"+bean.get(position).getPrice());
        cyzMode.tv_present.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );

        return convertView;
    }

    private class  CyzMode{
        ImageView iv_delete;
        ImageView iv_Image;
        TextView tv_name;
        TextView tv_brand;
        TextView tv_primary;
        TextView tv_present;
    }

    public void initList(){
        list=new ArrayList<>();
        num=0;
        for (int i=0;i<bean.size();i++){
            Boolean b=new Boolean(false);
            list.add(b);
        }
    }

    //设置选中项
    public void setImageView(int position){
        if (list.get(position)){
            list.set(position, false);
            num=num-1;
            activity_myCollection.tvDelete.setText("删除("+num+")");
        }else{
            list.set(position,true);
            num=num+1;
            activity_myCollection.tvDelete.setText("删除("+num+")");
        }
        notifyDataSetChanged();
    }
    //设置显示对勾
    public void setVisibility(boolean b){
        state=b;
        notifyDataSetChanged();
    }

    //删除选中项
    public void deleteAll(){
        StringBuffer stringBuffer=new StringBuffer();
        for (int i=0;i<list.size();i++){
            if (list.get(i)){
                stringBuffer.append(bean.get(i).getId()+",");
            }
        }
        delete(stringBuffer.toString().substring(0,stringBuffer.toString().length()-1));
    }

    private void delete(String id){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("ids",id+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.collect_Delete, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if(i==0){
                                Activity_MyCollection activity_myCollection= (Activity_MyCollection) mContext;
                                activity_myCollection.list.clear();
                                activity_myCollection.mPage=1;
                                activity_myCollection.select();
                                activity_myCollection.initView();

                            }else if( i== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            }else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext,"解析数据错误");
                            ToastUtil.show(mContext,e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
    }
}
