package com.qfc.yft.net.action.member;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SendMobileCodeReq implements ActionRequestImpl {
	
	private String mobile;		//  ÷ª˙
	private Integer type;		// 1◊¢≤·£¨2÷ÿ÷√√‹¬Î
	
	public SendMobileCodeReq(String mobile, Integer type) {
		this.mobile = mobile;
		this.type = type;
	}
	
	@Override
	public String getApiName() {
		return REQUEST_SEND_MOBILE_CODE;
	}
	
	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies.getBasicParamMapInstance(getApiName())));
	}
	
	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put("mobile", mobile);
		halfway.put("type", type  + "");
		return halfway;
	}
}
