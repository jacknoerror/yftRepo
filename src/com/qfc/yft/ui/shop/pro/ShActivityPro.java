package com.qfc.yft.ui.shop.pro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huewu.pla.lib.MultiColumnPullToRefreshListView;
import com.huewu.pla.lib.MultiColumnPullToRefreshListView.OnRefreshListener;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.huewu.pla.lib.internal.PLA_HeaderViewListAdapter;
import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.Series;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.offline.IOfflineConst;
import com.qfc.yft.entity.offline.OfflineData;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.net.JackRequestManager;
import com.qfc.yft.ui.YftActivityGate;
import com.qfc.yft.ui.custom.list.JackListView;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;
import com.qfc.yft.utils.JackImageLoader;
import com.qfc.yft.utils.JackUtils;

public class ShActivityPro extends Activity implements HttpReceiver{
	private final String TAG = ShActivityPro.class.getSimpleName();
	private final String END_SERIES="系列",END_SERIES_SON="子系";
	
	GridView saGrid;
//	MyGridAdapter mAdapter;
	
	List<Series> seriesList;
	User saUser;
	YftData yftData;
	
	MultiColumnPullToRefreshListView listview;
	JackListView jlv;
//	private OfflineReceiver mor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saUser = YftData.data().getCurrentUser();
		if(!isFreeUser()){
			
		setContentView(R.layout.replaceme_grid_sh_product);
		init();
		}else{
			jlv = new JackListView(this, ListItemImpl.ITEMTYPE_PRODUCT_SEARCH);
			jlv.setOnGetPageListener(new JackListView.OnGetPageListener() {
				
				@Override
				public void page(JackListView qListView, int pageNo) {
					new HttpRequestTask(qListView).execute(//RequestType.PRODUCT_INFO, csUser.getShopId()+"",sid+"",YftValues.DEFULAT_PAGESIZE+"",page+""));
							YftValues.getHTTPBodyString(RequestType.PRODUCT_INFO, 
									saUser.getShopId()+"","",
									YftValues.DEFULAT_PAGESIZE*2+"",pageNo+""));
				}
			});
			
			setContentView(jlv);
		}
		
	}
	
	@Override
	protected void onResume() {//欧买高
		super.onResume();
		/*if(saUser.getMemberType()==4	){//免费商铺
			new HttpRequestTask(this).execute(YftValues.getHTTPBodyString(RequestType.PRODUCT_INFO, 
					//shopId,proSeriesId(系列id),pageSize(每次请求的产品数量10),pageNo(每次请求索引从1开始)
					saUser.getShopId()+"",
					"",
					YftValues.DEFULAT_PAGESIZE+"","1"
					));
		}*/
		if(isFreeUser()){
			jlv.setup();
			return;
		}
		yftData=YftData.data();
		seriesList = yftData.getSeriesList(saUser.getShopId());//getAllUserSeriesMap().get(saUser.getShopId());
		if(seriesList==null){//||seriesList.size()==0){
			updateData();
		}else{
			 updateView();
		}
	}

	/**
	 * @return
	 */
	private boolean isFreeUser() {
		return saUser.getMemberType()==4;
	}

	/**
	 * @param yftData
	 */
	public void updateData() {
		String offResponse = yftData.getOffStr(IOfflineConst.PREFOFF_SERIES);
		if(!yftData.isOfflineEnabled()||offResponse.isEmpty()||!yftData.isMe()){
			if(!YftValues.isopen(this))return;
			new HttpRequestTask(this).execute(YftValues.getHTTPBodyString(RequestType.SERIES_INFO, !isFreeUser()?saUser.getShopId()+"":""));//0312 0
		}else{//[{"productSeriesName":"床上用品主系列","imgCode":null,"productSeriesId":406058,"productSeriesPic":"MDF8cHJvZHVjdHw2ODg5ODMuanBn&:&http://img.qfc.cn/upload/01/product/d7/18/688983_300X300.jpg","parentSid":0},{"productSeriesName":"箱包主系列","imgCode":null,"productSeriesId":406059,"productSeriesPic":"MDF8cHJvZHVjdHw2ODkwMjIuanBn&:&http://img.qfc.cn/upload/01/product/c9/8a/689022_300X300.jpg","parentSid":0}]
			response(offResponse);
		}
	}

	
	/*private boolean needToUpdate(){
		return mAdapter!=null&&seriesList!=null&&seriesList.size()!=mAdapter.getCount();
	}*/
	private void init() {
		
		
		listview = (MultiColumnPullToRefreshListView)findViewById(R.id.list);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(PLA_AdapterView<?> parent, View view,
					int position, long id) {
//				Log.i(TAG, position+"::lvlv_pos click||"+seriesList.get(position).getProductSeriesId());
//				position -=1;
				Series series=seriesList.get(--position);
				if(saUser.getMemberType()<=4	){
					Intent intent =  new Intent();
					intent.setClass(ShActivityPro.this, CurrentSeriesActivity.class);//TODO
					intent.putExtra("sid", series.getProductSeriesId());
					intent.putExtra("sname", series.getProductSeriesName());//
					startActivity(intent);
				}else{}
			}
		});
		listview.setEmptyView(findViewById(R.id.emptyview_series));
		listview.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				updateData();
				listview.onRefreshComplete();
			}
		});
//		initGrid();
	}
	

	/*private void initGrid() {
		//取得GridView对象
		saGrid = (GridView)findViewById(R.id.gridview_sh_pro);
				//添加元素给gridview
		mAdapter = new MyGridAdapter(new ArrayList<ShActivityPro.ProItem>(),this);
		saGrid.setAdapter(mAdapter);
				// 设置Gallery的背景
//		saGrid.setBackgroundResource(R.drawable.bg0);
				//事件监听
		saGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
//				Toast.makeText(Activity01.this, "你选择了" + (position + 1) + " 号图片", Toast.LENGTH_SHORT).show();
				Log.i(TAG, position+"::grid_pos click");
				Intent intent =  new Intent();
//				intent.setClass(ShActivityPro.this, CurrentSeriesActivity.class);//TODO
				intent.putExtra("sid", seriesList.get(position).getProductSeriesId());
				intent.putExtra("sname", seriesList.get(position).getProductSeriesName());//
				startActivity(intent);
			}
		});
	}*/
	
	private void updateView(){
		
		MyAdapter adapter = new MyAdapter(getData());
		listview.setAdapter(new PLA_HeaderViewListAdapter(null, null, adapter));
		adapter.notifyDataSetChanged();
		
//		showNothingMsg();
//		if(!needToUpdate()) return;
		//
//		adapter.notifyDataSetChanged();
		
	}
	
	private List<FallItem> getData(){
		List<FallItem> list = new ArrayList<FallItem>();
		for(Series s : seriesList){
//			ProItem pi = new ProItem();
			FallItem fi = new FallItem();
			fi.desc = (s.getParentSeriesName().isEmpty()?s.getProductSeriesName():s.getParentSeriesName());
			fi.imgPath = s.getProductSeriesPic();//
			fi.anotherDesc=(s.getParentSeriesName().isEmpty()?"":s.getProductSeriesName());
			list.add(fi);
		}
		return list;
	}

	/*private void addItem(ProItem item){
		mAdapter.contentList.add(item);
			
	}*/
	
	private String addEndStr(String originStr ,String endStr){
		if(originStr==null) return null;
		if(originStr.contains("系列")) return originStr;
//		if(originStr.endsWith(endStr)) return originStr;
		return originStr+endStr;
	}
	
	private void failed(){
		//TODO
	}
	
	@Override
	public void response(String result) {
		try {
			JSONObject jsonObj = new JSONObject(result);
			if(jsonObj.has(YftValues.RESULT_SIGN)&&jsonObj.getBoolean(YftValues.RESULT_SIGN)){
				if(jsonObj.has(YftValues.RESULT_OBJECT)){//获得resultObj中的series数据
					JSONArray seriesArray = jsonObj.getJSONArray(YftValues.RESULT_OBJECT);//讲多个series获取为series
//					allSeriesMap = new HashMap<String, List<Series>>();//初始化将要存放series信息的map
					seriesList = new ArrayList<Series>();
					for(int i=0;i<seriesArray.length();i++){//遍历seriesArray
						JSONObject job = seriesArray.getJSONObject(i);
//						Log.i(TAG, job.toString()+"____pro");
						String parentName;
						int parentId;
						parentName = job.has(YftValues.JSON_SERIES_NAME_PARENTSERIES)?
							job.getString(YftValues.JSON_SERIES_NAME_PARENTSERIES):
							"";
						parentId = job.has(YftValues.JSON_SERIES_ID_PARENT)?
								job.getInt(YftValues.JSON_SERIES_ID_PARENT):
								-1;
						Series series = new Series(
								job.getString(YftValues.JSON_SERIES_NAME_PRODUCTSERIES), 
								parentName, 
								Integer.parseInt(job.getString(YftValues.JSON_SERIES_ID_PRODUCT)), 
								job.has(YftValues.JSON_SERIES_PIC)?job.getString(YftValues.JSON_SERIES_PIC):"", //可能没有
								parentId);
						
						seriesList.add(series);
					}//for seriesArray
//					if(seriesList.size()>0){
						YftData.data().putSeriesList(saUser.getShopId(), seriesList);
						updateView();
//					}else{
						//1209
//					}
				}else{
					failed();
				}//hasResultObject
			}//resultSign ture
			else{
				//请求失败
				failed();
			}
			
		} catch (JSONException e) {
			failed();
			e.printStackTrace();
		}
		
	}
	
	public Context getReceiverContext() {
		//TODO
		return getParent();
	}
	
	

	public class MyAdapter extends BaseAdapter{
		List<FallItem> contentList;
		SparseArray<View> viewMap;
		
		public MyAdapter(List<FallItem> list){
			if(list==null) list = new ArrayList<FallItem>();
			contentList = list;
			viewMap = new SparseArray<View>();
		}
		
		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public Object getItem(int position) {
			return contentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = viewMap.get(position);
			ViewHolder holder ;
			
			if(view==null){
				view = LayoutInflater.from(ShActivityPro.this).inflate(R.layout.item_fall, null);//????????
				   
                holder = new ViewHolder();
                holder.anoText=(TextView)view.findViewById(R.id.tv_fall_item_sub);
                holder.image = (ImageView)view.findViewById(R.id.img_fall_item);
                holder.text = (TextView)view.findViewById(R.id.tv_fall_item);
   
   
                viewMap.put(position, view);
   
                view.setTag(holder);
                view.setBackgroundResource(R.drawable.bg_grey);
			}else{
				view = viewMap.get(position);
                holder = (ViewHolder)view.getTag();
            }
            

            //哟啊不要换地方？
			FallItem ssi = contentList.get(position);
            JackImageLoader.justSetMeImage(ssi.imgPath, holder.image);
            Vector vector = dealWithWH(getOWH(ssi.imgPath));
            if(vector.valid){
            	holder.image.setLayoutParams(new RelativeLayout.LayoutParams(vector.wid, vector.hei));
            	if(JackUtils.getApiLvl()<=10)view.setPadding(15, 0, 0, 15);//
            }
            holder.text.setText(addEndStr(ssi.desc, END_SERIES));
            if(null!=ssi.anotherDesc&&!ssi.anotherDesc.isEmpty()){
            	holder.anoText.setText(addEndStr(ssi.anotherDesc, END_SERIES_SON));
            }else{
            	holder.anoText.setVisibility(View.INVISIBLE);
            }
			return view;
		}
		
	}
	
	/**
	 * 图片过小时 处理图片
	 * @param v
	 * @return
	 */
	private Vector dealWithWH(Vector v){
		if(null==v) return new Vector();
		final float WIDTH = YftValues.SCREEN_WIDTH/2-10;//delta
//		if(v.valid&&v.wid<WIDTH){
			v.hei*=WIDTH/v.wid;
			v.wid=(int) WIDTH;
//		}else{
//			v.hei*=WIDTH/v.wid;
//			v.wid=(int) WIDTH;
//		}
		return v;
	}
	/**
	 * 通过url获得图片长宽
	 * @param url
	 * @return
	 */
	private Vector getOWH(String url){
		Vector vec = new Vector();
		try{
			String sub = url.substring(url.lastIndexOf("_")+1, url.indexOf(".jp"));
			String[] intStrs = sub.split("X");
			vec.wid=Integer.parseInt(intStrs[0]);
			vec.hei=Integer.parseInt(intStrs[1]);
			vec.valid=vec.wid>0&&vec.hei>0;
		}catch(Exception e){
		}
		
		return vec; 
	}
	
	static class Vector{
		/** could be used		 */
		public boolean valid;
		/**	*/
		public int wid;
		public int hei;
	}
	static class ViewHolder{
		ImageView image;
		TextView text;
		TextView anoText;
	}
	public class FallItem{
		public String imgPath;
		public String desc;
		public String anotherDesc;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
//			((ShParentActivity)getParent()).showExitDialog();
			//TODO
			
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
