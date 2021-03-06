package com.demo.mall.bean;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class SaveRestaurantOrderBean {

    /**
     * orderCode : 1609140252185000
     * shopInformationId : 1
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
        private String orderCode;
        private long shopInformationId;

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public long getShopInformationId() {
            return shopInformationId;
        }

        public void setShopInformationId(long shopInformationId) {
            this.shopInformationId = shopInformationId;
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
