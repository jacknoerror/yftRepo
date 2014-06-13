package com.qfc.yft;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;
import android.util.Log;

import com.qfc.yft.util.HandleStrings;
import com.qfc.yft.util.JackUtils;


public class FormatingTest extends AndroidTestCase {
	private final String TAG  = "UNIT_TEST";

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	public void fromJsonstrToCode() throws JSONException{
		convertJsonToCode();
		String result = HandleStrings.readAndConvert3(mContext);
		assertNotNull(result);
		Log.i(TAG, result);
	}
	
	public void convertJsonToCode() throws JSONException{
		final String jsonStr = "{\"albumId\":5070,\"picOriginNameCode\":\"http://img-i.qfc.cn/upload/01/galary/91/7f/7830_300X300.jpg\",\"picId\":2407,\"picCreateTime\":{\"nanos\":0,\"time\":1369730072000,\"minutes\":34,\"seconds\":32,\"hours\":16,\"month\":4,\"timezoneOffset\":-480,\"year\":113,\"day\":2,\"date\":28},\"picName\":\"5\",\"picCompId\":14843}";
		JSONObject job = new JSONObject(jsonStr);
		Iterator<String> it = job.keys();
		StringBuffer sb = new StringBuffer();
		while(it.hasNext()){
			String name = it.next();
			String line = String.format("private String %s;", name);
			sb.append(line);
//			write(name);
		}
//		Log.i(TAG, sb.toString());
		JackUtils.writeToSomeWhere(mContext, sb.toString());
	}

	/**
	 * @param name
	 */
	private void write(String name) {
		JackUtils.writeToFile(getContext(), "Format", name);
	}
	
	
}
