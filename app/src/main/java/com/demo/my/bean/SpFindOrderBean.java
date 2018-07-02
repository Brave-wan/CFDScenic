package com.demo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24 0024.
 */
public class SpFindOrderBean {

    /**
     * orderCount : 3
     * orderList : {"lastPage":1,"rows":[[{"billing":0,"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸腰果","head_img":"http://192.168.1.115:8081/img/getWebImg?imageUrl=1476841408609sy_op_tu2.jpg","id":1611051206100291,"is_deliver_fee":0,"is_not_return":1,"is_update_price":0,"name":"刘","new_price":180,"order_code":"1611051206100290","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":3,"pay_state":1,"price":200,"quantity":1,"real_price":210,"shopName":"刘"}],[{"billing":0,"deliver_fee":0,"describe_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1612518386998430.jpg","goodsName":"这是特产名称","head_img":"http://192.168.1.115:8081/img/getWebImg?imageUrl=1476841408609sy_op_tu2.jpg","id":1611050346357401,"is_deliver_fee":0,"is_not_return":1,"is_update_price":0,"name":"刘","new_price":90,"order_code":"1611050346357400","order_describe":"这是商品描述","order_state":4,"pay_state":1,"price":100,"quantity":1,"real_price":90,"shopName":"刘"}],[{"billing":0,"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸腰果","head_img":"http://192.168.1.115:8081/img/getWebImg?imageUrl=1476841408609sy_op_tu2.jpg","id":1611030854434291,"is_deliver_fee":0,"is_not_return":1,"is_update_price":0,"name":"刘","new_price":180,"order_code":"1611030854434290","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":3,"pay_state":1,"price":200,"quantity":1,"real_price":210,"shopName":"刘"}]],"total":6}
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
         * lastPage : 1
         * rows : [[{"billing":0,"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸腰果","head_img":"http://192.168.1.115:8081/img/getWebImg?imageUrl=1476841408609sy_op_tu2.jpg","id":1611051206100291,"is_deliver_fee":0,"is_not_return":1,"is_update_price":0,"name":"刘","new_price":180,"order_code":"1611051206100290","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":3,"pay_state":1,"price":200,"quantity":1,"real_price":210,"shopName":"刘"}],[{"billing":0,"deliver_fee":0,"describe_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1612518386998430.jpg","goodsName":"这是特产名称","head_img":"http://192.168.1.115:8081/img/getWebImg?imageUrl=1476841408609sy_op_tu2.jpg","id":1611050346357401,"is_deliver_fee":0,"is_not_return":1,"is_update_price":0,"name":"刘","new_price":90,"order_code":"1611050346357400","order_describe":"这是商品描述","order_state":4,"pay_state":1,"price":100,"quantity":1,"real_price":90,"shopName":"刘"}],[{"billing":0,"deliver_fee":30,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸腰果","head_img":"http://192.168.1.115:8081/img/getWebImg?imageUrl=1476841408609sy_op_tu2.jpg","id":1611030854434291,"is_deliver_fee":0,"is_not_return":1,"is_update_price":0,"name":"刘","new_price":180,"order_code":"1611030854434290","order_describe":"腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述","order_state":3,"pay_state":1,"price":200,"quantity":1,"real_price":210,"shopName":"刘"}]]
         * total : 6
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
             * billing : 0
             * deliver_fee : 30
             * describe_img : http://192.168.1.149/images/06.jpg
             * goodsName : 曹妃甸腰果
             * head_img : http://192.168.1.115:8081/img/getWebImg?imageUrl=1476841408609sy_op_tu2.jpg
             * id : 1611051206100291
             * is_deliver_fee : 0
             * is_not_return : 1
             * is_update_price : 0
             * name : 刘
             * new_price : 180
             * order_code : 1611051206100290
             * order_describe : 腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述
             * order_state : 3
             * pay_state : 1
             * price : 200
             * quantity : 1
             * real_price : 210
             * shopName : 刘
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
                private int billing;
                private int deliver_fee;
                private String describe_img;
                private String goodsName;
                private String head_img;
                private long id;
                private int is_deliver_fee;
                private int is_not_return;
                private int is_update_price;
                private String name;
                private int new_price;
                private String order_code;
                private String order_describe;
                private int order_state;
                private int pay_state;
                private int price;
                private int quantity;
                private int real_price;
                private String shopName;
                private long siId;

                public long getSiId() {
                    return siId;
                }

                public void setSiId(long siId) {
                    this.siId = siId;
                }

                public int getBilling() {
                    return billing;
                }

                public void setBilling(int billing) {
                    this.billing = billing;
                }

                public int getDeliver_fee() {
                    return deliver_fee;
                }

                public void setDeliver_fee(int deliver_fee) {
                    this.deliver_fee = deliver_fee;
                }

                public String getDescribe_img() {
                    return describe_img;
                }

                public void setDescribe_img(String describe_img) {
                    this.describe_img = describe_img;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
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

                public int getIs_deliver_fee() {
                    return is_deliver_fee;
                }

                public void setIs_deliver_fee(int is_deliver_fee) {
                    this.is_deliver_fee = is_deliver_fee;
                }

                public int getIs_not_return() {
                    return is_not_return;
                }

                public void setIs_not_return(int is_not_return) {
                    this.is_not_return = is_not_return;
                }

                public int getIs_update_price() {
                    return is_update_price;
                }

                public void setIs_update_price(int is_update_price) {
                    this.is_update_price = is_update_price;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getNew_price() {
                    return new_price;
                }

                public void setNew_price(int new_price) {
                    this.new_price = new_price;
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

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public int getReal_price() {
                    return real_price;
                }

                public void setReal_price(int real_price) {
                    this.real_price = real_price;
                }

                public String getShopName() {
                    return shopName;
                }

                public void setShopName(String shopName) {
                    this.shopName = shopName;
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
