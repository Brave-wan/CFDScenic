package com.demo.monitor.bean;

public class MonitorVideosBean {


    /**
     * data : {"url":"http://47.104.238.102:6713/mag/hls/c1aca375df204c109e040c5ec824020d/1/live.m3u8"}
     * header : {"msg":"成功","status":0}
     */

    private DataBean data;
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
         * url : http://47.104.238.102:6713/mag/hls/c1aca375df204c109e040c5ec824020d/1/live.m3u8
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class HeaderBean {
        /**
         * msg : 成功
         * status : 0
         */

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
