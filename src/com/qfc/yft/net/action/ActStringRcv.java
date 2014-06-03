package com.qfc.yft.net.action;

import org.json.JSONException;

import android.content.Context;

public class ActStringRcv implements ActionReceiverImpl {

	public String resultStr;
	Context context;
	
	
	public ActStringRcv(Context context) {
		super();
		this.context = context;
	}

	@Override
	public boolean response(String result) throws JSONException {
		resultStr = ActionStrategies.getResultString(result);
		return null!=resultStr&&!resultStr.isEmpty();
	}

	@Override
	public Context getReceiverContext() {
		return context;
	}

}
