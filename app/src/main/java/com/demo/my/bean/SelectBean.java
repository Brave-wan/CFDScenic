package com.demo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class SelectBean {

    /**
     * rows : [{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"小虾不好吃","goods_id":3,"goods_name":"小霞","id":1608221148574740,"new_price":200,"price":200},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"大侠好吃","goods_id":2,"goods_name":"大虾","id":1608221129126130,"new_price":998,"price":200},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"大酒店666","goods_id":1,"goods_name":"大酒店阿","id":1608221110011330,"new_price":200,"price":200}]
     * total : 3
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
        private int total;
        /**
         * describe_img : http://192.168.1.149/images/1.jpg
         * goods_describe : 小虾不好吃
         * goods_id : 3
         * goods_name : 小霞
         * id : 1608221148574740
         * new_price : 200
         * price : 200
         */

        private List<RowsBean> rows;

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
            private String goods_describe;
            private long goods_id;
            private String goods_name;
            private long id;
            private int new_price;
            private int price;

            public String getDescribe_img() {
                return describe_img;
            }

            public void setDescribe_img(String describe_img) {
                this.describe_img = describe_img;
            }

            public String getGoods_describe() {
                return goods_describe;
            }

            public void setGoods_describe(String goods_describe) {
                this.goods_describe = goods_describe;
            }

            public long getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(long goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getNew_price() {
                return new_price;
            }

            public void setNew_price(int new_price) {
                this.new_price = new_price;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
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
