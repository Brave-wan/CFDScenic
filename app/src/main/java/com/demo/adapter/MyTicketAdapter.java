package com.demo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_SeeImage;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogApplyRefund;
import com.demo.view.DialogProgressbar;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.demo.my.activity.Activity_MyTicket;
import com.demo.my.activity.Activity_ToEvaluate;
import com.demo.demo.myapplication.R;
import com.demo.my.bean.GetMyTicketsBean;
import com.demo.scenicspot.activity.ChosePaywayActivity;
import com.demo.view.DialogCancelOrder;
import com.demo.view.DialogRefundNo;
import com.demo.view.DialogRefundOk;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class MyTicketAdapter extends BaseAdapter {
    String type ;
    Context mContext;
    List<GetMyTicketsBean.DataBean> list;

    public MyTicketAdapter(String type,Context mContext, List<GetMyTicketsBean.DataBean> list) {
        this.type = type;
        this.mContext = mContext;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_my_ticket, null);
            cyzMode.rl_delete = (RelativeLayout) convertView.findViewById(R.id.rl_delete);
            cyzMode.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            cyzMode.tv_qxdd = (TextView) convertView.findViewById(R.id.tv_qxdd);
            cyzMode.tv_sqtk = (TextView) convertView.findViewById(R.id.tv_sqtk);
            cyzMode.tv_ckewm = (TextView) convertView.findViewById(R.id.tv_ckewm);
            cyzMode.tv_scdd = (TextView) convertView.findViewById(R.id.tv_scdd);
            cyzMode.tv_qpj = (TextView) convertView.findViewById(R.id.tv_qpj);
            cyzMode.tv_ypj = (TextView) convertView.findViewById(R.id.tv_ypj);
            cyzMode.tv_zcgm = (TextView) convertView.findViewById(R.id.tv_zcgm);
            cyzMode.tv_tkz = (TextView) convertView.findViewById(R.id.tv_tkz);
            cyzMode.tv_tkcg = (TextView) convertView.findViewById(R.id.tv_tkcg);
            cyzMode.tv_qzf=(TextView) convertView.findViewById(R.id.tv_qzf);
            cyzMode.tv_tksb=(TextView) convertView.findViewById(R.id.tv_tksb);

            cyzMode.iv_ScenicSpot = (ImageView) convertView.findViewById(R.id.iv_ScenicSpot);
            cyzMode.tv_OrderNumber = (TextView) convertView.findViewById(R.id.tv_OrderNumber);
            cyzMode.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            cyzMode.tv_ScenicSpotName = (TextView) convertView.findViewById(R.id.tv_ScenicSpotName);
            cyzMode.tv_youxiaoqi = (TextView) convertView.findViewById(R.id.tv_youxiaoqi);
            cyzMode.tv_total = (TextView) convertView.findViewById(R.id.tv_total);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }

        setVisition(cyzMode);

        ImageLoader.getInstance().displayImage(list.get(position).getTicketslist().get(0).getHead_img(), cyzMode.iv_ScenicSpot);
        cyzMode.tv_OrderNumber.setText("订单号：" + list.get(position).getTicketslist().get(0).getOrder_code());
        cyzMode.tv_ScenicSpotName.setText(list.get(position).getTicketslist().get(0).getName());
        cyzMode.tv_youxiaoqi.setText("截止日期：" + list.get(position).getTicketslist().get(0).getEnd_valid().substring(0, 10));
        cyzMode.tv_total.setText("￥" + list.get(position).getTicketslist().get(0).getReal_price());

        //状态值判断
        if (list.get(position).getTicketslist().get(0).getOrder_state()==1){
            cyzMode.tv_state.setText("待支付");
            cyzMode.tv_qxdd.setVisibility(View.VISIBLE);
            cyzMode.tv_qzf.setVisibility(View.VISIBLE);
        }else if (list.get(position).getTicketslist().get(0).getOrder_state()==2){
            cyzMode.tv_state.setText("未使用");
            cyzMode.tv_sqtk.setVisibility(View.VISIBLE);
            cyzMode.tv_ckewm.setVisibility(View.VISIBLE);
        }else if (list.get(position).getTicketslist().get(0).getOrder_state()==3){
            cyzMode.tv_state.setText("待评价");
            cyzMode.tv_scdd.setVisibility(View.VISIBLE);
            cyzMode.tv_qpj.setVisibility(View.VISIBLE);
        }else if (list.get(position).getTicketslist().get(0).getOrder_state()==4){
            cyzMode.tv_state.setText("交易完成");
            cyzMode.tv_scdd.setVisibility(View.VISIBLE);
            cyzMode.tv_ypj.setVisibility(View.VISIBLE);
        }else if (list.get(position).getTicketslist().get(0).getOrder_state()==5){
            cyzMode.tv_state.setText("退款中");
            cyzMode.tv_tkz.setVisibility(View.VISIBLE);
        }else if (list.get(position).getTicketslist().get(0).getOrder_state()==6){
            cyzMode.tv_state.setText("申请失败");
            cyzMode.tv_tksb.setVisibility(View.VISIBLE);
        }else if (list.get(position).getTicketslist().get(0).getOrder_state()==7){
            cyzMode.tv_state.setText("申请成功");
            cyzMode.tv_scdd.setVisibility(View.VISIBLE);
            cyzMode.tv_tkcg.setVisibility(View.VISIBLE);
        }else if (list.get(position).getTicketslist().get(0).getOrder_state()==8){
            cyzMode.tv_state.setText("已过期");
            cyzMode.tv_scdd.setVisibility(View.VISIBLE);
            //  cyzMode.tv_zcgm.setVisibility(View.VISIBLE);
        }


        cyzMode.tv_qxdd.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.tv_sqtk.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.tv_qpj.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.tv_qzf.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.tv_zcgm.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.tv_scdd.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.tv_ckewm.setOnClickListener(new SetOnClick(cyzMode, position));
        return convertView;
    }

    private void setVisition(CyzMode cyzMode){
        cyzMode.tv_qxdd.setVisibility(View.GONE);
        cyzMode.tv_qzf.setVisibility(View.GONE);
        cyzMode.tv_sqtk.setVisibility(View.GONE);
        cyzMode.tv_ckewm.setVisibility(View.GONE);
        cyzMode.tv_scdd.setVisibility(View.GONE);
        cyzMode.tv_qpj.setVisibility(View.GONE);
        cyzMode.tv_ypj.setVisibility(View.GONE);
        cyzMode.tv_zcgm.setVisibility(View.GONE);
        cyzMode.tv_tkz.setVisibility(View.GONE);
        cyzMode.tv_tkcg.setVisibility(View.GONE);
        cyzMode.tv_tksb.setVisibility(View.GONE);
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
            Intent intent;
            switch (v.getId()) {
                case R.id.tv_qxdd:
                    DialogCancelOrder dialogCancelOrder = new DialogCancelOrder(mContext, R.style.AlertDialogStyle,position,0);
                    dialogCancelOrder.show();
                    break;
                case R.id.tv_qzf:
                    intent = new Intent(mContext, ChosePaywayActivity.class);
                    intent.putExtra("entrance",1);
                    intent.putExtra("data", list.get(position).getTicketslist().get(0).getOrder_code() + "");
                    intent.putExtra("youxiaoqi", list.get(position).getTicketslist().get(0).getEnd_valid());
                    if (list.get(position).getTicketslist().get(0).getIs_mention()==1){
                        intent.putExtra("invoice", 2);//判断是否需要发票    邮寄
                        intent.putExtra("deliver_fee", list.get(position).getTicketslist().get(0).getDeliver_fee()+"");
                        intent.putExtra("name", list.get(position).getTicketslist().get(0).getName());
                        intent.putExtra("telphone", list.get(position).getTicketslist().get(0).getTelphone());
                        intent.putExtra("address", list.get(position).getTicketslist().get(0).getPlace_address()+list.get(position).getTicketslist().get(0).getDetail_address());
                    }else if (list.get(position).getTicketslist().get(0).getIs_mention()==0){
                        intent.putExtra("invoice", 1);//判断是否需要发票    自提
                    }
                    mContext.startActivity(intent);
                    break;
                case R.id.tv_sqtk:
                    //可以申请
                    DialogApplyRefund dialogApplyRefund=new DialogApplyRefund(mContext, R.style.AlertDialogStyle,position,0);
                    dialogApplyRefund.show();
                   /* //不可以申请
                    DialogRefundNo dialogRefundNo = new Dia logRefundNo(mContext, R.style.AlertDialogStyle);
                    dialogRefundNo.show();*/
                    break;
                case R.id.tv_ckewm:
                    intent=new Intent(mContext, Activity_SeeImage.class);
                    ArrayList<String> image=new ArrayList<>();
                    for (int id=0;id<list.get(position).getTicketslist().size();id++){
                        image.add(list.get(position).getTicketslist().get(id).getId()+"");
                    }
                    intent.putStringArrayListExtra("id",image);
                    intent.putExtra("name",list.get(0).getTicketslist().get(0).getName());
                    mContext.startActivity(intent);
                    break;
                case R.id.tv_scdd:
                    final Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog .getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog .setCanceledOnTouchOutside(false);
                    dialog .show();
                    TextView text = (TextView) dialog .getWindow().findViewById(R.id.tv_content);
                    text.setText("确定删除该订单？");
                    dialog .getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            deleteMyTickets(position);
                            dialog .dismiss();
                        }
                    });
                    dialog .getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog.dismiss();
                        }
                    });

                    break;
                case R.id.tv_qpj:
                    intent = new Intent(mContext, Activity_ToEvaluate.class);
                    intent.putExtra("entrance",1);
                    intent.putExtra("id",list.get(position).getTicketslist().get(0).getVisitorsId()+"");
                    intent.putExtra("orderCode",list.get(position).getTicketslist().get(0).getOrder_code()+"");
                    intent.putExtra("image",list.get(position).getTicketslist().get(0).getHead_img()+"");
                    intent.putExtra("name",list.get(position).getTicketslist().get(0).getName()+"");
                    intent.putExtra("date",list.get(position).getTicketslist().get(0).getEnd_valid()+"");
                    intent.putExtra("price",list.get(position).getTicketslist().get(0).getReal_price()+"");
                    mContext.startActivity(intent);
                    break;
                case R.id.tv_zcgm:
                    intent = new Intent(mContext, ChosePaywayActivity.class);
                    intent.putExtra("entrance",1);
                    intent.putExtra("data", list.get(position).getTicketslist().get(0).getOrder_code() + "");
                    intent.putExtra("youxiaoqi", list.get(position).getTicketslist().get(0).getEnd_valid());
                    if (list.get(position).getTicketslist().get(0).getIs_mention()==1){
                        intent.putExtra("invoice", 2);//判断是否需要发票    邮寄
                        intent.putExtra("deliver_fee", list.get(position).getTicketslist().get(0).getDeliver_fee()+"");
                        intent.putExtra("name", list.get(position).getTicketslist().get(0).getName());
                        intent.putExtra("telphone", list.get(position).getTicketslist().get(0).getTelphone());
                        intent.putExtra("address", list.get(position).getTicketslist().get(0).getPlace_address()+list.get(position).getTicketslist().get(0).getDetail_address());
                    }else if (list.get(position).getTicketslist().get(0).getIs_mention()==0){
                        intent.putExtra("invoice", 1);//判断是否需要发票    自提
                    }
                    mContext.startActivity(intent);
                    break;
            }
        }
    }

    //删除订单
    public void  deleteMyTickets(final int position){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("orderCode",list.get(position).getTicketslist().get(0).getOrder_code()+"");
        params.addQueryStringParameter("orderState",list.get(position).getTicketslist().get(0).getOrder_state()+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.deleteMyTickets, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(mContext,R.style.AlertDialogStyle);
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
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if(i==0){
                                list.remove(position);
                                notifyDataSetChanged();
                                if (list.size()==0){
                                    Activity_MyTicket activity_myTicket= (Activity_MyTicket) mContext;
                                    activity_myTicket.llWudingdan.setVisibility(View.VISIBLE);
                                }
                            }else if( i== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            }else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext,"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
    }

    //申请退款
    public void refund(final int position){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("orderCode",list.get(position).getTicketslist().get(0).getOrder_code()+"");
        params.addQueryStringParameter("endValid",list.get(position).getTicketslist().get(0).getEnd_valid()+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.refund, params,
                new RequestCallBack<String>() {
                    DialogProgressbar dialogProgressbar=new DialogProgressbar(mContext,R.style.AlertDialogStyle);
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
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            JSONObject header=jsonObject.getJSONObject("header");
                            int i=header.getInt("status");
                            if(i==0){
                                DialogRefundOk dialogRefundOk = new DialogRefundOk(mContext, R.style.AlertDialogStyle);
                                dialogRefundOk.show();
                                if (type=="0"){
                                    list.get(position).getTicketslist().get(0).setOrder_state(5);
                                }else {
                                    list.remove(position);
                                }

                                notifyDataSetChanged();
                                if (list.size()==0){
                                    Activity_MyTicket activity_myTicket= (Activity_MyTicket) mContext;
                                    activity_myTicket.llWudingdan.setVisibility(View.VISIBLE);
                                }
                            }else if( i== 3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            }else if (i == 4) {
                                ToastUtil.show(mContext, "不能申请退款");
                            }else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext,"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, s);
                    }
                });
    }


    private class CyzMode {
        RelativeLayout rl_delete;
        ImageView iv_delete;
        TextView tv_qxdd;
        TextView tv_qzf;
        TextView tv_sqtk;
        TextView tv_ckewm;
        TextView tv_scdd;
        TextView tv_qpj;
        TextView tv_ypj;
        TextView tv_zcgm;
        TextView tv_tkz;
        TextView tv_tkcg;
        TextView tv_tksb;


        ImageView iv_ScenicSpot;
        TextView tv_OrderNumber;
        TextView tv_state;
        TextView tv_ScenicSpotName;
        TextView tv_youxiaoqi;
        TextView tv_total;
    }


}
