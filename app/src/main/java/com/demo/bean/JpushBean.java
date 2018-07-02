package com.demo.bean;

/**
 * 作者：sonnerly on 2016/10/24 0024 15:50
 */
public class JpushBean {
//0 系统消息  1 订单消息
    /**
     * content : 您有新的消息，请注意查收！
     * goodsType : 3
     * orderCode : 0
     * siId : 0
     * type : 0
     */

    private String content;
    private String goodsType;
    private String orderCode;
    private String siId;
    private String type;
    private String detailId;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSiId() {
        return siId;
    }

    public void setSiId(String siId) {
        this.siId = siId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
