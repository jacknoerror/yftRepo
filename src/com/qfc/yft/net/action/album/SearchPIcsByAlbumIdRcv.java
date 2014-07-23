package com.qfc.yft.net.action.album;

import java.util.List;

import org.json.JSONException;

import android.content.Context;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.ui.custom.list.MspFactory;
import com.qfc.yft.ui.custom.list.MspJsonItem;
import com.qfc.yft.ui.custom.list.MspPage;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;

public class SearchPIcsByAlbumIdRcv implements ActionReceiverImpl {

	protected MspPage page;
	protected List<MspJsonItem> dataList;

	Context context;
	
	
	public SearchPIcsByAlbumIdRcv(Context context) {
		super();
		this.context = context;
	}

	@Override
	public boolean response(String result) throws JSONException {
		page = new MspPage(new MspFactory(Type.APIC));
		page.initJackJson(ActionStrategies.getResultObject(result));
		dataList = page.getDataList();
		return dataList.size()>0;
	}

	@Override
	public Context getReceiverContext() {
		return context;
	}

}
