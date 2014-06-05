package com.qfc.yft.ui;

import android.app.Activity;
import android.os.Bundle;


public abstract class MyTitleActivity extends Activity implements JackInitViewImpl {
	protected final String TAG = getClass().getSimpleName();

	protected TitleManager titleManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getLayoutRid()>0)setContentView(getLayoutRid());
		titleManager = new TitleManager(this);
		initView();
		
	}
	
	
}
