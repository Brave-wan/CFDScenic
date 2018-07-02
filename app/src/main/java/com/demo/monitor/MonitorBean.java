package com.demo.monitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/12/6 0006 14:29
 */
public class MonitorBean {

    /**
     * lastPage : 1  未小经大   经lng
     * rows : [{"code":"1000005","creatime":"2016-12-06 10:55:31","id":1,"name":"球体1","x_point":"118.3482711780","y_point":"39.2125620210"},{"code":"1000007","creatime":"2016-12-06 10:36:35","id":2,"name":"摄像头2","x_point":"118.3550867068","y_point":"39.2087738361"}]
     * total : 2
     */

    private DataBean data;
    /**
     * msg : 成功
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
         * code : 1000005
         * creatime : 2016-12-06 10:55:31
         * id : 1
         * name : 球体1
         * x_point : 118.3482711780
         * y_point : 39.2125620210
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
            private String code;
            private String creatime;
            private long id;
            private String name;
            private String x_point;
            private String y_point;
            private String content;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreatime() {
                return creatime;
            }

            public void setCreatime(String creatime) {
                this.creatime = creatime;
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

            public String getX_point() {
                return x_point;
            }

            public void setX_point(String x_point) {
                this.x_point = x_point;
            }

            public String getY_point() {
                return y_point;
            }

            public void setY_point(String y_point) {
                this.y_point = y_point;
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
