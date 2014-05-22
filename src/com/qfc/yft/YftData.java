package com.qfc.yft;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.ggwork.net.socket.SocketBuild;
import com.qfc.yft.entity.Category;
import com.qfc.yft.entity.Company;
import com.qfc.yft.entity.Series;
import com.qfc.yft.entity.SimpleCompany;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.listitem.LIIPeople;
import com.qfc.yft.entity.listitem.LIIProduct;
import com.qfc.yft.entity.offline.OfflineData;
import com.qfc.yft.entity.page.QfcPageInfo;
import com.qfc.yft.ui.AllAdapterControl;
import com.qfc.yft.ui.BuildData;
import com.qfc.yft.ui.ConversationListAdapter;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;
import com.qfc.yft.utils.JackUtils;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TabHost;

public class YftData {
	private static YftData yData;
	private YftData(){};
	public static YftData data(){
		if(yData==null){
			yData = new YftData();
		}
		return yData;
	}
	
	public void destroy(){
		yData=null;
	}
	
	private Map<Integer, Company> companyMap; //<shopId,company>
	
	public Map<Integer, Company> getCompanyMap(){
		if(companyMap==null) companyMap = new HashMap<Integer, Company>();
		return companyMap;
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
	 * user
	 */
	private User currentUser,meUser;
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
	/*
	 * offline
	 */
	private SharedPreferences offlinePreference;
	private OfflineData offlineData;
	public String getOffStr(String key){
		String result="";
		if(offlinePreference!=null) result = offlinePreference.getString(key, "");
		return result;
	}
	public void setOfflineEnable(boolean enable){
		Editor editor = getOffEditor();
		if(editor!=null) editor.putBoolean("enable", enable).commit();
	}
	
	public boolean isOfflineEnabled(){
		SharedPreferences pref = getOffPref();
		if(pref!=null){
			return pref.getBoolean("enable", false);
		}
		return false;
	}
	
	public SharedPreferences getOffPref(){
		return offlinePreference;
	}
	public void setOffPref(SharedPreferences pref){//mainactivity oncreate inited
		this.offlinePreference = pref;
	}
	public OfflineData getOfflineData(){
		if(offlineData==null) {
			SharedPreferences pref = getOffPref();
			if(pref!=null){
				String json = pref.getString(meUser.getShopId()+"", "");
				if(!json.isEmpty()){
					try {
						offlineData = new OfflineData(new JSONObject(json));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} 
		}
		return offlineData;//null?
	}
	
	private Editor offlineEditor;
	public Editor getOffEditor()	{
		if(offlineEditor==null&&offlinePreference!=null){
			offlineEditor = offlinePreference.edit();
		}
		return offlineEditor;
	}
	public void commitOffPref(){
		commitOffPref(meUser.getShopId()+"", offlineData.toJsonStr());
	}
	public boolean commitOffPref(String key,String value){
		if(isOffDatCommittable())  return getOffEditor().putString(key, value).commit();
		return false;
	}
	private boolean isOffDatCommittable(){
		return offlinePreference!=null&&offlineData!=null&&meUser!=null&&meUser.getShopId()>0;
	}
	public void setOffline(OfflineData od){
		this.offlineData = od;
	}
	public boolean clearOffPref(){
		offlineData=null;
		return getOffEditor().clear().commit();
	}
	
	/*store*/
	SparseArray<LIIProduct> productMap;
	SparseArray<LIIPeople> peopleMap;
	SparseArray<SimpleCompany> scMap;
	public void storeShopById(SimpleCompany lc) {//0318
		if(null==scMap) scMap = new SparseArray<SimpleCompany>();
		scMap.put(lc.userId, lc);
	}
	public SimpleCompany getStoredShop(int scuid){
		if(null==scMap) return null;
		return scMap.get(scuid);
	}
	public void storeProduct(LIIProduct product) {
		if(null==productMap) productMap = new SparseArray<LIIProduct>();
		productMap.put(product.getProductId(), product);
	}
	public LIIProduct getStoredProduct(int pid){
		if(null==productMap) return null;
		return productMap.get(pid);
	}
	public void storePerson(LIIPeople peop) {
		if(null==peop) return;
		if(null==peopleMap) peopleMap = new SparseArray<LIIPeople>();
		peopleMap.put(peop.accountId, peop);
	}
	public LIIPeople getStoredPerson(int pid) {
		if(null==peopleMap) return null;
		return peopleMap.get(pid);
	}
	
	/*host*/
	TabHost mTabHost;
	public void setHostTab(TabHost mTabHost) {
		this.mTabHost = mTabHost;
	}
	public TabHost getHostTab(){
		return mTabHost;
	}
	
	//timeout store
	private Map<String, QfcPageInfo> pageInfoMap;
	private Map<String,Long> pageInfoTimeMap;
	public QfcPageInfo getPageInfo(int shopId,int seriesId,int page){//1203
		if(pageInfoMap==null||pageInfoTimeMap==null) return null;
		String key = getPageInfoKey(shopId, seriesId, page);
		Long thatTime = pageInfoTimeMap.get(key);
		if(thatTime==null) return null;
		if(System.currentTimeMillis()-thatTime>YftValues.TIMEOUT_REQUEST) return null;
		return pageInfoMap.get(key);
	}
	public void addPageInfo(int shopId,int seriesId,int page,QfcPageInfo pi){
		if(pi==null) return;
		if(pageInfoMap==null) pageInfoMap = new HashMap<String, QfcPageInfo>();
		if(pageInfoTimeMap==null) pageInfoTimeMap = new HashMap<String, Long>();
		String key = getPageInfoKey(shopId, seriesId, page);
		pageInfoMap.put(key, pi);
		pageInfoTimeMap.put(key, System.currentTimeMillis());
		
	}
	private String getPageInfoKey(int shopId,int sid,int page){
		return shopId+"_"+sid+"_"+page;
	}
	
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
		if(System.currentTimeMillis()-thatTime>YftValues.TIMEOUT_REQUEST_10) return null;
		return getAllUserSeriesMap().get(shopId);
	}
	public void putSeriesList(int sid, List<Series> list){
		getAllUserSeriesMap().put(sid, list);
		getUserSeriesTimeMap().put(sid, System.currentTimeMillis());
	}
	NotificationManager nm;
	public NotificationManager getNotificationManager() {
		if(null==nm)nm = (NotificationManager)YftApplication.getApp(). getSystemService(Context.NOTIFICATION_SERVICE);
		return nm;
	}
	ConversationListAdapter convListAdapter;
	public ConversationListAdapter getConvListAdapter() {
		if(null==convListAdapter){
			convListAdapter = new ConversationListAdapter(YftApplication.getApp());
			//2.AllAdapterControl
			AllAdapterControl.getInstance().setConversationlistAdapter(convListAdapter);
			//3.buildData
			final List<Long> idArrs = BuildData.getInstance().fullConversation(YftApplication.getApp(), convListAdapter);
			new Thread() {
				public void run() {
					SocketBuild.sendConversation(idArrs);
				}
			}.start();
		}
		return convListAdapter;
	}
	
	String myChatName;
	public void setMyChatName(String userName) {
		this.myChatName = userName;
		
	}
	public String getMyChatName(){
		return myChatName!=null?myChatName:""	;
	}
	
	
	public boolean hasFZL(){
		User user = getMe();
		return null!=user&&user.getMemberType()>2;
	}
}
