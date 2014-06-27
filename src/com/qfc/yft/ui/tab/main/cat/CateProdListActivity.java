package com.qfc.yft.ui.tab.main.cat;

import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.product.SearchProductReq;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.ui.custom.list.MspPage;
import com.qfc.yft.ui.custom.list.MyJackListView;
import com.qfc.yft.ui.custom.list.MyJackListView.OnGetPageListener;
import com.qfc.yft.vo.LIIProduct;

public class CateProdListActivity extends MyTitleActivity implements
		OnItemClickListener {
	final String TAG = CateProdListActivity.class.getSimpleName();

	String keyword;
	int requestingPage = -1;

	int total;// 0413

	// ListView plListView;
	// ProductPageInfo currentPageInfo;
	List<ListItemImpl> plProductList;
	// ListAdapterProduct plAdapter;
	MyJackListView listFra;
	FrameLayout frameContainer;
	EditText plEdit;

	private void init() {
		keyword = getIntent().getStringExtra("keyword");
		titleManager.setTitleName(keyword);
		titleManager.initTitleBack();

		//
		listFra = new MyJackListView(this,
				ListItemImpl.Type.IP_PRODUCT_SEARCH) {
			@Override
			public boolean response(String result) throws JSONException {
				super.response(result);
				MspPage qpi = listFra.getCurrentPage();
				if (total == 0 && null != qpi) {
					total = qpi.totalCount;
					if (plEdit != null)
						plEdit.setText(String.format("共%d件商品，%s", total,
								"点击从结果中搜索"));
				}
				return true;
			}
		};
		listFra.setOnGetPageListener(new OnGetPageListener() {

			@Override
			public void page(MyJackListView qListView, int pageNo) {
				ActionRequestImpl actReq = new SearchProductReq(keyword, null,Const.DEFULAT_PAGESIZE, pageNo);
				ActionBuilder.getInstance().request(actReq, qListView);
			}
		});
		listFra.setOnItemClickListener(this);
		listFra.setup();
		if (null == frameContainer) {
			frameContainer = (FrameLayout) findViewById(R.id.frame_productslist);
			frameContainer.addView(listFra);
		}
		listFra.requestFocus();

		plEdit = ((EditText) findViewById(R.id.et_products));
		plEdit.setFocusable(false);
		plEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Intent intent = new Intent();
				 * intent.setClass(ProductListActivity.this, HubActivity.class);
				 * intent.putExtra(NetConst.EXTRAS_HUB_TAB, 1);
				 * intent.putExtra(NetConst.EXTRAS_HUB_KEYWORD, keyword);
				 * intent.putExtra(NetConst.EXTRAS_SINGLEBACK, true);
				 * startActivity(intent);
				 */
				// MyData.data().getHostTab().setCurrentTab(2);//不行
				MyPortal.goCatnSrch(CateProdListActivity.this, 1,keyword);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		LIIProduct product = (LIIProduct) parent.getItemAtPosition(position);
		if (null == product) return;
		MyPortal.goProduct(this, product);
	}

	@Override
	public int getLayoutRid() {
		return R.layout.activity_productlist;
	}

	@Override
	public void initView() {
		init();
	}

}
