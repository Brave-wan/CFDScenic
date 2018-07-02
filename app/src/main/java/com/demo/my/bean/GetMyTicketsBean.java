package com.demo.my.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class GetMyTicketsBean {


    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
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
        private List<Long> ids;
        /**
         * address_id : 1608250643394940
         * balance : 8535
         * deliver_fee : 10
         * detail_address : 1
         * end_valid : 2016-09-21 00:00:00
         * head_img : http://192.168.1.149/images/1.jpg
         * id : 1609200217458471
         * is_invoice : 1
         * is_mention : 1
         * name : 123
         * order_code : 1609200217458470
         * order_state : 8
         * place_address : 北京市北京市东城区
         * price : 123
         * quantity : 1
         * real_price : 133
         * start_valid : 2016-09-20 00:00:00
         * telphone : 12312124124
         * user_name : 13
         * visitorsId : 1609060753039780
         */

        private List<TicketslistBean> ticketslist=new ArrayList<>();

        public List<Long> getIds() {
            return ids;
        }

        public void setIds(List<Long> ids) {
            this.ids = ids;
        }

        public List<TicketslistBean> getTicketslist() {
            return ticketslist;
        }

        public void setTicketslist(List<TicketslistBean> ticketslist) {
            this.ticketslist = ticketslist;
        }

        public static class TicketslistBean {
            private long address_id;
            private int balance;
            private int deliver_fee;
            private String detail_address;
            private String end_valid;
            private String head_img;
            private long id;
            private int is_invoice;
            private int is_mention;
            private String name;
            private String order_code;
            private int order_state;
            private String place_address;
            private int price;
            private int quantity;
            private int real_price;
            private String start_valid;
            private String telphone;
            private String user_name;
            private long visitorsId;

            public long getAddress_id() {
                return address_id;
            }

            public void setAddress_id(long address_id) {
                this.address_id = address_id;
            }

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public int getDeliver_fee() {
                return deliver_fee;
            }

            public void setDeliver_fee(int deliver_fee) {
                this.deliver_fee = deliver_fee;
            }

            public String getDetail_address() {
                return detail_address;
            }

            public void setDetail_address(String detail_address) {
                this.detail_address = detail_address;
            }

            public String getEnd_valid() {
                return end_valid;
            }

            public void setEnd_valid(String end_valid) {
                this.end_valid = end_valid;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getIs_invoice() {
                return is_invoice;
            }

            public void setIs_invoice(int is_invoice) {
                this.is_invoice = is_invoice;
            }

            public int getIs_mention() {
                return is_mention;
            }

            public void setIs_mention(int is_mention) {
                this.is_mention = is_mention;
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

            public int getOrder_state() {
                return order_state;
            }

            public void setOrder_state(int order_state) {
                this.order_state = order_state;
            }

            public String getPlace_address() {
                return place_address;
            }

            public void setPlace_address(String place_address) {
                this.place_address = place_address;
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

            public String getStart_valid() {
                return start_valid;
            }

            public void setStart_valid(String start_valid) {
                this.start_valid = start_valid;
            }

            public String getTelphone() {
                return telphone;
            }

            public void setTelphone(String telphone) {
                this.telphone = telphone;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public long getVisitorsId() {
                return visitorsId;
            }

            public void setVisitorsId(long visitorsId) {
                this.visitorsId = visitorsId;
            }
        }
    }
}
