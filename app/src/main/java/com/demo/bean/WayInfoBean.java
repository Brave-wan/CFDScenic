package com.demo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14 0014.
 */
public class WayInfoBean {


    /**
     * status : 0
     */

    private HeaderBean header;
    /**
     * id : 1609220458008860
     * latitude : 118.332411
     * longitude : 39.186803
     * name : 曹妃甸湿地和鸟类自然保护区
     * visitors_describe : 2005年9月，经河北省人民政府批准建立的省级自然保护区。
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
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class DataBean {
        private long id;
        private String latitude;
        private String longitude;
        private String name;
        private String visitors_describe;

        public int getBobaoflag() {
            return bobaoflag;
        }

        public void setBobaoflag(int bobaoflag) {
            this.bobaoflag = bobaoflag;
        }

        private int bobaoflag;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public String getVisitors_describe() {
            return visitors_describe;
        }

        public void setVisitors_describe(String visitors_describe) {
            this.visitors_describe = visitors_describe;
        }
    }
}
