package com.qfc.yft.vo.chat;

import net.n3.nanoxml.IXMLElement;

import com.qfc.yft.util.XMLUtil;


public abstract class CimAbstractVo {
	private long id;

	private String name;

	public void setId(long aId) {
		id = aId;
	}

	public long getId() {
		return id;
	}

	public void setName(String aName) {
		name = aName;
	}

	public String getName() {
		return name;
	}

	public void decodeFromXml(String xmlStr)  {
		IXMLElement root;
		try {
			root = XMLUtil.loadFromStr(xmlStr);
			decodeFromXmlRoot(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void decodeFromWSReturn(CimWSReturn wsReturn) {
		if (wsReturn.getCode() == 0) {
			decodeFromXmlRoot(wsReturn.getRoot());
		}
	}

	public abstract void decodeFromXmlRoot(IXMLElement root);

	public abstract void decodeFromXmlNode(IXMLElement node);
}
