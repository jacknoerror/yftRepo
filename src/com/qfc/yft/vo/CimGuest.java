package com.qfc.yft.vo;

import com.qfc.yft.utils.XMLUtil;

import net.n3.nanoxml.IXMLElement;


public class CimGuest extends CimAbstractVo {
	private UserList users = new UserList();

	private String addr;

	private String code;

	private String keyword;

	private String kind;

	private long lastRemindTime;

	private String name;

	private String time;

	private String title;

	private String url;

	private String userIp;

	private short status;

	private short type;

	private long id;

	private long shopId;

	public void decodeFromXmlNode(IXMLElement node) {

		setId(Long.parseLong(node.getAttribute("id", "0")));
		setName(node.getAttribute("name", ""));
		setTime(node.getAttribute("time", ""));//
		setType((short) node.getAttribute("type", 0));
		setUserIp(node.getAttribute("userIp", ""));
		setUrl(node.getAttribute("url", ""));
		setTitle(node.getAttribute("title", ""));
		setKeyword(node.getAttribute("keyword", ""));
		setAddr(node.getAttribute("addr", ""));
		if (getAddr() == "") {
			setAddr(node.getAttribute("userArea", ""));
			if (getAddr() == "")
				setAddr(userIp);
		}
		setCode(node.getAttribute("code", ""));
	}

	public void decodeFromXmlRoot(IXMLElement root) {
		IXMLElement node = XMLUtil.getChildByName(root, "user");
		if (node != null) {
			decodeFromXmlNode(node);
		}

	}

	public String getAddr() {
		return addr;
	}

	public String getCode() {
		return code;
	}

	public long getId() {
		return id;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getKind() {
		return kind;
	}

	public long getLastRemindTime() {
		return lastRemindTime;
	}

	public String getName() {
		return name;
	}

	public long getShopId() {
		return shopId;
	}

	public short getStatus() {
		return status;
	}

	public String getTime() {
		return time;
	}

	public String getTitle() {
		return title;
	}

	public short getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public CimGuest getUserById(long userId) {
		return (CimGuest) getUsers().getById(userId);
	}

	public String getUserIp() {
		return userIp;
	}

	public UserList getUsers() {
		return users;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setLastRemindTime(long lastRemindTime) {
		this.lastRemindTime = lastRemindTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(short type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public void setUsers(UserList users) {
		this.users = users;
	}
}
