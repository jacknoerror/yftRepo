package com.qfc.yft.net.action.shop;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class GetShopAndCompanyByIdReq implements ActionRequestImpl {
	int shopId;

	public GetShopAndCompanyByIdReq(int shopId) {
		super();
		this.shopId = shopId;
	}

	@Override
	public String getApiName() {
		return REQUEST_PATH_COMPANY_INFO;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(URL_SHOPID, shopId + "");
		return halfway;
	}
}