package com.qfc.yft.net.action.shop;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SearchShopForIphoneReq implements ActionRequestImpl {
	String keyword;
	int pageSize, pageNo;

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
		halfway.put(URL_SEARCH, keyword);
		halfway.put(URL_PAGESIZE, pageSize + "");
		halfway.put(URL_PAGENO, pageNo + "");
		return halfway;
	}
	// TODO

}
