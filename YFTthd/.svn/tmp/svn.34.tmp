package com.qfc.yft.net.action.member;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.util.JackUtils;

public class DoAttentionReq implements ActionRequestImpl {
	int fromMember, toMember;

	public DoAttentionReq(int fromMember, int toMember) {
		super();
		this.fromMember = fromMember;
		this.toMember = toMember;
	}

	@Override
	public String getApiName() {
		return REQUEST_CARD_ADD;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(URL_FROM_MEMBER, fromMember+"");
		halfway.put(URL_TO_MEMBER, toMember+"");
		halfway.put("fromIp", JackUtils.getTimeStamp());
		return halfway;
	}

}
