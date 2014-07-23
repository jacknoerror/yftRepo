package com.qfc.yft.net.action;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;

public abstract class NoParamsReq implements ActionRequestImpl {

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));

	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		return halfway;
	}

}
