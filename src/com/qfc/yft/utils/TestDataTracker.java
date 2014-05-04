package com.qfc.yft.utils;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.qfc.yft.YftApplication;
import com.qfc.yft.net.HttpReceiver;

public class TestDataTracker {
	static final String TAG = "TestDataTracker";
	/*public static final String REQUEST_PATH_COMPANY_PRO ="open.api.product.findSeriesByShopIdForIphone"; //获取产品系列
	public static final String REQUEST_PATH_COMPANY_SUBPRO ="open.api.product.searchProductByShopIdAndSeriesIdForIphone"; //系列下所有产品
	public static final String REQUEST_PATH_RECOMMEND ="open.api.shop.searchShopForIphone"; //企业推荐
	public static final String REQUEST_PATH_SEARCH ="open.api.shop.searchShopForIphone"; //企业搜索接口
	public static final String REQUEST_PATH_CHECKVERSION ="cn.shop.getIOSVersionConfig"; //检查最新客户端版本
	public static final String REQUEST_PATH_LOGIN ="cn.member.sso.pointVerify"; //验证登录
	public static final String REQUEST_PATH_MEMBER_INFO ="cn.member.getMemberByUserCode";//获取用户信息
	public static final String REQUEST_PATH_COMPANY_INFO ="cn.shop.getShopAndCompanyById";//获取公司信息
//	public static final String REQUEST_PATH_SYNC = "cn.motion.basic.parseOffLineData";//同步 1113
	public static final String REQUEST_PATH_SYNC = "cn.motion.basic.parseOffLineDataForAndriod";//1122
	public static final String REQUEST_PATH_SERIESFORMOTION = "cn.product.series.getSeriesForMotion";//1128
	public static final String REQUEST_PATH_PRODUCTFORMOTION = "cn.product.getProductForMotion1";//1128*/
	
	public static String fetchDataString(String dataKey){
		String fetch = JackUtils.readFromFile(getContext()	, shortenFilename(dataKey));
		Log.i(TAG, fetch);
		return fetch;
	}
	
	private static String shortenFilename(String dataKey){
		try {
			return dataKey.substring(dataKey.charAt(dataKey.lastIndexOf(".")));
		} catch (Exception e) {
			return dataKey;
		}
	}
	private static Context getContext(){
		return YftApplication.getApp();
	}
	
	public static void simulateConnection(HttpReceiver receiver,String dataKey){
		try {
			receiver.response("{"+fetchDataString(dataKey));
		} catch (JSONException e) {
			Log.e(TAG, "simulateConnection json error");
			e.printStackTrace();
		}
	}
	
	public static  boolean settleDataString(String dataKey, String content){
		return JackUtils.writeToFile(getContext(), shortenFilename(dataKey), content);
	}
	
}
