package com.demo.amusement.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2017/1/11 0011 09:08
 */
public class ThreeBridBean {

    /**
     * msg : success
     * status : 0
     */

    private HeaderBean header;
    /**
     * advert_describe : 观鸟景点
     * advertisement_type : 7
     * img_url : https://www.cfdzhsd.com/cfdScenic/img/getWebImg?imageUrl=1702584550515407.png
     * link_id : 1234551009480430
     * title : 观鸟景点
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
        private int advertisement_type;
        private String img_url;
        private long link_id;
        private String title;

        public String getAdvert_describe() {
            return advert_describe;
        }

        public void setAdvert_describe(String advert_describe) {
            this.advert_describe = advert_describe;
        }

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
