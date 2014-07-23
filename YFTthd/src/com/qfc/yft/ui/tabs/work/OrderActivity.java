package com.qfc.yft.ui.tabs.work;

import android.content.Intent;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.trade.GetOrdersReq;
import com.qfc.yft.net.action.trade.GetSellerOrdersReq;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.common.QRCaptureActivity;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.ui.custom.list.MyJackListView;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.ui.custom.list.MyJackListView.OnGetPageListener;
import com.qfc.yft.ui.tabs.main.cat.JackRadioViewGroup;
import com.qfc.yft.vo.Order;
import com.qfc.yft.vo.User;

/**
 * 订单界面，目前有销售和采购两种
 * UI实现有两种方案，
 * 1.每次切换时都刷新，用同一个listview；
 * 2.或者用是个listview，刷新过的不再刷新；
 * 目前用的第二种
 * @author taotao
 * @Date 2014-7-3
 */
public class OrderActivity extends MyTitleActivity implements OnCheckedChangeListener, OnItemClickListener, OnGetPageListener {

	private RadioGroup jrGroup;
	private SparseArray<MyJackListView> jlvMap;
	private FrameLayout frameContainer;
	private JackRadioViewGroup jRadio;
	private MyJackListView currentJlv;
	private String orderStatus;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_frame;
	}

	@Override
	public void initView() {
		int a = getIntent().getIntExtra(NetConst.EXTRAS_ORDERTYPE, 0);
		if(a==0) return;
		factory = new SimpleFactory(a);
		me = MyData.data().getMe();
		
		titleManager.initTitleBack();
		titleManager.setTitleName(factory.getTitle());
		
		FrameLayout frameUnderTitle = (FrameLayout)this.findViewById(R.id.frame_common);
		jRadio = new JackRadioViewGroup(this);
		jrGroup = jRadio.initBtns(factory.getLayoutId(), this);
		frameContainer = jRadio.mFrame;
		frameUnderTitle.addView(jRadio);

		jlvMap = new SparseArray<MyJackListView>();
		
		frameContainer.addView(emptyText = MyJackListView.getEmptyTextView(this, "暂无订单"));
		
		jrGroup.check(R.id.radio1);//
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		MyJackListView jlv = jlvMap.get(checkedId);
		if(null==jlv) {
			jlv = initJlv(checkedId, factory.getType() );
		}else if(jlv==currentJlv) return;//
			switch (checkedId) {//sell;buy
			case R.id.radio1://全部
				orderStatus = null;
				break;
			case R.id.radio2://待确认；代付款
				orderStatus = factory.type==SELL?GetOrdersReq.OStatus_2CNFM:GetOrdersReq.OStatus_2PAY;
				break;
			case R.id.radio3://代发货，待确认
				orderStatus = factory.type==SELL?GetOrdersReq.OStatus_2SEND:GetOrdersReq.OStatus_2CNFM;
				break;
			case R.id.radio4://带退款；带评价
				orderStatus = factory.type==SELL?GetOrdersReq.OStatus_2REFUND:GetOrdersReq.OStatus_2BCMMT;
				break;
				
			default:
				break;
			}
		
		if(null!=jlv)switchJlv(jlv);
		
	}

	/**
	 * @param checkedId
	 * @param type
	 * @return
	 */
	public MyJackListView initJlv(int checkedId, Type type) {
		MyJackListView jlv;
		jlv = new MyJackListView(this, type);
		jlvMap.put(checkedId, jlv);
		jlv.setOnItemClickListener(this);
		jlv.setOnGetPageListener(this);
		jlv.setDivider(getResources().getDrawable(R.color.bg_grey));
		jlv.setDividerHeight((int) getResources().getDimension(R.dimen.activity_vertical_margin));
//		jlv.setBackgroundColor(0xf1f1f4);
		return jlv;
	}

	/**
	 * @param jlv
	 */
	public void switchJlv(MyJackListView jlv) {
		if(null!=currentJlv) frameContainer.removeView(currentJlv);
		currentJlv= jlv ;
		frameContainer.addView(currentJlv);
		if(!jlv.isSetup())jlv.setup();
		if(null!=emptyText)jlv.setEmptyView(emptyText);
	}

	@Override
	public void page(MyJackListView qListView, int pageNo) {
		if(null==factory||null==me) return;
		ActionBuilder.getInstance().request(new GetOrdersReq(factory.type, me.getId(), orderStatus, pageNo, Const.DEFULAT_PAGESIZE), qListView);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Order item = (Order) parent.getItemAtPosition(position);
		Intent intent = new Intent();
		intent.putExtra(NetConst.EXTRAS_ORDERID, item.getOrderId());
		intent.setClass(this, OrderDetailActivity.class);
		startActivity(intent);
		
	}

	public final static int SELL = 0x01, BUY = 0x02;
	private SimpleFactory factory;
	private User me;
	private TextView emptyText;
	/**
	 * 根据type种类（如销售订单、采购订单）出产生成整个订单界面所需的参数
	 * @author taotao
	 * @Date 2014-7-3
	 */
	class SimpleFactory{
		public int type;
		public SimpleFactory(int type){
			this.type = type;
		}
		
		public String getTitle(){
			String result="";
			if(type == SELL){
				result= getString(R.string.titlename_order_sell);
			}else if(type==BUY){
				result= getString(R.string.titlename_order_buy);
			}
			return result;
		}
		
		public int getLayoutId(){
			int result = 0;
			
			if(type == SELL){
				result= R.layout.view_radiogroup_order_sell;
			}else if(type==BUY){
				result= R.layout.view_radiogroup_order_buy;
			}
			return result;
		}
		
		public ListItemImpl.Type getType(){
			ListItemImpl.Type result = null; 
			if(type == SELL){
				result= ListItemImpl.Type.ORDER_SELL;
			}else if(type==BUY){
				result= ListItemImpl.Type.ORDER_BUY;
			}
			return result;
		}
		
		
	}
	
}
