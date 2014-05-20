package com.qfc.yft.entity.page;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.listitem.LIICompany;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;

public class CompanyPageInfo extends QfcPageInfo {
	List<ListItemImpl> companyList;
	
	
	@Override
	public List<ListItemImpl> getDataList() {
		if(companyList!=null) return companyList;//如果有了，直接返回
		companyList = new ArrayList<ListItemImpl>();//
		for(int i=0;null!=infoArr&&i<infoArr.length();i++){
			LIICompany pp = new LIICompany();//
			try {
				JSONObject job = infoArr.getJSONObject(i);
				if(job.has(COMPANY_SHOPID))pp.setShopId(job.getInt(COMPANY_SHOPID));
				if(job.has(COMPANY_SHOPNAME))pp.setShopName(job.getString(COMPANY_SHOPNAME));
				if(job.has(COMPANY_COMPINTRO))pp.setCompIntro(job.getString(COMPANY_COMPINTRO));
				if(job.has(COMPANY_HASMOTION))pp.setHasMotion(job.getInt(COMPANY_HASMOTION));
				if(job.has(COMPANY_MAINPRODUCTS))pp.setMainProducts(job.getString(COMPANY_MAINPRODUCTS));
				if(job.has(COMPANY_SHOPLOGOIMAGE))pp.setShopLogoImage(job.getString(COMPANY_SHOPLOGOIMAGE));
				if(job.has(COMPANY_SERVICE))pp.setService(job.optInt(COMPANY_SERVICE, -1));//
				
				//由于接口返回值不统一 有如下备用填充方式
				if(job.has("compId"))pp.setShopId(job.getInt("compId"));
				if(job.has("compName"))pp.setShopName(job.getString("compName"));
				if(job.has("compMainProduct"))pp.setMainProducts(job.getString("compMainProduct"));
				if(job.has("shopType"))pp.setHasMotion(job.getInt("shopType"));
				if(job.has("shopLevel"))pp.setHasMotion(job.getInt("shopLevel"));//0416
				if(job.has("compLogoImg"))pp.setShopLogoImage(job.getString("compLogoImg"));
				if(job.has("compPurchaseProduct"))pp.setCompPurchaseProduct(job.getString("compPurchaseProduct"));//0514
				//compXxiangImg
				
				companyList.add(pp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//
			
		}
		return companyList;
	}
	
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
}
