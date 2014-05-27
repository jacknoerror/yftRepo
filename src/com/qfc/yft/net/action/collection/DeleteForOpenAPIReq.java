package com.qfc.yft.net.action.collection;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class DeleteForOpenAPIReq implements ActionRequestImpl {
	int accountId, fromId, collectType;

	public DeleteForOpenAPIReq(int accountId, int fromId, int collectType) {
		super();
		this.accountId = accountId;
		this.fromId = fromId;
		this.collectType = collectType;
	}

	@Override
	public String getApiName() {
		return REQUEST_COLLECTION_DELETE;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(URL_ACCOUNT_ID, accountId + "");// 36662
		halfway.put(URL_FROM_ID, fromId + "");// 14723
		halfway.put(URL_COLLECT_TYPE, collectType + "");// 0 ²úÆ· 3 ÉÌÆÌ
		return halfway;
	}

}
