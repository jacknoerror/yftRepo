package com.qfc.yft.net.action.product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.qfc.yft.data.MyData;
import com.qfc.yft.net.action.ActJobRcv;
import com.qfc.yft.vo.Category;

public final class FindAllWithJSONRcv extends ActJobRcv {
	public FindAllWithJSONRcv(Context context) {
		super(context);
	}

	@Override
	public boolean response(String result) throws JSONException {
		boolean response = super.response(result);
		if(response){
			JSONArray root = resultJob.getJSONArray("ROOT");
			for(int i=0;i<root.length();i++){//¼Óµ½yftData
				Category cat = new Category();
				JSONObject catJob = root.getJSONObject(i);
				cat.initJackJson(catJob);
				MyData.data().putCategory(cat);
			}
		}
		return response;
	}
}