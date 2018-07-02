package com.demo.my.bean;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class selectVisitorsOrderDetailBean {

    /**
     * create_time : 2016-09-07 01:35:56
     * detail_address : null
     * end_valid : 2016-09-07 12:00:00
     * head_img : http://192.168.1.149/images/1.jpg
     * id : 1609070135566570
     * integra_price : 60
     * is_mention : null
     * name : 1
     * order_code : 1609070135566571
     * pay_time : 2016-09-07 01:35:56
     * pay_way : 4
     * place_address : null
     * real_price : 10
     * start_valid : 2016-09-06 12:00:00
     * telphone : null
     * type : 4
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
        private String detail_address;
        private String end_valid;
        private String head_img;
        private long id;
        private int integra_price;
        private int is_mention;
        private String name;
        private String order_code;
        private String pay_time;
        private int pay_way;
        private String place_address;
        private int real_price;
        private String start_valid;
        private String telphone;
        private int type;
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

        public int getIntegra_price() {
            return integra_price;
        }

        public void setIntegra_price(int integra_price) {
            this.integra_price = integra_price;
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

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public int getPay_way() {
            return pay_way;
        }

        public void setPay_way(int pay_way) {
            this.pay_way = pay_way;
        }

        public String getPlace_address() {
            return place_address;
        }

        public void setPlace_address(String place_address) {
            this.place_address = place_address;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
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
