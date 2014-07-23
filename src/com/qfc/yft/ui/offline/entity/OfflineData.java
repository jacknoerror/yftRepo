package com.qfc.yft.ui.offline.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class OfflineData extends JsonOffline {
	
	JSONArray seriesArray;
	JSONArray productArray;
	OffShopInfo shopInfo;
	OffDownloadStatus downloadStatus;
	
	public OfflineData(){
		this(0);
	}
	public OfflineData(int shopId){
		seriesArray= new JSONArray();
		productArray= new JSONArray();
		shopInfo = new OffShopInfo(shopId);
		downloadStatus = new OffDownloadStatus();
	}
	public OfflineData(JSONObject job){
		super(job);
	}
	
	@Override
	public JSONObject toJsonObj() {
		JSONObject job = new JSONObject();
		try {
			job.put(OFF_SERIESARRAY, seriesArray);
			job.put(OFF_PRODUCTARRAY, productArray);
			job.put(OFF_SHOPINFO, shopInfo.toJsonObj());
			job.put(OFF_DOWNLOADSTATUS, downloadStatus.toJsonObj());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return job;
	}
	@Override
	public void initJackJson(JSONObject job) {
		seriesArray=job.optJSONArray(OFF_SERIESARRAY );
		productArray=job.optJSONArray(OFF_PRODUCTARRAY );
		try {
			shopInfo=new OffShopInfo(job.getJSONObject(OFF_SHOPINFO ));
			downloadStatus=new OffDownloadStatus(job.getJSONObject(OFF_DOWNLOADSTATUS ));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getSize(){
		int size=0;
		size+=getArraySize(seriesArray);
		size+=getArraySize2(productArray);
		size+=shopInfo.getSize();
		return size;
	}
	
	
	private int getArraySize2(JSONArray array) {
		if(array==null) return 0;
		int size=0;
		for(int i=0;i<array.length();i++){
			try {
				JSONObject job  =array.getJSONObject(i);
				if(countMe(job.optInt(OFF_STATUS, 0))){
					if(job.has(OFF_PRODUCTIMAGE)){
						if( countMe(job)) size++;
					}
					if(job.has(OFF_PRODUCTPICSARRAY)){
						size+= getArraySize(job.getJSONArray(OFF_PRODUCTPICSARRAY));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return size;
	}
	public JSONArray getSeriesArray() {
		return seriesArray;
	}
	public void setSeriesArray(JSONArray seriesArray) {
		this.seriesArray = seriesArray;
	}
	public JSONArray getProductArray() {
		return productArray;
	}
	public void setProductArray(JSONArray productArray) {
		this.productArray = productArray;
	}
	public OffShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(OffShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public OffDownloadStatus getDownloadStatus() {
		return downloadStatus;
	}
	public void setDownloadStatus(OffDownloadStatus downloadStatus) {
		this.downloadStatus = downloadStatus;
	}
	
	
}
