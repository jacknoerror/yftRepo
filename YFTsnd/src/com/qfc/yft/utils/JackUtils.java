package com.qfc.yft.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

public class JackUtils {
	public static void showToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	public static ProgressDialog showProgressDialog(Context context,String text){
		return ProgressDialog.show(context, "", text);
	}
	final static String HTTP = "http";
	public static String makeItUrl(String str){
		if(!str.startsWith(HTTP)){
			if(str.contains(HTTP)){
				str = str.substring(str.indexOf(HTTP)).replace("\"", "");//1202
			}else{
				str = "";
				Log.e("jackUtil", "url illegal:"+str);
			}
		}
		return str;
	}
	

	 /** 
	      * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
	      */  
	     public static int dip2px(Context context, float dpValue) {  
	         final float scale = context.getResources().getDisplayMetrics().density;  
	         return (int) (dpValue * scale + 0.5f);  
	     }  
	   
	     /** 
	      * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
	      */  
	     public static int px2dip(Context context, float pxValue) {  
	         final float scale = context.getResources().getDisplayMetrics().density;  
	         return (int) (pxValue / scale + 0.5f);  
	     }  

	     /**
	      * 从Assets中读取图片
	      */
	     public static Bitmap getbmFromAssetsFile(Resources res , String fileName)
	     {
	     	if(res==null) return null;
	         Bitmap bm = null;
	         AssetManager am = res.getAssets();
	         try
	         {
	             InputStream is = am.open(fileName);
	             bm = BitmapFactory.decodeStream(is);
	             is.close();
	         }
	         catch (IOException e)
	         {
	             e.printStackTrace();
	         }

	         return bm;

	     }
	
	     
      public static String getDate(){
    	  return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
      }
      
      /**
       * write string to /data
       * @param context
       * @param str
       * @return
       */
      public static boolean writeToSomeWhere(Context context , String str) {
          try { 
              OutputStream out=context.openFileOutput("fromSomewhere.xml", Context.MODE_PRIVATE); 
              OutputStreamWriter outw=new OutputStreamWriter(out); 
              try { 
                  outw.write(str); 
                  outw.close(); 
                  out.close(); 
                  return true; 
              } catch (IOException e) { 
                  // TODO Auto-generated catch block 
                  return false; 
              } 
          } catch (FileNotFoundException e) { 
              // TODO Auto-generated catch block 
              return false; 
          } 
	}
      /**
       * add SD-pre automately
       * @param path
       * @return
       */
      public static boolean deleteFile(String path){
    	  if(!path.startsWith(getSDPre())) path = getSDPre()+path;
    	  return deleteFile(new File(path));
      }
      //将SD卡文件删除
      public static boolean  deleteFile(File file)
      {
    	   
       if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
       {
	        if (file.exists())
	        {
		         if (file.isFile())
		         {
		        	 return file.delete();//1210
		         }
		         // 如果它是一个目录
		         else if (file.isDirectory())
		         {
		          // 声明目录下所有的文件 files[];
			          File files[] = file.listFiles();
			          for (int i = 0; i < files.length; i++)
			          { // 遍历目录下所有的文件
			           deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
			          }
		         }
	         return file.delete();
	        }else
	        {//已经不存在
	       	 return true; //1210 taotao
	        }
       }
       return false;
      }  
      
      public static AlertDialog  showDialog(Context context, String hintContent,DialogInterface.OnClickListener positiveListener ){
    	  AlertDialog.Builder builder = new Builder(context);
		  builder.setMessage(hintContent);

		  builder.setTitle("提示");

		  builder.setPositiveButton("确认", positiveListener);
		  
		  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		   }
		  });
		  
		  return builder.show();
      }
      
      /**
       * 
       * @param path   "/qfc/01/we.jpg"
       * @return
       */
      public static Bitmap getBitmapFromSDCard(String filePath){
    	  /**
    	      *图片文件路径
    	 *打印Environment.getExternalStorageDirectory()得到："/mnt/sdcard"，即找到了sd卡的根目录
    	      */
    	 if(filePath==null||!filePath.startsWith("/mnt/sdcard"))filePath=Environment.getExternalStorageDirectory()+filePath;
    	     File mfile=new File(filePath);
    	     if (mfile.exists()) {//若该文件存在
    	     Bitmap bm = BitmapFactory.decodeFile(filePath);
    	     Log.i("SD", "exists");
    	     return bm;
    	     }
    	  return null;
      }
      
      public static void textpaint_bold(TextView tv) {
  		TextPaint tp = tv.getPaint();tp.setFakeBoldText(true);//加粗
  	}
     public static void textpaint_underline(TextView tv){
    	 TextPaint tp = tv.getPaint();tp.setUnderlineText(true);
     }
     
     public static String getSDPre(){
    	 return Environment.getExternalStorageDirectory().getPath();
     }
     
     final static long durationMillis = 800,delayMillis=200;
     public static void slideview(final View view , final float p1, final float p2) {//1231
 		if(null==view) return;
 	     
 		 TranslateAnimation animation = new TranslateAnimation(0, 0, p1, p2);
 	     //添加了这行代码的作用时，view移动的时候 会有弹性效果
 	     animation.setInterpolator(new DecelerateInterpolator());
 	     animation.setDuration(durationMillis);
 	     animation.setStartOffset(delayMillis);
 	     animation.setAnimationListener(new Animation.AnimationListener() {
 	         @Override
 	         public void onAnimationStart(Animation animation) {
 	         }
 	         
 	         @Override
 	         public void onAnimationRepeat(Animation animation) {
 	         }
 	         
 	         @Override
 	         public void onAnimationEnd(Animation animation) {
 	             int left = view.getLeft();
 	             int top = view.getTop()+(int)(p2-p1);
 	             int width = view.getWidth();
 	             int height = view.getHeight();
 	            view.clearAnimation();
 	           view.layout(left, top, left+width, top+height);
 	         }
 	     });
 	    view.startAnimation(animation);
 	 }
}
