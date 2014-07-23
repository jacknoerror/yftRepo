package com.qfc.yft.ui.adapter.mj;

import java.util.List;

import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.ui.custom.list.MspAdapter;

public class ListAdapterSearchHistory extends MspAdapter {
	public class ViewHolderSH extends ViewHolderImpl{
		TextView tv_his;
		
		@Override
		public int getLayoutId() {
			return R.layout.item_list_searchhistory;
		}

		@Override
		public void init() {
			tv_his = (TextView)getHolderView().findViewById(R.id.tv_searchhistory);
			
		}

		@Override
		public void setup(int position) {
			SearchHistoryItemInfo shi = (SearchHistoryItemInfo)getItem(position);
			tv_his.setText(shi.history_str);
			
		}
		
	}

	public static class SearchHistoryItemInfo implements ListItemImpl {
		public String history_str;
		public SearchHistoryItemInfo(){
			history_str="";
		}
		
	}
	
	public ListAdapterSearchHistory( ) {
		super( );
	}
	public ListAdapterSearchHistory(List<ListItemImpl> contentList) {
		super(contentList);
	}

	@Override
	public ViewHolderImpl getHolderInstance() {
		return new ViewHolderSH();
	}

}
