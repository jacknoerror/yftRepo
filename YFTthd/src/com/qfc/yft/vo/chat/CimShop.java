package com.qfc.yft.vo.chat;

import net.n3.nanoxml.IXMLElement;

public class CimShop extends CimAbstractVo {
	private long ShopId = 0;

	private UserList users = new UserList();

	private short ownerKind = 0;

	private String code;

	private String url;

	private String http;

	private String addr;

	private String fax;

	private String msn;

	private String notes;

	private String phone;

	private String qq;

	private int maxChildCount;

	private double money;

	private int smsCount;

	private short status;

	public void decodeFromXmlRoot(IXMLElement root) {
	}

	public CimUser getUserById(long userId) {
		return (CimUser) getUsers().getById(userId);
	}

	public void decodeFromXmlNode(IXMLElement node) {
		setId(Long.parseLong(node.getAttribute("id", "0")));
		setName(node.getAttribute("name", ""));
		setCode(node.getAttribute("code", ""));
		setUrl(node.getAttribute("url", ""));
		setStatus((short) node.getAttribute("state", 0));
		setMaxChildCount((short) node.getAttribute("createMaxShop", 0));
		setPhone(node.getAttribute("infoPhone", ""));
		setFax(node.getAttribute("infoFax", ""));
		setQq(node.getAttribute("infoQQ", ""));
		setMsn(node.getAttribute("infoMSN", ""));
		setAddr(node.getAttribute("infoAddr", ""));
		setNotes(node.getAttribute("infoNotes", ""));
		setHttp(node.getAttribute("intoHttp", ""));
		setSmsCount(node.getAttribute("smsCount", 0));
		setMoney(Double.parseDouble(node.getAttribute("cimMoney", "0")));
		users.decodeShopUsers(node);
		int n = users.getIndexById(SystemParams.getInstance().getUserId());
		if (n >= 0) {
			setOwnerKind(users.getUser(n).getShopUserType());
		}
	}

	public UserList getUsers() {
		return users;
	}

	public short getOwnerKind() {
		return ownerKind;
	}

	public void setOwnerKind(short ownerKind) {
		this.ownerKind = ownerKind;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHttp() {
		return http;
	}

	public void setHttp(String http) {
		this.http = http;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public int getMaxChildCount() {
		return maxChildCount;
	}

	public void setMaxChildCount(int maxChildCount) {
		this.maxChildCount = maxChildCount;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public long getShopId() {
		return ShopId;
	}

	public void setShopId(long shopId) {
		ShopId = shopId;
	}
}
