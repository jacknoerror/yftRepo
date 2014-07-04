package com.qfc.yft.ui.tab.work;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.qfc.yft.R;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.trade.GetOrderDetail;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.vo.OrderDetail;

public class OrderDetailActivity extends MyTitleActivity implements ActionReceiverImpl {

	private LinearLayout mLayout;
	private OrderDetail od;

	private void updateUI() {
		//head
		
		//products
		
		//购销通订单状态
		
		//收货地址/买卖家信息/订单信息
		
		//订单总价
		
		
	}

	@Override
	public int getLayoutRid() {
		return R.layout.activity_orderdetail;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_order_detail));
		titleManager.initTitleBack();

		mLayout = (LinearLayout) this.findViewById(R.id.layout_scrollcontent);
		
		int a = getIntent().getIntExtra("orderid", 0);
		int b = getIntent().getIntExtra("usertype", 0);
		if(a==0) return;
		
		ActionBuilder.getInstance().request(new GetOrderDetail(a,b), this);
		
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
