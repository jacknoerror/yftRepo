package com.qfc.yft.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.qfc.yft.YftApplication;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.offline.IOfflineConst;
import com.qfc.yft.entity.offline.OffImage;
import com.qfc.yft.entity.offline.OffProduct;
import com.qfc.yft.entity.offline.OffSeries;
import com.qfc.yft.entity.offline.OffShopInfo;
import com.qfc.yft.entity.offline.OfflineData;
import com.qfc.yft.ui.tabs.custom.OfflineReceiver;
import com.qfc.yft.utils.JackImageUtils;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.utils.OfflineUtill;

/**
 * @author taotao
 *
 */
public class OfflineDownloadBuilder extends AsyncTask<OfflineData, Double, Void> implements IOfflineConst{
	private static final String OFFLINETASK_STOP_OR_NOT = "离线数据正在下载，确认停止？";

	static final String TAG  = OfflineDownloadBuilder.class.getSimpleName();
	
	double percentInOne,PercentNowHas;
	OfflineData data;
	int oShopId;
	int oError;
	String prefSI="",prefSRS="";
	JSONObject prefPRDjo = new JSONObject();
	
	static Context mContext;
	static ProgressBar mProgress;
	static ImageView btn_update;
	public static void cancel() throws InterruptedException, ExecutionException{
		if(null!=builder){
			
			builder.cancel(true);
//			builder.get();
			showCancelWaitingDialog();//0421
			
			builder.mProgress.setVisibility(View.GONE);
			builder.btn_update.setVisibility(View.VISIBLE);
//			clear();//0421
		}else{
			Log.e(TAG, "no builder to cancel");
		}
	}
	public static void setBtns(ProgressBar pb , ImageView ib){
		mProgress = pb;
		btn_update = ib;
		if(builder!=null&&builder.getStatus()!=Status.FINISHED){
			updateUIwhenStarts();
		}
	}
	public static void setContext(Context context){
		mContext = context;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		updateUIwhenStarts();
	}
	@Override
	protected Void doInBackground(OfflineData... params) {
		data = params[0];
		if(data==null) return null;
		//calculate size
		int size = data.getSize();
		PercentNowHas = data.getDownloadStatus().getPercent();
		publishProgress(PercentNowHas);
		percentInOne = (1-PercentNowHas)/size;
		
		//do downloading
		handleThisShit();
		
		
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Double... values) {
		super.onProgressUpdate(values);
		PercentNowHas+=values[0];
		Log.i(TAG, PercentNowHas+"::::::::::::::::PROGRESS");
//		JackUtils.showToast(NavPersonalActivity.this, ((double)((int)(PercentNowHas*100))/100)+":progress");
		if(mProgress!=null)mProgress.setProgress((int)Math.round(PercentNowHas*100));//1129
		if(values[0]>0)countForDebug++;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if(mProgress!=null){
			mProgress.setVisibility(View.GONE );
			JackUtils.showToast(mContext, "离线数据下载完成："+mProgress.getProgress()+"%");
		}
		if(btn_update!=null)btn_update.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
//		checkCancel();
		Log.i(TAG, "oncancel");
	}
	
	/**
	 * 
	 */
	private boolean checkCancel() {
		if(isCancelled())	{
			
			if(data!=null) data.getDownloadStatus().setPercent(PercentNowHas);
			clear();//0421
			YftData.data().commitOffPref();
			dismissCancelWaitingDialog();
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 */
	private void handleThisShit(){
		int sec=oError;
		//shopinfo
		OffShopInfo osInfo = data.getShopInfo();
		oShopId = osInfo.getShopId();
		switch (osInfo.getStatus()) {
		case OFFSTATUS_COMMON_STATUS:
			break;
		case OFFSTATUS_NEED_DOWNLOAD:case OFFSTATUS_DOWNLOADING:case OFFSTATUS_NEED_DELETE:
			downloadShopInfo(osInfo);
			break;
		
		default:
			break;
		}
		//series
		if(checkCancel()){return;}
		JSONArray osArr = data.getSeriesArray();
		downloadArrSrs(osArr);
		//product
		if(checkCancel()){return;}
		JSONArray opArr = data.getProductArray();
		downloadArrPrd(opArr);
		if(checkCancel()){return;}
		if(oError-sec==0) data.getDownloadStatus().setStatus(OFFSTATUS_COMMON_STATUS);
		JackUtils.writeToSomeWhere(YftApplication.app(), data.toJsonStr());
		finishCommit();
	}
	
	private void finishCommit() {
		SharedPreferences pref = YftData.data().getOffPref();
		if(pref!=null){//TODO
			Editor fcEditor= YftData.data().getOffEditor();
			if(!prefSI.isEmpty())//1206
				fcEditor	.putString(PREFOFF_SHOPINFO, prefSI);
			if(!prefSRS.isEmpty())fcEditor	.putString(PREFOFF_SERIES, prefSRS);
			if(prefPRDjo.length()>0)fcEditor	.putString(PREFOFF_PRODUCTS, prefPRDjo.toString());
			YftData.data().getOfflineData().setDownloadStatus(data.getDownloadStatus());//0417 why not set a whole
			YftData.data().commitOffPref();
			YftData.data().putSeriesList(YftData.data().getMe().getShopId(),null);//0303 下载好后清楚内存 以方便那啥 //0305 time
		}
		if(oError==0){
//		
		}else{
			Log.e(TAG, "更新过程中有错误");
			//TODO
		}
		Log.i(TAG, countForDebug+"::count__pio::"+percentInOne+"---error:"+oError);
	}

	private int ifYouHaveToGetShopIdStraightly(){
		User user = YftData.data().getMe();
		if(user!=null) return user.getShopId();
		return 0;
	}
	private boolean isValidResponse(String response){ 
		return !response.isEmpty()&&response.contains(YftValues.RESULT_OBJECT);
	}
	private int downloadArrSrs(JSONArray array){//down nothing,load seriesInfo, save to pref
		int sec = oError;
		int shopId = ifYouHaveToGetShopIdStraightly();
		if(shopId>0) {//用户正确
			String response = doHttpRequest(YftValues.getHTTPBodyString(RequestType.SERIES_INFO, shopId+""));
			if(isValidResponse(response)){//
				prefSRS= response;
				for(int i=0;i<array.length();i++){
					try {
						OffSeries os = new OffSeries(array.getJSONObject(i));
						os.setStatus(OFFSTATUS_COMMON_STATUS);
						array.put(i, os.toJsonObj());
						publishProgress(percentInOne);
//						String sResult=doHttpRequest(YftValues.getHTTPBodyString(RequestType.SM, os.getSeriesId()+""));
//						Log.i(TAG, sResult);
					} catch (JSONException e) {
						countError();
						e.printStackTrace();
						continue;
					}
				}
				return oError-sec;
			}
		}
		countError();//because it's wrong to be here
		return oError-sec;
	} 
	private void addProdJobToPrefPrdsStr(JSONObject productJob,OffProduct op) throws JSONException{
		final String PSKey= "productSeries",
				IMGStrs = "imageString",
				IMGMain = "mainPic",
				IMG300= IOfflineConst.OFF_PRODUCT_X800_IMAGE,IMG800= IOfflineConst.OFF_PRODUCT_X800_IMAGE;
		Log.i("NOW", productJob.getString(PSKey)+"))))))))line:248");
		for(String sid:productJob.getString(PSKey).split(",")){
			if(sid.isEmpty()) continue;
			JSONArray jArr = getJarBySid(sid);
			try{
				productJob.put(IMGMain, op.getProductImage().getImgUrl());
				productJob.put(IMG300, op.getProduct300XImage().getImgUrl());//0417
				productJob.put(IMG800, op.getProductX800Image().getImgUrl());//0417
				productJob.put(IMGStrs, getArrFromOIArr(op.getProductPicsArray()));
				jArr.put(productJob);
				prefPRDjo.put(sid, jArr);//要
			}catch(Exception e){
				e.printStackTrace();
				continue;//0521
			}
		}
		
	}
	private JSONArray getArrFromOIArr(JSONArray arr){//1202
		JSONArray result = new JSONArray();
		for(int i=0;i<arr.length();i++){
			try {
				result.put(arr.getJSONObject(i).getString("imageUrl"));
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
		}
		return result;
	}
	private JSONArray getJarBySid(String seriesId){
		JSONArray jar ;
		try {
			jar = prefPRDjo.getJSONArray(seriesId);
		} catch (JSONException e) {
			jar = new JSONArray();
		}
		return jar;
		
	}
	private int downloadArrPrd(JSONArray array){
		int sec = oError;
		for(int i=0;i<array.length();i++){
			if(checkCancel()){return oError;}
			try {
				//download
				OffProduct op = new OffProduct(array.getJSONObject(i));//array.getJSONObject(i);
				int st = op.getStatus();
				if(OfflineUtill.needDownload(st)||OfflineUtill.needDelete(st)){//download  //&delete  1210
					int dsec=0;
					//download
						//prdImg
					dsec+=downloadOfflineImage(op.getProductImage());
						//prdPicsImgArray
					dsec+=downloadOfflineImageFromJsonArray(op.getProductPicsArray());
					//
					//0417
					op.getProduct300XImage().setStatus(OFFSTATUS_COMMON_STATUS);//不下载,用不着
					op.getProductX800Image().setStatus(OFFSTATUS_COMMON_STATUS);
//					if(isOIStatusOk(op.getProductImage(),OFFSTATUS_COMMON_STATUS)&&isArrayStatusOk(op.getProductPicsArray(),OFFSTATUS_COMMON_STATUS)) //update status
					if(dsec==0)
						op.setStatus(OfflineUtill.needDownload(st)?OFFSTATUS_COMMON_STATUS:OFFSTATUS_HAS_DELETE);
				}
				if(OfflineUtill.needDownload(st)||st==0)addOpToJobToPref(op); //新下载和已下载
//				else if(OfflineUtill.needDelete(st)){//  delete //merge to download   //1210
//				}
				array.put(i, op.toJsonObj());
			} catch (JSONException e) {
				countError();
				e.printStackTrace();
				continue;
			}
		}
		return oError-sec;
	}

	private void addOpToJobToPref(OffProduct op) throws JSONException {
		//add to pref-products-string

		String response = doHttpRequest(YftValues.getHTTPBodyString(RequestType.PM, op.getProductId()+""));
		if(isValidResponse(response)){
			JSONObject productJob = new JSONObject(response).getJSONObject(YftValues.RESULT_OBJECT);
			addProdJobToPrefPrdsStr(productJob,op);
			
		}else{
			countError();
		}
	}
	private String doHttpRequest(String httpBodyString) {
		String result=null;
		try {
			result = HttpRequestTask.doHttpRequest(httpBodyString);
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}
	private boolean isOIStatusOk(OffImage oi,int standard){
		return oi.getStatus()==standard;
	}
	private boolean isArrayStatusOk(JSONArray arr,int standard){
		for(int i=0;i<arr.length();i++){
			try {
				int st = arr.getJSONObject(i).getInt(OFF_STATUS);
				if(st!=standard) return false;
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	private int downloadShopInfo(OffShopInfo osi){
		
		String response = doHttpRequest(YftValues.getHTTPBodyString(RequestType.SHOP_INFO, ""+osi.getShopId()));
		if(isValidResponse(response)){//&&YftData.data().commitOffPref(PREFOFF_SHOPINFO+osi.getShopId(), response)){//获得shopinfo response
			prefSI = response;
			//success
		}else{
			countError();
		}
		int allGood=0;
		OffImage oiBp = osi.getShopBannerPic();
		allGood+=downloadOfflineImage(oiBp);
		
		OffImage oiLp = osi.getShopLogoPic();
		allGood+=downloadOfflineImage(oiLp);
		
		JSONArray scArr = osi.getShopCertArray();
		allGood+=downloadOfflineImageFromJsonArray(scArr);
		
		JSONArray spArr = osi.getShopPicsArray();
		allGood+=downloadOfflineImageFromJsonArray(spArr);
		
		if(allGood==0) osi.setStatus(OFFSTATUS_COMMON_STATUS);
		return allGood;
	}
	private int downloadOfflineImageFromJsonArray(JSONArray array){
		int sec = oError;
		if(array==null) return 0;
		for(int i=0;i<array.length();i++){
			try {
				OffImage oi = new OffImage(array.getJSONObject(i));
				downloadOfflineImage(oi);
				array.put(i,oi.toJsonObj());//don't put ahead
			} catch (JSONException e) {
				countError();
				e.printStackTrace();
			}
		}
		return oError-sec;
	}
	int countForDebug;
	private int downloadOfflineImage(OffImage oi){
		int sec = oError;
		if(oi==null||oi.isNothing()){
			publishProgress(percentInOne);
			return 0;//图片没有？暂设为0
		}
		if(OfflineUtill.needDownload(oi.getStatus())&&downloadImage(oi.getImgUrl())){
			publishProgress(percentInOne);
			oi.setStatus(OFFSTATUS_COMMON_STATUS);
		}else if(OfflineUtill.needDelete(oi.getStatus())&&deleteImage(oi.getImgUrl())){
			publishProgress(percentInOne);
			oi.setStatus(OFFSTATUS_HAS_DELETE);
		}else{
		}
		return oError-sec;
	}
	private boolean deleteImage(String imgUrl) {
		if(imgUrl==null||imgUrl.isEmpty()) return false;
		String path = OfflineUtill.getPathUponUrl(oShopId, imgUrl);//"/qfc/imgs/01/qf/tn/si0981.jpg";
		if(!path.isEmpty()){
			if( JackUtils.deleteFile(path)) return true;
		}else{//wrong
		}
		countError();
		return false;
	}

	private boolean downloadImage(String url){
		if(url==null||url.isEmpty()) return false;
		/*if(JackImageLoader.findBitmap(url)!=null){
			return true;}*///1210 TODO
		String path = OfflineUtill.getPathUponUrl(oShopId, url);//"/qfc/imgs/01/qf/tn/si0981.jpg";
		if(!path.isEmpty()){
			Bitmap bm = JackImageUtils.getBitmapFromUrl(url);
			if(bm!=null){
				//success
				JackImageUtils.storeInSD(bm, path);
				return true;
			} 
		} 
		countError();
		return false;
	}
	private void countError(){
		oError++;
	}
	
	private static void updateUIwhenStarts() {
		if(null!=mProgress)mProgress.setVisibility(View.VISIBLE);
		if(null!=btn_update)btn_update.setVisibility(View.GONE);
	}
	static OfflineReceiver mOffRcr;
	public static boolean shouldStart(){
		return builder==null||builder.getStatus()==Status.FINISHED;//0304 finished
	}
	static ProgressDialog pDialog;
	public static void showCancelWaitingDialog(){
		if(null!=mContext){
			pDialog=ProgressDialog.show(mContext, "", "");
		}
	}
	public static void dismissCancelWaitingDialog(){
		if(null!=pDialog){
			pDialog .dismiss();
		}
	}
	public static void onClick(){
		if(shouldStart()){
			mOffRcr = new OfflineReceiver(builder.mContext, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				    startDownloadOfflineData();	
				}
			});
			YftValues.tryGetOfflineDataHere(mOffRcr);
		}else{
			JackUtils.showDialog(builder.mContext, OFFLINETASK_STOP_OR_NOT, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					cancelDownloadOfflineData();
					
				}
			});
		}
	}
	
	public static void startDownloadOfflineData(){
		YftData.data().getHostTab().setCurrentTab(3);//0304
//		if(cannotShowUpdate()) return;
		OfflineDownloadBuilder odBuilder= OfflineDownloadBuilder.getInstance();
		//  start task
		if(shouldStart()){
			odBuilder = OfflineDownloadBuilder.getNewInstance();
		}
		OfflineData  od = mOffRcr.getOfflineData();
		if(mOffRcr!=null){//??
			YftData.data().setOffline(od);
			odBuilder.execute(od);
		}
	}
	private static void cancelDownloadOfflineData(){
//		if(cannotShowUpdate()) return;
		if(builder!=null&&builder.getStatus()!=Status.FINISHED){
			try {
				cancel();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	private static OfflineDownloadBuilder builder;
	private OfflineDownloadBuilder(){}
	public static OfflineDownloadBuilder getInstance(){
		if(null==builder) builder = new OfflineDownloadBuilder();
		return builder;
	}
	public static OfflineDownloadBuilder getNewInstance(){
		builder = new OfflineDownloadBuilder();
		return builder ;
	}
	/**
	 *set current builder null 
	 */
	public static void clear() {
		builder = null;//0421
	}
	
	
	/*public static String doHttpRequest(String urlBody){
   	 String result="";

		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		Log.i("doHttpRequest", urlBody);
		try {
			url = new URL(YftValues._URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Charset", "utf-8");
			//设置连接服务器超时时间
//			connection.setConnectTimeout(TIMEOUT);
			//设置从服务器读取数据超时时间
//			connection.setReadTimeout(TIMEOUT);
			DataOutputStream dop = new DataOutputStream(
					connection.getOutputStream());
//			dop.writeBytes(params[0].getBytes("utf-8"));
			dop.write(urlBody.getBytes("utf-8"));//把中文转成utf-8
			dop.flush();
			dop.close();
			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch(UnknownHostException e){
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		if(null==result) result="";
		Log.i("doHttpRequest", result);
		return result;
	
    }*/
}
