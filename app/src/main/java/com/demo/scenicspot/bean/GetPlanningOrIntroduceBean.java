package com.demo.scenicspot.bean;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class GetPlanningOrIntroduceBean {

    /**
     * data : http://www.baidu.com
     * header : {"msg":"success","status":0}
     */

    private String data;
    /**
     * msg : success
     * status : 0
     */

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
