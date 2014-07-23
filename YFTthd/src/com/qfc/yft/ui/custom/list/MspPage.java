package com.qfc.yft.ui.custom.list;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.entity.json.JsonImport;


public class MspPage extends JsonImport {

	private static final String INFOARR = "result";
	private static final String HASNEXT = "hasNext";

	public int curPageNo;
	public int totalCount;

//	boolean resultSign;
	public boolean hasNext;
	JSONArray infoArr;

	private MspFactoryImpl factory;

	public MspPage(MspFactoryImpl factory){
		super();
		this.factory = factory;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(null==job) return;
		hasNext = job.optBoolean(HASNEXT);
		totalCount = job.optInt("totalCount");
		infoArr = job.optJSONArray(INFOARR);
		//currentPage?
	}
	
	
	List<MspJsonItem> LiiList;
	public  List<MspJsonItem> getDataList(){
		if(LiiList!=null) return LiiList;//如果有了，直接返回
		
		LiiList = new ArrayList<MspJsonItem>();//
		for(int i=0;i<(null!=infoArr?infoArr.length():0);i++){
			try {
				
				MspJsonItem pp = factory.getMjiInstance();
				pp.initJackJson(infoArr.getJSONObject(i));
				LiiList.add(pp);
			} catch (JSONException e) {
				e.printStackTrace();
			}//
			
		}
		return LiiList;
	}
	
	
}
