package com.qfc.yft.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.json.JsonImport;
import com.qfc.yft.ui.custom.list.MspJsonItem;

public class AlbumPic extends MspJsonItem {
	private int picId;
	private String picName;
	private JSONObject picCreateTime;
	private int albumId;
	private int picCompId;
	private String picOriginNameCode;
	

	public final int getPicId() {
		return picId;
	}


	public final void setPicId(int picId) {
		this.picId = picId;
	}


	public final String getPicName() {
		return picName;
	}


	public final void setPicName(String picName) {
		this.picName = picName;
	}


	public final JSONObject getPicCreateTime() {
		return picCreateTime;
	}


	public final void setPicCreateTime(JSONObject picCreateTime) {
		this.picCreateTime = picCreateTime;
	}


	public final int getAlbumId() {
		return albumId;
	}


	public final void setAlbumId(int albumId) {
		this.albumId = albumId;
	}


	public final int getPicCompId() {
		return picCompId;
	}


	public final void setPicCompId(int picCompId) {
		this.picCompId = picCompId;
	}


	public final String getPicOriginNameCode() {
		return picOriginNameCode;
	}


	public final void setPicOriginNameCode(String picOriginNameCode) {
		this.picOriginNameCode = picOriginNameCode;
	}


	public AlbumPic() {
		super();
	}


	public AlbumPic(JSONObject job) {
		super(job);
	}


	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("picId")) picId = job.getInt("picId");
		if(job.has("picName")) picName = job.getString("picName");
		if(job.has("picCreateTime")) picCreateTime = job.getJSONObject("picCreateTime");
		if(job.has("albumId")) albumId = job.getInt("albumId");
		if(job.has("picCompId")) picCompId = job.getInt("picCompId");
		if(job.has("picOriginNameCode")) picOriginNameCode = job.getString("picOriginNameCode");
	}

}
