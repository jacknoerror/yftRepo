package com.qfc.yft.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.qfc.yft.CimConsts;
import com.qfc.yft.data.CachMsg;
import com.qfc.yft.data.DataManager;
import com.qfc.yft.net.chat.IWSCallback;
import com.qfc.yft.net.chat.WSCaller;
import com.qfc.yft.ui.tabs.chat.ChatMsgAdapter;
import com.qfc.yft.ui.tabs.chat.ChatMsgEntity;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.vo.CimGroup;
import com.qfc.yft.vo.CimGuest;
import com.qfc.yft.vo.CimShop;
import com.qfc.yft.vo.CimUser;
import com.qfc.yft.vo.CimWSReturn;
import com.qfc.yft.vo.GroupList;
import com.qfc.yft.vo.ShopList;
import com.qfc.yft.vo.StringList;
import com.qfc.yft.vo.SystemParams;
import com.qfc.yft.vo.UserList;


public class AllAdapterControl {
	public ExpandableListAdapter shopExpandableListAdapter;
	public ExpandableListAdapter friendExpandableListAdapter;
	public ExpandableListAdapter groupExpandableListAdapter;
	public ExpandableListAdapter guestExpandableListAdapter;
	public ConversationListAdapter conversationlistAdapter;

	private ListView chatListView;

	public ListView getChatListView() {
		return chatListView;
	}

	public void setChatListView(ListView chatListView) {
		this.chatListView = chatListView;
	}

	public ChatMsgAdapter chatListAdapter;

	public ChatMsgAdapter getChatListAdapter() {

		return chatListAdapter;
	}

	public void setChatListAdapter(ChatMsgAdapter chatListAdapter) {
		this.chatListAdapter = chatListAdapter;
	}

	public ExpandableListAdapter getShopExpandableListAdapter() {
		return shopExpandableListAdapter;
	}

	public void setShopExpandableListAdapter(
			ExpandableListAdapter shopExpandableListAdapter) {
		this.shopExpandableListAdapter = shopExpandableListAdapter;
	}

	public ExpandableListAdapter getFriendExpandableListAdapter() {
		return friendExpandableListAdapter;
	}

	public void setFriendExpandableListAdapter(
			ExpandableListAdapter friendExpandableListAdapter) {
		this.friendExpandableListAdapter = friendExpandableListAdapter;
	}

	public ExpandableListAdapter getGroupExpandableListAdapter() {
		return groupExpandableListAdapter;
	}

	public void setGroupExpandableListAdapter(
			ExpandableListAdapter groupExpandableListAdapter) {
		this.groupExpandableListAdapter = groupExpandableListAdapter;
	}

	public ExpandableListAdapter getGuestExpandableListAdapter() {
		return guestExpandableListAdapter;
	}

	public void setGuestExpandableListAdapter(
			ExpandableListAdapter guestExpandableListAdapter) {
		this.guestExpandableListAdapter = guestExpandableListAdapter;
	}

	public ConversationListAdapter getConversationlistAdapter() {
		return conversationlistAdapter;
	}

	public void setConversationlistAdapter(ConversationListAdapter conversationlistAdapter) {
		this.conversationlistAdapter = conversationlistAdapter;
	}

	/**
	 * 设置离线状态
	 */
	public void setAllOffLineStatus() {
		for (int i = 0; i < shopExpandableListAdapter.getRoot().size(); i++) {
			shopExpandableListAdapter.getRoot().get(i).setAllStatus();
		}
		shopExpandableListAdapter.notifyDataSetChanged();
		for (int i = 0; i < friendExpandableListAdapter.getRoot().size(); i++) {
			friendExpandableListAdapter.getRoot().get(i).setAllStatus();
		}
		for (int i = 0; i < guestExpandableListAdapter.getRoot().size(); i++) {
			guestExpandableListAdapter.getRoot().get(i).setAllStatus();
		}
		conversationlistAdapter.getRoot().setAllStatus();
	}

	/**
	 * 设置状态
	 * 
	 * @param userId
	 * @param status
	 */
	public void setUserStatus(long userId, short status) {
		if (friendExpandableListAdapter != null) {
			TreeNode node = friendExpandableListAdapter.getRoot().find(userId);
			if (node != null) {
				node.setStatus(status);

			}
		}
		if (shopExpandableListAdapter != null) {
			TreeNode shopNode = shopExpandableListAdapter.getRoot()
					.find(userId);
			if (shopNode != null) {
				shopNode.setStatus(status);
			}
		}
		if (guestExpandableListAdapter != null) {
			TreeNode guestNode = guestExpandableListAdapter.getRoot().find(
					userId);
			if (guestNode != null) {
				guestNode.setStatus(status);
			}
		}

		if (conversationlistAdapter != null) {
			TreeNode latelyNode = conversationlistAdapter.getRoot()
					.find(userId);
			if (latelyNode != null) {
				latelyNode.setStatus(status);
			}
		}

		sortByState();
	}

	/**
	 * 查找节点
	 * 
	 * @param userId
	 * @param status
	 */
	public TreeNode findAllNode(long userId) {
		TreeNode newNode = null;
		if (friendExpandableListAdapter != null) {
			TreeNode node = friendExpandableListAdapter.getRoot().find(userId);
			if (node != null) {
				newNode = node;
			}
		}
		if (shopExpandableListAdapter != null) {
			TreeNode shopNode = shopExpandableListAdapter.getRoot()
					.find(userId);
			if (shopNode != null) {
				newNode = shopNode;
			}
		}
		if (guestExpandableListAdapter != null) {
			TreeNode guestNode = guestExpandableListAdapter.getRoot().find(
					userId);
			if (guestNode != null) {
				newNode = guestNode;
			}
		}

		if (conversationlistAdapter != null) {
			TreeNode latelyNode = conversationlistAdapter.getRoot()
					.find(userId);
			if (latelyNode != null) {
				newNode = latelyNode;

			}
		}
		return newNode;

	}

	public void sortByState() {
		// if (conversationlistAdapter != null) {
		//
		// // conversationlistAdapter.getRoot().sortByState();
		// }
		if (shopExpandableListAdapter != null) {
			shopExpandableListAdapter.getRoot().sortByState();
		}
		if (friendExpandableListAdapter != null) {

			friendExpandableListAdapter.getRoot().sortByState();
		}
		if (guestExpandableListAdapter != null) {

			guestExpandableListAdapter.getRoot().sortByState();
		}

	}

	public void notifyDataSetChanged() {
		if (conversationlistAdapter != null) {
			conversationlistAdapter.getRoot().sortByTime();
			conversationlistAdapter.notifyDataSetChanged();
		}
		if (shopExpandableListAdapter != null) {
			shopExpandableListAdapter.notifyDataSetChanged();
		}
		if (friendExpandableListAdapter != null) {
			friendExpandableListAdapter.notifyDataSetChanged();
		}
		if (guestExpandableListAdapter != null) {
			guestExpandableListAdapter.notifyDataSetChanged();
		}

	}

	public void setSysBadge(Context context, long id, int type) {
		List<ChatMsgEntity> listMess = CachMsg.getInstance()
				.getUserChatList(id);
		CachMsg.getInstance().addCount(id);
		ChatMsgEntity chatMsgEntity = listMess.get(listMess.size() - 1);
		String mess = chatMsgEntity.getText();
		TreeNode treeNode = conversationlistAdapter.getRoot().find(id);
		if (treeNode == null) {
			TreeNode newNode = new TreeNode("系统消息", false);
			newNode.setBadge(CachMsg.getInstance().getCount(id));
			newNode.setDescription(mess);
			newNode.setId(id);
			newNode.setType(type);
			newNode.setTime(JackUtils.getDate());
			conversationlistAdapter.getRoot().add(newNode);
			setWhat(0);
		} else {
			treeNode.setDescription(mess);
			treeNode.setTime(JackUtils.getDate());
			treeNode.setBadge(CachMsg.getInstance().getCount(id));
		}

	}

	public boolean isBlacklist(long userId) {
		boolean result = false;
		if (friendExpandableListAdapter != null) {
			TreeNode treeNode = friendExpandableListAdapter.getRoot().find(
					-1 * userId);
			if (treeNode != null) {
				result = true;
			}
		}

		return result;
	}

	public void setBadge(Context context, long id, int type) {
		List<ChatMsgEntity> listMess = CachMsg.getInstance()
				.getUserChatList(id);
		CachMsg.getInstance().addCount(id);
		ChatMsgEntity chatMsgEntity = listMess.get(listMess.size() - 1);
		String mess = chatMsgEntity.getText();
		TreeNode treeNode = conversationlistAdapter.getRoot().find(id);
		if (treeNode == null) {
			treeNode = BuildData.getInstance().addConversationNode(
					conversationlistAdapter, id, type,
					System.currentTimeMillis());
			if (treeNode != null) {
				treeNode.setBadge(CachMsg.getInstance().getCount(id));
				treeNode.setStatus((short) 10);
				treeNode.setDescription(mess);
				treeNode.setTime(JackUtils.getChatTime(System
						.currentTimeMillis()));
				DataManager.getInstance(context).deleteContact(id);
				DataManager.getInstance(context).addContact(id, type);

			} else {
				if (type == CimConsts.ConnectUserType.GROUP) {
					getGroupInfo(context, mess, id);
				} else if (type == CimConsts.ConnectUserType.GUEST) {
					getGuestInfo(context, mess, id);
				} else if (type == CimConsts.ConnectUserType.FRIEND) {
					getFriendInfo(context, mess, id);

				}
			}
		} else {
			treeNode.setDescription(mess);
			treeNode.setStatus((short) 10);
			treeNode.setTime(JackUtils.getChatTime(System.currentTimeMillis()));
			treeNode.setBadge(CachMsg.getInstance().getCount(id));

		}

	}

	public void getFriendInfo(final Context context, final String mess, long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sessionId", SystemParams.getInstance().getSessionId());
		map.put("userId", id);
		WSCaller.call("getUser", map, new IWSCallback() {
			public void callback(CimWSReturn wsReturn) {
				if (wsReturn != null) {
					wsReturn.getRoot();
					CimUser cimUser = new CimUser();
					cimUser.decodeFromWSReturn(wsReturn);
					addChartDynamic(context, cimUser.getId(), mess,
							cimUser.getDisplayName(),
							CimConsts.ConnectUserType.FRIEND);
					addStrangerNode(cimUser);

				}
			}
		});
	}

	/**
	 * 动态加陌生人
	 */
	public void addStrangerNode(CimUser cimUser) {
		ExpandableListAdapter listAdapter = AllAdapterControl.getInstance()
				.getFriendExpandableListAdapter();
		if (listAdapter != null) {
			TreeNode node = listAdapter.getRoot().find(2L); // 父级分组
			if (node != null) {
				TreeNode grTreeNode = new TreeNode(cimUser.getDisplayName(),
						false);
				grTreeNode.setDescription(cimUser.getIdiograph());
				grTreeNode.setId(cimUser.getId());
				grTreeNode.setStatus(CimConsts.UserStatus.US_ONLINE);
				grTreeNode.setType(CimConsts.ConnectUserType.FRIEND);
				node.add(grTreeNode);
				setWhat(1);
			}
		}

	}

	public CimGroup getGroupInfo(final Context context, final String mess,
			long groupId) {
		CimGroup cimGroup = null;
		ShopList shopList = SystemParams.getInstance().getShops();
		for (int i = 0; i < shopList.size(); i++) {
			CimShop shop = shopList.getShop(i);
			if (shop.getId() == groupId) {
				cimGroup = new CimGroup();
				cimGroup.setId(groupId);
				cimGroup.setName(shop.getName());
				cimGroup.setNotes("");
				addChartDynamic(context, groupId, mess, shop.getName(),
						CimConsts.ConnectUserType.GROUP);
				return cimGroup;
			}
			UserList userList = shop.getUsers();
			StringList kinds = userList.getKinds();
			for (int k = 0; k < kinds.size(); k++) {
				String deptId = kinds.getName(k);
				if (deptId.equals(String.valueOf(groupId))) {
					cimGroup = new CimGroup();
					cimGroup.setId(groupId);
					cimGroup.setName(kinds.getValue(k));
					cimGroup.setNotes("");
					addChartDynamic(context, groupId, mess, kinds.getValue(k),
							CimConsts.ConnectUserType.GROUP);
					return cimGroup;
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sessionId", SystemParams.getInstance().getSessionId());
		map.put("groupId", groupId);
		WSCaller.call("getGroup", map, new IWSCallback() {
			public void callback(CimWSReturn wsReturn) {
				if (wsReturn != null) {
					wsReturn.getRoot();
					GroupList groupList = new GroupList();
					groupList.decodeFromReturn(wsReturn);
					CimGroup cimGroup = groupList.getGroup(0);
					if (cimGroup != null) {
						addGroupDynamic(cimGroup);
						addChartDynamic(context, cimGroup.getId(), mess,
								cimGroup.getName(),
								CimConsts.ConnectUserType.GROUP);
					}
				}
			}
		});
		return cimGroup;

	}

	public CimGuest getGuestInfo(final Context context, final String mess,
			long id) {
		CimGuest cimGuest = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sessionId", SystemParams.getInstance().getSessionId());
		map.put("userId", id);
		WSCaller.call("getGuest", map, new IWSCallback() {
			public void callback(CimWSReturn wsReturn) {
				if (wsReturn != null) {
					CimGuest cimGuest = new CimGuest();
					cimGuest.decodeFromWSReturn(wsReturn);
					if (cimGuest != null) {
						addChartDynamic(context, cimGuest.getId(), mess, "访客_"
								+ cimGuest.getCode(),
								CimConsts.ConnectUserType.GUEST);
					}
				}
			}
		});

		return cimGuest;

	}

	/**
	 * 动态加载会话
	 * 
	 * @param context
	 * @param id
	 * @param mess
	 * @param name
	 * @param type
	 */
	public void addChartDynamic(Context context, long id, String mess,
			String name, int type) {
		Log.i("addChartDynamic", id+"-"+mess+"-"+name);
		TreeNode newNode = new TreeNode(name, false);
		newNode.setBadge(CachMsg.getInstance().getCount(id));
		newNode.setStatus((short) 10);
		newNode.setDescription(mess);
		newNode.setId(id);
		newNode.setType(type);
		newNode.setTime(JackUtils.getChatTime(System.currentTimeMillis()));
		conversationlistAdapter.getRoot().add(newNode);
		DataManager.getInstance(context).deleteContact(id);
		DataManager.getInstance(context).addContact(id, type);
		setWhat(0);
	}

	private Handler eventHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				conversationlistAdapter.getRoot().sortByTime();
				conversationlistAdapter.notifyDataSetChanged();
			}
			if (msg.what == 1) {

				friendExpandableListAdapter.notifyDataSetChanged();
			}
		}
	};

	private void setWhat(int what) {
		eventHandler.sendEmptyMessage(what);
	}

	/**
	 * 动态加载群
	 */
	public void addGroupDynamic(CimGroup cimGroup) {
		ExpandableListAdapter listAdapter = AllAdapterControl.getInstance()
				.getGuestExpandableListAdapter();
		if (listAdapter != null) {
			TreeNode node = listAdapter.getRoot().find(-10L); // 父级分组
			if (node != null) {
				TreeNode grTreeNode = new TreeNode(cimGroup.getName(), false);
				grTreeNode.setDescription(cimGroup.getNotes());
				grTreeNode.setId(cimGroup.getId());
				grTreeNode.setType(CimConsts.ConnectUserType.GROUP);
				node.add(grTreeNode);
			}
		}

	}

	/**
	 * 动态加载访客
	 */
	public void addGuestDynamic(CimGuest cimGuest) {
		ExpandableListAdapter listAdapter = AllAdapterControl.getInstance()
				.getGuestExpandableListAdapter();
		if (listAdapter != null) {
			TreeNode node = listAdapter.getRoot().find(42L); // 父级分组
			if (node != null) {
				TreeNode grTreeNode = new TreeNode("访客_" + cimGuest.getCode(),
						false);
				grTreeNode.setDescription(cimGuest.getAddr());
				grTreeNode.setId(cimGuest.getId());
				grTreeNode.setType(CimConsts.ConnectUserType.GUEST);
				node.add(grTreeNode);
			}
		}

	}

	private static AllAdapterControl instance;

	public static AllAdapterControl getInstance() {
		if (instance == null) {
			instance = new AllAdapterControl();
		}
		return instance;
	}
}
