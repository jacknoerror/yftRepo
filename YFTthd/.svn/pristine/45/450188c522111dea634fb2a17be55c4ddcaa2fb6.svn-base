package com.qfc.yft.ui.adapter.mj;

import java.util.List;

import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.ui.custom.list.MspAdapter;

public class ListAdapterImagine extends MspAdapter {
	public class ViewHolderIM extends ViewHolderImpl{
		TextView tv_imag;
		TextView tv_count;
		
		@Override
		public int getLayoutId() {
			return R.layout.item_list_imagine;
		}

		@Override
		public void init() {
			tv_imag = (TextView)getHolderView().findViewById(R.id.tv_imagine_name);
			tv_count = (TextView)getHolderView().findViewById(R.id.tv_imagine_count);
			
		}

		@Override
		public void setup(int position) {
			ImagineItemInfo shi = (ImagineItemInfo)getItem(position);
			tv_imag.setText(shi.iii_name);
			if(!shi.iii_count.equals("null ")&&!shi.iii_count.isEmpty()){
				tv_count.setText("约"+shi.iii_count+"条信息");
			}
		}
		
	}

	public static class ImagineItemInfo implements ListItemImpl {
		public String iii_name,iii_count;
		public ImagineItemInfo(){
			iii_name="";
			iii_count="";
		}
		
	}
	
	public ListAdapterImagine( ) {
		super( );
	}
	public ListAdapterImagine(List<ListItemImpl> contentList) {
		super(contentList);
	}

	@Override
	public ViewHolderImpl getHolderInstance() {
		return new ViewHolderIM();
	}

}
