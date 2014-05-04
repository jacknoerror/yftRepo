package com.qfc.yft.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.InetSocketAddress;
import java.net.Proxy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

import com.qfc.yft.YftValues;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.http.AndroidHttpClient;
import android.os.Environment;
import android.util.Log;

public class JackImageUtils {
	private static final String TAG ="JackImageUtils";
	private static final int IO_BUFFER_SIZE= 4 * 1024; 
//	private static final int getMaxLong() = 480;
	private static final String HTTP = "http";
	
	private static int getMaxLong(){
		return YftValues.SCREEN_WIDTH;
	}

	/**
		  * ��bitmap��С��maxlong���򱣳�ԭ��
		  * @param bitmap
		  * @param maxlong
		  * @return
		  */
		 public static Bitmap resizeBitmap(Bitmap bitmap, int maxlong){
			// ��ȡ���ͼƬ�Ŀ�͸� 
		       float width = bitmap.getWidth(); 
		       float height = bitmap.getHeight(); 
		       // ��������ͼƬ�õ�matrix���� 
		       Matrix matrix = new Matrix(); 
		       // ������������ 
		       float scaleWidth = ((float) maxlong) / width; 
		       float scaleHeight = ((float) maxlong) / height; 
		       float scale= scaleWidth<scaleHeight?scaleWidth:scaleHeight;
		       if(scale>1) return bitmap;
		       // ����ͼƬ���� 
		       matrix.postScale(scale, scale); 
		       Bitmap resulttBm = Bitmap.createBitmap(bitmap, 0, 0, (int) width, 
		                       (int) height, matrix, true); 
		       if(resulttBm==null) return bitmap;
	//	       Log.i(TAG, resulttBm.getWidth()+"?do u succeed?"+resulttBm.getHeight());
		       return resulttBm; 
		 }

	public static byte[] getDataStreamFromUrl(String url){

		 
		url = JackUtils.makeItUrl(url);
	    final AndroidHttpClient client =AndroidHttpClient.newInstance("Android"); 	
//	    HttpHost proxy = new HttpHost(YftValues.PROXY_HOST, 80, "http");//
//	    DefaultHttpClient httpClient = new DefaultHttpClient();//
//	    httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);//
//	    HttpHost target = new HttpHost(url,80,"https");//
//	    final HttpGet getRequest = new HttpGet("/");//
	    final HttpGet getRequest = new HttpGet(url); 
	    try {  
//	    	HttpResponse response =client.execute(target, getRequest);// 
	        HttpResponse response =client.execute(getRequest); 
	        final int statusCode =response.getStatusLine().getStatusCode(); 
	        if (statusCode !=HttpStatus.SC_OK) { 
	            Log.w(TAG, "��" +url + "�л�ȡͼƬ����ʱ����!,������:" + statusCode); 
	            return null; 
	        } 
	        final HttpEntity entity =response.getEntity(); 
	        if (entity != null) { 
	            InputStream inputStream =null; 
	            OutputStream outputStream =null; 
	            try{
	                inputStream =entity.getContent(); 
	                final ByteArrayOutputStream dataStream = new ByteArrayOutputStream(); 
	                outputStream = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE); 
	                copy(inputStream,outputStream); 
	                outputStream.flush(); 
	                byte[] data =dataStream.toByteArray(); 
	                return data;
	            }finally { 
	                if (inputStream !=null) { 
		                   inputStream.close(); 
		                } 
		                if (outputStream !=null) { 
		                   outputStream.close(); 
		                } 
		               entity.consumeContent(); 
		            } 
	        }
	        
	    } catch (IOException e) { 
	        getRequest.abort(); 
	        Log.w(TAG, "I/O errorwhile retrieving datastream from " + url, e); 
	    } catch (IllegalStateException e) { 
	        getRequest.abort(); 
	        Log.w(TAG, "Incorrect URL:" + url); 
	    } catch (Exception e) { 
	        getRequest.abort(); 
	        Log.w(TAG, "Error whileretrieving datastream from " + url, e); 
	    } finally { 
	        if (client != null) { 
	            client.close(); 
	        } 
	    } 
	
		return null;
	}
	
	public static Bitmap getBitmapFromUrl(String url){
			 
		  
                byte[] data =getDataStreamFromUrl(url);
                if(data==null) return null;//1218
	//                Log.i(TAG, data.length+":;:datalength+++++   "+url);
                BitmapFactory.Options options = new BitmapFactory.Options();
	                // 1.������ʵ�ͼƬ����ֵ���Լ��ٶ�JVM̫����ڴ�����
	//                options.inSampleSize = (int) 2;//TODO
	                // 2. inPurgeable �趨Ϊ true��������javaϵͳ, ���ڴ治��ʱ���л��ղ��ֵ��ڴ�
                options.inPurgeable = true;
	                // ��inPurgeable һ��ʹ��
                options.inInputShareable = true;
	                // 3. ���ٶ�Aphla ͨ�� 
                options.inPreferredConfig = Bitmap.Config.RGB_565;
	                
               Bitmap bitmap =BitmapFactory.decodeByteArray(data, 0,data.length,options); // Math.min(data.length, 10000));
               if(bitmap==null) return null;
	//                Log.i(TAG, bitmap.getWidth()+":size:"+bitmap.getHeight()+"|||lenth:"+data.length);
               if(bitmap.getWidth()>getMaxLong()||bitmap.getHeight()>getMaxLong()){
               	bitmap = resizeBitmap(bitmap,getMaxLong());
               }
	                
	                //bitmap.compress(CompressFormat.JPEG, 0, fos);
                SoftReference<Bitmap> softBM = new SoftReference<Bitmap>(bitmap);
                JackImageLoader.addBitmap(url, softBM);
	                /*if(bitmap!=null){
	                	//save
	                	storeInSD(bitmap, handler.getPath());
	                }*/
               bitmap =null;
               return softBM.get(); 
	            

	}
	public static void copy(InputStream in,OutputStream out) throws IOException{
		   int b;
		   while((b = in.read()) != -1) { 
			   out.write(b);
		   }
		}
	 /**
	 * storeInSD(bitmap, "/01/imgs/test.jpg");
	 * @param bitmap
	 * @param path
	 */
	public static void storeInSD(Bitmap bitmap, String path) {
			path = Environment.getExternalStorageDirectory().getPath() + path;
			Log.i(TAG+"_storeInSD", path+"::path__"+path.substring(0, path.lastIndexOf('/')+1)+"__||__"+path.substring(path.lastIndexOf('/')+1));
			File file = new File(path.substring(0, path.lastIndexOf('/')+1));
			if (!file.exists()) {
				file.mkdirs();
				System.out.println("here u go");
			}
			File imageFile = new File(file, path.substring(path.lastIndexOf('/')+1));
				try {
					imageFile.createNewFile();
					FileOutputStream fos = new FileOutputStream(imageFile);
					bitmap.compress(CompressFormat.JPEG, 100, fos);//
					fos.flush();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	public static void saveMyBitmap(String bitName,Bitmap mBitmap){//not in use
		  File f = new File(Environment.getExternalStorageDirectory().getPath() + bitName);
		  try {
		   f.createNewFile();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   Log.i(TAG+"_saveMyBitmap", "�ڱ���ͼƬʱ����"+e.toString());
		  }
		  FileOutputStream fOut = null;
		  try {
		   fOut = new FileOutputStream(f);
		  } catch (FileNotFoundException e) {
		   e.printStackTrace();
		  }
		  mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		  try {
		   fOut.flush();
		   fOut.close();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
	
	/**
	 * ����ָ����ͼ��·���ʹ�С����ȡ����ͼ
	 * �˷���������ô���
	 *     1. ʹ�ý�С���ڴ�ռ䣬��һ�λ�ȡ��bitmapʵ����Ϊnull��ֻ��Ϊ�˶�ȡ��Ⱥ͸߶ȣ�
	 *        �ڶ��ζ�ȡ��bitmap�Ǹ��ݱ���ѹ������ͼ�񣬵����ζ�ȡ��bitmap����Ҫ������ͼ��
	 *     2. ����ͼ����ԭͼ������û�����죬����ʹ����2.2�汾���¹���ThumbnailUtils��ʹ
	 *        ������������ɵ�ͼ�񲻻ᱻ���졣
	 * @param imagePath ͼ���·��
	 * @param width ָ�����ͼ��Ŀ��
	 * @param height ָ�����ͼ��ĸ߶�
	 * @return ���ɵ�����ͼ
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// ��ȡ���ͼƬ�Ŀ�͸ߣ�ע��˴���bitmapΪnull
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // ��Ϊ false
		// �������ű�
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// ���¶���ͼƬ����ȡ���ź��bitmap��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// ����ThumbnailUtils����������ͼ������Ҫָ��Ҫ�����ĸ�Bitmap����
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
}
