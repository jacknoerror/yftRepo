package com.qfc.yft.net.action;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;



public interface ActionRequestImpl extends NetConst{
	
	public String getApiName();
	/**
	 * NetStrategies.finishTheURL(halfwayParamMap(NetStrategies.getBasicParamMapInstance(getApiName())));
	 * @return
	 */
	public String toHttpBody();
	/**
	 * halfway.put("",""+xx);
	 * @param halfway
	 * @return halfway
	 */
	public Map<String, String> halfwayParamMap(Map<String,String> halfway);
}
