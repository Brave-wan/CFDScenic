package com.demo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class HomeGridviewItemBean {
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
         * id : 1
         * imgurl1 : http://pic3.nipic.com/20090709/1412106_092426061_2.jpg
         * status : 0
         * type : 0
         * url : 123123
         */

        private List<CarouselImgBean> carouselImg=new ArrayList<>();
        /**
         * collectionCount : 192
         * destination : 海南
         * imageUrl : http://pic18.nipic.com/20120206/9083505_071847721194_2.jpg
         * linesType : 1
         * travelInfoId : 22
         * userId : 3
         */

        private List<TravelInfoBean> travelInfo=new ArrayList<>();

        public List<CarouselImgBean> getCarouselImg() {
            return carouselImg;
        }

        public void setCarouselImg(List<CarouselImgBean> carouselImg) {
            this.carouselImg = carouselImg;
        }

        public List<TravelInfoBean> getTravelInfo() {
            return travelInfo;
        }

        public void setTravelInfo(List<TravelInfoBean> travelInfo) {
            this.travelInfo = travelInfo;
        }

        public static class CarouselImgBean {
            private long id;
            private String imgurl1;
            private int status;
            private int type;
            private String url;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getImgurl1() {
                return imgurl1;
            }

            public void setImgurl1(String imgurl1) {
                this.imgurl1 = imgurl1;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class TravelInfoBean {
            private int collectionCount;
            private String destination;
            private String imageUrl;
            private int linesType;
            private int travelInfoId;
            private int userId;

            public int getCollectionCount() {
                return collectionCount;
            }

            public void setCollectionCount(int collectionCount) {
                this.collectionCount = collectionCount;
            }

            public String getDestination() {
                return destination;
            }

            public void setDestination(String destination) {
                this.destination = destination;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public int getLinesType() {
                return linesType;
            }

            public void setLinesType(int linesType) {
                this.linesType = linesType;
            }

            public int getTravelInfoId() {
                return travelInfoId;
            }

            public void setTravelInfoId(int travelInfoId) {
                this.travelInfoId = travelInfoId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
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
//        /**
//         * id : 1606280631185080
//         * imgurl1 : http://192.168.1.115:8080/hwTravel/img/getImg?imageUrl=201606\1467109878506.jpg
//         * status : 0
//         * type : 0
//         * url : 123
//         */
//
//        private List<CarouselImgBean> carouselImg;
//        /**
//         * collectionCount : 0
//         * imageUrl : http://pic41.nipic.com/20140509/18285693_231156450339_2.jpg
//         * nickName : 888
//         * travelInfoId : 2
//         * userId : 1
//         */
//
//        private List<TravelInfoBean> travelInfo=new ArrayList<>();
//
//        public List<CarouselImgBean> getCarouselImg() {
//            return carouselImg;
//        }
//
//        public void setCarouselImg(List<CarouselImgBean> carouselImg) {
//            this.carouselImg = carouselImg;
//        }
//
//        public List<TravelInfoBean> getTravelInfo() {
//            return travelInfo;
//        }
//
//        public void setTravelInfo(List<TravelInfoBean> travelInfo) {
//            this.travelInfo = travelInfo;
//        }
//
//        public static class CarouselImgBean {
//            private long id;
//            private String imgurl1;
//            private int status;
//            private int type;
//            private String url;
//
//            public long getId() {
//                return id;
//            }
//
//            public void setId(long id) {
//                this.id = id;
//            }
//
//            public String getImgurl1() {
//                return imgurl1;
//            }
//
//            public void setImgurl1(String imgurl1) {
//                this.imgurl1 = imgurl1;
//            }
//
//            public int getStatus() {
//                return status;
//            }
//
//            public void setStatus(int status) {
//                this.status = status;
//            }
//
//            public int getType() {
//                return type;
//            }
//
//            public void setType(int type) {
//                this.type = type;
//            }
//
//            public String getUrl() {
//                return url;
//            }
//
//            public void setUrl(String url) {
//                this.url = url;
//            }
//        }
//
//        public static class TravelInfoBean {
//            private int collectionCount;
//            private String imageUrl;
//            private String nickName;
//            private int travelInfoId;
//            private int userId;
//
//            public int getCollectionCount() {
//                return collectionCount;
//            }
//
//            public void setCollectionCount(int collectionCount) {
//                this.collectionCount = collectionCount;
//            }
//
//            public String getImageUrl() {
//                return imageUrl;
//            }
//
//            public void setImageUrl(String imageUrl) {
//                this.imageUrl = imageUrl;
//            }
//
//            public String getNickName() {
//                return nickName;
//            }
//
//            public void setNickName(String nickName) {
//                this.nickName = nickName;
//            }
//
//            public int getTravelInfoId() {
//                return travelInfoId;
//            }
//
//            public void setTravelInfoId(int travelInfoId) {
//                this.travelInfoId = travelInfoId;
//            }
//
//            public int getUserId() {
//                return userId;
//            }
//
//            public void setUserId(int userId) {
//                this.userId = userId;
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
