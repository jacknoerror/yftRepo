package com.qfc.yft.ui.custom.list;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.album.SearchAlbumReq;
import com.qfc.yft.net.action.member.SearchManageProductReq;
import com.qfc.yft.ui.adapter.mj.ListAdapterAlbumLc;
import com.qfc.yft.ui.adapter.mj.ListAdapterAlbumSh;
import com.qfc.yft.ui.adapter.mj.ListAdapterCompany;
import com.qfc.yft.ui.adapter.mj.ListAdapterImagine;
import com.qfc.yft.ui.adapter.mj.ListAdapterPeople;
import com.qfc.yft.ui.adapter.mj.ListAdapterProduct;
import com.qfc.yft.ui.adapter.mj.ListAdapterProductManage;
import com.qfc.yft.ui.adapter.mj.ListAdapterSearchHistory;
import com.qfc.yft.ui.custom.list.MyJackListView.OnGetPageListener;
import com.qfc.yft.util.TestDataTracker;
import com.qfc.yft.vo.Album;
import com.qfc.yft.vo.AlbumPic;
import com.qfc.yft.vo.LIICompany;
import com.qfc.yft.vo.LIIProduct;
import com.qfc.yft.vo.ProductManage;
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
		case CHOOSE:
			adapter = new ListAdapterAlbumLc();
			break;
		case PM:
			adapter = new ListAdapterProductManage();
			break;
		case ITEMTYPE_PRODUCT_SEARCH:
			adapter = new ListAdapterProduct();
			break;
		case ITEMTYPE_COMPANY_SEARCH:
			adapter = new ListAdapterCompany();
			break;
		case ITEMTYPE_PEOPLE_SEARCH:
			adapter = new ListAdapterPeople();
			break;
		case ITEMTYPE_IMAGINE:
			adapter = new ListAdapterImagine();
			break;
		case ITEMTYPE_LOCALHISTORY:
			adapter=  new ListAdapterSearchHistory( );
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
		case CHOOSE:
			mji = new Album();
			break;
		case APIC:
			mji = new AlbumPic();
			break;
		case PM:
			mji = new ProductManage();
			break;
		case ITEMTYPE_PRODUCT_SEARCH:
			mji = new LIIProduct();
			break;
		case ITEMTYPE_COMPANY_SEARCH:
			mji = new LIICompany();
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
		case CHOOSE:
//		case ITEMTYPE_PRODUCT_SEARCH:
			listener = new MspDefaultOnGetPageListener();
			break;
		// // already add , test it
		// break;
		default:
			break;
		}
		return listener;
	}
	
	class MspDefaultOnGetPageListener implements OnGetPageListener{

		@Override
		public void page(MyJackListView qListView, int pageNo) {
			User me = MyData.data().getMe();
			ActionRequestImpl req = null;
			switch (type) {
				case ALBUM:
				case CHOOSE:
					if (null == me)	return;
					req = new SearchAlbumReq(me.getShopId(),pageNo, NetConst.DEFULAT_PAGESIZE);
					ActionBuilder.getInstance().request(req, qListView);
					break;
				/*case ITEMTYPE_PRODUCT_SEARCH:
					req
					ActionBuilder.getInstance().request(req, qListView);
					break;*/
				default:
					break;
			}
//					 TestDataTracker.simulateConnection(qListView,req.getApiName());// delete me
			
		}
		
		
	}

}
