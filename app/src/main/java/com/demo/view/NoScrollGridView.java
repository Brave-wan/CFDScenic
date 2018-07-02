package com.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NoScrollGridView extends GridView {

	private boolean haveScrollbar = false;

	public NoScrollGridView(Context context) {
		super(context);
	}

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 锟斤拷锟斤拷锟角凤拷锟斤拷ScrollBar锟斤拷锟斤拷要锟斤拷ScollView锟斤拷锟斤拷示时锟斤拷应锟斤拷锟斤拷锟斤拷为false锟斤拷 默锟斤拷为 true
	 *
	 * @param haveScrollbars
	 */
	public void setHaveScrollbar(boolean haveScrollbar) {

		this.haveScrollbar = haveScrollbar;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (haveScrollbar == false) {
			int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
