package com.qfc.yft.vo.chat;

import net.n3.nanoxml.IXMLElement;

import com.qfc.yft.data.chat.CimConsts;
import com.qfc.yft.util.XMLUtil;


public class UserList extends CimList {
	private StringList kinds = new StringList();

	protected void decodeFromRoot(IXMLElement root) {
		decodeUsers(root, "friends");

		// decodeUsers(root, "fixusers");

	}

	public void decodeUsers(IXMLElement root, String nodeName) {
		clear();
		IXMLElement node = XMLUtil.getChildByName(root, "user");
		if (node != null) {
			decodeUser(node);
		}

		node = XMLUtil.getChildByName(root, nodeName);
		if (node == null)
			node = root;
		decodeFriends(node);
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

	public void decodeFriends(IXMLElement node) {
		interDecodeUsers(node, "friendKind");
	}

	public void decodeShopUsers(IXMLElement node) {
		interDecodeUsers(node, "dept");
	}

	public void decodeGroupUsers(IXMLElement node) {
		interDecodeUsers(node, "@");
	}

	private void interDecodeUsers(IXMLElement node, String nodeName) {
		clear();
		for (int i = 0; i < node.getChildrenCount(); i++) {
			IXMLElement n = node.getChildAtIndex(i);
			if (nodeName.equals(n.getName())) {
				long kindId = Long.parseLong(n.getAttribute("id", "0"));
				String kindName = n.getAttribute("name", "");
				getKinds().put(String.valueOf(kindId), kindName);
				for (int j = 0; j < n.getChildrenCount(); j++) {
					if ("user".equals(n.getChildAtIndex(j).getName())) {
						CimUser user = decodeUser(n.getChildAtIndex(j));
						if (user != null) {
							user.setFriendKindId(kindId);
							user.setFriendKindName(kindName);
						}
					}
				}
			} else if ("user".equals(n.getName())) {
				CimUser user = decodeUser(n);
				if (user != null) {
					user.setFriendKindId(0);
					user.setFriendKindName("");
				}
			}
		}
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

	public CimUser getUserById(long userId) {

		return (CimUser) getById(userId);

	}
}
