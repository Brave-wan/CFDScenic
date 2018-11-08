package com.demo.monitor.bean;

public class MonitorVideosBean {


    /**
     * data : {"url":"rtsp://120.211.5.27:8124/pag://192.168.5.151:7302:bb2b5bb90b0842fdab534498d795b603:0:SUB:TCP"}
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
         * url : rtsp://120.211.5.27:8124/pag://192.168.5.151:7302:bb2b5bb90b0842fdab534498d795b603:0:SUB:TCP
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
