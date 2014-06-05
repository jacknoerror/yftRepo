package com.qfc.yft.ui;

import com.qfc.yft.ui.gallery.AlbumListShActivity;

import android.content.Context;
import android.content.Intent;




public class MyPortal {
	@SuppressWarnings("rawtypes")
	private static void justGo(Context context, Class clazz) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		context.startActivity(intent);
	}
	
	public static void goAlbumShList(Context context){
		justGo(context, AlbumListShActivity.class);
	}
	
}
