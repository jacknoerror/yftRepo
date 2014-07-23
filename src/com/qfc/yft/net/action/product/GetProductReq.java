package com.qfc.yft.net.action.product;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

/**
 *PM
 *���ݲ�ƷID��ȡ��Ʒ���ƶ����� �ã�,�˷����в�Ʒ���ܲ�����html
 * @author taotao
 */
public class GetProductReq implements ActionRequestImpl {
	int productId;

	@Override
	public String getApiName() {
		return "cn.product.getProductForMotion";
	}

	public GetProductReq(int productId) {
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
		halfway.put(PARAMS_PRODUCTID, productId + "");
		return halfway;
	}

}
