package com.demo.amusement.bean;

/**
 * 作者：sonnerly on 2016/9/20 0020 16:32
 */
public class DatadataBean {

    /**
     * data : 1
     * header : {"msg":"ok","status":0}
     */

    private int data;
    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
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
