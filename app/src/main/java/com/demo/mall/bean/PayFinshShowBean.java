package com.demo.mall.bean;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class PayFinshShowBean {

    /**
     * balance : 10207
     * check_days : 1
     * end_date : 2016-09-15 12:00:00
     * goods_name : 酒店的房间
     * id : 1609140539002120
     * informationName : 123213
     * orderName : 123213
     * order_code : 1609140539002121
     * personName : 1
     * price : 200
     * quantity : 1
     * real_price : 200
     * start_date : 2016-09-14 12:00:00
     */

    private DataBean data;
    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public static class DataBean {
        private int balance;
        private int check_days;
        private String end_date;
        private String goods_name;
        private long id;
        private String informationName;
        private String orderName;
        private String order_code;
        private String personName;
        private int price;
        private int quantity;
        private int real_price;
        private String start_date;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getCheck_days() {
            return check_days;
        }

        public void setCheck_days(int check_days) {
            this.check_days = check_days;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
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

        public String getInformationName() {
            return informationName;
        }

        public void setInformationName(String informationName) {
            this.informationName = informationName;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
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

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }
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
}
