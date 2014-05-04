package com.ggwork.net.socket.message;

import net.n3.nanoxml.IXMLElement;
import android.util.Log;

import com.qfc.yft.CimConsts;
import com.qfc.yft.ui.BuildData;
import com.qfc.yft.utils.APIDesUtils;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.utils.XMLUtil;


/**
 * 文字消息
 * 
 * @author Administrator
 * 
 */
public class CimChatMessage extends CimAbstractMessage {
	private long fromUserId = 0;
	private long peerUserId = 0;
	private long groupId = 0;
	private long shopId = 0;
	private short kind = CimConsts.MessageType.MT_NONE;
	private String html;
	private String time;
	private short status = CimConsts.UserStatus.US_ONLINE;
	private String remark;

	public CimChatMessage() {
		super();
	}

	/**
	 * 解析节点
	 */
	protected void decodeNode(IXMLElement node) {
		String nodeName = node.getName();
		if ("user".equals(nodeName)) {
			fromUserId = Long.parseLong(node.getAttribute("id", "0"));
			status = (short) node.getAttribute("status", 0);
		} else if ("message".equals(nodeName)) {
			kind = (short) node.getAttribute("type", 0);
			time = node.getAttribute("time", "");
			if (time.length() <= 14) {
				time = JackUtils.parseDateTime1(node.getAttribute("time", ""));
			}
			Log.e("decodeNode", (node==null)+":::node nil?");
			remark = new String(APIDesUtils.base64Decode(node.getAttribute("remark", "0")));
			html = new String(APIDesUtils.base64Decode(node.getContent()));
			if (CimConsts.MessageType.isWebMessage(kind)) {
				shopId = groupId;
				groupId = 0;
			} else {
				groupId = Long.parseLong(node.getAttribute("groupId", "0"));
			}
		}
	}

	public String getRemark() {
		String name = "";
		try {
			IXMLElement element = XMLUtil.loadFromStr(remark);
			if (element != null) {
				name = element.getAttribute("userName", "");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (name.trim().equals("")) {

			name = BuildData.getInstance().getUserName(fromUserId);

		}
		return name;
	}

	public void setFromUserId(long aFromUserId) {
		fromUserId = aFromUserId;
	}

	public long getFromUserId() {
		return fromUserId;
	}

	public void setPeerUserId(long aPeerUserId) {
		peerUserId = aPeerUserId;
	}

	public long getPeerUserId() {
		return peerUserId;
	}

	public void setGroupId(long aGroupId) {
		groupId = aGroupId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setShopId(long aShopId) {
		shopId = aShopId;
	}

	public long getShopId() {
		return shopId;
	}

	public short getKind() {
		return kind;
	}

	public String endTest(String html) {
		String res = "";
		if (html.indexOf("$}") >= 0 || html.indexOf(".gif\">") >= 0) {
			if (html.indexOf("$}") >= 0 && html.indexOf(".gif\">") >= 0
					&& html.indexOf("$}") < html.indexOf(".gif\">")) {
				res = res
						+ "["
						+ html.substring(html.indexOf("$}") + 2,
								html.indexOf(".gif")) + "]";
				html = html.substring(html.indexOf(".gif"));
			} else {
				if (html.indexOf("<img") >= 0) {
					res = res
							+ html.substring(html.indexOf(".gif\">") + 6,
									html.indexOf("<img"));
					html = html.substring(html.indexOf("<img") + 4);
				} else {

				}
			}
			endTest(html);
		} else {

		}

		return res;

	}

	public String getHtml() {
		Log.d("cimchatmessage", html);
		html = JackUtils.repaceMess(html);
		return html;
	}

	public String getText() {
		// Log.d("------->html", html.toString());
		// StringBuffer sb = new StringBuffer();
		// if (html != null) {
		// int step = 0;
		// int pos = 0;
		// char ch;
		// for (int i = 0; i < html.length(); i++) {
		// ch = html.charAt(i);
		// if (step == 0) {
		// if (ch == '<') {
		// step = 1;
		//
		// } else if (ch == '&') {
		// step = 2;
		// pos = i + 1;
		// } else {
		// sb.append(ch);
		// }
		// } else if (step == 1) {
		//
		// if (ch == '>') {
		// step = 0;
		// } else if (ch == '}') {
		// pos = i;
		// } else if (ch == '.') {
		// String token = html.substring(pos + 1, i);
		// if (CimUtils.isInteger(token)) {
		// sb.append("^" + token + "`");
		// }
		// }
		// } else if (step == 2) {
		// if (ch == ';') {
		// step = 0;
		// if (i - pos >= 2) {
		// if (html.charAt(pos) == '#') {
		// ch = (char) Integer.parseInt(html.substring(
		// pos + 1, i));
		// sb.append(ch);
		// } else {
		// String token = html.substring(pos, i);
		// if ("amp".equals(token)) {
		// sb.append('&');
		// } else if ("nbsp".equals(token)) {
		// sb.append(' ');
		// } else if ("lt".equals(token)) {
		// sb.append('<');
		// } else if ("gt".equals(token)) {
		// sb.append('>');
		// }
		// }
		// }
		// }
		// }
		// }
		// }

		// }
		Log.d("----", html);
		return html.replace("{$USER_IMAGE_PATH$}", "").replaceAll("gif", "jpg");
	}

	public String getTime() {

		return time;
	}

	public short getStatus() {
		return status;
	}
}
