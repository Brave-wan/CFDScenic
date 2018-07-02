package com.demo.amusement.bean;

/**
 * 作者：sonnerly on 2016/9/19 0019 17:26
 */
public class ActivityMoreBean {


    /**
     * address : 123
     * buynumber : 0
     * dateEnd : 1
     * deliver_fee : 10
     * detailUrl : http://localhost:8081/scripts/upload/1477300752241travel.html
     * end_valid : 2016-09-15 00:00:00
     * head_img : http://192.168.1.149/images/1.jpg
     * id : 1609060739414680
     * latitude : 114.604
     * longitude : 38.018012
     * name : aaaaa
     * name_en : yingwenmingzi
     * new_price : 123
     * number : 111
     * personNumber : 0
     * price : 123
     * soldOut : 0
     * start_valid : 2016-09-06 00:00:00
     * visitors_describe : 123
     */

    private DataBean data=new DataBean();
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
        private String address;
        private int buynumber;
        private int dateEnd;
        private int deliver_fee;
        private String detailUrl;
        private String end_valid;
        private String head_img;
        private long id;
        private String latitude;
        private String longitude;
        private String name;
        private String name_en;
        private int new_price;
        private int number;
        private int personNumber;
        private int price;
        private int soldOut;
        private String start_valid;
        private String visitors_describe;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getBuynumber() {
            return buynumber;
        }

        public void setBuynumber(int buynumber) {
            this.buynumber = buynumber;
        }

        public int getDateEnd() {
            return dateEnd;
        }

        public void setDateEnd(int dateEnd) {
            this.dateEnd = dateEnd;
        }

        public int getDeliver_fee() {
            return deliver_fee;
        }

        public void setDeliver_fee(int deliver_fee) {
            this.deliver_fee = deliver_fee;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        public String getEnd_valid() {
            return end_valid;
        }

        public void setEnd_valid(String end_valid) {
            this.end_valid = end_valid;
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

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getPersonNumber() {
            return personNumber;
        }

        public void setPersonNumber(int personNumber) {
            this.personNumber = personNumber;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSoldOut() {
            return soldOut;
        }

        public void setSoldOut(int soldOut) {
            this.soldOut = soldOut;
        }

        public String getStart_valid() {
            return start_valid;
        }

        public void setStart_valid(String start_valid) {
            this.start_valid = start_valid;
        }

        public String getVisitors_describe() {
            return visitors_describe;
        }

        public void setVisitors_describe(String visitors_describe) {
            this.visitors_describe = visitors_describe;
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
