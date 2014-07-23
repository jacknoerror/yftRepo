package com.qfc.yft.ui.tabs.work;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.trade.GetOrderDetail;
import com.qfc.yft.ui.ImageLoaderHelper;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.shop.Company;
import com.qfc.yft.util.TestDataTracker;
import com.qfc.yft.vo.OrderCompany;
import com.qfc.yft.vo.OrderDetail;
import com.qfc.yft.vo.OrderProduct;

public class OrderDetailActivity extends MyTitleActivity implements ActionReceiverImpl {

	private LinearLayout mLayout_products,mLayout_details,mLayout_results;
	private OrderDetail od;
	private LayoutInflater mInflater;

	
	/**
	 * should be called once only
	 */
	private void updateUI() {
		//head
		TextView tv1,tv2;
		tv1 = (TextView)this.findViewById(R.id.tv_item_order_name);
		tv2 = (TextView)this.findViewById(R.id.tv_item_order_status);
		tv1.setText(od.getOrderNo());//
		tv2.setText(od.getOrderStatusCh());
		//products
		setDivider(mLayout_products);
		JSONArray opJar = od.getOrderProducts();
		for(int i=0;i<opJar.length();i++){
			OrderProduct op = new OrderProduct(opJar.optJSONObject(i));
			View view = mInflater.inflate(R.layout.merge_orderproductitem,mLayout_products,true);
//			mLayout_products.addView(view);
			ImageView img = (ImageView) view.findViewById(R.id.img_item_left);
			TextView tv_p_d = (TextView) view.findViewById(R.id.tv_item_order_desc);
			TextView tv_p_p = (TextView) view.findViewById(R.id.tv_item_order_price);
			TextView tv_p_c = (TextView) view.findViewById(R.id.tv_item_order_count);
			tv_p_d.setText(op.getProductName());
			tv_p_p.setText("￥"+op.getProductPrice());
			tv_p_c.setText("数量："+op.getProductUnit());
			ImageLoaderHelper.imageLoader.displayImage(op.getImageUrl300X300(), img);
		}
		
		setDivider(mLayout_details);
		//购销通订单状态
		Map<String, String> map0 ;
		map0 = new HashMap<String, String>();
		map0.put(null, od.getOtherOrderStatusCh());
		addDetailLayout("购销通订单状态",map0);
		//收货地址/买卖家信息/订单信息
		Map<String, String> map1,map2,map3;
		map1 = new HashMap<String, String>();
		map2 = new HashMap<String, String>();
		map3 = new HashMap<String, String>();
		map1.put("地\t\t\t\t址", od.getAddrAddress());
		map1.put("邮\t\t\t\t编", od.getAddrZipCode());
		map1.put("联\t系\t人", od.getAddrConsignee());
		map1.put("联系电话", od.getAddrMobile());
		addDetailLayout("收货地址",map1);
		
		OrderCompany companyInfo = od.getCompanyInfo();
		if (null != companyInfo) {
			map2.put("公司名称", companyInfo.getShopName());
			map2.put("联\t系\t人", companyInfo.getCompContacter());
			map2.put("联系电话", companyInfo.getCompMobile());
			addDetailLayout("卖家信息", map2);
		}
		
		map3.put("订单编号", od.getOrderNo());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map3.put("下单时间", od.getOrderTime()!=null?sdf.format(od.getOrderTime().toDate()):"");//od.getOrderTime().toString());
		map3.put("确认时间", od.getConfirmTime()!=null?sdf.format(od.getConfirmTime().toDate()):"");
		addDetailLayout("订单信息",map3);
		//订单总价
		TextView titleView = (TextView) mInflater.inflate(R.layout.view_tv_od_title, null);//
		titleView .setText("订单总价");
		titleView.setTextColor(getResources().getColor(R.color.down_orange));
		mLayout_details.addView(titleView);
		LinearLayout lastLayout = (LinearLayout) mInflater.inflate(R.layout.view_layout_od_content, null);
		lastLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
		TextView lastText = (TextView) lastLayout.getChildAt(1);
		lastText.setText(Html.fromHtml(
				String.format("产品总价（￥%.2f)<br>- 优惠金额（￥%.2f）<br>+ 运输费用（￥%.2f）<br>= <font color=#fe5636>￥%.2f</font>", 
						0f+od.getProductAmount(),0f+od.getOrderDiscount(),0f+od.getShipFee(),0f+od.getOrderAmount())  )); 
		
		mLayout_details.addView(lastLayout);
	}

	/**
	 * @param a
	 * @throws NotFoundException
	 */
	@SuppressLint("NewApi")
	public void setDivider(LinearLayout a) throws NotFoundException {
		a.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE|LinearLayout.SHOW_DIVIDER_END);
		a.setDividerDrawable(getResources().getDrawable(R.drawable.divider_h_grey_png));
		a.setDividerPadding(2);
	}

	private void addDetailLayout(String title, Map<String, String> contentMap) {
		if(null==contentMap) return;
		TextView titleView = (TextView) mInflater.inflate(R.layout.view_tv_od_title, null);// 
		titleView .setText(title);
		mLayout_details.addView(titleView);
		Set<String> keySet = contentMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String contentLeft = iterator.next();
			String contentRight = contentMap.get(contentLeft);
			LinearLayout itemInDetail = (LinearLayout) mInflater.inflate(R.layout.view_layout_od_content, null);
			itemInDetail.setOrientation(LinearLayout.HORIZONTAL);
			TextView child1,child2;
			if(contentLeft!=null){
				child1 =(TextView) itemInDetail.getChildAt(0);
				child1.setText(contentLeft+":");
				child1.setVisibility(View.VISIBLE);
//				itemInDetail.addView(child1);
			}
			child2 = (TextView) itemInDetail.getChildAt(1);
			child2.setText(contentRight);
//			itemInDetail.addView(child2);
			mLayout_details.addView(itemInDetail);
		}
		
	}

	@Override
	public int getLayoutRid() {
		return R.layout.activity_orderdetail;
	}

	@Override
	public void initView() {
		mInflater = LayoutInflater.from(this);
		
		titleManager.setTitleName(getString(R.string.titlename_order_detail));
		titleManager.initTitleBack();

		mLayout_products = (LinearLayout) this.findViewById(R.id.layout_orderdetail_products);
		mLayout_details = (LinearLayout) this.findViewById(R.id.layout_orderdetail_details);
		mLayout_results = (LinearLayout) this.findViewById(R.id.layout_orderdetail_results);
		
		int a = getIntent().getIntExtra(NetConst.EXTRAS_ORDERID, 0);
		int b = getIntent().getIntExtra(NetConst.EXTRAS_USERTYPE, 0);
		if(a==0) return;
		
		ActionBuilder.getInstance().request(new GetOrderDetail(a,b), this);
//		TestDataTracker.simulateConnection(this, new GetOrderDetail(a,b).getApiName());
	}

	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = ActionStrategies.getResultObject(result);
		if(null!=job){
			od = new OrderDetail(job);
			if(null!=od)updateUI();
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}

}
