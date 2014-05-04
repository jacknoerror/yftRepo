package com.qfc.yft.vo;

import net.n3.nanoxml.IXMLElement;

import com.qfc.yft.utils.XMLUtil;


public class GroupList extends CimList {
	private StringList kinds = new StringList();

	public GroupList() {

		super();
	}

	protected void decodeFromRoot(IXMLElement root) {
		clear();

		IXMLElement node = XMLUtil.getChildByName(root, "groups");

		if (node != null) {

			IXMLElement catalogNodes = XMLUtil.getChildByName(root, "catalogs");
			if (catalogNodes != null) {
				for (int i = 0; i < catalogNodes.getChildrenCount(); i++) {
					IXMLElement n = catalogNodes.getChildAtIndex(i);
					if ("catalog".equals(n.getName())) {
						long kindId = Long.parseLong(n.getAttribute("id", "0"));
						String kindName = n.getAttribute("name", "");
						getKinds().put(String.valueOf(kindId), kindName);
					}
				}

			}

			for (int i = 0; i < node.getChildrenCount(); i++) {
				if ("group".equals(node.getChildAtIndex(i).getName())) {
					CimGroup group = new CimGroup();
					group.decodeFromXmlNode(node.getChildAtIndex(i));
					add(group);
				}
			}
		}

	}

	public CimGroup getGroup(int index) {
		return (CimGroup) get(index);
	}

	public CimGroup getGroupById(long id) {
		return (CimGroup) getById(id);
	}

	public StringList getKinds() {
		return kinds;
	}

}
