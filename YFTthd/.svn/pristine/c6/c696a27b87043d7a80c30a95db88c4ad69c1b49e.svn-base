package com.qfc.yft.net.action.member;

import java.util.Map;

import android.R.string;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SearchCardsByKeywordReq implements ActionRequestImpl {
	String keyword;
	int pageSize, pageNo;

	public SearchCardsByKeywordReq(String keyword, int pageSize, int pageNo) {
		super();
		this.keyword = keyword;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	@Override
	public String getApiName() {
		return REQUEST_CARD_SEARCH;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_SEARCH, keyword);
		halfway.put(PARAMS_PAGESIZE, pageSize + "");
		halfway.put(PARAMS_PAGENO, pageNo + "");
		return halfway;
	}

}
