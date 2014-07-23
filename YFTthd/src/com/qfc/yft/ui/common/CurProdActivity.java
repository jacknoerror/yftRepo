package com.qfc.yft.ui.common;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qfc.yft.MyApplication;
import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.net.action.ActJobRcv;
import com.qfc.yft.net.action.ActUpdateRcv;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.ActionUpdateImpl;
import com.qfc.yft.net.action.collection.DeleteForOpenAPIReq;
import com.qfc.yft.net.action.collection.IsCollectByProductIdReq;
import com.qfc.yft.net.action.collection.SaveCollectionReq;
import com.qfc.yft.net.action.product.GetProductForMotion1Req;
import com.qfc.yft.ui.MyPagerAdapater;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.ui.common.CurrentPersonActivity.SimpleCompany;
import com.qfc.yft.ui.custom.ScrollViewExtend;
import com.qfc.yft.ui.shop.pic.ViewpagerActivity;
import com.qfc.yft.util.JackImageLoader;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.util.ShareHelper;
import com.qfc.yft.vo.LIIPeople;
import com.qfc.yft.vo.LIIProduct;

public class CurProdActivity extends Activity  implements ViewPager.OnPageChangeListener,View.OnClickListener, ActionUpdateImpl{
private final String TAG = CurProdActivity.class.getSimpleName();
	
	LIIProduct cpProduct;
	
	TextView  tv_productname,tv_desc;
	ImageView btn_coo,btn_fzl,btn_contact,btn_shop,arrow;
	WebView mWebView;
	ViewPager mViewPager;
	List<ImageView> spotList;
	boolean isWebViewShow;
	
	CPInfoReceiver cpir;
	ScrollViewExtend mScrollView;
	
	private ShareHelper shareHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cp);
		int pid = getIntent().getIntExtra(NetConst.EXTRAS_PRODUCT_ID, 0);
		if(0==pid) return; //
		
		ActionRequestImpl actReq = new GetProductForMotion1Req(pid);
		ActionReceiverImpl actRcv = new ActUpdateRcv(new ActJobRcv(this), this);
		ActionBuilder.getInstance().request(actReq, actRcv);
		
		/*if(cpProduct!=null){
			init();
		}*/
		mScrollView = (ScrollViewExtend )findViewById(R.id.scroll_cp);
		findViewById(R.id.btn_cpback).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
		if (null != shareHelper)
			shareHelper.callback(requestCode, resultCode, data);
	}
	private void init() {
//		Log.i(TAG, cpProduct.getProductProps());
		/*tv_prop = (TextView) findViewById(R.id.tv_name_cp);
		if(tv_prop!=null) tv_prop.setText(cpProduct.getPropString().replace("&;&", "\n"	));*/
		LinearLayout attr_layout = (LinearLayout)findViewById(R.id.ll_attr_cp);
//		attr_layout.setDividerDrawable(getResources().getDrawable(R.drawable.divider_h));
//		attr_layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
		for(String str :cpProduct.getProductPropArray() ){
			TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.view_tv_normalitem, null);
			tv.setText(str);
			attr_layout.addView(tv);
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
			final String[] neatProductImgArray = cpProduct.getNeatProductImgArray();//0604
			for(String s:neatProductImgArray){//cpProduct.getProductImg()
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
						intent.setClass(CurProdActivity.this, ViewpagerActivity.class);
						intent.putExtra(Const.EXTRAS_SHOWPIC_PATHS, neatProductImgArray);
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
				im.setImageResource(R.drawable.spot_selector_big);
//				im.setImageResource(R.drawable.spot_selector);
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
		
		if(!JackUtils.isNetworkAvailable(this))return;//0429
		ActionRequestImpl actReq = new IsCollectByProductIdReq(MyData.data().getMe().getId(), cpProduct.getProductId());
		cpir = new CPInfoReceiver( );
		ActionBuilder.getInstance().request(actReq, cpir);
		findViewById(R.id.btn_share).setOnClickListener(this);//0529
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
		if (JackUtils.getApiLvl() < 12)
			return;
		if (null == arrow)
			arrow = (ImageView) findViewById(R.id.arrow);
		Animation animation = new RotateAnimation(isWebViewShow ? 90 : 0,
				isWebViewShow ? 0 : 90, arrow.getWidth() / 2,
				arrow.getHeight() / 2);
		animation.setDuration(250);
		animation.setFillAfter(true);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// if(null==mScrollView) return;
				// mScrollView.scrollBy(0, 180);
				new Thread() {
					@Override
					public void run() {
						SystemClock.sleep(200);
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// Log.i("NOW", "");
								if (null != mScrollView)
									mScrollView.scrollBy(0, 180);
							}
						});
					};
				}.start();
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
			mWebView.loadData(cpProduct.getProductDesc(), "text/html; charset=UTF-8", null);//这种写法可以正确解码 
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
	        mWebView.setWebViewClient(new WebViewClient(){
	        	@Override
	        	public void onPageFinished(WebView view, String url) {
	        		if(null==mScrollView) return;
	        		Log.i("NOW", "");
					mScrollView.scrollBy(0, 120);
	        	};
	        });
		}else{
			if(isWebViewShow) mWebView.setVisibility(View.GONE);
			else mWebView.setVisibility(View.VISIBLE);
		}
		isWebViewShow = mWebView.getVisibility()==View.VISIBLE;
	}
	private int getShopTabId(int vid,int mt){
		int result;
		if(mt<0||mt==3){
			result = vid==R.id.btn_cp_2?1:2;
		}else{
			result = vid==R.id.btn_cp_3?1:2;
		}
		return result;
	}
	@Override
		public void onClick(View v) {
		
			int myid = MyData.data().getMe().getId();
			switch (v.getId()) {
			case R.id.btn_cp_coo:
					if(myid==0){
//						JackUtils.showToast(this, "您还没有登录");
						MyPortal.goStartLogin(this);
						break;
					}
				ActionRequestImpl actReq = btn_coo.isSelected()
						?new DeleteForOpenAPIReq(myid,cpProduct.getProductId(), 0)
						:new  SaveCollectionReq(myid,cpProduct.getProductId(),  0, "");
				ActionReceiverImpl rcv = new CPSaveReceiver();
				ActionReceiverImpl actRcv = new ActUpdateRcv( rcv, this);
					ActionBuilder.getInstance().request(actReq, actRcv);
					break;
			case R.id.btn_cp_1:
				if(myid==0	){MyPortal.goStartLogin(this);break;}
				if(null!=cpir){
					if(cpir.userId!=myid){//怎样以游客身份聊天?  0503
						if(cpir.textTalk!=null){
							if(myid!=0){//optimize this
								MyPortal.goChat(this, cpir.companyName, (long) cpir.textTalk, 0, "");//TODO
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
				MyPortal.goShop(this, cpir.shopId, cpir.companyName, cpir.memberType, getShopTabId(v.getId(), cpir.memberType));//TODO
				break;
			case R.id.btn_share:
//				share();
				showShareDialog();
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

	
	public class CPSaveReceiver implements ActionReceiverImpl{
		boolean btnShouldBeSelected;
		String toast;
		@Override
		public boolean response(String result) throws JSONException {
			//{"resultSign":"true","resultObj":null}
			boolean response = ActionStrategies.getResultTrueObject(result)!=null;
			if(response){
				if(btnShouldBeSelected = !btn_coo.isSelected()){
					toast="收藏成功";
				}else {
					toast = "取消收藏成功";
				} 
			} 
			return response;
		}

		@Override
		public Context getReceiverContext() {
			return CurProdActivity.this;
		}
		
	}
	
	
	public class CPInfoReceiver implements ActionReceiverImpl{
		int userId;
		int shopId;
		boolean isSaved;//
		int memberType=-1;
		String companyName;
		Long textTalk;
		String productUrl;

		public CPInfoReceiver( ){
		}
		
		@Override
		public boolean response(String result) throws JSONException {
			JSONObject job = ActionStrategies.getResultObject(result);
			if(null!=job){
				isSaved= job.getBoolean("isCollect");
				if(isSaved){
					btn_coo.setSelected(true);
				}
				productUrl = job.optString("productUrl");
				userId = job.getInt("memberId");
				shopId = job.getInt("companyId");
				memberType = job.getInt("memberType");
				companyName=job.optString("companyName");
				if(job.has("texTalk")&&!job.isNull("texTalk"))textTalk=job.getLong("texTalk");//TODO 
						
				SimpleCompany lc = new SimpleCompany();//0319
				lc.userId=userId;
				lc.shopId=shopId;
				lc.shopName=companyName;
				lc.memberType=memberType;
//				YftData.data().storeShopById(lc);//用于聊天界面的shop类
				return true;
			}
			return false;
			
		}
		
		@Override
		public Context getReceiverContext() {
			return CurProdActivity.this;
		}

		
	}
	
	
	
	/**
	 * 
	 */
	private void showShareDialog() {
		if(null==shareHelper)shareHelper = new ShareHelper(this);
		shareHelper.title = "向您推荐网上轻纺城的产品";
		shareHelper.desc = cpProduct.getProductName();
		shareHelper.shareUrl = null!=cpir?cpir.productUrl:null;
		String[] neatProductImgArray = cpProduct.getNeatProductImgArray();
		if(neatProductImgArray.length>0){
			shareHelper.setThumb(JackImageLoader.findBitmap(neatProductImgArray[0]));
		}
		shareHelper.showShareDialog();
	}

	@Override
	public boolean update(ActionReceiverImpl rcv) {
		if(rcv instanceof ActJobRcv){
			//产品基本信息
			cpProduct = new LIIProduct();
			cpProduct.initJackJson(((ActJobRcv) rcv).resultJob);
			if(cpProduct.getProductId()>0)init();
			else {
				JackUtils.showToast(MyApplication.app(), "对不起，暂无该产品详情");
				finish();
			}
		}else if(rcv instanceof CPInfoReceiver){
			//产品的用户相关信息
		}else if(rcv instanceof CPSaveReceiver){
			//收藏或取消
			CPSaveReceiver r = (CPSaveReceiver)rcv ;
			btn_coo.setSelected(r.btnShouldBeSelected);
			JackUtils.showToast(this, r.toast);
		}
		return false;
	}
	
	
	/*private void share(){
		Intent intent=new Intent(Intent.ACTION_SEND); 
		intent.setType("text/plain"); 
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享"); 
		intent.putExtra(Intent.EXTRA_TEXT, "来自网上轻纺城，这件产品不错："+cpProduct.getMainPic());  
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		startActivity(Intent.createChooser(intent, getTitle())); 
	}*/

}
