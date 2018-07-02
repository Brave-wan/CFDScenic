package com.demo.scenicspot.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class ScenicSpotCommentBean {

    /**
     * msg : success
     * status : 0
     */

    private HeaderBean header;
    /**
     * content : 景区评论
     * create_time : 2016-09-07 08:40:10
     * have_img : 1
     * head_img : http://192.168.1.149/cfdScenic/img/getImg?imageUrl=1608160918510940/1473314642550image.png
     * id : 2
     * is_travels : 0
     * memo : 备注
     * nick_name : 小夕
     * pic : ["http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg","http://192.168.1.149/images/1.jpg"]
     * satisfy_state : 3
     * user_id : 1608160918510940
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
        private int is_travels;
        private String memo;
        private String nick_name;
        private int satisfy_state;
        private long user_id;
        private List<String> pic;

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

        public int getIs_travels() {
            return is_travels;
        }

        public void setIs_travels(int is_travels) {
            this.is_travels = is_travels;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getSatisfy_state() {
            return satisfy_state;
        }

        public void setSatisfy_state(int satisfy_state) {
            this.satisfy_state = satisfy_state;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }
    }
}
