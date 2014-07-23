package com.qfc.yft.net.action.trade;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.ui.tabs.work.OrderActivity;

/**
 * �ɹ�����
 * @author taotao
 * @Date 2014-6-23
 */
public class GetOrdersReq implements ActionRequestImpl {
	public static final String OStatus_ALL ="";
	public static final String OStatus_2PAY ="waitPay";
	public static final String OStatus_2CNFM ="waitConfirm";
	public static final String OStatus_2SCMMT= "waitSellerJudge";
	public static final String OStatus_2BCMMT= "waitBuyerJudge";
	public static final String OStatus_2SEND= "waitSend";
	public static final String OStatus_2REFUND= "waitRefund";
	
	int memberId;// �û�Id��Integer�������ݣ� �������Ϊ��ʱcompanyId���
	// long companyId ;// ��˾Id��Long�������ݣ� �������Ϊ��ʱmemberId���
	String orderStatus;// ����״̬��String����,ȡֵ��
	// waitPay��waitReceive��waitBuyerJudge��waitSellerJudge��waitConfirm��waitSend��waitRefund
	// �������������ȡȫ����
	int pageNo;// ҳ�ţ�Integer���ͣ� �����
	int pageSize;// ÿҳ��¼����Integer���ͣ� �����

	// int order ;// ����ʽ��String���ͣ� ���������Ĭ��desc��
	// int orderBy ;// �����ֶΣ�String���ͣ� ���������Ĭ��order_time��

	private int type;
	
	public GetOrdersReq(int type, int memberId, String orderStatus, int pageNo,
			int pageSize) {
		super();
		this.type = type;
		this.memberId = memberId;
		this.orderStatus = orderStatus;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	@Override
	public String getApiName() {
		if (type == OrderActivity.BUY)
			return NetConst.REQUEST_TRADE_API_ORDER_EXT_SEARCH_BUYER_ORDERS;
		else
			return NetConst.REQUEST_TRADE_API_ORDER_EXT_SEARCH_SELLER_ORDERS;
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
