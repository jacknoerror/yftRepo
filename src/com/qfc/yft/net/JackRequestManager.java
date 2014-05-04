package com.qfc.yft.net;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask.Status;
import android.util.Log;
/**
 * mainly prevent requesting repeatedly
 * @author taotao
 *
 */
public class JackRequestManager {
	private final String TAG  = JackRequestManager.class.getSimpleName();
	
	private static JackRequestManager manager;
	private Map<String, Long> requestTimeMap;
	private Map<String, HttpRequestTask> requestTaskMap;
	
	
	private JackRequestManager(){
		requestTimeMap = new HashMap<String, Long>();
		requestTaskMap = new HashMap<String, HttpRequestTask>();
	};
	public static JackRequestManager getInstance(){
		if(manager==null) manager= new JackRequestManager();
		return manager;
	}
	
	public void tryRequest(String url,HttpReceiver receiver, long timeout){//TODO
		Log.i(TAG, "sure it works?"+url);
		HttpRequestTask rTask =  requestTaskMap.get(url);
		if(rTask==null){
			requestAnyway(url, receiver);
		}else if(rTask.getStatus()==Status.FINISHED){
			long thatTime = requestTimeMap.get(url);
			long delTime =System.currentTimeMillis()-thatTime; 
			Log.i(TAG , delTime+"::request deltime");
			if(delTime>timeout){//一定时间外继续请求
				requestAnyway(url, receiver);
			}
		}
	}
	public void tryRequest(String url,HttpReceiver receiver ){
		tryRequest(url, receiver, 3000);
	}
	
	private void requestAnyway(String url,HttpReceiver receiver){
		HttpRequestTask rTask = new HttpRequestTask(receiver);
		rTask.execute(url);
		requestTaskMap.put(url, rTask);
		requestTimeMap.put(url, System.currentTimeMillis());
	}
}
