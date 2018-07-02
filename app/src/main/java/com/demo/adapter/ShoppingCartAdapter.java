package com.demo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.demo.demo.myapplication.R;
import com.demo.my.activity.Activity_ShoppingCart;
import com.demo.my.bean.GetShopCartByUserIdBean;
import com.demo.my.bean.ShoppingAdapterBean;
import com.demo.utils.ToastUtil;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class ShoppingCartAdapter extends BaseExpandableListAdapter {
    Context mContext;
    boolean state = false;//判断是否处于编辑状态    false没
    Activity_ShoppingCart activity_shoppingCart;
    public static List<GetShopCartByUserIdBean.DataBean> list;
    public double totalMoney = 0;
    public int totalPiece = 0;

    public int groupIndex = -1;//商家的下标
    private int childIndex = -1;//商品的下标

    /*ArrayList<ArrayList<ShoppingAdapterBean>> groupList = new ArrayList<ArrayList<ShoppingAdapterBean>>();//存放子对勾状态
    ArrayList<ShoppingAdapterBean> childList;      //子bean*/

    ArrayList<Boolean> groupState = new ArrayList<>();//存放父类对勾状态

    public ShoppingCartAdapter(Context mContext, List<GetShopCartByUserIdBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
        activity_shoppingCart = (Activity_ShoppingCart) mContext;
        initList();
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
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_shopping_cart_shang, null);
            cyzMode.iv_businessY = (ImageView) convertView.findViewById(R.id.iv_businessY);
            cyzMode.iv_business = (ImageView) convertView.findViewById(R.id.iv_business);
            cyzMode.tv_businessName = (TextView) convertView.findViewById(R.id.tv_businessName);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }

        if (groupState.get(groupPosition)) {
            cyzMode.iv_businessY.setImageResource(R.mipmap.yuan_duigou_true);
        } else {
            cyzMode.iv_businessY.setImageResource(R.mipmap.yuan_duigou_false);
        }

        ImageLoader.getInstance().displayImage(list.get(groupPosition).getShopInformationImg(), cyzMode.iv_business);

        cyzMode.tv_businessName.setText(list.get(groupPosition).getName());

        cyzMode.iv_businessY.setOnClickListener(new SetOnClick(cyzMode, 0, groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView == null) {
            cyzMode = new CyzMode();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_shopping_cart_xia, null);
            cyzMode.iv_commodityY = (ImageView) convertView.findViewById(R.id.iv_commodityY);
            cyzMode.ll_true = (LinearLayout) convertView.findViewById(R.id.ll_true);
            cyzMode.ll_false = (LinearLayout) convertView.findViewById(R.id.ll_false);
            cyzMode.tv_upLine = (TextView) convertView.findViewById(R.id.tv_upLine);

            cyzMode.ll_ProductDetails = (LinearLayout) convertView.findViewById(R.id.ll_ProductDetails);
            cyzMode.ll_BusinessDetails = (LinearLayout) convertView.findViewById(R.id.ll_BusinessDetails);

            cyzMode.iv_plus = (ImageView) convertView.findViewById(R.id.iv_plus);
            cyzMode.iv_reduce = (ImageView) convertView.findViewById(R.id.iv_reduce);
            cyzMode.tv_num = (TextView) convertView.findViewById(R.id.tv_num);

            cyzMode.iv_commodity = (ImageView) convertView.findViewById(R.id.iv_commodity);
            cyzMode.tv_commodityName = (TextView) convertView.findViewById(R.id.tv_commodityName);
            cyzMode.tv_present = (TextView) convertView.findViewById(R.id.tv_present);
            cyzMode.tv_primary = (TextView) convertView.findViewById(R.id.tv_primary);
            cyzMode.tv_Number = (TextView) convertView.findViewById(R.id.tv_Number);
            convertView.setTag(cyzMode);
        } else {
            cyzMode = (CyzMode) convertView.getTag();
        }


        if (list.get(groupPosition).getGoodsList().get(childPosition).getState() == 0) {
            cyzMode.iv_commodityY.setImageResource(R.mipmap.yuan_duigou_true);
        } else {
            cyzMode.iv_commodityY.setImageResource(R.mipmap.yuan_duigou_false);
        }

        //判断是否处于编辑状态
        if (state) {
            cyzMode.ll_false.setVisibility(View.GONE);
            cyzMode.ll_true.setVisibility(View.VISIBLE);
        } else {
            cyzMode.ll_false.setVisibility(View.VISIBLE);
            cyzMode.ll_true.setVisibility(View.GONE);
        }

        //判断数量改变减号
        if (list.get(groupPosition).getGoodsList().get(childPosition).getNumber() > 1) {
            cyzMode.iv_reduce.setImageResource(R.mipmap.jian_lan);
        } else {
            cyzMode.iv_reduce.setImageResource(R.mipmap.jian);
        }

        BitmapUtils bitmapUtils = new BitmapUtils(mContext);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
        //bitmapUtils.display(cyzMode.iv_commodity, list.get(groupPosition).getGoodsList().get(childPosition));
        ImageLoader.getInstance().displayImage(list.get(groupPosition).getGoodsList().get(childPosition).getShopGoodsImg(), cyzMode.iv_commodity);

        cyzMode.tv_commodityName.setText(list.get(groupPosition).getGoodsList().get(childPosition).getGoods_name());
        cyzMode.tv_present.setText("￥" + list.get(groupPosition).getGoodsList().get(childPosition).getNew_price() + "");
        cyzMode.tv_primary.setText("￥" + list.get(groupPosition).getGoodsList().get(childPosition).getPrice() + "");
        cyzMode.tv_primary.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        cyzMode.tv_Number.setText("X" + list.get(groupPosition).getGoodsList().get(childPosition).getNumber() + "");

        cyzMode.tv_num.setText(list.get(groupPosition).getGoodsList().get(childPosition).getNumber() + "");

        cyzMode.iv_commodityY.setOnClickListener(new SetOnClick(cyzMode, childPosition, groupPosition));
        cyzMode.iv_reduce.setOnClickListener(new SetOnClick(cyzMode, childPosition, groupPosition));
        cyzMode.iv_plus.setOnClickListener(new SetOnClick(cyzMode, childPosition, groupPosition));
        return convertView;
    }


    private class CyzMode {
        ImageView iv_businessY;//商家的圆
        ImageView iv_commodityY;//商品的圆
        LinearLayout ll_true;
        LinearLayout ll_false;
        ImageView iv_plus;  //加
        ImageView iv_reduce;//减
        TextView tv_num;
        TextView tv_upLine;

        LinearLayout ll_BusinessDetails;
        LinearLayout ll_ProductDetails;

        ImageView iv_business;
        ImageView iv_commodity;
        TextView tv_businessName;
        TextView tv_commodityName;
        TextView tv_present;
        TextView tv_primary;
        TextView tv_Number;
    }

    //初始化listView
    public void initList() {
        for (int group = 0; group < getGroupCount(); group++) {
            groupState.add(group, false);
            for (int child = 0; child < getChildrenCount(group); child++) {
                list.get(group).getGoodsList().get(child).setState(1);
            }
        }
        totalMoney = 0;
        totalPiece = 0;
        activity_shoppingCart.tvSettlement.setText("结算（" + totalPiece + "）");
        activity_shoppingCart.tvTotalMoney.setText("￥" + totalMoney);
        notifyDataSetChanged();
    }

    //单击事件
    private class SetOnClick implements View.OnClickListener {
        CyzMode cyzMode;
        int childPosition;
        int groupPosition;

        public SetOnClick(CyzMode cyzMode, int childPosition, int groupPosition) {
            this.cyzMode = cyzMode;
            this.childPosition = childPosition;
            this.groupPosition = groupPosition;
        }

        @Override
        public void onClick(View v) {
            List<GetShopCartByUserIdBean.DataBean.GoodsListBean> goodsListBean = list.get(groupPosition).getGoodsList();
            int selectedChildNum = 0;//子控件计数
            int selectedGroupNum = 0;//父控件计数
            int num;//购买数量
            switch (v.getId()) {
                case R.id.iv_businessY://商铺
                    if (groupState.get(groupPosition)) {//选中  选中变不选中
                        groupState.set(groupPosition, false);
                        for (int id = 0; id < getChildrenCount(groupPosition); id++) {
                            goodsListBean.get(id).setState(1);
                        }
                        //改变总计和件数
                        for (int i = 0; i < list.get(groupPosition).getGoodsList().size(); i++) {
                            totalMoney = totalMoney - list.get(groupPosition).getGoodsList().get(i).getNumber() * list.get(groupPosition).getGoodsList().get(i).getNew_price();
                            totalPiece = totalPiece - list.get(groupPosition).getGoodsList().get(i).getNumber();
                        }
                        groupIndex = -1;
                        activity_shoppingCart.tvSettlement.setText("结算（" + totalPiece + "）");
                        activity_shoppingCart.tvTotalMoney.setText("￥" + totalMoney);
                        notifyDataSetChanged();
                    } else {//没选中  没选中变不选中
                        if (!state) {//是否处于编辑
                            if (groupIndex != -1) {
                                if (groupIndex != groupPosition) {
                                    groupState.set(groupIndex,false);
                                    for (int chiId=0;chiId<getChildrenCount(groupIndex);chiId++){
                                        list.get(groupIndex).getGoodsList().get(chiId).setState(1);
                                    }
                                }
                            }
                        }

                        groupState.set(groupPosition, true);
                        for (int id = 0; id < getChildrenCount(groupPosition); id++) {
                            goodsListBean.get(id).setState(0);
                        }
                        //改变总计和件数
                        totalMoney=0;
                        totalPiece=0;
                        for (int i = 0; i < list.get(groupPosition).getGoodsList().size(); i++) {
                            totalMoney = totalMoney + list.get(groupPosition).getGoodsList().get(i).getNumber() * list.get(groupPosition).getGoodsList().get(i).getNew_price();
                            totalPiece = totalPiece + list.get(groupPosition).getGoodsList().get(i).getNumber();
                        }
                        groupIndex = groupPosition;

                        activity_shoppingCart.tvSettlement.setText("结算（" + totalPiece + "）");
                        activity_shoppingCart.tvTotalMoney.setText("￥" + totalMoney);
                        notifyDataSetChanged();
                    }
                    //判断全选是否勾选
                    if (activity_shoppingCart.viewAll.getStatus()) {
                        activity_shoppingCart.viewAll.setStatus(false);
                    }
                    //遍历组集合，如果都选中了则组集合
                    for (int group = 0; group < getGroupCount(); group++) {
                        if (groupState.get(group)) {
                            selectedGroupNum = selectedGroupNum + 1;
                        }
                    }
                    if (selectedGroupNum == getGroupCount()) {
                        activity_shoppingCart.viewAll.setStatus(true);
                    }
                    notifyDataSetChanged();
                    break;
                case R.id.iv_commodityY://商品
                    if (!state) {//是否处于编辑
                        if (groupIndex != -1) {
                            if (groupIndex != groupPosition) {
                                ToastUtil.show(mContext, "一次只能选择一家商铺");
                                return;
                            }
                        }
                    }
                    //判断是否店铺全选
                    if (groupState.get(groupPosition)) {
                        groupState.set(groupPosition, !groupState.get(groupPosition));
                    }
                    //判断全选是否勾选
                    if (activity_shoppingCart.viewAll.getStatus()) {
                        activity_shoppingCart.viewAll.setStatus(false);
                    }
                    //修改商品状态
                    if (goodsListBean.get(childPosition).getState() == 0) { //选中  选中变不选中
                        goodsListBean.get(childPosition).setState(1);
                        //改变总计和件数
                        totalPiece = totalPiece - list.get(groupPosition).getGoodsList().get(childPosition).getNumber();
                        activity_shoppingCart.tvSettlement.setText("结算（" + totalPiece + "）");
                        totalMoney = totalMoney - list.get(groupPosition).getGoodsList().get(childPosition).getNumber() * list.get(groupPosition).getGoodsList().get(childPosition).getNew_price();
                        activity_shoppingCart.tvTotalMoney.setText("￥" + totalMoney);

                        if (totalPiece==0){
                            groupIndex=-1;
                        }
                    } else {//没选中   没选中变选中
                        goodsListBean.get(childPosition).setState(0);
                        //改变总计和件数
                        totalPiece = totalPiece + list.get(groupPosition).getGoodsList().get(childPosition).getNumber();
                        activity_shoppingCart.tvSettlement.setText("结算（" + totalPiece + "）");
                        totalMoney = totalMoney + list.get(groupPosition).getGoodsList().get(childPosition).getNumber() * list.get(groupPosition).getGoodsList().get(childPosition).getNew_price();
                        activity_shoppingCart.tvTotalMoney.setText("￥" + totalMoney);

                        groupIndex = groupPosition;
                    }
                    //遍历组  如果全选中了  商铺对勾选中
                    for (int child = 0; child < getChildrenCount(groupPosition); child++) {
                        if (goodsListBean.get(child).getState() == 0) {
                            selectedChildNum = selectedChildNum + 1;
                        }
                    }
                    if (selectedChildNum == getChildrenCount(groupPosition)) {
                        groupState.set(groupPosition, !groupState.get(groupPosition));
                    }

                    //遍历组集合，如果都选中了则组集合
                    for (int group = 0; group < getGroupCount(); group++) {
                        if (groupState.get(group)) {
                            selectedGroupNum = selectedGroupNum + 1;
                        }
                    }
                    if (selectedGroupNum == getGroupCount()) {
                        activity_shoppingCart.viewAll.setStatus(true);
                    }
                    notifyDataSetChanged();
                    break;
                case R.id.iv_reduce:
                    num = list.get(groupPosition).getGoodsList().get(childPosition).getNumber();
                    if (num == 1) {
                        return;
                    }
                    if (num == 2) {
                        cyzMode.iv_reduce.setImageResource(R.mipmap.jian);
                        num = num - 1;
                        list.get(groupPosition).getGoodsList().get(childPosition).setNumber(num);
                        updateShopCart(groupPosition, childPosition, 1);
                        return;
                    }
                    num = num - 1;
                    list.get(groupPosition).getGoodsList().get(childPosition).setNumber(num);
                    updateShopCart(groupPosition, childPosition, 1);
                    break;
                case R.id.iv_plus:
                    num = list.get(groupPosition).getGoodsList().get(childPosition).getNumber();
                    num = num + 1;
                    list.get(groupPosition).getGoodsList().get(childPosition).setNumber(num);
                    if (num == 2) {
                        cyzMode.iv_reduce.setImageResource(R.mipmap.jian_lan);
                    }
                    updateShopCart(groupPosition, childPosition, 0);

                    break;
            }
        }
    }

    //修改状态
    public void setState(boolean b) {
        state = b;
        notifyDataSetChanged();
    }

    //设置全选-反选
    public void setAllImageView(boolean b) {
        totalMoney = 0;
        totalPiece = 0;
        for (int group = 0; group < getGroupCount(); group++) {
            groupState.set(group, b);
            for (int child = 0; child < getChildrenCount(group); child++) {
                GetShopCartByUserIdBean.DataBean.GoodsListBean bean = list.get(group).getGoodsList().get(child);
                if (b) {
                    bean.setState(0);
                    totalMoney = totalMoney + list.get(group).getGoodsList().get(child).getNumber() * list.get(group).getGoodsList().get(child).getNew_price();
                    totalPiece = totalPiece + list.get(group).getGoodsList().get(child).getNumber();
                } else {
                    bean.setState(1);
                }

            }
        }
        activity_shoppingCart.tvSettlement.setText("结算（" + totalPiece + "）");
        activity_shoppingCart.tvTotalMoney.setText("￥" + totalMoney);
        notifyDataSetChanged();
    }


    //修改数量
    public void updateShopCart(int groupPosition, int childPosition, final int s) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("id", list.get(groupPosition).getGoodsList().get(childPosition).getShopCartId() + "");
        params.addQueryStringParameter("number", list.get(groupPosition).getGoodsList().get(childPosition).getNumber() + "");
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.updateShopCart, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                notifyDataSetChanged();
                            } else if (i == 3) {
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
                        ToastUtil.show(mContext, s);
                    }
                });
    }

    //删除
    public void deleteItem() {
        //int groupPosition,int childPosition
        StringBuffer stringBuffer = new StringBuffer();
        for (int groupPosition = 0; groupPosition < getGroupCount(); groupPosition++) {
            for (int childPosition = 0; childPosition < getChildrenCount(groupPosition); childPosition++) {
                if (list.get(groupPosition).getGoodsList().get(childPosition).getState() == 0) {
                    stringBuffer.append(list.get(groupPosition).getGoodsList().get(childPosition).getShopCartId() + ",");
                }
            }
        }

        String string = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
        deleteShopCartById(string);
    }

    //删除购物车
    private void deleteShopCartById(String ids) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("ids", ids);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.deleteShopCartById, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                Activity_ShoppingCart activity_shoppingCart = (Activity_ShoppingCart) mContext;
                                activity_shoppingCart.getShopCartByUserId();
                            } else if (i == 3) {
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
                        ToastUtil.show(mContext, s);
                    }
                });
    }


    //移到收藏夹
    public void favorites() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int groupPosition = 0; groupPosition < getGroupCount(); groupPosition++) {
            for (int childPosition = 0; childPosition < getChildrenCount(groupPosition); childPosition++) {
                if (list.get(groupPosition).getGoodsList().get(childPosition).getState() == 0) {
                    stringBuffer.append(list.get(groupPosition).getGoodsList().get(childPosition).getShopCartId() + ",");
                }
            }
        }

        String string = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
        saveCollection(string);
    }

    private void saveCollection(String ids) {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(mContext, SpName.token, ""));
        params.addQueryStringParameter("ids", ids);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset("UTF_8");
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.saveCollection, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            JSONObject header = jsonObject.getJSONObject("header");
                            int i = header.getInt("status");
                            if (i == 0) {
                                Activity_ShoppingCart activity_shoppingCart = (Activity_ShoppingCart) mContext;
                                activity_shoppingCart.getShopCartByUserId();
                            } else if (i == 3) {
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
                        ToastUtil.show(mContext, s);
                    }
                });
    }
}
