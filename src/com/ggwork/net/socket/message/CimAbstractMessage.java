package com.ggwork.net.socket.message;

import net.n3.nanoxml.IXMLElement;

import com.qfc.yft.util.XMLUtil;


public abstract class CimAbstractMessage {
	/** ��Ϣ���� */
	private String type = "";
	/** ������� */
	private short errCode = 0;
	/**
	 * ������Ϣ
	 * 
	 * */
	private String errMsg = "";

	public CimAbstractMessage() {

	}

	/**
	 * ��Xml������
	 * 
	 * @param xmlStr
	 * @throws Exception
	 */
	public void decodeFromXml(String xmlStr)  {
		IXMLElement root;
		try {
			root = XMLUtil.loadFromStr(xmlStr);
			decodeFromXmlRoot(root);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��Xml������
	 * 
	 * @param xmlStr
	 * @throws Exception
	 */
	public final void decodeFromXmlRoot(IXMLElement root) {
		decodeRootNode(root);
		for (int i = 0; i < root.getChildrenCount(); i++) {
			decodeNode(root.getChildAtIndex(i));
		}
	}

	/**
	 * �������ڵ�
	 * 
	 * @param node
	 */
	protected void decodeRootNode(IXMLElement node) {
		if ("cim".equals(node.getName())) {
			type = node.getAttribute("type", "");
			errCode = (short) node.getAttribute("errorCode", 0);
			errMsg = node.getAttribute("errorMsg", "");
		}
	}

	/**
	 * �����ӽڵ�
	 * 
	 * @param node
	 */
	protected abstract void decodeNode(IXMLElement node);

	/**
	 * ����Xml��
	 * 
	 * @return
	 */
	public String toXmlStr() {
		return null;
	}

	public void setType(String aType) {
		type = aType;
	}

	public String getType() {
		return type;
	}

	public void setErrCode(short aErrCode) {
		errCode = aErrCode;
	}

	public short getErrCode() {
		return errCode;
	}

	public void setErrMsg(String aErrMsg) {
		errMsg = aErrMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}
}
