package com.qfc.yft.data;

import com.qfc.yft.vo.User;

public class TestConst {

	public static final String usercode = "de63e674ab5c4204988f01a89714efe2";//ydspipad1
	public static final int compId = 14843;
	public static final int userId = 36607;
	public static final int albumId = 5069;

	public static User getTestUser(){
		User user = new User();
		user .setShopId(compId);
		user.setCompanyId(compId);
		user.setId(36607);
		return user;
	}
	
}
