package com.qfc.yft.net.action.collection;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class IsCollectByProductIdReq implements ActionRequestImpl {
	int accountId, productId;

	public IsCollectByProductIdReq(int accountId, int productId) {
		super();
		this.accountId = accountId;
		this.productId = productId;
	}

	@Override
	public String getApiName() {
		return REQUEST_IS_COLLECTION_PRODUCT;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_PRODUCTID, productId + "");
		halfway.put(PARAMS_ACCOUNT_ID, accountId + "");
		return halfway;
	}

}
