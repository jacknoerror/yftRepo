package com.qfc.yft.ui.tabs.person;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.listitem.LIIPeople;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.current.CurrentPersonActivity;
import com.qfc.yft.ui.custom.list.JackListView;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;
import com.qfc.yft.utils.JackUtils;

public class PeopleActivity extends MyTitleActivity implements JackListView.OnGetPageListener {
	final String TAG = PeopleActivity.class.getSimpleName();
	
	JackListView peopleListView;
	FrameLayout frameContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypeople);
//		TestDataTracker.simulateConnection(this, RequestType.CARDMY.toString());
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("我的人脉");
		initViews();
	}

	private void initViews() {
		setBackBtnAlive();
		View emptyView = findViewById(R.id.emptyview_mypeople);
//		((TextView)findViewById(R.id.tv_title)).setText("我的人脉");
		peopleListView = new JackListView(this, ListItemImpl.ITEMTYPE_PEOPLE_MY);
		peopleListView.setOnGetPageListener(this);
//		peopleListView.setOnItemClickListener(this);
		peopleListView.setEmptyView(emptyView);
		frameContainer = (FrameLayout)findViewById(R.id.frame_people);
		frameContainer.addView(peopleListView);
	}

	@Override
	protected void onResume() {
		peopleListView.setup();
		super.onResume();
	}
	
	@Override
	public void page(JackListView qListView, int pageNo) {
		if(null!=peopleListView)new HttpRequestTask(peopleListView).execute(
				YftValues.getHTTPBodyString(RequestType.CARDMY, 
						YftData.data().getMe().getId()+"",
						100+"",pageNo+""));//
		
	}

	/*@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		LIIPeople peop = (LIIPeople)parent.getAdapter().getItem(position);
		if(null==peop) return;
		if(peop.texTalk!=null&&!peop.texTalk.equals("null")){
			
			YftData.data().storePerson(peop);
			Intent intent = new Intent();
			intent.setClass(this, CurrentPersonActivity.class);
			intent.putExtra("accountId", peop.texTalk);
			startActivity(intent);
		}else{
			JackUtils.showToast(this, "该用户没有纺织聊");
		}
	}*/
}
