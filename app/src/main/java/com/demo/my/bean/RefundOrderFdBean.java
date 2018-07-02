package com.demo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class RefundOrderFdBean {

    /**
     * orderCount : 10
     * orderList : {"lastPage":2,"rows":[[{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140341188140,"informationName":"刘","name":"鹅肝","order_code":"1610140341187720","order_describe":"搭配红酒好吃","order_state":3,"pay_state":1,"real_price":180},{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140341187730,"informationName":"刘","name":"红酒","order_code":"1610140341187720","order_describe":"红酒，一杯红酒","order_state":3,"pay_state":1,"real_price":180}],[{"describe_img":null,"goods_type":1,"id":1610140340075851,"informationName":"刘","name":"红酒鹅肝","order_code":"1610140340075850","order_describe":"红酒鹅肝","order_state":3,"pay_state":1,"real_price":360}],[{"describe_img":null,"goods_type":1,"id":1610140339374941,"informationName":"刘","name":"红酒鹅肝","order_code":"1610140339374940","order_describe":"红酒鹅肝","order_state":3,"pay_state":1,"real_price":360}],[{"describe_img":null,"goods_type":1,"id":1610140340314280,"informationName":"刘","name":"红酒鹅肝","order_code":"1610140340314270","order_describe":"红酒鹅肝","order_state":3,"pay_state":1,"real_price":360}],[{"describe_img":null,"goods_type":1,"id":1610140339556391,"informationName":"刘","name":"红酒鹅肝","order_code":"1610140339556390","order_describe":"红酒鹅肝","order_state":3,"pay_state":1,"real_price":360}],[{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140424207770,"informationName":"刘","name":"红酒","order_code":"1610140424207760","order_describe":"红酒，一杯红酒","order_state":3,"pay_state":1,"real_price":180},{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140424208030,"informationName":"刘","name":"鹅肝","order_code":"1610140424207760","order_describe":"搭配红酒好吃","order_state":3,"pay_state":1,"real_price":180}],[{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140341347650,"informationName":"刘","name":"鹅肝","order_code":"1610140341347020","order_describe":"搭配红酒好吃","order_state":3,"pay_state":1,"real_price":180},{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140341347030,"informationName":"刘","name":"红酒","order_code":"1610140341347020","order_describe":"红酒，一杯红酒","order_state":3,"pay_state":1,"real_price":180}]],"total":11}
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
        private int orderCount;
        /**
         * lastPage : 2
         * rows : [[{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140341188140,"informationName":"刘","name":"鹅肝","order_code":"1610140341187720","order_describe":"搭配红酒好吃","order_state":3,"pay_state":1,"real_price":180},{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140341187730,"informationName":"刘","name":"红酒","order_code":"1610140341187720","order_describe":"红酒，一杯红酒","order_state":3,"pay_state":1,"real_price":180}],[{"describe_img":null,"goods_type":1,"id":1610140340075851,"informationName":"刘","name":"红酒鹅肝","order_code":"1610140340075850","order_describe":"红酒鹅肝","order_state":3,"pay_state":1,"real_price":360}],[{"describe_img":null,"goods_type":1,"id":1610140339374941,"informationName":"刘","name":"红酒鹅肝","order_code":"1610140339374940","order_describe":"红酒鹅肝","order_state":3,"pay_state":1,"real_price":360}],[{"describe_img":null,"goods_type":1,"id":1610140340314280,"informationName":"刘","name":"红酒鹅肝","order_code":"1610140340314270","order_describe":"红酒鹅肝","order_state":3,"pay_state":1,"real_price":360}],[{"describe_img":null,"goods_type":1,"id":1610140339556391,"informationName":"刘","name":"红酒鹅肝","order_code":"1610140339556390","order_describe":"红酒鹅肝","order_state":3,"pay_state":1,"real_price":360}],[{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140424207770,"informationName":"刘","name":"红酒","order_code":"1610140424207760","order_describe":"红酒，一杯红酒","order_state":3,"pay_state":1,"real_price":180},{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140424208030,"informationName":"刘","name":"鹅肝","order_code":"1610140424207760","order_describe":"搭配红酒好吃","order_state":3,"pay_state":1,"real_price":180}],[{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140341347650,"informationName":"刘","name":"鹅肝","order_code":"1610140341347020","order_describe":"搭配红酒好吃","order_state":3,"pay_state":1,"real_price":180},{"describe_img":"http://192.168.1.149/images/06.jpg","goods_type":0,"id":1610140341347030,"informationName":"刘","name":"红酒","order_code":"1610140341347020","order_describe":"红酒，一杯红酒","order_state":3,"pay_state":1,"real_price":180}]]
         * total : 11
         */

        private OrderListBean orderList;

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }

        public OrderListBean getOrderList() {
            return orderList;
        }

        public void setOrderList(OrderListBean orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            private int lastPage;
            private int total;
            /**
             * describe_img : http://192.168.1.149/images/06.jpg
             * goods_type : 0
             * id : 1610140341188140
             * informationName : 刘
             * name : 鹅肝
             * order_code : 1610140341187720
             * order_describe : 搭配红酒好吃
             * order_state : 3
             * pay_state : 1
             * real_price : 180
             */

            private List<List<RowsBean>> rows;

            public int getLastPage() {
                return lastPage;
            }

            public void setLastPage(int lastPage) {
                this.lastPage = lastPage;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<List<RowsBean>> getRows() {
                return rows;
            }

            public void setRows(List<List<RowsBean>> rows) {
                this.rows = rows;
            }

            public static class RowsBean {
                private String describe_img;
                private int goods_type;
                private long id;
                private String informationName;
                private String name;
                private String order_code;
                private String order_describe;
                private int order_state;
                private int pay_state;
                private int real_price;

                public String getDescribe_img() {
                    return describe_img;
                }

                public void setDescribe_img(String describe_img) {
                    this.describe_img = describe_img;
                }

                public int getGoods_type() {
                    return goods_type;
                }

                public void setGoods_type(int goods_type) {
                    this.goods_type = goods_type;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getInformationName() {
                    return informationName;
                }

                public void setInformationName(String informationName) {
                    this.informationName = informationName;
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

                public String getOrder_describe() {
                    return order_describe;
                }

                public void setOrder_describe(String order_describe) {
                    this.order_describe = order_describe;
                }

                public int getOrder_state() {
                    return order_state;
                }

                public void setOrder_state(int order_state) {
                    this.order_state = order_state;
                }

                public int getPay_state() {
                    return pay_state;
                }

                public void setPay_state(int pay_state) {
                    this.pay_state = pay_state;
                }

                public int getReal_price() {
                    return real_price;
                }

                public void setReal_price(int real_price) {
                    this.real_price = real_price;
                }
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
