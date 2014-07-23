package com.qfc.yft.net.action.product;

import java.util.Map;

import android.R.integer;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SearchProductByShopIdAndSeriesIdForIphoneReq implements
		ActionRequestImpl {
	int shopId, pageSize, pageNo;
	Integer proSeriesId;

	public SearchProductByShopIdAndSeriesIdForIphoneReq(int shopId,
			int pageSize, int pageNo, int proSeriesId) {
		super();
		this.shopId = shopId;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.proSeriesId = proSeriesId;
	}

	public SearchProductByShopIdAndSeriesIdForIphoneReq(int shopId,
			int pageSize, int pageNo) {
		super();
		this.shopId = shopId;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	@Override
	public String getApiName() {
		return REQUEST_PATH_COMPANY_SUBPRO;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));

	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(URL_SHOPID, shopId + "");
		if (null != proSeriesId)
		halfway.put(URL_SERIESID, proSeriesId + "");
		halfway.put(URL_PAGESIZE, pageSize + "");
		halfway.put(URL_PAGENO, pageNo + "");
		return halfway;
	}

}
