package com.ggwork.net.socket;

import java.util.ArrayList;
import java.util.List;

import com.qfc.yft.utils.APIDesUtils;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.vo.CimGuest;
import com.qfc.yft.vo.CimShop;
import com.qfc.yft.vo.CimUser;
import com.qfc.yft.vo.FixUsersList;
import com.qfc.yft.vo.GuestList;
import com.qfc.yft.vo.ShopList;
import com.qfc.yft.vo.SystemParams;
import com.qfc.yft.vo.UserList;


public class SocketBuild {

	// public static void static() {
	// sendMeStatus();
	// sendShopStatus();
	// sendFriendStatus();
	// sendFixUsersStatus();
	// }
	//

	public static void sendMeStatus() {
		CimSocket.getInstance().sendMsg(SocketProtocol.socketSelfStatusXml());
	}

	public static void sendAddFriendApply(long userId) {
		String remark = "<userInfo sendUserType=\"-1\" industryId=\"0\" GroupType=\"0\" IsSelfGroup=\"false\" userName=\" "
				+ SystemParams.getInstance().getUserName() + "\"/>";
		String sb = "<cim client=\"cs\" type=\"sendMessage\"><userList><user id='"
				+ userId
				+ "'/></userList><message type=\"3\" groupId=\"0\"   remark=\""
				+ new String(APIDesUtils.base64Encode(remark.getBytes()))
				+ "\" userStauts=\"10\">MDA=</message></cim>";
		CimSocket cimSocket = CimSocket.getInstance();
		cimSocket.sendMsg(sb.toString());
	}

	public static void sendUserStatus(long userId) {
		String sb = "<cim client=\"bs\" type=\"queryStatus\"><userList>"
				+ "<user id=\"" + userId + "\"/></userList></cim>";
		CimSocket cimSocket = CimSocket.getInstance();
		cimSocket.sendMsg(sb.toString());
	}

	/**
	 * 发送企业用户状态
	 * 
	 */

	public static void sendShopStatus() {
		CimShop shop = null;
		ShopList shopList = SystemParams.getInstance().getShops();
		if (shopList != null) {
			for (int i = 0; i < shopList.size(); i++) {
				shop = shopList.getShop(i);
				if (shop != null) {
					createShop(shop);
				}
			}
		}
	}

	/**
	 * 发送好友状态
	 * 
	 */

	public static void sendFriendStatus() {
		StringBuffer sb = new StringBuffer();
		sb.append("<cim client=\"cs\" type=\"queryStatus\"><userList>");
		UserList userList = SystemParams.getInstance().getUserList();
		for (int i = 0; i < userList.size(); i++) {
			CimUser user = userList.getUser(i);
			sb.append("<user id=\"");
			sb.append(user.getId());
			sb.append("\"/>");
		}
		sb.append("</userList></cim>");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CimSocket.getInstance().sendMsg(sb.toString());
		// Log.d("send", sb.toString());
	}

	public static void sendFixUsersStatus() {

		StringBuffer sb = new StringBuffer();
		sb.append("<cim client=\"cs\" type=\"queryStatus\"><userList>");
		FixUsersList fixUsersList = SystemParams.getInstance()
				.getFixUsersList();
		for (int i = 0; i < fixUsersList.size(); i++) {
			CimUser user = fixUsersList.getUser(i);
			sb.append("<user id=\"");
			sb.append(user.getId());
			sb.append("\"/>");
		}
		sb.append("</userList></cim>");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CimSocket.getInstance().sendMsg(sb.toString());
	}

	public static void sendGuestStatus() {
		StringBuffer sb = new StringBuffer();
		sb.append("<cim client=\"cs\" type=\"queryStatus\"><userList>");
		GuestList userList = SystemParams.getInstance().getGuestList();
		for (int i = 0; i < userList.size(); i++) {
			CimGuest user = userList.getGuest(i);
			sb.append("<user id=\"");
			sb.append(user.getId());
			sb.append("\"/>");
		}
		sb.append("</userList></cim>");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CimSocket.getInstance().sendMsg(sb.toString());
		// Log.d("send", sb.toString());
	}

	public static void createShop(CimShop cimShop) {
		List<Long> vrUser = new ArrayList<Long>();
		UserList userList = cimShop.getUsers();
		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				CimUser user = userList.getUser(i);
				vrUser.add(user.getId());
			}
		}
		StringBuffer sb = new StringBuffer();
		if (vrUser.size() > 0) {
			sb.append("<cim client=\"bs\" type=\"queryStatus\"><userList>");
			boolean bigger = false;
			int j = 0;
			for (int i = 0; i < vrUser.size(); i++) {
				if (!bigger && vrUser.size() - i < 20) {
					sb.append("<user id=\"" + vrUser.get(i) + "\"/>");
					if (vrUser.size() == i + 1) {
						sb.append("</userList></cim>");
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						CimSocket.getInstance().sendMsg(sb.toString());
					}
				} else {
					bigger = true;
					j++;
					sb.append("<user id=\"" + vrUser.get(i) + "\"/>");
					if (j == 20) {
						sb.append("</userList></cim>");
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}
						CimSocket.getInstance().sendMsg(sb.toString());
						sb.setLength(0);
						sb.append("<cim client=\"bs\" type=\"queryStatus\"><userList>");
						j = 0;
						bigger = false;
					}

				}

			}
		}
	}

	public static void sendConversation(List<Long> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("<cim client=\"cs\" type=\"queryStatus\"><userList>");
		for (Long id : list) {
			sb.append("<user id=\"");
			sb.append(id);
			sb.append("\"/>");
		}
		sb.append("</userList></cim>");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CimSocket.getInstance().sendMsg(sb.toString());
	}                                                                                                                                          

}
