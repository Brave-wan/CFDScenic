package com.demo.scenicspot.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class PayOrderFinshBean {

    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * end_valid : 2016-09-15
     * name : 这是积分商品
     * order_code : 1609080423099230
     * type : null
     * visitorsOrderId : 1609080423099231
     */

    private List<DataBean> data= new ArrayList<>();

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

    public static class DataBean {
        private String end_valid;
        private String name;
        private String order_code;
        private Object type;
        private long visitorsOrderId;

        public String getEnd_valid() {
            return end_valid;
        }

        public void setEnd_valid(String end_valid) {
            this.end_valid = end_valid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public long getVisitorsOrderId() {
            return visitorsOrderId;
        }

        public void setVisitorsOrderId(long visitorsOrderId) {
            this.visitorsOrderId = visitorsOrderId;
        }
    }
}
