package com.demo.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_ConfirmOrder;
import com.demo.my.bean.GetShopCartByUserIdBean;
import com.demo.view.MyRadioButton;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class ConfirmOrderAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<GetShopCartByUserIdBean.DataBean> list;
    Activity_ConfirmOrder activity;
    int select=0;

    public  ConfirmOrderAdapter(Context mContext, List<GetShopCartByUserIdBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
        activity = (Activity_ConfirmOrder) mContext;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getGoodsList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getGoodsList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return list.get(groupPosition).getGoodsList().size();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_confirm_order_shang,null);
            cyzMode.iv_business= (ImageView) convertView.findViewById(R.id.iv_business);
            cyzMode.tv_businessName= (TextView) convertView.findViewById(R.id.tv_businessName);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        cyzMode.iv_business.setVisibility(View.GONE);
        cyzMode.tv_businessName.setVisibility(View.GONE);
        for (int position=0;position<getChildrenCount(groupPosition);position++){
            if (list.get(groupPosition).getGoodsList().get(position).getState()==0){//有选中项
                cyzMode.iv_business.setVisibility(View.VISIBLE);
                cyzMode.tv_businessName.setVisibility(View.VISIBLE);
            }
        }
        select=0;


        ImageLoader.getInstance().displayImage(list.get(groupPosition).getShopInformationImg(),cyzMode.iv_business);
        cyzMode.tv_businessName.setText(list.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_confirm_order_xia,null);
            cyzMode.ll_Allxia= (LinearLayout) convertView.findViewById(R.id.ll_Allxia);
            cyzMode.ll_heji= (LinearLayout) convertView.findViewById(R.id.ll_heji);
            cyzMode.view_ziti= (MyRadioButton) convertView.findViewById(R.id.view_ziti);
            cyzMode.view_songhuo= (MyRadioButton) convertView.findViewById(R.id.view_songhuo);
            cyzMode.tv_ChoiceDate= (TextView) convertView.findViewById(R.id.tv_ChoiceDate);
            cyzMode.iv_commodity= (ImageView) convertView.findViewById(R.id.iv_commodity);
            cyzMode.tv_commodityName= (TextView) convertView.findViewById(R.id.tv_commodityName);
            cyzMode.tv_present= (TextView) convertView.findViewById(R.id.tv_present);
            cyzMode.tv_primary= (TextView) convertView.findViewById(R.id.tv_primary);
            cyzMode.tv_Number= (TextView) convertView.findViewById(R.id.tv_Number);
            cyzMode.tv_Distribution= (TextView) convertView.findViewById(R.id.tv_Distribution);
            cyzMode.tv_Total= (TextView) convertView.findViewById(R.id.tv_Total);
            cyzMode.tv_gongshangpin= (TextView) convertView.findViewById(R.id.tv_gongshangpin);
            cyzMode.tv_peisong= (TextView) convertView.findViewById(R.id.tv_peisong);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }


        //判断是否选中
        if (list.get(groupPosition).getGoodsList().get(childPosition).getState()==0){
            cyzMode.ll_Allxia.setVisibility(View.VISIBLE);
            cyzMode.tv_commodityName.setText(list.get(groupPosition).getGoodsList().get(childPosition).getGoods_name());
            BitmapUtils bitmapUtils = new BitmapUtils(mContext);
            bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);//加载失败
            bitmapUtils.configDefaultLoadingImage(R.mipmap.zhanwei);//加载过程中
            bitmapUtils.display(cyzMode.iv_commodity, list.get(groupPosition).getGoodsList().get(childPosition).getShopGoodsImg());
            cyzMode.tv_present.setText("￥" + list.get(groupPosition).getGoodsList().get(childPosition).getNew_price());
            cyzMode.tv_primary.setText("￥"+list.get(groupPosition).getGoodsList().get(childPosition).getPrice());
            cyzMode.tv_primary.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            cyzMode.tv_Number.setText("X"+list.get(groupPosition).getGoodsList().get(childPosition).getNumber());

            select=select+1;
        }else {
            cyzMode.ll_Allxia.setVisibility(View.GONE);
        }

        //判断是不是最后一项，用来显示总价格
        if (childPosition==getChildrenCount(groupPosition)-1&&select!=0){
            cyzMode.ll_heji.setVisibility(View.VISIBLE);
            double distribution=0;
            double total=0;
            for (int i=0;i<getChildrenCount(groupPosition);i++){
                if (list.get(groupPosition).getGoodsList().get(i).getState()==0){
                    //配送费  无返回字段
                    distribution=distribution+list.get(groupPosition).getGoodsList().get(i).getDeliverFee();
                    total=total+list.get(groupPosition).getGoodsList().get(i).getNumber()*list.get(groupPosition).getGoodsList().get(i).getNew_price();
                }
            }

            list.get(groupPosition).setDeliverFeeSum(distribution);
            list.get(groupPosition).setTotal(total);

            cyzMode.tv_gongshangpin.setText("共" + select + "件商品");
            cyzMode.tv_Distribution.setText("￥"+distribution);

            cyzMode.tv_Total.setText("￥" + (total + distribution));


        }else {
            cyzMode.ll_heji.setVisibility(View.GONE);
        }

        cyzMode.view_ziti.setOnClickListener(new SetOnClick(cyzMode,groupPosition,childPosition));
        cyzMode.view_songhuo.setOnClickListener(new SetOnClick(cyzMode,groupPosition,childPosition));
        return convertView;
    }


    private class SetOnClick implements View.OnClickListener{
        CyzMode cyzMode;
        int groupPosition;
        int childPosition;

        public SetOnClick(CyzMode cyzMode, int groupPosition, int childPosition) {
            this.cyzMode = cyzMode;
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View v) {
            Double total=Double.parseDouble(cyzMode.tv_Total.getText().toString().substring(1));
            double activityTotal=activity.bottomTotal;

            switch (v.getId()){
                case R.id.view_ziti:
                    cyzMode.view_ziti.setStatus(true);
                    cyzMode.view_songhuo.setStatus(false);
                    list.get(groupPosition).setIsPickup(1);
                    cyzMode.tv_peisong.setVisibility(View.GONE);
                    cyzMode.tv_Distribution.setVisibility(View.GONE);

                    cyzMode.tv_Total.setText("￥" + (total - list.get(groupPosition).getDeliverFeeSum()));

                    activity.bottomTotal=activityTotal - list.get(groupPosition).getDeliverFeeSum();
                    activity.tvTotalMoney.setText("￥"+activity.bottomTotal);
                break;
                case R.id.view_songhuo:
                    cyzMode.view_ziti.setStatus(false);
                    cyzMode.view_songhuo.setStatus(true);
                    list.get(groupPosition).setIsPickup(0);
                    cyzMode.tv_peisong.setVisibility(View.VISIBLE);
                    cyzMode.tv_Distribution.setVisibility(View.VISIBLE);

                    cyzMode.tv_Total.setText("￥" + (total + list.get(groupPosition).getDeliverFeeSum()));
                    activity.bottomTotal=activityTotal + list.get(groupPosition).getDeliverFeeSum();
                    activity.tvTotalMoney.setText("￥"+activity.bottomTotal);
                    break;
            }
        }
    }


    private class CyzMode{
        LinearLayout ll_Allxia;
        LinearLayout ll_heji;
        MyRadioButton view_ziti;
        MyRadioButton view_songhuo;
        TextView tv_ChoiceDate;
        ImageView iv_business;
        TextView tv_businessName;
        ImageView iv_commodity;
        TextView tv_commodityName;
        TextView tv_present;
        TextView tv_primary;
        TextView tv_Number;
        TextView tv_Distribution;
        TextView tv_Total;
        TextView tv_gongshangpin;
        TextView tv_peisong;
    }
}
