/**
 * 
 */
package com.qfc.yft.net.action.member;

import org.json.JSONException;

import android.content.Context;

import com.qfc.yft.data.MyData;
import com.qfc.yft.net.action.ActJobRcv;

/**
 * @author taotao
 *
 */
public class PointVerifyForIMRcv extends ActJobRcv{
/*{"userCode":"de63e674ab5c4204988f01a89714efe2",
 * "imID":"1370415196415","userName":"ydspipad1","memberType":0,
 * "ssoSign":"bd11250089154a82bfefd3f556116df7"}}
*/
	public String userCode,sessionId;
	
	public PointVerifyForIMRcv(Context context) {
		super(context);
		userCode = "";
		sessionId = "";
	}

	@Override
	public boolean response(String result) throws JSONException {
		
		boolean response = super.response(result);
		if(response){
			userCode = resultJob.optString("userCode");
			sessionId = resultJob.optString("ssoSign");
			MyData.data().setUserCode(userCode);
			MyData.data().setSessionId(sessionId);
		}
		return response;
	}
	
}
