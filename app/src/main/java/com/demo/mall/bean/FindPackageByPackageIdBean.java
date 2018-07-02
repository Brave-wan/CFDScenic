package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class FindPackageByPackageIdBean {


    /**
     * address : 华腾科技3号楼507
     * goods : [{"goods_name":"麻辣香锅","id":12,"new_price":23},{"goods_name":"鱼香肉丝","id":13,"new_price":15},{"goods_name":"四喜丸子","id":14,"new_price":40},{"goods_name":"满汉全席","id":15,"new_price":998}]
     * goods_ids : 12,13,14,15
     * html_url : http://www.baidu.com
     * name : 这是套餐商品
     * packageId : 1029384756120395
     * phone : 1231231231
     * picList : []
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
        private String address;
        private String goods_ids;
        private String html_url;
        private String name;
        private long packageId;
        private String phone;
        /**
         * goods_name : 麻辣香锅
         * id : 12
         * new_price : 23
         */

        private List<GoodsBean> goods=new ArrayList<>();
        private List<String> picList=new ArrayList<>();

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGoods_ids() {
            return goods_ids;
        }

        public void setGoods_ids(String goods_ids) {
            this.goods_ids = goods_ids;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getPackageId() {
            return packageId;
        }

        public void setPackageId(long packageId) {
            this.packageId = packageId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }

        public static class GoodsBean {
            private String goods_name;
            private long id;
            private int new_price;

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
