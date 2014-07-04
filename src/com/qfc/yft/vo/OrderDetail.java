package com.qfc.yft.vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.json.JsonImport;

public class OrderDetail extends JsonImport{
	private String addrZipCode;
	private String addrConsignee;
	private String orderIp;
	private int tradeModel;
	private int shipFee;
	private String addrAddress;
	private int cashStatus;
	private int productType;
	private int shipType;
	private int sellerAccountId;
	private int orderAmount;
	private int autorecDate;
	private JSONArray orderProducts;
	private JSONObject attachInfo;
	private DateJson orderOverdueTime;
	private int isMarster;
	private JSONObject companyInfo;
	private int orderId;
	private String buyerCompanyName;
	private int orderPayed;
	private int sellerCompanyId;
	private int orderDiscount;
	private int urgeSign;
	private int shipStatus;
	private int buyerCompanyId;
	private String orderStatusCh;
	private String orderNote;
	private String mallType;
	private int withdrawAccountId;
	private String sellerCompanyName;
	private String sellerMainSite;
	private DateJson orderTime;
	private String orderStatus;
	private int buyerAccountId;
	private int judgeStatus;
	private String orderNo;
	private int payStatus;
	private int needSellerConfirm;
	private int payType;
	private String addrTelephone;
	private int productAmount;
	private String addrMobile;
	 
	
	
	public OrderDetail() {
		super();
	}



	public OrderDetail(JSONObject job) {
		super(job);
	}



	public final String getAddrZipCode() {
		return addrZipCode;
	}



	public final void setAddrZipCode(String addrZipCode) {
		this.addrZipCode = addrZipCode;
	}



	public final String getAddrConsignee() {
		return addrConsignee;
	}



	public final void setAddrConsignee(String addrConsignee) {
		this.addrConsignee = addrConsignee;
	}



	public final String getOrderIp() {
		return orderIp;
	}



	public final void setOrderIp(String orderIp) {
		this.orderIp = orderIp;
	}



	public final int getTradeModel() {
		return tradeModel;
	}



	public final void setTradeModel(int tradeModel) {
		this.tradeModel = tradeModel;
	}



	public final int getShipFee() {
		return shipFee;
	}



	public final void setShipFee(int shipFee) {
		this.shipFee = shipFee;
	}



	public final String getAddrAddress() {
		return addrAddress;
	}



	public final void setAddrAddress(String addrAddress) {
		this.addrAddress = addrAddress;
	}



	public final int getCashStatus() {
		return cashStatus;
	}



	public final void setCashStatus(int cashStatus) {
		this.cashStatus = cashStatus;
	}



	public final int getProductType() {
		return productType;
	}



	public final void setProductType(int productType) {
		this.productType = productType;
	}



	public final int getShipType() {
		return shipType;
	}



	public final void setShipType(int shipType) {
		this.shipType = shipType;
	}



	public final int getSellerAccountId() {
		return sellerAccountId;
	}



	public final void setSellerAccountId(int sellerAccountId) {
		this.sellerAccountId = sellerAccountId;
	}



	public final int getOrderAmount() {
		return orderAmount;
	}



	public final void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}



	public final int getAutorecDate() {
		return autorecDate;
	}



	public final void setAutorecDate(int autorecDate) {
		this.autorecDate = autorecDate;
	}



	public final JSONArray getOrderProducts() {
		return orderProducts;
	}



	public final void setOrderProducts(JSONArray orderProducts) {
		this.orderProducts = orderProducts;
	}



	public final JSONObject getAttachInfo() {
		return attachInfo;
	}



	public final void setAttachInfo(JSONObject attachInfo) {
		this.attachInfo = attachInfo;
	}



	public final DateJson getOrderOverdueTime() {
		return orderOverdueTime;
	}



	public final void setOrderOverdueTime(DateJson orderOverdueTime) {
		this.orderOverdueTime = orderOverdueTime;
	}



	public final int getIsMarster() {
		return isMarster;
	}



	public final void setIsMarster(int isMarster) {
		this.isMarster = isMarster;
	}



	public final JSONObject getCompanyInfo() {
		return companyInfo;
	}



	public final void setCompanyInfo(JSONObject companyInfo) {
		this.companyInfo = companyInfo;
	}



	public final int getOrderId() {
		return orderId;
	}



	public final void setOrderId(int orderId) {
		this.orderId = orderId;
	}



	public final String getBuyerCompanyName() {
		return buyerCompanyName;
	}



	public final void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}



	public final int getOrderPayed() {
		return orderPayed;
	}



	public final void setOrderPayed(int orderPayed) {
		this.orderPayed = orderPayed;
	}



	public final int getSellerCompanyId() {
		return sellerCompanyId;
	}



	public final void setSellerCompanyId(int sellerCompanyId) {
		this.sellerCompanyId = sellerCompanyId;
	}



	public final int getOrderDiscount() {
		return orderDiscount;
	}



	public final void setOrderDiscount(int orderDiscount) {
		this.orderDiscount = orderDiscount;
	}



	public final int getUrgeSign() {
		return urgeSign;
	}



	public final void setUrgeSign(int urgeSign) {
		this.urgeSign = urgeSign;
	}



	public final int getShipStatus() {
		return shipStatus;
	}



	public final void setShipStatus(int shipStatus) {
		this.shipStatus = shipStatus;
	}



	public final int getBuyerCompanyId() {
		return buyerCompanyId;
	}



	public final void setBuyerCompanyId(int buyerCompanyId) {
		this.buyerCompanyId = buyerCompanyId;
	}



	public final String getOrderStatusCh() {
		return orderStatusCh;
	}



	public final void setOrderStatusCh(String orderStatusCh) {
		this.orderStatusCh = orderStatusCh;
	}



	public final String getOrderNote() {
		return orderNote;
	}



	public final void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}



	public final String getMallType() {
		return mallType;
	}



	public final void setMallType(String mallType) {
		this.mallType = mallType;
	}



	public final int getWithdrawAccountId() {
		return withdrawAccountId;
	}



	public final void setWithdrawAccountId(int withdrawAccountId) {
		this.withdrawAccountId = withdrawAccountId;
	}



	public final String getSellerCompanyName() {
		return sellerCompanyName;
	}



	public final void setSellerCompanyName(String sellerCompanyName) {
		this.sellerCompanyName = sellerCompanyName;
	}



	public final String getSellerMainSite() {
		return sellerMainSite;
	}



	public final void setSellerMainSite(String sellerMainSite) {
		this.sellerMainSite = sellerMainSite;
	}



	public final DateJson getOrderTime() {
		return orderTime;
	}



	public final void setOrderTime(DateJson orderTime) {
		this.orderTime = orderTime;
	}



	public final String getOrderStatus() {
		return orderStatus;
	}



	public final void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}



	public final int getBuyerAccountId() {
		return buyerAccountId;
	}



	public final void setBuyerAccountId(int buyerAccountId) {
		this.buyerAccountId = buyerAccountId;
	}



	public final int getJudgeStatus() {
		return judgeStatus;
	}



	public final void setJudgeStatus(int judgeStatus) {
		this.judgeStatus = judgeStatus;
	}



	public final String getOrderNo() {
		return orderNo;
	}



	public final void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}



	public final int getPayStatus() {
		return payStatus;
	}



	public final void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}



	public final int getNeedSellerConfirm() {
		return needSellerConfirm;
	}



	public final void setNeedSellerConfirm(int needSellerConfirm) {
		this.needSellerConfirm = needSellerConfirm;
	}



	public final int getPayType() {
		return payType;
	}



	public final void setPayType(int payType) {
		this.payType = payType;
	}



	public final String getAddrTelephone() {
		return addrTelephone;
	}



	public final void setAddrTelephone(String addrTelephone) {
		this.addrTelephone = addrTelephone;
	}



	public final int getProductAmount() {
		return productAmount;
	}



	public final void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}



	public final String getAddrMobile() {
		return addrMobile;
	}



	public final void setAddrMobile(String addrMobile) {
		this.addrMobile = addrMobile;
	}



	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("addrZipCode")) addrZipCode = job.getString("addrZipCode");
		if(job.has("addrConsignee")) addrConsignee = job.getString("addrConsignee");
		if(job.has("orderIp")) orderIp = job.getString("orderIp");
		if(job.has("tradeModel")) tradeModel = job.getInt("tradeModel");
		if(job.has("shipFee")) shipFee = job.getInt("shipFee");
		if(job.has("addrAddress")) addrAddress = job.getString("addrAddress");
		if(job.has("cashStatus")) cashStatus = job.getInt("cashStatus");
		if(job.has("productType")) productType = job.getInt("productType");
		if(job.has("shipType")) shipType = job.getInt("shipType");
		if(job.has("sellerAccountId")) sellerAccountId = job.getInt("sellerAccountId");
		if(job.has("orderAmount")) orderAmount = job.getInt("orderAmount");
		if(job.has("autorecDate")) autorecDate = job.getInt("autorecDate");
		if(job.has("orderProducts")) orderProducts = job.getJSONArray("orderProducts");
		if(job.has("attachInfo")) attachInfo = job.getJSONObject("attachInfo");
		if(job.has("orderOverdueTime")) orderOverdueTime = new DateJson(job.getJSONObject("orderOverdueTime"));
		if(job.has("isMarster")) isMarster = job.getInt("isMarster");
		if(job.has("companyInfo")) companyInfo = job.getJSONObject("companyInfo");
		if(job.has("orderId")) orderId = job.getInt("orderId");
		if(job.has("buyerCompanyName")) buyerCompanyName = job.getString("buyerCompanyName");
		if(job.has("orderPayed")) orderPayed = job.getInt("orderPayed");
		if(job.has("sellerCompanyId")) sellerCompanyId = job.getInt("sellerCompanyId");
		if(job.has("orderDiscount")) orderDiscount = job.getInt("orderDiscount");
		if(job.has("urgeSign")) urgeSign = job.getInt("urgeSign");
		if(job.has("shipStatus")) shipStatus = job.getInt("shipStatus");
		if(job.has("buyerCompanyId")) buyerCompanyId = job.getInt("buyerCompanyId");
		if(job.has("orderStatusCh")) orderStatusCh = job.getString("orderStatusCh");
		if(job.has("orderNote")) orderNote = job.getString("orderNote");
		if(job.has("mallType")) mallType = job.getString("mallType");
		if(job.has("withdrawAccountId")) withdrawAccountId = job.getInt("withdrawAccountId");
		if(job.has("sellerCompanyName")) sellerCompanyName = job.getString("sellerCompanyName");
		if(job.has("sellerMainSite")) sellerMainSite = job.getString("sellerMainSite");
		if(job.has("orderTime")) orderTime = new DateJson(job.getJSONObject("orderTime"));
		if(job.has("orderStatus")) orderStatus = job.getString("orderStatus");
		if(job.has("buyerAccountId")) buyerAccountId = job.getInt("buyerAccountId");
		if(job.has("judgeStatus")) judgeStatus = job.getInt("judgeStatus");
		if(job.has("orderNo")) orderNo = job.getString("orderNo");
		if(job.has("payStatus")) payStatus = job.getInt("payStatus");
		if(job.has("needSellerConfirm")) needSellerConfirm = job.getInt("needSellerConfirm");
		if(job.has("payType")) payType = job.getInt("payType");
		if(job.has("addrTelephone")) addrTelephone = job.getString("addrTelephone");
		
	}

}
