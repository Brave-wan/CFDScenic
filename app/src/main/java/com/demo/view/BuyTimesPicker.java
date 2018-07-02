package com.demo.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 购买次数选择器
 */
public class BuyTimesPicker extends NumberPicker {

    public BuyTimesPicker(Context context) {
        super(context);
    }

    public BuyTimesPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BuyTimesPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BuyTimesPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 重新算偏差
     *
     * @return
     */
    @Override
    public float getValue() {
        float value = super.getValue();
        //检查 有效值，检测是否 是 Interval 的倍数，如果不是就 取小
        float validValue = getValidValue(value);

        //计算偏差值
        float validOffset = validValue % getInterval();
        if (validOffset != 0) {
            validValue = super.getValidValue(validValue - validOffset);
        }

        //TODO 需要设置value回去给这个view？
        //setValue(value);
        return validValue;
    }
}
