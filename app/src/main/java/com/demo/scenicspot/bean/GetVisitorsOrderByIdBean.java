package com.demo.scenicspot.bean;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class GetVisitorsOrderByIdBean {

    /**
     * balance : 999898
     * end_valid : 2016-09-15
     * id : 1609081241172526
     * name : 0
     * order_code : 1609081241172525
     * order_describe : 订单描述
     * price : 200
     * quantity : 1
     * real_price : 0
     * start_valid : 2016-09-08
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
        private String end_valid;
        private long id;
        private String name;
        private String order_code;
        private String order_describe;
        private double price;
        private int quantity;
        private double real_price;
        private String start_valid;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getEnd_valid() {
            return end_valid;
        }

        public void setEnd_valid(String end_valid) {
            this.end_valid = end_valid;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public String getOrder_describe() {
            return order_describe;
        }

        public void setOrder_describe(String order_describe) {
            this.order_describe = order_describe;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getReal_price() {
            return real_price;
        }

        public void setReal_price(double real_price) {
            this.real_price = real_price;
        }

        public String getStart_valid() {
            return start_valid;
        }

        public void setStart_valid(String start_valid) {
            this.start_valid = start_valid;
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
