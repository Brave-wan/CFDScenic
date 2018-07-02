package com.pubfin.tools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/5/10 0010.
 * 公用工具类
 */
public class Library_T {

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 验证邮箱格式
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private static ProgressDialog pd;

    /**
     * 显示等待框
     * @param context
     * @param msg 提示消息-
     */
    public static void showMyDialog(Context context, String msg)
    {
        pd = new ProgressDialog(context);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(msg);
        pd.show();
    }

    /**
     * 取消等待框
     */
    public static void disMyDialog()
    {
        try {
            if (pd != null)
                pd.dismiss();
            pd = null;
        }catch (Exception e)
        {}
    }



    /**
     *
     * TODO<图片圆角处理>
     * @throw
     * @return Bitmap
     * @param srcBitmap 源图片的bitmap
     * @param ret 圆角的度数
     */
    public static Bitmap getRoundImage(Bitmap srcBitmap, float ret) {

        if(null == srcBitmap){
            Log.e("", "the srcBitmap is null");
            return null;
        }

        int bitWidth = srcBitmap.getWidth();
        int bitHight = srcBitmap.getHeight();

        BitmapShader bitmapShader = new BitmapShader(srcBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        RectF rectf = new RectF(0, 0, bitWidth, bitHight);

        Bitmap outBitmap = Bitmap.createBitmap(bitWidth, bitHight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        canvas.drawRoundRect(rectf, ret, ret, paint);
        canvas.save();
        canvas.restore();
        return outBitmap;
    }



    /**
     * 获取时分
     * @param time
     * @param type 0:获取完整年月日时分秒 1：获取时分
     * @return
     */
    public static String getDateTimeStr(long time, int type)
    {
        String time_str = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(new Date(time * 1000));
        if (type == 0)
            time_str = str;
        else if (type == 1)
        {
            String[] times = str.split(" ")[1].split(":");
            time_str = times[0] + ":" + times[1];
        }
        return time_str;
    }

    /**
     * BITMAP转inputStream
     * @param bm
     * @param quality
     * @return
     */
    public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    /**
     * 时间戳转日期
     * @param time
     * @param format
     * @return
     */
    public static String getDateStr(long time, String format)
    {
        String time_str = "";
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        time_str=sdf.format(new Date(time));
        return time_str;
    }

    public static long getTime(String user_time, String format) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d;
        long l = 0;
        try {
            d = sdf.parse(user_time);
            l = d.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return l / 1000;
    }

    /**
     * 压缩图片
     * @param maxSize 指定大小
     * @param bitMap 图片
     */
    public static Bitmap imageZoom(double maxSize, Bitmap bitMap) {
        //图片允许最大空间   单位：KB
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length/1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                    bitMap.getHeight() / Math.sqrt(i));
        }
        return bitMap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

}
