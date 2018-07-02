package com.pubfin.tools.myview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/5/11 0011.
 * 自定义获取验证码控件
 */
public class CodeButton extends Button{

    private int color_01;//初始背景颜色
    private int color_02;//点击后背景颜色
    private int color_03;//初始文字颜色
    private int color_04;//点击后文字颜色
    private int time;//倒计时时长
    private int currTime = -1;

    private Timer timer;
    private TimerTask timerTask;

    Handler handler = new Handler(){
        public void handleMessage(Message message)
        {
            setText(currTime + "S后重新获取");
            if (currTime < 0)
            {
                setText("获取验证码");
                setBackgroundColor(color_01);
                setTextColor(color_03);
                timer.cancel();
            }
        }
    };

    public CodeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CodeButton(Context context) {
        super(context);
    }

    public CodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置空间初始背景颜色
     * @param color_01:初始背景颜色
     * @param color_02:点击后背景颜色
     * @param color_03:初始文字颜色
     * @param color_04:点击后文字颜色
     */
    public CodeButton setINITBackColor(int color_01, int color_02, int color_03, int color_04)
    {
        this.color_01 = color_01;
        this.color_02 = color_02;
        this.color_03 = color_03;
        this.color_04 = color_04;
        setBackgroundColor(color_01);
        setTextColor(color_03);
        return this;
    }

    /**
     * 设置倒计时时长
     */
    public void setTime(int time)
    {
        this.time = time;
    }

    public void onClickEvent() {
        System.out.print(currTime);
        if (currTime < 0)
        {
            currTime = time;
            timer = new Timer(true);
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    currTime--;
                    handler.sendEmptyMessage(0);
                }
            };
            timer.schedule(timerTask, 1000, 1000);
            setText(currTime + "S后重新获取");
            setBackgroundColor(color_02);
            setTextColor(color_04);
        }
    }
}
