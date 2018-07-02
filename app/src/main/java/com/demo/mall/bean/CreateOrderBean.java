package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class CreateOrderBean {

    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * addressId : 1609200516487860
     * createTime : 2016-09-24 15:02:45
     * deliverDate : null
     * deliverFee : 2
     * goodsId : 3
     * id : 1609240302457501
     * isComment : 0
     * isDeliverFee : 0
     * isPickup : 1
     * isUpdatePrice : null
     * name : 小霞
     * orderCode : 1609240302457500
     * orderDescribe : 小虾不好吃
     * orderState : 1
     * payState : 0
     * payTime : null
     * payWay : 0
     * price : 200
     * quantity : 1
     * realPrice : 200
     * refundTime : null
     * shopInformationId : 3
     * userId : 1609190445546590
     */

    private List<List<DataBean>> data=new ArrayList<>();

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
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
        private long addressId;
        private String createTime;
        private String deliverDate;
        private int deliverFee;
        private long goodsId;
        private long id;
        private int isComment;
        private int isDeliverFee;
        private int isPickup;
        private String isUpdatePrice;
        private String name;
        private String orderCode;
        private String orderDescribe;
        private int orderState;
        private int payState;
        private String payTime;
        private int payWay;
        private int price;
        private int quantity;
        private int realPrice;
        private String refundTime;
        private long shopInformationId;
        private long userId;

        public long getAddressId() {
            return addressId;
        }

        public void setAddressId(long addressId) {
            this.addressId = addressId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeliverDate() {
            return deliverDate;
        }

        public void setDeliverDate(String deliverDate) {
            this.deliverDate = deliverDate;
        }

        public int getDeliverFee() {
            return deliverFee;
        }

        public void setDeliverFee(int deliverFee) {
            this.deliverFee = deliverFee;
        }

        public long getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(long goodsId) {
            this.goodsId = goodsId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getIsComment() {
            return isComment;
        }

        public void setIsComment(int isComment) {
            this.isComment = isComment;
        }

        public int getIsDeliverFee() {
            return isDeliverFee;
        }

        public void setIsDeliverFee(int isDeliverFee) {
            this.isDeliverFee = isDeliverFee;
        }

        public int getIsPickup() {
            return isPickup;
        }

        public void setIsPickup(int isPickup) {
            this.isPickup = isPickup;
        }

        public String getIsUpdatePrice() {
            return isUpdatePrice;
        }

        public void setIsUpdatePrice(String isUpdatePrice) {
            this.isUpdatePrice = isUpdatePrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOrderDescribe() {
            return orderDescribe;
        }

        public void setOrderDescribe(String orderDescribe) {
            this.orderDescribe = orderDescribe;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public int getPayState() {
            return payState;
        }

        public void setPayState(int payState) {
            this.payState = payState;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public int getPayWay() {
            return payWay;
        }

        public void setPayWay(int payWay) {
            this.payWay = payWay;
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

        public int getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(int realPrice) {
            this.realPrice = realPrice;
        }

        public String getRefundTime() {
            return refundTime;
        }

        public void setRefundTime(String refundTime) {
            this.refundTime = refundTime;
        }

        public long getShopInformationId() {
            return shopInformationId;
        }

        public void setShopInformationId(int shopInformationId) {
            this.shopInformationId = shopInformationId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }
    }
}
