package com.demo.scenicspot.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class FindAtlasByVisitorsIdBean {

    /**
     * msg : success
     * status : 0
     */

    private HeaderBean header;
    /**
     * data : ["http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg"]
     * header : {"msg":"success","status":0}
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
