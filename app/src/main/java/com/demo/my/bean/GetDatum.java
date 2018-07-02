package com.demo.my.bean;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class GetDatum {


    /**
     * address : null
     * cashPassword : null
     * createTime : null
     * creator : null
     * gender : 0
     * headImg : http://192.168.1.149/cfdScenic/scripts/upload/1610090721239070/147799504904409805d4da69c4dcf81a89208036ba952
     * id : null
     * idCard : 130633199508240014
     * isVirtual : null
     * latitude : null
     * longitude : null
     * mobileNo : 13191867143
     * nickName : 刘
     * openId :
     * password : e10adc3949ba59abbe56e057f20f883e
     * salt : null
     * source : null
     * state : null
     * token : null
     * trueName : 刘
     * userName : null
     * userType : null
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
        private Object address;
        private Object cashPassword;
        private Object createTime;
        private Object creator;
        private int gender;
        private String headImg;
        private Object id;
        private String idCard;
        private Object isVirtual;
        private Object latitude;
        private Object longitude;
        private String mobileNo;
        private String nickName;
        private String openId;
        private String password;
        private Object salt;
        private Object source;
        private Object state;
        private Object token;
        private String trueName;
        private Object userName;
        private Object userType;

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getCashPassword() {
            return cashPassword;
        }

        public void setCashPassword(Object cashPassword) {
            this.cashPassword = cashPassword;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getCreator() {
            return creator;
        }

        public void setCreator(Object creator) {
            this.creator = creator;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public Object getIsVirtual() {
            return isVirtual;
        }

        public void setIsVirtual(Object isVirtual) {
            this.isVirtual = isVirtual;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getSalt() {
            return salt;
        }

        public void setSalt(Object salt) {
            this.salt = salt;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }

        public Object getUserType() {
            return userType;
        }

        public void setUserType(Object userType) {
            this.userType = userType;
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
