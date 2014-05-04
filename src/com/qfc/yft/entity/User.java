package com.qfc.yft.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
 /*
  * {"id":36607,"hasHall":0,"email":"365626589@qq.com","shopId":14843,
  * "userName":"ydspipad1","memberType":3,
  * "realName":"郭靖","companyId":14843,
  * "mobile":"13588104052"}
  */
	
	int id;
	boolean hall;
	String email="";
	int shopId;
	String userName="";
	int memberType;
	String realName="";
	int companyId;
	String mobile="";
	
	private String[] keys= new String[]{"id","hasHall","email","shopId","userName","memberType","realName","companyId","mobile"};
	/**
	 * 初始化user各值
	 * @return 是否成功init
	 */
	public boolean initWithJsonString(String json){
//		JSONArray jArray;
		JSONObject jObject;
		try {
//			jArray = new JSONArray(json.replace('}', ']').replace('{', '['));
			jObject =new JSONObject(json);
			int cur=0;
			if(jObject.has(keys[cur++]))this.id = jObject.getInt(keys[cur-1]);
			if(jObject.has(keys[cur++]))this.hall=(jObject.getInt(keys[cur-1])==1);
			if(jObject.has(keys[cur++]))this.email=jObject.getString(keys[cur-1]);
			if(jObject.has(keys[cur++]))this.shopId=jObject.getInt(keys[cur-1]);
			if(jObject.has(keys[cur++]))this.userName=jObject.getString(keys[cur-1]);
			if(jObject.has(keys[cur++]))this.memberType=jObject.getInt(keys[cur-1]);
			if(jObject.has(keys[cur++]))this.realName=jObject.getString(keys[cur-1]);
			if(jObject.has(keys[cur++]))this.companyId=jObject.getInt(keys[cur-1]);
			if(jObject.has(keys[cur++]))this.mobile=jObject.getString(keys[cur-1]);
			//这里顺序不能改
			if(shopId==0) shopId = companyId ;//TODO temp ,better idea?
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isHall() {
		return hall;
	}
	public void setHall(boolean hall) {
		this.hall = hall;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getMemberType() {
		return memberType;
	}
	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
