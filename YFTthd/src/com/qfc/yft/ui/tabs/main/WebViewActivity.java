package com.qfc.yft.ui.tabs.main;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.common.StringUtils;
import com.qfc.yft.R;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.vo.LIIProduct;

public class WebViewActivity extends MyTitleActivity {

	WebView mWebView;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_frame;
	}

	@Override
	public void initView() {
		Bundle extras = getIntent().getExtras();
		String mName = extras.getString(NetConst.EXTRAS_MARKETNAME);
		String mUrl = extras.getString(NetConst.EXTRAS_MARKETURL);
		
		titleManager.setTitleName(mName);
		titleManager.initTitleBack();
		titleManager.setRightImg(R.drawable.title_filter, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null!=mWebView){
					mWebView.loadUrl("javascript:appPanelOpen();");
				}
			}
		});
		
		mWebView = new WebView(this);// 
		((FrameLayout)findViewById(R.id.frame_common)).addView(mWebView);
		mWebView.setWebChromeClient(new WebChromeClient(){
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
//				Log.i(TAG, url+"_"+message+"+"+result);
				AlertDialog.Builder builder = new Builder(WebViewActivity.this);
				  builder.setMessage(message);
				  builder.setTitle("网上轻纺城提醒您");
				  builder.setOnKeyListener(new DialogInterface.OnKeyListener() {  
			            public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {  
			                Log.v("onJsAlert", "keyCode==" + keyCode + "event="+ event);  
			                return true;  
			            }  
			        });  
				 builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
						dialog.dismiss();
					}
				});
				builder.show();
				return true;
			}
			
			
		});
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				Toast.makeText(WebViewActivity .this, "网上轻纺城提醒您： " + description, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d("regist_finished", url);
				 
				super.onPageFinished(view, url);
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				Log.i("onPageStarted", url);

				 
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				final String USERCODE = "userCode=";
				if(url.startsWith("http://m.qfc.cn/products/detail")){
					//http://m.qfc.cn/products/detail-160242.html
					Integer pid = Integer.parseInt(url.substring(url.indexOf("detail"),url.lastIndexOf(".")).replaceAll("[^0-9]", ""));
					MyPortal.goProduct(WebViewActivity.this, pid );
					
					return true;
				}
				return false;
			}
			@Override
			public void onScaleChanged(WebView view, float oldScale,
					float newScale) {
				super.onScaleChanged(view, oldScale, oldScale);
			}
		});
		mWebView.setVisibility(WebView.VISIBLE);
        WebSettings ws = mWebView.getSettings();
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(false);  
        ws.setBuiltInZoomControls(false);
        ws.setSupportZoom(false);
        mWebView.setInitialScale(100);
        ws.setJavaScriptEnabled(true);

     // 让网页自适应屏幕宽度

        ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put(NetConst.WEBVIEW_INFO, NetConst.WEBVIEW_FROM_ANDROID);
		mWebView.loadUrl(mUrl, extraHeaders);

	}

}
