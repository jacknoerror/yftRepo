package com.qfc.yft.vo;

import net.n3.nanoxml.IXMLElement;

public class CimGroupUser extends CimAbstractVo {
	private long id;
	private String loginId;
	private String nickname;
	private String realname;
	private String sex;
	private String idiograph;
	private String faceIndex;
	private String groupUserType;

	public void decodeFromXmlNode(IXMLElement node) {
		// <user id="282686136894776" loginId="100361@1681860.net"
		// nickname="" realname="" sex="δ֪" idiograph="" faceIndex="2"
		// groupUserType="0"/>

		setId(Long.parseLong(node.getAttribute("id", "0")));
		setLoginId(node.getAttribute("loginId", ""));
		setNickname(node.getAttribute("nickname", ""));
		setRealname(node.getAttribute("realname", ""));
		setSex(node.getAttribute("sex", ""));
		setIdiograph(node.getAttribute("idiograph", ""));
		setFaceIndex(node.getAttribute("faceIndex", ""));
		setGroupUserType(node.getAttribute("groupUserType", ""));

	}

	public void decodeFromXmlRoot(IXMLElement root) {

	}

	public String getFaceIndex() {
		return faceIndex;
	}

	public String getGroupUserType() {
		return groupUserType;
	}

	public long getId() {
		return id;
	}

	public String getIdiograph() {
		return idiograph;
	}

	public String getLoginId() {
		boolean isBake = false;
		int index = 0;
		for (int i = 0; i < loginId.length(); i++) {
			if (loginId.charAt(i) == '@') {
				isBake = true;
				index = i;
				break;
			}
		}
		if (isBake) {
			loginId = loginId.substring(0, index);
		}

		return loginId;
	}

	public String getNickname() {
		String result = "";
		if (nickname.equals("")) {
			result = loginId;
		} else {
			result = nickname;
		}
		return result;
	}

	public String getRealname() {
		return realname;
	}

	public String getSex() {
		return sex;
	}

	public void setFaceIndex(String faceIndex) {
		this.faceIndex = faceIndex;
	}

	public void setGroupUserType(String groupUserType) {
		this.groupUserType = groupUserType;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIdiograph(String idiograph) {
		this.idiograph = idiograph;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
