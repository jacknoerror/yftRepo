package com.qfc.yft.vo;

import com.qfc.yft.CimConsts;
import com.qfc.yft.utils.XMLUtil;

import net.n3.nanoxml.IXMLElement;


public class FixList extends CimList{
	private StringList kinds = new StringList();

	protected void decodeFromRoot(IXMLElement root) {
		//decodeUsers(root, "fixusers");
		
//		clear();
//		IXMLElement node = XMLUtil.getChildByName(root, "shops");
//		if (node == null)
//			return;
//
//		for (int i = 0; i < node.getChildrenCount(); i++) {
//			if ("shop".equals(node.getChildAtIndex(i).getName())) {
//				CimShop shop = new CimShop();
//				shop.decodeFromXmlNode(node.getChildAtIndex(i));
//				add(shop);
//
//			}
//		}
		
		
		clear();
		IXMLElement node = XMLUtil.getChildByName(root, "fixusers");
		if(node == null)
			return;
		for (int i = 0; i < node.getChildrenCount(); i++) {
			
			if("user".equals(node.getChildAtIndex(i).getName())){
				CimUser cimUser = new CimUser();
				cimUser.decodeFromXmlNode(node.getChildAtIndex(i));
				add(cimUser);
			}
		
		}
		
	}

	
	

	public CimUser decodeUser(IXMLElement node) {
		if (node == null)
			return null;
		CimUser user = new CimUser();
		user.decodeFromXmlNode(node);
		int index = getIndexById(user.getId());
		if (index < 0) {
			add(user);
		} else {
			set(user, index);
		}
		return user;
	}
	
	
	public CimUser getUserById(long userId) {
		return (CimUser) getById(userId);
	}
	
	public CimUser getUser(int index) {
		return (CimUser) get(index);
	}

	public void allOffLine() {
		for (int i = 0; i < size(); i++) {
			getUser(i).setStatus(CimConsts.UserStatus.US_OFFLINE);
		}
	}

	public void setUserStatus(long id, short status) {
		CimUser user = (CimUser) getById(id);
		if (user != null)
			user.setStatus(status);
	}

	public void freshUser(long id, CimWSReturn wr) {
		CimUser user = (CimUser) getById(id);
		if (user != null)
			user.decodeFromXmlRoot(wr.getRoot());
	}

	public StringList getKinds() {
		return kinds;
	}

}
