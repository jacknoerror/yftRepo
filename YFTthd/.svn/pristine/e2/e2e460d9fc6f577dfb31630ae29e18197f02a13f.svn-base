package com.qfc.yft.ui.tabs.chat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.qfc.yft.data.chat.CimConsts;
import com.qfc.yft.data.chat.DataManager;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.chat.CimShop;
import com.qfc.yft.vo.chat.CimUser;
import com.qfc.yft.vo.chat.FixUsersList;
import com.qfc.yft.vo.chat.ShopList;
import com.qfc.yft.vo.chat.SystemParams;
import com.qfc.yft.vo.chat.UserList;



public class BuildData {

	private static BuildData instance = null;

	public static BuildData getInstance() {
		if (instance == null) {
			instance = new BuildData();
		}
		return instance;
	}

	/*public void fillShop(ExpandableListAdapter listAdapter) {
		ShopList shopList = SystemParams.getInstance().getShops();
		for (int i = 0; i < shopList.size(); i++) {
			CimShop shop = shopList.getShop(i);
			// TreeNode node = new TreeNode(shop.getName(), true);
			// node.setData(shop);
			createShopNode(shop, listAdapter);
			// node.setExpand(true);
			// listAdapter.getRoot().add(node);
		}
	}*/

	/*public void createShopNode(CimShop shop, ExpandableListAdapter listAdapter) {
		Map<Long, TreeNode> map = new HashMap<Long, TreeNode>();
		UserList userList = shop.getUsers();
		StringList kinds = userList.getKinds();
		for (int i = 0; i < kinds.size(); i++) {
			String deptName = kinds.getValue(i);
			if (deptName.equals("") || deptName == null) {
				deptName = "未分组";
			}
			String deptId = kinds.getName(i);
			long kindId = 0L;
			if (deptId != null && !deptId.equals("")) {
				kindId = Long.parseLong(deptId);
			}

			TreeNode node = new TreeNode(deptName, true);
			map.put(kindId, node);
			listAdapter.getRoot().add(node);
		}

		for (int i = 0; i < userList.size(); i++) {
			CimUser user = userList.getUser(i);
			TreeNode pNode = map.get(user.getFriendKindId());//
			createUserNode(pNode, user);
		}
	}*/

	/**
	 * 好友节点
	 * 
	 * @param parentNode
	 * @param user
	 */
	public void createUserNode(TreeNode parentNode, CimUser user) {

		TreeNode node = new TreeNode(user.getDisplayName(), false);
		node.setFaceIndex(user.getFaceIndex());
		node.setStatus(CimConsts.UserStatus.US_OFFLINE);
		if (parentNode.getId() == -1) {
			node.setId(user.getId() * -1);
		} else {
			node.setId(user.getId());
		}
		if ("女".equals(user.getSex())) {
			node.setSex(2);
		} else {
			node.setSex(1);
		}
		node.setDescription(user.getIdiograph());
		parentNode.add(node);

	}

	public void crectUserNodes(UserList userList, long kindId, TreeNode kindNode) {
		for (int j = 0; j < userList.size(); j++) {
			CimUser user = userList.getUser(j);
			if (kindId == user.getFriendKindId()) {
				createUserNode(kindNode, user);
			}
		}
	}

	/**
	 * 好友列表
	 */
	/*public void fillFriendList(ExpandableListAdapter listAdapter) {
		UserList userList = SystemParams.getInstance().getUserList();
		StringList kinds = userList.getKinds();
		FixUsersList fixUsersList = SystemParams.getInstance()
				.getFixUsersList();
		for (int i = 0; i < kinds.size(); i++) {
			String kind = kinds.get(i);
			int index = kind.indexOf("=");
			long kindId = Long.parseLong(kind.substring(0, index));
			if (kindId == 1 || kindId > 200) {
				String kindName = kind.substring(index + 1, kind.length());
				TreeNode kindNode = new TreeNode(kindName, true);
				kindNode.setId(kindId);
				if (!(kindName.equals("黑名单") || kindName.equals("陌生人"))) {
					crectUserNodes(userList, kindId, kindNode);
					listAdapter.getRoot().add(kindNode);
				}
			}
		}
		if (fixUsersList != null) {
			TreeNode fixNode = new TreeNode("客服", true);
			listAdapter.getRoot().add(fixNode);
			if (fixUsersList.size() > 0) {
				for (int i = 0; i < fixUsersList.size(); i++) {
					CimUser user = fixUsersList.getUser(i);
					createUserNode(fixNode, user);
				}
			}
		}

		TreeNode StrangerNode = new TreeNode("陌生人", true);
		StrangerNode.setId(2);
		for (int j = 0; j < userList.size(); j++) {
			CimUser user = userList.getUser(j);
			if ("陌生人".equals(user.getFriendKindName())) {
				createUserNode(StrangerNode, user);
			}
		}
		listAdapter.getRoot().add(StrangerNode);
		TreeNode breckNode = new TreeNode("黑名单", true);
		breckNode.setId(-1);

		for (int j = 0; j < userList.size(); j++) {
			CimUser user = userList.getUser(j);
			if ("黑名单".equals(user.getFriendKindName())) {
				createUserNode(breckNode, user);
			}
		}

		listAdapter.getRoot().add(breckNode);
	}*/

	/*public void fullGroupNode(ExpandableListAdapter listAdapter) {
		GroupList groupList = SystemParams.getInstance().getGroupList();
		if (Config.bigGroup) {
			StringList kinds = groupList.getKinds();
			Map<Long, TreeNode> map = new HashMap<Long, TreeNode>();
			for (int i = 0; i < kinds.size(); i++) {
				String deptName = kinds.getValue(i);
				if (deptName.equals("") || deptName == null) {
					deptName = "未分组";
				}
				String deptId = kinds.getName(i);
				long kindId = 0L;
				if (deptId != null && !deptId.equals("")) {
					kindId = Long.parseLong(deptId);
				}
				TreeNode node = new TreeNode(deptName, true);
				node.setType(1);
				map.put(kindId, node);
				listAdapter.getRoot().add(node);
			}
			for (int i = 0; i < groupList.size(); i++) {
				CimGroup cimGroup = groupList.getGroup(i);
				if (!cimGroup.getType().equals("210")) {

					String catalogId = cimGroup.getCatalog();
					long kindId = 0L;
					if (catalogId != null && !catalogId.equals("")) {
						kindId = Long.parseLong(catalogId);
					}
					TreeNode kindNode = map.get(kindId);
					if (kindNode != null) {

						TreeNode groupNode = new TreeNode(cimGroup.getName(),
								false);
						groupNode.setId(cimGroup.getId());
						groupNode.setType(1);
						groupNode.setIndex(cimGroup.getIndex());
						groupNode.setDescription(cimGroup.getNotes());
						kindNode.add(groupNode);
					}
				}
			}

		} else {
			TreeNode mygroupNode = new TreeNode("我的群", true);
			mygroupNode.setType(1);
			for (int i = 0; i < groupList.size(); i++) {
				CimGroup cimGroup = groupList.getGroup(i);
				TreeNode groupNode = new TreeNode(cimGroup.getName(), false);
				groupNode.setId(cimGroup.getId());
				groupNode.setIndex(cimGroup.getIndex());
				groupNode.setType(1);
				groupNode.setDescription(cimGroup.getNotes());
				mygroupNode.add(groupNode);
			}
			listAdapter.getRoot().add(mygroupNode);
		}
		listAdapter.getRoot().sortByIndex();
		listAdapter.notifyDataSetChanged();
	}*/

	/*public void fullGestNode(ExpandableListAdapter listAdapter) {

		if (Config.SHOW_business) {
			TreeNode businessNode = new TreeNode("我的商机", true);
			businessNode.setId(-10L);
			businessNode.setType(CimConsts.ConnectUserType.GROUP);

			GroupList groupList = SystemParams.getInstance().getGroupList();
			for (int i = 0; i < groupList.size(); i++) {
				CimGroup cimGroup = groupList.getGroup(i);
				if (cimGroup.getType().equals("210")) {
					TreeNode groupNode = new TreeNode(cimGroup.getName(), false);
					groupNode.setId(cimGroup.getId());
					groupNode.setType(1);
					groupNode.setIndex(cimGroup.getIndex());
					groupNode.setDescription(cimGroup.getNotes());
					businessNode.add(groupNode);
				}
			}
			listAdapter.getRoot().add(businessNode);
		}

		TreeNode groupNodeY = new TreeNode("网站访客", true);
		groupNodeY.setId(42l);
		listAdapter.getRoot().add(groupNodeY);
		GuestList guests = SystemParams.getInstance().getGuestList();
		if (guests != null) {
			for (int i = 0; i < guests.size(); i++) {
				CimGuest cimGuest = guests.getGuest(i);
				TreeNode guestNode = new TreeNode("访客_" + cimGuest.getCode(),
						false);
				guestNode.setId(cimGuest.getId());
				guestNode.setType(CimConsts.ConnectUserType.GUEST);
				guestNode.setDescription(cimGuest.getAddr());
				groupNodeY.add(guestNode);
			}
		}

		listAdapter.notifyDataSetChanged();
	}*/

	/**
	 * 动态加载访客节点
	 * 
	 * groupType 1,2 1表示已接待访客分组 2表示未接待访客分组
	 * 
	 * @param node
	 */

	public void dynamicAddGuestNode(short status, long groupType, String tile,
			String description, long gusetId) {
		ExpandableListAdapter listAdapter = AllAdapterControl.getInstance()
				.getGuestExpandableListAdapter();
		TreeNode node = listAdapter.getRoot().find(groupType); // 父级分组
		TreeNode newNode = listAdapter.getRoot().find(gusetId);//
		if (node != null) {
			if (newNode == null) {
				TreeNode childreNode = new TreeNode(tile, false);
				childreNode.status = status;
				childreNode.setType(CimConsts.ConnectUserType.GUEST);
				childreNode.setDescription(description);
				childreNode.setId(gusetId);
				node.add(childreNode);
			}

		}

	}

	public List<Long> fullConversation(Context context, ConversationListAdapter listAdapter) {
		List<Long> idArr = new ArrayList<Long>();
		Cursor cur = null;
		// laUserId bigint(20) , laType int(8), laTime bigint(20) NOT NULL,
		// laMess text
		try {
			String sql = "select laUserId,laType ,laTime,laMess from cimlately  ORDER BY laTime ASC";
			cur = DataManager.getInstance(context).query(sql, null);
			if (cur != null) {
				while (cur.moveToNext()) {
					long id = cur.getLong(0);
					Log.i(getClass().getSimpleName(), "fullconversation:"+id);
					int type = cur.getInt(1);
					long time = cur.getLong(2);
					if (type == CimConsts.ConnectUserType.FRIEND
							|| type == CimConsts.ConnectUserType.GUEST) {
						idArr.add(id);
					}
					addConversationNode(listAdapter, id, type, time);//0429
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
		}

		listAdapter.getRoot().sortByTime();
		listAdapter.notifyDataSetChanged();
		return idArr;
	}

	public TreeNode addConversationNode(ConversationListAdapter listAdapter, long id,
			int type, long time) {
		TreeNode node = null;
		if (type == CimConsts.ConnectUserType.FRIEND) {
			CimUser user = getCimUser(id);
			if (user != null) {
//				if(user.getDisplayName().equals(MyData.data().getMyChatName())) return null;//TODO
				node = new TreeNode(user.getDisplayName(), false);
				node.setId(user.getId());
				node.setStatus(CimConsts.UserStatus.US_ONLINE);
				node.setFaceIndex(user.getFaceIndex());
				node.setDescription(user.getIdiograph());
				node.setType(CimConsts.ConnectUserType.FRIEND);
				node.setData(user);
				node.setTime(JackUtils.getChatTime(time));
				listAdapter.getRoot().add(node);
			}
		} else if (type == CimConsts.ConnectUserType.GROUP) {
			/*CimGroup cimGroup = getGroup(id);
			if (cimGroup != null) {
				node = new TreeNode(cimGroup.getName(), false);
				node.setId(cimGroup.getId());
				node.setType(CimConsts.ConnectUserType.GROUP);
				node.setDescription(cimGroup.getNotes());
				node.setTime(JackUtils.getChatTime(time));
				listAdapter.getRoot().add(node);
			}*/

		} else if (type == CimConsts.ConnectUserType.GUEST) {
			/*CimGuest cimGuest = getCimGuest(id);
			if (cimGuest != null) {
				node = new TreeNode("访客_" + cimGuest.getCode(), false);
				node.setId(cimGuest.getId());
				node.setStatus(CimConsts.UserStatus.US_ONLINE);
				node.setTime(JackUtils.getChatTime(time));
				node.setDescription(cimGuest.getAddr());
				node.setType(CimConsts.ConnectUserType.GUEST);
				listAdapter.getRoot().add(node);
			}*/

		}
//		else if(type == CimConsts.ConnectUserType.SHOP){//taotao 0505
//			CimShop cimShop = get
//		}
		return node;
	}

	public CimUser getCimUser(long userId) {
		CimUser cimUser = null;
		UserList userList = SystemParams.getInstance().getUserList();
		FixUsersList fixUsersList = SystemParams.getInstance()
				.getFixUsersList();
		ShopList shopList = SystemParams.getInstance().getShops();
		if (cimUser == null) {
			cimUser = (CimUser) fixUsersList.getById(userId);
			if (cimUser == null) {
				cimUser = (CimUser) userList.getById(userId);
				if (cimUser == null) {
					for (int i = 0; i < shopList.size(); i++) {
						CimShop shop = shopList.getShop(i);
						UserList users = shop.getUsers();
						cimUser = (CimUser) users.getById(userId);
					}
				}
			}
		}

		return cimUser;

	}

	/**
	 * taotao 0505
	 * @param id
	 * @return
	 */
	public CimShop getCimShop(long id){
		CimShop cimShop = null;
		ShopList shopList = SystemParams.getInstance().getShops();
		cimShop = (CimShop)shopList.getById(id);
		return cimShop;
	}
	
	/*public CimGuest getCimGuest(long id) {
		CimGuest cimGuest = null;
		GuestList guestList = SystemParams.getInstance().getGuestList();
		cimGuest = (CimGuest) guestList.getById(id);
		return cimGuest;

	}*/

	/*public CimGroup getGroup(long id) {
		GroupList groupList = SystemParams.getInstance().getGroupList();
		CimGroup cimGroup = (CimGroup) groupList.getById(id);
		return cimGroup;

	}*/

	public void addConversationlist(long id, ConversationListAdapter listAdapter) {
		CimUser user = getCimUser(id);
		if (user != null) {
			TreeNode node = new TreeNode(user.getDisplayName(), false);
			node.setId(user.getId());
			node.setFaceIndex(user.getFaceIndex());
			node.setDescription(user.getIdiograph());
			node.setData(user);
			listAdapter.getRoot().add(node);
		} else {
			/*CimGroup cimGroup = getGroup(id);
			if (cimGroup != null) {
				TreeNode groupNode = new TreeNode(cimGroup.getName(), false);
				groupNode.setId(cimGroup.getId());
				groupNode.setType(1);
				groupNode.setDescription(cimGroup.getNotes());
				listAdapter.getRoot().add(groupNode);
			}*/ 

		}
	}

	public String getUserName(long userId) {
		String userName = userId + "";

		ShopList shopList = SystemParams.getInstance().getShops();
		for (int i = 0; i < shopList.size(); i++) {
			CimShop shop = shopList.getShop(i);
			UserList userList = shop.getUsers();
			for (int j = 0; j < userList.size(); j++) {
				CimUser user = userList.getUserById(userId);
				if (user != null) {
					userName = user.getDisplayName();
					break;
				}
			}
		}
		FixUsersList fixUsersList = SystemParams.getInstance()
				.getFixUsersList();
		CimUser fixUser = fixUsersList.getUserById(userId);
		if (fixUser != null) {
			userName = fixUser.getDisplayName();
		}
		UserList userList = SystemParams.getInstance().getUserList();
		CimUser user = userList.getUserById(userId);
		if (user != null) {
			userName = user.getDisplayName();
		}

		return userName;

	}

	/*public void fillSearch(final ConversationListAdapter listAdapter, String query) {

		ShopList shopList = SystemParams.getInstance().getShops();
		for (int i = 0; i < shopList.size(); i++) {
			CimShop shop = shopList.getShop(i);
			UserList userList = shop.getUsers();
			for (int j = 0; j < userList.size(); j++) {
				CimUser user = userList.getUser(j);
				String displayNamePingYing = JackUtils.getPinYin(user
						.getDisplayName());
				if (user.getLoginId().contains(query)
						|| user.getDisplayName().contains(query)
						|| displayNamePingYing.contains(query)) {

					addNode(listAdapter, user);

				}

			}
		}
		FixUsersList fixUsersList = SystemParams.getInstance()
				.getFixUsersList();
		for (int i = 0; i < fixUsersList.size(); i++) {
			CimUser user = fixUsersList.getUser(i);
			String displayNamePingYing = JackUtils.getPinYin(user
					.getDisplayName());
			if (user.getLoginId().contains(query)
					|| user.getDisplayName().contains(query)
					|| displayNamePingYing.contains(query)) {

				addNode(listAdapter, user);

			}
		}

		UserList userList = SystemParams.getInstance().getUserList();
		for (int i = 0; i < userList.size(); i++) {
			CimUser user = userList.getUser(i);
			String displayNamePingYing = JackUtils.getPinYin(user
					.getDisplayName());
			if (user.getLoginId().contains(query)
					|| user.getDisplayName().contains(query)
					|| displayNamePingYing.contains(query)) {
				addNode(listAdapter, user);
			}
		}
		GroupList groupList = SystemParams.getInstance().getGroupList();

		for (int i = 0; i < groupList.size(); i++) {
			CimGroup cimGroup = groupList.getGroup(i);
			String displayNamePingYing = JackUtils.getPinYin(cimGroup.getName());
			if (cimGroup.getName().contains(query)
					|| cimGroup.getCode().contains(query)
					|| displayNamePingYing.contains(query)) {
				TreeNode groupNode = new TreeNode(cimGroup.getName(), false);
				groupNode.setId(cimGroup.getId());
				groupNode.setType(1);
				groupNode.setDescription(cimGroup.getNotes());
				listAdapter.getRoot().add(groupNode);
			}
		}

		if (listAdapter.getCount() == 0) {
			TreeNode node = new TreeNode("没有搜索到匹配的数据", false);
			node.setDescription("");
			node.setType(-1);
			listAdapter.getRoot().add(node);
		}

		listAdapter.notifyDataSetChanged();

	}*/

	private void addNode(final ConversationListAdapter listAdapter, CimUser user) {
		TreeNode kindNode = new TreeNode(user.getDisplayName(), false);
		kindNode.setImgUrl(user.getFaceIndex());
		kindNode.setStatus(CimConsts.UserStatus.US_ONLINE);
		kindNode.setId(user.getId());
		kindNode.setType(CimConsts.ConnectUserType.FRIEND);
		kindNode.setFaceIndex(user.getFaceIndex());
		kindNode.setDescription(user.getIdiograph());
		kindNode.setData(user);
		listAdapter.getRoot().add(kindNode);
	}

}
