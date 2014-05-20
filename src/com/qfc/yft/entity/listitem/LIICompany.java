package com.qfc.yft.entity.listitem;

import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;

public class LIICompany implements ListItemImpl {
	/*
	 *  {
            "shopId": "14620",
            "shopName": "坯布测试（梭织88）",
            "service": 3,
            "compIntro": "棉坯布、化纤坯布、混纺坯布",
            "hasMsotion": 0,
            "mainProducts": "",
            "shopLogoImage": "http://img-i.qfc.cn/upload/01/company/b4/ff/11533.jpg"
        },
	 */
	/*
	 ** findcompany ** 
		"compLogoImg": "01|company|637764.jpg",
	    "compId": 402745,
	    "compXxiangImg": "01|company|637762.jpg",
	    "shopType": 3,
	    "compName": "德泓（宁夏）国际纺织有限公司",
	    "mainAccountId": 2035186, //
	    "compMainProduct": "羊绒精纺面料,羊绒混纺面料,羊毛混纺面料,羊绒纱线,羊绒条,成衣"
	 */
	int shopId;
	String shopName;
	Integer service;
	String compIntro;
	int hasMotion;
	String mainProducts;
	String shopLogoImage;
String 	compPurchaseProduct ;

	int shopType;
	
	public final int getShopType() {
		return shopType;
	}

	public final void setShopType(int shopType) {
		this.shopType = shopType;
	}

	public LIICompany(){
		shopId = -1;
		shopName = "";
		compIntro="";
		mainProducts="";
		shopLogoImage="";
		compPurchaseProduct="";
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public final String getCompPurchaseProduct() {
		return compPurchaseProduct;
	}

	public final void setCompPurchaseProduct(String compPurchaseProduct) {
		this.compPurchaseProduct = compPurchaseProduct;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getService() {
		return service;
	}

	public void setService(Integer service) {
		this.service = service;
	}

	public String getCompIntro() {
		return compIntro;
	}

	public void setCompIntro(String compIntro) {
		this.compIntro = compIntro;
	}

	public int getHasMotion() {
		return hasMotion;
	}

	public void setHasMotion(int hasMotion) {
		this.hasMotion = hasMotion;
	}

	public String getMainProducts() {
		return mainProducts;
	}

	public void setMainProducts(String mainProducts) {
		this.mainProducts = mainProducts;
	}

	public String getShopLogoImage() {
		return shopLogoImage;
	}

	public void setShopLogoImage(String shopLogoImage) {
		this.shopLogoImage = shopLogoImage;
	}
	
	
	
}
