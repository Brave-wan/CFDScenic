package com.demo.view.photo;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.demo.demo.myapplication.R;


/**
 * Created by LinYi.
 * <br/>自定义的PopupWindow
 * <br/>
 * popupwindow的最外层的container需要加id(ppw_layout)
 */
public class PPWindow extends PopupWindow {

    private Activity mActivity;


    public PPWindow(Activity act, View contentView) {
        super(act);
        //软键盘设置，重新布局popwindows，让它网上移
        setInputMethodMode(INPUT_METHOD_NEEDED);
        //也可以使用 adjust_pan 但是，这个 ppwindow的contentview 必须是可以 滚动的类型才会往上移动
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        this.mActivity = act;

        this.setContentView(contentView);//设置ppw的view

        //ColorDrawable drawable = new ColorDrawable(0xb0000000);
        ColorDrawable drawable = new ColorDrawable(0xb0ffffff);
        this.setBackgroundDrawable(drawable);
        this.setAnimationStyle(R.style.AnimFromBottom);//底部弹出
//        this.setTouchable(true);
        this.setOutsideTouchable(true);
        this.setFocusable(true);

        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);


        /**
         * 添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
         */
        getContentView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = getContentView().findViewById(R.id.ll_data_popup).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        /**
         * 拦截返回键，按下返回键关闭当前ppw
         */
        getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public void dismiss() {
        super.dismiss();
        setOutsideBg(1.0f);//消失后还原背景
    }


    /**
     * 设置ppw以外区域的背景
     *
     * @param alpha (0-1.0f)
     */
    private void setOutsideBg(float alpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = alpha;
        mActivity.getWindow().setAttributes(lp);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        setOutsideBg(0.5f);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        setOutsideBg(0.5f);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        setOutsideBg(0.5f);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setOutsideBg(0.5f);
    }


    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

}
