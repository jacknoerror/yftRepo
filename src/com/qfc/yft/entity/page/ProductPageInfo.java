package com.qfc.yft.entity.page;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.SparseArray;

import com.qfc.yft.entity.listitem.LIIProduct;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;

public class ProductPageInfo extends QfcPageInfo {
	
	List<ListItemImpl> productList;
	
	public List<ListItemImpl> getProductListFromDataArr(){
		if(productList!=null) return productList;//������ˣ�ֱ�ӷ���
		productList = new ArrayList<ListItemImpl>();//
		for(int i=0;i<(null!=infoArr?infoArr.length():0);i++){
			LIIProduct pp = new LIIProduct();
			try {
				JSONObject job = infoArr.getJSONObject(i);
				pp.initJackJson(job);
				productList.add(pp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//
			
		}
		return productList;
	}
	
	@Override
	public List<ListItemImpl> getDataList() {
		return getProductListFromDataArr();
	}
	
	/**
	 * ������������ʱ�Լ�����ҳ������
	 * @param list
	 * @date 20140303
	 */
	public void setProductList(List<ListItemImpl> list){
		productList = list;
	}
}
