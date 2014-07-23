package com.qfc.yft.ui.offline.entity;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.json.JackJson;

public class OffImage extends JackJson implements IOfflineConst{
	int status ;
	double percentWeight ;
	String imgUrl ;
	String imageCode ;
	
	public OffImage(){
	}
	public OffImage(JSONObject job){
		super(job);
	}
	@Override
	public JSONObject toJsonObj() {
		JSONObject job = new JSONObject();
		if(isNothing()) return job;//1210
		try {
			job.put(OFF_STATUS, status+"");//1209
			job.put(OFF_IMGURL, imgUrl);
			job.put(OFF_IMAGECODE, imageCode);
			job.put(OFF_PERCENTWEIGHT, percentWeight);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return job;
	}
	@Override
	public void initJackJson(JSONObject job) {
		if(job==null||job.length()==0) return;//
		status=job.optInt(OFF_STATUS);
		percentWeight=job.optDouble(OFF_PERCENTWEIGHT,0);//1210
		imgUrl=job.optString(OFF_IMGURL);
		imageCode=job.optString(OFF_IMAGECODE);
		
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getPercentWeight() {
		return percentWeight;
	}
	public void setPercentWeight(double percentWeight) {
		this.percentWeight = percentWeight;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImageCode() {
		return imageCode;
	}
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	
	public boolean isNothing(){//1210
		return status==0&&(null==imgUrl||imgUrl.isEmpty());
	}
	
}
