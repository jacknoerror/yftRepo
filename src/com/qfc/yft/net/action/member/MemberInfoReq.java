package com.qfc.yft.net.action.member;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class MemberInfoReq implements ActionRequestImpl {
	
	String userCode;
	
	

	public MemberInfoReq(String userCode) {
		super();
		this.userCode = userCode;
	}

	@Override
	public String getApiName() {
		return REQUEST_PATH_MEMBER_INFO;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(URL_USERCODE,userCode );
		return halfway;
	}

}
