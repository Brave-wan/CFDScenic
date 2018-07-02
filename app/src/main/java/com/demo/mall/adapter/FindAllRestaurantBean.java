package com.demo.mall.adapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class FindAllRestaurantBean {

    /**
     * lastPage : 1
     * rows : [{"backgroud_img":"1","consumption":"1","content":"1","head_img":"1","id":1610110635258390,"name":"刘"},{"backgroud_img":null,"consumption":null,"content":null,"head_img":null,"id":1610110636086220,"name":"王小林"}]
     * total : 2
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
        private int lastPage;
        private int total;
        /**
         * backgroud_img : 1
         * consumption : 1
         * content : 1
         * head_img : 1
         * id : 1610110635258390
         * name : 刘
         */

        private List<RowsBean> rows;

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
            private String backgroud_img;
            private String consumption;
            private String content;
            private String head_img;
            private long id;
            private String name;

            public String getBackgroud_img() {
                return backgroud_img;
            }

            public void setBackgroud_img(String backgroud_img) {
                this.backgroud_img = backgroud_img;
            }

            public String getConsumption() {
                return consumption;
            }

            public void setConsumption(String consumption) {
                this.consumption = consumption;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
