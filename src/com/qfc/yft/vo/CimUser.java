package com.qfc.yft.vo;

import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.utils.XMLUtil;

import net.n3.nanoxml.IXMLElement;


public class CimUser extends CimAbstractUser {
	private UserList users = new UserList();

	private long friendKindId;

	private String friendKindName;

	private short groupUserType;

	private String loginId;

	private String DisplayLoginId;

	private String nickName;

	private String realName;
	private String faceIndex;

	public String getFaceIndex() {
		return faceIndex;
	}

	public void setFaceIndex(String faceIndex) {
		this.faceIndex = faceIndex;
	}

	private String sex;

	private String sexName;

	private short shopUserType;

	private int smsCount;

	private double money;

	private String idiograph;

	private short friendVerifyType;

	private String notes;

	private String mobile;

	private short status;

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public void decodeFromXmlNode(IXMLElement node) {
		setId(Long.parseLong(node.getAttribute("id", "0")));
		setName(node.getAttribute("name", ""));
		loginId = node.getAttribute("loginId", "");
		nickName = node.getAttribute("nickname", "");
		faceIndex = node.getAttribute("faceIndex", "");

		realName = node.getAttribute("realname", "");

		sex = node.getAttribute("sex", "");
		setIdiograph(node.getAttribute("idiograph", ""));
		groupUserType = Short.parseShort(node
				.getAttribute("groupUserType", "0"));
		shopUserType = Short.parseShort(node.getAttribute("shopUserType", "0"));
		smsCount = Integer.parseInt(node.getAttribute("smsCount", "0"));
		money = Double.parseDouble(node.getAttribute("cimMoney", "0.0"));
		setFriendVerifyType(Short.parseShort(node.getAttribute(
				"friendVerifyType", "0")));
		setNotes(node.getAttribute("notes", ""));
		setMobile(node.getAttribute("mobile", ""));
	}

	public void decodeFromXmlRoot(IXMLElement root) {
		IXMLElement node = XMLUtil.getChildByName(root, "user");
		if (node != null)
			decodeFromXmlNode(node);
	}

	public String getDisplayLoginId() {
		return DisplayLoginId;
	}

	public String getDisplayName() {
		String name = "";
		if (JackUtils.isBlank(realName)) {
			if (JackUtils.isBlank(nickName)) {
				int index = loginId.indexOf("@");
				if (index > 0) {
					loginId = loginId.substring(0, index);
					name = loginId;
				} else {
					name = loginId;
				}
			} else {
				name = nickName;
			}
		} else {
			name = realName;
		}
		return name;

	}

	public long getFriendKindId() {
		return friendKindId;
	}

	public String getFriendKindName() {

		if (friendKindName == null || friendKindName.equals("")) {
			friendKindName = "Î´·Ö×é";
		}
		return friendKindName;
	}

	public short getFriendVerifyType() {
		return friendVerifyType;
	}

	public short getGroupUserType() {
		return groupUserType;
	}

	public String getIdiograph() {
		return idiograph;
	}

	public String getLoginId() {
		int index = loginId.indexOf("@");
		if (index > 0) {
			loginId = loginId.substring(0, index);
		}
		return loginId;
	}

	public String getMobile() {
		return mobile;
	}

	public double getMoney() {
		return money;
	}

	public String getNickName() {
		return nickName;
	}

	public String getNotes() {
		return notes;
	}

	public String getRealName() {
		return realName;
	}

	public String getSex() {
		return sex;
	}

	public String getSexName() {
		return sexName;
	}

	public short getShopUserType() {
		return shopUserType;
	}

	public int getSmsCount() {
		return smsCount;
	}

	public UserList getUsers() {
		return users;
	}

	public void setDisplayLoginId(String aDisplayLoginId) {
		DisplayLoginId = aDisplayLoginId;
	}

	public void setFriendKindId(long aFriendKindId) {
		friendKindId = aFriendKindId;
	}

	public void setFriendKindName(String aFriendKindName) {
		friendKindName = aFriendKindName;
	}

	public void setFriendVerifyType(short friendVerifyType) {
		this.friendVerifyType = friendVerifyType;
	}

	public void setGroupUserType(short aGroupUserType) {
		groupUserType = aGroupUserType;
	}

	public void setIdiograph(String idiograph) {
		this.idiograph = idiograph;
	}

	public void setLoginId(String aLoginId) {

		loginId = aLoginId;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setMoney(double aMoney) {
		money = aMoney;
	}

	public void setNickName(String aNickName) {
		nickName = aNickName;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setRealName(String aRealName) {
		realName = aRealName;
	}

	public void setSex(String aSex) {
		sex = aSex;
	}

	public void setSexName(String aSexName) {
		sexName = aSexName;
	}

	public void setShopUserType(short aShopUserType) {
		shopUserType = aShopUserType;
	}

	public void setSmsCount(int aSmsCount) {
		smsCount = aSmsCount;
	}

	public void setUsers(UserList users) {
		this.users = users;
	}
}
