package com.demo.my.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class IntegralGoodsDetailBean  implements Serializable {

    /**
     * deliver_fee : 34
     * end_valid : 2016-09-08
     * head_img : http://192.168.1.149/images/1.jpg
     * html_url : http://www.bai.com
     * id : 5
     * integral : 23
     * name : 5
     * name_en : 5
     * start_valid : 2016-09-06
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

    public static class DataBean implements Serializable{
        private int deliver_fee;
        private String end_valid;
        private String head_img;
        private String html_url;
        private long id;
        private int integral;
        private String name;
        private String name_en;
        private String start_valid;

        public int getDeliver_fee() {
            return deliver_fee;
        }

        public void setDeliver_fee(int deliver_fee) {
            this.deliver_fee = deliver_fee;
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

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
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

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getStart_valid() {
            return start_valid;
        }

        public void setStart_valid(String start_valid) {
            this.start_valid = start_valid;
        }
    }

    public static class HeaderBean implements Serializable{
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
