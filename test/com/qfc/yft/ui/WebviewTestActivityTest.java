package com.qfc.yft.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.activity.WebviewTestActivity;

public class WebviewTestActivityTest extends ActivityInstrumentationTestCase2<WebviewTestActivity> {

	public WebviewTestActivityTest(){
		super(WebviewTestActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
//		setActivityIntent(new Intent());
	}
	@Override
	public void setActivityIntent(Intent i) {
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		super.setActivityIntent(i);
	}
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAvailable() {
		assertNotNull(getActivity());
		SystemClock.sleep(1000*60);
	}
	
}
