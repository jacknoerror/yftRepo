package com.qfc.yft.net.action;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.qfc.yft.util.JackUtils;


/**
 *A receiver does nothing but offers the request-result
 * @author tao
 */
public class BareReceiver implements ActionReceiverImpl {
	
	Context context;
	
	public BareReceiver(Context context) {
		super();
		this.context = context;
	}
	
	protected JSONObject resultJob;//for child
	@Override
	public boolean response(String result) throws JSONException {//0504
		return null!=(resultJob = new JSONObject(result))&&resultJob.optBoolean(RESULT_SIGN);
	}
	

	@Override
	public Context getReceiverContext() {
		return context;
	}


}
