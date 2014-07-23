package com.qfc.yft.net.action.collection;

import java.util.Map;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SaveCollectionReq implements ActionRequestImpl {
	public static final int COLLECTTYPE_PRODUCT = 0;
	public static final int COLLECTTYPE_COMPANY = 3;
	
	int accountId, collectType;
	long fromId;
	String collectTitle;

	public SaveCollectionReq(int accountId, int id, int collectType,
			String collectTitle) {
		super();
		this.accountId = accountId;
		this.fromId = id;
		this.collectType = collectType;
		this.collectTitle = collectTitle;
	}
	

	public SaveCollectionReq(int accountId, long fromId, int collectType) {
		super();
		this.accountId = accountId;
		this.collectType = collectType;
		this.fromId = fromId;
		this.collectTitle = "";
	}


	@Override
	public String getApiName() {
		return REQUEST_COLLECTION_SAVE;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_ACCOUNT_ID, accountId + ""); 
		halfway.put(PARAMS_FROM_ID, fromId + ""); 
		halfway.put(PARAMS_COLLECT_TYPE, collectType + "");// 0 ²úÆ· 3 ÉÌÆÌ
		halfway.put("collectTitle", collectTitle);// ?
		return halfway;
	}

}
