package com.demo.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.activity.HotelSureOrderActivity;

/**
 * 作者：sonnerly on 2016/8/10 0010 19:11
 */
public class DialogChoseRoom extends Dialog implements View.OnClickListener {
    Context context;
    public int number = 1;
    TextView one, two, three, four, five, six, seven, eight;
    HotelSureOrderActivity activity;

    public DialogChoseRoom(Context context) {
        super(context);
        activity = (HotelSureOrderActivity) context;
    }

    protected DialogChoseRoom(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        activity = (HotelSureOrderActivity) context;
    }

    public DialogChoseRoom(Context context, int themeResId, int number) {
        super(context, themeResId);
        activity = (HotelSureOrderActivity) context;
        this.context = context;
        this.number = number;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mall_choseroomnum);
        one = (TextView) findViewById(R.id.tv_dialog_roomnum_one);
        one.setOnClickListener(this);
        two = (TextView) findViewById(R.id.tv_dialog_roomnum_two);
        two.setOnClickListener(this);
        three = (TextView) findViewById(R.id.tv_dialog_roomnum_three);
        three.setOnClickListener(this);
        four = (TextView) findViewById(R.id.tv_dialog_roomnum_four);
        four.setOnClickListener(this);
        five = (TextView) findViewById(R.id.tv_dialog_roomnum_five);
        five.setOnClickListener(this);
        six = (TextView) findViewById(R.id.tv_dialog_roomnum_six);
        six.setOnClickListener(this);
        seven = (TextView) findViewById(R.id.tv_dialog_roomnum_seven);
        seven.setOnClickListener(this);
        eight = (TextView) findViewById(R.id.tv_dialog_roomnum_eight);
        eight.setOnClickListener(this);
        ImageView cha = (ImageView) findViewById(R.id.iv_dialog_cha);
        cha.setOnClickListener(this);
        if (number == 1) {
            one.setBackgroundResource(R.mipmap.fanglan);
            one.setTextColor(Color.parseColor("#ffffff"));
            two.setBackgroundResource(R.mipmap.fangbai);
            two.setTextColor(Color.parseColor("#4bc4fb"));
            three.setBackgroundResource(R.mipmap.fangbai);
            three.setTextColor(Color.parseColor("#4bc4fb"));
            four.setBackgroundResource(R.mipmap.fangbai);
            four.setTextColor(Color.parseColor("#4bc4fb"));
            five.setBackgroundResource(R.mipmap.fangbai);
            five.setTextColor(Color.parseColor("#4bc4fb"));
            six.setBackgroundResource(R.mipmap.fangbai);
            six.setTextColor(Color.parseColor("#4bc4fb"));
            seven.setBackgroundResource(R.mipmap.fangbai);
            seven.setTextColor(Color.parseColor("#4bc4fb"));
            eight.setBackgroundResource(R.mipmap.fangbai);
            eight.setTextColor(Color.parseColor("#4bc4fb"));
        } else if (number == 2) {
            two.setBackgroundResource(R.mipmap.fanglan);
            two.setTextColor(Color.parseColor("#ffffff"));
            one.setBackgroundResource(R.mipmap.fangbai);
            one.setTextColor(Color.parseColor("#4bc4fb"));
            three.setBackgroundResource(R.mipmap.fangbai);
            three.setTextColor(Color.parseColor("#4bc4fb"));
            four.setBackgroundResource(R.mipmap.fangbai);
            four.setTextColor(Color.parseColor("#4bc4fb"));
            five.setBackgroundResource(R.mipmap.fangbai);
            five.setTextColor(Color.parseColor("#4bc4fb"));
            six.setBackgroundResource(R.mipmap.fangbai);
            six.setTextColor(Color.parseColor("#4bc4fb"));
            seven.setBackgroundResource(R.mipmap.fangbai);
            seven.setTextColor(Color.parseColor("#4bc4fb"));
            eight.setBackgroundResource(R.mipmap.fangbai);
            eight.setTextColor(Color.parseColor("#4bc4fb"));
        } else if (number == 3) {
            three.setBackgroundResource(R.mipmap.fanglan);
            three.setTextColor(Color.parseColor("#ffffff"));
            two.setBackgroundResource(R.mipmap.fangbai);
            two.setTextColor(Color.parseColor("#4bc4fb"));
            one.setBackgroundResource(R.mipmap.fangbai);
            one.setTextColor(Color.parseColor("#4bc4fb"));
            four.setBackgroundResource(R.mipmap.fangbai);
            four.setTextColor(Color.parseColor("#4bc4fb"));
            five.setBackgroundResource(R.mipmap.fangbai);
            five.setTextColor(Color.parseColor("#4bc4fb"));
            six.setBackgroundResource(R.mipmap.fangbai);
            six.setTextColor(Color.parseColor("#4bc4fb"));
            seven.setBackgroundResource(R.mipmap.fangbai);
            seven.setTextColor(Color.parseColor("#4bc4fb"));
            eight.setBackgroundResource(R.mipmap.fangbai);
            eight.setTextColor(Color.parseColor("#4bc4fb"));
        } else if (number == 4) {
            four.setBackgroundResource(R.mipmap.fanglan);
            four.setTextColor(Color.parseColor("#ffffff"));
            two.setBackgroundResource(R.mipmap.fangbai);
            two.setTextColor(Color.parseColor("#4bc4fb"));
            three.setBackgroundResource(R.mipmap.fangbai);
            three.setTextColor(Color.parseColor("#4bc4fb"));
            one.setBackgroundResource(R.mipmap.fangbai);
            one.setTextColor(Color.parseColor("#4bc4fb"));
            five.setBackgroundResource(R.mipmap.fangbai);
            five.setTextColor(Color.parseColor("#4bc4fb"));
            six.setBackgroundResource(R.mipmap.fangbai);
            six.setTextColor(Color.parseColor("#4bc4fb"));
            seven.setBackgroundResource(R.mipmap.fangbai);
            seven.setTextColor(Color.parseColor("#4bc4fb"));
            eight.setBackgroundResource(R.mipmap.fangbai);
            eight.setTextColor(Color.parseColor("#4bc4fb"));
        } else if (number == 5) {
            five.setBackgroundResource(R.mipmap.fanglan);
            five.setTextColor(Color.parseColor("#ffffff"));
            two.setBackgroundResource(R.mipmap.fangbai);
            two.setTextColor(Color.parseColor("#4bc4fb"));
            three.setBackgroundResource(R.mipmap.fangbai);
            three.setTextColor(Color.parseColor("#4bc4fb"));
            four.setBackgroundResource(R.mipmap.fangbai);
            four.setTextColor(Color.parseColor("#4bc4fb"));
            one.setBackgroundResource(R.mipmap.fangbai);
            one.setTextColor(Color.parseColor("#4bc4fb"));
            six.setBackgroundResource(R.mipmap.fangbai);
            six.setTextColor(Color.parseColor("#4bc4fb"));
            seven.setBackgroundResource(R.mipmap.fangbai);
            seven.setTextColor(Color.parseColor("#4bc4fb"));
            eight.setBackgroundResource(R.mipmap.fangbai);
            eight.setTextColor(Color.parseColor("#4bc4fb"));
        } else if (number == 6) {
            six.setBackgroundResource(R.mipmap.fanglan);
            six.setTextColor(Color.parseColor("#ffffff"));
            two.setBackgroundResource(R.mipmap.fangbai);
            two.setTextColor(Color.parseColor("#4bc4fb"));
            three.setBackgroundResource(R.mipmap.fangbai);
            three.setTextColor(Color.parseColor("#4bc4fb"));
            four.setBackgroundResource(R.mipmap.fangbai);
            four.setTextColor(Color.parseColor("#4bc4fb"));
            five.setBackgroundResource(R.mipmap.fangbai);
            five.setTextColor(Color.parseColor("#4bc4fb"));
            one.setBackgroundResource(R.mipmap.fangbai);
            one.setTextColor(Color.parseColor("#4bc4fb"));
            seven.setBackgroundResource(R.mipmap.fangbai);
            seven.setTextColor(Color.parseColor("#4bc4fb"));
            eight.setBackgroundResource(R.mipmap.fangbai);
            eight.setTextColor(Color.parseColor("#4bc4fb"));
        } else if (number == 7) {
            seven.setBackgroundResource(R.mipmap.fanglan);
            seven.setTextColor(Color.parseColor("#ffffff"));
            two.setBackgroundResource(R.mipmap.fangbai);
            two.setTextColor(Color.parseColor("#4bc4fb"));
            three.setBackgroundResource(R.mipmap.fangbai);
            three.setTextColor(Color.parseColor("#4bc4fb"));
            four.setBackgroundResource(R.mipmap.fangbai);
            four.setTextColor(Color.parseColor("#4bc4fb"));
            five.setBackgroundResource(R.mipmap.fangbai);
            five.setTextColor(Color.parseColor("#4bc4fb"));
            six.setBackgroundResource(R.mipmap.fangbai);
            six.setTextColor(Color.parseColor("#4bc4fb"));
            one.setBackgroundResource(R.mipmap.fangbai);
            one.setTextColor(Color.parseColor("#4bc4fb"));
            eight.setBackgroundResource(R.mipmap.fangbai);
            eight.setTextColor(Color.parseColor("#4bc4fb"));
        } else if (number == 8) {
            eight.setBackgroundResource(R.mipmap.fanglan);
            eight.setTextColor(Color.parseColor("#ffffff"));
            two.setBackgroundResource(R.mipmap.fangbai);
            two.setTextColor(Color.parseColor("#4bc4fb"));
            three.setBackgroundResource(R.mipmap.fangbai);
            three.setTextColor(Color.parseColor("#4bc4fb"));
            four.setBackgroundResource(R.mipmap.fangbai);
            four.setTextColor(Color.parseColor("#4bc4fb"));
            five.setBackgroundResource(R.mipmap.fangbai);
            five.setTextColor(Color.parseColor("#4bc4fb"));
            six.setBackgroundResource(R.mipmap.fangbai);
            six.setTextColor(Color.parseColor("#4bc4fb"));
            seven.setBackgroundResource(R.mipmap.fangbai);
            seven.setTextColor(Color.parseColor("#4bc4fb"));
            one.setBackgroundResource(R.mipmap.fangbai);
            one.setTextColor(Color.parseColor("#4bc4fb"));
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_roomnum_one:
                one.setBackgroundResource(R.mipmap.fanglan);
                one.setTextColor(Color.parseColor("#ffffff"));
                two.setBackgroundResource(R.mipmap.fangbai);
                two.setTextColor(Color.parseColor("#4bc4fb"));
                three.setBackgroundResource(R.mipmap.fangbai);
                three.setTextColor(Color.parseColor("#4bc4fb"));
                four.setBackgroundResource(R.mipmap.fangbai);
                four.setTextColor(Color.parseColor("#4bc4fb"));
                five.setBackgroundResource(R.mipmap.fangbai);
                five.setTextColor(Color.parseColor("#4bc4fb"));
                six.setBackgroundResource(R.mipmap.fangbai);
                six.setTextColor(Color.parseColor("#4bc4fb"));
                seven.setBackgroundResource(R.mipmap.fangbai);
                seven.setTextColor(Color.parseColor("#4bc4fb"));
                eight.setBackgroundResource(R.mipmap.fangbai);
                eight.setTextColor(Color.parseColor("#4bc4fb"));
                number = 1;
                activity.num = number;
                activity.zong = activity.total * activity.xianjia*number;
                activity.tvAtyHotelsureorderTotal.setText("￥" + activity.zong);
                // activity.number=1;
                activity.tvAtyHotelsureorderRoomnum.setText("1间");
                activity.llAtyHotelsureorder2.setVisibility(View.GONE);
                activity.llAtyHotelsureorder3.setVisibility(View.GONE);
                activity.llAtyHotelsureorder4.setVisibility(View.GONE);
                activity.llAtyHotelsureorder5.setVisibility(View.GONE);
                activity.llAtyHotelsureorder6.setVisibility(View.GONE);
                activity.llAtyHotelsureorder7.setVisibility(View.GONE);
                activity.llAtyHotelsureorder8.setVisibility(View.GONE);
                dismiss();
                break;
            case R.id.tv_dialog_roomnum_two:
                two.setBackgroundResource(R.mipmap.fanglan);
                two.setTextColor(Color.parseColor("#ffffff"));
                one.setBackgroundResource(R.mipmap.fangbai);
                one.setTextColor(Color.parseColor("#4bc4fb"));
                three.setBackgroundResource(R.mipmap.fangbai);
                three.setTextColor(Color.parseColor("#4bc4fb"));
                four.setBackgroundResource(R.mipmap.fangbai);
                four.setTextColor(Color.parseColor("#4bc4fb"));
                five.setBackgroundResource(R.mipmap.fangbai);
                five.setTextColor(Color.parseColor("#4bc4fb"));
                six.setBackgroundResource(R.mipmap.fangbai);
                six.setTextColor(Color.parseColor("#4bc4fb"));
                seven.setBackgroundResource(R.mipmap.fangbai);
                seven.setTextColor(Color.parseColor("#4bc4fb"));
                eight.setBackgroundResource(R.mipmap.fangbai);
                eight.setTextColor(Color.parseColor("#4bc4fb"));
                number = 2;
                activity.num = number;
                activity.zong = activity.total * activity.xianjia*number;
                activity.tvAtyHotelsureorderTotal.setText("￥" + activity.zong);
                activity.tvAtyHotelsureorderRoomnum.setText("2间");
                activity.llAtyHotelsureorder2.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder3.setVisibility(View.GONE);
                activity.llAtyHotelsureorder4.setVisibility(View.GONE);
                activity.llAtyHotelsureorder5.setVisibility(View.GONE);
                activity.llAtyHotelsureorder6.setVisibility(View.GONE);
                activity.llAtyHotelsureorder7.setVisibility(View.GONE);
                activity.llAtyHotelsureorder8.setVisibility(View.GONE);
                dismiss();
                break;
            case R.id.tv_dialog_roomnum_three:
                three.setBackgroundResource(R.mipmap.fanglan);
                three.setTextColor(Color.parseColor("#ffffff"));
                two.setBackgroundResource(R.mipmap.fangbai);
                two.setTextColor(Color.parseColor("#4bc4fb"));
                one.setBackgroundResource(R.mipmap.fangbai);
                one.setTextColor(Color.parseColor("#4bc4fb"));
                four.setBackgroundResource(R.mipmap.fangbai);
                four.setTextColor(Color.parseColor("#4bc4fb"));
                five.setBackgroundResource(R.mipmap.fangbai);
                five.setTextColor(Color.parseColor("#4bc4fb"));
                six.setBackgroundResource(R.mipmap.fangbai);
                six.setTextColor(Color.parseColor("#4bc4fb"));
                seven.setBackgroundResource(R.mipmap.fangbai);
                seven.setTextColor(Color.parseColor("#4bc4fb"));
                eight.setBackgroundResource(R.mipmap.fangbai);
                eight.setTextColor(Color.parseColor("#4bc4fb"));
                number = 3;
                activity.num = number;
                activity.zong = activity.total * activity.xianjia*number;
                activity.tvAtyHotelsureorderTotal.setText("￥" + activity.zong);
                activity.tvAtyHotelsureorderRoomnum.setText("3间");
                activity.llAtyHotelsureorder2.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder3.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder4.setVisibility(View.GONE);
                activity.llAtyHotelsureorder5.setVisibility(View.GONE);
                activity.llAtyHotelsureorder6.setVisibility(View.GONE);
                activity.llAtyHotelsureorder7.setVisibility(View.GONE);
                activity.llAtyHotelsureorder8.setVisibility(View.GONE);
                dismiss();
                break;
            case R.id.tv_dialog_roomnum_four:
                four.setBackgroundResource(R.mipmap.fanglan);
                four.setTextColor(Color.parseColor("#ffffff"));
                two.setBackgroundResource(R.mipmap.fangbai);
                two.setTextColor(Color.parseColor("#4bc4fb"));
                three.setBackgroundResource(R.mipmap.fangbai);
                three.setTextColor(Color.parseColor("#4bc4fb"));
                one.setBackgroundResource(R.mipmap.fangbai);
                one.setTextColor(Color.parseColor("#4bc4fb"));
                five.setBackgroundResource(R.mipmap.fangbai);
                five.setTextColor(Color.parseColor("#4bc4fb"));
                six.setBackgroundResource(R.mipmap.fangbai);
                six.setTextColor(Color.parseColor("#4bc4fb"));
                seven.setBackgroundResource(R.mipmap.fangbai);
                seven.setTextColor(Color.parseColor("#4bc4fb"));
                eight.setBackgroundResource(R.mipmap.fangbai);
                eight.setTextColor(Color.parseColor("#4bc4fb"));
                number = 4;
                activity.num = number;
                activity.zong = activity.total * activity.xianjia*number;
                activity.tvAtyHotelsureorderTotal.setText("￥" + activity.zong);
                activity.tvAtyHotelsureorderRoomnum.setText("4间");
                activity.llAtyHotelsureorder2.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder3.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder4.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder5.setVisibility(View.GONE);
                activity.llAtyHotelsureorder6.setVisibility(View.GONE);
                activity.llAtyHotelsureorder7.setVisibility(View.GONE);
                activity.llAtyHotelsureorder8.setVisibility(View.GONE);
                dismiss();
                break;
            case R.id.tv_dialog_roomnum_five:
                five.setBackgroundResource(R.mipmap.fanglan);
                five.setTextColor(Color.parseColor("#ffffff"));
                two.setBackgroundResource(R.mipmap.fangbai);
                two.setTextColor(Color.parseColor("#4bc4fb"));
                three.setBackgroundResource(R.mipmap.fangbai);
                three.setTextColor(Color.parseColor("#4bc4fb"));
                four.setBackgroundResource(R.mipmap.fangbai);
                four.setTextColor(Color.parseColor("#4bc4fb"));
                one.setBackgroundResource(R.mipmap.fangbai);
                one.setTextColor(Color.parseColor("#4bc4fb"));
                six.setBackgroundResource(R.mipmap.fangbai);
                six.setTextColor(Color.parseColor("#4bc4fb"));
                seven.setBackgroundResource(R.mipmap.fangbai);
                seven.setTextColor(Color.parseColor("#4bc4fb"));
                eight.setBackgroundResource(R.mipmap.fangbai);
                eight.setTextColor(Color.parseColor("#4bc4fb"));
                number = 5;
                activity.num = number;
                activity.zong = activity.total * activity.xianjia*number;
                activity.tvAtyHotelsureorderTotal.setText("￥" + activity.zong);
                activity.tvAtyHotelsureorderRoomnum.setText("5间");
                activity.llAtyHotelsureorder2.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder3.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder4.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder5.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder6.setVisibility(View.GONE);
                activity.llAtyHotelsureorder7.setVisibility(View.GONE);
                activity.llAtyHotelsureorder8.setVisibility(View.GONE);
                dismiss();
                break;
            case R.id.tv_dialog_roomnum_six:
                six.setBackgroundResource(R.mipmap.fanglan);
                six.setTextColor(Color.parseColor("#ffffff"));
                two.setBackgroundResource(R.mipmap.fangbai);
                two.setTextColor(Color.parseColor("#4bc4fb"));
                three.setBackgroundResource(R.mipmap.fangbai);
                three.setTextColor(Color.parseColor("#4bc4fb"));
                four.setBackgroundResource(R.mipmap.fangbai);
                four.setTextColor(Color.parseColor("#4bc4fb"));
                five.setBackgroundResource(R.mipmap.fangbai);
                five.setTextColor(Color.parseColor("#4bc4fb"));
                one.setBackgroundResource(R.mipmap.fangbai);
                one.setTextColor(Color.parseColor("#4bc4fb"));
                seven.setBackgroundResource(R.mipmap.fangbai);
                seven.setTextColor(Color.parseColor("#4bc4fb"));
                eight.setBackgroundResource(R.mipmap.fangbai);
                eight.setTextColor(Color.parseColor("#4bc4fb"));
                number = 6;
                activity.num = number;
                activity.zong = activity.total * activity.xianjia*number;
                activity.tvAtyHotelsureorderTotal.setText("￥" + activity.zong);
                activity.tvAtyHotelsureorderRoomnum.setText("6间");
                activity.llAtyHotelsureorder2.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder3.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder4.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder5.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder6.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder7.setVisibility(View.GONE);
                activity.llAtyHotelsureorder8.setVisibility(View.GONE);
                dismiss();
                break;
            case R.id.tv_dialog_roomnum_seven:
                seven.setBackgroundResource(R.mipmap.fanglan);
                seven.setTextColor(Color.parseColor("#ffffff"));
                two.setBackgroundResource(R.mipmap.fangbai);
                two.setTextColor(Color.parseColor("#4bc4fb"));
                three.setBackgroundResource(R.mipmap.fangbai);
                three.setTextColor(Color.parseColor("#4bc4fb"));
                four.setBackgroundResource(R.mipmap.fangbai);
                four.setTextColor(Color.parseColor("#4bc4fb"));
                five.setBackgroundResource(R.mipmap.fangbai);
                five.setTextColor(Color.parseColor("#4bc4fb"));
                six.setBackgroundResource(R.mipmap.fangbai);
                six.setTextColor(Color.parseColor("#4bc4fb"));
                one.setBackgroundResource(R.mipmap.fangbai);
                one.setTextColor(Color.parseColor("#4bc4fb"));
                eight.setBackgroundResource(R.mipmap.fangbai);
                eight.setTextColor(Color.parseColor("#4bc4fb"));
                number = 7;
                activity.num = number;
                activity.zong = activity.total * activity.xianjia*number;
                activity.tvAtyHotelsureorderTotal.setText("￥" + activity.zong);
                activity.tvAtyHotelsureorderRoomnum.setText("7间");
                activity.llAtyHotelsureorder2.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder3.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder4.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder5.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder6.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder7.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder8.setVisibility(View.GONE);
                dismiss();
                break;
            case R.id.tv_dialog_roomnum_eight:
                eight.setBackgroundResource(R.mipmap.fanglan);
                eight.setTextColor(Color.parseColor("#ffffff"));
                two.setBackgroundResource(R.mipmap.fangbai);
                two.setTextColor(Color.parseColor("#4bc4fb"));
                three.setBackgroundResource(R.mipmap.fangbai);
                three.setTextColor(Color.parseColor("#4bc4fb"));
                four.setBackgroundResource(R.mipmap.fangbai);
                four.setTextColor(Color.parseColor("#4bc4fb"));
                five.setBackgroundResource(R.mipmap.fangbai);
                five.setTextColor(Color.parseColor("#4bc4fb"));
                six.setBackgroundResource(R.mipmap.fangbai);
                six.setTextColor(Color.parseColor("#4bc4fb"));
                seven.setBackgroundResource(R.mipmap.fangbai);
                seven.setTextColor(Color.parseColor("#4bc4fb"));
                one.setBackgroundResource(R.mipmap.fangbai);
                one.setTextColor(Color.parseColor("#4bc4fb"));
                number = 8;
                activity.num = number;
                activity.zong = activity.total * activity.xianjia*number;
                activity.tvAtyHotelsureorderTotal.setText("￥" + activity.zong);
                activity.tvAtyHotelsureorderRoomnum.setText("8间");
                activity.llAtyHotelsureorder2.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder3.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder4.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder5.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder6.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder7.setVisibility(View.VISIBLE);
                activity.llAtyHotelsureorder8.setVisibility(View.VISIBLE);
                dismiss();
                break;
            case R.id.iv_dialog_cha:
                dismiss();
                break;
        }
    }
}
