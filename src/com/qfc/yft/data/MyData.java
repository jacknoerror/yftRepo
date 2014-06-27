package com.qfc.yft.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Environment;
import android.util.SparseArray;
import android.widget.TabHost;

import com.qfc.yft.ui.shop.Company;
import com.qfc.yft.vo.Category;
import com.qfc.yft.vo.LIIPeople;
import com.qfc.yft.vo.Series;
import com.qfc.yft.vo.User;


/**
 * 
 * @author taotao
 * @Date 2014-6-23
 */
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
	/*
	 * tabhost
	 */
	TabHost tabHost;
	public final TabHost getTabHost() {
		return tabHost;
	}
	public final void setTabHost(TabHost tabHost) {
		this.tabHost = tabHost;
	}
	
	/*
	 * category
	 */
//	List<Category> categoryList;
	SparseArray< Category> categoryAllMap;

	public Category getCategorySecond(int cid) {
		if(categoryAllMap==null) return null;
		return categoryAllMap.get(cid);
	}
	public void putCategory(Category cate){
		if(categoryAllMap==null) categoryAllMap = new SparseArray< Category>();
		categoryAllMap.put(cate.getCateId(), cate);
	}
	public boolean isCatInited(){
		return null!=categoryAllMap&&categoryAllMap.size()>0;
	}
	
	/*
	 * huancun
	 */
	private Map<Integer, List<Series>> allUserSeriesMap;
	private Map<Integer,Long> userSeriesTimeMap;
	private Map<Integer, List<Series>> getAllUserSeriesMap(){
		if(allUserSeriesMap==null) allUserSeriesMap = new HashMap<Integer, List<Series>>();
		return allUserSeriesMap;
	}
	private Map<Integer,Long> getUserSeriesTimeMap(){
		if(userSeriesTimeMap==null) userSeriesTimeMap = new HashMap<Integer, Long>();
		return userSeriesTimeMap;
	}
	public List<Series> getSeriesList(int shopId){
		Long thatTime = getUserSeriesTimeMap().get(shopId);
		if(thatTime==null) return null;
		if(System.currentTimeMillis()-thatTime>Const.TIMEOUT_REQUEST_10) return null;
		return getAllUserSeriesMap().get(shopId);
	}
	public void putSeriesList(int sid, List<Series> list){
		getAllUserSeriesMap().put(sid, list);
		getUserSeriesTimeMap().put(sid, System.currentTimeMillis());
	}
	
	//
	public String getMyLocalPath() {
		User user = meUser;
		if (user == null)
			return "00";
		int shid = user.getShopId();
		return Environment.getExternalStorageDirectory().getPath()
				+ "/qfc/imgs/" + shid;
	}
	
	/*
	 * companymap
	 */
	private Map<Integer, Company> companyMap; //<shopId,company>
	
	public Map<Integer, Company> getCompanyMap(){
		if(companyMap==null) companyMap = new HashMap<Integer, Company>();
		return companyMap;
	}
	
	LIIPeople peop;
	public void storePerson(LIIPeople peop) {
		if(null==peop) return;
		this.peop = peop;
//		if(null==peopleMap) peopleMap = new SparseArray<LIIPeople>();
//		peopleMap.put(peop.accountId, peop);
	}
	public LIIPeople getStoredPerson(int pid) {
//		if(null==peopleMap) return null;
//		return peopleMap.get(pid);
		return peop;
	}
}
