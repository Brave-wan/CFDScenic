package com.demo.my.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class MyBalanceBean {

    /**
     * balanceMap : {"balance":24,"id":1608290622097640,"user_id":1608150448120500}
     * tradeLogList : [{"balance":1,"createTime":"2016-09-06 11:37:22","id":1609061137216450,"integration":0,"name":"支付宝充值","price":1,"trade_integration":0,"type":0},{"balance":2,"createTime":"2016-09-06 11:37:30","id":1609061137301920,"integration":0,"name":"支付宝充值","price":1,"trade_integration":0,"type":0},{"balance":3,"createTime":"2016-09-06 11:37:54","id":1609061137541680,"integration":0,"name":"支付宝充值","price":1,"trade_integration":0,"type":0},{"balance":4,"createTime":"2016-09-06 11:40:12","id":1609061140123900,"integration":0,"name":"支付宝充值","price":1,"trade_integration":0,"type":0},{"balance":5,"createTime":"2016-09-06 11:41:44","id":1609061141448360,"integration":0,"name":"支付宝充值","price":1,"trade_integration":0,"type":0},{"balance":6,"createTime":"2016-09-06 12:01:12","id":1609061201128390,"integration":0,"name":"支付宝充值","price":1,"trade_integration":0,"type":0},{"balance":7,"createTime":"2016-09-06 12:01:18","id":1609061201184720,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":8,"createTime":"2016-09-06 12:01:27","id":1609061201271140,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":9,"createTime":"2016-09-06 12:01:35","id":1609061201356850,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":10,"createTime":"2016-09-06 12:01:46","id":1609061201461170,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":11,"createTime":"2016-09-06 12:01:55","id":1609061201555740,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":12,"createTime":"2016-09-06 12:02:07","id":1609061202076190,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":13,"createTime":"2016-09-06 12:22:41","id":1609061222414300,"integration":0,"name":"支付宝充值","price":1,"trade_integration":0,"type":0},{"balance":14,"createTime":"2016-09-06 12:22:55","id":1609061222552990,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":15,"createTime":"2016-09-06 12:23:03","id":1609061223035940,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":16,"createTime":"2016-09-06 12:23:14","id":1609061223146730,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":17,"createTime":"2016-09-06 12:23:21","id":1609061223214230,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":18,"createTime":"2016-09-06 12:23:25","id":1609061223259150,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":19,"createTime":"2016-09-06 12:23:30","id":1609061223303360,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":20,"createTime":"2016-09-06 12:23:37","id":1609061223371040,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":21,"createTime":"2016-09-06 12:36:05","id":1609061236049020,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":22,"createTime":"2016-09-06 12:36:21","id":1609061236219360,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":23,"createTime":"2016-09-06 12:36:38","id":1609061236386690,"integration":0,"name":"微信充值","price":1,"trade_integration":0,"type":0},{"balance":24,"createTime":"2016-09-06 12:39:46","id":1609061239460600,"integration":0,"name":"支付宝充值","price":1,"trade_integration":0,"type":0}]
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
         * balance : 24.0
         * id : 1608290622097640
         * user_id : 1608150448120500
         */

        private BalanceMapBean balanceMap;
        /**
         * balance : 1.0
         * createTime : 2016-09-06 11:37:22
         * id : 1609061137216450
         * integration : 0
         * name : 支付宝充值
         * price : 1.0
         * trade_integration : 0
         * type : 0
         */

        private List<TradeLogListBean> tradeLogList=new ArrayList<>();

        public BalanceMapBean getBalanceMap() {
            return balanceMap;
        }

        public void setBalanceMap(BalanceMapBean balanceMap) {
            this.balanceMap = balanceMap;
        }

        public List<TradeLogListBean> getTradeLogList() {
            return tradeLogList;
        }

        public void setTradeLogList(List<TradeLogListBean> tradeLogList) {
            this.tradeLogList = tradeLogList;
        }

        public static class BalanceMapBean {
            private double balance;
            private long id;
            private long user_id;

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
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

        public static class TradeLogListBean {
            private double balance;
            private String createTime;
            private long id;
            private int integration;
            private String name;
            private double price;
            private int trade_integration;
            private int type;

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getIntegration() {
                return integration;
            }

            public void setIntegration(int integration) {
                this.integration = integration;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getTrade_integration() {
                return trade_integration;
            }

            public void setTrade_integration(int trade_integration) {
                this.trade_integration = trade_integration;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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
