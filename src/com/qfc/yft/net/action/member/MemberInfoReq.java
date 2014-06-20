package com.qfc.yft.net.action.member;

import java.util.Map;

import com.qfc.yft.data.MyData;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

/**
 *通过usercode获取用户信息
 * @author taotao
 */
public class MemberInfoReq implements ActionRequestImpl {
	
	@Deprecated
	String userCode;
	
	

	public MemberInfoReq() {
	}

	public MemberInfoReq(String userCode) {
		super();
		this.userCode = userCode;
	}

	@Override
	public String getApiName() {
		return REQUEST_PATH_MEMBER_INFO;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies.getBasicParamMapInstance(getApiName())));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
//		halfway.put(URL_USERCODE,userCode );
		halfway.put(PARAMS_USERCODE,MyData.data().getUserCode());// to be tested 0604
		return halfway;
	}

}
