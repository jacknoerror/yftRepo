package com.qfc.yft.ui.shop.pic;

public class ShActivityPCC extends ShPicParentActivity {

	@Override
	protected String[] getPathsFromCompany() {
		return shHelper.dfCompany.getCertString();
	}

}
