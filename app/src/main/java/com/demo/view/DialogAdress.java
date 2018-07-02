package com.demo.view;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.demo.demo.myapplication.R;

/**
 * Created by Administrator on 2016/5/20.
 */
public class DialogAdress {
    private Dialog dialog;
    private Context mcontext;
    private Display display;
    private TextView txt_cancel,text_cofirm;
    private CustomNumberPicker valuepicker, propicker, townpicker;
    private confirmListener mylistener;
    private boolean isrun = true;
    private City mycity;
    private int pro=0;
    public DialogAdress(Context context){
        this.mcontext=context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DialogAdress builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(mcontext).inflate(
                R.layout.dialog_address, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth((int)(display.getWidth()*0.95));

        // 获取自定义Dialog布局中的控件
        mycity = new City();
        valuepicker = (CustomNumberPicker) view.findViewById(R.id.mypicker);
        propicker = (CustomNumberPicker) view.findViewById(R.id.propicker);
        townpicker = (CustomNumberPicker) view.findViewById(R.id.townpicker);
        valuepicker.setDisplayedValues(mycity.getCity("0"));
        valuepicker.setMinValue(0);
        valuepicker.setMaxValue(mycity.getCity("0").length - 1);
        valuepicker.setValue(0);
        valuepicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        valuepicker.setNumberPickerDividerColor(valuepicker);
        pro=0;
        valuepicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                switch (i) {
                    case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                    case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                        if (isrun == true) {
                            propicker.setMaxValue(0);
                            isrun = false;
                            String[] date = mycity.getCity("0_" + valuepicker.getValue());
                            propicker.setDisplayedValues(date);
                            propicker.setValue(0);
                            propicker.setMinValue(0);
                            propicker.setMaxValue(date.length - 1);
                            String[] towndate = mycity.getCity("0_" + valuepicker.getValue() + "_0");
                            townpicker.setMaxValue(0);
                            townpicker.setDisplayedValues(towndate);
                            townpicker.setValue(0);
                            townpicker.setMinValue(0);
                            townpicker.setMaxValue(towndate.length - 1);
                            pro = valuepicker.getValue();
                            isrun = true;
                        }
                        break;
                    case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }
            }
        });
        valuepicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (isrun == true) {
                    propicker.setMaxValue(0);
                    isrun = false;
                    String[] date = mycity.getCity("0_" + valuepicker.getValue());
                    propicker.setDisplayedValues(date);
                    propicker.setValue(0);
                    propicker.setMinValue(0);
                    propicker.setMaxValue(date.length - 1);
                    String[] towndate = mycity.getCity("0_" + valuepicker.getValue() + "_0");
                    townpicker.setMaxValue(0);
                    townpicker.setDisplayedValues(towndate);
                    townpicker.setValue(0);
                    townpicker.setMinValue(0);
                    townpicker.setMaxValue(towndate.length - 1);
                    pro = valuepicker.getValue();
                    isrun = true;
                }
            }
        });
        propicker.setDisplayedValues(mycity.getCity("0_0"));
        propicker.setMinValue(0);
        propicker.setMaxValue(mycity.getCity("0_0").length - 1);
        propicker.setValue(1);
        propicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        propicker.setNumberPickerDividerColor(propicker);
        propicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                switch (i) {
                    case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                    case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                        if (isrun == true) {
                            String[] towndate = mycity.getCity("0_" + pro + "_" + propicker.getValue());
                            townpicker.setMaxValue(0);
                            townpicker.setDisplayedValues(towndate);
                            townpicker.setValue(0);
                            townpicker.setMinValue(0);
                            townpicker.setMaxValue(towndate.length - 1);
                            isrun = true;
                        }
                        break;
                    case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }
            }
        });
        propicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (isrun == true) {
                    String[] towndate = mycity.getCity("0_" + pro + "_" + propicker.getValue());
                    townpicker.setMaxValue(0);
                    townpicker.setDisplayedValues(towndate);
                    townpicker.setValue(0);
                    townpicker.setMinValue(0);
                    townpicker.setMaxValue(towndate.length - 1);
                    isrun = true;
                }
            }
        });
        townpicker.setDisplayedValues(mycity.getCity("0_0_0"));
        townpicker.setMinValue(0);
        townpicker.setMaxValue(mycity.getCity("0_0_0").length - 1);
        townpicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        townpicker.setNumberPickerDividerColor(townpicker);
        txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        text_cofirm=(TextView)view.findViewById(R.id.txt_confirm);
        text_cofirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(mylistener!=null){
                    mylistener.onClick();
                }
            }
        });


        // 定义Dialog布局和参数
        dialog = new Dialog(mcontext, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public DialogAdress setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public DialogAdress setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void setConfim(confirmListener listener){
        this.mylistener=listener;
    }

    public interface confirmListener {
        void onClick();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public String getprovince(){
        return mycity.getCity("0")[valuepicker.getValue()];
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public String getAdress(){
        return mycity.getCity("0_" + pro)[propicker.getValue()];
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public String gettown(){
        return mycity.getCity("0_" + pro+"_"+propicker.getValue())[townpicker.getValue()];
    }
}
