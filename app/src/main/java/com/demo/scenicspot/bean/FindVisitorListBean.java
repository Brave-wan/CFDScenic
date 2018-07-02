package com.demo.scenicspot.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/28 0028.
 */
public class FindVisitorListBean {


    /**
     * lastPage : 1
     * rows : [{"head_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1611747958966520.jpg","id":1609220458008860,"name":"曹妃甸湿地和鸟类自然保护区","name_en":"Caofeidian wetlands and Birds Nature Reserve","new_price":90,"price":100,"visitors_describe":"2005年9月，经河北省人民政府批准建立的省级自然保护区。"},{"head_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1611747979174565.jpg","id":1609221152496720,"name":"九龙峡风景区","name_en":"Kowloon Gorge Scenic Area","new_price":120,"price":120,"visitors_describe":"太行山最绿的地方"},{"head_img":"http://192.168.1.134:8081/img/getWebImg?imageUrl=1611717937140602.jpg","id":1610240632067010,"name":"qweqweqweq","name_en":"qweqweqwe","new_price":44444,"price":444444,"visitors_describe":"qweqweqwe"},{"head_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1611747985986189.jpg","id":1610241159398990,"name":"曹妃湖风景区","name_en":"Cao Feihu Scenic Area","new_price":123,"price":123,"visitors_describe":"曹妃甸湿地的重要组成部分，总面积733.3万平方米，蓄水能力2365万立方米。"},{"head_img":"http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1611747779988133.png","id":1610270230397270,"name":"测试景点修改","name_en":"Test Attractionss","new_price":62,"price":85,"visitors_describe":"简介简介简介简介简介简介简介简介简介修改"}]
     * total : 5
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
        private int lastPage;
        private int total;
        /**
         * head_img : http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1611747958966520.jpg
         * id : 1609220458008860
         * name : 曹妃甸湿地和鸟类自然保护区
         * name_en : Caofeidian wetlands and Birds Nature Reserve
         * new_price : 90
         * price : 100
         * visitors_describe : 2005年9月，经河北省人民政府批准建立的省级自然保护区。
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
            private String head_img;
            private long id;
            private String name;
            private String name_en;
            private int new_price;
            private int price;
            private String visitors_describe;

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

            public String getName_en() {
                return name_en;
            }

            public void setName_en(String name_en) {
                this.name_en = name_en;
            }

            public int getNew_price() {
                return new_price;
            }

            public void setNew_price(int new_price) {
                this.new_price = new_price;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getVisitors_describe() {
                return visitors_describe;
            }

            public void setVisitors_describe(String visitors_describe) {
                this.visitors_describe = visitors_describe;
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
