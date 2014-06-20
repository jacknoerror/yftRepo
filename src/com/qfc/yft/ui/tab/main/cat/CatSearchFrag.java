package com.qfc.yft.ui.tab.main.cat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.keyword.SearchKeywordReq;
import com.qfc.yft.net.action.member.SearchCardsByKeywordReq;
import com.qfc.yft.net.action.product.SearchProductReq;
import com.qfc.yft.net.action.shop.SearchShopForIphoneReq;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.ui.adapter.mj.ListAdapterImagine.ImagineItemInfo;
import com.qfc.yft.ui.adapter.mj.ListAdapterSearchHistory.SearchHistoryItemInfo;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.ui.custom.list.MyJackListView;
import com.qfc.yft.ui.custom.list.MyJackListView.OnGetPageListener;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.LIIProduct;

public class CatSearchFrag extends CatAbsContentFragment implements OnCheckedChangeListener, OnGetPageListener, OnItemClickListener, TextWatcher{
	private static final int FIRSTiD = R.id.radio1;
	JackRadioViewGroup jRadios  ;
	private FrameLayout jrFrame;
//	private final String[] nAMESs = new String[]{"产 品","企 业","人 脉"};
	private EditText searchEdit;
	private Map<ListItemImpl.Type, MyJackListView> jlvMap;
	private MyJackListView currentJlv;
	private View emptyImageView;
	private TextView emptyTextView;
	
	@Override
	public int getLayoutRid() {
		return 0;
	}
	
	@Override
	public void initView() {
		super.initView();
		jlvMap = new HashMap<ListItemImpl.Type, MyJackListView>();
		
		jRadios = new JackRadioViewGroup(getActivity());
		jRadios.initBtns(R.layout.view_radiogroup_search,this);
//		jRadios.setOnCheckedChangeListener( this);
		mView = jRadios;
		mView.setBackgroundColor(getResources().getColor(android.R.color.white));
		jrFrame = jRadios.mFrame;
		
		emptyImageView = mInflator.inflate(R.layout.emptyview_img_noresult, null);
		emptyTextView = new TextView(getActivity());
		emptyTextView.setTextAppearance(getActivity(), R.style.Nodata_textview);
		emptyTextView.setText("没有记录");
		jrFrame.addView(emptyImageView);
		jrFrame.addView(emptyTextView);
		
		CatTopBarFrag top = (CatTopBarFrag)fraMana.findFragmentByTag(CatTopBarFrag.class.getSimpleName());
		if(null!=top) {
			searchEdit = top.getEdit();
			searchEdit.addTextChangedListener(this);
			top.getBtnSearch().setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					goSearch();
					
				}
			});
		}
		
		jRadios.getRadioGroup().check(FIRSTiD);
	}

	protected void goSearch() {
		if(searchEdit==null) return;//"" enabled 0505
		String keyword = searchEdit.getText().toString();
		MyJackListView ccurrentJlv = getJackListViewByType((Type) jrFrame.getTag());//
		ccurrentJlv.setup();	
		switchTabcontent(ccurrentJlv);
		LocalSearchHistoryManager.getInstance().add(keyword, jRadios.getRadioGroup().getCheckedRadioButtonId()+"");
		emptyTextView.setVisibility(View.GONE);
		emptyImageView.setVisibility(View.GONE);
		
//		hideSoftKeyboard();//0325 原来位置在gosearch调用的后面 即onclick
		JackUtils.hideSoftKeyboard(getActivity());//tobe test
		
	}

	private List<ListItemImpl> getLocalData(int tid) {
//		Log.i(TAG, tid+"::tid::");
		List<ListItemImpl> list = new ArrayList<ListItemImpl>();
		for(String s:LocalSearchHistoryManager.getInstance().getList(tid+"")){
			SearchHistoryItemInfo item = new SearchHistoryItemInfo();
			item.history_str=s;
			list.add(0,item);
		}
		return list;
	}
	
	/*
	 * 调用该方法将赋值currentJlv
	 */
	private MyJackListView getJackListViewByType(ListItemImpl.Type type){
		if(null==type) throw new IllegalStateException("search list type should not be null!");
//		JackListView jlv = null;
		MyJackListView ccurrentJlv = jlvMap.get(type);
		if(null!=ccurrentJlv) return ccurrentJlv;
		ccurrentJlv = new MyJackListView(getActivity(),type);
		jlvMap.put(type, ccurrentJlv);
		//TODO emptyView 优化
		
		switch (type) {
		case ITEMTYPE_COMPANY_SEARCH:
		case ITEMTYPE_PRODUCT_SEARCH:
		case ITEMTYPE_PEOPLE_SEARCH:
			ccurrentJlv.setOnGetPageListener(this);//don't move to common--keywords..
			ccurrentJlv.setEmptyView(emptyImageView);
			break;
		case ITEMTYPE_LOCALHISTORY:
			ccurrentJlv.setOnItemClickListener(this);
				ccurrentJlv.setEmptyView(emptyTextView);
			break;
		case ITEMTYPE_IMAGINE:
			ccurrentJlv.setOnItemClickListener(this);
			break;
		default:
			break;
		}
		
		return ccurrentJlv;
	}
	
	private void switchTabcontent(ListView view){
//		frameContainer.removeAllViews();
		if(null!=currentJlv)jrFrame.removeView(currentJlv);
		jrFrame.addView(view);
		currentJlv = (MyJackListView)view;
	}

	@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
	//		Log.i(TAG, String.format("(%s)(%s)(%s)",checkedId+"","",""));
			if(null!=searchEdit)searchEdit.setText("");//0312  
			ListItemImpl.Type t = null;
			int index = checkedId-FIRSTiD ;
			//mark the search type for goSearch();
			if(index==0) t = ListItemImpl.Type.ITEMTYPE_PRODUCT_SEARCH;
			else if(index ==1) t = ListItemImpl.Type.ITEMTYPE_COMPANY_SEARCH;
			else if(index ==2) t = ListItemImpl.Type.ITEMTYPE_PEOPLE_SEARCH;
			jrFrame.setTag(t);
	//		searchEdit.setHint(HINTS[checkedId-FIRSTiD]);//0312
			
			emptyTextView.setVisibility(View.GONE);
			emptyImageView.setVisibility(View.GONE);
			
			MyJackListView history = getJackListViewByType(ListItemImpl.Type.ITEMTYPE_LOCALHISTORY);
			history.updateList(getLocalData(checkedId));
			switchTabcontent(history);
		}

	@Override
	public void page(MyJackListView qListView, int pageNo) {
		ActionRequestImpl req = null;
		String keyword = searchEdit.getText().toString();
		switch (qListView.getType()) {
		case ITEMTYPE_PRODUCT_SEARCH:
			req = new SearchProductReq(keyword, Const.DEFULAT_PAGESIZE	, pageNo);
			break;
		case ITEMTYPE_COMPANY_SEARCH:
			req = new SearchShopForIphoneReq(keyword, Const.DEFULAT_PAGESIZE, pageNo);
			break;
		case ITEMTYPE_PEOPLE_SEARCH:
			req = new SearchCardsByKeywordReq(keyword, Const.DEFULAT_PAGESIZE, pageNo);
			break;
		default:
			break;
		}
		if(null!=req)ActionBuilder.getInstance().request(req, qListView);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Object itemAtPosition = parent.getItemAtPosition(position);
		switch (currentJlv.getType()) {
		case ITEMTYPE_IMAGINE:
			searchEdit.setText(((ImagineItemInfo)itemAtPosition).iii_name);
			goSearch();
			break;
		case  ITEMTYPE_LOCALHISTORY:
			searchEdit.setText((((SearchHistoryItemInfo)itemAtPosition).history_str).trim());//trim
			goSearch();
			break;
		case ITEMTYPE_PRODUCT_SEARCH:
			MyPortal.goProduct( getActivity(),  (LIIProduct)itemAtPosition);
			break;
		default:
			break;
		}
		
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {
		Log.i(TAG, String.format("(%s)(%s)(%s)",start+"","before",s+""));
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable s) {
		Log.i(TAG, String.format("(%s)(%s)(%s)",searchEdit.hasFocus()+"","after",""));
		//TODO xx btn
		//获得searchType字段
		ListItemImpl.Type t = (Type) jrFrame.getTag();
		if(null==t||t==ListItemImpl.Type.ITEMTYPE_PEOPLE_SEARCH) return;
		String searchType = t==ListItemImpl.Type.ITEMTYPE_COMPANY_SEARCH?"company":"product";
		MyJackListView fraImagine = getJackListViewByType(ListItemImpl.Type.ITEMTYPE_IMAGINE);//{keyword=h, pageSize=3, searchType=product}
		
		//发送请求
		ActionBuilder.getInstance().cancelLastReq();//终止上次请求
		ActionRequestImpl req = new SearchKeywordReq(searchEdit.getText().toString().trim(), Const.DEFULAT_PAGESIZE, searchType);
		ActionBuilder.getInstance().request(req , fraImagine);
		switchTabcontent(fraImagine);
		
		
	}
	
}
