package com.demo.my.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class IntegralShopGoodsBean {

    /**
     * goodsList : [{"deliver_fee":10,"head_img":"http://192.168.1.149/images/1.jpg","id":4,"integral":60,"name":"1","name_en":"4","type":4},{"deliver_fee":34,"head_img":"http://192.168.1.149/images/1.jpg","id":5,"integral":23,"name":"5","name_en":"5","type":5}]
     * integration : 300
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
        private int integration;
        /**
         * deliver_fee : 10
         * head_img : http://192.168.1.149/images/1.jpg
         * id : 4
         * integral : 60
         * name : 1
         * name_en : 4
         * type : 4
         */

        private List<GoodsListBean> goodsList=new ArrayList<>();

        public int getIntegration() {
            return integration;
        }

        public void setIntegration(int integration) {
            this.integration = integration;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            private int deliver_fee;
            private String head_img;
            private long id;
            private int integral;
            private String name;
            private String name_en;
            private int type;

            public int getDeliver_fee() {
                return deliver_fee;
            }

            public void setDeliver_fee(int deliver_fee) {
                this.deliver_fee = deliver_fee;
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

            public String getName_en() {
                return name_en;
            }

            public void setName_en(String name_en) {
                this.name_en = name_en;
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
