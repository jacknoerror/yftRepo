package com.qfc.yft.ui.shop;

import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.util.JackUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;

public abstract class ShopTabAbsActivity extends Activity implements
		HttpUpdater {

	protected CompanyHttpHelper shHelper;
	
	/**
	 * 
	 * @return 0 if wanna custom
	 */
	public abstract int getLayoutResId();
	public abstract void initUI();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int rid = getLayoutResId();
		if(rid!=0)setContentView(rid);
		shHelper = new CompanyHttpHelper(this);//TODO factory?
		initUI();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(!JackUtils.isNetworkAvailable(this))return;//
		if(null!=shHelper) shHelper.onResume();
	}
	
	@Override
	public boolean response(String result) {
		return null!=shHelper&& shHelper.onResponse(result);

	}

	@Override
	public Context getReceiverContext() {
		return getParent();
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
//			((ShParentActivity)getParent()).showExitDialog();
			//TODO
			
		}
		return super.onKeyDown(keyCode, event);
	}
}
