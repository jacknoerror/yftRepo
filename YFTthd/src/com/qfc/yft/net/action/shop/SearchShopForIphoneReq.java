package com.qfc.yft.net.action.shop;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SearchShopForIphoneReq implements ActionRequestImpl {
	String keyword;
	int pageSize, pageNo;

	/**
	 * @param keyword parse null to get the recommended-shop list
	 * @param pageSize
	 * @param pageNo
	 */
	public SearchShopForIphoneReq(String keyword, int pageSize, int pageNo) {
		super();
		this.keyword = keyword;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}
	
	@Override
	public String getApiName() {
		return REQUEST_PATH_RECOMMEND;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		if(null!=keyword)halfway.put(PARAMS_SEARCH, keyword);
		else halfway.put(PARAMS_RECOMMEND, "1");
		halfway.put(PARAMS_PAGESIZE, pageSize + "");
		halfway.put(PARAMS_PAGENO, pageNo + "");
		return halfway;
	}
	// TODO

}
