package com.qfc.yft.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.data.TestConst;
import com.qfc.yft.ui.tabs.work.OrderDetailActivity;
import com.qfc.yft.vo.User;

public class OrderDetailActivityTest extends ActivityInstrumentationTestCase2<OrderDetailActivity> {

	public OrderDetailActivityTest(){
		super(OrderDetailActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityIntent(new Intent());
	}
	@Override
	public void setActivityIntent(Intent i) {
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.putExtra(NetConst.EXTRAS_ORDERID, 9355);
//		i.putExtra(NetConst.EXTRAS_ORDERID, OrderActivity.SELL);
		User user = new User();
		user.setId(36745);
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