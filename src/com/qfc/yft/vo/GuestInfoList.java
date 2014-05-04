package com.qfc.yft.vo;

import net.n3.nanoxml.IXMLElement;

public class GuestInfoList extends CimList {

	protected void decodeFromRoot(IXMLElement root) {

		clear();

		for (int i = 0; i < root.getChildrenCount(); i++) {
			if ("user".equals(root.getChildAtIndex(i).getName())) {

				CimGuestInfo grCimGuestInfo = new CimGuestInfo();

				grCimGuestInfo.decodeFromXmlNode(root.getChildAtIndex(i));

				add(grCimGuestInfo);
			}
		}

	}

	public CimGuestInfo getGuestByCode(String code) {

		for (int i = 0; i < size(); i++) {
			if (code.equals(getGuestInfo(i).getCode()))
				return getGuestInfo(i);
		}
		return null;
	}

	public CimGuestInfo getGuestInfo(int index) {
		return (CimGuestInfo) get(index);
	}

}
