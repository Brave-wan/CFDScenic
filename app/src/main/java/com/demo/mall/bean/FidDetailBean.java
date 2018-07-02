package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/9/12 0012 10:01
 */
public class FidDetailBean {

    /**
     * contentUrl : http://www.bai.com
     * detail : {"content_id":1,"deliver_fee":2,"detailUrl":"http://www.bai.com","goods_describe":"大侠好吃","goods_name":"大虾","id":2,"isCollect":0,"is_hot":1,"monthlySales":23,"new_price":998,"price":200,"shop_information_id":3,"stock":20}
     * pic : ["http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg"]
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
        private String contentUrl;
        /**
         * content_id : 1
         * deliver_fee : 2
         * detailUrl : http://www.bai.com
         * goods_describe : 大侠好吃
         * goods_name : 大虾
         * id : 2
         * isCollect : 0
         * is_hot : 1
         * monthlySales : 23
         * new_price : 998
         * price : 200
         * shop_information_id : 3
         * stock : 20
         */

        private DetailBean detail;
        private List<String> pic=new ArrayList<>();

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }

        public static class DetailBean {
            private long content_id;
            private int deliver_fee;
            private String detailUrl;
            private String goods_describe;
            private String goods_name;
            private long id;
            private int isCollect;
            private int is_hot;
            private int monthlySales;
            private int new_price;
            private int price;
            private long shop_information_id;
            private int stock;
            private String informationName;
            private String head_img;

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public String getInformationName() {
                return informationName;
            }

            public void setInformationName(String informationName) {
                this.informationName = informationName;
            }
            public long getContent_id() {
                return content_id;
            }

            public void setContent_id(long content_id) {
                this.content_id = content_id;
            }

            public int getDeliver_fee() {
                return deliver_fee;
            }

            public void setDeliver_fee(int deliver_fee) {
                this.deliver_fee = deliver_fee;
            }

            public String getDetailUrl() {
                return detailUrl;
            }

            public void setDetailUrl(String detailUrl) {
                this.detailUrl = detailUrl;
            }

            public String getGoods_describe() {
                return goods_describe;
            }

            public void setGoods_describe(String goods_describe) {
                this.goods_describe = goods_describe;
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

            public int getIsCollect() {
                return isCollect;
            }

            public void setIsCollect(int isCollect) {
                this.isCollect = isCollect;
            }

            public int getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(int is_hot) {
                this.is_hot = is_hot;
            }

            public int getMonthlySales() {
                return monthlySales;
            }

            public void setMonthlySales(int monthlySales) {
                this.monthlySales = monthlySales;
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

            public long getShop_information_id() {
                return shop_information_id;
            }

            public void setShop_information_id(long shop_information_id) {
                this.shop_information_id = shop_information_id;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
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
