package com.qfc.yft.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.data.TestConst;
import com.qfc.yft.ui.tab.work.OrderActivity;
import com.qfc.yft.vo.User;

public class OrderActivityTest extends ActivityInstrumentationTestCase2<OrderActivity> {

	public OrderActivityTest(){
		super(OrderActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityIntent(new Intent());
	}
	@Override
	public void setActivityIntent(Intent i) {
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.putExtra(OrderActivity.EXTRAS_ORDERTYPE, OrderActivity.SELL);
		User user = new User();
		user.setId(36745);
//		user.setId(TestConst.userId);
		user.setShopId(TestConst.compId);
		MyData.data().setMe(user );
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
