package com.qfc.yft.net.action;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;



public class ActionBuilder {
	
	private Map<String, ActionRequestImpl> actingMap ;
	
	private static ActionBuilder ab;
	private ActionBuilder(){
		actingMap = new HashMap<String, ActionRequestImpl>();
	}
	public static ActionBuilder getInstance(){
		if(null==ab){
			ab = new ActionBuilder();
		}
		return ab;
	}
	
	public void request(ActionRequestImpl actReq, ActionReceiverImpl actRcv ){
		//TODO map?
//		new HttpRequestTask(actRcv).execute(actReq.toHttpBody());
//		TestDataTracker.simulateConnection(actRcv, actReq.getApiName());
	}
	
	/**
	 *获得服务器时间 
	 */
	/*public void getTime(final DummiListener dListener ){
		new HttpRequestTask(new ActionReceiverImpl() {
			
			@Override
			public boolean response(String result) throws JSONException {
				try{
					long svTime = Long.parseLong(result);
					if(svTime>0)PLData.data().setApiTimeLag(svTime-System.currentTimeMillis());//
//					return false;
				}catch(Exception e){
					Log.e("getTime", "not getting the time");
				}
				if(null!=dListener) dListener.go(null);
				return false;//无论成功
			}
			
			@Override
			public Context getReceiverContext() {
				return null ;
			}
		}).execute("",NetConst._URL_GETTIME);
	}*/
	
}
