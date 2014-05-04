package com.qfc.yft.vo;

import net.n3.nanoxml.IXMLElement;

public class GuestMss extends CimAbstractVo {
	private String title;

	private String url;

	private String userIp;

	private short type;

	public void decodeFromXmlRoot(IXMLElement root) {

	}

	public void decodeFromXmlNode(IXMLElement node) {

		setUserIp(node.getAttribute("userIp", ""));
		setTitle(node.getAttribute("title", ""));
		setUrl(node.getAttribute("url", ""));

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

}
