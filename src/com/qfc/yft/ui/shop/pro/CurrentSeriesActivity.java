package com.qfc.yft.ui.shop.pro;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.listitem.LIIProduct;
import com.qfc.yft.entity.offline.IOfflineConst;
import com.qfc.yft.entity.page.ProductPageInfo;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.current.CurrentProductActivity;
import com.qfc.yft.ui.custom.list.JackListView;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;

public class CurrentSeriesActivity extends Activity implements JackListView.OnGetPageListener,OnItemClickListener {
	private final String TAG = CurrentSeriesActivity.this.getClass().getSimpleName();
	
	View moreView;
	TextView seriesName;
	JackListView csJlv;
	
	int sid;
	YftData mHouse;
	User csUser;
	List<LIIProduct> productList;
	ProductPageInfo currentPageInfo;
	int requestingPage = -1;
	
	List<ListItemImpl> offdataProducts;
	
	@Override
	public void page(JackListView qListView, int pageNo) {
		if(null==offdataProducts){//TODO
			ProductPageInfo ppi = (ProductPageInfo)mHouse.getPageInfo(csUser.getShopId(), sid, requestingPage);//
			if(null==ppi){
				if(!YftValues.isopen(this))return;//0429
				new HttpRequestTask(qListView).execute(//RequestType.PRODUCT_INFO, csUser.getShopId()+"",sid+"",YftValues.DEFULAT_PAGESIZE+"",page+""));
					YftValues.getHTTPBodyString(RequestType.PRODUCT_INFO, 
							csUser.getShopId()+"",sid+"",
							YftValues.DEFULAT_PAGESIZE+"",pageNo+""));
				
			}else{
				currentPageInfo = ppi;
				qListView.updateList(currentPageInfo.getDataList());
			}
			
		}else{
			/*
			 * 应用之前写的offlinedatapage获取每页的数据 TODO tobe test
			 */
			if(null!=offdataProducts){
				requestingPage=pageNo;
				initPageInfoMyself();
				qListView.updateList(currentPageInfo.getDataList());
			}
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {//TODO 封装起来
		Intent intent = new Intent();
		LIIProduct prod = (LIIProduct)parent.getAdapter().getItem(position);
		YftData.data().storeProduct(prod);
		intent.setClass(this, CurrentProductActivity.class);
		intent.putExtra("productId", prod.getProductId());
		startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypeople);
		mHouse = YftData.data();
		csUser = mHouse.getCurrentUser();
		Intent intent = getIntent();
		sid = intent.getIntExtra("sid", 0);
		if(sid!=0){
			if(mHouse.isOfflineEnabled())tryInitOffdata();
//			requestNextPage(0);
			initListView();
		}
		
		String name = intent.getStringExtra("sname");
		seriesName = (TextView)findViewById(R.id.tv_title);
		seriesName.setText(name);
	}
	private void initListView() {
		csJlv = new JackListView(this, ListItemImpl.ITEMTYPE_PRODUCT_SEARCH){
			@Override
			public void response(String result) throws JSONException {
				super.response(result);
				YftData.data().addPageInfo(csUser.getShopId(), sid, requestingPage, getCurrentPageInfo());
			}
		};
		csJlv.setOnGetPageListener(this);
//		csJlv.setOnItemClickListener(this);//0302
		/*if(offdataProducts==null){
		}else{//1129
			initPageInfoMyself();
//			prepareList();
		}*/
		csJlv.setup();
		
		FrameLayout frameContainer = (FrameLayout)findViewById(R.id.frame_people);
		frameContainer.addView(csJlv);
	}
		/*
		 * gets
		 */
		/*private List<Product> fetchData(){
			if(currentPageInfo==null) return new ArrayList<Product>();
	//		List<ProductPageInfo> list ;//= new ArrayList<NavSearchActivity.ProductPageInfo>();
	//				currentPageInfo.getShopListFromShopArray();
			if(null==mAdapter||mAdapter.getCount()==0){//第一次数据
	//			return currentPageInfo.getproductListFromproductArray();
				return new ArrayList<CurrentSeriesActivity.Product>();
			}else{
	//			mAdapter.contentList.addAll(currentPageInfo.getproductListFromproductArray());
				mAdapter.contentList.addAll(new ArrayList<CurrentSeriesActivity.Product>());
				return mAdapter.contentList;
			}
		}*/
		
		/**
		 * 
		 * @param result
		 * @throws JSONException
		 *//*
		private void initPageInfo(String result) throws JSONException {
			final String RESULT="result",NEXTPAGE="nextPage",ORDERBY="orderBy",PAGESIZE="pageSize",
					PREPAGE="prePage",HASPRE="hasPre",ORDER="order",
					TOTALCOUNT="totalCount",HASNEXT="hasNext",PAGENO="pageNo",OFFSET="offset",
					ORDERBYSETTED="orderBySetted",AUTOCOUNT="autoCount",FIRST="first",TOTALPAGES="totalPages";
			
			JSONObject job = YftValues.getResultObject(result);
			if(job==null) return;
			ProductPageInfo pInfo;
	//		if(currentPageInfo==null)  //似乎每次都改new
				currentPageInfo= new ProductPageInfo();
			pInfo = currentPageInfo;
	//		pInfo.productArray = job.getJSONArray(RESULT);//TODO
			pInfo.nextPage 	= job.getInt(NEXTPAGE);
			pInfo.orderBy 	= job.getString(ORDERBY);
			pInfo.pageSize	= job.getInt(PAGESIZE);
			pInfo.prePage	= job.getInt(PREPAGE);
			pInfo.hasPre 	= job.getBoolean(HASPRE);
			pInfo.order 	= job.getString(ORDER);
			pInfo.totalCount = job.getInt(TOTALCOUNT);
			pInfo.hasNext 	= job.getBoolean(HASNEXT);
			pInfo.pageNo 	= job.getInt(PAGENO);
			pInfo.offset 	= job.getInt(OFFSET);
			pInfo.orderBySetted = job.getBoolean(ORDERBYSETTED);
			pInfo.autoCount = job.getBoolean(AUTOCOUNT);
			pInfo.first 	= job.getInt(FIRST);
			pInfo.totalPages = job.getInt(TOTALPAGES);
			
	//		mHouse.addPageInfo(csUser.getShopId(), sid, requestingPage, pInfo);//TODO
		}*/
		
		private void initPageInfoMyself() {
			if(currentPageInfo==null){
				currentPageInfo = new ProductPageInfo();
				currentPageInfo.pageNo = 1;
				currentPageInfo.pageSize=YftValues.DEFULAT_PAGESIZE;
				currentPageInfo.first = 1;
				currentPageInfo.totalCount = offdataProducts.size();
				currentPageInfo.totalPages=currentPageInfo.totalCount/currentPageInfo.pageSize+1	;
				currentPageInfo.prePage = Math.max(1, currentPageInfo.pageNo-1);
				currentPageInfo.nextPage=Math.min(currentPageInfo.totalPages, currentPageInfo.pageNo+1);
				currentPageInfo.hasNext=currentPageInfo.nextPage>currentPageInfo.pageNo;
//		currentPageInfo.setProductList(offdataProducts.subList(0,  Math.min(currentPageInfo.pageSize, currentPageInfo.totalCount)) );//(requestingPage-1)*YftValues.LIST_PAGESIZE,Math.min(offdataProducts.size()-1
				currentPageInfo.setProductList(offdataProducts.subList(0,  Math.min(currentPageInfo.pageSize, currentPageInfo.totalCount)) );//0303
			}else{
				if(requestingPage<=currentPageInfo.totalPages&&requestingPage>=1){
					currentPageInfo.pageNo=requestingPage;
					int startIndex = getYaStartIndex(currentPageInfo.pageNo,currentPageInfo.pageSize);
//	currentPageInfo.setProductList(offdataProducts.subList(startIndex, Math.min( startIndex+currentPageInfo.pageSize-1, currentPageInfo.totalCount-1)) );
					currentPageInfo.setProductList(offdataProducts.subList(startIndex, Math.min( startIndex+currentPageInfo.pageSize-1, currentPageInfo.totalCount)) );//0303
					currentPageInfo.prePage = Math.max(1, currentPageInfo.pageNo-1);
					currentPageInfo.nextPage=Math.min(currentPageInfo.totalPages, currentPageInfo.pageNo+1);
					currentPageInfo.hasNext=currentPageInfo.nextPage>currentPageInfo.pageNo;
				}
			}
		}

	/*private void showPrivateDialog(){
				PrivateChecker.et = new EditText(this);//
				new AlertDialog.Builder(getReceiverContext()).setTitle("请输入私人展厅密码").setIcon(
					     R.drawable.ic_launcher).setView(
					    		 PrivateChecker.et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
								checkPrivateCode();
								dialog.dismiss();
								PrivateChecker.dispose();
							}
						})
					     .setNegativeButton("取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								
							}
						}).show();
			}*/
			/*private void checkPrivateCode(){
				String inputCode = PrivateChecker.et.getText().toString();
				String input2MD5 = JackUtils.getMD5(inputCode);
				Log.i(TAG, inputCode+"_c_m_"+input2MD5);
				if(input2MD5.equals(PrivateChecker.pwd)){//成功
					goActivity(mAdapter.contentList.get(PrivateChecker.pos));
				}else{
					JackUtils.showToast(getReceiverContext(), "密码不正确");
				}
			}*/
			/*
			 * gets
			 */
			/*private List<Product> fetchData(){
				if(currentPageInfo==null) return new ArrayList<Product>();
		//		List<ProductPageInfo> list ;//= new ArrayList<NavSearchActivity.ProductPageInfo>();
		//				currentPageInfo.getShopListFromShopArray();
				if(null==mAdapter||mAdapter.getCount()==0){//第一次数据
		//			return currentPageInfo.getproductListFromproductArray();
					return new ArrayList<CurrentSeriesActivity.Product>();
				}else{
		//			mAdapter.contentList.addAll(currentPageInfo.getproductListFromproductArray());
					mAdapter.contentList.addAll(new ArrayList<CurrentSeriesActivity.Product>());
					return mAdapter.contentList;
				}
			}*/
			
			private int getYaStartIndex(int pageNo, int pageSize){
				return (pageNo-1)*pageSize;
			}

	private void tryInitOffdata() {//try not to init list without any products
		String matStr = mHouse.getOffStr(IOfflineConst.PREFOFF_PRODUCTS);
		JSONArray jar = getOffArr(matStr);
		if(jar!=null){
			try {
				offdataProducts = new ArrayList<ListItemImpl>();
				for(int i=0;i<jar.length();i++){
					LIIProduct product = new LIIProduct();//jar.getJSONObject(i));
					product.initJackJson(jar.getJSONObject(i));//TODO 产生了奇怪字符
					offdataProducts.add(product);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private JSONArray getOffArr(String matStr) {
		JSONArray result = null;
		if(!matStr.isEmpty()){
			try {
				result = new JSONObject(matStr).getJSONArray(sid+"");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	/*private void showPrivateDialog(){
		PrivateChecker.et = new EditText(this);//
		new AlertDialog.Builder(getReceiverContext()).setTitle("请输入私人展厅密码").setIcon(
			     R.drawable.ic_launcher).setView(
			    		 PrivateChecker.et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						checkPrivateCode();
						dialog.dismiss();
						PrivateChecker.dispose();
					}
				})
			     .setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				}).show();
	}*/
	/*private void checkPrivateCode(){
		String inputCode = PrivateChecker.et.getText().toString();
		String input2MD5 = JackUtils.getMD5(inputCode);
		Log.i(TAG, inputCode+"_c_m_"+input2MD5);
		if(input2MD5.equals(PrivateChecker.pwd)){//成功
			goActivity(mAdapter.contentList.get(PrivateChecker.pos));
		}else{
			JackUtils.showToast(getReceiverContext(), "密码不正确");
		}
	}*/
	/*
	 * gets
	 */
	/*private List<Product> fetchData(){
		if(currentPageInfo==null) return new ArrayList<Product>();
//		List<ProductPageInfo> list ;//= new ArrayList<NavSearchActivity.ProductPageInfo>();
//				currentPageInfo.getShopListFromShopArray();
		if(null==mAdapter||mAdapter.getCount()==0){//第一次数据
//			return currentPageInfo.getproductListFromproductArray();
			return new ArrayList<CurrentSeriesActivity.Product>();
		}else{
//			mAdapter.contentList.addAll(currentPageInfo.getproductListFromproductArray());
			mAdapter.contentList.addAll(new ArrayList<CurrentSeriesActivity.Product>());
			return mAdapter.contentList;
		}
	}*/
	
	/*private void requestNextPage(int curPage){
		if(currentPageInfo==null){
			request(1);
		}else{
			if(curPage==currentPageInfo.pageNo&&currentPageInfo.pageNo<currentPageInfo.nextPage){
				new HttpRequestTask(this).execute(
						YftValues.getHTTPBodyString(
								RequestType.SEARCH_RECOMMEND, 
								"1",pageSize+"",currentPageInfo.nextPage+""));
				request( currentPageInfo.nextPage);
			}
		}
	}*/
	/*private void request(int page) {
		if(requestingPage==page) return;//相同页码请求中，不重复请求
		requestingPage = page;
		if(offdataProducts==null){
			ProductPageInfo ppi = null;//TODO (ProductPageInfo)mHouse.getPageInfo(csUser.getShopId(), sid, requestingPage);
			if(ppi==null){
				new HttpRequestTask(this).execute(YftValues.getHTTPBodyString(
						RequestType.PRODUCT_INFO, csUser.getShopId()+"",sid+"",YftValues.DEFULAT_PAGESIZE+"",page+""));
			}else{
				currentPageInfo = ppi;
				prepareList();
			}
		}else{//1129
			initPageInfoMyself();
			prepareList();
		}
		
	}*/
	
	/*static class ViewHolder{
		        ImageView icimg;
		        TextView title;
		        TextView content1;
//		        ImageView icreco;
	}
	static class PrivateChecker{
		static Integer pos;
		static EditText et;
		static String pwd;
		public static void dispose() {
			PrivateChecker.et=null;
			PrivateChecker.pos=null;
			PrivateChecker.pwd =null;
		}
	}*/

	/*@Override
	public void response(String result) {
		try {
			JSONObject job;
			job = YftValues.getResultObject(result);
			if(job!=null){
				initPageInfo(result);
				prepareList();
			}else{
				//request failed
				requestFailed();
			}
		} catch (JSONException e) {
			requestFailed();
			e.printStackTrace();
		}
		requestingPage = -1;
		
	}*/
	
}
