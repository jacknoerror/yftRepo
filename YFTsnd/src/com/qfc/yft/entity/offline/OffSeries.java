package com.qfc.yft.entity.offline;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.JackJson;

public class OffSeries extends JackJson implements IOfflineConst{
	
	
	/*
	 * {
    "resultSign": "true",
    "resultObj": {
        "productSeriesName": "风2.1",
        "parentSeriesName": "风系列",
        "productSeriesId": 2726,
        "parentSid": 2725
    }
}*/
	
	
	
	String updateTime;
	int status;
	int seriesId;
	
	public OffSeries(JSONObject job){
		super(job);
	}
	
	@Override
	public JSONObject toJsonObj() {
		JSONObject job = new JSONObject();
		try {
			job.put(OFF_UPDATETIME, updateTime);
			job.put(OFF_STATUS, status+"");//1209
			job.put(OFF_SERIESID, seriesId+"");//1209
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return job;
	}
	@Override
	public void initJackJson(JSONObject job) {
		updateTime=job.optString(OFF_UPDATETIME  );
		status=job.optInt(OFF_STATUS  );
		seriesId=job.optInt(OFF_SERIESID  );
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(int seriesId) {
		this.seriesId = seriesId;
	}
	
	
	
}
