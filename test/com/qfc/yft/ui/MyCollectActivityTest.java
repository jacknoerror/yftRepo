package com.qfc.yft.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.qfc.yft.data.MyData;
import com.qfc.yft.data.TestConst;
import com.qfc.yft.ui.common.MyCollectionActivity;
import com.qfc.yft.vo.User;

public class MyCollectActivityTest extends ActivityInstrumentationTestCase2<MyCollectionActivity> {

	public MyCollectActivityTest(){
		super(MyCollectionActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityIntent(new Intent());
	}
	@Override
	public void setActivityIntent(Intent i) {
		User user = new User();
		user.setId(TestConst.userId);
		user.setShopId(TestConst.compId);
		MyData.data().setMe(user);
		super.setActivityIntent(i);
	}
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAvailable() {
		assertNotNull(getActivity());
		SystemClock.sleep(1000*60);
//		SystemClock.sleep(1000*60);
	}
	
}
