package com.ggwork.net.socket.message;

import java.util.Hashtable;

import net.n3.nanoxml.IXMLElement;

import com.qfc.yft.utils.XMLUtil;


public class CimMessageFactory {
	private static final Hashtable<String, CimMessageFactory> ht;
	// private static final String pxmlStr;
	static {
		ht = new Hashtable<String, CimMessageFactory>();
		ht.put("recvMessage", new CimMessageFactory() {
			public CimAbstractMessage create() {
				return new CimChatMessage();
			}
		});
		ht.put("login", new CimMessageFactory() {
			public CimAbstractMessage create() {
				return new CimLoginMessage();
			}
		});

		ht.put("queryStatus", new CimMessageFactory() {
			public CimAbstractMessage create() {
				return new CimQueryStatusMessage();
			}
		});
		ht.put("checkResult", new CimMessageFactory() {
			public CimAbstractMessage create() {
				return new SaturationMessage();
			}

		});
	}

	private CimMessageFactory() {
	}

	public CimAbstractMessage create() {
		return null;
	}

	public static CimAbstractMessage createMessage(String xmlStr) {
		CimAbstractMessage result = null;

		try {
			IXMLElement root = XMLUtil.loadFromStr(xmlStr);
			CimMessageFactory factory = (CimMessageFactory) ht.get(root
					.getAttribute("type", ""));

			if (factory != null) {
				CimAbstractMessage cam = factory.create();
				cam.decodeFromXmlRoot(root);
				result = cam;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}

}
