package com.qfc.yft.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.common.CurProdActivity;
import com.qfc.yft.ui.common.ViewpagerActivity;

public class CurPActivityTest extends ActivityInstrumentationTestCase2<CurProdActivity> {

	public CurPActivityTest(){
		super(CurProdActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityIntent(new Intent());
	}
	@Override
	public void setActivityIntent(Intent i) {
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		final String[] strs = new String[]{
				"http://img.qfc.cn/upload/01/galary/a3/85/501912.jpg",
					"http://img.qfc.cn/upload/01/galary/6c/d7/501929.jpg",
					"http://img.qfc.cn/upload/01/galary/d5/12/501949.jpg"
		};
		i.putExtra(NetConst.EXTRAS_SHOWPIC_PATHS, strs);
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
