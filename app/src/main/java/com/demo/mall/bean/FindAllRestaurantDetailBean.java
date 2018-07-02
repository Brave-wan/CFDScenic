package com.demo.mall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class FindAllRestaurantDetailBean implements Serializable{
    /**
     * packageList : [{"head_img":"http://192.168.1.149/images/1.jpg","id":1029384756120395,"name":"这是套餐商品","new_price":180,"price":200}]
     * restaurantDetail : {"backgroud_img":"http://192.168.1.149/images/1.jpg","detailUrl":"http://www.baidu.com","head_img":"http://192.168.1.149/images/1.jpg","id":1,"latitude":"38.018002","longitude":"114.613","name":"111","phone":"1231231231","shop_id":2}
     * restaurantPic : ["http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/0.jpg"]
     * shopGoods : [{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"王婆大虾 88 一份，小葱拌豆腐 108 1份，天价包子 9999 1份","goods_name":"王婆大虾","id":6,"is_hot":0,"monthlySales":0,"new_price":55555,"price":66666,"shop_information_id":1,"stock":99},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"这就是豆腐","goods_name":"豆腐豆腐","id":7,"is_hot":0,"monthlySales":0,"new_price":100,"price":200,"shop_information_id":1,"stock":99},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"麻辣香锅","goods_name":"麻辣香锅","id":12,"is_hot":0,"monthlySales":0,"new_price":23,"price":25,"shop_information_id":1,"stock":40},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"鱼香肉丝","goods_name":"鱼香肉丝","id":13,"is_hot":0,"monthlySales":0,"new_price":15,"price":18,"shop_information_id":1,"stock":40},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"四喜丸子","goods_name":"四喜丸子","id":14,"is_hot":0,"monthlySales":0,"new_price":40,"price":45,"shop_information_id":1,"stock":40}]
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

    public static class DataBean implements Serializable{
        /**
         * backgroud_img : http://192.168.1.149/images/1.jpg
         * detailUrl : http://www.baidu.com
         * head_img : http://192.168.1.149/images/1.jpg
         * id : 1
         * latitude : 38.018002
         * longitude : 114.613
         * name : 111
         * phone : 1231231231
         * shop_id : 2
         */

        private RestaurantDetailBean restaurantDetail=new RestaurantDetailBean();
        /**
         * head_img : http://192.168.1.149/images/1.jpg
         * id : 1029384756120395
         * name : 这是套餐商品
         * new_price : 180
         * price : 200
         */

        private List<PackageListBean> packageList=new ArrayList<>();
        private List<String> restaurantPic=new ArrayList<>();
        /**
         * describe_img : http://192.168.1.149/images/1.jpg
         * goods_describe : 王婆大虾 88 一份，小葱拌豆腐 108 1份，天价包子 9999 1份
         * goods_name : 王婆大虾
         * id : 6
         * is_hot : 0
         * monthlySales : 0
         * new_price : 55555
         * price : 66666
         * shop_information_id : 1
         * stock : 99
         */

        private List<ShopGoodsBean> shopGoods=new ArrayList<>();

        public RestaurantDetailBean getRestaurantDetail() {
            return restaurantDetail;
        }

        public void setRestaurantDetail(RestaurantDetailBean restaurantDetail) {
            this.restaurantDetail = restaurantDetail;
        }

        public List<PackageListBean> getPackageList() {
            return packageList;
        }

        public void setPackageList(List<PackageListBean> packageList) {
            this.packageList = packageList;
        }

        public List<String> getRestaurantPic() {
            return restaurantPic;
        }

        public void setRestaurantPic(List<String> restaurantPic) {
            this.restaurantPic = restaurantPic;
        }

        public List<ShopGoodsBean> getShopGoods() {
            return shopGoods;
        }

        public void setShopGoods(List<ShopGoodsBean> shopGoods) {
            this.shopGoods = shopGoods;
        }

        public static class RestaurantDetailBean implements Serializable{
            private String backgroud_img;
            private String detailUrl;
            private String head_img;
            private long id;
            private String latitude;
            private String longitude;
            private String name;
            private String phone;
            private long shop_id;
            private String address;
            private int is_blss;
            private int is_food;
            private int is_media;
            private int is_wifi;
            private int is_yushi;

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

            public String getDetailUrl() {
                return detailUrl;
            }

            public void setDetailUrl(String detailUrl) {
                this.detailUrl = detailUrl;
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

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
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

            public long getShop_id() {
                return shop_id;
            }

            public void setShop_id(long shop_id) {
                this.shop_id = shop_id;
            }
        }

        public static class PackageListBean implements Serializable{
            private String head_img;
            private long id;
            private String name;
            private int new_price;
            private int price;

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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }

        public static class ShopGoodsBean implements Serializable{
            private String describe_img;
            private String goods_describe;
            private String goods_name;
            private long id;
            private int is_hot;
            private int monthlySales;
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

    public static class HeaderBean implements Serializable{
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

//    /**
//     * packageList : [{"head_img":"http://192.168.1.149/images/1.jpg","id":1029384756120395,"name":"这是套餐商品","new_price":180,"price":200}]
//     * restaurantDetail : {"address":null,"backgroud_img":"http://192.168.1.149/images/1.jpg","content":null,"detailUrl":"http://www.baidu.com","end_date":null,"head_img":"http://192.168.1.149/images/1.jpg","id":1,"is_blss":null,"is_food":null,"is_media":null,"is_wifi":null,"is_yushi":null,"latitude":"38.018002","longitude":"114.613","name":"111","phone":"1231231231","shop_id":2,"start_date":null}
//     * restaurantPic : ["http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg"]
//     * shopGoods : [{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"王婆大虾 88 一份，小葱拌豆腐 108 1份，天价包子 9999 1份","goods_name":"王婆大虾","goods_type":1,"id":6,"is_hot":0,"monthlySales":0,"new_price":55555,"price":66666,"shop_information_id":1,"stock":99},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"这就是豆腐","goods_name":"豆腐豆腐","goods_type":0,"id":7,"is_hot":0,"monthlySales":0,"new_price":100,"price":200,"shop_information_id":1,"stock":99},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"麻辣香锅","goods_name":"麻辣香锅","goods_type":0,"id":12,"is_hot":0,"monthlySales":0,"new_price":23,"price":25,"shop_information_id":1,"stock":40},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"鱼香肉丝","goods_name":"鱼香肉丝","goods_type":0,"id":13,"is_hot":0,"monthlySales":0,"new_price":15,"price":18,"shop_information_id":1,"stock":40},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"四喜丸子","goods_name":"四喜丸子","goods_type":0,"id":14,"is_hot":0,"monthlySales":0,"new_price":40,"price":45,"shop_information_id":1,"stock":40},{"describe_img":"http://192.168.1.149/images/1.jpg","goods_describe":"满汉全席","goods_name":"满汉全席","goods_type":0,"id":15,"is_hot":1,"monthlySales":0,"new_price":998,"price":998,"shop_information_id":1,"stock":40}]
//     */
//
//    private DataBean data;
//    /**
//     * msg : ok
//     * status : 0
//     */
//
//    private HeaderBean header;
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public HeaderBean getHeader() {
//        return header;
//    }
//
//    public void setHeader(HeaderBean header) {
//        this.header = header;
//    }
//
//    public static class DataBean implements Serializable {
//        /**
//         * address : null
//         * backgroud_img : http://192.168.1.149/images/1.jpg
//         * content : null
//         * detailUrl : http://www.baidu.com
//         * end_date : null
//         * head_img : http://192.168.1.149/images/1.jpg
//         * id : 1
//         * is_blss : null
//         * is_food : null
//         * is_media : null
//         * is_wifi : null
//         * is_yushi : null
//         * latitude : 38.018002
//         * longitude : 114.613
//         * name : 111
//         * phone : 1231231231
//         * shop_id : 2
//         * start_date : null
//         */
//
//        private RestaurantDetailBean restaurantDetail=new RestaurantDetailBean();
//        /**
//         * head_img : http://192.168.1.149/images/1.jpg
//         * id : 1029384756120395
//         * name : 这是套餐商品
//         * new_price : 180
//         * price : 200
//         */
//
//        private List<PackageListBean> packageList=new ArrayList<>();
//        private List<String> restaurantPic=new ArrayList<>();
//        /**
//         * describe_img : http://192.168.1.149/images/1.jpg
//         * goods_describe : 王婆大虾 88 一份，小葱拌豆腐 108 1份，天价包子 9999 1份
//         * goods_name : 王婆大虾
//         * goods_type : 1
//         * id : 6
//         * is_hot : 0
//         * monthlySales : 0
//         * new_price : 55555
//         * price : 66666
//         * shop_information_id : 1
//         * stock : 99
//         */
//
//        private List<ShopGoodsBean> shopGoods=new ArrayList<>();
//
//        public RestaurantDetailBean getRestaurantDetail() {
//            return restaurantDetail;
//        }
//
//        public void setRestaurantDetail(RestaurantDetailBean restaurantDetail) {
//            this.restaurantDetail = restaurantDetail;
//        }
//
//        public List<PackageListBean> getPackageList() {
//            return packageList;
//        }
//
//        public void setPackageList(List<PackageListBean> packageList) {
//            this.packageList = packageList;
//        }
//
//        public List<String> getRestaurantPic() {
//            return restaurantPic;
//        }
//
//        public void setRestaurantPic(List<String> restaurantPic) {
//            this.restaurantPic = restaurantPic;
//        }
//
//        public List<ShopGoodsBean> getShopGoods() {
//            return shopGoods;
//        }
//
//        public void setShopGoods(List<ShopGoodsBean> shopGoods) {
//            this.shopGoods = shopGoods;
//        }
//
//        public static class RestaurantDetailBean implements Serializable{
//            private Object address;
//            private String backgroud_img;
//            private String content;
//            private String detailUrl;
//            private Object end_date;
//            private String head_img;
//            private long id;
//            private int is_blss;
//            private int is_food;
//            private int is_media;
//            private int is_wifi;
//            private int is_yushi;
//            private String latitude;
//            private String longitude;
//            private String name;
//            private String phone;
//            private long shop_id;
//            private Object start_date;
//
//            public Object getAddress() {
//                return address;
//            }
//
//            public void setAddress(Object address) {
//                this.address = address;
//            }
//
//            public String getBackgroud_img() {
//                return backgroud_img;
//            }
//
//            public void setBackgroud_img(String backgroud_img) {
//                this.backgroud_img = backgroud_img;
//            }
//
//            public String getContent() {
//                return content;
//            }
//
//            public void setContent(String content) {
//                this.content = content;
//            }
//
//            public String getDetailUrl() {
//                return detailUrl;
//            }
//
//            public void setDetailUrl(String detailUrl) {
//                this.detailUrl = detailUrl;
//            }
//
//            public Object getEnd_date() {
//                return end_date;
//            }
//
//            public void setEnd_date(Object end_date) {
//                this.end_date = end_date;
//            }
//
//            public String getHead_img() {
//                return head_img;
//            }
//
//            public void setHead_img(String head_img) {
//                this.head_img = head_img;
//            }
//
//            public long getId() {
//                return id;
//            }
//
//            public void setId(long id) {
//                this.id = id;
//            }
//
//            public int getIs_blss() {
//                return is_blss;
//            }
//
//            public void setIs_blss(int is_blss) {
//                this.is_blss = is_blss;
//            }
//
//            public int getIs_food() {
//                return is_food;
//            }
//
//            public void setIs_food(int is_food) {
//                this.is_food = is_food;
//            }
//
//            public int getIs_media() {
//                return is_media;
//            }
//
//            public void setIs_media(int is_media) {
//                this.is_media = is_media;
//            }
//
//            public int getIs_wifi() {
//                return is_wifi;
//            }
//
//            public void setIs_wifi(int is_wifi) {
//                this.is_wifi = is_wifi;
//            }
//
//            public int getIs_yushi() {
//                return is_yushi;
//            }
//
//            public void setIs_yushi(int is_yushi) {
//                this.is_yushi = is_yushi;
//            }
//
//            public String getLatitude() {
//                return latitude;
//            }
//
//            public void setLatitude(String latitude) {
//                this.latitude = latitude;
//            }
//
//            public String getLongitude() {
//                return longitude;
//            }
//
//            public void setLongitude(String longitude) {
//                this.longitude = longitude;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getPhone() {
//                return phone;
//            }
//
//            public void setPhone(String phone) {
//                this.phone = phone;
//            }
//
//            public long getShop_id() {
//                return shop_id;
//            }
//
//            public void setShop_id(long shop_id) {
//                this.shop_id = shop_id;
//            }
//
//            public Object getStart_date() {
//                return start_date;
//            }
//
//            public void setStart_date(Object start_date) {
//                this.start_date = start_date;
//            }
//        }
//
//        public static class PackageListBean implements Serializable{
//            private String head_img;
//            private long id;
//            private String name;
//            private int new_price;
//            private int price;
//
//            public String getHead_img() {
//                return head_img;
//            }
//
//            public void setHead_img(String head_img) {
//                this.head_img = head_img;
//            }
//
//            public long getId() {
//                return id;
//            }
//
//            public void setId(long id) {
//                this.id = id;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public int getNew_price() {
//                return new_price;
//            }
//
//            public void setNew_price(int new_price) {
//                this.new_price = new_price;
//            }
//
//            public int getPrice() {
//                return price;
//            }
//
//            public void setPrice(int price) {
//                this.price = price;
//            }
//        }
//
//        public static class ShopGoodsBean implements Serializable{
//            private String describe_img;
//            private String goods_describe;
//            private String goods_name;
//            private int goods_type;
//            private long id;
//            private int is_hot;
//            private int monthlySales;
//            private int new_price;
//            private int price;
//            private long shop_information_id;
//            private int stock;
//
//            public String getDescribe_img() {
//                return describe_img;
//            }
//
//            public void setDescribe_img(String describe_img) {
//                this.describe_img = describe_img;
//            }
//
//            public String getGoods_describe() {
//                return goods_describe;
//            }
//
//            public void setGoods_describe(String goods_describe) {
//                this.goods_describe = goods_describe;
//            }
//
//            public String getGoods_name() {
//                return goods_name;
//            }
//
//            public void setGoods_name(String goods_name) {
//                this.goods_name = goods_name;
//            }
//
//            public int getGoods_type() {
//                return goods_type;
//            }
//
//            public void setGoods_type(int goods_type) {
//                this.goods_type = goods_type;
//            }
//
//            public long getId() {
//                return id;
//            }
//
//            public void setId(long id) {
//                this.id = id;
//            }
//
//            public int getIs_hot() {
//                return is_hot;
//            }
//
//            public void setIs_hot(int is_hot) {
//                this.is_hot = is_hot;
//            }
//
//            public int getMonthlySales() {
//                return monthlySales;
//            }
//
//            public void setMonthlySales(int monthlySales) {
//                this.monthlySales = monthlySales;
//            }
//
//            public int getNew_price() {
//                return new_price;
//            }
//
//            public void setNew_price(int new_price) {
//                this.new_price = new_price;
//            }
//
//            public int getPrice() {
//                return price;
//            }
//
//            public void setPrice(int price) {
//                this.price = price;
//            }
//
//            public long getShop_information_id() {
//                return shop_information_id;
//            }
//
//            public void setShop_information_id(long shop_information_id) {
//                this.shop_information_id = shop_information_id;
//            }
//
//            public int getStock() {
//                return stock;
//            }
//
//            public void setStock(int stock) {
//                this.stock = stock;
//            }
//        }
//    }
//
//    public static class HeaderBean {
//        private String msg;
//        private int status;
//
//        public String getMsg() {
//            return msg;
//        }
//
//        public void setMsg(String msg) {
//            this.msg = msg;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//    }
}
