package com.qfc.yft.ui.tabs.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.User;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.custom.list.JackListView;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;

public class FavActivity extends MyTitleActivity  {
	final String TAG = FavActivity.class.getSimpleName();
	RadioGroup rGroup;
	FrameLayout fLayout;
	JackListView qList1,qList2;
	RadioButton[] rBtns;
	View emptyView;
	
	User fUser;
	int currentTab;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fav);
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(null==rGroup) return;
		if(currentTab==2)currentTab=1;//回来后切到企业
		if(rGroup.getCheckedRadioButtonId()==currentTab){
			clickTab(currentTab);
			return;
		}
		rGroup.check(currentTab);
	}
	
	@Override
	protected void onPause() {
		qList1.setSetup(false);qList2.setSetup(false);//0310
		super.onPause();
	}
	
	private void initViews() {
		fUser = YftData.data().getMe();
				
		emptyView = findViewById(R.id.emptyview_search_1);//0228
//		((TextView)findViewById(R.id.tv_title)).setText("我的收藏");
		setBackBtnAlive();
		
		rGroup = (RadioGroup)findViewById(R.id.group_fav);
		rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				clickTab(checkedId);//-R.id.btn_fav_1_product);
			}
		});
		rBtns = new RadioButton[3];
		rBtns[0] = (RadioButton)findViewById(R.id.btn_fav_1_product);
		rBtns[1] = (RadioButton)findViewById(R.id.btn_fav_2_company);
		rBtns[2] = (RadioButton)findViewById(R.id.btn_fav_3_snap);
		rBtns[0].setId(0);rBtns[1].setId(1);rBtns[2].setId(2);
		
		fLayout= (FrameLayout)findViewById(R.id.frame_fav);
		qList1 = new JackListView(this, ListItemImpl.ITEMTYPE_PRODUCT_SEARCH);
		qList2 = new JackListView(this, ListItemImpl.ITEMTYPE_COMPANY_SEARCH);
		qList1.setEmptyView(emptyView);//0228
		qList2.setEmptyView(emptyView);
		qList1.setOnGetPageListener(new JackListView.OnGetPageListener() {
			
			@Override
			public void page(JackListView qListView, int pageNo) {
				if(null==fUser) return;
				new HttpRequestTask(qListView).execute(YftValues.getHTTPBodyString(RequestType.FIND_PRODUCT, 
						fUser.getId()+"",
						"10",1+""));
			}
		});
		qList2.setOnGetPageListener(new JackListView.OnGetPageListener() {
			
			@Override
			public void page(JackListView qListView, int pageNo) {
				if(null==fUser) return;
				new HttpRequestTask(qListView).execute(YftValues.getHTTPBodyString(RequestType.FIND_COMPANY, 
						fUser.getId()+"",
						"10",1+""));
			}
		});
	}
	
	private void clickTab(int tid){
		if(null==rGroup||tid<0||tid>2) return;//0317
		Log.i(TAG, "tid:"+tid);
		select(rBtns[tid]);
		currentTab=tid;
		switch (tid) {
		case 0:
			if(!qList1.isSetup())qList1.setup();
			fLayout.removeAllViews();
			fLayout.addView(qList1);
			break;

		case 1:
			if(!qList2.isSetup())qList2.setup();
			fLayout.removeAllViews();
			fLayout.addView(qList2);
			break;
		case 2:
			Intent intent = new Intent();
			intent.setClass(this, QRCaptureActivity.class);
			startActivity(intent);
			qList1.setSetup(false);
			qList2.setSetup(false);
			rGroup.clearCheck();
			break;
		default: 
			break;
		}
		if(null!=emptyView&&null==emptyView.getParent())fLayout.addView(emptyView);//0228
	}
	private void select(View toSelect){ 
		View selected = (View)rGroup.getTag();
		if(null!=selected) selected.setSelected(false);
		toSelect.setSelected(true);
		rGroup.setTag(toSelect);
	}
}
