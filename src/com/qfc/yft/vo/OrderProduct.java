package com.qfc.yft.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.json.JsonImport;

public class OrderProduct extends JsonImport {
	 private String normValue;
	 private String mallType;
	 private String productImage;
	 private String normId;
	 private String productUnit;
	 private String sampleFlag;
	 private String productId;
	 private String productSn;
	 private String productNumber;
	 private String productDesc;
	 private String orderProdId;
	 private String imageUrl300X;
	 private String imageUrl300X300;
	 private String productName;
	 private String productPrice;
	 private String orderId;
	 private String cateCode;
	
	 

	public OrderProduct() {
		super();
	}



	public OrderProduct(JSONObject job) {
		super(job);
	}



	public final String getNormValue() {
		return normValue;
	}



	public final void setNormValue(String normValue) {
		this.normValue = normValue;
	}



	public final String getMallType() {
		return mallType;
	}



	public final void setMallType(String mallType) {
		this.mallType = mallType;
	}



	public final String getProductImage() {
		return productImage;
	}



	public final void setProductImage(String productImage) {
		this.productImage = productImage;
	}



	public final String getNormId() {
		return normId;
	}



	public final void setNormId(String normId) {
		this.normId = normId;
	}



	public final String getProductUnit() {
		return productUnit;
	}



	public final void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}



	public final String getSampleFlag() {
		return sampleFlag;
	}



	public final void setSampleFlag(String sampleFlag) {
		this.sampleFlag = sampleFlag;
	}



	public final String getProductId() {
		return productId;
	}



	public final void setProductId(String productId) {
		this.productId = productId;
	}



	public final String getProductSn() {
		return productSn;
	}



	public final void setProductSn(String productSn) {
		this.productSn = productSn;
	}



	public final String getProductNumber() {
		return productNumber;
	}



	public final void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}



	public final String getProductDesc() {
		return productDesc;
	}



	public final void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}



	public final String getOrderProdId() {
		return orderProdId;
	}



	public final void setOrderProdId(String orderProdId) {
		this.orderProdId = orderProdId;
	}



	public final String getImageUrl300X() {
		return imageUrl300X;
	}



	public final void setImageUrl300X(String imageUrl300X) {
		this.imageUrl300X = imageUrl300X;
	}



	public final String getImageUrl300X300() {
		return imageUrl300X300;
	}



	public final void setImageUrl300X300(String imageUrl300X300) {
		this.imageUrl300X300 = imageUrl300X300;
	}



	public final String getProductName() {
		return productName;
	}



	public final void setProductName(String productName) {
		this.productName = productName;
	}



	public final String getProductPrice() {
		return productPrice;
	}



	public final void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}



	public final String getOrderId() {
		return orderId;
	}



	public final void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public final String getCateCode() {
		return cateCode;
	}



	public final void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}



	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		 if(job.has("normValue")) normValue = job.getString("normValue");
		 if(job.has("mallType")) mallType = job.getString("mallType");
		 if(job.has("productImage")) productImage = job.getString("productImage");
		 if(job.has("normId")) normId = job.getString("normId");
		 if(job.has("productUnit")) productUnit = job.getString("productUnit");
		 if(job.has("sampleFlag")) sampleFlag = job.getString("sampleFlag");
		 if(job.has("productId")) productId = job.getString("productId");
		 if(job.has("productSn")) productSn = job.getString("productSn");
		 if(job.has("productNumber")) productNumber = job.getString("productNumber");
		 if(job.has("productDesc")) productDesc = job.getString("productDesc");
		 if(job.has("orderProdId")) orderProdId = job.getString("orderProdId");
		 if(job.has("imageUrl300X")) imageUrl300X = job.getString("imageUrl300X");
		 if(job.has("imageUrl300X300")) imageUrl300X300 = job.getString("imageUrl300X300");
		 if(job.has("productName")) productName = job.getString("productName");
		 if(job.has("productPrice")) productPrice = job.getString("productPrice");
		 if(job.has("orderId")) orderId = job.getString("orderId");
		 if(job.has("cateCode")) cateCode = job.getString("cateCode");

	}

}
