package com.qfc.yft.ui.shop;

public class SimpleShopInfo{
	/**
	 * 
	 */
	/*
	 * {"shopId":"14656",
	 * "shopName":"¹´¹´¼Ò·Ä",
	 * "compIntro":"balabala",
	 * "hasMotion":1,
	 * "shopLogoImage":"http://img-i.qfc.cn/upload/01/company/d3/87/5260.jpg"},
	 */
	public int shopId;
	public String shopName;
	public String compIntro;
	public boolean hasMotion;
	public String shopLogoImage;
	
	public SimpleShopInfo( ) {
		shopId=-1;
		shopName="";
		compIntro="";
		shopLogoImage="";
	}

	@Override
	public String toString() {
		return "SearchShopInfo [shopId=" + shopId + ", shopName=" + shopName
				+ ", compIntro=" + compIntro + ", hasMotion=" + hasMotion
				+ ", shopLogoImage=" + shopLogoImage + "]";
	}
	
	
	
}