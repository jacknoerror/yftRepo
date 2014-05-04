package com.qfc.yft.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class BitmapUtil {

	// public static int readPictureDegree(String path) {
	// int degree = 0;
	// try {
	// ExifInterface exifInterface = new ExifInterface(path);
	// int orientation = exifInterface.getAttributeInt(
	// ExifInterface.TAG_ORIENTATION,
	// ExifInterface.ORIENTATION_NORMAL);
	// switch (orientation) {
	// case ExifInterface.ORIENTATION_ROTATE_90:
	// degree = 90;
	// break;
	// case ExifInterface.ORIENTATION_ROTATE_180:
	// degree = 180;
	// break;
	// case ExifInterface.ORIENTATION_ROTATE_270:
	// degree = 270;
	// break;
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return degree;
	// }

	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// ��תͼƬ ����
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// �����µ�ͼƬ
		
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * ��ȡ������Դ��ͼƬ
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap ReadBitmapById(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// ��ȡ��ԴͼƬ
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/***
	 * ������Դ�ļ���ȡBitmap
	 * 
	 * @param context
	 * @param drawableId
	 * @return
	 */
	public static Bitmap ReadBitmapById(Context context, int drawableId,
			int screenWidth, int screenHight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.ARGB_8888;
		options.inInputShareable = true;
		options.inPurgeable = true;
		InputStream stream = context.getResources().openRawResource(drawableId);
		Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
		return getBitmap(bitmap, screenWidth, screenHight);
	}

	public static Bitmap ReadBitmapPath(Context context, String path,
			int screenWidth, int screenHight) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Config.ARGB_8888;
			options.inInputShareable = true;
			options.inPurgeable = true;
			bitmap = BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}
		if (bitmap == null) {
			return null;
		} else {
			return getBitmap(bitmap, screenWidth, screenHight);
		}
	}

	/***
	 * �ȱ���ѹ��ͼƬ
	 * 
	 * @param bitmap
	 * @param screenWidth
	 * @param screenHight
	 * @return
	 */
	public static Bitmap getBitmap(Bitmap bitmap, int screenWidth,
			int screenHight) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Log.e("jj", "ͼƬ���" + w + ",screenWidth=" + screenWidth);
		Matrix matrix = new Matrix();
		float scale = (float) screenWidth / w;
		float scale2 = (float) screenHight / h;

		// scale = scale < scale2 ? scale : scale2;

		// ��֤ͼƬ������.
		matrix.postScale(scale, scale);
		// w,h��ԭͼ������.
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	}

	/***
	 * ����ͼƬ��SD��
	 * 
	 * @param bm
	 * @param url
	 * @param quantity
	 */
	private static int FREE_SD_SPACE_NEEDED_TO_CACHE = 1;
	private static int MB = 1024 * 1024;
	public final static String DIR = "/sdcard/hypers";

	public static void saveBmpToSd(Bitmap bm, String url, int quantity) {
		// �ж�sdcard�ϵĿռ�
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			return;
		}
		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()))
			return;
		String filename = url;
		// Ŀ¼�����ھʹ���
		File dirPath = new File(DIR);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		File file = new File(DIR + "/" + filename);
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.PNG, quantity, outStream);
			outStream.flush();
			outStream.close();

		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/***
	 * ��ȡSD��ͼƬ
	 * 
	 * @param url
	 * @param quantity
	 * @return
	 */
	public static Bitmap GetBitmap(String url, int quantity) {
		InputStream inputStream = null;
		String filename = "";
		Bitmap map = null;
		URL url_Image = null;
		String LOCALURL = "";
		if (url == null)
			return null;
		try {
			filename = url;
		} catch (Exception err) {
		}

		LOCALURL = URLEncoder.encode(filename);
		if (Exist(DIR + "/" + LOCALURL)) {
			map = BitmapFactory.decodeFile(DIR + "/" + LOCALURL);
		} else {
			try {
				url_Image = new URL(url);
				inputStream = url_Image.openStream();
				map = BitmapFactory.decodeStream(inputStream);
				// url = URLEncoder.encode(url, "UTF-8");
				if (map != null) {
					saveBmpToSd(map, LOCALURL, quantity);
				}
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return map;
	}

	/***
	 * �ж�ͼƬ�Ǵ���
	 * 
	 * @param url
	 * @return
	 */
	public static boolean Exist(String url) {
		File file = new File(DIR + url);
		return file.exists();
	}

	/** * ����sdcard�ϵ�ʣ��ռ� * @return */
	private static int freeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize()) / MB;

		return (int) sdFreeMB;
	}

	/**
	 * �洢����ͼƬ��SD��
	 */
	public static void saveImage(Bitmap bitmap, String filepath, String fileName) {
		try {
			int bitmapWidth = bitmap.getWidth();
			int bitmapHeight = bitmap.getHeight();
			// ����ͼƬ�ĳߴ�
			int zoomWidth = 600;
			float scale = (float) zoomWidth / bitmapWidth;
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			// �������ź��Bitmap����
			Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0,
					bitmapWidth, bitmapHeight, matrix, false);
			// save file
			File myCaptureFile = new File(filepath + "small" + fileName);
			FileOutputStream out = new FileOutputStream(myCaptureFile);
			if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)) {
				out.flush();
				out.close();
			}
			if (!bitmap.isRecycled()) {
				bitmap.recycle();// �ǵ��ͷ���Դ��������ڴ����
			}
			if (!resizeBitmap.isRecycled()) {
				resizeBitmap.recycle();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}

	}

}
