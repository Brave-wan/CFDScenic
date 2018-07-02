package com.demo.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class ShoppingAdapterBean implements Parcelable{
    private int column;//列
    private int state;//状态，是否选中  0是，1否

    public ShoppingAdapterBean(int column, int state) {
        this.column = column;
        this.state = state;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
