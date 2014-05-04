package com.qfc.yft.vo;

import net.n3.nanoxml.IXMLElement;

import com.qfc.yft.utils.XMLUtil;


public class GroupUserList extends CimList{
	public GroupUserList(){
		super();
	}

	protected void decodeFromRoot(IXMLElement root) {
		clear();
		IXMLElement node = XMLUtil.getChildByName(root, "group");
		if (node == null)
			return;

		for (int i = 0; i < node.getChildrenCount(); i++) {
			if ("user".equals(node.getChildAtIndex(i).getName())) {
				CimGroupUser groupUser = new CimGroupUser();
				groupUser.decodeFromXmlNode(node.getChildAtIndex(i));
				add(groupUser);

			}
		}
		
		
	}
	public CimGroupUser getGroupUser(int index) {
		return (CimGroupUser) get(index);
	}

}
