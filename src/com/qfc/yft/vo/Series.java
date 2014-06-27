package com.qfc.yft.vo;

import com.qfc.yft.util.JackUtils;

public class Series {
	/*
	 * {"productSeriesName":"和煦1子系列",
	 * "parentSeriesName":"和煦系列",
	 * "productSeriesId":2856,
	 * "productSeriesPic":"MDF8cHJvZHVjdHw5OTIxLmpwZWc=&:&http://img-i.qfc.cn/upload/01/product/2d/ef/9921_300X300.jpeg",
	 * "parentSid":2852}
	 */
	
	String productSeriesName="";
	String parentSeriesName="";
	int productSeriesId;
	String productSeriesPic="";
	int parentSid;
	
	public Series(String productSeriesName, String parentSeriesName,
			int productSeriesId, String productSeriesPic, int parentSid) {
		super();
		this.productSeriesName = productSeriesName;
		this.parentSeriesName = parentSeriesName;
		this.productSeriesId = productSeriesId;
		productSeriesPic = JackUtils.makeItUrl(productSeriesPic);
		this.productSeriesPic = productSeriesPic;
		this.parentSid = parentSid;
	}
	
	public String getProductSeriesName() {
		return productSeriesName;
	}
	public void setProductSeriesName(String productSeriesName) {
		this.productSeriesName = productSeriesName;
	}
	public String getParentSeriesName() {
		return parentSeriesName;
	}
	public void setParentSeriesName(String parentSeriesName) {
		this.parentSeriesName = parentSeriesName;
	}
	public int getProductSeriesId() {
		return productSeriesId;
	}
	public void setProductSeriesId(int productSeriesId) {
		this.productSeriesId = productSeriesId;
	}
	public String getProductSeriesPic() {
		return productSeriesPic;
	}
	public void setProductSeriesPic(String productSeriesPic) {
		this.productSeriesPic = productSeriesPic;
	}
	public int getParentSid() {
		return parentSid;
	}
	public void setParentSid(int parentSid) {
		this.parentSid = parentSid;
	}
	
	
	
}
