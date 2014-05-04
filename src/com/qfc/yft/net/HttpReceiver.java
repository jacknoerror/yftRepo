package com.qfc.yft.net;

import org.json.JSONException;

import android.content.Context;

public interface HttpReceiver {
	public void response(String result) throws JSONException;
	public Context getReceiverContext();
}
