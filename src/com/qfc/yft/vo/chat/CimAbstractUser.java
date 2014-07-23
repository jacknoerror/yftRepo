package com.qfc.yft.vo.chat;

public abstract class CimAbstractUser extends CimAbstractVo {
	private short status;

	private String imageFile;

	private short type;

	public String getFullName() {
		return getName();
	}

	public void setStatus(short aStatus) {
		status = aStatus;
	}

	public short getStatus() {
		return status;
	}

	public void setImageFile(String aImageFile) {
		imageFile = aImageFile;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setType(short aType) {
		type = aType;
	}

	public short getType() {
		return type;
	}
}
