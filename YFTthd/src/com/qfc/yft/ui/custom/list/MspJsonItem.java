package com.qfc.yft.ui.custom.list;

import org.json.JSONObject;

import com.qfc.yft.entity.json.JsonImport;


/**
 *VOs that play roles in MSP are supposed to extends this class
 * @author taotao
 */
public abstract class MspJsonItem extends JsonImport implements ListItemImpl {

	public MspJsonItem() {
		super();
	}

	public MspJsonItem(JSONObject job) {
		super(job);
	}


}
