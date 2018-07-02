package com.demo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class WithDrawBean {


    /**
     * balance : {"balance":999999,"id":3,"user_id":1608150443083090}
     * bank : [{"bankaccount":null,"bankcode":"6222020","bankname":"中国工商银行.E时代卡","id":1608191004316780,"idcard":"1306331995","realname":"刘","type":-1,"user_id":1608150443083090},{"bankaccount":null,"bankcode":"6222020","bankname":"中国工商银行.E时代卡","id":1608191009412140,"idcard":"123456789","realname":"哈哈","type":-1,"user_id":1608150443083090},{"bankaccount":null,"bankcode":"123456798","bankname":"输入卡号有误","id":1608191014280370,"idcard":"123456789","realname":"123","type":0,"user_id":1608150443083090}]
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
         * balance : 999999
         * id : 3
         * user_id : 1608150443083090
         */

        private BalanceBean balance;
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

        private List<BankBean> bank;

        public BalanceBean getBalance() {
            return balance;
        }

        public void setBalance(BalanceBean balance) {
            this.balance = balance;
        }

        public List<BankBean> getBank() {
            return bank;
        }

        public void setBank(List<BankBean> bank) {
            this.bank = bank;
        }

        public static class BalanceBean {
            private long balance;
            private long id;
            private long user_id;

            public long getBalance() {
                return balance;
            }

            public void setBalance(long balance) {
                this.balance = balance;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }
        }

        public static class BankBean {
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
