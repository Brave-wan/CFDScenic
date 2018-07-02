package com.demo.amusement.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/9/30 0030 14:58
 */
public class WriteBlogBean {

    /**
     * msg : success
     * status : 0
     */

    private HeaderBean header;
    /**
     * head_img : http://192.168.1.149/images/1.jpg
     * id : 1609060753039780
     * name : qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
     * name_en : awdfasdf
     * new_price : 123
     * price : 123
     * visitors_describe : qqqqqqqqqqqqqqqqqqqqqqqq
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
        private String head_img;
        private long id;
        private String name;
        private String name_en;
        private int new_price;
        private int price;
        private String visitors_describe;
        private boolean point=false;

        public boolean getPoint() {
            return point;
        }

        public void setPoint(boolean point) {
            this.point = point;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
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

        public String getVisitors_describe() {
            return visitors_describe;
        }

        public void setVisitors_describe(String visitors_describe) {
            this.visitors_describe = visitors_describe;
        }
    }
}
