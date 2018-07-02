package com.demo.my.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class GetShopCartByUserIdBean implements Serializable{


    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * goodsList : [{"deliverFee":2,"goods_describe":"小虾不好吃","goods_name":"小虾","new_price":200,"number":1,"price":200,"shopCartId":1609231257589737,"shopGoodsId":3,"shopGoodsImg":"http://192.168.1.149/images/1.jpg","shopInformationId":3,"stock":20,"user_id":1609190445546590}]
     * name : 1213131
     * shopInformationId : 3
     * shopInformationImg : http://192.168.1.149/images/1.jpg
     * userId : 1609190445546590
     */

    private List<DataBean> data;

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
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

    public static class DataBean {
        private String name;
        private long shopInformationId;
        private String shopInformationImg;
        private long userId;
        private double deliverFeeSum=0;//费送费的和
        private double total=0;//选中商品的和
        private int isPickup=0;//是否自提     0否，1是

        public int getIsPickup() {
            return isPickup;
        }

        public void setIsPickup(int isPickup) {
            this.isPickup = isPickup;
        }

        public double getDeliverFeeSum() {
            return deliverFeeSum;
        }

        public void setDeliverFeeSum(double deliverFeeSum) {
            this.deliverFeeSum = deliverFeeSum;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        /**
         * deliverFee : 2
         * goods_describe : 小虾不好吃
         * goods_name : 小虾
         * new_price : 200
         * number : 1
         * price : 200
         * shopCartId : 1609231257589737
         * shopGoodsId : 3
         * shopGoodsImg : http://192.168.1.149/images/1.jpg
         * shopInformationId : 3
         * stock : 20
         * user_id : 1609190445546590
         */

        private List<GoodsListBean> goodsList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getShopInformationId() {
            return shopInformationId;
        }

        public void setShopInformationId(long shopInformationId) {
            this.shopInformationId = shopInformationId;
        }

        public String getShopInformationImg() {
            return shopInformationImg;
        }

        public void setShopInformationImg(String shopInformationImg) {
            this.shopInformationImg = shopInformationImg;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            private int deliverFee;
            private String goods_describe;
            private String goods_name;
            private int new_price;
            private int number;
            private int price;
            private long shopCartId;
            private long shopGoodsId;
            private String shopGoodsImg;
            private long shopInformationId;
            private int stock;
            private long user_id;
            private int state=1;//状态，是否选中  商品   0是，1否




            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }


            public int getDeliverFee() {
                return deliverFee;
            }

            public void setDeliverFee(int deliverFee) {
                this.deliverFee = deliverFee;
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

            public int getNew_price() {
                return new_price;
            }

            public void setNew_price(int new_price) {
                this.new_price = new_price;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public long getShopCartId() {
                return shopCartId;
            }

            public void setShopCartId(long shopCartId) {
                this.shopCartId = shopCartId;
            }

            public long getShopGoodsId() {
                return shopGoodsId;
            }

            public void setShopGoodsId(long shopGoodsId) {
                this.shopGoodsId = shopGoodsId;
            }

            public String getShopGoodsImg() {
                return shopGoodsImg;
            }

            public void setShopGoodsImg(String shopGoodsImg) {
                this.shopGoodsImg = shopGoodsImg;
            }

            public long getShopInformationId() {
                return shopInformationId;
            }

            public void setShopInformationId(long shopInformationId) {
                this.shopInformationId = shopInformationId;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }
        }
    }
}
