package com.demo.my.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class GetBankBean {

    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * bankaccount : null
     * bankcode : 6222020
     * bankname : 中国工商银行.E时代卡
     * id : 1608191004316780
     * idcard : 1306331995
     * realname : 刘
     * type : -1
     * user_id : 1608150443083090
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
        private Object bankaccount;
        private String bankcode;
        private String bankname;
        private long id;
        private String idcard;
        private String realname;
        private int type;
        private long user_id;

        public Object getBankaccount() {
            return bankaccount;
        }

        public void setBankaccount(Object bankaccount) {
            this.bankaccount = bankaccount;
        }

        public String getBankcode() {
            return bankcode;
        }

        public void setBankcode(String bankcode) {
            this.bankcode = bankcode;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }
    }
}
