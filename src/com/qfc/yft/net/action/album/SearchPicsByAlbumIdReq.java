package com.qfc.yft.net.action.album;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class SearchPicsByAlbumIdReq implements ActionRequestImpl {
	
	private static final String REQUEST_PATH_OPEN_API_PICTURE_SEARCHBYALBUMID = "open.api.picture.searchbyalbumid";
	long compId  ;//<Long>   ��˾ID
	int albumId  ;//<Integer>   ���ID
//	String userCode  <String>   �û�У����
	int pageNo  ;//<Integer>   ҳ��
	int pageSize  ;//<Integer>   ҳ���С
	
	
	public SearchPicsByAlbumIdReq(long compId, int albumId, int pageNo) {
		super();
		this.compId = compId;
		this.albumId = albumId;
		this.pageNo = pageNo;
		this.pageSize=100;
	}

	@Override
	public String getApiName() {
		return REQUEST_PATH_OPEN_API_PICTURE_SEARCHBYALBUMID;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName(),2)));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.EXTRAS_COMP_ID,compId+"");
		halfway.put(NetConst.EXTRAS_ALBUM_ID,albumId+"");
		halfway.put(NetConst.URL_PAGENO,pageNo+"");
		halfway.put(NetConst.URL_PAGESIZE,pageSize+"");
		return halfway;
	}

}
