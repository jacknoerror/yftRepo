package com.qfc.yft.ui.activity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewTestActivity extends Activity {
	final String TAG = getClass().getSimpleName();
	
	private String keyword;
	private WebView mWebview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWebview = new WebView(this);
		setContentView(mWebview);
		
		WebSettings webSettings = mWebview.getSettings(); 
        webSettings.setJavaScriptEnabled(true); 
        webSettings.setSaveFormData(false); 
//        webSettings.setSavePassword(false); 
        webSettings.setSupportZoom(false); 
        
		mWebview.setWebChromeClient(new WebChromeClient(){

			private String detailTitle;

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
		mWebview.setWebViewClient(new WebViewClient(){
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
//				Toast.makeText(WebviewTestActivity.this, "ÍøÉÏÇá·Ä³ÇÌáÐÑÄú£º " + description, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.loadUrl("javascript:alert(\"mytitle\"+document.getElementById(\"e\").innerHTML);");
				 
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

		keyword = "jack";
		mWebview.loadUrl("http://dict.cn/mini.php?q="+keyword );
	}
	
}
