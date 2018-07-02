package com.demo.scenicspot.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class CarouselImgBean {


    /**
     * msg : success
     * status : 0
     */

    private HeaderBean header;
    /**
     * advert_describe : 这是水
     * img_url : http://192.168.1.149:8080/photo/1.jpg
     * link_id : 1
     * memo : 这是轮播图
     * title : 水
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
        private String advert_describe;
        private String img_url;
        private long link_id;
        private String memo;
        private String title;
        private int advertisement_type;

        public int getAdvertisement_type() {
            return advertisement_type;
        }

        public void setAdvertisement_type(int advertisement_type) {
            this.advertisement_type = advertisement_type;
        }

        public String getAdvert_describe() {
            return advert_describe;
        }

        public void setAdvert_describe(String advert_describe) {
            this.advert_describe = advert_describe;
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

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
