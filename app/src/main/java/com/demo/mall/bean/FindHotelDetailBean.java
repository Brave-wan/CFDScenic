package com.demo.mall.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class FindHotelDetailBean {


    /**
     * hotelDetail : {"address":"河北省唐山市曹妃甸区","backgroud_img":"http://192.168.1.115:8081/img/getWebImg?imageUrl=1476856926348sy_op_tu1.jpg","content":"这里是内容介绍","detailUrl":"http://192.168.1.149/cfdScenic/scripts/upload/1478140901943travel.html","end_date":"2016-10-08 00:00:00","head_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1612509182325036.jpg","id":1610120531513030,"is_blss":1,"is_food":1,"is_media":1,"is_wifi":1,"is_yushi":1,"latitude":null,"longitude":null,"name":"刘","phone":"0315-0000000","shop_id":1,"start_date":"2016-10-01 00:00:00"}
     * hotelPic : ["http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1612509181989492.jpg","http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1612509181994617.jpg","http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1612509182002691.jpg"]
     * shopGoods : [{"describe_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1476265364847sc13-tu4@3x.png","goods_describe":"hello","goods_name":"oneHome","id":1610120542451750,"isReservation":0,"new_price":8888,"price":9999,"shop_information_id":1610120531513030,"stock":9},{"describe_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1476265433977sc13-tu6@3x.png","goods_describe":"温馨  舒适","goods_name":"总统套房","id":1610120543543200,"isReservation":0,"new_price":1000,"price":10000,"shop_information_id":1610120531513030,"stock":5},{"describe_img":null,"goods_describe":"这里是房间描述","goods_name":"标准双人间","id":1611031116208130,"isReservation":1,"new_price":90,"price":100,"shop_information_id":1610120531513030,"stock":1}]
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
        /**
         * address : 河北省唐山市曹妃甸区
         * backgroud_img : http://192.168.1.115:8081/img/getWebImg?imageUrl=1476856926348sy_op_tu1.jpg
         * content : 这里是内容介绍
         * detailUrl : http://192.168.1.149/cfdScenic/scripts/upload/1478140901943travel.html
         * end_date : 2016-10-08 00:00:00
         * head_img : http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1612509182325036.jpg
         * id : 1610120531513030
         * is_blss : 1
         * is_food : 1
         * is_media : 1
         * is_wifi : 1
         * is_yushi : 1
         * latitude : null
         * longitude : null
         * name : 刘
         * phone : 0315-0000000
         * shop_id : 1
         * start_date : 2016-10-01 00:00:00
         */

        private HotelDetailBean hotelDetail;
        private List<String> hotelPic;
        /**
         * describe_img : http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1476265364847sc13-tu4@3x.png
         * goods_describe : hello
         * goods_name : oneHome
         * id : 1610120542451750
         * isReservation : 0
         * new_price : 8888
         * price : 9999
         * shop_information_id : 1610120531513030
         * stock : 9
         */

        private List<ShopGoodsBean> shopGoods;

        public HotelDetailBean getHotelDetail() {
            return hotelDetail;
        }

        public void setHotelDetail(HotelDetailBean hotelDetail) {
            this.hotelDetail = hotelDetail;
        }

        public List<String> getHotelPic() {
            return hotelPic;
        }

        public void setHotelPic(List<String> hotelPic) {
            this.hotelPic = hotelPic;
        }

        public List<ShopGoodsBean> getShopGoods() {
            return shopGoods;
        }

        public void setShopGoods(List<ShopGoodsBean> shopGoods) {
            this.shopGoods = shopGoods;
        }

        public static class HotelDetailBean {
            private String address;
            private String backgroud_img;
            private String content;
            private String detailUrl;
            private String end_date;
            private String head_img;
            private long id;
            private int is_blss;
            private int is_food;
            private int is_media;
            private int is_wifi;
            private int is_yushi;
            private Object latitude;
            private Object longitude;
            private String name;
            private String phone;
            private int shop_id;
            private String start_date;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBackgroud_img() {
                return backgroud_img;
            }

            public void setBackgroud_img(String backgroud_img) {
                this.backgroud_img = backgroud_img;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDetailUrl() {
                return detailUrl;
            }

            public void setDetailUrl(String detailUrl) {
                this.detailUrl = detailUrl;
            }

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
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

            public int getIs_blss() {
                return is_blss;
            }

            public void setIs_blss(int is_blss) {
                this.is_blss = is_blss;
            }

            public int getIs_food() {
                return is_food;
            }

            public void setIs_food(int is_food) {
                this.is_food = is_food;
            }

            public int getIs_media() {
                return is_media;
            }

            public void setIs_media(int is_media) {
                this.is_media = is_media;
            }

            public int getIs_wifi() {
                return is_wifi;
            }

            public void setIs_wifi(int is_wifi) {
                this.is_wifi = is_wifi;
            }

            public int getIs_yushi() {
                return is_yushi;
            }

            public void setIs_yushi(int is_yushi) {
                this.is_yushi = is_yushi;
            }

            public Object getLatitude() {
                return latitude;
            }

            public void setLatitude(Object latitude) {
                this.latitude = latitude;
            }

            public Object getLongitude() {
                return longitude;
            }

            public void setLongitude(Object longitude) {
                this.longitude = longitude;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }
        }

        public static class ShopGoodsBean {
            private String describe_img;
            private String goods_describe;
            private String goods_name;
            private long id;
            private int isReservation;
            private int new_price;
            private int price;
            private long shop_information_id;
            private int stock;

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

            public int getIsReservation() {
                return isReservation;
            }

            public void setIsReservation(int isReservation) {
                this.isReservation = isReservation;
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
