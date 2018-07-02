package com.demo.my.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class FindGoodsOrderByIdBean {

    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * balance : 95383
     * id : 1609271016353101
     * is_deliver_fee : 0
     * is_update_price : 0
     * name : 价格排序
     * order_code : 1609271016353100
     * price : 666
     * quantity : 1
     * real_price : 668
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
        private int balance;
        private long id;
        private int is_deliver_fee;
        private int is_update_price;
        private String name;
        private String order_code;
        private int price;
        private int quantity;
        private int real_price;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getIs_deliver_fee() {
            return is_deliver_fee;
        }

        public void setIs_deliver_fee(int is_deliver_fee) {
            this.is_deliver_fee = is_deliver_fee;
        }

        public int getIs_update_price() {
            return is_update_price;
        }

        public void setIs_update_price(int is_update_price) {
            this.is_update_price = is_update_price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
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

        public int getReal_price() {
            return real_price;
        }

        public void setReal_price(int real_price) {
            this.real_price = real_price;
        }
    }
}
