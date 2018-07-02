package com.demo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class MyCommentBean {

    /**
     * msg : success
     * status : 0
     */

    private HeaderBean header;
    /**
     * commentType : null
     * content : 打击乐器
     * contentType : null
     * createTime : 2016-11-04 07:11:12
     * fromUserId : null
     * haveImg : 1
     * headImg : http://192.168.1.149/cfdScenic/img/getWebImg?imageUrl=1610071041281710/1476168238135image.png
     * id : 1611040725121780
     * isTravels : 0
     * linkId : 1611040725121780
     * memo : null
     * nickName : 哈哈
     * picList : [{"createTime":"2016-11-04 07:11:12","id":1611040725125200,"imgRootUrl":null,"imgUrl":"http://192.168.1.149/cfdScenic/scripts/upload/1610071041281710/147825871212026596ae2603e4572ba4281904a12f6d0image.jpg","linkId":1611040725121780,"memo":null,"name":null,"picDescribe":null,"type":null},{"createTime":"2016-11-04 07:11:12","id":1611040725125600,"imgRootUrl":null,"imgUrl":"http://192.168.1.149/cfdScenic/scripts/upload/1610071041281710/1478258712148be4b6457a3f64954a0fa5158c8ad5937image.jpg","linkId":1611040725121780,"memo":null,"name":null,"picDescribe":null,"type":null}]
     * satisfyState : 0
     * userId : 1610071041281710
     * visitorsId : 1609220458008860
     * visitorsName : 曹妃甸湿地和鸟类自然保护区
     */

    private List<DataBean> data;

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
        private Object commentType;
        private String content;
        private Object contentType;
        private String createTime;
        private Object fromUserId;
        private int haveImg;
        private String headImg;
        private long id;
        private int isTravels;
        private long linkId;
        private Object memo;
        private String nickName;
        private int satisfyState;
        private long userId;
        private String visitorsId;
        private String visitorsName;
        /**
         * createTime : 2016-11-04 07:11:12
         * id : 1611040725125200
         * imgRootUrl : null
         * imgUrl : http://192.168.1.149/cfdScenic/scripts/upload/1610071041281710/147825871212026596ae2603e4572ba4281904a12f6d0image.jpg
         * linkId : 1611040725121780
         * memo : null
         * name : null
         * picDescribe : null
         * type : null
         */

        private List<PicListBean> picList;

        public Object getCommentType() {
            return commentType;
        }

        public void setCommentType(Object commentType) {
            this.commentType = commentType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getContentType() {
            return contentType;
        }

        public void setContentType(Object contentType) {
            this.contentType = contentType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(Object fromUserId) {
            this.fromUserId = fromUserId;
        }

        public int getHaveImg() {
            return haveImg;
        }

        public void setHaveImg(int haveImg) {
            this.haveImg = haveImg;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getIsTravels() {
            return isTravels;
        }

        public void setIsTravels(int isTravels) {
            this.isTravels = isTravels;
        }

        public long getLinkId() {
            return linkId;
        }

        public void setLinkId(long linkId) {
            this.linkId = linkId;
        }

        public Object getMemo() {
            return memo;
        }

        public void setMemo(Object memo) {
            this.memo = memo;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getSatisfyState() {
            return satisfyState;
        }

        public void setSatisfyState(int satisfyState) {
            this.satisfyState = satisfyState;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getVisitorsId() {
            return visitorsId;
        }

        public void setVisitorsId(String visitorsId) {
            this.visitorsId = visitorsId;
        }

        public String getVisitorsName() {
            return visitorsName;
        }

        public void setVisitorsName(String visitorsName) {
            this.visitorsName = visitorsName;
        }

        public List<PicListBean> getPicList() {
            return picList;
        }

        public void setPicList(List<PicListBean> picList) {
            this.picList = picList;
        }

        public static class PicListBean {
            private String createTime;
            private long id;
            private Object imgRootUrl;
            private String imgUrl;
            private long linkId;
            private Object memo;
            private Object name;
            private Object picDescribe;
            private Object type;

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

            public Object getImgRootUrl() {
                return imgRootUrl;
            }

            public void setImgRootUrl(Object imgRootUrl) {
                this.imgRootUrl = imgRootUrl;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public long getLinkId() {
                return linkId;
            }

            public void setLinkId(long linkId) {
                this.linkId = linkId;
            }

            public Object getMemo() {
                return memo;
            }

            public void setMemo(Object memo) {
                this.memo = memo;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public Object getPicDescribe() {
                return picDescribe;
            }

            public void setPicDescribe(Object picDescribe) {
                this.picDescribe = picDescribe;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }
        }
    }
}
