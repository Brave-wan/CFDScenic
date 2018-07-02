package com.demo.bean;

/**
 * 作者：sonnerly on 2016/11/9 0009 09:46
 */
public class AlipayBean {

    /**
     * id : 1611080455138830
     * partner : 12356464564
     * privateKey : 123678890789078960789
     * seller : 123456456678
     * shopUserId : 1610110741510160
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
        private long id;
        private String partner;//商户ID
        private String privateKey;//私钥
        private String seller;//账户ID
        private long shopUserId;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getSeller() {
            return seller;
        }

        public void setSeller(String seller) {
            this.seller = seller;
        }

        public long getShopUserId() {
            return shopUserId;
        }

        public void setShopUserId(long shopUserId) {
            this.shopUserId = shopUserId;
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
