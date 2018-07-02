package com.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;


/**
 * Created by Administrator on 2016/7/22 0022.
 */
public class MyLinearLayoutItem extends LinearLayout {
    boolean mLeftImageVisibility=false;     //左侧图片是否显示
    boolean bottomLinebool=false;           //底部线是否显示
    int mLeftImage;                         //左侧图片
    String mContent;                        //内容

    ImageView left_Image;                   //左侧图片
    ImageView right_Image;                  //右侧图片

    LinearLayout ll_myview;

    public MyLinearLayoutItem(Context context) {
        this(context, null);
    }

    public MyLinearLayoutItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearLayoutItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getValues(attrs);
        initView(context);

    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.my_linearlayout, this);
        left_Image = (ImageView) findViewById(R.id.im_adapter_my_left);
        right_Image= (ImageView) findViewById(R.id.im_adapter_my_right);
        TextView content_tv= (TextView)findViewById(R.id.tv_adapter_my_context);
        TextView bottomline= (TextView)findViewById(R.id.tv_adapter_my_bottomline);
        ll_myview= (LinearLayout) findViewById(R.id.ll_myview);

        //是否显示左侧图片
        if (mLeftImageVisibility){
            left_Image.setVisibility(VISIBLE);
            left_Image.setImageResource(mLeftImage);
        }else{
            left_Image.setVisibility(GONE);
        }

        //是否显示底部线
        if (bottomLinebool){
            bottomline.setVisibility(VISIBLE);
        }else {
            bottomline.setVisibility(GONE);
        }
        //左侧内容
        content_tv.setText(mContent);
    }

    private void getValues(AttributeSet attrs){
        /*<attr name="leftImageVisibility" format="boolean"/>
        <attr name="leftImage" format="integer"/>
        <attr name="leftText" format="String"/>*/

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MyLinearLayoutItem);

        mLeftImageVisibility=array.getBoolean(R.styleable.MyLinearLayoutItem_leftImageVisibility, false);
        mContent=array.getString(R.styleable.MyLinearLayoutItem_leftText);
        mLeftImage=array.getResourceId(R.styleable.MyLinearLayoutItem_leftImage, 0);
        bottomLinebool=array.getBoolean(R.styleable.MyLinearLayoutItem_bottomLine, false);
        array.recycle();
    }


    //设置右侧图片
    public void setRightImage(int image){
        right_Image.setImageResource(image);
    }
}
