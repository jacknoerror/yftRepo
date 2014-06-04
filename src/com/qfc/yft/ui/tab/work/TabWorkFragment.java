package com.qfc.yft.ui.tab.work;

import com.qfc.yft.R;
import com.qfc.yft.ui.tab.ContentAbstractFragment;

public class TabWorkFragment extends ContentAbstractFragment {

	@Override
	public void initView() {
		initTitleManager();
		titleManager.setTitleName(getString(R.string.titlename_work));

	}

	@Override
	public int getLayoutRid() {
		return R.layout.fragment_work;
	}

}
