package com.qfc.yft.net.action.member;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SearchReq implements ActionRequestImpl {
	int accountId;

	public SearchReq(int accountId) {
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
		halfway.put(URL_ACCOUNT_ID, accountId + "");
		halfway.put(URL_PAGESIZE, "10");
		halfway.put(URL_PAGENO, "1");
		return halfway;
	}

}