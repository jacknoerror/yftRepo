package com.qfc.yft.vo;

import net.n3.nanoxml.IXMLElement;

public class GuestList extends CimList {
	protected void decodeFromRoot(IXMLElement root) {
		for (int i = 0; i < root.getChildrenCount(); i++) {
			if ("user".equals(root.getChildAtIndex(i).getName())) {
				CimGuest guest = new CimGuest();
				guest.decodeFromXmlNode(root.getChildAtIndex(i));
				add(guest);
			}
		}
	}

	public CimGuest getGuest(int index) {
		return (CimGuest) get(index);
	}

	public CimGuest getGuestByCode(String code) {

		for (int i = 0; i < size(); i++) {
			if (code.equals(getGuest(i).getCode()))
				return getGuest(i);
		}
		return null;
	}

}
