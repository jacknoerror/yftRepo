package com.qfc.yft.net.action.member;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class ResetPasswordReq implements ActionRequestImpl {
	
	private String mobile;			// 手机
	private String mobileCode;		// 验证码
	private String password;		// 密码
	
	public ResetPasswordReq(String mobile, String mobileCode, String password) {
		this.mobile = mobile;
		this.mobileCode = mobileCode;
		this.password = password;
	}
	
	@Override
	public String getApiName() {
		return REQUEST_RESET_PASSWORD;
	}
	
	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies.getBasicParamMapInstance(getApiName())));
	}
	
	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put("mobile", mobile);
		halfway.put("mobileCode", mobileCode);
		halfway.put("password", password);
		return halfway;
	}
}
