package com.demo.my.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_OrderDetailsFdDp;
import com.demo.my.activity.Activity_OrderDetailsFdTc;
import com.demo.my.bean.FdFindOrderBean;
import com.demo.my.fragment.MyOrderFdFragment;
import com.demo.my.fragment.MyOrderFdFragment2;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogMyOrderDelete;
import com.demo.view.DialogRefund;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class MyOrderFdAdapterLv2 extends BaseAdapter {
    int mState;
    Context mContext;
    List<List<FdFindOrderBean.DataBean.OrderListBean.RowsBean>> list;


    public MyOrderFdAdapterLv2(Context mContext, List<List<FdFindOrderBean.DataBean.OrderListBean.RowsBean>> list,int state) {
        this.mContext = mContext;
        this.list = list;
        this.mState=state;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_my_order_fd2,null);
            cyzMode.lv_listView= (ListView) convertView.findViewById(R.id.lv_listView);
            cyzMode.tv_OrderNumber= (TextView) convertView.findViewById(R.id.tv_OrderNumber);
            cyzMode.tv_shenqing= (TextView) convertView.findViewById(R.id.tv_shenqing);
            cyzMode.tv_wancheng= (TextView) convertView.findViewById(R.id.tv_wancheng);
            cyzMode.tv_shiyong= (TextView) convertView.findViewById(R.id.tv_qushiyong);
            cyzMode.tv_zaici= (TextView) convertView.findViewById(R.id.tv_zaici);
            cyzMode.tv_shanchu= (TextView) convertView.findViewById(R.id.tv_shanchu);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        initView(cyzMode);

        cyzMode.lv_listView.setAdapter(new MyOrderFdAdapterLvN(mContext,list.get(position)));

        cyzMode.tv_OrderNumber.setText("订单号："+list.get(position).get(0).getOrder_code());

        if (list.get(position).get(0).getOrder_state()==2){
            cyzMode.tv_shiyong.setVisibility(View.VISIBLE);
            if (list.get(position).get(0).getGoods_type()==1){
                cyzMode.tv_shenqing.setVisibility(View.VISIBLE);
            }
        }else if (list.get(position).get(0).getOrder_state()==3){

        }else if (list.get(position).get(0).getOrder_state()==4){
            cyzMode.tv_wancheng.setVisibility(View.VISIBLE);
            cyzMode.tv_shanchu.setVisibility(View.VISIBLE);
        }else if (list.get(position).get(0).getOrder_state()==5){
            //cyzMode.tv_zaici.setVisibility(View.VISIBLE);
            cyzMode.tv_shanchu.setVisibility(View.VISIBLE);
        }else if (list.get(position).get(0).getOrder_state()==6){

        }else if (list.get(position).get(0).getOrder_state()==7){

        }

        cyzMode.tv_shiyong.setOnClickListener(new SetOnClick(position));
        cyzMode.tv_shenqing.setOnClickListener(new SetOnClick(position));
        cyzMode.tv_zaici.setOnClickListener(new SetOnClick(position));
        cyzMode.tv_shanchu.setOnClickListener(new SetOnClick(position));

        cyzMode.lv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int cPosition, long id) {
                Intent intent = null;
                if (list.get(position).get(0).getGoods_type() == 1) {
                    intent = new Intent(mContext, Activity_OrderDetailsFdTc.class);
                } else {
                    intent = new Intent(mContext, Activity_OrderDetailsFdDp.class);
                }
                intent.putExtra("mState", mState+"");
                intent.putExtra("position", position);
                intent.putExtra("type", 2 + "");
                intent.putExtra("orderId", list.get(position).get(0).getOrder_code() + "");
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }

    private class SetOnClick implements View.OnClickListener{

        private int position=0;

        public SetOnClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_shenqing:
                   /* DialogRefundOk dialogRefundOk=new DialogRefundOk(mContext,R.style.AlertDialogStyle);
                    dialogRefundOk.show();
                    DialogRefundNo dialogRefundNo=new DialogRefundNo(mContext,R.style.AlertDialogStyle);
                    dialogRefundNo.show();*/
                    DialogRefund dialogRefund=new DialogRefund(mContext,R.style.AlertDialogStyle,3,position,0);
                    dialogRefund.show();
                    break;
                case R.id.tv_qushiyong:
                    //需要判断进入那个订单详情
                    Intent intent = null;
                    if (list.get(position).get(0).getGoods_type()==1){
                        intent=new Intent(mContext, Activity_OrderDetailsFdTc.class);
                    }else{
                        intent=new Intent(mContext, Activity_OrderDetailsFdDp.class);
                    }
                    intent.putExtra("mState", mState+"");
                    intent.putExtra("position", position);
                    intent.putExtra("type", 2 + "");
                    intent.putExtra("orderId", list.get(position).get(0).getOrder_code() + "");
                    mContext.startActivity(intent);
                    break;
                case R.id.tv_zaici:

                    break;
                case R.id.tv_shanchu:
                    final Dialog dialog1 = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog1.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    TextView text1 = (TextView) dialog1.getWindow().findViewById(R.id.tv_content);
                    text1.setText("确定删除该订单？");
                    dialog1.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            deleteMyOrder(position,0);
                            dialog1.dismiss();
                        }
                    });
                    dialog1.getWindow().findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //取消
                            dialog1.dismiss();
                        }
                    });
                   /* DialogMyOrderDelete dialogMyOrderDelete=new DialogMyOrderDelete(mContext,R.style.AlertDialogStyle,3,position,0);
                    dialogMyOrderDelete.show();*/
                    break;
            }
        }
    }

    //申请退款
    public void backMoney(final int groupPosition, final int childPosition){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("type", 2 + "");
        params.addQueryStringParameter("orderState",3+"");
        params.addQueryStringParameter("orderCode",list.get(groupPosition).get(childPosition).getOrder_code()+"");
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
                                MyOrderFdFragment2 myOrderFdFragment= (MyOrderFdFragment2) Activity_MyOrder.fd_1;
                                myOrderFdFragment.list.clear();
                                myOrderFdFragment.mPage=1;
                                myOrderFdFragment.findOrder();
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
    //删除订单
    public void deleteMyOrder(final int groupPosition,int childPosition){
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("type", 2 + "");
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
                                if (mState==2){
                                    MyOrderFdFragment2 myOrderFdFragment= (MyOrderFdFragment2) Activity_MyOrder.fd_2;
                                    myOrderFdFragment.list.clear();
                                    myOrderFdFragment.mPage=1;
                                    myOrderFdFragment.findOrder();
                                }else if (mState==3){
                                    MyOrderFdFragment2 myOrderFdFragment= (MyOrderFdFragment2) Activity_MyOrder.fd_3;
                                    myOrderFdFragment.list.clear();
                                    myOrderFdFragment.mPage=1;
                                    myOrderFdFragment.findOrder();
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
        cyzMode.tv_shiyong.setVisibility(View.GONE);
        cyzMode.tv_shenqing.setVisibility(View.GONE);
        cyzMode.tv_wancheng.setVisibility(View.GONE);
        cyzMode.tv_zaici.setVisibility(View.GONE);
        cyzMode.tv_shanchu.setVisibility(View.GONE);
    }

    private class CyzMode {
        ListView lv_listView;

        TextView tv_shiyong;
        TextView tv_shenqing;
        TextView tv_wancheng;
        TextView tv_zaici;
        TextView tv_shanchu;

        TextView tv_OrderNumber;


    }
}
