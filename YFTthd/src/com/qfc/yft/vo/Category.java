package com.qfc.yft.vo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.json.JackJson;


public class Category extends JackJson{
	/*
	 * 							"cateLevel": 3,
                                "cateId": 3,
                                "cateCode": "001001001",
                                "parentCateId": 2,
                                "cateName": "切片"
	 */
	int cateLevel;
	int cateId;
	String cateCode;
	int parentCateId;
	String cateName;
	Category[] children;
//	List<Category> children;
	
	JSONObject catJob;
	@Override
	public JSONObject toJsonObj() {//不支持改动
		if(catJob==null) catJob = new JSONObject();
		return job;
	}
	@Override
	public void initJackJson(JSONObject job) {
		if(job==null||job.length()==0) return;//
		cateLevel=job.optInt("cateLevel");
		cateId=job.optInt("cateId",0);//1210
		cateCode=job.optString("cateCode");
		parentCateId=job.optInt("parentCateId");
		cateName=job.optString("cateName");
		initChildren(job.optJSONArray("children"));
	}
	
	protected void initChildren(JSONArray jarr){
		if(jarr==null) return;
		children = new Category[jarr.length()];
//		children = new ArrayList<Category>();
		for(int i=0;i<jarr.length();i++){
			Category cat = new Category();
			cat.initJackJson(jarr.optJSONObject(i));
			children[i]=cat;
//			children.add(cat);
		}
	}
	
	
	public Category[] getChildren() {
		return children;
	}
	public void setChildren(Category[] children) {
		this.children = children;
	}
	public int getCateLevel() {
		return cateLevel;
	}
	public void setCateLevel(int cateLevel) {
		this.cateLevel = cateLevel;
	}
	public int getCateId() {
		return cateId;
	}
	public void setCateId(int cateId) {
		this.cateId = cateId;
	}
	public String getCateCode() {
		return cateCode;
	}
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}
	public int getParentCateId() {
		return parentCateId;
	}
	public void setParentCateId(int parentCateId) {
		this.parentCateId = parentCateId;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
	
}
