package com.demo.scenicspot.bean;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class PressDetailsBean {

    /**
     * content : 阿拉德秘历阿拉德秘历
     * content_url : http://www.baidu.com
     * create_time : 2016-8-03 12:00:00
     * creator : 鬼剑士
     * creator_id : 1
     * header_img : http://192.168.1.149:8080/photo/1.jpg
     * id : 1
     * name : 阿拉德秘历
     * news_describe : 阿拉德秘历
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
        private String content;
        private String content_url;
        private String create_time;
        private String creator;
        private int creator_id;
        private String header_img;
        private long id;
        private String name;
        private String news_describe;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent_url() {
            return content_url;
        }

        public void setContent_url(String content_url) {
            this.content_url = content_url;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public int getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(int creator_id) {
            this.creator_id = creator_id;
        }

        public String getHeader_img() {
            return header_img;
        }

        public void setHeader_img(String header_img) {
            this.header_img = header_img;
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

        public String getNews_describe() {
            return news_describe;
        }

        public void setNews_describe(String news_describe) {
            this.news_describe = news_describe;
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
