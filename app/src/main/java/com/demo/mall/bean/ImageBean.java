package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/10/17 0017 20:21
 */
public class ImageBean {


    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * data : ["http://192.168.1.149/cfdScenic/scripts/upload/1608150448120500/1476708871984a25fbb61cf8c46b5b6901103065bc44b"]
     * header : {"msg":"ok","status":0}
     */

    private List<String> data=new ArrayList<>();

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
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
}
