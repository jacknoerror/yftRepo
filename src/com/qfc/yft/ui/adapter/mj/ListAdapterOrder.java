package com.qfc.yft.ui.adapter.mj;

import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.ui.custom.list.MspAdapter;

public class ListAdapterOrder extends MspAdapter {

	public class ViewHolder extends ViewHolderImpl{

		TextView tv_name,tv_status,tv_desc,tv_price,tv_count;
		ImageView icon;
		
		@Override
		public void init() {
			tv_name = (TextView)getHolderView().findViewById(R.id.tv_item_order_name);
			tv_status = (TextView)getHolderView().findViewById(R.id.tv_item_order_status);
			tv_desc = (TextView)getHolderView().findViewById(R.id.tv_item_order_desc);
			tv_price = (TextView)getHolderView().findViewById(R.id.tv_item_order_price);
			tv_count = (TextView)getHolderView().findViewById(R.id.tv_item_order_count);
			icon = (ImageView)getHolderView().findViewById(R.id.img_item_left);
			
		}

		@Override
		public void setup(int position) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getLayoutId() {
			return R.layout.item_order;
		}
		
	}
	
	public ListAdapterOrder() {
		super();
	}


	@Override
	public ViewHolderImpl getHolderInstance() {
		return new ViewHolder();
	}

}
