package com.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.demo.demo.myapplication.R;

import java.util.HashSet;
import java.util.Set;

/**
 * 数字选择器
 * 带-,+
 * <p/>
 * 自定义属性
 * max 最大值
 * min 最小值
 * value 默认值
 * interval 每次的递增数量
 * number_mode 显示的模式
 * <p/>
 * <p/>
 * 需要修改各个 样式 可以通过覆盖自己项目style ,dimen, color 来修改
 * <p/>
 * 涉及到的 style
 * NumberPickerContainer,NumberPickerControlButton,NumberPickerControlButton.Pre,NumberPickerControlButton.Next,NumberPickerEditText
 * <p/>
 * 涉及到的 color
 * number_picker_divider_color
 * <p/>
 * 涉及到 到dimen
 * number_picker_button_width,number_picker_number_width,number_picker_height
 *
 * @author chenzipeng
 */
public class NumberPickerPicker extends LinearLayout implements View.OnClickListener {
    final String TAG = NumberPickerPicker.class.getSimpleName();
    boolean DEBUG = true;

    public static final int MODE_INTEGER = 0;
    public static final int MODE_FLOAT = 1;
    protected int mMode = MODE_INTEGER;//模式

    protected float mMax = Integer.MAX_VALUE;//最大值
    protected float mMin = Integer.MIN_VALUE;//最小值
    protected float mInterval = 1;//递增递减的范围
    protected float mDefalutValue = 0;

    protected View mPreButton1;
    protected View mNextButton1;
    protected TextView mValueView1;

    protected Set<OnValueChangeListener> mOnValueChangeListeners = new HashSet<>();

    public NumberPickerPicker(Context context) {
        super(context);
        init(null);
    }

    public NumberPickerPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NumberPickerPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NumberPickerPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    void init(AttributeSet attrs) {

        LinearLayout contentView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.widget_number_picker_cheng, this);
        mPreButton1 = findViewById(R.id.control_prepre);
        mNextButton1 = findViewById(R.id.control_nextnext);
        mValueView1 = (TextView) findViewById(R.id.numbernumber);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NumberPicker);

            if (a.hasValue(R.styleable.NumberPicker_max)) {
                setMax(a.getFloat(R.styleable.NumberPicker_max, Integer.MAX_VALUE));
            }

            if (a.hasValue(R.styleable.NumberPicker_min)) {
                setMin(a.getFloat(R.styleable.NumberPicker_min, Integer.MIN_VALUE));
            }

            if (a.hasValue(R.styleable.NumberPicker_value)) {
                mDefalutValue = (a.getFloat(R.styleable.NumberPicker_value, 0));
            }

            if (a.hasValue(R.styleable.NumberPicker_interval)) {
                setInterval(a.getFloat(R.styleable.NumberPicker_interval, 1));
            }

            if (a.hasValue(R.styleable.NumberPicker_number_mode)) {
                mMode = a.getInt(R.styleable.NumberPicker_number_mode, MODE_INTEGER);
            }
            a.recycle();
        }

        mValueView1.addTextChangedListener(mTextWatcher);

        mPreButton1.setOnClickListener(this);
        mNextButton1.setOnClickListener(this);
//        mAllLinearLayout.setOnClickListener(this);
        setMode(getMode());

        setValue(mDefalutValue);
    }

    public float getMax() {
        return mMax;
    }

    /**
     * 设置最大值，设置的值必须 >= min
     *
     * @param max
     * @return 设置后的值
     */
    public float setMax(float max) {
        if (max >= getMin()) {
            mMax = max;
        }
        return mMax;
    }

    public float getMin() {
        return mMin;
    }

    /**
     * 设置最小值 ，设置的值，必须 <= min
     *
     * @param min
     * @return 设置后的值
     */
    public float setMin(float min) {
        if (min <= getMax()) {
            mMin = min;
        }
        return mMin;
    }

    public float getValue() {
        try {
            if (getMode() == MODE_INTEGER) {
                return Integer.valueOf(mValueView1.getText().toString());
            } else {
                return Float.valueOf(mValueView1.getText().toString());
            }
        } catch (NumberFormatException e) {
            Log.w("NumberPicker", "format value faild NumberFormatException");
            return 0;
        }
    }

    public float getInterval() {
        return mInterval;
    }

    public void setInterval(float interval) {
        mInterval = interval;
    }

    public int getMode() {
        return mMode;
    }

    public void setMode(int mode) {
        mMode = mode;
        switch (getMode()) {
            case MODE_FLOAT:
                mValueView1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case MODE_INTEGER:
            default:
                mValueView1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        float interval = getInterval();

        if (v.getId() == R.id.control_prepre) {
            interval = -getInterval();
        } else if (v.getId() == R.id.control_nextnext) {
            interval = getInterval();
        }

        addInterval(interval);
    }

    /**
     * 设置 为某个值
     *
     * @param value
     */
    public void setValue(float value) {
        updateValue(getValidValue(value));
    }

    /**
     * 计算min - max 范围内的有效值
     *
     * @return
     */
    public float getValidValue(float value) {
        return Math.max(getMin(), Math.min(getMax(), value));
    }

    /**
     * 获取显示的text
     *
     * @param value
     * @return
     */
    public String getDisplayText(float value) {
        String displayText;
        switch (getMode()) {
            case MODE_INTEGER:
                displayText = String.valueOf((int) value);
                break;
            case MODE_FLOAT:
            default:
                displayText = String.valueOf(value);
                break;
        }
        return displayText;
    }

    /**
     * 更新值到UI
     *
     * @param value
     */
    private void updateValue(float value) {
        mValueView1.setText(getDisplayText(value));
        //mValueView.setSelection(getDisplayText(value).length());
    }

    /**
     * 递增某个值，可以为负数
     *
     * @param interval
     */
    public void addInterval(float interval) {
        setValue(getValue() + interval);
    }

    TextWatcher mTextWatcher = new TextWatcher() {

        private float mOrginValue = 0;//原来的值

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            try {
                mOrginValue = Float.valueOf(s.toString());
            } catch (NumberFormatException e) {
                Log.w(TAG, "beforeTextChanged input string NumberFormatException source:" + s + " will set min " + getMin());
                mOrginValue = getMin();
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //屏蔽此 自动检测修正数值的方法，这里会导致 无法手工输入

//            try {
//                float targeValue = Float.valueOf(s.toString());
//                if (targeValue < getMin() || targeValue > getMax()) {
//                    updateValue(getValidValue(targeValue));
//                }
//            } catch (NumberFormatException e) {
//                Log.w(TAG, "onTextChanged input string NumberFormatException source:" + s);
//            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                float targeValue = Float.valueOf(s.toString());
                if (targeValue >= getMin() && targeValue <= getMax()) {
                    for (OnValueChangeListener listener : mOnValueChangeListeners) {
                        listener.onChange(mOrginValue, targeValue);
                    }
                }
            } catch (NumberFormatException e) {
                Log.w(TAG, "afterTextChanged input string NumberFormatException source:" + s);
            }
        }
    };


    /**
     * 值改变监听器
     */
    public interface OnValueChangeListener {
        void onChange(float orginValue, float changedValue);
    }

    public void addOnValueChangeListener(OnValueChangeListener listener) {
        mOnValueChangeListeners.add(listener);
    }

    public void removeOnValueChangeListener(OnValueChangeListener listener) {
        mOnValueChangeListeners.remove(listener);
    }

}
