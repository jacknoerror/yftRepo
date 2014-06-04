package com.qfc.yft.net.action;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class ActJobRcv extends ActAbsSthRcv {

	public JSONObject resultJob;
	
	public ActJobRcv(Context context) {
		super(context);
	}

	@Override
	public boolean response(String result) throws JSONException {
		resultJob = ActionStrategies.getResultObject(result);
		return null!=resultJob;
	}

}
