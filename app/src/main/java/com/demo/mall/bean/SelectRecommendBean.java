package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class SelectRecommendBean {

    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * content_id : 1
     * describe_img : 假装是图片
     * goods_describe : 大酒店666
     * goods_name : 大酒店阿
     * id : 1
     * is_hot : 1
     * is_recomment : 1
     * monthlySales : 10
     * new_price : 200
     * price : 200
     * shop_information_id : 1
     * type : 1
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
        private long content_id;
        private String describe_img;
        private String goods_describe;
        private String goods_name;
        private long id;
        private int is_hot;
        private int is_recomment;
        private int monthlySales;
        private int new_price;
        private int price;
        private long shop_information_id;
        private int type;//商品类型（1酒店2特产3饭店4小吃）

        public long getContent_id() {
            return content_id;
        }

        public void setContent_id(long content_id) {
            this.content_id = content_id;
        }

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

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(int is_hot) {
            this.is_hot = is_hot;
        }

        public int getIs_recomment() {
            return is_recomment;
        }

        public void setIs_recomment(int is_recomment) {
            this.is_recomment = is_recomment;
        }

        public int getMonthlySales() {
            return monthlySales;
        }

        public void setMonthlySales(int monthlySales) {
            this.monthlySales = monthlySales;
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

        public long getShop_information_id() {
            return shop_information_id;
        }

        public void setShop_information_id(long shop_information_id) {
            this.shop_information_id = shop_information_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
