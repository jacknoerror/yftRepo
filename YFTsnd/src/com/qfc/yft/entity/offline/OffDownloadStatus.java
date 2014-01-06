package com.qfc.yft.entity.offline;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.JackJson;

public class OffDownloadStatus extends JackJson implements IOfflineConst{
	double percent ;
	int status ;
	double size  ;
	
	public OffDownloadStatus(){
		percent= 0f;
		status = -1;
		size = 0f;
	};
	public OffDownloadStatus(JSONObject job){
		super(job);
	}
	
	@Override
	public JSONObject toJsonObj() {
		JSONObject job = new JSONObject();
		try {
			job.put(OFF_PERCENT, percent);
			job.put(OFF_STATUS, status+"");//1209
			job.put(OFF_SIZE, size);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return job;
	}
	@Override
	public void initJackJson(JSONObject job) {
		percent = job.optDouble(OFF_PERCENT);
		status =job.optInt(OFF_STATUS);
		size = job.optDouble(OFF_SIZE);
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	
	
}
