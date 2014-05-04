package com.qfc.yft.ui.custom.list;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.entity.page.CompanyPageInfo;
import com.qfc.yft.entity.page.PeoplePageInfo;
import com.qfc.yft.entity.page.QfcPageInfo;
import com.qfc.yft.entity.page.ProductPageInfo;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;
import com.qfc.yft.ui.custom.list.ListAdapterImagine.ImagineItemInfo;
import com.qfc.yft.utils.JackUtils;
/**
 * ���ڽӿ�������⣬LIIProduct��LIIPeople����product��people������ϸ��Ϣ����LIICompany��Company���м�������ϸ֮��ͬ��
 * ��ʹ�øÿؼ�ʱ��ע����ȷ�Ĳ��裬�����Ƿ�Ҫ��������
 * �������Ŀ����У����Ըÿؼ���һ������
 */
/**
 * 
 * @author taotao
 *
 */
public class JackListView extends ListView implements HttpReceiver,OnScrollListener{
	
	public interface OnGetPageListener{
		public void page(JackListView qListView,int pageNo);
	}

	final String TAG = JackListView.class.getSimpleName();

	ListAbsAdapter qlfAdapter;
	QfcPageInfo currentPageInfo;
	OnGetPageListener gpListener;
	View moreView;
	
	List<ListItemImpl> everythingList;
	
	int listType=-1,requestingPage=-1;
	boolean isSetup;//0306
	
	public JackListView(Context context, int type) {
		super(context);
		listType = type;
		
		switch (type) {
		case ListItemImpl.ITEMTYPE_PRODUCT_SEARCH:
			currentPageInfo= new ProductPageInfo();
			qlfAdapter= new ListAdapterProduct();
			break;
		case ListItemImpl.ITEMTYPE_PEOPLE_SEARCH:
			currentPageInfo= new PeoplePageInfo();
			qlfAdapter= new ListAdapterPeople();
			((ListAdapterPeople)qlfAdapter).setRightInvisible(true);
			break;
		case ListItemImpl.ITEMTYPE_PEOPLE_MY:
			currentPageInfo= new PeoplePageInfo();
			qlfAdapter= new ListAdapterPeople();
			break;
		case ListItemImpl.ITEMTYPE_COMPANY_SEARCH:
			currentPageInfo= new CompanyPageInfo();
			qlfAdapter=  new ListAdapterCompany( );
			break;
		case ListItemImpl.ITEMTYPE_LOCALHISTORY:
			qlfAdapter=  new ListAdapterSearchHistory( );
			break;
		case ListItemImpl.ITEMTYPE_IMAGINE:
			qlfAdapter=  new ListAdapterImagine( );
			break;
		default:
			throw new IllegalStateException("JLVBundle type wrong: "+type);
		}
//		setAdapter(qlfAdapter);
		setOnScrollListener(this);
		setDivider(getResources().getDrawable(R.drawable.divider_h));
		setOnItemClickListener(new JLVDefaultOnItemListener(type));
		setFastScrollEnabled(true	);//0312
//		setOnSystemUiVisibilityChangeListener(null);
		everythingList = new ArrayList<ListItemImpl>();
	}
	
	/**
	 * ��������ʹ�ÿؼ�ǰ���ô˷���
	 */
	public void setup(){
		isSetup=true;
		
		if(gpListener!=null){//��ʼ������
			gpListener.page(this, 1);
			if(everythingList!=null) everythingList.clear();//reset 
			requestingPage=1;//0227
		}
		addMoreView();//��ʼʱ���� ������Ҫʱ���� �� ע��λ�ã�С�ı���
		
	}

	/**
	 * ��������Ĳ�������˴�
	 * @param gpListener
	 */
	public void setOnGetPageListener(OnGetPageListener gpListener){
		this.gpListener = gpListener;
	}
	public QfcPageInfo getCurrentPageInfo() {
		return currentPageInfo;
	}

	public int getType(){
		return listType;
	}
	
	
	public boolean isSetup() {
		return isSetup;
	}
	public void setSetup(boolean isSetup) {
		this.isSetup = isSetup;
	}

	public void updateList(List<ListItemImpl> list){
		if(null==getAdapter() &&null!=qlfAdapter) setAdapter(qlfAdapter);
		//�����ڸ������ݵ�ʱ��������adapter
		if(qlfAdapter==null){
			/*qlfAdapter=(ListAbsAdapter)initAdapter(list);
			setAdapter(qlfAdapter);
			setOnScrollListener(this);
			setDivider(getResources().getDrawable(R.drawable.divider_h));*///moved to constructor
		}else{
			List<ListItemImpl> lvList = ((ListAbsAdapter)qlfAdapter).getList();
			if(lvList!=list){
				lvList.clear();
				lvList.addAll(list);
			}
		}
		qlfAdapter.notifyDataSetChanged();
	}
	private QfcPageInfo initPageEntity(){
		switch (listType) {
		case ListItemImpl.ITEMTYPE_PRODUCT_SEARCH:
			return new ProductPageInfo();
		case ListItemImpl.ITEMTYPE_PEOPLE_SEARCH:
		case ListItemImpl.ITEMTYPE_PEOPLE_MY:
			return new PeoplePageInfo();
		case ListItemImpl.ITEMTYPE_COMPANY_SEARCH:
			return new CompanyPageInfo();
		default:
			throw new IllegalStateException("qfcListView type wrong: "+listType);
		}
	}
	private void nextPage(){
		if(null==gpListener||requestingPage==currentPageInfo.nextPage||!currentPageInfo.hasNext) return; 
		requestingPage = currentPageInfo.nextPage;
		gpListener.page(this, currentPageInfo.nextPage);
	}
	
	private void addMoreView() {
		//footer   //0224 ����if���ֹ������ʱmoreView��bug  //0226 extract handle visibility
		if(null==moreView){
			moreView = LayoutInflater.from(getContext()).inflate(R.layout.footer_more, null);
			addFooterView(moreView);
		}else{
			moreView.setVisibility(View.VISIBLE);
		}
	}

	private void removeMoreView() {
		if(null!=moreView){
			removeFooterView(moreView);
			moreView=null;
		}
	}

	@Override
	public Context getReceiverContext() {
		switch (listType) {
		case ListItemImpl.ITEMTYPE_IMAGINE:
			return null;
		default:
			break;
		}
		
		return requestingPage<=1?getContext():null;
	}
	
	@Override
	public void response(String result) throws JSONException {
		JSONObject job = YftValues.getResultObject(result);
		if(job==null) {
			JackUtils.showToast(getContext(), "���ݻ�ȡʧ��");
			return;//
		}
		switch(listType){
			case ListItemImpl.ITEMTYPE_IMAGINE://{"data":[{"��������":0}]}}
				job = YftValues.getResultObject(result);
				if(null!=job){
					JSONArray jarr = job.getJSONArray("data");
//					if(null==everythingList) everythingList = new ArrayList<ListItemImpl>();
					everythingList.clear();
					for(int i=0;i<jarr.length();i++){
						ImagineItemInfo iii = new ImagineItemInfo();
						String singleResult = jarr.getString(i);
						if(!singleResult.contains(":")) continue;
						String[] srPats = singleResult.split(":");
						iii.iii_name = srPats[0].replace('{', ' ').replace('\"', ' ');
						iii.iii_count = srPats[1].replace('}', ' ');
						everythingList.add(iii);
					}
					updateList(everythingList);
				}
				break;
			default:
				currentPageInfo= initPageEntity();
				currentPageInfo.loadJob(job);
				
//				if(null==everythingList) everythingList = new ArrayList<ListItemImpl>();
				everythingList.addAll(currentPageInfo.getDataList());
				
				updateList(everythingList);
				
				if(!currentPageInfo.hasNext){
					removeMoreView();
					Log.i(TAG, "no Next");
				}else{
				}
//				YftData.data().addPageInfo(csUser.getShopId(), sid, requestingPage, pInfo);	
				break;
		}
		
//		Log.i(TAG, (getParent() instanceof FrameLayout)+"::is it a FrameLayout??");
//		setEmptyView(((View)getParent()).findViewById(R.id.emptyview_search_1));
		//TODO �����ڴ˴����emptyView
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(qlfAdapter==null||qlfAdapter.getCount()==0) return;
		int count = qlfAdapter.getCount();
		int lastItem = firstVisibleItem + visibleItemCount ;
//		Log.i(TAG, "last::"+lastItem+"___count::"+count);
		if(lastItem==count){
			nextPage();  //���ظ������ݣ��������ʹ���첽����
		}
	}

	
	
	
}
