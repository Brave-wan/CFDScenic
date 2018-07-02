package com.demo.amusement.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/10/13 0013 17:45
 */
public class CommentToOtherBean {


    /**
     * lastPage : 1
     * rows : [{"content":"有没有人","create_time":1476264034000,"have_img":0,"id":1609060753039780,"name":"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","picList":[],"userCommentId":1610120520343520},{"content":"和好多好多好","create_time":1476267513000,"have_img":0,"id":1609060753039780,"name":"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","picList":[],"userCommentId":1610120618333460},{"content":"与国际电话","create_time":1476350637000,"have_img":0,"id":1609060753039780,"name":"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","picList":[],"userCommentId":1610130523574470},{"content":"一看","create_time":1476350756000,"have_img":0,"id":1609060753039780,"name":"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","picList":[],"userCommentId":1610130525569050},{"content":"计算机","create_time":1476351119000,"have_img":0,"id":1609060753039780,"name":"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","picList":[],"userCommentId":1610130531597300},{"content":"上升股","create_time":1476353148000,"have_img":0,"id":1609060753039780,"name":"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","picList":[],"userCommentId":1610130605487800},{"content":"大家上课","create_time":1476353357000,"have_img":0,"id":1609060753039780,"name":"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","picList":[],"userCommentId":1610130609179120},{"content":"不失时机","create_time":1476411921000,"have_img":0,"id":1609060753039780,"name":"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","picList":[],"userCommentId":1610141025215640},{"content":"大好河山上课","create_time":1476412498000,"have_img":0,"id":1609060753039780,"name":"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","picList":[],"userCommentId":1610141034586380},{"content":"给大喊大叫","create_time":1476700320000,"have_img":0,"id":1609060753039780,"name":"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","picList":[],"userCommentId":1610170632003190},{"content":"果断回家的时间","create_time":1476839233000,"have_img":0,"id":1609221152496720,"name":"九龙峡风景区","picList":[],"userCommentId":1610191240592838}]
     * total : 11
     */

    private DataBean data;
    /**
     * msg : null
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
        private int lastPage;
        private int total;
        /**
         * content : 有没有人
         * create_time : 1476264034000
         * have_img : 0
         * id : 1609060753039780
         * name : qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
         * picList : []
         * userCommentId : 1610120520343520
         */

        private List<RowsBean> rows=new ArrayList<>();

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

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
            private String content;
            private String create_time;
            private int have_img;
            private long id;
            private String name;
            private long userCommentId;
            private List<String> picList=new ArrayList<>();

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getHave_img() {
                return have_img;
            }

            public void setHave_img(int have_img) {
                this.have_img = have_img;
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

            public long getUserCommentId() {
                return userCommentId;
            }

            public void setUserCommentId(long userCommentId) {
                this.userCommentId = userCommentId;
            }

            public List<String> getPicList() {
                return picList;
            }

            public void setPicList(List<String> picList) {
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
