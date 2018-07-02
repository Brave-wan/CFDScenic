package com.demo.amusement.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/9/21 0021 09:22
 */
public class CommentBean {
    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * content : 嘎哈
     * create_time : 2016-11-02 18:44:19
     * have_img : 0
     * head_img : http://192.168.1.149/cfdScenic/scripts/upload/1608160918510940/1477468240977622c1815ad924c709600009c75dbc066image.png
     * id : 1611020644196710
     * nick_name : 我们
     */

    private List<DataBean> data=new ArrayList<>();

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
        private String content;
        private String create_time;
        private int have_img;
        private String head_img;
        private long id;
        private String nick_name;
        private long userId;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

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

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }
    }

   /* *//**
     * rows : [{"content":"哈哈哈1","create_time":1474353404000,"have_img":0,"head_img":"http://meng.com","id":1609200236440980,"nick_name":"伏梦"},{"content":"哈哈哈哈2","create_time":1474353404000,"have_img":0,"head_img":"http://meng.com","id":1609200236441730,"nick_name":"伏梦"},{"content":"哈哈哈3","create_time":1474353429000,"have_img":0,"head_img":"http://meng.com","id":1609200237092120,"nick_name":"伏梦"}]
     * total : 3
     *//*

    private DataBean data;
    *//**
     * msg : ok
     * status : 0
     *//*

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
        *//**
         * content : 哈哈哈1
         * create_time : 1474353404000
         * have_img : 0
         * head_img : http://meng.com
         * id : 1609200236440980
         * nick_name : 伏梦
         *//*

        private List<RowsBean> rows=new ArrayList<>();
        private int lastPage;

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
            private String head_img;
            private long id;
            private String nick_name;

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

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
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
    }*/
}
