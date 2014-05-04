package com.qfc.yft.utils;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.net.HttpRequestTask;

public class TestActivity extends Activity implements HttpReceiver , OnClickListener{
	
	LinearLayout mLayout;
	String curDataKey;
	
	/*
	 * NONE,POINT_VERIFY(REQUEST_PATH_LOGIN),MEMBER_INFO(REQUEST_PATH_MEMBER_INFO),
		SHOP_INFO(REQUEST_PATH_COMPANY_INFO),SERIES_INFO(REQUEST_PATH_COMPANY_PRO),
		PRODUCT_INFO(REQUEST_PATH_COMPANY_SUBPRO),SEARCH_RECOMMEND(REQUEST_PATH_RECOMMEND),
		SEARCH(REQUEST_PATH_SEARCH),UPDATE(REQUEST_PATH_CHECKVERSION),
		SYNC(REQUEST_PATH_SYNC),SM(REQUEST_PATH_SERIESFORMOTION),PM(REQUEST_PATH_PRODUCTFORMOTION),
		CATEGORY_ALL(REQUEST_CATEGORY),CARDSEARCH(REQUEST_CARD_SEARCH),CARDMY(REQUEST_CARD_MY),
		SEARCH_PRODUCT(REQUEST_SEARCH_PRODCUT),COLLECTION_SAVE(REQUEST_COLLECTION_SAVE)
	 */
	final RequestType[] DATA_KEYS = new RequestType[]{
			RequestType.CARDSEARCH,
			RequestType.CARDMY,
			RequestType.SEARCH_PRODUCT,
			RequestType.FIND_COMPANY,
			RequestType.FIND_PRODUCT,
			RequestType.COLLECTION_SAVE,
			RequestType.CARD_ADD,
			RequestType.CARD_REMOVE
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLayout = new LinearLayout(this);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		TextView textView = new TextView(this);
		mLayout.addView(textView);
		setContentView(mLayout);
		
		for(int i=0;i<DATA_KEYS.length;i++){
			addNetBtn(i);
		}
	}
	
	private void addNetBtn(int btnDataKeyIndex){
		if(null!=curDataKey&&!curDataKey.isEmpty()) return;
		Button btn = new Button(this);
		btn.setText(DATA_KEYS[btnDataKeyIndex].toString());
		btn.setId(btnDataKeyIndex+1000);
		btn.setOnClickListener(this);
		mLayout.addView(btn);
	}

	@Override
	public void response(String result) throws JSONException {
		if(null!=curDataKey&&!curDataKey.isEmpty()){
			TestDataTracker.settleDataString(curDataKey, result);
			curDataKey = "";
		}
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		vid-=1000;
		if(vid>=DATA_KEYS.length) return;
		curDataKey = DATA_KEYS[vid].toString();
		new HttpRequestTask(this).execute(YftValues.getHTTPBodyString(DATA_KEYS[vid], ""));
		
	}
}
