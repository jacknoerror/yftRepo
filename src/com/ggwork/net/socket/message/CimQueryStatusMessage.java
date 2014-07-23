package com.ggwork.net.socket.message;

import java.util.Vector;

import net.n3.nanoxml.IXMLElement;

public class CimQueryStatusMessage extends CimAbstractMessage {
	private Vector<UserStatus> userList = new Vector<UserStatus>();
	/**
	 * ½âÎö½Úµã
	 */
	protected void decodeNode(IXMLElement node){
		String nodeName = node.getName();
		if ("userList".equals(nodeName)) {
			for (int i = 0; i < node.getChildrenCount(); i++) {
				IXMLElement e = node.getChildAtIndex(i);
				if ("user".equals(e.getName())) {
					userList.addElement(new UserStatus(
							Long.parseLong(e.getAttribute("id", "0")), 
							e.getAttribute("status", 0)));
				}
			}
		}
	}
	
	public int getUserCount() {
		return userList.size();
	}

	public UserStatus getUserStatus(int index) {
		return (UserStatus)userList.elementAt(index);
	}
}
