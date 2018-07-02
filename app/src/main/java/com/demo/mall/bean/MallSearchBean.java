package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/9/14 0014 10:19
 */
public class MallSearchBean {

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
        /**
         * goods_name : 大酒店666
         * id : 1
         * type : 1
         */

        private List<ShopGoodsMapBean> shopGoodsMap=new ArrayList<>();
        /**
         * id : 5
         * name : 双人床大房间
         * shopGroupId : 1
         */

        private List<ShopUserMapBean> shopUserMap=new ArrayList<>();

        public List<ShopGoodsMapBean> getShopGoodsMap() {
            return shopGoodsMap;
        }

        public void setShopGoodsMap(List<ShopGoodsMapBean> shopGoodsMap) {
            this.shopGoodsMap = shopGoodsMap;
        }

        public List<ShopUserMapBean> getShopUserMap() {
            return shopUserMap;
        }

        public void setShopUserMap(List<ShopUserMapBean> shopUserMap) {
            this.shopUserMap = shopUserMap;
        }

        public static class ShopGoodsMapBean {
            private String goods_name;
            private long id;
            private int type;//商品类型（1酒店，2特产，3饭店）
            private String describe_img;
            private int monthlySales;
            private int new_price;
            private int is_hot;

            public int getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(int is_hot) {
                this.is_hot = is_hot;
            }

            public String getDescribe_img() {
                return describe_img;
            }

            public void setDescribe_img(String describe_img) {
                this.describe_img = describe_img;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class ShopUserMapBean {



            private long informationId;
            private String name;
            private long shopGroupId;//店铺类型（1酒店，2饭店，3特产，4小吃）
            private String backgroudImg;
            /**
             * consumption : 0
             * content :
             */

            private int consumption;
            private String content;

            public String getBackgroudImg() {
                return backgroudImg;
            }

            public void setBackgroudImg(String backgroudImg) {
                this.backgroudImg = backgroudImg;
            }

            public long getInformationId() {
                return informationId;
            }

            public void setInformationId(long informationId) {
                this.informationId = informationId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getShopGroupId() {
                return shopGroupId;
            }

            public void setShopGroupId(long shopGroupId) {
                this.shopGroupId = shopGroupId;
            }

            public int getConsumption() {
                return consumption;
            }

            public void setConsumption(int consumption) {
                this.consumption = consumption;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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
