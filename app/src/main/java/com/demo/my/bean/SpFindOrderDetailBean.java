package com.demo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class SpFindOrderDetailBean {

    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * address_id : 1610281032493110
     * create_time : 2016-11-05 14:15:52
     * deliver_fee : 30
     * describe_img : http://192.168.1.149/images/06.jpg
     * detail_address : 空
     * express_code : 1
     * express_name : 1
     * goodsId : 1611051206100291
     * goods_name : 曹妃甸腰果
     * head_img : http://192.168.1.149/cfdScenic/scripts/upload/1610090721239070/147799504904409805d4da69c4dcf81a89208036ba952
     * informationHeadImg : http://192.168.1.115:8081/img/getWebImg?imageUrl=1476841408609sy_op_tu2.jpg
     * informationId : 1610110743130630
     * informationName : 刘
     * is_comment : 0
     * is_deliver_fee : 0
     * is_not_return : 0
     * is_pickup : 0
     * is_update_price : 0
     * new_price : 180
     * nick_name : 刘
     * order_code : 1611051206100290
     * order_describe : 腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述腰果描述
     * order_state : 3
     * pay_state : 1
     * pay_time : 2016-11-05 14:15:56
     * pay_way : 1
     * place_address : 北京市北京市东城区
     * price : 200
     * quantity : 1
     * real_price : 210
     * refund_time : null
     * telphone : 13888888888
     * user_id : 1610090721239070
     * user_name : 刘胜
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
        private long address_id;
        private String create_time;
        private int deliver_fee;
        private String describe_img;
        private String detail_address;
        private String express_code;
        private String express_name;
        private long goodsId;
        private String goods_name;
        private String head_img;
        private String informationHeadImg;
        private long informationId;
        private String informationName;
        private int is_comment;
        private int is_deliver_fee;
        private int is_not_return;
        private int is_pickup;
        private int is_update_price;
        private int new_price;
        private String nick_name;
        private String order_code;
        private String order_describe;
        private int order_state;
        private int pay_state;
        private String pay_time;
        private int pay_way;
        private String place_address;
        private int price;
        private int quantity;
        private int real_price;
        private Object refund_time;
        private String telphone;
        private long user_id;
        private String user_name;

        public long getAddress_id() {
            return address_id;
        }

        public void setAddress_id(long address_id) {
            this.address_id = address_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public String getDetail_address() {
            return detail_address;
        }

        public void setDetail_address(String detail_address) {
            this.detail_address = detail_address;
        }

        public String getExpress_code() {
            return express_code;
        }

        public void setExpress_code(String express_code) {
            this.express_code = express_code;
        }

        public String getExpress_name() {
            return express_name;
        }

        public void setExpress_name(String express_name) {
            this.express_name = express_name;
        }

        public long getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(long goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getInformationHeadImg() {
            return informationHeadImg;
        }

        public void setInformationHeadImg(String informationHeadImg) {
            this.informationHeadImg = informationHeadImg;
        }

        public long getInformationId() {
            return informationId;
        }

        public void setInformationId(long informationId) {
            this.informationId = informationId;
        }

        public String getInformationName() {
            return informationName;
        }

        public void setInformationName(String informationName) {
            this.informationName = informationName;
        }

        public int getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
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

        public int getIs_pickup() {
            return is_pickup;
        }

        public void setIs_pickup(int is_pickup) {
            this.is_pickup = is_pickup;
        }

        public int getIs_update_price() {
            return is_update_price;
        }

        public void setIs_update_price(int is_update_price) {
            this.is_update_price = is_update_price;
        }

        public int getNew_price() {
            return new_price;
        }

        public void setNew_price(int new_price) {
            this.new_price = new_price;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
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

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public int getPay_way() {
            return pay_way;
        }

        public void setPay_way(int pay_way) {
            this.pay_way = pay_way;
        }

        public String getPlace_address() {
            return place_address;
        }

        public void setPlace_address(String place_address) {
            this.place_address = place_address;
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

        public Object getRefund_time() {
            return refund_time;
        }

        public void setRefund_time(Object refund_time) {
            this.refund_time = refund_time;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
