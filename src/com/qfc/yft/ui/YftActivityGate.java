package com.qfc.yft.ui;

import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.data.DataManager;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.listitem.LIIPeople;
import com.qfc.yft.entity.listitem.LIIProduct;
import com.qfc.yft.ui.current.CurrentPersonActivity;
import com.qfc.yft.ui.current.CurrentProductActivity;
import com.qfc.yft.ui.shop.ShopActivity;
import com.qfc.yft.ui.tabs.chat.ChatActivity;
import com.qfc.yft.utils.JackUtils;

import android.content.Context;
import android.content.Intent;


public class YftActivityGate {
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
		/*intent.putExtra(YftValues.EXTRAS_SHOP_ID, user.getShopId());
		intent.putExtra(YftValues.EXTRAS_MEMBER_TYPE, user.getMemberType());*/
		User user = new User();
		user.setShopId(shopId);
		user.setMemberType(memberType);
		if(shopId!=YftData.data().getMe().getShopId()){
			
			YftData.data().setCurrentUser(user);
		}else{
			YftData.data().setMeCurrentUser();//0404
		}
		
		intent.putExtra(YftValues.EXTRAS_SHOP_NAME, shopName);
		intent.putExtra(YftValues.EXTRAS_SHOP_MEMBER_TYPE, memberType);
		if(tabId>-1)intent.putExtra(YftValues.EXTRAS_SHOP_TAB,tabId);
		context.startActivity(intent);
	}
	/**
	 * 
	 * @param context
	 * @param prod
	 */
	public static void goProduct(Context context,LIIProduct prod) {
		if(null==prod) return;
		YftData.data().storeProduct(prod);
		Intent intent = new Intent();
		intent.setClass(context, CurrentProductActivity.class);
		intent.putExtra(YftValues.EXTRAS_PRODUCT_ID, prod.getProductId());
		context.startActivity(intent);
	}
	
	public static void goPeople(Context context,LIIPeople peop){
		if(null==peop) return;
		YftData.data().storePerson(peop);
		Intent intent = new Intent();
		intent.setClass(context, CurrentPersonActivity.class);
		intent.putExtra(YftValues.EXTRAS_ACCOUNT_ID, peop.accountId);
		context.startActivity(intent);
	}
	/**
	 * 
	 * @param context
	 * @param userName
	 * @param id
	 * @param type
	 * @param face
	 */
	public static void goChat(Context context,String userName, Long id, int type, String face){//taotao 0220
		if(null==id||id==0) {
			JackUtils.showToast(context, YftValues.HINT_NOCHAT);
			return;
		}
		Intent intent = new Intent();
		intent.setClass(context,
				ChatActivity.class);
		intent.putExtra("userName", userName);
		intent.putExtra("id", id);
		intent.putExtra("type", type);
		intent.putExtra("faceIndex", face);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Calling startActivity() from outside of an Activity
		context.startActivity(intent);
		DataManager.getInstance(context)//TODO 检查数据持久化是否到位
				.deleteContact(id);
		DataManager.getInstance(context)
				.addContact(id, type);
	}
}
