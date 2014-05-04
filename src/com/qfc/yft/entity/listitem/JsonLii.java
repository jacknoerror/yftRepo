package com.qfc.yft.entity.listitem;

import org.json.JSONObject;

import com.qfc.yft.entity.JackJson;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;

public abstract class JsonLii extends JackJson implements ListItemImpl {

	JSONObject jliJob;
	
	@Override
	public JSONObject toJsonObj() {
		return null!=jliJob?jliJob:new JSONObject();
	}


}
