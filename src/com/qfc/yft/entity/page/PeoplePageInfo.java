package com.qfc.yft.entity.page;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.listitem.LIIPeople;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;



public class PeoplePageInfo extends QfcPageInfo {
	
	
	List<ListItemImpl> peopleList;
	
	@Override
	public List<ListItemImpl> getDataList() {
		if(peopleList!=null) return peopleList;//如果有了，直接返回
		peopleList = new ArrayList<ListItemImpl>();
		for(int i=0;i<infoArr.length();i++){
			LIIPeople pp = new LIIPeople();
			try {//compName":"测试公司","realName":"周强","memberSex":1,"memberPosition":"JAVA工程师"
				JSONObject job = infoArr.getJSONObject(i);
//				pp.phoneDdd = job.getString(PHONEDDD);
				if(job.has(CONTACTVISIBLE))pp.contactVisible = job.optInt(CONTACTVISIBLE);
				if(job.has(ACCOUNTID))pp.accountId = job.optInt(ACCOUNTID);
				if(job.has(DEPARTMENT))pp.department = job.optString(DEPARTMENT);
				if(job.has(COLLEGE))pp.college = job.optString(COLLEGE);
				if(job.has(PHONEIDD))pp.phoneIdd = job.optInt(PHONEIDD);
				if(job.has(CONTACT))pp.contact = job.optString(CONTACT);
				if(job.has(WANGWANG))pp.wangwang = job.optString(WANGWANG);
				if(job.has(PHONETEL))pp.phoneTel = job.optString(PHONETEL);
				if(job.has(WEBSITE))pp.webSite = job.optString(WEBSITE);
				if(job.has(COMPPROV))pp.compProv = job.optInt(COMPPROV);
				if(job.has(COMPPROFESSION))pp.compProfession = job.optString(COMPPROFESSION);
				if(job.has(COMPCOUNTY))pp.compCounty = job.optInt(COMPCOUNTY);
				if(job.has(USERNAME))pp.userName = job.optString(USERNAME);
				if(job.has(COMPMAINPRODUCT))pp.compMainProduct = job.optString(COMPMAINPRODUCT);
				if(job.has(QQ))pp.qq = job.optString(QQ);
				if(job.has(MEMBERPOSITION))pp.memberPosition = job.optString(MEMBERPOSITION);
				if(job.has(STATUS))pp.status = job.optInt(STATUS);
				if(job.has(COMPNAME))pp.compName = job.optString(COMPNAME);
				if(job.has(COMPCITY))pp.compCity = job.optInt(COMPCITY);
				if(job.has(PRIVATESIGNATURE))pp.privateSignature = job.optString(PRIVATESIGNATURE);
				if(job.has(HEADICON))pp.headIcon = job.optString(HEADICON);
				if(job.has(MEMBERSEX))pp.memberSex = job.optInt(MEMBERSEX);
				if(job.has(COMPADDRESS))pp.compAddress = job.optString(COMPADDRESS);
				if(job.has(EMAIL))pp.email = job.optString(EMAIL);
				if(job.has(REALNAME))pp.realName = job.optString(REALNAME);
				if(job.has(TEXTALK))pp.texTalk = job.optLong(TEXTALK);
				if(job.has(WECHAT))pp.wechat = job.optString(WECHAT);
				if(job.has(MOBILE))pp.mobile = job.optString(MOBILE);
				if(job.has("region"))pp.region=job.optString("region");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//
			
			peopleList.add(pp);//0227 opt
		}
		return peopleList;
	}
}
