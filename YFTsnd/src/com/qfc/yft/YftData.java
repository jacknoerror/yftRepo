package com.qfc.yft;

import android.support.v4.app.FragmentTabHost;

public class YftData {
	private static YftData yData;
	private YftData(){};
	public static YftData data(){
		if(yData==null){
			yData = new YftData();
		}
		return yData;
	}
	
	FragmentTabHost ftHost;
	public FragmentTabHost getFtHost() {
		return ftHost;
	}
	public void setFtHost(FragmentTabHost ftHost) {
		this.ftHost = ftHost;
	}
	
	
}
