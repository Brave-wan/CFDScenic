package com.demo.mall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class FindFeatureBean {
    /**
     * msg : ok
     * status : 0
     */

    private HeaderBean header;
    /**
     * content : 1
     * head_img : 1
     * id : 3
     * name : 1213131
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
        private String head_img;
        private long id;
        private String name;

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


//    /**
//     * rows : [{"content":"这是特产大虾","head_img":"192.168.1.105:8080/photo/3.jpg","id":3,"name":"特产大侠"},{"content":"这是特产麻辣烫","head_img":"192.168.1.105:8080/photo/3.jpg","id":4,"name":"特产麻辣烫"}]
//     * total : 2
//     */
//
//    private DataBean data;
//    /**
//     * msg : ok
//     * status : 0
//     */
//
//    private HeaderBean header;
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public HeaderBean getHeader() {
//        return header;
//    }
//
//    public void setHeader(HeaderBean header) {
//        this.header = header;
//    }
//
//    public static class DataBean {
//        private int total;
//        /**
//         * content : 这是特产大虾
//         * head_img : 192.168.1.105:8080/photo/3.jpg
//         * id : 3
//         * name : 特产大侠
//         */
//
//        private List<RowsBean> rows;
//
//        public int getTotal() {
//            return total;
//        }
//
//        public void setTotal(int total) {
//            this.total = total;
//        }
//
//        public List<RowsBean> getRows() {
//            return rows;
//        }
//
//        public void setRows(List<RowsBean> rows) {
//            this.rows = rows;
//        }
//
//        public static class RowsBean {
//            private String content;
//            private String head_img;
//            private int id;
//            private String name;
//
//            public String getContent() {
//                return content;
//            }
//
//            public void setContent(String content) {
//                this.content = content;
//            }
//
//            public String getHead_img() {
//                return head_img;
//            }
//
//            public void setHead_img(String head_img) {
//                this.head_img = head_img;
//            }
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//        }
//    }
//
//    public static class HeaderBean {
//        private String msg;
//        private int status;
//
//        public String getMsg() {
//            return msg;
//        }
//
//        public void setMsg(String msg) {
//            this.msg = msg;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//    }
}
