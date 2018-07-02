package com.demo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class GetTraveLogsBean {

    /**
     * rows : [{"address":"异次元裂隙之后","commentCount":0,"content":"曹妃甸","createdate":"2016-08-24 17:02:35","favorCount":1,"head_img":"2","id":1608240502364810,"isFavor":0,"nick_name":"2","picList":[],"shareCount":0,"title":"曹妃甸","travel_img":"http://192.168.1.149/images/0.jpg","travel_type":3,"travel_video":"http://hvly.oss-cn-shanghai.aliyuncs.com/1d7d4533-9b58-402b-9c42-54c6fac788d0","type":2},{"address":"异次元裂隙之后","commentCount":0,"content":"曹妃甸123123123123123123123123","createdate":"2016-08-24 16:59:41","favorCount":1,"head_img":"2","id":1608240459423780,"isFavor":0,"nick_name":"2","picList":[],"shareCount":0,"title":"曹妃甸123123123","travel_img":"http://192.168.1.149/images/1.jpg","travel_type":2,"travel_video":"http://hvly.oss-cn-shanghai.aliyuncs.com/1d7d4533-9b58-402b-9c42-54c6fac788d0","type":2},{"address":"异次元裂隙之后","commentCount":0,"content":"曹妃甸","createdate":"2016-08-24 16:58:12","favorCount":0,"head_img":"2","id":1608240458101480,"isFavor":0,"nick_name":"2","picList":[],"shareCount":0,"title":"曹妃甸","travel_img":"http://192.168.1.149/images/0.jpg","travel_type":1,"travel_video":"http://hvly.oss-cn-shanghai.aliyuncs.com/1d7d4533-9b58-402b-9c42-54c6fac788d0","type":1},{"address":"异次元裂隙之后","commentCount":0,"content":"曹妃甸","createdate":"2016-08-22 20:44:31","favorCount":1,"head_img":"2","id":1608220844290450,"isFavor":0,"nick_name":"2","picList":[],"shareCount":0,"title":"曹妃甸","travel_img":"http://192.168.1.149/images/1.jpg","travel_type":3,"travel_video":"http://hvly.oss-cn-shanghai.aliyuncs.com/1d7d4533-9b58-402b-9c42-54c6fac788d0","type":1},{"address":"异次元裂隙之后","commentCount":0,"content":"曹妃甸","createdate":"2016-08-22 20:43:51","favorCount":0,"head_img":"2","id":1608220843486090,"isFavor":0,"nick_name":"2","picList":[],"shareCount":0,"title":"曹妃甸","travel_img":"http://192.168.1.149/images/0.jpg","travel_type":1,"travel_video":"http://hvly.oss-cn-shanghai.aliyuncs.com/1d7d4533-9b58-402b-9c42-54c6fac788d0","type":0},{"address":"地址","commentCount":0,"content":"宋宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字宋鱼雷要很长很长的字鱼雷要很长很长的字","createdate":"2016-08-10 11:09:49","favorCount":2,"head_img":"2","id":1,"isFavor":0,"nick_name":"2","picList":["http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/0.jpg"],"shareCount":2,"title":"曹妃甸游记","travel_img":"http://192.168.1.149/images/1.jpg","travel_type":2,"travel_video":"http://hvly.oss-cn-shanghai.aliyuncs.com/1d7d4533-9b58-402b-9c42-54c6fac788d0","type":0}]
     * total : 6
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
        private int total;
        /**
         * address : 异次元裂隙之后
         * commentCount : 0
         * content : 曹妃甸
         * createdate : 2016-08-24 17:02:35
         * favorCount : 1
         * head_img : 2
         * id : 1608240502364810
         * isFavor : 0
         * nick_name : 2
         * picList : []
         * shareCount : 0
         * title : 曹妃甸
         * travel_img : http://192.168.1.149/images/0.jpg
         * travel_type : 3
         * travel_video : http://hvly.oss-cn-shanghai.aliyuncs.com/1d7d4533-9b58-402b-9c42-54c6fac788d0
         * type : 2
         */

        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            private String address;
            private int commentCount;
            private String content;
            private String createdate;
            private int favorCount;
            private String head_img;
            private long id;
            private int isFavor;
            private String nick_name;
            private int shareCount;
            private String title;
            private String travel_img;
            private int travel_type;
            private String travel_video;
            private int type;
            private List<?> picList;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public int getFavorCount() {
                return favorCount;
            }

            public void setFavorCount(int favorCount) {
                this.favorCount = favorCount;
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

            public int getIsFavor() {
                return isFavor;
            }

            public void setIsFavor(int isFavor) {
                this.isFavor = isFavor;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public int getShareCount() {
                return shareCount;
            }

            public void setShareCount(int shareCount) {
                this.shareCount = shareCount;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTravel_img() {
                return travel_img;
            }

            public void setTravel_img(String travel_img) {
                this.travel_img = travel_img;
            }

            public int getTravel_type() {
                return travel_type;
            }

            public void setTravel_type(int travel_type) {
                this.travel_type = travel_type;
            }

            public String getTravel_video() {
                return travel_video;
            }

            public void setTravel_video(String travel_video) {
                this.travel_video = travel_video;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public List<?> getPicList() {
                return picList;
            }

            public void setPicList(List<?> picList) {
                this.picList = picList;
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
