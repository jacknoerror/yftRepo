package com.qfc.yft.ui.offline;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.qfc.yft.MyApplication;
import com.qfc.yft.data.MyData;
import com.qfc.yft.ui.offline.entity.OfflineData;
import com.qfc.yft.vo.User;

/**
 * remember to init before using;
 * @author taotao
 * @Date 2014-6-25
 */
public class OfflineDataKeeper {

	private static final String PREF_OFFLINE_BASE_ = ".offline";
	
	private static SharedPreferences offlinePref ;
	
	static Context appContext = MyApplication.app();
	static int shopId = 0;
	
	public static int init(int shopId){
		OfflineDataKeeper.shopId = shopId;
		offlinePref= appContext.getSharedPreferences(PREF_OFFLINE_BASE_+shopId, Context.MODE_PRIVATE);
		return 0;
	}
	
	/**
	 * @return empty string if never stored
	 */
	public static String getWholeLocalData(){
		if(null==offlinePref) throw   new IllegalStateException("keeper not inited");
		return offlinePref.getString("whole", "");
	}
	
	private static Editor offlineEditor;
	public static Editor getOffEditor()	{
		if(offlineEditor==null&&offlinePref!=null){
			offlineEditor = offlinePref.edit();
		}
		return offlineEditor;
	}
	public static boolean commitOffPref(OfflineData offlineData){
		if(isOffDatCommittable(offlineData))  return getOffEditor().putString("whole", offlineData.toJsonStr()).commit();
		return false;
	}
	private static boolean isOffDatCommittable(OfflineData offlineData){
		User meUser = MyData.data().getMe();
		return offlinePref!=null&&offlineData!=null&&meUser !=null&&meUser.getShopId()>0;
	}
	public static boolean clearOffPref(){
//		offlineData=null;
		return getOffEditor().clear().commit();
	}
	
	public static void setOfflineEnable(boolean enable){
		Editor editor = getOffEditor();
		if(editor!=null) editor.putBoolean("enable", enable).commit();
	}
	public static boolean isOfflineEnabled(){
		if(offlinePref!=null){
			return offlinePref.getBoolean("enable", false);
		}
		return false;
	}
	public static String getOffStr(String key){
		String result="";
		if(offlinePref!=null) result = offlinePref.getString(key, "");
		return result;
	}
}
