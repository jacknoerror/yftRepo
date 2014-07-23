package com.qfc.yft.net.action.product;


import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.action.ActionRequestImpl;

/**
 * ио╪э
 * 
 * @author taotao
 * @Date 2014;//6;//17
 */
public class PMOnline extends PMFuncAbsReq  {
	
	

	public PMOnline(long companyId, long... productId) {
		super(companyId, productId);
	}

	@Override
	public String getApiName() {
		return NetConst.REQUEST_OPEN_API_PRODUCT_ONLINE;
	}
	
}
