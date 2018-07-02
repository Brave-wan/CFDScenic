package com.demo.my.bean;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class ImmediatelyChangeBean {


    /**
     * address : {"detail_address":"五无","id":1608180852266880,"place_address":"北京市北京市东城区","postcode":null,"telphone":"15027832626","user_id":1608150443083090,"user_name":"你好"}
     * content : {"deliver_fee":10,"details":"曾经一度繁荣的国度，由于国玉的无知破害两个将士至死,两位将士死后一位化做混沌之神，一个位化做鬼神","details_url":null,"end_valid":"2016-8-03 12:00:00","goods_type":1,"head_img":"http://192.168.1.149:8080/photo/2.jpg","id":1,"integral":20,"name":"阿拉德大陆","open_date_id":2,"start_valid":"2016-8-02 12:00:00"}
     */

    private DataBean data;
    /**
     * msg : success
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
        /**
         * detail_address : 五无
         * id : 1608180852266880
         * place_address : 北京市北京市东城区
         * postcode : null
         * telphone : 15027832626
         * user_id : 1608150443083090
         * user_name : 你好
         */

        private AddressBean address;
        /**
         * deliver_fee : 10
         * details : 曾经一度繁荣的国度，由于国玉的无知破害两个将士至死,两位将士死后一位化做混沌之神，一个位化做鬼神
         * details_url : null
         * end_valid : 2016-8-03 12:00:00
         * goods_type : 1
         * head_img : http://192.168.1.149:8080/photo/2.jpg
         * id : 1
         * integral : 20
         * name : 阿拉德大陆
         * open_date_id : 2
         * start_valid : 2016-8-02 12:00:00
         */

        private ContentBean content;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public ContentBean getContent() {
            return content;
        }

        public void setContent(ContentBean content) {
            this.content = content;
        }

        public static class AddressBean {
            private String detail_address;
            private long id;
            private String place_address;
            private Object postcode;
            private String telphone;
            private long user_id;
            private String user_name;

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

            public String getPlace_address() {
                return place_address;
            }

            public void setPlace_address(String place_address) {
                this.place_address = place_address;
            }

            public Object getPostcode() {
                return postcode;
            }

            public void setPostcode(Object postcode) {
                this.postcode = postcode;
            }

            public String getTelphone() {
                return telphone;
            }

            public void setTelphone(String telphone) {
                this.telphone = telphone;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }

        public static class ContentBean {
            private int deliver_fee;
            private String details;
            private Object details_url;
            private String end_valid;
            private int goods_type;
            private String head_img;
            private int id;
            private int integral;
            private String name;
            private int open_date_id;
            private String start_valid;

            public int getDeliver_fee() {
                return deliver_fee;
            }

            public void setDeliver_fee(int deliver_fee) {
                this.deliver_fee = deliver_fee;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public Object getDetails_url() {
                return details_url;
            }

            public void setDetails_url(Object details_url) {
                this.details_url = details_url;
            }

            public String getEnd_valid() {
                return end_valid;
            }

            public void setEnd_valid(String end_valid) {
                this.end_valid = end_valid;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOpen_date_id() {
                return open_date_id;
            }

            public void setOpen_date_id(int open_date_id) {
                this.open_date_id = open_date_id;
            }

            public String getStart_valid() {
                return start_valid;
            }

            public void setStart_valid(String start_valid) {
                this.start_valid = start_valid;
            }
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
