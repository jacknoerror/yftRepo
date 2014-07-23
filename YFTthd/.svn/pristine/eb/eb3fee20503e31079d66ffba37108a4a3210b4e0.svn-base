package com.qfc.yft.data;

import android.widget.TabHost;

import com.qfc.yft.vo.User;


public class MyData {
	private static MyData mydata;
	private MyData(){}
	public static MyData data(){
	   if(null==mydata){
	      mydata = new MyData();
	   }
	   return mydata;
	}

	/*
	 * user
	 */
	private User currentUser,meUser;
	private String userCode,SessionId;
	public boolean isMe(){
		return currentUser==meUser;
	}
	public void setMeCurrentUser(){
		this.currentUser = meUser;
	}
	public User getCurrentUser() {
		return currentUser;
	}
	public void setMe(User user){
		this.meUser = user;
	}
	public User getMe(){
		return meUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	public String getUserCode() {
		if(null==userCode) return "";//
		return userCode;
	}
	public void setUserCode(String usercode){
		this.userCode = usercode;
	}
	public final String getSessionId() {
		if(null==SessionId) SessionId="";//
		return SessionId;
	}
	public final void setSessionId(String sessionId) {
		SessionId = sessionId;
	}

	TabHost tabHost;
	public final TabHost getTabHost() {
		return tabHost;
	}
	public final void setTabHost(TabHost tabHost) {
		this.tabHost = tabHost;
	}
	
	
	
}
