package com.qfc.yft.net.action.product;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class FindSeriesByShopIdForIphoneReq implements ActionRequestImpl {
	int shopId;

	public FindSeriesByShopIdForIphoneReq(int shopId) {
		super();
		this.shopId = shopId;
	}

	@Override
	public String getApiName() {
		return REQUEST_PATH_COMPANY_PRO;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_SHOPID, shopId + "");
		return halfway;
	}

}
