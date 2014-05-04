package com.qfc.yft.entity.offline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.utils.JackUtils;

public class OffShopInfo extends JsonOffline {
	OffImage shopBannerPic;
	JSONArray shopCertArray;
	JSONArray shopPicsArray;
	OffImage shopLogoPic;
	int status ;
	String updateTime;
	int shopId;
	
	
	public OffShopInfo(){
		this(0);
	}
	public OffShopInfo(int shopId) {
		super();
		this.shopId = shopId;
		this.shopBannerPic = new OffImage();
		this.shopCertArray = new JSONArray();
		this.shopPicsArray = new JSONArray();
		this.shopLogoPic = new OffImage();
		this.status = -1;
		this.updateTime = JackUtils.getDate();
	}
	public OffShopInfo(JSONObject job){
		super(job);
	}
	
	public int getSize() {
		if(!countMe(status)) return 0;
 		int size = 0;
		if(countMe(shopBannerPic.status)) size++;
		if(countMe(shopLogoPic.status)) size++;
		size+=getArraySize(shopCertArray);
		size+=getArraySize(shopPicsArray);
		return size;
	}
	
	@Override
	public JSONObject toJsonObj() {
		JSONObject job = new JSONObject();
		try {
			job.put(OFF_SHOPBANNERPIC, shopBannerPic.toJsonObj());
			 job.put(OFF_SHOPCERTARRAY, shopCertArray);
			job.put(OFF_SHOPPICSARRAY, shopPicsArray);
			job.put(OFF_SHOPLOGOPIC, shopLogoPic.toJsonObj());
			job.put(OFF_SHOPID, shopId+"");//1209
			job.put(OFF_STATUS, status+"");//1209
			job.put(OFF_UPDATETIME, updateTime);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return job;
	}
	@Override
	public void initJackJson(JSONObject job) {
		if(job==null) return;
		shopBannerPic=new OffImage(job.optJSONObject(OFF_SHOPBANNERPIC  ));
		shopCertArray=job.optJSONArray(OFF_SHOPCERTARRAY  );
		shopPicsArray=job.optJSONArray(OFF_SHOPPICSARRAY  );
		shopLogoPic=new OffImage(job.optJSONObject(OFF_SHOPLOGOPIC  ));		
		shopId=job.optInt(OFF_SHOPID );
		status=job.optInt(OFF_STATUS );
		updateTime=job.optString(OFF_UPDATETIME );
	}
	public OffImage getShopBannerPic() {
		return shopBannerPic;
	}
	public void setShopBannerPic(OffImage shopBannerPic) {
		this.shopBannerPic = shopBannerPic;
	}
	public JSONArray getShopCertArray() {
		return shopCertArray;
	}
	public void setShopCertArray(JSONArray shopCertArray) {
		this.shopCertArray = shopCertArray;
	}
	public JSONArray getShopPicsArray() {
		return shopPicsArray;
	}
	public void setShopPicsArray(JSONArray shopPicsArray) {
		this.shopPicsArray = shopPicsArray;
	}
	public OffImage getShopLogoPic() {
		return shopLogoPic;
	}
	public void setShopLogoPic(OffImage shopLogoPic) {
		this.shopLogoPic = shopLogoPic;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	
	
	
}
