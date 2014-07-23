package com.qfc.yft.ui.offline.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.json.JackJson;

public abstract class JsonOffline extends JackJson implements IOfflineConst{

	public JsonOffline() {
		super();
	}

	public JsonOffline(JSONObject job) {
		super(job);
	}
	
	protected boolean countMe(int stat){
		return stat!=OfflineData.OFFSTATUS_COMMON_STATUS;
	}
	protected boolean countMe(JSONObject job){
		return countMe(job.optInt(OFF_STATUS,0));
	}
	protected int getArraySize(JSONArray array){
		if(array==null) return 0;
		int size=0;
		for(int i=0;i<array.length();i++){
			try {
				JSONObject job  =array.getJSONObject(i);
				if(countMe(job.optInt(OFF_STATUS, 0))){
					size++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return size;
	}
}