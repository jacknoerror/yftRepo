package com.qfc.yft.ui.adapter.mj;

import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.ui.custom.list.MspAdapter;
import com.qfc.yft.util.JackImageLoader;
import com.qfc.yft.vo.LIICompany;


public class ListAdapterCompany extends MspAdapter {
	public class ViewHolder extends ViewHolderImpl{
		ImageView icimg;
		TextView cname;
		TextView major;
		TextView purchase;
		
		@Override
		public int getLayoutId() {
			return R.layout.item_list_company;
		}

		@Override
		public void init() {
			  icimg = (ImageView)getHolderView().findViewById(R.id.img_item_company);
			  cname = (TextView)getHolderView().findViewById(R.id.tv_item_cname);
			  major = (TextView)getHolderView().findViewById(R.id.tv_item_major);
			  purchase = (TextView)getHolderView().findViewById(R.id.tv_item_purchase);

		}

		@Override
		public void setup(int position) {
			LIICompany ssi = getItem(position);
            JackImageLoader.justSetMeImage(ssi.getShopLogoImage(), icimg);
            cname.setText(ssi.getShopName());
            if(!ssi.getMainProducts().isEmpty())major.setText("主营业务/服务: "+ssi.getMainProducts());
            if(!ssi.getCompPurchaseProduct().isEmpty())purchase.setText("采购信息: "+ssi.getCompPurchaseProduct());
		}
		
	}

	/*public static class CompanyItemInfo implements ListItemImpl {
		public String imgPath,cname_str,major_str;
		public CompanyItemInfo(){
			imgPath="";
			cname_str="";
			major_str="";
		}
		
	}*/
	public ListAdapterCompany() {
		super( );
	}
	public ListAdapterCompany(List<ListItemImpl> contentList) {
		super(contentList);
	}
	
	@Override
	public ViewHolderImpl getHolderInstance() {
		return new ViewHolder();
	}

	@Override
	public LIICompany getItem(int position) {
		return (LIICompany)super.getItem(position);
	}

	

}
