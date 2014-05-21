package com.qfc.yft.ui.custom.list;

import java.util.List;

import com.qfc.yft.R;
import com.qfc.yft.entity.listitem.LIIProduct;
import com.qfc.yft.utils.JackImageLoader;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdapterProduct extends ListAbsAdapter {
	public class ViewHolder extends ViewHolderImpl{
		ImageView icimg;
		TextView pname;
		TextView attrs[];
		TextView price;
		TextView location;
		
		@Override
		public int getLayoutId() {
			return R.layout.item_list_product;
		}

		@Override
		public void init() {
			  icimg = (ImageView)getHolderView().findViewById(R.id.img_item_product);
			  pname = (TextView)getHolderView().findViewById(R.id.tv_item_pname);
			  attrs = new TextView[4];
			  attrs[0] = (TextView)getHolderView().findViewById(R.id.tv_item_attr1);
			  attrs[1] = (TextView)getHolderView().findViewById(R.id.tv_item_attr2);
			  attrs[2] = (TextView)getHolderView().findViewById(R.id.tv_item_attr3);
			  attrs[3] = (TextView)getHolderView().findViewById(R.id.tv_item_attr4);
			  price = (TextView)getHolderView().findViewById(R.id.tv_item_price);
			  location = (TextView)getHolderView().findViewById(R.id.tv_item_location);

		}

		@Override
		public void setup(int position) {
			LIIProduct ssi = getItem(position);
			if(ssi.isPrivate()){//0302
				icimg.setImageResource(R.drawable.lock);
			}else{
				if(!ssi.getMainPic().isEmpty())JackImageLoader.justSetMeImage(ssi.getMainPic(), icimg);
			}
            pname.setText(ssi.getProductName());
            String[] attrStrs = ssi.getProductPropArray();
            for(int i=0;i<4&&i<attrStrs.length;i++){
            	attrs[i].setText(attrStrs[i]);//0228
            }
            price.setText(ssi.getProductPrice());
            location.setText(ssi.getProductRegion());
		}
		
	}

	/*public static class ProductItemInfo implements ListItemImpl {
		public String imgPath,pname_str,attrs_str,price_str,location_str;
		public ProductItemInfo(){
			imgPath="";
			pname_str="";
			attrs_str="";
			price_str="";
			location_str="";
		}
		
	}*/
	public ListAdapterProduct(){
		super();
	}
	public ListAdapterProduct(List<ListItemImpl> contentList) {
		super(contentList );
	}
	
	@Override
	public ViewHolderImpl getHolderInstance() {
		return new ViewHolder();
	}

	@Override
	public LIIProduct getItem(int position) {
		return (LIIProduct)super.getItem(position);
	}

	

}
