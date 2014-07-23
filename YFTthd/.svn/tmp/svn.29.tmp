package com.qfc.yft.net.action.collection;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class IsCollectByShopIdReq implements ActionRequestImpl {
	int shopId, accountId;

	public IsCollectByShopIdReq(int shopId, int accountId) {
		super();
		this.shopId = shopId;
		this.accountId = accountId;
	}

	@Override
	public String getApiName() {
		return REQUEST_IS_COLLECTION_COMPANY;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(URL_SHOPID, shopId + "");
		halfway.put(URL_ACCOUNT_ID, accountId + "");
		return halfway;
	}

}
