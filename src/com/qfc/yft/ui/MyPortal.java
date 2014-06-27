package com.qfc.yft.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import com.qfc.yft.data.Const;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.data.ParamConst;
import com.qfc.yft.ui.account.StartLoginActivity;
import com.qfc.yft.ui.common.CurProdActivity;
import com.qfc.yft.ui.common.CurrentPersonActivity;
import com.qfc.yft.ui.common.MyCollectionActivity;
import com.qfc.yft.ui.common.MyPeopleActivity;
import com.qfc.yft.ui.common.RecommendActivity;
import com.qfc.yft.ui.gallery.AlbumFragmentActivity;
import com.qfc.yft.ui.gallery.CreateAlbumActivity;
import com.qfc.yft.ui.shop.ShopActivity;
import com.qfc.yft.ui.tab.main.cat.CategoryAndSearchActivity;
import com.qfc.yft.ui.tab.work.ProductManageActivity;
import com.qfc.yft.vo.LIIPeople;
import com.qfc.yft.vo.LIIProduct;
import com.qfc.yft.vo.User;

/**
 * 
 * @author taotao
 * @Date 2014-6-23
 */
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
		if(null==product) return;
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
		Intent intent = new Intent();
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
		context.startActivity(intent);
	}

	public static void goCatnSrch(Context context, int i, String keyword) {
		Intent intent = new Intent();
		intent.putExtra(NetConst.EXTRAS_CATEPAGE, i);
		intent.putExtra(NetConst.EXTRAS_SEARCH_TYPE_INT, i-1);
		if(null!=keyword)intent.putExtra("keyword", keyword);
		intent.setClass(context, CategoryAndSearchActivity.class);
		context.startActivity(intent);
		
	}
	public static void goCatnSrch(Context context , int i ){
		goCatnSrch(context, i, null);
	}

	public static void goMyCollection(Context context) {
		justGo(context, MyCollectionActivity.class);
		
	}
	public static void goMyPeople(Context context) {
		justGo(context, MyPeopleActivity.class);
	}
	public static void goRecommend(Context context) {
		justGo(context, RecommendActivity.class);
	}
	
	public static void goPeople(Context context,LIIPeople peop){
		if(null==peop) return;
		MyData.data().storePerson(peop);
		Intent intent = new Intent();
		intent.setClass(context, CurrentPersonActivity.class);
		intent.putExtra(NetConst.EXTRAS_ACCOUNT_ID, peop.accountId);
		context.startActivity(intent);
	}
	public static void goStartLogin(Context context){
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(context, StartLoginActivity.class);
		context.startActivity(intent);
	}
}
