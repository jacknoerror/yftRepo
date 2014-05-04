package com.qfc.yft.vo;

import java.util.ArrayList;



public class SystemParams {
	private String version;
	private String softDownloadUrl;

	public String getSoftDownloadUrl() {
		return softDownloadUrl;
	}

	public void setSoftDownloadUrl(String softDownloadUrl) {
		this.softDownloadUrl = softDownloadUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	private long userId;
	private String loginId;

	public String getLoginId() {

		// if (loginId != null && loginId.indexOf("@") > 0) {
		// loginId = loginId.substring(0, loginId.indexOf("@"));
		//
		// }
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	private String userName;
	private String faceIndex;

	public String getFaceIndex() {
		return faceIndex;
	}

	public void setFaceIndex(String faceIndex) {
		this.faceIndex = faceIndex;
	}

	private short status = 10;

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	private String sex = "";

	private String userCode;

	private String userPwd;

	private String time;

	private String sessionId;

	private GuestMss guestMss = new GuestMss();

	private ShopList shops = new ShopList();

	private UserList userList = new UserList();
	private FixUsersList fixUsersList = new FixUsersList();

	public FixUsersList getFixUsersList() {
		return fixUsersList;
	}

	public void setFixUsersList(FixUsersList fixUsersList) {
		this.fixUsersList = fixUsersList;
	}

	private GuestList guestList = new GuestList();

	private GuestInfoList cimGuestInfo = new GuestInfoList();

	private CimGuestMssList guestMssList = new CimGuestMssList();

	private GroupList groupList = new GroupList();

	private GroupUserList groupUserList = new GroupUserList();
	private LookupUserList lookupUserList = new LookupUserList();
	ArrayList<CimColumn> cimColumnList = new ArrayList<CimColumn>();

	public ArrayList<CimColumn> getCimColumnList() {
		return cimColumnList;
	}

	public void setCimColumnList(ArrayList<CimColumn> cimColumnList) {
		this.cimColumnList = cimColumnList;
	}

	public LookupUserList getLookupUserList() {
		return lookupUserList;
	}

	public void setLookupUserList(LookupUserList lookupUserList) {
		this.lookupUserList = lookupUserList;
	}

	private static SystemParams instance;

	public static SystemParams getInstance() {
		if (instance == null) {
			instance = new SystemParams();
		}
		return instance;
	}

	public void clear() {
		instance = null;
	}

	public static void setSystemParams() {

		instance = null;

	}

	private SystemParams() {
	}

	public void debug() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public void decodeFromLoginReturn(CimWSReturn wsReturn) {

		userId = wsReturn.getId();
		sessionId = wsReturn.getSessionId();
		userName = wsReturn.getUserName();
		faceIndex = wsReturn.getFaceIndex();
		loginId = wsReturn.getLoginId();
		time = wsReturn.getTime();
		getUserList().decodeFromReturn(wsReturn);
		getShops().decodeFromReturn(wsReturn);
		getFixUsersList().decodeFromReturn(wsReturn);

	}

	public String getTime() {
		return time;
	}

	public ShopList getShops() {
		return shops;
	}

	public UserList getUserList() {
		return userList;
	}

	public String getSessionId() {
		return sessionId;
	}

	public GuestList getGuestList() {
		return guestList;
	}

	public void setGuestList(GuestList guestList) {
		this.guestList = guestList;
	}

	public String getShopName() {
		if (shops != null && shops.size() > 0) {
			return ((CimShop) shops.getShop(0)).getName();

		}

		else {
			return "";

		}

	}

	public long getShopId() {
		// TODO 自动生成方法存根
		if (shops != null && shops.size() > 0) {
			return ((CimShop) shops.getShop(0)).getId();

		}

		else {
			return 0;
		}

	}

	public GuestInfoList getCimGuestInfo() {
		return cimGuestInfo;
	}

	public void setCimGuestInfo(GuestInfoList cimGuestInfo) {
		this.cimGuestInfo = cimGuestInfo;
	}

	public GuestMss getGuestMss() {
		return guestMss;
	}

	public void setGuestMss(GuestMss guestMss) {
		this.guestMss = guestMss;
	}

	public long getUserId() {

		return userId;

	}

	public String getUserName() {
		return userName;
	}

	public String getSex() {
		return sex;
	}

	public CimGuestMssList getGuestMssList() {
		return guestMssList;
	}

	public void setGuestMssList(CimGuestMssList guestMssList) {
		this.guestMssList = guestMssList;
	}

	public GroupList getGroupList() {
		return groupList;
	}

	public void setGroupList(GroupList groupList) {
		this.groupList = groupList;
	}

	public GroupUserList getGroupUserList() {
		return groupUserList;
	}

	public void setGroupUserList(GroupUserList groupUserList) {
		this.groupUserList = groupUserList;
	}

}
