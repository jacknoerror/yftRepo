package com.qfc.yft.vo.chat;

import net.n3.nanoxml.IXMLElement;

import com.qfc.yft.util.JackUtils;
import com.qfc.yft.util.XMLUtil;


public class CimWSReturn {
	private int code = 0;
	private String message = "";
	private String sessionId = "";
	private long id = 0L;
	private String time = "";
	private String version = "";
	private String userName = "";
	private IXMLElement root = null;
	private String faceIndex = "";
	private String loginId = "";
	private String password = "";

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getFaceIndex() {
		return faceIndex;
	}

	public void setFaceIndex(String faceIndex) {
		this.faceIndex = faceIndex;
	}

	public CimWSReturn(String xmlStr) throws Exception {

		if (xmlStr == null || xmlStr.indexOf("<cim>") < 0) {
			code = 9;
			message = "登录信息有问题" + xmlStr;
		} else {
			root = XMLUtil.loadFromStr(xmlStr);
			IXMLElement node = XMLUtil.getChildByName(getRoot(), "result");

			IXMLElement node2 = XMLUtil.getChildByName(getRoot(), "user");
			if (node != null) {
				code = Integer.parseInt(node.getAttribute("code", "0"));
				message = node.getAttribute("msg", "");
				if (code == 0) {
					time = node.getAttribute("serverTime", "");
					sessionId = node.getAttribute("sessionid", "");
					version = node.getAttribute("softVersion", "");
				}
				if (node2 != null) {
					id = Long.parseLong(node2.getAttribute("id", "0"));
					userName = node2.getAttribute("nickname", "");
					faceIndex = node2.getAttribute("faceIndex", "");
					loginId = node2.getAttribute("loginId", "");

				}

			} else {
				code = 99;
			}
		}

	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getSessionId() {
		return sessionId;
	}

	public long getId() {
		return id;
	}

	public String getTime() {
		return time;
	}

	public String getVersion() {
		return version;
	}

	public IXMLElement getRoot() {
		return root;
	}

	public boolean isSuccess() {
		return (code == 0 || code == 1);
	}

	public String getUserName() {

		if (JackUtils.isBlank(userName)) {
			int index = loginId.indexOf("@");
			if (index > 0) {
				loginId = loginId.substring(0, index);
			}
			return loginId;
		}

		else {
			return userName;
		}

	}

}
