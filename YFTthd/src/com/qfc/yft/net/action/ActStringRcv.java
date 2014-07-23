package com.qfc.yft.net.action;

import org.json.JSONException;

import android.content.Context;

public class ActStringRcv extends ActAbsSthRcv{

	public ActStringRcv(Context context) {
		super(context);
	}

	public String resultStr;
	

	@Override
	public boolean response(String result) throws JSONException {
		resultStr = ActionStrategies.getResultString(result);
		return null!=resultStr&&!resultStr.isEmpty();
	}


}
