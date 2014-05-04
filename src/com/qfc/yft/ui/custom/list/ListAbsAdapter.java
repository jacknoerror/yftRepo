package com.qfc.yft.ui.custom.list;


import java.util.ArrayList;
import java.util.List;

import com.qfc.yft.YftApplication;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ListAbsAdapter extends BaseAdapter {
	public abstract class ViewHolderImpl{
		View holderView;
		public ViewHolderImpl(){
			holderView = LayoutInflater.from(context).inflate(getLayoutId(), null);
			init();
			holderView.setTag(this);
		}
		public abstract int getLayoutId();
		public abstract void init();
		public abstract void setup(int position);
		
		public View getHolderView(){
			return holderView;
		}
	}
	
	public interface ListItemImpl{
		public static final int ITEMTYPE_PRODUCT_SEARCH = 110;
		public static final int ITEMTYPE_COMPANY_SEARCH = 111;
		public static final int ITEMTYPE_PEOPLE_SEARCH = 112;
		public static final int ITEMTYPE_LOCALHISTORY = 113;
		public static final int ITEMTYPE_IMAGINE = 114;
		public static final int ITEMTYPE_PEOPLE_MY = 115;
	}

	SparseArray<View> viewMap;
	List<ListItemImpl> contentList;
	Context context;
	
	public ListAbsAdapter(){
		this(new ArrayList<ListItemImpl>());
	}
	public ListAbsAdapter(List<ListItemImpl> contentList){
		this.contentList = contentList;
		viewMap = new SparseArray<View>();
		this.context = YftApplication.getApp();
	}
	

	@Override
	public int getCount() {
		return getList().size();
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public ListItemImpl getItem(int position) {
		return getList().get(position);//
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=viewMap.get(position);
		ViewHolderImpl holder;
		
		if(null==view){
			holder= getHolderInstance();
			view = holder.getHolderView();
			viewMap.put(position, view);
		}else{
			holder= (ViewHolderImpl)view.getTag();
		}
		
		holder.setup(position);
		
		return view;
	}
	
	public List<ListItemImpl> getList(){
		if(contentList==null) contentList=new ArrayList<ListAbsAdapter.ListItemImpl>();
		return contentList;
	}
	
	public abstract ViewHolderImpl getHolderInstance();
	
}
