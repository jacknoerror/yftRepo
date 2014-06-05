package com.qfc.yft.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.ui.custom.list.MspJsonItem;

/**
 * …Ã∆Ãœ‡≤·
 * 
 * @author taotao
 */
public class AlbumInShop extends MspJsonItem {
	private String albumName;
	private long albumCompId;
	private long albumId;
	private int albumCapacity;
	private String albumBgImgUrl;
	private int albumCategory;

	private int albumOrder;
	private String albumDesc;

	public final String getAlbumName() {
		return albumName;
	}

	public final void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public final long getAlbumCompId() {
		return albumCompId;
	}

	public final void setAlbumCompId(long albumCompId) {
		this.albumCompId = albumCompId;
	}

	public final long getAlbumId() {
		return albumId;
	}

	public final void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public final int getAlbumCapacity() {
		return albumCapacity;
	}

	public final void setAlbumCapacity(int albumCapacity) {
		this.albumCapacity = albumCapacity;
	}

	public final String getAlbumBgImgUrl() {
		return albumBgImgUrl;
	}

	public final void setAlbumBgImgUrl(String albumBgImgUrl) {
		this.albumBgImgUrl = albumBgImgUrl;
	}

	public final int getAlbumCategory() {
		return albumCategory;
	}

	public final void setAlbumCategory(int albumCategory) {
		this.albumCategory = albumCategory;
	}

	public final int getAlbumOrder() {
		return albumOrder;
	}

	public final void setAlbumOrder(int albumOrder) {
		this.albumOrder = albumOrder;
	}

	public final String getAlbumDesc() {
		return albumDesc;
	}

	public final void setAlbumDesc(String albumDesc) {
		this.albumDesc = albumDesc;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if (job.has("albumName"))
			albumName = job.getString("albumName");
		if (job.has("albumCompId"))
			albumCompId = job.getInt("albumCompId");
		if (job.has("albumId"))
			albumId = job.getInt("albumId");
		if (job.has("albumCapacity"))
			albumCapacity = job.getInt("albumCapacity");
		if (job.has("albumBgImgUrl"))
			albumBgImgUrl = job.getString("albumBgImgUrl");
		if (job.has("albumCategory"))
			albumCategory = job.getInt("albumCategory");
		if (job.has("albumOrder"))
			albumOrder = job.getInt("albumOrder");
		if (job.has("albumDesc"))
			albumDesc = job.getString("albumDesc");
	}

}
