package com.qfc.yft.net.action.basic;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class ParseOffLineDataForAndriodReq implements ActionRequestImpl {
	String jsonString;

	public ParseOffLineDataForAndriodReq(String jsonString) {
		super();
		this.jsonString = jsonString;
	}

	@Override
	public String getApiName() {
		return REQUEST_PATH_SYNC;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(URL_SYNC, jsonString);
		return halfway;
	}

}
