package com.qfc.yft.net.action.product;


import com.qfc.yft.data.NetConst;

/**
 * ÖØ·¢
 * 
 * @author taotao
 * @Date 2014;//6;//17
 */
public class PMRepublish extends PMFuncAbsReq  {
	
	

	public PMRepublish(long companyId, long[] productId) {
		super(companyId, productId);
	}

	@Override
	public String getApiName() {
		return NetConst.REQUEST_OPEN_API_PRODUCT_REPUBLISH;
	}
	
}
