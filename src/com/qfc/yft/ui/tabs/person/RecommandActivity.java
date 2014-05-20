package com.qfc.yft.ui.tabs.person;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.listitem.LIICompany;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.current.CurrentPersonActivity;
import com.qfc.yft.ui.custom.list.JackListView;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;
import com.qfc.yft.ui.shop.ShopActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RecommandActivity extends MyTitleActivity implements JackListView.OnGetPageListener,OnItemClickListener{
final String TAG = RecommandActivity.class.getSimpleName();
	
	JackListView recommandListView;
	FrameLayout frameContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypeople);
//		TestDataTracker.simulateConnection(this, RequestType.CARDMY.toString());
		initViews();
		setBackBtnAlive();
	}

	private void initViews() {
		((TextView)findViewById(R.id.tv_title)).setText("ÍÆ¼öÉÌÆÌ");
		recommandListView = new JackListView(this, ListItemImpl.ITEMTYPE_COMPANY_SEARCH);
		recommandListView.setOnGetPageListener(this);
		recommandListView.setOnItemClickListener(this);
		recommandListView.setup();
		frameContainer = (FrameLayout)findViewById(R.id.frame_people);
		frameContainer.addView(recommandListView);
	}
	
	@Override
	public void page(JackListView qListView, int pageNo) {
		if(null!=recommandListView)new HttpRequestTask(recommandListView).execute(
				YftValues.getHTTPBodyString(RequestType.SEARCH_RECOMMEND, 
						"1","10",pageNo+""
						));
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		LIICompany comp = (LIICompany)parent.getAdapter().getItem(position);
		if(null==comp) return;
		Intent intent = new Intent();
//		YftData.data().storePerson(peop);
		User user = new User();
		user.setShopId(comp.getShopId());
		user.setMemberType(comp.getHasMotion());//membertype&shoplevel   logic disturbed here
		YftData.data().setCurrentUser(user);
		
		intent.setClass(this, ShopActivity.class);
		intent.putExtra(YftValues.EXTRAS_SHOP_MEMBER_TYPE, comp.getHasMotion());
		intent.putExtra("shopName", comp.getShopName());
		startActivity(intent);
	}
}
