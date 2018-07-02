package com.demo.bean;

/**
 * 游乐圈--展开全文
 * Created by Administrator on 2016/8/2 0002.
 */
public class OpenBean {
    private int state=1;//是否展开更多
    private boolean zan=false;  //true是已经点赞
    private int zan_Number=50;  //点赞的个数


    public int getZan_Number() {
        return zan_Number;
    }

    public void setZan_Number(int zan_Number) {
        this.zan_Number = zan_Number;
    }

    public boolean getZan() {
        return zan;
    }

    public void setZan(boolean zan) {
        this.zan = zan;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
