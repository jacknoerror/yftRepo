package com.qfc.yft.util;

import java.util.Vector;

import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.IXMLParser;
import net.n3.nanoxml.IXMLReader;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLParserFactory;

public class XMLUtil {

	public static IXMLElement loadFromStr(String xmlStr) throws Exception {
		IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
		IXMLReader r = StdXMLReader.stringReader(xmlStr);
		parser.setReader(r);
		IXMLElement root = (IXMLElement) parser.parse();
		return root;
	}

	public static IXMLElement getChildByName(IXMLElement pNode, String name) {
		Vector<?> children = pNode.getChildrenNamed(name);
		if (children != null && children.size() > 0)
			return (IXMLElement) children.elementAt(0);
		return null;
	}
}
