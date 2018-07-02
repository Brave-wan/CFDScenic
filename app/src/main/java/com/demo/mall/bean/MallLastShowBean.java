package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/9/27 0027 16:43
 */
public class MallLastShowBean {

//{"data":[{"create_time":"2016-10-24 14:43:34","detail_address":"","id":1610240243341391,"is_deliver_fee":0,"name":"刘","order_code":"1610240243341390","pay_time":"2016-10-24 14:43:45","place_address":"","telphone":"","user_name":""},{"create_time":"2016-10-24 14:43:34","detail_address":"","id":1610240243341970,"is_deliver_fee":1,"name":"总和","order_code":"1610240243341390","pay_time":"2016-10-24 14:43:45","place_address":"","telphone":"","user_name":""}],"header":{"msg":"ok","status":0}}
    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * create_time : 2016-09-27 17:07:08
     * detail_address : 1
     * id : 1609270507084051
     * is_deliver_fee : 0
     * name : 8888
     * order_code : 1609270507084050
     * pay_time : 2016-09-27 17:07:12
     * place_address : 北京市北京市东城区
     * telphone : 11111111111
     * user_name : 1
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
        private String create_time;
        private String detail_address;
        private long id;
        private int is_deliver_fee;//0显 1只要价格和
        private String name;
        private String order_code;
        private String pay_time;
        private String place_address;
        private String telphone;
        private String user_name;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDetail_address() {
            return detail_address;
        }

        public void setDetail_address(String detail_address) {
            this.detail_address = detail_address;
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

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getPlace_address() {
            return place_address;
        }

        public void setPlace_address(String place_address) {
            this.place_address = place_address;
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
    }
}
