package com.qfc.yft.net.action.attention;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class IsAttentionReq implements ActionRequestImpl {
	int memberId, accountId;

	public IsAttentionReq(int memberId, int accountId) {
		super();
		this.memberId = memberId;
		this.accountId = accountId;
	}

	@Override
	public String getApiName() {
		return REQUEST_IS_ATTENTION;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(URL_MEMBER_ID, memberId + "");
		halfway.put(URL_ACCOUNT_ID, accountId + "");
		return halfway;
	}

}
