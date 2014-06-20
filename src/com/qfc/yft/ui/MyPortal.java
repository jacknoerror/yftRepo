package com.qfc.yft.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import com.qfc.yft.data.Const;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.data.ParamConst;
import com.qfc.yft.ui.common.CurProdActivity;
import com.qfc.yft.ui.gallery.AlbumFragmentActivity;
import com.qfc.yft.ui.gallery.CreateAlbumActivity;
import com.qfc.yft.ui.tab.work.ProductManageActivity;
import com.qfc.yft.vo.LIIProduct;
import com.qfc.yft.vo.User;

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

	
	public static void goCamera(Activity activity){
		Intent i = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivityForResult(i,ParamConst.AR_UP_PHOTO);//
	}
	
	/**
	 * @param activity  
	 * 
	 */
	public static void goCreateAlbum(Activity activity) {
		Intent intent = new Intent();
		intent.setClass(activity, CreateAlbumActivity.class);
		activity.startActivityForResult(intent, Const.AR_UP_CREATE);
	}

	public static void goProductManage(Context context) {
		justGo(context, ProductManageActivity.class);
		
	}

	public static void goProduct(Context context,  			LIIProduct product) {
		Intent intent = new Intent();
		intent.putExtra(NetConst.EXTRAS_PRODUCT_ID, product.getProductId());
		intent.setClass(context, CurProdActivity.class);
		context.startActivity(intent);
	}
	
	/**
	 * 
	 * @param context
	 * @param shopId 
	 * @param shopName
	 * @param memberType 
	 */
	public static void goShop(Context context,int shopId,String shopName, int memberType){
		goShop(context, shopId, shopName, memberType, -1);
	}
	/**
	 * 
	 * @param context
	 * @param shopId 
	 * @param shopName
	 * @param memberType 
	 * @param tabId 
	 */
	public static void goShop(Context context,int shopId,String shopName, int memberType, int tabId){
		/*Intent intent = new Intent();
		intent.setClass(context, ShopActivity.class);
		MyData mData = MyData.data();
		User user = new User();
		user.setShopId(shopId);
		user.setMemberType(memberType);
		if(shopId!=mData.getMe().getShopId()){
			
			mData.setCurrentUser(user);
		}else{
			mData.setMeCurrentUser();//0404
		}
		
		intent.putExtra(NetConst.EXTRAS_SHOP_NAME, shopName);
		intent.putExtra(NetConst.EXTRAS_SHOP_MEMBER_TYPE, memberType);
		if(tabId>-1)intent.putExtra(NetConst.EXTRAS_SHOP_TAB,tabId);
		context.startActivity(intent);*/
	}
}
