package com.qfc.yft.utils;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.qfc.yft.YftApplication;
import com.qfc.yft.net.HttpReceiver;

public class TestDataTracker {
	static final String TAG = "TestDataTracker";
	/*public static final String REQUEST_PATH_COMPANY_PRO ="open.api.product.findSeriesByShopIdForIphone"; //��ȡ��Ʒϵ��
	public static final String REQUEST_PATH_COMPANY_SUBPRO ="open.api.product.searchProductByShopIdAndSeriesIdForIphone"; //ϵ�������в�Ʒ
	public static final String REQUEST_PATH_RECOMMEND ="open.api.shop.searchShopForIphone"; //��ҵ�Ƽ�
	public static final String REQUEST_PATH_SEARCH ="open.api.shop.searchShopForIphone"; //��ҵ�����ӿ�
	public static final String REQUEST_PATH_CHECKVERSION ="cn.shop.getIOSVersionConfig"; //������¿ͻ��˰汾
	public static final String REQUEST_PATH_LOGIN ="cn.member.sso.pointVerify"; //��֤��¼
	public static final String REQUEST_PATH_MEMBER_INFO ="cn.member.getMemberByUserCode";//��ȡ�û���Ϣ
	public static final String REQUEST_PATH_COMPANY_INFO ="cn.shop.getShopAndCompanyById";//��ȡ��˾��Ϣ
//	public static final String REQUEST_PATH_SYNC = "cn.motion.basic.parseOffLineData";//ͬ�� 1113
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
