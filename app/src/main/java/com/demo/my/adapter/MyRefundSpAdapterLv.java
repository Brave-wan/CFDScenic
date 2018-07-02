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

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.my.activity.Activity_MyOrder;
import com.demo.my.activity.Activity_RefundDetails_sp;
import com.demo.my.activity.Activity_RefundOrder;
import com.demo.my.activity.Activity_logistics;
import com.demo.my.bean.RefundOrderSpBean;
import com.demo.my.fragment.MyOrderSpFragment;
import com.demo.my.fragment.MyRefundSpFragment;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogMyOrderDelete;
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
 * Created by Administrator on 2016/9/24 0024.
 */
public class MyRefundSpAdapterLv extends BaseAdapter {
    Context mContext;
    List<List<RefundOrderSpBean.DataBean.OrderListBean.RowsBean>> list;

    public MyRefundSpAdapterLv(Context mContext, List<List<RefundOrderSpBean.DataBean.OrderListBean.RowsBean>> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_refund_order_sp, null);
            cyzMode.lv_listView = (ListView) convertView.findViewById(R.id.lv_listView);

            cyzMode.iv_business = (ImageView) convertView.findViewById(R.id.iv_business);
            cyzMode.tv_businessName = (TextView) convertView.findViewById(R.id.tv_businessName);
            cyzMode.tv_code = (TextView) convertView.findViewById(R.id.tv_code);

            cyzMode.tv_beibohui = (TextView) convertView.findViewById(R.id.tv_beibohui);
            cyzMode.tv_tianxiewuliu = (TextView) convertView.findViewById(R.id.tv_tianxiewuliu);
            cyzMode.tv_tuikuanzhong = (TextView) convertView.findViewById(R.id.tv_tuikuanzhong);
            cyzMode.tv_yituikuan = (TextView) convertView.findViewById(R.id.tv_yituikuan);
            cyzMode.tv_yifahuo = (TextView) convertView.findViewById(R.id.tv_yifahuo);
            cyzMode.tv_shenhezhong = (TextView) convertView.findViewById(R.id.tv_shenhezhong);
            cyzMode.tv_shanchu = (TextView) convertView.findViewById(R.id.tv_shanchu);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }

        initView(cyzMode);

        cyzMode.lv_listView.setAdapter(new MyRefundSpAdapterLv2(mContext, list.get(position)));

        cyzMode.tv_businessName.setText(list.get(position).get(0).getShopName());
        cyzMode.tv_code.setText(list.get(position).get(0).getOrder_code());
        ImageLoader.getInstance().displayImage(list.get(position).get(0).getHead_img(), cyzMode.iv_business);

        //状态值判断
        if (list.get(position).get(0).getOrder_state() == 6) {
            cyzMode.tv_shenhezhong.setVisibility(View.VISIBLE);
        } else if (list.get(position).get(0).getOrder_state() == 7) {
            cyzMode.tv_tianxiewuliu.setVisibility(View.VISIBLE);
        } else if (list.get(position).get(0).getOrder_state() == 8) {
            cyzMode.tv_tuikuanzhong.setVisibility(View.VISIBLE);
        } else if (list.get(position).get(0).getOrder_state() == 9) {
            cyzMode.tv_yituikuan.setVisibility(View.VISIBLE);
            cyzMode.tv_shanchu.setVisibility(View.VISIBLE);
        } else if (list.get(position).get(0).getOrder_state() == 10) {
            cyzMode.tv_beibohui.setVisibility(View.VISIBLE);
        } else if (list.get(position).get(0).getOrder_state() == 11) {
            cyzMode.tv_yifahuo.setVisibility(View.VISIBLE);
        }

        cyzMode.lv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int cposition, long id) {
                Intent intent = new Intent(mContext, Activity_RefundDetails_sp.class);
                intent.putExtra("groupPosition", position);
                intent.putExtra("childPosition", 0);
                intent.putExtra("type", 3 + "");
                intent.putExtra("orderId", list.get(position).get(0).getOrder_code() + "");
                mContext.startActivity(intent);
            }
        });

        cyzMode.tv_tianxiewuliu.setOnClickListener(new SetOnClick(position, 0));
        cyzMode.tv_shanchu.setOnClickListener(new SetOnClick(position, 0));

        return convertView;
    }

    private class SetOnClick implements View.OnClickListener {
        int groupPosition;
        int childPosition;

        public SetOnClick(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.tv_tianxiewuliu:
                    intent = new Intent(mContext, Activity_logistics.class);
                    intent.putExtra("orderState", list.get(groupPosition).get(childPosition).getOrder_state() + "");
                    intent.putExtra("orderCode", list.get(groupPosition).get(childPosition).getOrder_code() + "");
                    mContext.startActivity(intent);
                    break;
                case R.id.tv_shanchu:
                   /* DialogMyOrderDelete dialogMyOrderDelete=new DialogMyOrderDelete(mContext,R.style.AlertDialogStyle,8,groupPosition,childPosition);
                    dialogMyOrderDelete.show();*/

                    final Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
                    dialog.getWindow().setContentView(R.layout.dialog_cancel_order);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    TextView text = (TextView) dialog.getWindow().findViewById(R.id.tv_content);
                    text.setText("确定删除该订单？");
                    dialog.getWindow().findViewById(R.id.ll_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //确定
                            deleteMyOrder(groupPosition, childPosition);
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
    public void backMoney(int orderState, final int groupPosition, int childPosition) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("type", 3 + "");
        params.addQueryStringParameter("orderState", orderState + "");
        params.addQueryStringParameter("orderCode", list.get(groupPosition).get(0).getOrder_code() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.backMoney, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int status = header.getInt("status");
                            if (status == 0) {
                                MyRefundSpFragment myOrderSpFragment4 = (MyRefundSpFragment) Activity_MyOrder.sp_4;
                                myOrderSpFragment4.list.clear();
                                myOrderSpFragment4.refundOrder();
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

    //删除订单  取消订单
    public void deleteMyOrder(final int groupPosition, final int childPosition) {
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
                            Log.i("deleteMyOrder", responseInfo.result);
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int status = header.getInt("status");
                            if (status == 0) {
                                MyRefundSpFragment myOrderSpFragment = (MyRefundSpFragment) Activity_RefundOrder.sp_4;
                                myOrderSpFragment.list.clear();
                                myOrderSpFragment.refundOrder();
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

    private void initView(CyzMode cyzMode) {
        cyzMode.tv_beibohui.setVisibility(View.GONE);
        cyzMode.tv_tianxiewuliu.setVisibility(View.GONE);
        cyzMode.tv_tuikuanzhong.setVisibility(View.GONE);
        cyzMode.tv_yituikuan.setVisibility(View.GONE);
        cyzMode.tv_yifahuo.setVisibility(View.GONE);
        cyzMode.tv_shenhezhong.setVisibility(View.GONE);
        cyzMode.tv_shanchu.setVisibility(View.GONE);
    }


    private class CyzMode {
        ListView lv_listView;

        TextView tv_code;
        TextView tv_businessName;
        ImageView iv_business;

        TextView tv_beibohui;
        TextView tv_tianxiewuliu;
        TextView tv_tuikuanzhong;
        TextView tv_yituikuan;
        TextView tv_yifahuo;
        TextView tv_shenhezhong;
        TextView tv_shanchu;
    }
}
