package com.qfc.yft.net.action.trade;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

/**
 * 采购订单
 * @author taotao
 * @Date 2014-6-23
 */
@Deprecated
public class GetBuyerOrdersReq implements ActionRequestImpl {
	public static final String OStatus_ALL ="";
	public static final String OStatus_2PAY ="waitPay";
	public static final String OStatus_2CNFM ="waitConfirm";
	public static final String OStatus_2SCMMT= "waitSellerJudge";
	public static final String OStatus_2BCMMT= "waitBuyerJudge";
	
	int memberId;// 用户Id（Integer类型数据） （不必填，为空时companyId必填）
	// long companyId ;// 公司Id（Long类型数据） （不必填，为空时memberId必填）
	String orderStatus;// 订单状态（String类型,取值：
	// waitPay、waitReceive、waitBuyerJudge、waitSellerJudge、waitConfirm、waitSend、waitRefund
	// ）（不必填，不填取全部）
	int pageNo;// 页号（Integer类型） （必填）
	int pageSize;// 每页记录数（Integer类型） （必填）

	// int order ;// 排序方式（String类型） （不必填）（默认desc）
	// int orderBy ;// 排序字段（String类型） （不必填）（默认order_time）

	public GetBuyerOrdersReq(int memberId, String orderStatus, int pageNo,
			int pageSize) {
		super();
		this.memberId = memberId;
		this.orderStatus = orderStatus;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	@Override
	public String getApiName() {
		return NetConst.REQUEST_TRADE_API_ORDER_EXT_SEARCH_BUYER_ORDERS;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies.getBasicParamMapInstance(getApiName())));

	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_MEMBER_ID, "" + memberId);
		if(null!=orderStatus&&!orderStatus.isEmpty())halfway.put(PARAMS_ORDER_STATUS, "" + orderStatus);
		halfway.put(PARAMS_PAGENO, "" + pageNo);
		halfway.put(PARAMS_PAGESIZE, "" + pageSize);
		return halfway;
	}

}
