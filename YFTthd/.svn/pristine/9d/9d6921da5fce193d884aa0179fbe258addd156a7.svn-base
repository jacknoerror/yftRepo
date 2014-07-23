package com.qfc.yft.ui.offline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.qfc.yft.ui.offline.entity.IOfflineConst;
import com.qfc.yft.ui.offline.entity.OfflineData;
import com.qfc.yft.util.JackUtils;

public class OfflineUtil implements IOfflineConst{
final static String TAG = "offlineUtil";
	
	/**
	 * @param shopidStr
	 * @return as Param
	 * @throws JSONException
	 */
	public static String getEmptyOfflineString(String shopidStr) {
		JSONObject job = new JSONObject();
		try{
			JSONObject shopInfoJson = new JSONObject();
			shopInfoJson.put(OFF_SHOPBANNERPIC, new JSONObject());
			shopInfoJson.put(OFF_SHOPCERTARRAY, new JSONArray());
			shopInfoJson.put(OFF_SHOPID, shopidStr);//
			shopInfoJson.put(OFF_SHOPLOGOPIC,new JSONObject());
			shopInfoJson.put(OFF_SHOPPICSARRAY, new JSONArray());
			shopInfoJson.put(OFF_STATUS, "1");
			
			shopInfoJson.put(OFF_UPDATETIME,JackUtils.getDate());
		job.put(OFF_SHOPINFO, shopInfoJson);
			JSONObject downloadStatusJson = new JSONObject();
			downloadStatusJson.put(OFF_PERCENT, "0.00");
			downloadStatusJson.put(OFF_SIZE, "0.00");
			downloadStatusJson.put(OFF_STATUS, "-1");
		job.put(OFF_DOWNLOADSTATUS, downloadStatusJson);
		
		job.put(OFF_PRODUCTARRAY, new JSONArray());
		job.put(OFF_SERIESARRAY, new JSONArray());
		
		System.out.println(job.toString());
		}catch(JSONException e){
			Log.e(TAG, "getemptyofflineString json error");
		}
		return job.toString();
	}
	
	public static String getPathUponUrl(int shopId ,String url){
		final String UPLOAD = "upload/",TITLE = "/qfc/imgs";
		String path = "";
		if(shopId>0&&url!=null&&!url.isEmpty()&&url.contains(UPLOAD)){
//			path = TITLE+shopId+url.substring(url.indexOf(UPLOAD)+UPLOAD.length());
			String[] strs = url.substring(url.indexOf(UPLOAD)+UPLOAD.length()).split("/");
			if(strs.length>=3){
				path = String.format("%s/%d/%s/%s/%s", TITLE,shopId,strs[0],strs[1],strs[strs.length-1]);
			}
		}
		return path;
	}
	
	/**
	 * get OfflineData by JsonString in SharedPreference
	 * @return could be null
	 * @throws JSONException
	 */
	public static OfflineData getLocalOfflineData() throws JSONException{
		String dataString = OfflineDataKeeper.getWholeLocalData();
		if(dataString.isEmpty()) return null;
		OfflineData od = new OfflineData(new JSONObject(dataString));
		return od;
	}//每次获得，还是放入内存
	
	public static boolean needUpdate(OfflineData localOfflineData){
		return localOfflineData==null||localOfflineData.getDownloadStatus().getStatus()!=OFFSTATUS_COMMON_STATUS;
		
	}

	public static boolean needDownload(int stat){
		return stat==OFFSTATUS_NEED_DOWNLOAD||stat==OFFSTATUS_DOWNLOADING;
	}
	public static boolean needDelete(int stat){
		return stat==OFFSTATUS_NEED_DELETE;
	}
	
}
