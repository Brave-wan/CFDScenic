package com.demo.monitor.bean;

public class MonitorVideosBean {


    /**
     * data : rtsp://120.211.5.27:8124/pag://192.168.5.151:7302:c1aca375df204c109e040c5ec824020d:0:SUB:TCP
     * header : {"msg":"成功","status":0}
     */

    private String data;
    private HeaderBean header;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
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
