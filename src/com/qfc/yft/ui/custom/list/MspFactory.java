package com.qfc.yft.ui.custom.list;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.album.SearchAlbumReq;
import com.qfc.yft.ui.adapter.mj.ListAdapterAlbumSh;
import com.qfc.yft.ui.custom.list.MyJackListView.OnGetPageListener;
import com.qfc.yft.vo.AlbumInShop;
import com.qfc.yft.vo.User;

public class MspFactory implements MspFactoryImpl {

	ListItemImpl.Type type;

	public MspFactory(ListItemImpl.Type type) {
		this.type = type;
	}

	@Override
	public MspAdapter getNewAdapter() {
		MspAdapter adapter = null;
		switch (type) {
		case ALBUM:
			 adapter = new ListAdapterAlbumSh();
			break;
		default:
			break;
		}
		return adapter;
	}

	@Override
	public MspPage getMspPage(JSONObject job) {
		if (job == null)
			return null;
		MspPage mp = new MspPage(this);
		try {
			mp.initJackJson(job);
		} catch (JSONException e) {
			Log.e("MspFactory", "msppage initjson error");
		}
		return mp;
	}

	/*
	 * set if have pages
	 */
	@Override
	public MspJsonItem getMjiInstance() {
		MspJsonItem mji = null;
		switch (type) {
		case ALBUM:
			mji = new AlbumInShop();
			break;
		default:
			break;
		}
		return mji;
	}

	/*
	 * set if have pages
	 * 
	 */
	@Override
	public OnGetPageListener getDefaultOnPageChangeListener() {
		OnGetPageListener listener = null;
		switch (type) {
		case ALBUM:
			listener = new OnGetPageListener() {

				@Override
				public void page(MyJackListView qListView, int pageNo) {
					User me = MyData.data().getMe();
					if (null == me)
						return;
					ActionRequestImpl req = new SearchAlbumReq(me.getShopId(),
							pageNo, NetConst.DEFULAT_PAGESIZE);
					ActionBuilder.getInstance().request(req, qListView);
					// TestDataTracker.simulateConnection(qListView,req.getApiName());
				}
			};
			break;
		// case GOODS:
		// // already add , test it
		// break;
		default:
			break;
		}
		return listener;
	}

	/**
	 * not in use in QFC projects
	 * 
	 * @return
	 */
	@Override
	public String getPageName() {
		String name = "";
		switch (type) {
		case ALBUM:
			// name = "orders"; //
			break;

		default:
			break;
		}
		return name;
	}

}
