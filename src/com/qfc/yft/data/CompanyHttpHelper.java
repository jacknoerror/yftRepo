package com.qfc.yft.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.Company;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.offline.IOfflineConst;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.net.HttpUpdater;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.utils.TestDataTracker;

public class CompanyHttpHelper {
	final String TAG = "data_fetcher";
	
	public User dfUser;
	public Company dfCompany;
	
	HttpUpdater dfUpdater;
	
	
	public CompanyHttpHelper(HttpUpdater httpUp){
		this.dfUpdater = httpUp;
		this.dfUser = YftData.data().getCurrentUser();
	}
	
	public void onResume() {
		
		dfCompany = 	YftData.data().getCompanyMap().get(dfUser.getShopId()); //≈–∂œ «∑Ò“—”–
		if(dfCompany==null){
			String prefResponse = YftData.data().getOffStr(IOfflineConst.PREFOFF_SHOPINFO);
			if(prefResponse.isEmpty()||!YftData.data().isMe()){
				new HttpRequestTask(dfUpdater).execute(YftValues.getHTTPBodyString(RequestType.SHOP_INFO, dfUser.getShopId()+""));
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
	
	public void onResponse(String result) {
		try {
			JSONObject job = YftValues.getResultObject(result);
			if(job!=null){
				
				dfCompany = new Company(job);
				YftData.data().getCompanyMap().put(dfUser.getShopId(), dfCompany);
				
				dfUpdater.updateUI();
			}else{
				JackUtils.showToast(dfUpdater.getReceiverContext(), result.substring(Math.min(result.length(), 20)));
			}
		} catch (JSONException e) {
			Log.i(TAG, "response json wrong!");
			e.printStackTrace();
		}
//		TestDataTracker.settleDataString(RequestType.SHOP_INFO.toString(), result);//
		
	}
}
