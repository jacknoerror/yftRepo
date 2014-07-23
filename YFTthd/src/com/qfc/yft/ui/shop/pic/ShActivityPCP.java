package com.qfc.yft.ui.shop.pic;

import com.qfc.yft.R;

import android.widget.TextView;

public class ShActivityPCP extends ShPicParentActivity {

	TextView pcpPicName;
	
	String[] pcpNames;
	
	@Override
	protected String[] getPathsFromCompany() {
		return shHelper.dfCompany.getCompicStrs();
	}
	
	private String[] getPicNames() {
		if(pcpNames!=null) return pcpNames;
		final String DIVIDER = "&:&";
		String nameString = shHelper.dfCompany.getCompPicName();
		if(!nameString.isEmpty()) {
			if(nameString.contains(DIVIDER)){
				pcpNames = nameString.split(DIVIDER);
				return pcpNames;
			}
		}
		return null;
	}
	
	private void setPicName(int pageIndex){
		if(pcpNames==null) pcpNames=getPicNames();
		if(pcpNames==null||pcpNames.length<pageIndex) return;
		if(pcpPicName==null) pcpPicName = (TextView)findViewById(R.id.tv_PC_picname_sh);
		pcpPicName.setText(pcpNames[pageIndex-1]);
	}
	@Override
	protected void setPageIndex(int at, int all) {
		super.setPageIndex(at, all);
		setPicName(at);
	}
}
