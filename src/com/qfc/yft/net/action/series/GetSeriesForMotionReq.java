package com.qfc.yft.net.action.series;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class GetSeriesForMotionReq implements ActionRequestImpl {
	int proSeriesId;

	public GetSeriesForMotionReq(int proSeriesId) {
		super();
		this.proSeriesId = proSeriesId;
	}

	@Override
	public String getApiName() {
		return REQUEST_PATH_SERIESFORMOTION;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));

	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_SERIESID, proSeriesId + "");
		return halfway;
	}

}
