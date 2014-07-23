package com.qfc.yft.entity;


import org.json.JSONObject;


public abstract class JackJson {
	
	protected JSONObject job;
	public JackJson(){};
	public JackJson(JSONObject job){
		this.job = job;
		initJackJson(job);
	}
	
	public abstract JSONObject toJsonObj();
	public abstract void initJackJson(JSONObject job);
	
	public String toJsonStr(){
		return toJsonObj().toString();
	}
}
