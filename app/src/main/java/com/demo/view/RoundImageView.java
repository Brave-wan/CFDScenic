package com.demo.view;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.demo.demo.myapplication.R;


public class RoundImageView extends ImageView {
	private Context mContext;
	// 圆的宽度
	private int thickness = 0;
	// 若值等于0，则不设置外圆、内圆
	private int mBorderOutSideColor = 0;
	private int mBorderInsiderColor = 0;
	// 因为担心用户设置白色，而不会画圆的原因，将颜色设置0xFFFFFFFE，不用0xFFFFFFFF
	private int defaultColor = 0xFFFFFFFE;
	private int defWidth = 0;
	private int defHeight = 0;

	public RoundImageView(Context context) {
		super(context);
		mContext = context;
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setAttributeset(attrs);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setAttributeset(attrs);
	}

	public void setAttributeset(AttributeSet attrs) {
		// 无设置参数，使用默认值
		TypedArray ta = mContext.obtainStyledAttributes(attrs, // 数组存放
				R.styleable.roundimageview);
		thickness = ta.getDimensionPixelSize(
				R.styleable.roundimageview_border_thickness, 0);
		mBorderInsiderColor = ta.getColor(
				R.styleable.roundimageview_border_inside_color, defaultColor);
		mBorderOutSideColor = ta.getColor(
				R.styleable.roundimageview_border_outside_color, defaultColor);
		ta.recycle(); // 回收以便再次使用
	}

	@Override
	protected void onDraw(Canvas canvas) {
		System.out.println(mBorderInsiderColor + "  insider");
		System.out.println(mBorderOutSideColor + "  outsider");
		System.out.println(defaultColor);
		// super.onDraw(canvas);
		Drawable drawable = getDrawable();
		if (drawable != null && drawable.isStateful()) {
			drawable.setState(getDrawableState());
		}
		if (drawable == null) {
			return;
		}
		// getWidth()是获得ImageView的宽度，getHeight()是获得ImageView的高度
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		// 内部的主体逻辑是判断是否需要重新测量视图大小
		this.measure(0, 0);
		if (drawable.getClass() == NinePatchDrawable.class)
			return;
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bitmap = bd.getBitmap();
		bitmap = bitmap.copy(Config.ARGB_8888, true);
		if (defWidth == 0) {
			defWidth = getWidth(); // 获取view的宽度，不是图片的宽度
		}
		if (defHeight == 0) {
			defHeight = getHeight();
		}
		int radius = 0;
		// 图片边框宽度是以边框线为中心，向两侧均匀描边，所以要注意半径的处理
		if (mBorderInsiderColor != defaultColor
				&& mBorderOutSideColor != defaultColor) { // 画出内外边框
			radius = (defWidth < defHeight ? defWidth : defHeight) / 2
					- thickness * 2;
			drawCircularBorder(canvas, radius + thickness / 2,
					mBorderInsiderColor);
			drawCircularBorder(canvas, radius + thickness / 2 + thickness,
					mBorderOutSideColor);
		} else if (mBorderInsiderColor != defaultColor
				&& mBorderOutSideColor == defaultColor) { // 画出内边框
			radius = (defWidth < defHeight ? defWidth : defHeight) / 2
					- thickness / 2;
			drawCircularBorder(canvas, radius + thickness / 2,
					mBorderInsiderColor);
		} else if (mBorderInsiderColor == defaultColor
				&& mBorderOutSideColor != defaultColor) { // 画出外边框
			radius = (defWidth < defHeight ? defWidth : defHeight) / 2
					- thickness;
			drawCircularBorder(canvas, radius + thickness / 2,
					mBorderOutSideColor);
		} else { // 无边框
			radius = (defWidth < defHeight ? defWidth : defHeight) / 2;
		}

		Bitmap roundBitmap = getRoundBitmap(bitmap, radius);

		canvas.drawBitmap(roundBitmap, defWidth / 2 - radius, defHeight / 2
				- radius, null);
	}

	/**
	 * 获得圆形位图
	 * 
	 * @param bitmap
	 * @param radius
	 * @return
	 */
	private Bitmap getRoundBitmap(Bitmap bitmap, int radius) {
		Bitmap scaledSrcBmp;
		// 直径
		int diameter = radius * 2;
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		int squalWidth = 0;
		int squalHeight = 0;
		Bitmap squareBtimap = null;
		// 现将图片裁剪成正方形
		if (width > height) {
			squalWidth = squalHeight = height;
			y = 0;
			x = (width - height) / 2;
			squareBtimap = Bitmap.createBitmap(bitmap, x, y, squalWidth,
					squalHeight);
		} else if (width < height) {
			squalWidth = squalHeight = width;
			x = 0;
			y = (height - width) / 2;
			squareBtimap = Bitmap.createBitmap(bitmap, x, y, squalWidth,
					squalHeight);
		} else if (width == height) {
			squareBtimap = bitmap;
		}
		// 判断直径与宽高是否相等
		if (squareBtimap.getWidth() != diameter
				|| squareBtimap.getHeight() != diameter) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBtimap, diameter,
					diameter, true);

		} else {
			scaledSrcBmp = squareBtimap;
		}
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
				scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		bitmap = null;
		scaledSrcBmp = null;
		squareBtimap = null;
		return output;
	}

	/**
	 * 画空心圆
	 * 
	 * @param canvas
	 * @param radius
	 * @param color
	 */

	private void drawCircularBorder(Canvas canvas, int radius, int color) {
		Paint paint = new Paint();
		// 锯齿处理
		paint.setAntiAlias(true);
		// 对位图进行滤波处理
		paint.setFilterBitmap(true);
		// 位图抖动处理
		paint.setDither(true);
		paint.setColor(color);
		// 设置成抗锯齿
		paint.setStyle(Style.STROKE);
		// 边框的宽度
		paint.setStrokeWidth(thickness);
		canvas.drawCircle(defWidth / 2, defHeight / 2, radius, paint);
	}
}
