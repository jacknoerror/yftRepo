package com.qfc.yft.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.ui.custom.list.MspJsonItem;

/**
 * 产品管理列表产品
 * @author taotao
 * @Date 2014-6-16
 */
public class ProductManage extends MspJsonItem {
	

	/*
  	wholePrice - <String>  - 批发价
	sellStatus - <Integer>  - 销售状态
	marketPrice - <Double>  - 市场价格
	tradeStatus - <Integer>  - 交易状态
	statusStr - <String>  - 用于状态显示字符串
	priceType - <Integer>  - 价格类型
	productUnit - <String>  - 单位
	productId - <Integer>  - 产品id
	city - <Integer>  - 城市
	propStr - <String>  - 属性字符串
	auditStatus - <Integer>  - 审核状态
	stock - <Integer>  - 库存量
	county - <Integer>  - 县/区
	productStatus - <Integer>  - 产品状态
	isPrivate - <Boolean>  - 是否私有
	productStatusQuo - <Integer>  - 货物状态
	province - <Integer>  - 省
	companyId - <Integer>  - 公司id
	sellEndTime - <Timestamp>  - 销售结束时间
	productName - <String>  - 产品名称
	productPrice - <String>  - 产品价格
	addTime - <Timestamp>  - 添加时间
	 */
	private int sellStatus;//为空表示未开通交易
	private JSONObject propMap;
	private int tradeStatus;
	private String statusStr;
	private String productUnit;
	private int city;
	private String imageNum;
	private int productId;
	private String propStr;
	private String productNumber;
	private int auditStatus;
	private boolean isPrivate;
	private int county;
	private int productStatus;
	private int productStatusQuo;
	private int province;
	private int companyId;
	private DateJson sellEndTime;
	private String productName;
	private String productPrice;
	private DateJson addTime;
	
	String imageUrl300X300 ; 
	
	int online;
	int offline;
	int republish;


	public ProductManage() {
		super();
	}


	public ProductManage(JSONObject job) {
		super(job);
	}


	public final int getSellStatus() {
		return sellStatus;
	}


	public final void setSellStatus(int sellStatus) {
		this.sellStatus = sellStatus;
	}


	public final JSONObject getPropMap() {
		return propMap;
	}


	public final void setPropMap(JSONObject propMap) {
		this.propMap = propMap;
	}


	public final int getTradeStatus() {
		return tradeStatus;
	}


	public final void setTradeStatus(int tradeStatus) {
		this.tradeStatus = tradeStatus;
	}


	public final String getStatusStr() {
		return statusStr;
	}


	public final void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}


	public final String getProductUnit() {
		return productUnit;
	}


	public final void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}


	public final int getCity() {
		return city;
	}


	public final void setCity(int city) {
		this.city = city;
	}


	public final String getImageNum() {
		return imageNum;
	}


	public final void setImageNum(String imageNum) {
		this.imageNum = imageNum;
	}


	public final int getProductId() {
		return productId;
	}


	public final void setProductId(int productId) {
		this.productId = productId;
	}


	public final String getPropStr() {
		return propStr;
	}


	public final void setPropStr(String propStr) {
		this.propStr = propStr;
	}


	public final String getProductNumber() {
		return productNumber;
	}


	public final void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}


	public final int getAuditStatus() {
		return auditStatus;
	}


	public final void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}


	public final boolean isPrivate() {
		return isPrivate;
	}


	public final void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}


	public final int getCounty() {
		return county;
	}


	public final void setCounty(int county) {
		this.county = county;
	}


	public final int getProductStatus() {
		return productStatus;
	}


	public final void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}


	public final int getProductStatusQuo() {
		return productStatusQuo;
	}


	public final void setProductStatusQuo(int productStatusQuo) {
		this.productStatusQuo = productStatusQuo;
	}


	public final int getProvince() {
		return province;
	}


	public final void setProvince(int province) {
		this.province = province;
	}


	public final int getCompanyId() {
		return companyId;
	}


	public final void setCompanyId(int companyId) {
		this.companyId = companyId;
	}


	public final DateJson getSellEndTime() {
		return sellEndTime;
	}


	public final void setSellEndTime(DateJson sellEndTime) {
		this.sellEndTime = sellEndTime;
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


	public final DateJson getAddTime() {
		return addTime;
	}


	public final void setAddTime(DateJson addTime) {
		this.addTime = addTime;
	}


	public final String getImageUrl300X300() {
		return imageUrl300X300;
	}


	public final void setImageUrl300X300(String imageUrl300X300) {
		this.imageUrl300X300 = imageUrl300X300;
	}


	public final int getOnline() {
		return online;
	}
	public boolean isOnline(){
		return online>0;
	}

	public final void setOnline(int online) {
		this.online = online;
	}


	public boolean isOffline(){
		return offline>0;
	}


	public final void setOffline(int offline) {
		this.offline = offline;
	}


	public boolean isRepublish(){
		return republish>0;
	}

	public final void setRepublish(int republish) {
		this.republish = republish;
	}


	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("propMap")) propMap = job.getJSONObject("propMap");
		if(job.has("tradeStatus")) tradeStatus = job.getInt("tradeStatus");
		if(job.has("statusStr")) statusStr = job.getString("statusStr");
		if(job.has("productUnit")) productUnit = job.getString("productUnit");
		if(job.has("city")) city = job.getInt("city");
		if(job.has("imageNum")) imageNum = job.getString("imageNum");
		if(job.has("productId")) productId = job.getInt("productId");
		if(job.has("propStr")) propStr = job.getString("propStr");
		if(job.has("productNumber")) productNumber = job.getString("productNumber");
		if(job.has("auditStatus")) auditStatus = job.getInt("auditStatus");
		if(job.has("isPrivate")) isPrivate = job.getBoolean("isPrivate");
		if(job.has("county")) county = job.getInt("county");
		if(job.has("productStatus")) productStatus = job.getInt("productStatus");
		if(job.has("productStatusQuo")) productStatusQuo = job.getInt("productStatusQuo");
		if(job.has("province")) province = job.getInt("province");
		if(job.has("companyId")) companyId = job.getInt("companyId");
		if(job.has("sellEndTime")) sellEndTime = new DateJson(job.optJSONObject("sellEndTime"));
		if(job.has("productName")) productName = job.getString("productName");
		if(job.has("productPrice")) productPrice = job.getString("productPrice");
		if(job.has("addTime")) addTime =  new DateJson(job.optJSONObject("addTime"));
		if(job.has("imageUrl300X300")) imageUrl300X300 = job.getString("imageUrl300X300");
		if(job.has("sellStatus")) sellStatus = job.optInt("sellStatus");
		
		if(job.has("online")) online = job.optInt("online");
		if(job.has("offline")) offline = job.optInt("offline");
		if(job.has("republish")) republish = job.optInt("republish");
		

	}

	
	/**
	 * if(auditStatus != null) {
		
		if("0".equals(auditStatus.toString())) {
		
		statusStr = "等待审核";
		
		} else if("1".equals(auditStatus.toString())) {
		
		statusStr = "审核通过";
		
		if(productStatus != null && "1".equals(productStatus.toString())) { //上架
		
		statusStr = "已上架";
		
		if(tradeStatus != null && "1".equals(tradeStatus.toString())) { //可交易
		
		if(sellStatus != null && "1".equals(sellStatus.toString())) { // 出售
		
		statusStr = "将于" + sellEndTime + "停售";
		
		}
		
		}
		
		}
		
		} else {
		
		statusStr = "审核驳回";
		
		}
		
		}


	 */
}
