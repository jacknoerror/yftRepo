package com.qfc.yft.net.action.product;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public abstract class PMFuncAbsReq implements ActionRequestImpl{

	protected long companyId;
	protected long[] productId;

	public PMFuncAbsReq(long companyId, long... productId) {
		super();
		this.companyId = companyId;
		this.productId = productId;
	}
	
	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_COMPANY_ID, "" + companyId);
		halfway.put(NetConst.PARAMS_PRODUCT_IDS, getIdArrStr(productId));
		return halfway;
	}

	private String getIdArrStr(long [] ids) {
		if(null==ids||ids.length==0) return "";
		StringBuffer sb = new StringBuffer();
		for(long id:ids){
			sb.append(id).append(",");
		}
		return sb.toString();
	}

}