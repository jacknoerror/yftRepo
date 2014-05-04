package com.qfc.yft.ui.tabs.main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;


import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.listitem.LIIProduct;
import com.qfc.yft.entity.page.QfcPageInfo;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.current.CurrentProductActivity;
import com.qfc.yft.ui.custom.list.JackListView;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;
import com.qfc.yft.ui.custom.list.JackListView.OnGetPageListener;
import com.qfc.yft.ui.tabs.HubActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ProductListActivity extends Activity implements OnItemClickListener{
	final String TAG  = ProductListActivity.class.getSimpleName();
	
	String keyword;
	int requestingPage=-1;
	
	int total;//0413
	
//	ListView plListView;
//	ProductPageInfo currentPageInfo;
	List<ListItemImpl> plProductList;
//	ListAdapterProduct plAdapter;
	JackListView listFra;
	FrameLayout frameContainer;
	EditText plEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productlist);
		init();
	}

	private void init() {
		keyword = getIntent().getStringExtra("keyword");
		((TextView)findViewById(R.id.tv_title)).setText(keyword);
//		search(1);
		
		//
		listFra = new JackListView(this, ListItemImpl.ITEMTYPE_PRODUCT_SEARCH){
			@Override
			public void response(String result) throws JSONException {
				super.response(result);
				QfcPageInfo  qpi = getCurrentPageInfo();
				if(total==0&&null!=qpi){
					total=qpi.totalCount;
					if(plEdit!=null)plEdit.setText(String.format("共%d件商品，%s", total,getString(R.string.hint_searchresult)));
				}
			}
		};
		listFra.setOnGetPageListener(new OnGetPageListener() {
			
			@Override
			public void page(JackListView qListView, int pageNo) {
				new HttpRequestTask(qListView).execute(YftValues.getHTTPBodyString(RequestType.SEARCH_PRODUCT, keyword,YftValues.DEFULAT_PAGESIZE+"",pageNo+""));
			}
		});
		listFra.setOnItemClickListener(this);
		listFra.setup();
		if(null==frameContainer) {
			frameContainer = (FrameLayout)findViewById(R.id.frame_productslist);
			frameContainer.addView(listFra);
		}
		listFra.requestFocus();
		
		//TODO 共XX件产品
		plEdit = ((EditText)findViewById(R.id.et_products));
		plEdit.setFocusable(false);
		plEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ProductListActivity.this, HubActivity.class);
				intent.putExtra(YftValues.EXTRAS_HUB_TAB, 1);
				intent.putExtra(YftValues.EXTRAS_HUB_KEYWORD, keyword);
				intent.putExtra(YftValues.EXTRAS_SINGLEBACK, true);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
//				finish();
//				YftData.data().getHostTab().setCurrentTab(2);//不行
			}
		});
	}
	
	


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		LIIProduct product = (LIIProduct)parent.getItemAtPosition(position);
		if(null==product) return;
		YftData.data().storeProduct(product);
		Intent intent = new Intent();
		intent.setClass(this, CurrentProductActivity.class);
		intent.putExtra("productId", product.getProductId());
		startActivity(intent);
	}

}
