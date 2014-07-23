package com.qfc.yft.net.action.member;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

/**
 * 产品管理列表
 * 
 * @author taotao
 * @Date 2014616
 */
public class SearchManageProductReq implements ActionRequestImpl {

	String/*Integer*/ auditStatus;// /*Integer*/> 审核状态
	String/*Integer*/ isPrivate;// Integer> 是否私有
	String/*Integer*/ productStatus;// Integer> 产品状态
	String productSeries;// String> 产品系列
	long companyId;// Long> 公司id
	int pageNo;


	public SearchManageProductReq(String auditStatus, String isPrivate,
			String productStatus, String productSeries, long companyId,
			int pageNo) {
		super();
		this.auditStatus = auditStatus;
		this.isPrivate = isPrivate;
		this.productStatus = productStatus;
		this.productSeries = productSeries;
		this.companyId = companyId;
		this.pageNo = pageNo;
	}

	@Override
	public String getApiName() {
		return NetConst.REQUEST_OPEN_API_PRODUCT_MEMBER_SEARCH;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName(), 2)));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		if (null != auditStatus)
			halfway.put(NetConst.PARAMS_AUDIT_STATUS, auditStatus + "");
		if (null != isPrivate)
			halfway.put(NetConst.PARAMS_IS_PRIVATE, isPrivate + "");
		if (null != productStatus)
			halfway.put(NetConst.PARAMS_PRODUCT_STATUS, productStatus + "");
		if (null != productSeries)
			halfway.put(NetConst.PARAMS_PRODUCT_SERIES, productSeries);
		halfway.put(PARAMS_COMPANY_ID, companyId + "");
		halfway.put(PARAMS_PAGENO, pageNo + "");
		return halfway;
	}

}
