package com.qfc.yft.net.action.member;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SearchMyCardReq implements ActionRequestImpl {
	int accountId;

	public SearchMyCardReq(int accountId) {
		super();
		this.accountId = accountId;
	}

	@Override
	public String getApiName() {
		return REQUEST_CARD_MY;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_ACCOUNT_ID, accountId + "");
		halfway.put(PARAMS_PAGESIZE, "10");
		halfway.put(PARAMS_PAGENO, "1");
		return halfway;
	}

}
