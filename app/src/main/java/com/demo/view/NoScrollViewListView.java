package com.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author on 2016/1/5 10:09.
 */
public class NoScrollViewListView extends ListView {


    public NoScrollViewListView(Context context) {
        super(context);

    }

    public NoScrollViewListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
