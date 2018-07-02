package com.demo.view.photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;



import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {
    /**
     * 收缩图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
    }

    /**
     * 将bitmap转换成二进制数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 将二进制数组转换成图片
     *
     * @param bytes
     * @param opts  传NULL值即可
     * @return
     */
    public Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return compressImage(BitmapFactory.decodeByteArray(bytes, 0,
                        bytes.length, opts));
            else
                return compressImage(BitmapFactory.decodeByteArray(bytes, 0,
                        bytes.length, opts));
        return null;
    }

    /**
     * 将输入流转换成二进制数组
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;


    }

    /**
     * 按质量压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 图片旋转
     *
     * @param degree 旋转角度
     * @param bitmap 图片
     * @return
     */
    public Bitmap rotateBitmap(int degree, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bm;
    }

    /**
     * @param imgPath
     * @param bitmap
     * @param imgFormat 图片格式
     * @return
     */
    public String imgToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * @param base64Data
     * @param imgName
     * @param imgFormat  图片格式
     */
    @SuppressLint("SdCardPath")
    public void base64ToBitmap(String base64Data, String imgName) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        File myCaptureFile = new File(imgName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myCaptureFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean isTu = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        if (isTu) {
            // fos.notifyAll();
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理图片
     *
     * @param bm           所要转换的bitmap
     * @param newWidth新的宽
     * @param newHeight新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, float newWidth, float newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return toRoundBitmap(newbm);
    }


    /**
     * 处理图片  ,按大小压缩
     *
     * @param bm           所要转换的bitmap
     * @param newWidth新的宽
     * @param newHeight新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg1(Bitmap bm, float newWidth, float newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth=1;
        float scaleHeight=1;
        if(width>newWidth)
            // 计算缩放比例
            scaleWidth = newWidth / width;
        if(height>newHeight)
            scaleHeight = newHeight / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }


    /**
     * 处理图片  ,按大小压缩
     *
     * @param bm           所要转换的bitmap
     * @param newWidth新的宽
     * @param newHeight新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg2(Bitmap bm, float newWidth) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth=1;
        float scaleHeight=1;
        if(width>newWidth)
            // 计算缩放比例
            scaleWidth = newWidth / width;
//        if(height>newHeight)
            scaleHeight = scaleWidth;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }



    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    /**
     *      * 添加文字到图片，类似水印文字。      * @param gContext      * @param gResId      * @param
     * gText      * @return   
     */
    public static Bitmap drawTextToBitmap(Bitmap bitmaps, Context gContext,
                                          String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        // Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        Config bitmapConfig = bitmaps.getConfig();

        if (bitmapConfig == null) {
            bitmapConfig = Config.ARGB_8888;
        }

        bitmaps = bitmaps.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmaps);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.rgb(61, 61, 61));

        paint.setTextSize((int) (25.0f));

        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);

        int x = (bitmaps.getWidth() - bounds.width()) / 10 * 1;
        int y = (bitmaps.getHeight() + bounds.height()) / 10 * 9;
        canvas.drawText(gText, x, y, paint);
        return bitmaps;

    }


    /**
     * @param bitmap     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     * 边长为屏幕的三分之一
     */
//    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, Context context) {
//
//        int edgeLength = StaticData.getScreenW(context) / 3 - 1;
//        if (null == bitmap || edgeLength <= 0) {
//            return null;
//        }
//
//        Bitmap result = bitmap;
//        int widthOrg = bitmap.getWidth();
//        int heightOrg = bitmap.getHeight();
//
//        if (widthOrg > edgeLength && heightOrg > edgeLength) {
//            //压缩到一个最小长度是edgeLength的bitmap
//            int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
//            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
//            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
//            Bitmap scaledBitmap;
//
//            try {
//                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
//            } catch (Exception e) {
//                return null;
//            }
//
//            //从图中截取正中间的正方形部分。
//            int xTopLeft = (scaledWidth - edgeLength) / 2;
//            int yTopLeft = (scaledHeight - edgeLength) / 2;
//
//            try {
//                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
//                scaledBitmap.recycle();
//            } catch (Exception e) {
//                return null;
//            }
//        } else result = zoomImg1(bitmap, edgeLength, edgeLength);
//
//        return result;
//    }


//	图片水印的生成方法
//
//	　　生成水印的过程。其实分为三个环节：第一，载入原始图片;第二，载入水印图片;第三，保存新的图片。

    /**
     * 　　* create the bitmap from a byte array
     * <p/>
     * 　　*
     * <p/>
     * 　　* @param src the bitmap object you want proecss
     * <p/>
     * 　　* @param watermark the water mark above the src
     * <p/>
     * 　　* @return return a bitmap object ,if paramter's length is 0,return null
     * <p/>
     */

//    public static Bitmap createBitmap(Bitmap src,Context context)
//
//    {
//        BitmapFactory.Options options=new BitmapFactory.Options();
////        options.inSampleSize=4;
//        Bitmap watermark=BitmapFactory.decodeResource(context.getResources(), R.mipmap.shuiyin,options);
//        watermark= zoomImg2(watermark,watermark.getWidth()*src.getWidth()/StaticData.getScreenW(context));
//        String tag = "createBitmap";
//
//        Log.d(tag, "create a new bitmap");
//
//        if (src == null)
//
//        {
//
//        return null;
//
//    }
//
//        int w = src.getWidth();
//
//        int h = src.getHeight();
//
//        int ww = watermark.getWidth();
//
//        int wh = watermark.getHeight();
//
//        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);//创建一个新的和SRC长度宽度一样的位图
//
//        Canvas cv = new Canvas(newb);
//
//        //draw src into
//
//        cv.drawBitmap(src, 0, 0, null);//在 0，0坐标开始画入src
//
//        //draw watermark into
//
//        cv.drawBitmap(watermark, w - ww -10, h - wh - 10, null);//在src的右下角画入水印
//
//        //save all clip
//
//        cv.save(Canvas.ALL_SAVE_FLAG);//保存
//
//        //store
//
//        cv.restore();//存储
//        newb=compressImage(newb);
//
//        return newb;
//
//    }



    /**
     * A safer decodeStream method
     * rather than the one of {@link BitmapFactory}
     * which will be easy to get OutOfMemory Exception
     * while loading a big image file.
     *
     * @param uri
     * @param width
     * @param height
     * @return
     * @throws FileNotFoundException
     */
    protected Bitmap safeDecodeStream(Uri uri, int width, int height,Context context)
            throws FileNotFoundException{
        int scale = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        android.content.ContentResolver resolver = context.getContentResolver();

        if(width>0 || height>0){
            // Decode image size without loading all data into memory
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(
                    new BufferedInputStream(resolver.openInputStream(uri), 16*1024),
                    null,
                    options);

            int w = options.outWidth;
            int h = options.outHeight;
            while (true) {
                if ((width>0 && w/2 < width)
                        || (height>0 && h/2 < height)){
                    break;
                }
                w /= 2;
                h /= 2;
                scale *= 2;
            }
        }

        // Decode with inSampleSize option
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                new BufferedInputStream(resolver.openInputStream(uri), 16*1024),
                null,
                options);
    }

}
