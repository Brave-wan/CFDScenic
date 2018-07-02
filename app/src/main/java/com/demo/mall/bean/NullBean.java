package com.demo.mall.bean;

/**
 * 作者：sonnerly on 2016/9/12 0012 13:53
 */
public class NullBean {

    /**
     * data : null
     * header : {"msg":"error","status":1}
     */

    private Object data;
    /**
     * msg : error
     * status : 1
     */

    private HeaderBean header;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
