/**
 * 
 */
package com.qfc.yft;

import java.io.File;

import com.qfc.yft.data.Const;
import com.qfc.yft.ui.ImageLoaderHelper;


import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * @author taotao
 *
 */
public class MyApplication extends Application {
	
	
	
	private static MyApplication yApp;

	private void initScreenData(){
		DisplayMetrics dm = getResources().getDisplayMetrics();
		Const.SCREEN_WIDTH = dm.widthPixels;
		Const.SCREEN_HEIGHT= dm.heightPixels;
		Log.i("yft_app", dm.densityDpi+":dpi+=+desi:"+dm.density);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		yApp = this;
		initScreenData();
		ImageLoaderHelper.initImageLoader(this);//0610
	}

	public static MyApplication app() {
		return yApp;
	}
}
