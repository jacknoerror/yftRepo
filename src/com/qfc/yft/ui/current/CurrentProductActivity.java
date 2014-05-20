package com.qfc.yft.ui.current;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.SimpleCompany;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.listitem.LIIProduct;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.MyPagerAdapater;
import com.qfc.yft.ui.YftActivityGate;
import com.qfc.yft.ui.custom.ScrollViewExtend;
import com.qfc.yft.ui.shop.ShopActivity;
import com.qfc.yft.ui.shop.pic.ViewpagerActivity;
import com.qfc.yft.utils.JackImageLoader;
import com.qfc.yft.utils.JackUtils;

public class CurrentProductActivity extends Activity implements ViewPager.OnPageChangeListener,View.OnClickListener,HttpReceiver{
	private final String TAG = CurrentProductActivity.class.getSimpleName();
	
	LIIProduct cpProduct;
	
	TextView  tv_productname,tv_desc;
	ImageView btn_coo,btn_fzl,btn_contact,btn_shop,arrow;
	WebView mWebView;
	ViewPager mViewPager;
	List<ImageView> spotList;
	boolean isWebViewShow;
	
	CPInfoReceiver cpir;
	ScrollViewExtend mScrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_current_product);
		int pid = getIntent().getIntExtra("productId",-1);
		cpProduct = YftData.data().getStoredProduct(pid);
//		cpProduct  = getIntent().getParcelableExtra("product");
		if(cpProduct!=null){
			init();
		}
		mScrollView = (ScrollViewExtend )findViewById(R.id.scroll_cp);
		findViewById(R.id.btn_cpback).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void init() {
//		Log.i(TAG, cpProduct.getProductProps());
		/*tv_prop = (TextView) findViewById(R.id.tv_name_cp);
		if(tv_prop!=null) tv_prop.setText(cpProduct.getPropString().replace("&;&", "\n"	));*/
		LinearLayout attr_layout = (LinearLayout)findViewById(R.id.ll_attr_cp);
		for(String str :cpProduct.getProductPropArray() ){
			View view = LayoutInflater.from(this).inflate(R.layout.layout_name_dashed, null);
			TextView tv = (TextView)view.findViewById(R.id.tv_namedashed);
			tv.setText(str);
			attr_layout.addView(view);
		}
		/*tv_productname = (TextView) findViewById(R.id.tv_productname);
		tv_productname.setText(cpProduct.getProductName());
 		JackUtils.bold(tv_productname);*/
		tv_desc = (TextView) findViewById(R.id.tv_desc_cp);
		tv_desc.setText(cpProduct.getProductName());
//		initWebView();
		mViewPager = (ViewPager)findViewById(R.id.viewpager_cp);
		if(mViewPager!=null) {
			List<View> vList = new ArrayList<View>();
			spotList = new ArrayList<ImageView>();
			LinearLayout spotLayout = (LinearLayout)findViewById(R.id.layout_spots_cp);
			for(String s:cpProduct.getNeatProductImgArray()){//cpProduct.getProductImg()
				ImageView img = new ImageView(this);
				img.setImageDrawable(getResources().getDrawable(R.drawable.load_default));
				img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				img.setScaleType(ScaleType.CENTER_CROP);
				Log.i(TAG, s+":::img url");
				JackImageLoader.justSetMeImage(JackUtils.makeItUrl(s), img);
				vList.add(img);
				img.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(CurrentProductActivity.this, ViewpagerActivity.class);
						intent.putExtra(YftValues.SHOWPIC_PATHS, cpProduct.getNeatProductImgArray());
						startActivity(intent);
					}
				});
				//spot
				FrameLayout layoutWithImg = new FrameLayout(this);
//				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//				lp.setMargins(15, 15, 15, 15);
//				layoutWithImg.setLayoutParams(lp);
				layoutWithImg.setPadding(5, 5, 5, 5);
				ImageView im = new ImageView(this);//(ImageView)LayoutInflater.from(this).inflate(R.layout.image_spot, null);//@drawable/spot_selector
				im.setImageResource(R.drawable.spot_selector);
				layoutWithImg.addView(im);
				spotLayout.addView(layoutWithImg);
				spotList.add(im);
			}
			setSpotSelected(0);
			if(spotList.size()<=1) spotLayout.setVisibility(View.INVISIBLE);//只有一个则不显示
			mViewPager.setAdapter(new MyPagerAdapater(vList));
			mViewPager.setOnPageChangeListener(this);
		}
		View view = findViewById(R.id.rl_more);
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//箭头动画
				rotateArrow();
				//webview
				initWebView();
			}

		});
		
		btn_coo = (ImageView)findViewById(R.id.btn_cp_coo);
		btn_fzl = (ImageView)findViewById(R.id.btn_cp_1);
		btn_contact = (ImageView)findViewById(R.id.btn_cp_2);
		btn_shop = (ImageView)findViewById(R.id.btn_cp_3);
		btn_coo.setOnClickListener(this);
		btn_fzl.setOnClickListener(this);
		btn_contact.setOnClickListener(this);
		btn_shop.setOnClickListener(this);
		
		cpir = new CPInfoReceiver(this);
		if(!YftValues.isopen(this))return;//0429
		new HttpRequestTask(cpir).execute(YftValues.getHTTPBodyString(RequestType.ISCOLLECTEDPRODUCT, 
				cpProduct.getProductId()+"",
				YftData.data().getMe().getId()+""));
		
	}

	private void setSpotSelected(int index){
		if(mViewPager==null||spotList==null||spotList.size()==0) return;
		View viewSelected = (View)mViewPager.getTag();
		if(viewSelected!=null){
			viewSelected.setSelected(false);
		}
		spotList.get(index).setSelected(true);
		spotList.get(index).requestFocus();
		mViewPager.setTag(spotList.get(index));
	}
	private void rotateArrow() {
		if(JackUtils.getApiLvl()<12) return;
		if(null==arrow)arrow = (ImageView)findViewById(R.id.arrow);
		Animation animation = new RotateAnimation(isWebViewShow?90:0, isWebViewShow?0:90,arrow.getWidth()/2,arrow.getHeight()/2);
		animation.setDuration(250);
		animation.setFillAfter(true);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {			}
			@Override
			public void onAnimationRepeat(Animation animation) {			}
			@Override
			public void onAnimationEnd(Animation animation) {
				if(null==mScrollView) return;
				mScrollView.scrollBy(0, 200);
			}
		});
		arrow.setAnimation(animation);
		arrow.startAnimation(animation);
	}
	private void initWebView() {
		if(mWebView==null) {
			mWebView = (WebView)findViewById(R.id.web_cp);
			mWebView.setVisibility(View.VISIBLE);
//			mWebView.loadData(cpProduct.getProductDesc(),  "text/html","gbk");
			mWebView.getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
			mWebView.loadData(cpProduct.getProductDesc(), "text/html; charset=UTF-8", null);//这种写法可以正确解码 TODO
			WebSettings ws = mWebView.getSettings();

//	        ws.setJavaScriptEnabled(true);
	        //设置可以支持缩放   
	        ws.setSupportZoom(true);   
	        //设置默认缩放方式尺寸是far   
	        ws.setDefaultZoom(WebSettings.ZoomDensity.FAR);  
	        //设置出现缩放工具   
	        ws.setBuiltInZoomControls(true);
	     // 让网页自适应屏幕宽度
//	        WebSettings webSettings= mWebView.getSettings(); // mWebView: 类WebView的实例
	        ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
	        
		}else{
			if(isWebViewShow) mWebView.setVisibility(View.GONE);
			else mWebView.setVisibility(View.VISIBLE);
		}
		isWebViewShow = mWebView.getVisibility()==View.VISIBLE;
	}
	private int getShopTabId(int vid,int mt){
		int result;
		if(mt<0||mt>=3){
			result = vid==R.id.btn_cp_2?1:2;
		}else{
			result = vid==R.id.btn_cp_3?1:2;
		}
		return result;
	}
	@Override
		public void onClick(View v) {
		
			switch (v.getId()) {
			case R.id.btn_cp_coo:
					if(YftData.data().getMe().getId()==0){
						JackUtils.showToast(this, "您还没有登录");
						break;
					}
					new HttpRequestTask(this).execute(YftValues.getHTTPBodyString(
							btn_coo.isSelected()?RequestType.COLLECTION_DELETE:RequestType.COLLECTION_SAVE, 
							YftData.data().getMe().getId()+"",
							cpProduct.getProductId()+"",
							"0",
							""));//
				break;
			case R.id.btn_cp_1:
				if(null!=cpir){
					if(cpir.userId!=YftData.data().getMe().getId()){//怎样以游客身份聊天?  0503
						if(cpir.textTalk!=null){
							if(YftData.data().getMe().getId()!=0){//optimize this
								YftActivityGate.goChat(this, cpir.companyName, (long) cpir.textTalk, 0, "");//TODO
							}else{
								JackUtils.showToast(this, "您没有登录纺织聊");
							}
						}
						else JackUtils.showToast(this, "该商家没有纺织聊账号，请尝试其他联系方式");
					}else{
						JackUtils.showToast(this, "不能跟自己聊天");
					}
				}
				break;
			case R.id.btn_cp_2:case R.id.btn_cp_3:
				if(null==cpir) return ;//notice
				YftActivityGate.goShop(this, cpir.shopId, cpir.companyName, cpir.memberType, getShopTabId(v.getId(), cpir.memberType));//TODO
				break;
			case R.id.btn_share:
				share();
				break;
			default:
				break;
			}
			
		}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		//1.2.0
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		if(arg0>=spotList.size()||arg0<0||spotList.size()<=1) return;
		setSpotSelected(arg0);
	}

	@Override
	public void response(String result) throws JSONException {
		//TODO
		if(result.contains("true")){
			if(!btn_coo.isSelected()){
				JackUtils.showToast(this, "收藏成功");
				btn_coo.setSelected(true);
			}
			else {
				JackUtils.showToast(this, "取消收藏成功");
				btn_coo.setSelected(false);

			}
		}else{
			JackUtils.showToast(this, "操作失败");
		}
		
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}
	
	
	public class CPInfoReceiver implements HttpReceiver{
		int userId;
		int shopId;
		boolean isSaved;//
		int memberType=-1;
		String companyName;
		Long textTalk;

		Context cpiContext;
		public CPInfoReceiver(Context context){
			cpiContext = context;
		}
		
		@Override
		public void response(String result) throws JSONException {
			JSONObject job = YftValues.getResultObject(result);
			if(null!=job){
				isSaved= job.getBoolean("isCollect");
				if(isSaved){
					btn_coo.setSelected(true);
				}
				userId = job.getInt("memberId");
				shopId = job.getInt("companyId");
				memberType = job.getInt("memberType");
				companyName=job.optString("companyName");
				textTalk=job.getLong("texTalk");//TODO 
						
				SimpleCompany lc = new SimpleCompany();//0319
				lc.userId=userId;
				lc.shopId=shopId;
				lc.shopName=companyName;
				lc.memberType=memberType;
				YftData.data().storeShopById(lc);
			}
			
		}

		@Override
		public Context getReceiverContext() {
			return cpiContext;
		}
		
	}
	
	private void share(){
		Intent intent=new Intent(Intent.ACTION_SEND); 
		intent.setType("text/plain"); 
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享"); 
		intent.putExtra(Intent.EXTRA_TEXT, "来自网上轻纺城，这件产品不错："+cpProduct.getMainPic());  
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		startActivity(Intent.createChooser(intent, getTitle())); 
	}
}
