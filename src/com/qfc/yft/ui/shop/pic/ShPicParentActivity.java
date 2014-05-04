package com.qfc.yft.ui.shop.pic;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.ui.shop.ShopTabAbsActivity;
import com.qfc.yft.utils.JackImageLoader;

public abstract class ShPicParentActivity extends ShopTabAbsActivity implements OnPageChangeListener{
	private final String TAG = ShPicParentActivity.class.getSimpleName();
	
	
	HorizontalScrollView mHScroll;
	ViewPager mViewPager;
	MyViewPagerAdapter mAdapter;
	LayoutInflater mInflater;
	TextView tv_index;
	View view_slid,view_noda;
	
	List<ImageView> mImgListBig,mImgListSmall;
	String[] ppPaths;
	/**
	 * @return 图片路径数组
	 */
	protected abstract String[] getPathsFromCompany();
	
	@Override
	public int getLayoutResId() {
		return 0;
	}
	
	@Override
	public void initUI() {
		mInflater = LayoutInflater.from(this);
		view_slid = mInflater.inflate(R.layout.activity_slider_p, null);
//		view_noda = mInflater.inflate(R.layout.layout_nodata, null);//1206 TODO
		view_noda = new TextView(this);
		((TextView)view_noda).setText("暂无图片");
		setContentView(view_slid);
		
		shHelper.dfUser = YftData.data().getCurrentUser();
		
		mImgListBig = new ArrayList<ImageView>();
		mImgListSmall = new ArrayList<ImageView>();
		mHScroll =(HorizontalScrollView)findViewById(R.id.vp_hsv);
		mViewPager = (ViewPager)findViewById(R.id.vp_viewpager);
		mAdapter = new MyViewPagerAdapter(new ArrayList<View>());
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
		
		
	}
	/**
	 * initiate imageViews, launched after having company data prepared.
	 */
	private void initViews() {
		if(mAdapter==null) return;
		
		for(int i=0;i<ppPaths.length;i++){
			ppPaths[i] = formatPath(ppPaths[i]);
			initViewBig();
			initViewSmall();
		}
		mAdapter.notifyDataSetChanged();
		
		setPageIndex(1, ppPaths.length);
	}
	private void initViewBig() {
		//big
		View view = mInflater.inflate(R.layout.layout_vp_view_p, null);
		ImageView img = (ImageView)view.findViewById(R.id.img_vp_view);
		mImgListBig.add(img);
		mAdapter.addView(view);
		img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clickBig();
			}
		});
	}
	private void initViewSmall() {
		//small
		View view= mInflater.inflate(R.layout.layout_hsv_view_p, null);
		ImageView img= (ImageView)view.findViewById(R.id.img_hsv);
		mImgListSmall.add(img);
		((LinearLayout)(mHScroll.getChildAt(0))).addView(view);
		view.setTag(mImgListSmall.size()-1);//for interaction
		view.setFocusableInTouchMode(true);//useful in on-click listener?
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clickSmall(v);
			}
		});
		view.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN);
				v.requestFocus();
				return false;
			}
		});
		if(mHScroll.getTag()==null) selectSmall(view);//select one default
	}
	/**
	 * make certain view in hsv selected, and un-select the former one.
	 * @param viewToSelect
	 */
	private void selectSmall(View viewToSelect) {
		View viewSelected = (View)mHScroll.getTag();
		if(viewSelected!=null){
			viewSelected.setSelected(false);
		}
		viewToSelect.setSelected(true);
		viewToSelect.requestFocus();
		mHScroll.setTag(viewToSelect);
	}
	private void clickSmall(View view) {
		if(mViewPager==null) return;
		int index = Integer.parseInt(view.getTag().toString());
		mViewPager.setCurrentItem(index, true);
		selectSmall(view);
		setPageIndex(index+1, ppPaths.length);
	}
	
	private void clickBig() {
		int picIndex = mViewPager.getCurrentItem();
    	Intent intent = new Intent();
    	intent.setClass(this, ViewpagerActivity.class);
    	intent.putExtra(YftValues.SHOWPIC_PAGE, picIndex);//
    	intent.putExtra(YftValues.SHOWPIC_PATHS, ppPaths);//1022
    	startActivity(intent);
	}
	private void requestData() {
		
		for(int i=0;i<ppPaths.length;i++){
			String url = ppPaths[i];
			ImageView imgB,imgS;
			imgB = mImgListBig.get(i);
			imgS = mImgListSmall.get(i);
			Bitmap bm = JackImageLoader.findBitmap(url);
			if(bm==null){
				JackImageLoader.sendRequest(url, imgB,imgS);
			}else{
				imgB.setImageBitmap(bm);
				imgS.setImageBitmap(bm);
			}
		}
	}
	
	@Override
	public void updateUI() {
		if(areViewsInitedAlready()) return;//
		ppPaths = getPathsFromCompany();
		if(!isPathsEmpty()){
			setContentView(view_slid);
			initViews();//拿到paths才能initViews
			requestData(); //获取图片bitmaps
		}else{
			setContentView(view_noda);
		}
	}
	private boolean isPathsEmpty(){
		return null==ppPaths||ppPaths.length==1&&ppPaths[0].isEmpty()||ppPaths.length==0;
	}
	private boolean areViewsInitedAlready(){
		return !isPathsEmpty();
	}
	
	final String HTTP = "http://";
	/**
	 * in case of path which contains url but in-formatted;
	 * @param str
	 * @return
	 */
	private String formatPath(String str){
		if(!str.startsWith(HTTP)&&str.contains(HTTP)){
			str = str.substring(str.indexOf(HTTP));
		}
		return str;
	}
	
	protected void setPageIndex(int at,int all){
		if(tv_index==null){
			tv_index = (TextView)findViewById(R.id.tv_PC_sh);
		}
		tv_index.setText(at+"/"+all);
	}

	public class MyViewPagerAdapter extends PagerAdapter{
		private List<View> mListViews;
		
		public MyViewPagerAdapter(List<View> mListViews) {
			if(mListViews==null) mListViews = new ArrayList<View>();
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) 	{	
			container.removeView(mListViews.get(position));//
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {	//		
			 container.addView(mListViews.get(position), 0);// 
			 return mListViews.get(position);
		}

		@Override
		public int getCount() {			
			return  mListViews.size();// 
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {			
			return arg0==arg1;// 
		}
		
		public void addView(View view){
			if(view!=null) mListViews.add(view);
		}
	}
	
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		if(arg0>=mImgListSmall.size()||arg0<0) return;
		selectSmall((View)mImgListSmall.get(arg0).getParent());
		setPageIndex(arg0+1, ppPaths.length);
//		adjustImageView(arg0);
	}
	
}
/*@Override
protected void onDestroy() {
	if(needRecycle){
		for(Bitmap bm:bitmapList){
			if(!bm.isRecycled()){
				bm.recycle();
			}
		}
		//true
	}
	super.onDestroy();
}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode == KeyEvent.KEYCODE_BACK){
		needRecycle=true;
	}
	return super.onKeyDown(keyCode, event);
}*/