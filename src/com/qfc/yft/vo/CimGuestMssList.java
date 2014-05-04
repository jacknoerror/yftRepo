package com.qfc.yft.vo;

import com.qfc.yft.utils.XMLUtil;

import net.n3.nanoxml.IXMLElement;


public class CimGuestMssList extends GuestInfoList {
	public CimGuestMssList() {

		super();
	}

	protected void decodeFromRoot(IXMLElement root) {
		clear();
		IXMLElement node = XMLUtil.getChildByName(root, "infos");
		if (node == null)
			return;

		for (int i = 0; i < node.getChildrenCount(); i++) {
			if ("info".equals(node.getChildAtIndex(i).getName())) {
				GuestMess mess = new GuestMess();
				mess.decodeFromXmlNode(node.getChildAtIndex(i));
				add(mess);

			}
		}
	}

	public GuestMess getGuestMess(int index) {
		return (GuestMess) get(index);
	}

}
