package com.demo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class RefundOrderSpBean {


    /**
     * orderCount : 2
     * orderList : {"lastPage":1,"rows":[[{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸罗汉果","head_img":null,"id":1610151004022672,"is_deliver_fee":0,"is_update_price":0,"name":"曹妃甸罗汉果","new_price":130,"order_code":"1610151004022670","order_describe":"商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述","order_state":6,"pay_state":1,"price":130,"quantity":1,"real_price":170,"shopName":"刘"},{"deliver_fee":40,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸罗汉果","head_img":null,"id":1610151004022673,"is_deliver_fee":1,"is_update_price":0,"name":"总和","new_price":130,"order_code":"1610151004022670","order_describe":"总和","order_state":6,"pay_state":1,"price":0,"quantity":2,"real_price":260,"shopName":"刘"},{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸罗汉果","head_img":null,"id":1610151004022671,"is_deliver_fee":0,"is_update_price":0,"name":"曹妃甸罗汉果","new_price":130,"order_code":"1610151004022670","order_describe":"商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述","order_state":6,"pay_state":1,"price":130,"quantity":1,"real_price":170,"shopName":"刘"}],[{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸罗汉果","head_img":null,"id":1610151004022663,"is_deliver_fee":1,"is_update_price":0,"name":"总和","new_price":130,"order_code":"1610151004022661","order_describe":"总和","order_state":6,"pay_state":1,"price":0,"quantity":1,"real_price":160,"shopName":"刘"},{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸罗汉果","head_img":null,"id":1610151004022662,"is_deliver_fee":0,"is_update_price":0,"name":"刘","new_price":130,"order_code":"1610151004022661","order_describe":"商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述","order_state":6,"pay_state":1,"price":160,"quantity":1,"real_price":130,"shopName":"刘"}]],"total":5}
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
         * rows : [[{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸罗汉果","head_img":null,"id":1610151004022672,"is_deliver_fee":0,"is_update_price":0,"name":"曹妃甸罗汉果","new_price":130,"order_code":"1610151004022670","order_describe":"商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述","order_state":6,"pay_state":1,"price":130,"quantity":1,"real_price":170,"shopName":"刘"},{"deliver_fee":40,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸罗汉果","head_img":null,"id":1610151004022673,"is_deliver_fee":1,"is_update_price":0,"name":"总和","new_price":130,"order_code":"1610151004022670","order_describe":"总和","order_state":6,"pay_state":1,"price":0,"quantity":2,"real_price":260,"shopName":"刘"},{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸罗汉果","head_img":null,"id":1610151004022671,"is_deliver_fee":0,"is_update_price":0,"name":"曹妃甸罗汉果","new_price":130,"order_code":"1610151004022670","order_describe":"商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述","order_state":6,"pay_state":1,"price":130,"quantity":1,"real_price":170,"shopName":"刘"}],[{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸罗汉果","head_img":null,"id":1610151004022663,"is_deliver_fee":1,"is_update_price":0,"name":"总和","new_price":130,"order_code":"1610151004022661","order_describe":"总和","order_state":6,"pay_state":1,"price":0,"quantity":1,"real_price":160,"shopName":"刘"},{"deliver_fee":20,"describe_img":"http://192.168.1.149/images/06.jpg","goodsName":"曹妃甸罗汉果","head_img":null,"id":1610151004022662,"is_deliver_fee":0,"is_update_price":0,"name":"刘","new_price":130,"order_code":"1610151004022661","order_describe":"商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述","order_state":6,"pay_state":1,"price":160,"quantity":1,"real_price":130,"shopName":"刘"}]]
         * total : 5
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
             * deliver_fee : 20
             * describe_img : http://192.168.1.149/images/06.jpg
             * goodsName : 曹妃甸罗汉果
             * head_img : null
             * id : 1610151004022672
             * is_deliver_fee : 0
             * is_update_price : 0
             * name : 曹妃甸罗汉果
             * new_price : 130
             * order_code : 1610151004022670
             * order_describe : 商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述
             * order_state : 6
             * pay_state : 1
             * price : 130
             * quantity : 1
             * real_price : 170
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
                private int deliver_fee;
                private String describe_img;
                private String goodsName;
                private String head_img;
                private long id;
                private int is_deliver_fee;
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
