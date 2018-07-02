package com.demo.my.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class SelectDealMessageBean {

    /**
     * list : [{"end_valid":"2016-09-08 00:00:00","head_img":"http://192.168.1.149/images/1.jpg","id":1609071124116440,"integral":23,"name":"买家卖家所有页面的修改","orderId":1609071124089030,"pay_time":"2016-09-07 11:24:08","start_valid":"2016-09-06 00:00:00","trade_integration":23,"type":5},{"end_valid":"2016-09-08 00:00:00","head_img":"http://192.168.1.149/images/1.jpg","id":1609071137270390,"integral":23,"name":"买家卖家所有页面的修改","orderId":1609071137251260,"pay_time":"2016-09-07 11:37:25","start_valid":"2016-09-06 00:00:00","trade_integration":23,"type":5}]
     * remainingPoints : 999555
     */

    private DataBean data;
    /**
     * msg : success
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
        private int remainingPoints;
        /**
         * end_valid : 2016-09-08 00:00:00
         * head_img : http://192.168.1.149/images/1.jpg
         * id : 1609071124116440
         * integral : 23
         * name : 买家卖家所有页面的修改
         * orderId : 1609071124089030
         * pay_time : 2016-09-07 11:24:08
         * start_valid : 2016-09-06 00:00:00
         * trade_integration : 23
         * type : 5
         */

        private List<ListBean> list=new ArrayList<>();

        public int getRemainingPoints() {
            return remainingPoints;
        }

        public void setRemainingPoints(int remainingPoints) {
            this.remainingPoints = remainingPoints;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String end_valid;
            private String head_img;
            private long id;
            private int integral;
            private String name;
            private long orderId;
            private String pay_time;
            private String start_valid;
            private int trade_integration;
            private int type;

            public String getEnd_valid() {
                return end_valid;
            }

            public void setEnd_valid(String end_valid) {
                this.end_valid = end_valid;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getOrderId() {
                return orderId;
            }

            public void setOrderId(long orderId) {
                this.orderId = orderId;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public String getStart_valid() {
                return start_valid;
            }

            public void setStart_valid(String start_valid) {
                this.start_valid = start_valid;
            }

            public int getTrade_integration() {
                return trade_integration;
            }

            public void setTrade_integration(int trade_integration) {
                this.trade_integration = trade_integration;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
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
