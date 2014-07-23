package com.qfc.yft.net.action.product;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

/**
 * @author taotao
 * @Date 2014-6-16
 */
public class FindAllSeriesReq implements ActionRequestImpl {

	int companyId;

	public FindAllSeriesReq(int companyId) {
		super();
		this.companyId = companyId;
	}

	@Override
	public String getApiName() {
		return NetConst.REQUEST_OPEN_API_PRODUCT_SERISE_FINDALL;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_COMPANY_ID, companyId + "");
		return halfway;
	}

}
