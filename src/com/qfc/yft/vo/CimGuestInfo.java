package com.qfc.yft.vo;

import com.qfc.yft.utils.XMLUtil;

import net.n3.nanoxml.IXMLElement;


public class CimGuestInfo extends CimAbstractVo {
	private String time;

	private String userIp;

	private String url;

	private String title;

	private String userArea;

	private long id;

	private String code;

	private String nickname;

	private int accessCount;

	private int accessSiteCount;

	private UserList users = new UserList();

	public void decodeFromXmlRoot(IXMLElement root) {
		IXMLElement node = XMLUtil.getChildByName(root, "user");
		if (node != null) {
			decodeFromXmlNode(node);
		}

	}

	public void decodeFromXmlNode(IXMLElement node) {
		setNickname(node.getAttribute("nickname", ""));
		setId(Long.parseLong(node.getAttribute("id", "0")));
		setUserArea(node.getAttribute("userArea", ""));
		setCode(node.getAttribute("code", ""));
		setAccessCount(Integer.parseInt(node.getAttribute("accessCount", "0")));
		setAccessSiteCount(Integer.parseInt(node.getAttribute(
				"accessSiteCount", "0")));
		setTime(node.getAttribute("time", ""));

	}

	public UserList getUsers() {
		return users;
	}

	public void setUsers(UserList users) {
		this.users = users;
	}

	public String getUserArea() {
		return userArea;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}

	public int getAccessSiteCount() {
		return accessSiteCount;
	}

	public void setAccessSiteCount(int accessSiteCount) {
		this.accessSiteCount = accessSiteCount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
