package com.demo.my.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.bean.AlipayBean;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_ApplyRefund;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_OrderDetailsSp;
import com.demo.my.activity.Activity_OrderPaymentOrder;
import com.demo.my.bean.SpFindOrderBean;
import com.demo.my.fragment.MyOrderSpFragment;
import com.demo.my.fragment.MyOrderSpFragment2;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogMyCancelOrder;
import com.demo.view.DialogMyOrderCollect;
import com.demo.view.DialogMyOrderComplete;
import com.demo.view.DialogMyOrderDelete;
import com.demo.view.DialogSeafood;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24 0024.
 */
public class MyOrderSpAdapterLv extends BaseAdapter {
    Context mContext;
    List<List<SpFindOrderBean.DataBean.OrderListBean.RowsBean>> list;
    int gPosition;//父类的下标
    int mState=-1;
    AlipayBean alipayBean=new AlipayBean();
    public MyOrderSpAdapterLv(Context mContext, List<List<SpFindOrderBean.DataBean.OrderListBean.RowsBean>> list,int mState) {
        this.mContext = mContext;
        this.list = list;
        this.mState=mState;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_my_order_sp, null);
            cyzMode.iv_business = (ImageView) convertView.findViewById(R.id.iv_business);
            cyzMode.tv_businessName = (TextView) convertView.findViewById(R.id.tv_businessName);
            cyzMode.ll_context= (LinearLayout) convertView.findViewById(R.id.ll_context);
            cyzMode.tv_code = (TextView) convertView.findViewById(R.id.tv_code);
            cyzMode.tv_quxiao= (TextView) convertView.findViewById(R.id.tv_quxiao);
            cyzMode.tv_quzhifu= (TextView) convertView.findViewById(R.id.tv_quzhifu);
            cyzMode.tv_querenwancheng= (TextView) convertView.findViewById(R.id.tv_querenwancheng);
            cyzMode.tv_jiaoyiwancheng= (TextView) convertView.findViewById(R.id.tv_jiaoyiwancheng);
            cyzMode.tv_shenqing= (TextView) convertView.findViewById(R.id.tv_shenqing);
            cyzMode.tv_querenshouhuo= (TextView) convertView.findViewById(R.id.tv_querenshouhuo);
            cyzMode.tv_fahuozhong= (TextView) convertView.findViewById(R.id.tv_fahuozhong);
            cyzMode.tv_shanchu= (TextView) convertView.findViewById(R.id.tv_shanchu);

            cyzMode.lv_listView= (ListView) convertView.findViewById(R.id.lv_listView);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }
        gPosition=position;
        initView(cyzMode);
        getAlipay(position);
        cyzMode.tv_businessName.setText(list.get(position).get(0).getShopName());
        cyzMode.tv_code.setText(list.get(position).get(0).getOrder_code());
        ImageLoader.getInstance().displayImage(list.get(position).get(0).getHead_img(), cyzMode.iv_business);

        //状态值判断
        if (list.get(position).get(0).getOrder_state()==1){
            cyzMode.tv_quzhifu.setVisibility(View.VISIBLE);
            cyzMode.tv_quxiao.setVisibility(View.VISIBLE);
        }else if (list.get(position).get(0).getOrder_state()==2){
            cyzMode.tv_fahuozhong.setVisibility(View.VISIBLE);
        }else if (list.get(position).get(0).getOrder_state()==3){
            cyzMode.tv_querenshouhuo.setVisibility(View.VISIBLE);
        }else if (list.get(position).get(0).getOrder_state()==4){
            cyzMode.tv_shenqing.setVisibility(View.VISIBLE);
            cyzMode.tv_querenwancheng.setVisibility(View.VISIBLE);
        }else if (list.get(position).get(0).getOrder_state()==5){
            cyzMode.tv_jiaoyiwancheng.setVisibility(View.VISIBLE);
            cyzMode.tv_shanchu.setVisibility(View.VISIBLE);
        }

        cyzMode.lv_listView.setAdapter(new MyOrderSpAdapterLv2(mContext, list.get(position)));


        cyzMode.tv_quxiao.setOnClickListener(new SetOnClick(position));
        cyzMode.tv_quzhifu.setOnClickListener(new SetOnClick(position));
        cyzMode.tv_shenqing.setOnClickListener(new SetOnClick(position));
        cyzMode.tv_querenshouhuo.setOnClickListener(new SetOnClick(position));
        cyzMode.tv_querenwancheng.setOnClickListener(new SetOnClick(position));
        cyzMode.tv_shanchu.setOnClickListener(new SetOnClick(position));


       /* cyzMode.ll_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Activity_OrderDetailsSp.class);
                intent.putExtra("groupPosition", position);
                intent.putExtra("childPosition", 0);
                intent.putExtra("type", 3 + "");
                intent.putExtra("orderId", list.get(position).get(0).getOrder_code() + "");
                mContext.startActivity(intent);
            }
        });*/
        cyzMode.lv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int cPosition, long id) {
                Intent intent = new Intent(mContext, Activity_OrderDetailsSp.class);
                intent.putExtra("groupPosition", position);
                intent.putExtra("childPosition", 0);
                intent.putExtra("type", 3 + "");
                intent.putExtra("mState",mState);
                intent.putExtra("orderId", list.get(position).get(0).getOrder_code() + "");
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    private void getAlipay(int pos) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("siId", list.get(pos).get(0).getSiId()+"");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getAlipay, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                            alipayBean = new Gson().fromJson(responseInfo.result, AlipayBean.class);
                            if (alipayBean.getHeader().getStatus() == 0) {

                            } else {
                                ToastUtil.show(mContext, alipayBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext, e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, e.getMessage());
                    }
                });

    }
    private class SetOnClick implements View.OnClickListener{
        int position;
        Dialog dialog;
        TextView text;
        public SetOnClick(int position) {
            this.position = position;

            dialog = new Dialog(mContext, R.style.AlertDialogStyle);
            dialog.getWindow().setContentView(R.layout.dialog_cancel_order);
            dialog.setCanceledOnTouchOutside(false);

            text= (TextView) dialog.getWindow().findViewById(R.id.tv_content);
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.tv_shenqing:
                   /* DialogRefundOk dialogRefundOk=new DialogRefundOk(mContext,R.style.AlertDialogStyle);
                    dialogRefundOk.show();
                    DialogRefundNo dialogRefundNo=new DialogRefundNo(mContext,R.style.AlertDialogStyle);
                    dialogRefundNo.show();*/
                   /* DialogRefund dialogRefund=new DialogRefund(mContext,R.style.AlertDialogStyle,3,groupPosition,childPosition);
                    dialogRefund.show();*/
                    /*DialogRefund dialogRefund=new DialogRefund(mContext,R.style.AlertDialogStyle,3,groupPosition,childPosition);
                    dialogRefund.show();*/
                    if (list.get(position).get(0).getIs_not_return()==1){//0不是  1是  判断是否是海鲜
                        DialogSeafood dialogSeafood=new DialogSeafood(mContext,R.style.AlertDialogStyle);
                        dialogSeafood.show();
                    }else {
                        intent=new Intent(mContext,Activity_ApplyRefund.class);
                        intent.putExtra("orderState",list.get(position).get(0).getOrder_state()+"");
                        intent.putExtra("orderCode",list.get(position).get(0).getOrder_code()+"");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.tv_qushiyong:

                    break;
                case R.id.tv_quxiao:
                    text.setText("确定取消该订单？");
                    dialog.show();
                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            deleteMyOrder(position,0);
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog.dismiss();
                        }
                    });
                   /* DialogMyCancelOrder  dialogMyCancelOrder=new DialogMyCancelOrder(mContext,R.style.AlertDialogStyle,6,position,0);
                    dialogMyCancelOrder.show();*/
                    break;
                case R.id.tv_quzhifu://去支付
                    intent=new Intent(mContext,Activity_OrderPaymentOrder.class);
                    intent.putExtra("orderCode", list.get(position).get(0).getOrder_code() + "");
                    if(alipayBean.getData()==null||alipayBean.getData().equals("")){
                        //ToastUtil.show(mContext,"支付宝暂时无法支付");
                        mContext.startActivity(intent);
                    }else{
                        intent.putExtra("partner",alipayBean.getData().getPartner());
                        intent.putExtra("seller",alipayBean.getData().getSeller());
                        intent.putExtra("privateKey",alipayBean.getData().getPrivateKey());
                        mContext.startActivity(intent);
                    }

                    break;
                case R.id.tv_querenwancheng:
                   /* DialogMyOrderComplete dialogMyOrderComplete=new DialogMyOrderComplete(mContext,R.style.AlertDialogStyle,1,position,0);
                    dialogMyOrderComplete.show();*/

                    text.setText("确认完成？");
                    dialog.show();
                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            backMoney(5,position, 0);
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog.dismiss();
                        }
                    });
                    break;
                case R.id.tv_querenshouhuo:
                    /*DialogMyOrderCollect dialogMyOrderCollect=new DialogMyOrderCollect(mContext,R.style.AlertDialogStyle,1,position,0);
                    dialogMyOrderCollect.show();*/

                    text.setText("确认收货？");
                    dialog.show();
                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            backMoney(4,position,0);
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog.dismiss();
                        }
                    });
                    break;
                case R.id.tv_shanchu:
                    text.setText("确定删除该订单？");
                    dialog.show();
                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            deleteMyOrder(position,0);
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog.dismiss();
                        }
                    });
                    break;
            }
        }
    }

    //万能接口
    public void backMoney(int orderState,final int groupPosition,int childPosition){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderState", orderState + "");
        params.addQueryStringParameter("orderCode", list.get(groupPosition).get(0).getOrder_code() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0*1000);
        http.send(HttpRequest.HttpMethod.GET, URL.backMoney, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int status = header.getInt("status");
                            if (status == 0) {
                                MyOrderSpFragment2 myOrderSpFragment3= (MyOrderSpFragment2) Activity_MyOrder.sp_3;
                                myOrderSpFragment3.mPage=1;
                                myOrderSpFragment3.list.clear();
                                myOrderSpFragment3.findOrder();
                                if (Activity_MyOrder.sp_4!=null){
                                    MyOrderSpFragment2 myOrderSpFragment4= (MyOrderSpFragment2) Activity_MyOrder.sp_4;
                                    myOrderSpFragment4.mPage=1;
                                    myOrderSpFragment4.list.clear();
                                    myOrderSpFragment4.findOrder();
                                }
                            } else if (status == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            } else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            //ToastUtil.show(mContext, "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, "连接网络失败");
                    }
                });
    }
    //删除订单  取消订单
    public void deleteMyOrder(final int groupPosition, final int childPosition){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderState", list.get(groupPosition).get(childPosition).getOrder_state() + "");
        params.addQueryStringParameter("orderCode", list.get(groupPosition).get(childPosition).getOrder_code() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.deleteMyOrder, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int status = header.getInt("status");
                            if (status == 0) {
                                if (mState==1){
                                    MyOrderSpFragment2 myOrderSpFragment = (MyOrderSpFragment2) Activity_MyOrder.sp_1;
                                    myOrderSpFragment.mPage=1;
                                    myOrderSpFragment.list.clear();
                                    myOrderSpFragment.findOrder();
                                }else if (mState==4){
                                    MyOrderSpFragment2 myOrderSpFragment4 = (MyOrderSpFragment2) Activity_MyOrder.sp_4;
                                    myOrderSpFragment4.mPage=1;
                                    myOrderSpFragment4.list.clear();
                                    myOrderSpFragment4.findOrder();
                                }

                            } else if (status == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(mContext);
                            } else {
                                ToastUtil.show(mContext, header.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtil.show(mContext, "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(mContext, "连接网络失败");
                    }
                });
    }



    private void initView(CyzMode cyzMode){
        cyzMode.tv_shanchu.setVisibility(View.GONE);
        cyzMode.tv_quzhifu.setVisibility(View.GONE);
        cyzMode.tv_shenqing.setVisibility(View.GONE);
        cyzMode.tv_quxiao.setVisibility(View.GONE);
        cyzMode.tv_querenwancheng.setVisibility(View.GONE);
        cyzMode.tv_jiaoyiwancheng.setVisibility(View.GONE);
        cyzMode.tv_querenshouhuo.setVisibility(View.GONE);
        cyzMode.tv_fahuozhong.setVisibility(View.GONE);
    }

    private class CyzMode {
        ListView lv_listView;
        LinearLayout ll_context;

        TextView tv_code;
        TextView tv_businessName;
        ImageView iv_business;

        TextView tv_quzhifu;
        TextView tv_shenqing;
        TextView tv_quxiao;
        TextView tv_jiaoyiwancheng;
        TextView tv_querenwancheng;
        TextView tv_querenshouhuo;
        TextView tv_fahuozhong;
        TextView tv_shanchu;
    }
}
