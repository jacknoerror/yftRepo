package com.qfc.yft.net.action.attention;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class GetShopByMemberIdReq implements ActionRequestImpl {
	int memberId;

	public GetShopByMemberIdReq(int memberId) {
		super();
		this.memberId = memberId;
	}

	@Override
	public String getApiName() {
		return REQUEST_GETSHOPBYUID;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(URL_MEMBER_ID, memberId + "");
		return halfway;
	}

}
