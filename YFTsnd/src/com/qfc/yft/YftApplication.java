package com.qfc.yft;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;



public class YftApplication extends Application {
	private static YftApplication yApp;
	
	public static YftApplication getApp(){
		return yApp;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		yApp = this;
		initScreenData();
	}
	
	private void initScreenData(){
		DisplayMetrics dm = getResources().getDisplayMetrics();
		YftValues.SCREEN_WIDTH = dm.widthPixels;
		YftValues.SCREEN_HEIGHT= dm.heightPixels;
		Log.i("yft_app", dm.densityDpi+":dpi+=+desi:"+dm.density);
	}
	
}
