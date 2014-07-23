package com.qfc.yft.ui.adapter.mj;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.ui.ImageLoaderHelper;
import com.qfc.yft.ui.custom.list.MspAdapter;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.vo.LIIProduct;
import com.qfc.yft.vo.Order;
import com.qfc.yft.vo.OrderProduct;

public class ListAdapterOrder extends MspAdapter {

	public class ViewHolder extends ViewHolderImpl{

		TextView tv_name,tv_status,tv_pname,tv_price,tv_count;
		ImageView icon;
		
		@Override
		public void init() {
			tv_name = (TextView)getHolderView().findViewById(R.id.tv_item_order_name);
			tv_status = (TextView)getHolderView().findViewById(R.id.tv_item_order_status);
			tv_pname = (TextView)getHolderView().findViewById(R.id.tv_item_order_desc);
			tv_price = (TextView)getHolderView().findViewById(R.id.tv_item_order_price);
			tv_count = (TextView)getHolderView().findViewById(R.id.tv_item_order_count);
			icon = (ImageView)getHolderView().findViewById(R.id.img_item_left);
			
		}

		@Override
		public void setup(int position) {
			Order itm = (Order) getItem(position);
			tv_name.setText(itm.getOrderNo());
			if(myScrollPageListView.getType()==Type.ORDER_BUY)
				tv_name.setText(itm.getSellerCompanyName());
			tv_status.setText(itm.getOrderStatusCh());
//			icon
			tv_count.setText(Html.fromHtml(String.format("共<font color=#fe5636>%d</font>种产品", itm.getOrderProductsNum())));
//			tv_desc.setText(itm.getOrderProducts().get)
			JSONArray orderProducts = itm.getOrderProducts();
			JSONObject job = null;
			if(null!=orderProducts&&0<orderProducts.length()&&null!=(job = orderProducts.optJSONObject(0))){
				OrderProduct pr = new OrderProduct(job);
				tv_price.setText("￥"+pr.getProductPrice());
				tv_pname.setText(pr.getProductName());
				ImageLoaderHelper.imageLoader.displayImage(pr.getImageUrl300X300(), icon);//
			}
			
			
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
