package com.qfc.yft.net.action.trade;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class GetOrderDetail implements ActionRequestImpl {

	Integer orderId ;//�����
	Integer userType;//�û����ͣ�0:��� 1������  2���ͷ�
	

	public GetOrderDetail(Integer orderId, Integer userType) {
		super();
		this.orderId = orderId;
		this.userType = userType;
	}

	@Override
	public String getApiName() {
		return NetConst.REQUEST_OPEN_API_TRADE_GET_ORDERDETAIL;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_ORDER_ID,""+orderId);
		halfway.put(NetConst.PAMAMS_USER_TYPE,""+userType);
		return halfway;
	}

}
