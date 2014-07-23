package com.qfc.yft.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.util.Log;

import com.qfc.yft.MyApplication;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.data.chat.CachMsg;
import com.qfc.yft.net.chat.GIMSocketServer;
import com.qfc.yft.ui.account.ChatLoginHelper;
import com.qfc.yft.ui.offline.OfflineDownloadBuilder;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.chat.SystemParams;


public class NetStrategies implements NetConst{
	static final String TAG = "NetS";
	
	/**
	 * only use in test now.
	 * @param result
	 * @param params
	 * @return
	 */
	public static String doHttpRequest( String... params) throws UnknownHostException,SocketTimeoutException,IOException{
		return HttpRequestTask.doHttpRequest(_UF.getUrl(), params[0]);
	}
	
	public static String getCurrentUserCode(){
		return MyData.data().getUserCode();
	}
	public static String getSessionKey(){
		return MyData.data().getSessionId();
	}
	
	public static Map<String, String> getBasicParamMapInstance(String apiName,int paramAdd){
		Map<String, String> paramKV = new HashMap<String, String>();
		paramKV.put(URL_OPENAPI_APPKEY,_UF.getApp_key());
		paramKV.put(URL_OPENAPI_BUSICODE, apiName);
		paramKV.put(URL_OPENAPI_TIMESTAMP, JackUtils.getTimeStamp());//TODO 时区
		if(paramAdd>0){//
			paramKV.put(URL_OPENAPI_USERCODE, getCurrentUserCode());
		}
		if(paramAdd>1&&!getSessionKey().isEmpty()){
			paramKV.put(URL_OPENAPI_SESSIONKEY, getSessionKey());
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
	
	/**
	 * 提交数据到服务器
	 * @param path 上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.itcast.cn或http://192.168.1.10:8080这样的路径测试)
	 * @param params 请求参数 key为参数名,value为参数值
	 * @param encode 编码
	 */
	public static byte[] postFromHttpClient(String path, Map<String, String> params, String encode) throws Exception{
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();//用于存放请求参数
		for(Map.Entry<String, String> entry : params.entrySet()){
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, encode);
		HttpPost httppost = new HttpPost(path);
		httppost.setEntity(entity);
		HttpClient httpclient = new DefaultHttpClient();//看作是浏览器
		HttpResponse response = httpclient.execute(httppost);//发送post请求		
		return readStream(response.getEntity().getContent());
	}
	
	/**
	 * 读取流
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while( (len=inStream.read(buffer)) != -1){
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
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

	
	public static void logout(){
		try{
//			MyApplication.getApp().unregisterReceiver(MyApplication.getApp().getUIBadgeReceiver());//TODO tobe test
			ChatLoginHelper.getInstance().unregisterReceiver();
			MyApplication.app().stopService(new Intent(MyApplication.app(), GIMSocketServer.class));
			OfflineDownloadBuilder.getInstance().cancel();
		}catch(Exception e){
			e.printStackTrace();
		}
		CachMsg.getInstance().clear();//0319
		MyData.data().destroy();
		//fzl
		//TODO  纺织聊登出
		SystemParams.getInstance().clear();
		CachMsg.getInstance().clear();
	}


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
