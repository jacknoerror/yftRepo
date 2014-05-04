package com.qfc.yft.vo;

import net.n3.nanoxml.IXMLElement;

public class GuestMess extends CimAbstractVo {
	private String time;

	private String userIp;

	private String url;

	private String title;

	private String userArea;

	public void decodeFromXmlNode(IXMLElement node) {

		setTime(node.getAttribute("time", ""));
		setUrl(node.getAttribute("url", ""));
		setUserArea(node.getAttribute("userArea", ""));
		setTitle(node.getAttribute("title", ""));
		setUserIp(node.getAttribute("userIp", ""));

	}

	public void decodeFromXmlRoot(IXMLElement root) {

	}

	public String getTime() {
		return time;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getUserArea() {
		return userArea;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

}
