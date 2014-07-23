package com.qfc.yft.vo.chat;

import net.n3.nanoxml.IXMLElement;

import com.qfc.yft.util.XMLUtil;


public class ShopList extends CimList {
	protected void decodeFromRoot(IXMLElement root) {
		clear();
		IXMLElement node = XMLUtil.getChildByName(root, "shops");
		if (node == null)
			return;

		for (int i = 0; i < node.getChildrenCount(); i++) {
			if ("shop".equals(node.getChildAtIndex(i).getName())) {
				CimShop shop = new CimShop();
				shop.decodeFromXmlNode(node.getChildAtIndex(i));
				add(shop);

			}
		}
	}

	public CimShop getShop(int index) {
		return (CimShop) get(index);
	}

	public CimUser getUserById(long userId) {
		for (int i = 0; i < size(); i++) {
			CimUser user = getShop(i).getUserById(userId);

			if (user != null)
				return user;
		}

		return null;
	}

}
