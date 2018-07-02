package com.demo.view.photo;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTools {

	/**
	 * 保存图片到内存卡
	 * **/
	@SuppressLint("SdCardPath")
	public static boolean saveMyBitmap(Bitmap mBitmap, String bitName) {
		if (createsdJsonCardDir()) {
			File f = new File(sdCardDir + filename + bitName + ".jpg");
			FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			try {
				fOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}


	/**
	 * 保存图片到内存卡
	 * **/
	@SuppressLint("SdCardPath")
	public static String saveMyBitmap1(Bitmap mBitmap, String bitName) {
		if (createsdJsonCardDir()) {
			File f = new File(sdCardDir + filename + bitName + ".jpg");
			FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			try {
				fOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return f+"";
		}
		return "";
	}

	/**
	 * 　　* 保存文件 　　* @param toSaveString 　　* @param filePath 　　
	 */
	public static void saveFile(String toSaveString) 
	
{ 
		String filePath=Environment.getExternalStorageDirectory() + "/suizhenbao/Text/"  + "save.txt";
	try 
{ 
	File saveFile = new File(filePath); 
	if (!saveFile.exists()) 
	{ 
	File dir = new File(saveFile.getParent()); 
	dir.mkdirs(); 
	saveFile.createNewFile(); 
	   } 
	 
	  
	FileOutputStream outStream = new FileOutputStream(saveFile); 
	outStream.write(toSaveString.getBytes()); 
	outStream.close(); 
	} catch (Exception e) 
	{ 
	e.printStackTrace(); 
	} 
	 }
	/**
	 * 获取本地图片
	 * 
	 * **/
	public static Bitmap getbitmap(String bitmapname) {
		Bitmap bitmap = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File sdCardDir = Environment.getExternalStorageDirectory();
			String filepath = sdCardDir.getPath() + filename;
			// File f = new File( sdCardDir+filename+bitmapname + ".jpg");

			bitmap = BitmapFactory.decodeFile(filepath + bitmapname + ".jpg");

		}

		return bitmap;
	}

	/**
	 * 获取本地图片
	 *
	 * **/
	public static Bitmap getbitmap1(String bitmapname) {
		Bitmap bitmap = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
//			File sdCardDir = Environment.getExternalStorageDirectory();
//			String filepath = sdCardDir.getPath() + filename;
			// File f = new File( sdCardDir+filename+bitmapname + ".jpg");
			BitmapFactory.Options options = new BitmapFactory.Options();
//			options.inJustDecodeBounds = true;
//			options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
//			options.inPurgeable = true;
//			options.inInputShareable = true;
			options.inSampleSize=4;
			bitmap = BitmapUtil.compressImage(BitmapFactory.decodeFile(bitmapname,options));

		}

		return bitmap;
	}


	public static String filename = "/Higoutravel/bitmap/"; // 存放图片的文件夹
	public static File sdCardDir = Environment.getExternalStorageDirectory(); // 本地路径

	/**
	 * 创建路径
	 * **/

	public static boolean createsdJsonCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File sdCardDir = Environment.getExternalStorageDirectory();
			String filepath = sdCardDir.getPath() + filename;
			File file = new File(filepath);
			if (!file.exists())
				file.mkdirs();
			return true;
		} else
			return false;
	}

	/**
	 * uri路径转本地sd卡路径
	 * **/
	public static String getRealFilePath( final Context context, final Uri uri ) {
		if ( null == uri ) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if ( scheme == null )
			data = uri.getPath();
		else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
			data = uri.getPath();
		} else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
			Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
			if ( null != cursor ) {
				if ( cursor.moveToFirst() ) {
					int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
					if ( index > -1 ) {
						data = cursor.getString( index );
					}
				}
				cursor.close();
			}
		}
		return data;
	}



	/**
	 * 保存图片到内存卡
	 * **/
	@SuppressLint("SdCardPath")
	public static boolean saveBitmap(Bitmap mBitmap, String bitName) {
		if (createsdJsonCardDir()) {
			File f = new File(bitName+".jpg");
			FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			try {
				fOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}


}
