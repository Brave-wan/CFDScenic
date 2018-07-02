package com.demo.my.bean;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class MyPurse {

    /**
     * create_time : 2016-09-07 09:51:04
     * end_valid : 2016-09-07 12:00:00
     * id : 1609070951041560
     * name : 1
     * order_code : 1609070951041561
     * pay_time : null
     * place_address : null
     * start_valid : 2016-09-06 12:00:00
     * telphone : null
     * user_name : null
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
        private String create_time;
        private String end_valid;
        private long id;
        private String name;
        private String order_code;
        private Object pay_time;
        private Object place_address;
        private String start_valid;
        private Object telphone;
        private Object user_name;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public Object getPay_time() {
            return pay_time;
        }

        public void setPay_time(Object pay_time) {
            this.pay_time = pay_time;
        }

        public Object getPlace_address() {
            return place_address;
        }

        public void setPlace_address(Object place_address) {
            this.place_address = place_address;
        }

        public String getStart_valid() {
            return start_valid;
        }

        public void setStart_valid(String start_valid) {
            this.start_valid = start_valid;
        }

        public Object getTelphone() {
            return telphone;
        }

        public void setTelphone(Object telphone) {
            this.telphone = telphone;
        }

        public Object getUser_name() {
            return user_name;
        }

        public void setUser_name(Object user_name) {
            this.user_name = user_name;
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
