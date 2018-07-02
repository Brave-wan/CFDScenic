package com.demo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class MyIntegralBean {

    /**
     * integralMap : {"id":1608290622097640,"integration":198,"user_id":1608150448120500}
     * tradeLogList : [{"balance":22,"createTime":"2016-09-06 18:45:53","id":1609060645534990,"integration":300,"name":null,"price":34,"trade_integration":34,"type":3},{"balance":22,"createTime":"2016-09-06 18:52:35","id":1609060652359720,"integration":300,"name":null,"price":34,"trade_integration":34,"type":3},{"balance":22,"createTime":"2016-09-06 18:54:48","id":1609060654484670,"integration":266,"name":null,"price":34,"trade_integration":34,"type":3},{"balance":22,"createTime":"2016-09-06 18:56:38","id":1609060656380970,"integration":232,"name":null,"price":34,"trade_integration":34,"type":3}]
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
         * id : 1608290622097640
         * integration : 198
         * user_id : 1608150448120500
         */

        private IntegralMapBean integralMap;
        /**
         * balance : 22
         * createTime : 2016-09-06 18:45:53
         * id : 1609060645534990
         * integration : 300
         * name : null
         * price : 34
         * trade_integration : 34
         * type : 3
         */

        private List<TradeLogListBean> tradeLogList;

        public IntegralMapBean getIntegralMap() {
            return integralMap;
        }

        public void setIntegralMap(IntegralMapBean integralMap) {
            this.integralMap = integralMap;
        }

        public List<TradeLogListBean> getTradeLogList() {
            return tradeLogList;
        }

        public void setTradeLogList(List<TradeLogListBean> tradeLogList) {
            this.tradeLogList = tradeLogList;
        }

        public static class IntegralMapBean {
            private long id;
            private int integration;
            private long user_id;

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

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }
        }

        public static class TradeLogListBean {
            private int balance;
            private String createTime;
            private long id;
            private int integration;
            private String name;
            private int price;
            private int trade_integration;
            private int type;

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
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
