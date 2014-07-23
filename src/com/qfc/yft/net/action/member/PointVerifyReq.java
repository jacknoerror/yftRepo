package com.qfc.yft.net.action.member;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.util.APIDesUtils;
@Deprecated
public class PointVerifyReq implements ActionRequestImpl {

	String username;
	String password;
	
	public PointVerifyReq(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String getApiName() {
		return REQUEST_PATH_LOGIN;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		String pwdDesStr;
		try {
			pwdDesStr= new APIDesUtils().encrypt(password, DES_KEY);
			halfway.put(PARAMS_USERNAME, username);
			halfway.put(PARAMS_PASSWORD, pwdDesStr);
		} catch (Exception e) {
//			Log.e(TAG, "�������������");
			halfway = null;
		}
		return halfway;
	}

}
