package com.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.my.activity.Activity_EditReceiptAddress;
import com.demo.my.activity.Activity_ReceiptAddress;
import com.demo.my.bean.AddressListBean;
import com.demo.demo.myapplication.R;
import com.demo.view.DialogAgainSignIn;
import com.demo.view.DialogProgressbar;
import com.demo.view.MyRadioButton;
import com.demo.utils.ToastUtil;

import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class ReceiptAddressAdapter extends BaseAdapter {
    private int  state = -1;   //状态值，当前那个设置为默认地址
    Context mContext;
    List<AddressListBean.DataBean> dataArray;

    public ReceiptAddressAdapter(Context mContext, List<AddressListBean.DataBean> dataArray) {
        this.mContext = mContext;
        this.dataArray = dataArray;
    }

    @Override
    public int getCount() {
        return dataArray.size();
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
        final CyzMode cyzMode;
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_receipt_address, null);
            cyzMode.tv_default = (TextView) convertView.findViewById(R.id.tv_morendizhi);
            cyzMode.view_setDefault = (MyRadioButton) convertView.findViewById(R.id.view_setDefault);
            cyzMode.iv_edit= (ImageView) convertView.findViewById(R.id.iv_edit);
            cyzMode.iv_delete= (ImageView) convertView.findViewById(R.id.iv_delete);
            cyzMode.ll_ReceiptAddress= (LinearLayout) convertView.findViewById(R.id.ll_ReceiptAddress);
            cyzMode.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            cyzMode.tv_address= (TextView) convertView.findViewById(R.id.tv_address);
            cyzMode.tv_phone= (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }
        //判断是否有默认选中地址
        if (dataArray.get(position).getIs_default()==0){
            cyzMode.view_setDefault.setStatus(false);
            cyzMode.tv_default.setVisibility(View.INVISIBLE);
        }else {
            cyzMode.view_setDefault.setStatus(true);
            cyzMode.tv_default.setVisibility(View.VISIBLE);
            state=position;
        }


        cyzMode.tv_name.setText(dataArray.get(position).getUser_name());
        cyzMode.tv_phone.setText(dataArray.get(position).getTelphone());
        cyzMode.tv_address.setText("收货地址：" + dataArray.get(position).getPlace_address() + dataArray.get(position).getDetail_address());


        cyzMode.iv_delete.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.iv_edit.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.view_setDefault.setOnClickListener(new SetOnClick(cyzMode, position));
        return convertView;
    }

    private class SetOnClick implements View.OnClickListener {
        CyzMode cyzMode;
        int position;

        public SetOnClick(CyzMode cyzMode, int position) {
            this.cyzMode = cyzMode;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.view_setDefault:
                    if (position==state){
                        return;
                    }
                    defaultAddress(position);
                    break;
                case R.id.iv_edit:
                    Intent intent=new Intent(mContext, Activity_EditReceiptAddress.class);
                    intent.putExtra("data",dataArray.get(position));
                    mContext.startActivity(intent);
                    break;
                case R.id.iv_delete:
                    delete(position);
                    break;
            }
        }
    }

    private void delete(final int position){
        RequestParams params = new RequestParams();
        params.addBodyParameter("id",dataArray.get(position).getAddressId()+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.deleteAddress, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(mContext, R.style.AlertDialogStyle);

                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if (i==0){
                                ToastUtil.show(mContext, "删除成功");
                                dataArray.remove(position);
                                if (dataArray.size()==0){
                                    Activity_ReceiptAddress activity= (Activity_ReceiptAddress) mContext;
                                    activity.llWudingdan.setVisibility(View.VISIBLE);
                                }
                                notifyDataSetChanged();
                            }else if(i == 3){
                                DialogAgainSignIn dialogAgainSignIn=new DialogAgainSignIn(mContext,R.style.AlertDialogStyle);
                                dialogAgainSignIn.show();
                            }/*else if (i==2){
                                ToastUtil.show(mContext,"您有订单正在使用该收货地址\n不能删除");
                            }*/else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
    }

    public void defaultAddress(final int position){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("id", dataArray.get(position).getAddressId() + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);//设置缓存时间
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.POST, URL.defaultAddress, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar = new DialogProgressbar(mContext, R.style.AlertDialogStyle);

                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if (i==0){
                                ToastUtil.show(mContext, "设置成功");
                                dataArray.get(position).setIs_default(1);
                                if (state>=0){
                                    dataArray.get(state).setIs_default(0);
                                    state=position;
                                }
                                notifyDataSetChanged();
                            }else if(i == 3){
                                DialogAgainSignIn dialogAgainSignIn=new DialogAgainSignIn(mContext,R.style.AlertDialogStyle);
                                dialogAgainSignIn.show();
                            }else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
    }

    private class CyzMode {
        TextView tv_default;
        MyRadioButton view_setDefault;
        ImageView iv_edit;
        ImageView iv_delete;
        LinearLayout ll_ReceiptAddress;

        TextView tv_name;
        TextView tv_phone;
        TextView tv_address;
    }



}
