package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class FindFeatureShopBean {


    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * describe_img : 哦哦
     * goods_describe : 小虾不好吃
     * goods_name : 小霞
     * goods_type : 0
     * is_hot : 0
     * new_price : 200
     * price : 200
     * quantity : 1
     * shopGoodsId : 3
     */

    private List<DataBean> data=new ArrayList<>();

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class HeaderBean {
        private String msg;
        private int status;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class DataBean {
        private String describe_img;
        private String goods_describe;
        private String goods_name;
        private int goods_type;
        private int is_hot;
        private int new_price;
        private int price;
        private int quantity;
        private long shopGoodsId;

        public String getDescribe_img() {
            return describe_img;
        }

        public void setDescribe_img(String describe_img) {
            this.describe_img = describe_img;
        }

        public String getGoods_describe() {
            return goods_describe;
        }

        public void setGoods_describe(String goods_describe) {
            this.goods_describe = goods_describe;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public int getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(int is_hot) {
            this.is_hot = is_hot;
        }

        public int getNew_price() {
            return new_price;
        }

        public void setNew_price(int new_price) {
            this.new_price = new_price;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public long getShopGoodsId() {
            return shopGoodsId;
        }

        public void setShopGoodsId(long shopGoodsId) {
            this.shopGoodsId = shopGoodsId;
        }
    }
}
