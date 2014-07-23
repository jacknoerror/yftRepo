package com.qfc.yft.net.action.member;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class CancelAttentionReq implements ActionRequestImpl {
	int toMember, fromMember;

	public CancelAttentionReq(int toMember, int fromMember) {
		super();
		this.toMember = toMember;
		this.fromMember = fromMember;
	}

	@Override
	public String getApiName() {
		return REQUEST_CARD_REMOVE;
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
		return halfway;
	}

}
