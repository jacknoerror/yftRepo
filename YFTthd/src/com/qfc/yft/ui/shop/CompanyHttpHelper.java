package com.qfc.yft.ui.shop;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.qfc.yft.data.MyData;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.shop.GetShopAndCompanyByIdReq;
import com.qfc.yft.ui.offline.OfflineDataKeeper;
import com.qfc.yft.ui.offline.entity.IOfflineConst;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.User;

public class CompanyHttpHelper {
	final String TAG = "data_fetcher";
	
	public User dfUser;
	public Company dfCompany;
	
	HttpUpdater dfUpdater;
	
	
	public CompanyHttpHelper(HttpUpdater httpUp){
		this.dfUpdater = httpUp;
		this.dfUser = MyData.data().getCurrentUser();
	}
	
	public void onResume() {
		
		dfCompany = 	MyData.data().getCompanyMap().get(dfUser.getShopId()); //≈–∂œ «∑Ò“—”–
		if(dfCompany==null){
			String prefResponse = OfflineDataKeeper.getOffStr(IOfflineConst.PREFOFF_SHOPINFO);
			if(prefResponse.isEmpty()||!MyData.data().isMe()){
//				new HttpRequestTask(dfUpdater).execute(YftValues.getHTTPBodyString(RequestType.SHOP_INFO, dfUser.getShopId()+""));
				ActionBuilder.getInstance().request(new GetShopAndCompanyByIdReq(dfUser.getShopId()), dfUpdater);
			}else{
				try {
					dfUpdater.response(prefResponse);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}else{
			dfUpdater.updateUI();
		}
	}
	/*public void onResume(){
		TestDataTracker.simulateConnection(dfUpdater, RequestType.SHOP_INFO.toString());
	}*/
	
	public boolean onResponse(String result) {
		try {
			JSONObject job = ActionStrategies.getResultObject(result);
			if(job!=null){
				
				dfCompany = new Company(job);
				MyData.data().getCompanyMap().put(dfUser.getShopId(), dfCompany);
				
				dfUpdater.updateUI();
				return true;
			}else{
//				JackUtils.showToast(dfUpdater.getReceiverContext(), result.substring(Math.min(result.length(), 20)));
			}
		} catch (JSONException e) {
			Log.i(TAG, "response json wrong!");
			e.printStackTrace();
		}
		return false;
//		TestDataTracker.settleDataString(RequestType.SHOP_INFO.toString(), result);//
		
	}
}
