package com.qfc.yft.net.action.product;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

/**
 *PM
 *���ݲ�ƷID��ȡ��Ʒ���ƶ����� �ã�,�˷����в�Ʒ���ܲ�����html
 * @author taotao
 */
public class GetProductForMotion1Req implements ActionRequestImpl {
	int productId;

	@Override
	public String getApiName() {
		return REQUEST_PATH_PRODUCTFORMOTION;
	}

	public GetProductForMotion1Req(int productId) {
		super();
		this.productId = productId;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(URL_PRODUCTID, productId + "");
		return halfway;
	}

}
