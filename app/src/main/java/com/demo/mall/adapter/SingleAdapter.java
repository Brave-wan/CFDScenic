package com.demo.mall.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.bean.FindAllRestaurantDetailBean;
import com.demo.view.BuyTimesPickerPicker;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/8/8 0008 19:07
 */
public class SingleAdapter extends BaseAdapter{
    private Context context;
    private List<FindAllRestaurantDetailBean.DataBean.ShopGoodsBean> list;
    private ArrayList<Integer> numList;

    public SingleAdapter(Context context, List<FindAllRestaurantDetailBean.DataBean.ShopGoodsBean> list) {
        this.context = context;
        this.list = list;
        init();
    }

    private void init(){
        numList=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            numList.add(0);
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {
        CyzMode cyzMode;
        if (convertView==null){
            cyzMode=new CyzMode();
            convertView=LayoutInflater.from(context).inflate(R.layout.mall_adapter_single,null);
            cyzMode.ll_jian= (LinearLayout) convertView.findViewById(R.id.ll_jian);
            cyzMode.iv_jian= (ImageView) convertView.findViewById(R.id.iv_jian);
            cyzMode.tv_num= (TextView) convertView.findViewById(R.id.tv_num);
            cyzMode.iv_jia= (ImageView) convertView.findViewById(R.id.iv_jia);
            cyzMode.iv_adp_sing_img= (ImageView) convertView.findViewById(R.id.iv_adp_sing_img);
            cyzMode.iv_adp_pack_title= (TextView) convertView.findViewById(R.id.iv_adp_pack_title);
            cyzMode.iv_adp_pack_price= (TextView) convertView.findViewById(R.id.iv_adp_pack_price);
            convertView.setTag(cyzMode);
        }else {
            cyzMode= (CyzMode) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(list.get(position).getDescribe_img(), cyzMode.iv_adp_sing_img);
        cyzMode.iv_adp_pack_title.setText(list.get(position).getGoods_name());
        cyzMode.iv_adp_pack_price.setText("￥"+list.get(position).getNew_price()+"");

        cyzMode.iv_jian.setOnClickListener(new SetOnClick(cyzMode, position));
        cyzMode.iv_jia.setOnClickListener(new SetOnClick(cyzMode,position));
        return convertView;
    }

    private class  CyzMode{
        LinearLayout ll_jian;
        ImageView iv_jian;
        TextView tv_num;
        ImageView iv_jia;

        ImageView iv_adp_sing_img;
        TextView iv_adp_pack_title;
        TextView iv_adp_pack_price;
    }

    private class SetOnClick implements View.OnClickListener{
        CyzMode cyzMode;
        int position;

        public SetOnClick(CyzMode cyzMode, int position) {
            this.cyzMode = cyzMode;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_jian:
                    numList.set(position, numList.get(position) - 1);
                    if (numList.get(position)==0){
                        cyzMode.ll_jian.setVisibility(View.GONE);
                    }
                    cyzMode.tv_num.setText(numList.get(position) + "");
                break;
                case R.id.iv_jia:
                    numList.set(position, numList.get(position) + 1);
                    if (numList.get(position)==1){
                        cyzMode.ll_jian.setVisibility(View.VISIBLE);
                    }
                    cyzMode.tv_num.setText(numList.get(position) + "");
                break;
            }
        }
    }

    public List<FindAllRestaurantDetailBean.DataBean.ShopGoodsBean> getList(){
        return list;
    }

    public ArrayList<Integer> getNumList(){
        return numList;
    }
}
