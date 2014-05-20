package com.qfc.yft.ui.tabs;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.data.LocalSearchHistoryManager;
import com.qfc.yft.entity.listitem.LIICompany;
import com.qfc.yft.entity.listitem.LIIPeople;
import com.qfc.yft.entity.listitem.LIIProduct;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.YftActivityGate;
import com.qfc.yft.ui.custom.list.JackListView;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;
import com.qfc.yft.ui.custom.list.ListAdapterImagine.ImagineItemInfo;
import com.qfc.yft.ui.custom.list.ListAdapterSearchHistory.SearchHistoryItemInfo;

/**
 * 
 * @author taotao
 *
 */
public class TabSearchFragment extends ContentAbstractFragment implements OnCheckedChangeListener
									,TextWatcher,JackListView.OnGetPageListener,OnItemClickListener{
	final String TAG = TabSearchFragment.class.getSimpleName();
	final int FIRSTiD = ListItemImpl.ITEMTYPE_PRODUCT_SEARCH;//R.id.btn_search_1_product;
	final String[] HINTS = new String[]{"输入您要搜索的产品","输入您要搜索的企业","可根据人名/产品/单位/企业/商会/院校等信息搜索"};
//	View viewProd,viewComp,viewPeop;
	SparseArray<JackListView> jlvMap;
	JackListView currentJlv;
	List<ListItemImpl> hProList,hComList,hPeoList;
	RadioGroup rGroup;
	RadioButton[] rBtns;
	EditText edit;
	ImageView btn_clear;
	FrameLayout frameContainer;
	View emptyTextView,emptyImageView;

	HttpRequestTask imagineTask;
	String keyword;
	@Override
	public void initView() {
		mView = mInflator.inflate(R.layout.fragment_search, null);
		
		initBtnsOfTabs();
		
//		new HttpRequestTask(this).execute(YftValues.getHTTPBodyString(RequestType.SEARCH_PRODUCT, ""));
//		new HttpRequestTask(this).execute(YftValues.getHTTPBodyString(RequestType.COLLECTION_SAVE, ""));
	}

	
	private void initBtnsOfTabs() {
		jlvMap = new SparseArray<JackListView>();
		rBtns = new RadioButton[3];
		rGroup = (RadioGroup)mView.findViewById(R.id.group_search);
		rBtns[0] = (RadioButton)mView.findViewById(R.id.btn_search_1_product);
		rBtns[1] = (RadioButton)mView.findViewById(R.id.btn_search_2_company);
		rBtns[2] = (RadioButton)mView.findViewById(R.id.btn_search_3_people);
		rBtns[0].setId(ListItemImpl.ITEMTYPE_PRODUCT_SEARCH);
		rBtns[1].setId(ListItemImpl.ITEMTYPE_COMPANY_SEARCH);
		rBtns[2].setId(ListItemImpl.ITEMTYPE_PEOPLE_SEARCH);
		emptyTextView = mView.findViewById(R.id.emptyview_search_1);
		emptyImageView= mView.findViewById(R.id.emptyview_search_2);
		
		rGroup.setOnCheckedChangeListener(this);
		
		
		TextView searchBtn = (TextView)mView.findViewById(R.id.tv_gosearch);
		searchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gosearch();
				
			}

			
		});
		edit = (EditText)mView.findViewById(R.id.et_search);
		btn_clear = (ImageView)mView.findViewById(R.id.img_clearsearch);
		btn_clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null!=edit){
					edit.setText("");
				}
				
			}
		});
		edit.addTextChangedListener(this);
	
		keyword=getActivity().getIntent().getStringExtra(YftValues.EXTRAS_HUB_KEYWORD);
		
		if(null!=rGroup)onCheckedChanged(rGroup, FIRSTiD);//0305
		if(null!=keyword) {
			edit.setText(keyword);
//			edit.requestFocus();
//			searchBtn.performClick();//0310
			gosearch();
		}
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		
	}
	@Override
	public void onHiddenChanged(boolean hidden) {
		if(!hidden){
			if(null!=edit){
				edit.requestFocus();
				try{//0504
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(edit, InputMethodManager.SHOW_FORCED);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}else{
			if(null!=edit)edit.clearFocus();
		}
		super.onHiddenChanged(hidden);
	}
	
	/**
	 * 
	 */
	private void gosearch() {
		if(edit==null) return;//"" enabled 0505
		keyword = edit.getText().toString();
		View tag = (View)rGroup.getTag();
		if(tag==null) return;
//		JackListView qlFra = null;
		JackListView ccurrentJlv = getJackListViewByType(tag.getId());//
		ccurrentJlv.setup();	
		switchTabcontent(ccurrentJlv);
		LocalSearchHistoryManager.getInstance().add(keyword, ccurrentJlv.getType()+"");
//		JackUtils.hideSoftKeyboard(getActivity());
		if(null!=emptyTextView)emptyTextView.setVisibility(View.GONE);
		if(null!=emptyImageView)emptyImageView.setVisibility(View.GONE);
		
		hideSoftKeyboard();//0325 原来位置在gosearch调用的后面 即onclick
	}

	/*
	 * 调用该方法将赋值currentJlv
	 */
	private JackListView getJackListViewByType(int type){
//		JackListView jlv = null;
		JackListView ccurrentJlv = jlvMap.get(type);
		if(null!=ccurrentJlv) return ccurrentJlv;
		ccurrentJlv = new JackListView(getActivity(),type);
		jlvMap.put(type, ccurrentJlv);
		// 附加类型操作  不设itemOnItemClick 则用默认
		//TODO emptyView 优化
		
		switch (type) {
		case ListItemImpl.ITEMTYPE_COMPANY_SEARCH:
		case ListItemImpl.ITEMTYPE_PRODUCT_SEARCH:
		case ListItemImpl.ITEMTYPE_PEOPLE_SEARCH:
			ccurrentJlv.setOnGetPageListener(this);//TODO move to common?
			ccurrentJlv.setEmptyView(emptyImageView);
			break;
		case ListItemImpl.ITEMTYPE_LOCALHISTORY:
			ccurrentJlv.setOnItemClickListener(this);
			//清空历史记录功能不要了
				/*View footerView = mInflator.inflate(R.layout.footer_delete, null);
				footerView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						LocalSearchHistoryManager.getInstance().deleteAll();
						onCheckedChanged(rGroup, ((View)rGroup.getTag()).getId());
						
					}
				});
				ccurrentJlv.addFooterView(footerView);
				ccurrentJlv.setFooterDividersEnabled(false);*/
				ccurrentJlv.setEmptyView(emptyTextView);
			break;
		case ListItemImpl.ITEMTYPE_IMAGINE:
			ccurrentJlv.setOnItemClickListener(this);
			break;
		default:
			break;
		}
		
		return ccurrentJlv;
	}
	
	private void switchTabcontent(ListView view){
		if(null == frameContainer) {
			frameContainer = (FrameLayout)mView.findViewById(R.id.frame_lists);
		}
//		frameContainer.removeAllViews();
		if(null!=currentJlv)frameContainer.removeView(currentJlv);// TODO tobe test
		/*if(null!=emptyTextView)frameContainer.addView(emptyTextView);//哇哈哈 0227
		if(null!=emptyImageView)frameContainer.addView(emptyImageView);//0228
*/		frameContainer.addView(view);
		currentJlv = (JackListView)view;
	}
	
	private List<ListItemImpl> getLocalData(int tid) {
		Log.i(TAG, tid+"::tid::");
		List<ListItemImpl> list = new ArrayList<ListItemImpl>();
		for(String s:LocalSearchHistoryManager.getInstance().getList(tid+"")){
			SearchHistoryItemInfo item = new SearchHistoryItemInfo();
			item.history_str=s;
			
			list.add(0,item);
		}
		return list;
	}



	



	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		View selected = (View)rGroup.getTag();
		View toSelect = rBtns[checkedId-FIRSTiD];
		if(null!=selected) {
			selected.setSelected(false);
			edit.setText("");//0312
		}
		edit.setHint(HINTS[checkedId-FIRSTiD]);//0312
		toSelect.setSelected(true);
		rGroup.setTag(toSelect);
		
		if(null!=emptyImageView)emptyImageView.setVisibility(View.INVISIBLE);//0228
		if(null!=emptyTextView)emptyTextView.setVisibility(View.INVISIBLE);//0228
		
		JackListView history = getJackListViewByType(ListItemImpl.ITEMTYPE_LOCALHISTORY);
		history.updateList(getLocalData(checkedId));
		switchTabcontent(history);
		
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}
	@Override
	public void afterTextChanged(Editable s) {
		Log.i(TAG, edit.hasFocus()+":focused edit");
		if(s.length()==0) {
			if(null!=btn_clear){
				btn_clear.setVisibility(View.INVISIBLE);
			}
			return;
		}
		if(null!=btn_clear) btn_clear.setVisibility(View.VISIBLE);
		View tag = (View)rGroup.getTag();
		if(null==tag) return;
		int tid = tag.getId();
		String imagineType="product";
		if(tid==ListItemImpl.ITEMTYPE_PRODUCT_SEARCH){
		}else if(tid==ListItemImpl.ITEMTYPE_COMPANY_SEARCH){
			imagineType = "company";
		}else{
			return;
		}
		if(edit.hasFocus()){
			
			JackListView fraImagine = getJackListViewByType(ListItemImpl.ITEMTYPE_IMAGINE);//{keyword=h, pageSize=3, searchType=product}
			if(null!=imagineTask){
				imagineTask.cancel(true);
			}
			imagineTask = new HttpRequestTask(fraImagine);
			imagineTask.execute(YftValues.getHTTPBodyString(
					RequestType.SEARCH_IMAGINE, 
					s.toString(),YftValues.DEFULAT_PAGESIZE+"",imagineType));
			switchTabcontent(fraImagine);
		}
	}

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(null==currentJlv) return;
		switch (currentJlv.getType()) {
		case ListItemImpl.ITEMTYPE_PRODUCT_SEARCH:
			LIIProduct prod = (LIIProduct)parent.getAdapter().getItem(position);
			
			YftActivityGate.goProduct(parent.getContext(),prod);
			break;
		case ListItemImpl.ITEMTYPE_COMPANY_SEARCH:
			LIICompany comp = (LIICompany)parent.getAdapter().getItem(position);
			
			YftActivityGate.goShop(parent.getContext(), comp.getShopId(), comp.getShopName(), comp.getHasMotion(), -1);
			break;
		case ListItemImpl.ITEMTYPE_PEOPLE_SEARCH:
			LIIPeople peop = (LIIPeople)parent.getAdapter().getItem(position);
			
			YftActivityGate.goPeople(parent.getContext(), peop);
			break;
		case ListItemImpl.ITEMTYPE_IMAGINE:
			ImagineItemInfo iii = (ImagineItemInfo)currentJlv.getAdapter().getItem(position);
			edit.setText(iii.iii_name.trim());
			gosearch();
			break;
		case ListItemImpl.ITEMTYPE_LOCALHISTORY:
			SearchHistoryItemInfo shi = (SearchHistoryItemInfo)currentJlv.getAdapter().getItem(position);
			edit.setText(shi.history_str.trim());
			gosearch();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void page(JackListView qListView, int pageNo) {
		switch (qListView.getType()) {
		case ListItemImpl.ITEMTYPE_PRODUCT_SEARCH:
			new HttpRequestTask(qListView).execute(YftValues.getHTTPBodyString(RequestType.SEARCH_PRODUCT, keyword,YftValues.DEFULAT_PAGESIZE+"",pageNo+""));
			break;
		case ListItemImpl.ITEMTYPE_COMPANY_SEARCH:
			new HttpRequestTask(qListView).execute(YftValues.getHTTPBodyString(RequestType.SEARCH, keyword,YftValues.DEFULAT_PAGESIZE+"",pageNo+""));
			break;
		case ListItemImpl.ITEMTYPE_PEOPLE_SEARCH:
			new HttpRequestTask(qListView).execute(YftValues.getHTTPBodyString(RequestType.CARDSEARCH, keyword,YftValues.DEFULAT_PAGESIZE+"",pageNo+""));
			break;
		default:
			break;
		}
		
	}

}
