package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class FindOrderBean {

    /**
     * lastPage : 3
     * rows : [{"describe_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1476265433977sc13-tu6@3x.png","end_date":"2016-10-14 00:00:00","goodsName":"总统套房","id":1610131206473141,"name":"刘","order_code":"1610131206473142","order_describe":"温馨  舒适","order_state":2,"pay_state":1,"price":1000,"real_price":1000,"start_date":"2016-10-13 00:00:00"},{"describe_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1476265433977sc13-tu6@3x.png","end_date":"2016-10-14 00:00:00","goodsName":"总统套房","id":1610131206473145,"name":"刘","order_code":"1610131206473146","order_describe":"温馨  舒适","order_state":2,"pay_state":1,"price":1000,"real_price":1000,"start_date":"2016-10-13 00:00:00"}]
     * total : 6
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
        private int lastPage;
        private int total;
        /**
         * describe_img : http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1476265433977sc13-tu6@3x.png
         * end_date : 2016-10-14 00:00:00
         * goodsName : 总统套房
         * id : 1610131206473141
         * name : 刘
         * order_code : 1610131206473142
         * order_describe : 温馨  舒适
         * order_state : 2
         * pay_state : 1
         * price : 1000
         * real_price : 1000
         * start_date : 2016-10-13 00:00:00
         */

        private List<RowsBean> rows=new ArrayList<>();

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

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            private String describe_img;
            private String end_date;
            private String goodsName;
            private long id;
            private String name;
            private String order_code;
            private String order_describe;
            private int order_state;
            private int pay_state;
            private int price;
            private int real_price;
            private String start_date;

            public String getDescribe_img() {
                return describe_img;
            }

            public void setDescribe_img(String describe_img) {
                this.describe_img = describe_img;
            }

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getReal_price() {
                return real_price;
            }

            public void setReal_price(int real_price) {
                this.real_price = real_price;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
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
