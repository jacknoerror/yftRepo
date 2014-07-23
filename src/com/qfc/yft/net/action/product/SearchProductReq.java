package com.qfc.yft.net.action.product;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

/**
 * 搜索产品
 * @author taotao
 * @Date 2014-6-20
 */
public class SearchProductReq implements ActionRequestImpl {
	String keyword;
	String cateCodes;//cateCodes(分类代码,多个用逗号分开.比如:“001,002”) 
	int pageSize, pageNo;

	public SearchProductReq(String keyword, int pageSize, int pageNo) {
		super();
		this.keyword = keyword;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	public SearchProductReq(String keyword, String cateCodes, int pageSize,
			int pageNo) {
		super();
		this.keyword = keyword;
		this.cateCodes = cateCodes;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	@Override
	public String getApiName() {
		return REQUEST_SEARCH_PRODCUT;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_SEARCH, keyword);
		if(null!=cateCodes)
			halfway.put("cateCodes", cateCodes);
		halfway.put(PARAMS_PAGESIZE, pageSize + "");
		halfway.put(PARAMS_PAGENO, pageNo + "");
		return halfway;
	}

}
