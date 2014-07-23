package com.qfc.yft.ui;

public interface JackInitViewImpl {
	/**
	 * 
	 * @return 界面资源的id。如果不需要配置，必须在initView中对mView初始化。
	 */
	public   int getLayoutRid();
	/**
	 * 初始化
	 */
	public   void initView();

}