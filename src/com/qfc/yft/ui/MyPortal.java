package com.qfc.yft.ui;

import com.qfc.yft.data.ParamConst;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.gallery.AlbumFragmentActivity;
import com.qfc.yft.ui.gallery.AlbumListActivity;
import com.qfc.yft.ui.gallery.UploadActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

public class MyPortal {
	@SuppressWarnings("rawtypes")
	private static void justGo(Context context, Class clazz) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		context.startActivity(intent);
	}

	public static void goAlbumShList(Context context, int type) {
//		justGo(context, AlbumFragmentActivity.class);
		Intent intent = new Intent();
		intent.putExtra(NetConst.EXTRAS_ALBUMFIRSTTYPE, type);
		intent.setClass(context,  AlbumFragmentActivity.class);
		context.startActivity(intent);
	}

	/**
	 * @param context
	 */
	@Deprecated
	public static void goUploadPicByCamera(Context context) {
		if (null == context)
			return;
		Intent intent = new Intent();
		intent.setClass(context, UploadActivity.class);
		intent.putExtra(NetConst.EXTRAS_ALBUM_TYPE, ParamConst.AR_UP_PHOTO);
		context.startActivity(intent);
	}
	
	public static void goCamera(Activity activity){
		Intent i = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivityForResult(i,ParamConst.AR_UP_PHOTO);//
	}
}
