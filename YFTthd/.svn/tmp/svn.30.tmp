package com.qfc.yft.net.action.album;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SearchAlbumReq implements ActionRequestImpl {

	long compId;// - <Long>  - 公司ID
	int pageNo ;//- <Integer>  - 页码
	int pageSize;// - <Integer>  - 页面大小
	
	
	
	public SearchAlbumReq(long compId, int pageNo, int pageSize) {
		super();
		this.compId = compId;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	@Override
	public String getApiName() {
		return NetConst.REQUEST_PATH_SEARCHBYCOMPID;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName(),2)));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.URL_COMP_ID,compId+"");
		halfway.put(URL_PAGENO,pageNo+"");
		halfway.put(URL_PAGESIZE,pageSize+"");
		return halfway;
	}

}
