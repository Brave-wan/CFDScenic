package com.demo.my.bean;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class GetWaitPayBean {

    /**
     * noUse : 1
     * waitPay : 3
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

    public static class DataBean {
        private int noUse;
        private int waitPay;

        public int getNoUse() {
            return noUse;
        }

        public void setNoUse(int noUse) {
            this.noUse = noUse;
        }

        public int getWaitPay() {
            return waitPay;
        }

        public void setWaitPay(int waitPay) {
            this.waitPay = waitPay;
        }
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
