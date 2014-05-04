package com.qfc.yft.ui.tabs.chat;

/**
 * 
 ****************************************** 
 * 
 * @文件名称 : ChatMsgEntity.java
 * @创建时间 : 2013-1-27 下午02:33:33
 * @文件描述 : 消息实体
 ****************************************** 
 */
public class ChatMsgEntity {

	private String name;
	private long id;
	private String fileImg;
	private String date;
	private String text;
	private long groupId;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	private boolean isComMeg = true;

	public ChatMsgEntity() {
	}

	public ChatMsgEntity(String name, String date, String text, boolean isComMsg) {
		super();
		this.name = name;
		this.date = date;
		this.text = text;
		this.isComMeg = isComMsg;
	}

	public String getDate() {
		return date;
	}

	public String getFileImg() {
		return fileImg;
	}

	public long getId() {
		return id;
	}

	public boolean getMsgType() {
		return isComMeg;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setFileImg(String fileImg) {
		this.fileImg = fileImg;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setMsgType(boolean isComMsg) {
		isComMeg = isComMsg;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setText(String text) {
		this.text = text;
	}

}
