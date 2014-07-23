package com.qfc.yft.ui.offline;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.qfc.yft.MyApplication;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.MyEvent;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.product.FindSeriesByShopIdForIphoneReq;
import com.qfc.yft.net.action.product.GetProductForMotion1Req;
import com.qfc.yft.net.action.shop.GetShopAndCompanyByIdReq;
import com.qfc.yft.ui.offline.entity.IOfflineConst;
import com.qfc.yft.ui.offline.entity.OffImage;
import com.qfc.yft.ui.offline.entity.OffProduct;
import com.qfc.yft.ui.offline.entity.OffSeries;
import com.qfc.yft.ui.offline.entity.OffShopInfo;
import com.qfc.yft.ui.offline.entity.OfflineData;
import com.qfc.yft.util.JackImageUtils;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.User;

import de.greenrobot.event.EventBus;

public class OfflineDownloadBuilder implements IOfflineConst{
	private static final String EMB_PERCENT = "percent";

	static final String TAG = OfflineDownloadBuilder.class.getSimpleName();

	private static final String EM_UI_PROGRESS = "ui_progress";
	private static final String EM_UI_POST = "ui_post";
	private static final String EM_DOWNLOAD_START = "download_start";
	private static final String EM_UI_PRE = "ui_pre";

	/**
	 * 
	 */
	protected void registEventBus() {
		try{
			EventBus.getDefault().register(this);
		}catch(Exception e) {}
		// Log.i(TAG, "registEventBus");
	}

	/**
	 * 
	 */
	protected void unregistEventBus() {
		try{
			EventBus.getDefault().unregister(this);
		}catch(Exception e) {}
	}

	private static OfflineDownloadBuilder offlinedownloadbuilder;

	private OfflineDownloadBuilder() {
		status = AsyncTask.Status.FINISHED;
		eventbus = EventBus.getDefault();
	}

	public static OfflineDownloadBuilder getInstance() {
		if (null == offlinedownloadbuilder) {
			offlinedownloadbuilder = new OfflineDownloadBuilder();
		}
		return offlinedownloadbuilder;
	}

	/**
	 * PENDING = CANCELING, FINISHED = IDLE
	 */
	private AsyncTask.Status status;
	private OfflineHelper helper = OfflineHelper.getInstance();
	private EventBus eventbus;
	private OfflineData data;
	private double PercentNowHas;
	private double percentInOne;

	private int countForDebug;

	private int oError;


	private int oShopId;

	String prefSI="",prefSRS="";
	JSONObject prefPRDjo = new JSONObject();

	private ProgressBar mProgress;
	private View btn_update;

	private MyEvent mEvent;



	public void setViews(ProgressBar pb, ImageView img) {
		mProgress = pb;
		btn_update= img;
	}

	public void cancel() {
		if(status == AsyncTask.Status.RUNNING) status = AsyncTask.Status.PENDING;
		if(mProgress!=null) mProgress.setVisibility(View.GONE);
		if(btn_update!=null) btn_update.setVisibility(View.VISIBLE);
	};

	public AsyncTask.Status getStatus() {
		return status;
	}

	public void execute(OfflineData latestOD) {
		if(status != Status.FINISHED ||null==latestOD ) return; //
		
		registEventBus();
		
		
		data = latestOD;
		mEvent = new MyEvent("");
		eventbus.post(mEvent.clone().msg(EM_UI_PRE));
	}

	public void onEventMainThread(MyEvent e) {
		if(e.getEventMsg().equals(EM_UI_PRE)){
			//ui  
			if(null!=mProgress)mProgress.setVisibility(View.VISIBLE);
			if(null!=btn_update)btn_update.setVisibility(View.GONE);
			//onbackground
			eventbus.post(mEvent.clone().msg(EM_DOWNLOAD_START));
			
		}else if(e.getEventMsg().equals(EM_UI_POST)){
			if(mProgress!=null){
				mProgress.setVisibility(View.GONE );
				JackUtils.showToast(MyApplication.app(), "离线数据下载完成："+mProgress.getProgress()+"%");
			}
			if(btn_update!=null)btn_update.setVisibility(View.VISIBLE);
			status = Status.FINISHED;
			unregistEventBus();
		}else if(e.getEventMsg().equals(EM_UI_PROGRESS)){
			double double1 = e.getBundle().getDouble(EMB_PERCENT);
			PercentNowHas+=double1;
			Log.i(TAG, PercentNowHas+"::::::::::::::::PROGRESS");
			if(mProgress!=null){
				mProgress.setVisibility(View.VISIBLE);
				mProgress.setProgress((int)Math.round(PercentNowHas*100));//1129
			}
			if(double1>0)countForDebug++;
		}
	}

	public void onEventBackgroundThread(MyEvent e) {
		if(e.getEventMsg().equals(EM_DOWNLOAD_START)){
			download();
		}
	}

	private void publishProgress(double percentNowHas2) {
		MyEvent event = mEvent.clone().msg(EM_UI_PROGRESS);
		Bundle bundle = new Bundle();
		bundle.putDouble(EMB_PERCENT, percentNowHas2);
		event.setBundle(bundle );
		eventbus.post(event);
		
	}

	private void download() {
		//calculate size
		int size = data.getSize();
		PercentNowHas = data.getDownloadStatus().getPercent();
		publishProgress(PercentNowHas);
		percentInOne = (1-PercentNowHas)/size;
		
		//do downloading
		handleThisShit();
		
		eventbus.post(mEvent.clone().msg(EM_UI_POST));
	}

	
	
	
	private void handleThisShit() {
		int sec=oError;
		//shopinfo
		OffShopInfo osInfo = data.getShopInfo();
		oShopId = osInfo.getShopId();
		if(osInfo.getStatus()!=IOfflineConst.OFFSTATUS_COMMON_STATUS) downloadShopInfo(osInfo);
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
//		JackUtils.writeToSomeWhere(YftApplication.app(), data.toJsonStr());
		finishCommit();
	}
	/**
	 * 
	 */
	private boolean checkCancel() {
		if(isCancelled())	{
			
			if(data!=null) data.getDownloadStatus().setPercent(PercentNowHas);
//			clear();//0421
//			MyData.data().commitOffPref();
//			dismissCancelWaitingDialog();
			return true;
		}
		return false;
	}
	private boolean isCancelled() {
		return status == Status.PENDING;
	}

	private String doHttpRequest(String httpBodyString) {
		String result=null;
		try {
			result = HttpRequestTask.doHttpRequest(null, httpBodyString);
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}
	private void finishCommit() {
			Editor fcEditor=  OfflineDataKeeper.getOffEditor();
			if(!prefSI.isEmpty())//1206
				fcEditor	.putString(PREFOFF_SHOPINFO, prefSI);
			if(!prefSRS.isEmpty())fcEditor	.putString(PREFOFF_SERIES, prefSRS);
			if(prefPRDjo.length()>0)fcEditor	.putString(PREFOFF_PRODUCTS, prefPRDjo.toString());
//			MyData.data().getOfflineData().setDownloadStatus(data.getDownloadStatus());//0417 why not set a whole
			OfflineDataKeeper.commitOffPref(data);
			MyData.data().putSeriesList(MyData.data().getMe().getShopId(),null);//0303 下载好后清楚内存 以方便那啥 //0305 time
		if(oError==0){
//		
		}else{
			Log.e(TAG, "更新过程中有错误");
			//TODO
		}
		Log.i(TAG, countForDebug+"::count__pio::"+percentInOne+"---error:"+oError);
	}
	
	private int ifYouHaveToGetShopIdStraightly(){
		User user = MyData.data().getMe();
		if(user!=null) return user.getShopId();
		return 0;
	}
	private boolean isValidResponse(String response){ 
		return !response.isEmpty()&&response.contains(NetConst.RESULT_OBJECT);
	}
	private int downloadArrSrs(JSONArray array){//down nothing,load seriesInfo, save to pref
		int sec = oError;
		int shopId = ifYouHaveToGetShopIdStraightly();
		if(shopId>0) {//用户正确
			String response = doHttpRequest(new FindSeriesByShopIdForIphoneReq(shopId).toHttpBody());
//			String response = doHttpRequest(YftValues.getHTTPBodyString(RequestType.SERIES_INFO, shopId+""));
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
				if(OfflineUtil.needDownload(st)||OfflineUtil.needDelete(st)){//download  //&delete  1210
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
						op.setStatus(OfflineUtil.needDownload(st)?OFFSTATUS_COMMON_STATUS:OFFSTATUS_HAS_DELETE);
				}
				if(OfflineUtil.needDownload(st)||st==0)addOpToJobToPref(op); //新下载和已下载
//				else if(OfflineUtil.needDelete(st)){//  delete //merge to download   //1210
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
		ActionRequestImpl req = new GetProductForMotion1Req(op.getProductId());
//		String response = doHttpRequest(YftValues.getHTTPBodyString(RequestType.PM, op.getProductId()+""));
		String response=  doHttpRequest(req.toHttpBody());
		if(isValidResponse(response)){
			JSONObject productJob =ActionStrategies.getResultObject(response);
			addProdJobToPrefPrdsStr(productJob,op);
			
		}else{
			countError();
		}
	}
private int downloadShopInfo(OffShopInfo osi){
		String response = doHttpRequest(new GetShopAndCompanyByIdReq(osi.getShopId()).toHttpBody());
//		String response = doHttpRequest(NetStrategies.getHTTPBodyString(RequestType.SHOP_INFO, ""+osi.getShopId()));
		if(isValidResponse(response)){//&&MyData.data().commitOffPref(PREFOFF_SHOPINFO+osi.getShopId(), response)){//获得shopinfo response
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
	private int downloadOfflineImage(OffImage oi){
		int sec = oError;
		if(oi==null||oi.isNothing()){
			publishProgress(percentInOne);
			return 0;//图片没有？暂设为0
		}
		if(OfflineUtil.needDownload(oi.getStatus())&&downloadImage(oi.getImgUrl())){
			publishProgress(percentInOne);
			oi.setStatus(OFFSTATUS_COMMON_STATUS);
		}else if(OfflineUtil.needDelete(oi.getStatus())&&deleteImage(oi.getImgUrl())){
			publishProgress(percentInOne);
			oi.setStatus(OFFSTATUS_HAS_DELETE);
		}else{
		}
		return oError-sec;
	}
	private boolean deleteImage(String imgUrl) {
		if(imgUrl==null||imgUrl.isEmpty()) return false;
		String path = OfflineUtil.getPathUponUrl(oShopId, imgUrl);//"/qfc/imgs/01/qf/tn/si0981.jpg";
		if(!path.isEmpty()){
			if( JackUtils.deleteFile(path)) return true;
		}else{//wrong
		}
		countError();
		return false;
	}

	private boolean downloadImage(String url){
		if(url==null||url.isEmpty()) return false;
		String path = OfflineUtil.getPathUponUrl(oShopId, url);//"/qfc/imgs/01/qf/tn/si0981.jpg";
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
	
	
	
	
}
