package com.qfc.yft.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.json.JsonImport;

/**
 *ПаІб
 * @author taotao
 */
public class Album extends JsonImport {
	private String albumName;
	private String albumCompId;
	private String albumId;
	private String albumCapacity;
	private String albumBgImgUrl;
	private String albumCategory;
	

	public Album() {
		super();
	}


	public Album(JSONObject job) {
		super(job);
	}


	public final String getAlbumName() {
		return albumName;
	}


	public final void setAlbumName(String albumName) {
		this.albumName = albumName;
	}


	public final String getAlbumCompId() {
		return albumCompId;
	}


	public final void setAlbumCompId(String albumCompId) {
		this.albumCompId = albumCompId;
	}


	public final String getAlbumId() {
		return albumId;
	}


	public final void setAlbumId(String albumId) {
		this.albumId = albumId;
	}


	public final String getAlbumCapacity() {
		return albumCapacity;
	}


	public final void setAlbumCapacity(String albumCapacity) {
		this.albumCapacity = albumCapacity;
	}


	public final String getAlbumBgImgUrl() {
		return albumBgImgUrl;
	}


	public final void setAlbumBgImgUrl(String albumBgImgUrl) {
		this.albumBgImgUrl = albumBgImgUrl;
	}


	public final String getAlbumCategory() {
		return albumCategory;
	}


	public final void setAlbumCategory(String albumCategory) {
		this.albumCategory = albumCategory;
	}


	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("albumName")) albumName = job.getString("albumName");
		if(job.has("albumCompId")) albumCompId = job.getString("albumCompId");
		if(job.has("albumId")) albumId = job.getString("albumId");
		if(job.has("albumCapacity")) albumCapacity = job.getString("albumCapacity");
		if(job.has("albumBgImgUrl")) albumBgImgUrl = job.getString("albumBgImgUrl");
		if(job.has("albumCategory")) albumCategory = job.getString("albumCategory");
	}

}
