package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class FindPicBean {

    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * img_url : http://192.168.1.149:8080/photo/1.jpg
     * link_id : 1
     * title : 大虾
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
        private String img_url;
        private long link_id;
        private String title;
        private int advertisement_type;

        public int getAdvertisement_type() {
            return advertisement_type;
        }

        public void setAdvertisement_type(int advertisement_type) {
            this.advertisement_type = advertisement_type;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public long getLink_id() {
            return link_id;
        }

        public void setLink_id(long link_id) {
            this.link_id = link_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
