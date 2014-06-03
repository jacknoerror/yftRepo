package com.qfc.yft.net;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import android.util.Log;

import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.util.JackUtils;


public class NetStrategies implements NetConst{
	static final String TAG = "NetS";
	
	/**
	 * @param result
	 * @param params
	 * @return
	 */
	public static String doHttpRequest( String... params) throws UnknownHostException,SocketTimeoutException,IOException{
		return HttpRequestTask.doHttpRequest(params);
	}
	
	public static String getCurrentUserCode(){
		return MyData.data().getUserCode();
	}
	
	public static Map<String, String> getBasicParamMapInstance(String apiName,int paramAdd){
		Map<String, String> paramKV = new HashMap<String, String>();
		paramKV.put(URL_OPENAPI_APPKEY,_UF.getApp_key());
		paramKV.put(URL_OPENAPI_BUSICODE, apiName);
		paramKV.put(URL_OPENAPI_TIMESTAMP, JackUtils.getTimeStamp());//TODO 时区
		if(paramAdd>0){//
			paramKV.put(URL_OPENAPI_USERCODE, getCurrentUserCode());
		}
		return paramKV;
	}
	public static Map<String, String> getBasicParamMapInstance(String apiName){
		return getBasicParamMapInstance(apiName, 0);
	}
	
	public static String finishTheURL(Map<String, String> map){
		if(null==map)return "";//0418
		StringBuffer url,valid;
		url = new StringBuffer();
		valid = new StringBuffer();
		//排序
		String[] arrays = new String[]{};
		arrays = map.keySet().toArray(arrays);
		Arrays.sort(arrays);
		//验签
			for(String str : arrays){
				valid.append(str)
					.append(map.get(str));
					url.append(str)
						.append("=")
						.append(new String(map.get(str)))
						.append("&");
			}
		valid.append(_UF.getApp_secret());
		Log.i(TAG, "url::"+url);
		//拼接
		url.append(URL_OPENAPI_VALIDCODE).append("=").append(JackUtils.getMD5(valid.toString()));
		return url.toString();
	}
	
	
	
	
	
	/*public static void tryGetOfflineDataHere(HttpReceiver receiver){
   	 if(receiver==null)return;
   	 if(YftData.data().isMe()&&YftData.data().getMe()!=null){
   		 int shopId = YftData.data().getMe().getShopId();
   		 
	 		//try local
	 		String param = YftValues.getOffdataAsParam(shopId);
//	 		JackUtils.writeToSomeWhere(YftApplication.getApp(), param);
	 		//do network
	 		if(!param.isEmpty())JackRequestManager.getInstance().tryRequest(YftValues.getHTTPBodyString(RequestType.SYNC, param), receiver, 3000);
   	 }else{
   		 Log.e(TAG, "try offline data failed");
   	 }
	}*/
	/*public static String getOffdataAsParam(int shopId){
   	 JsonOffline od = YftData.data().getOfflineData();
			String param="";
			try {
				if(od==null){
						param = OfflineUtill.initOfflineJsonStr(shopId+"");
				}else{
					param = od.toJsonStr();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return param;
    }*/
	
	/*public static String getMyLocalPath(){
		User user = YftData.data().getMe();
		if(user==null) return "00";
		int shid = user.getShopId();
		return Environment.getExternalStorageDirectory().getPath()+"/qfc/imgs/"+shid;
	}*/

	
	/*public static void logout(){
		try{
			MyApplication.getApp().unregisterReceiver(MyApplication.getApp().getUIBadgeReceiver());//TODO tobe test
			MyApplication.getApp().stopService(new Intent(MyApplication.getApp(), GIMSocketServer.class));
			if(!OfflineDownloadBuilder.shouldStart()){
				OfflineDownloadBuilder.cancel();//0421
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		CachMsg.getInstance().clear();//0319
		YftData.data().destroy();
		//fzl
		SystemParams.getInstance().clear();
		CachMsg.getInstance().clear();
	}*/


	//	private static NotificationManager nm;
	public static int notification_id = 19172439;
	
	/**
	 * ugly ,temp
	 */
	/*public static  boolean isopen(Context context) {
		if(!JackUtils.isOpenNetwork()){
				OfflineData offlineData = YftData.data().getOfflineData();
				if(offlineData!=null){
					JackUtils.showToast(context, "请检查您的网络或切换到离线模式");
				}else{
					JackUtils.showToast(context, "请检查您的网络");
				}
				return false;
			}
		return true;
	}*/
}
