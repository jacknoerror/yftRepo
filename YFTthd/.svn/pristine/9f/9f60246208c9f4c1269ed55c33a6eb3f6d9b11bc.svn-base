package com.qfc.yft.net.action.album;

import java.util.Map;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ExpandableActReqImpl;

public class DelAlbumPicReq implements ExpandableActReqImpl {

	long compId;// Long> 公司ID
	int albumId;// Integer> 相册ID
	// String userCode ;//String> 用户校验码
	String picId;// Integer> 图片id

	public DelAlbumPicReq(long compId, int albumId, int picId) {
		this(compId, albumId, picId + "");
	}

	public DelAlbumPicReq(long compId, int albumId, String picId) {
		super();
		this.compId = compId;
		this.albumId = albumId;
		this.picId = picId;
	}

	@Override
	public String getApiName() {
		return NetConst.REQUEST_OPEN_API_PICTURE_DELETE;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.finishTheURL(halfwayParamMap(NetStrategies
				.getBasicParamMapInstance(getApiName(), 2)));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(EXTRAS_COMP_ID, compId + "");
		halfway.put(EXTRAS_ALBUM_ID, albumId + "");
		halfway.put(NetConst.EXTRAS_PIC_ID, picId + "");
		return halfway;
	}

	@Override
	public ExpandableActReqImpl setExpandableParam(String param) {
		picId =   param;
		return this;
	}

}
