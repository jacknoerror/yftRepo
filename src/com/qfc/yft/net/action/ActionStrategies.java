package com.qfc.yft.net.action;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.qfc.yft.MyApplication;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.util.JackUtils;

public class ActionStrategies implements NetConst {

	public static Object getResultTrueObject(String result) throws JSONException {
		JSONObject job = new JSONObject(result);
		if (job.optBoolean(RESULT_SIGN)) {
			return job.opt(RESULT_OBJECT);
		} else {
			showErrMsg(result);
		}
		return null;
	}


	/**
	 * @param result
	 * @throws JSONException
	 */
	public static void showErrMsg(String result) throws JSONException {
		String errMsg = new JSONObject(result).optString(RESULT_ERROR_MSG);
		if(!errMsg.isEmpty())JackUtils.showToast(MyApplication.app(), errMsg);
	}
	

	public static JSONObject getResultObject(String result) throws JSONException {
		Object obj = getResultTrueObject(result);
		if(null!=obj){
			if(obj instanceof JSONObject) return (JSONObject)obj;
		}
		Log.e("ACTTAG", "resultJob nil");
		return null;
	}
	public static String getResultString(String result) throws JSONException {
		Object obj = getResultTrueObject(result);
		if(null!=obj){
			if(obj instanceof String) return (String)obj;
		}
		return null;
	}
	public static boolean getResultBoolean(String result) throws JSONException {
		return new JSONObject(result).optBoolean(RESULT_OBJECT);
	}
}
