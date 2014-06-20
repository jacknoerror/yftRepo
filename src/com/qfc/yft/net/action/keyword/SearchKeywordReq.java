package com.qfc.yft.net.action.keyword;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SearchKeywordReq implements ActionRequestImpl {
	int pageSize;
	String searchType,keyword;

	public SearchKeywordReq(String keyword, int pageSize, String searchType) {
		super();
		this.keyword = keyword;
		this.pageSize = pageSize;
		this.searchType = searchType;
	}

	@Override
	public String getApiName() {
		return REQUEST_SEARCH_IMAGINE;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_SEARCH, keyword + "");
		halfway.put(PARAMS_PAGESIZE, pageSize + "");
		halfway.put(NetConst.PARAMS_SEARCH_TYPE, searchType + "");
		return halfway;
	}

}
