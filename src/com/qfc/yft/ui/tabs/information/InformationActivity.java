package com.qfc.yft.ui.tabs.information;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.qfc.yft.R;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.util.ShareHelper;

public class InformationActivity extends MyTitleActivity {
	
	private String detailTitle="";
	
	private ShareHelper shareHelper;
	private WebView mWebView;
	/**
	 * 
	 */
	private void showShareDialog() {//资讯标题＋资讯详情页的web地址


		if(null==shareHelper)shareHelper = new ShareHelper(this);
		shareHelper.title = "分享一个纺织资讯";
		shareHelper.desc = detailTitle;
		shareHelper.shareUrl = mWebView.getUrl();
//			shareHelper.setThumb(JackImageLoader.findBitmap(neatProductImgArray[0]));
		shareHelper.showShareDialog();
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
	@Override
	public int getLayoutRid() {
		return R.layout.activity_webview;
	}
	@Override
	public void initView() {
		titleManager.setRightImg(R.drawable.btn_share_transbg,
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						showShareDialog();
					}
				});
		titleManager.setTitleName(getString(R.string.titlename_information_detail));
		titleManager.initTitleBack();
		mWebView = (WebView	)this.findViewById(R.id.webview_information);
		String url  = getIntent().getStringExtra("informationurl");
		
		if(null==url||url.isEmpty()) return;
		
		WebSettings ws = mWebView.getSettings();
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(false);  
        ws.setBuiltInZoomControls(false);
        ws.setSupportZoom(false);
        mWebView.setInitialScale(100);
        ws.setJavaScriptEnabled(true);
        ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		
        mWebView.setWebChromeClient(new WebChromeClient(){

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				String prefix = "mytitle";
				if(message.startsWith(prefix)){
					detailTitle = message.substring(message.indexOf(prefix)+prefix.length());
					Log.i(TAG, detailTitle);
					result.confirm();
					return true;
				}
				return false;
			}
			
		});
        mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				Toast.makeText(InformationActivity.this, "网上轻纺城提醒您： " + description, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.loadUrl("javascript:alert(\"mytitle\"+document.title);");
				 
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return super.shouldOverrideUrlLoading(view, url);
			}
			@Override
			public void onScaleChanged(WebView view, float oldScale,
					float newScale) {
				super.onScaleChanged(view, oldScale, oldScale);
			}
		});
        
		mWebView.loadUrl(url);
		
		
		
	
		
	}
}
