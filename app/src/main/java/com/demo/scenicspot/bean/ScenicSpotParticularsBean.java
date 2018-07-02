package com.demo.scenicspot.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class ScenicSpotParticularsBean {

    /**
     * deliver_fee : 10
     * detailHtml : http://www.bai.com
     * detailText : 人们都说：“桂林山水甲天下。”我们乘着木船，荡漾在漓江上，来观赏桂林的山水。
     　　我看见过波澜壮阔的大海，玩赏过水平如镜的西湖，却从没看见过漓江这样的水。漓江的水真静啊，静得让你感觉不到它在流动；漓江的水真清啊，清得可以看见江底的沙石；漓江的水真绿啊，绿得仿佛那是一块无瑕的翡翠。船桨激起的微波扩散出一道道水纹，才让你感觉到船在前进，岸在后移。
     　　我攀登过峰峦雄伟的泰山，游览过红叶似火的香山，却从没看见过桂林这一带的山，桂林的山真奇啊，一座座拔地而起，各不相连，像老人，像巨象，像骆驼，奇峰罗列，形态万千；桂林的山真秀啊，像翠绿的屏障，像新生的竹笋，色彩明丽，倒映水中；桂林的山真险啊，危峰兀立，怪石嶙峋，好像一不小心就会栽倒下来。
     　　这样的山围绕着这样的水，这样的水倒映着这样的山，再加上空中云雾迷蒙，山间绿树红花，江上竹筏小舟，让你感到像是走进了连绵不断的画卷，真是“舟行碧波上，人在画中游”。
     * details_id : 1
     * head_img : http://192.168.1.149/images/1.jpg
     * id : 1
     * latitude : 111
     * longitude : 11
     * name : 阿拉德大陆
     * name_en : yinwenmingzi
     * new_price : 90
     * notice_id : 2
     * openDateList : [{"id":1,"name":"名字","time_detail":"星期一"}]
     * price : 100
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
        private int deliver_fee;
        private String detailHtml;
        private String detailText;
        private long details_id;
        private String head_img;
        private long id;
        private double latitude;
        private double longitude;
        private String name;
        private String name_en;
        private double new_price;
        private long notice_id;
        private double price;
        private String satisfaction;
        private long commentCount;

        public long getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(long commentCount) {
            this.commentCount = commentCount;
        }

        public String getSatisfaction() {
            return satisfaction;
        }

        public void setSatisfaction(String satisfaction) {
            this.satisfaction = satisfaction;
        }

        /**
         * id : 1
         * name : 名字
         * time_detail : 星期一
         */

        private List<OpenDateListBean> openDateList=new ArrayList<>();

        public int getDeliver_fee() {
            return deliver_fee;
        }

        public void setDeliver_fee(int deliver_fee) {
            this.deliver_fee = deliver_fee;
        }

        public String getDetailHtml() {
            return detailHtml;
        }

        public void setDetailHtml(String detailHtml) {
            this.detailHtml = detailHtml;
        }

        public String getDetailText() {
            return detailText;
        }

        public void setDetailText(String detailText) {
            this.detailText = detailText;
        }

        public long getDetails_id() {
            return details_id;
        }

        public void setDetails_id(long details_id) {
            this.details_id = details_id;
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

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public double getNew_price() {
            return new_price;
        }

        public void setNew_price(double new_price) {
            this.new_price = new_price;
        }

        public long getNotice_id() {
            return notice_id;
        }

        public void setNotice_id(long notice_id) {
            this.notice_id = notice_id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public List<OpenDateListBean> getOpenDateList() {
            return openDateList;
        }

        public void setOpenDateList(List<OpenDateListBean> openDateList) {
            this.openDateList = openDateList;
        }

        public static class OpenDateListBean {
            private long id;
            private String name;
            private String time_detail;

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

            public String getTime_detail() {
                return time_detail;
            }

            public void setTime_detail(String time_detail) {
                this.time_detail = time_detail;
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
