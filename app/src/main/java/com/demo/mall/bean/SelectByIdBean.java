package com.demo.mall.bean;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class SelectByIdBean {


    /**
     * address : 1
     * backgroud_img : http://192.168.1.149/images/1.jpg
     * content : 1
     * detailUrl : http://www.bai.com
     * end_date : 2016-09-09 14:12:29
     * head_img : http://192.168.1.149/images/1.jpg
     * id : 3
     * is_blss : 1
     * is_food : 1
     * is_media : 1
     * is_wifi : 1
     * is_yushi : 1
     * latitude : 38.018002
     * longitude : 114.613
     * name : 1213131
     * phone : 1
     * shop_id : 4
     * start_date : 2016-09-09 14:12:26
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
        private String address;
        private String backgroud_img;
        private String content;
        private String detailUrl;
        private String end_date;
        private String head_img;
        private long id;
        private int is_blss;
        private int is_food;
        private int is_media;
        private int is_wifi;
        private int is_yushi;
        private String latitude;
        private String longitude;
        private String name;
        private String phone;
        private long shop_id;
        private String start_date;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBackgroud_img() {
            return backgroud_img;
        }

        public void setBackgroud_img(String backgroud_img) {
            this.backgroud_img = backgroud_img;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
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

        public int getIs_blss() {
            return is_blss;
        }

        public void setIs_blss(int is_blss) {
            this.is_blss = is_blss;
        }

        public int getIs_food() {
            return is_food;
        }

        public void setIs_food(int is_food) {
            this.is_food = is_food;
        }

        public int getIs_media() {
            return is_media;
        }

        public void setIs_media(int is_media) {
            this.is_media = is_media;
        }

        public int getIs_wifi() {
            return is_wifi;
        }

        public void setIs_wifi(int is_wifi) {
            this.is_wifi = is_wifi;
        }

        public int getIs_yushi() {
            return is_yushi;
        }

        public void setIs_yushi(int is_yushi) {
            this.is_yushi = is_yushi;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getShop_id() {
            return shop_id;
        }

        public void setShop_id(long shop_id) {
            this.shop_id = shop_id;
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
