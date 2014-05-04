package com.qfc.yft.entity.page;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;

public class QfcPageInfo implements PageValues{
	public int nextPage;
	public String orderBy="";
	public int pageSize;
	public int prePage;
	public boolean hasPre;
	public String order;
	public int totalCount;
	public boolean hasNext;
	public int pageNo;
	public int offset;
	public boolean orderBySetted;
	public boolean autoCount;
	public int first;
	public int totalPages;
	
	protected JSONArray infoArr;
	
	public void loadJob(JSONObject job) throws JSONException{//有几个字段可能已经废弃？
		if(job.has(RESULT))infoArr = job.getJSONArray(RESULT);
		if(job.has(NEXTPAGE))nextPage 	= job.getInt(NEXTPAGE);
		if(job.has(ORDERBY))orderBy 	= job.getString(ORDERBY);
		if(job.has(PAGESIZE))pageSize	= job.getInt(PAGESIZE);
		if(job.has(PREPAGE))prePage	= job.getInt(PREPAGE);
		if(job.has(HASPRE))hasPre 	= job.getBoolean(HASPRE);
		if(job.has(ORDER))order 	= job.getString(ORDER);
		if(job.has(TOTALCOUNT))totalCount = job.getInt(TOTALCOUNT);
		if(job.has(HASNEXT))hasNext 	= job.getBoolean(HASNEXT);
		if(job.has(PAGENO))pageNo 	= job.getInt(PAGENO);
		if(job.has(OFFSET))offset 	= job.getInt(OFFSET);
		if(job.has(ORDERBYSETTED))orderBySetted = job.getBoolean(ORDERBYSETTED);
		if(job.has(AUTOCOUNT))autoCount = job.getBoolean(AUTOCOUNT);
		if(job.has(FIRST))first 	= job.getInt(FIRST);
		if(job.has(TOTALPAGES))totalPages = job.getInt(TOTALPAGES);
	}
	
	public  List<ListItemImpl> getDataList(){return new ArrayList<ListItemImpl>();};//暂时不做abstract 
	
}
