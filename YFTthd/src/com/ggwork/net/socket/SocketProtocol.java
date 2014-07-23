package com.ggwork.net.socket;

import com.qfc.yft.data.chat.CimConsts;
import com.qfc.yft.util.APIDesUtils;
import com.qfc.yft.vo.chat.SystemParams;


/**
 * socket消息协议
 * 
 * @author zw.bai
 * @date 2013-5-10下午3:40:15
 */
public class SocketProtocol {

	public final String SOCKET_LOGIN = "";
	public final String SOCKET_SATUATED = "";

	public static String scoketLogInXML(String sessionId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<cim client='cs' type='login'><user sessionId='");
		sb.append(sessionId);
		sb.append("' status='" + SystemParams.getInstance().getStatus()
				+ "'/></cim>");
		return sb.toString();
	}

	public static String socketSaturatedXml(String sessionId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<cim client='cs' type='check' resp='1'><user sessionId='");
		sb.append(sessionId);
		sb.append("' status='10'/></cim>");
		return sb.toString();

	}

	public static String socketSelfStatusXml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<cim client=\"cs\" type=\"setStatus\"><user status=\"");
		sb.append(SystemParams.getInstance().getStatus());
		sb.append("\"/></cim>");
		return sb.toString();
	}

	public static void socketSendMessXml(long id, int type, String mess) {
		StringBuffer sb = new StringBuffer();

		String remark = "<userInfo sendUserType=\"3\" userName=\""
				+ SystemParams.getInstance().getUserName() + "\"/>";
		if (type == CimConsts.ConnectUserType.FRIEND) {
			sb.append("<cim client=\"cs\" type=\"sendMessage\"><userList><user id='");
			sb.append(id)
					.append("'/></userList>")
					.append("<message type=\"" + CimConsts.MessageType.MT_CHAT
							+ "\" groupId=\"0\" userStauts=\"10\"  remark=\""
							+ APIDesUtils.base64Encode(remark.getBytes()) + "\">");
			sb.append(APIDesUtils.base64Encode(mess.getBytes()));
			sb.append("</message></cim>");

		} else if (type == CimConsts.ConnectUserType.GROUP) {

			sb.append("<cim client=\"cs\" type=\"sendMessage\">    ");
			sb.append("<message type=\"" + CimConsts.MessageType.MT_BIG_GROUP
					+ "\" groupId=\"" + id + "\" userStauts=\"10\"  remark=\""
					+ APIDesUtils.base64Encode(remark.getBytes()) + "\">");
			sb.append(APIDesUtils.base64Encode(mess.getBytes()));
			sb.append("</message></cim>");

		} else if (type == CimConsts.ConnectUserType.GUEST) {

			sb.append("<cim client=\"cs\" type=\"sendMessage\"><userList><user id='");
			sb.append(id)
					.append("'/></userList>")
					.append("<message type=\"" + CimConsts.MessageType.MT_CHAT
							+ "\" groupId=\"0\" userStauts=\"10\">")
					.append(APIDesUtils.base64Encode(mess.getBytes()))
					.append("</message></cim>");

		} else if (type == CimConsts.MessageType.MT_WEB_CUSTOM_FACE_SENT) {

			sb.append("<cim client=\"cs\" type=\"sendMessage\"><userList><user id='");
			sb.append(id)
					.append("'/></userList>")
					.append("<message type=\"" + type
							+ "\" groupId=\"0\" userStauts=\"10\"  remark=\""
							+ APIDesUtils.base64Encode(remark.getBytes()) + "\">");
			sb.append(APIDesUtils.base64Encode(mess.getBytes())).append("</message></cim>");

		} else if (type == CimConsts.MessageType.MT_WEB_CUSTOM_FACE_SENT) {

		}

		CimSocket.getInstance().sendMsg(sb.toString());

	}

	public static void socketAddTempGroupXml(long id, int type, String mess) {
		StringBuffer sb = new StringBuffer();
		sb.append("<cim client=\"cs\" type=\"sendMessage\">");
		sb.append("<message type=\"" + CimConsts.MessageType.MI_TEMP_ADD_GROUP
				+ "\" groupId=\"" + id + "\" userStauts=\"10\">");
		sb.append(APIDesUtils.base64Encode(mess.getBytes()));
		sb.append("</message></cim>");
		CimSocket.getInstance().sendMsg(sb.toString());
	}

	public static void socketOutTempGroupXml(long id, int type, String mess) {
		StringBuffer sb = new StringBuffer();
		sb.append("<cim client=\"cs\" type=\"sendMessage\">");
		sb.append("<message type=\"" + CimConsts.MessageType.MI_TEMP_OUT_GROUP
				+ "\" groupId=\"" + id + "\" userStauts=\"10\">");
		sb.append(APIDesUtils.base64Encode(mess.getBytes()));
		sb.append("</message></cim>");
		CimSocket.getInstance().sendMsg(sb.toString());
	}

	public static void socketAddFriend() {
		StringBuffer sb = new StringBuffer();
		sb.append("<cim client=\"cs\" type=\"recvMessage\">");
		sb.append("<user id=\"1322657711058\" status=\"10\"/>");
		sb.append("<message type=\"4\" groupId=\"0\" remark=\"\" time=\"20130614112220\">QA==</message></cim>");
	}
}
