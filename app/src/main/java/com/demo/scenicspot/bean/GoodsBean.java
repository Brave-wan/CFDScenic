package com.demo.scenicspot.bean;

/**
 * 作者：宋玉磊 on 2016/8/3 0003 14:47
 */
public class GoodsBean {
    public String name;
    public String pinyi;

    public GoodsBean(String name, String pinyi) {
        super();
        this.name = name;
        this.pinyi = pinyi;
    }

    public GoodsBean() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyi() {
        return pinyi;
    }

    public void setPinyi(String pinyi) {
        this.pinyi = pinyi;
    }

}
