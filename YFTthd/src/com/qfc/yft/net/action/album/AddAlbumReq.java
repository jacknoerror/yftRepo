package com.qfc.yft.net.action.album;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;

public class AddAlbumReq implements ActionRequestImpl {

	
	long compId  ;//Long>   ��˾ID
	String albumName  ;//String>   �������
	String albumDesc  ;//String>   �������
//	userCode  ;//String>   �û�У����
	
	
	
	@Override
	public String getApiName() {
		return NetConst.REQUEST_OPEN_API_ALBUM_ADD;
	}

	public AddAlbumReq(long compId, String albumName, String albumDesc) {
		super();
		this.compId = compId;
		this.albumName = albumName;
		this.albumDesc = albumDesc;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName(),2)));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.EXTRAS_COMP_ID,compId+"");
		halfway.put(NetConst.EXTRAS_ALBUM_NAME,albumName);
		halfway.put(NetConst.EXTRAS_ALBUM_DESC,albumDesc);
		return halfway;
	}

}
