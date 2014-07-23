package com.qfc.yft.ui.tabs.information;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qfc.yft.R;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.tabs.ContentAbstractFragment;
import com.qfc.yft.util.JackRexUtil;
import com.qfc.yft.util.JackRexUtil.JackReRules;
import com.qfc.yft.util.JackUtils;

public class TabInformationFragment extends ContentAbstractFragment  {

	private WebView mWebView;

	@Override
	public void initView() {
		
		
		mWebView = (WebView)mView.findViewById(R.id.webview_information);
		mWebView.setVisibility(WebView.VISIBLE);
        WebSettings ws = mWebView.getSettings();
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(false);  
        ws.setBuiltInZoomControls(false);
        ws.setSupportZoom(false);
        mWebView.setInitialScale(100);
        ws.setJavaScriptEnabled(true);
        ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        String url = "http://m.tnc.com.cn/info/";
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put(NetConst.WEBVIEW_INFO, NetConst.WEBVIEW_FROM_ANDROID);
		mWebView.loadUrl(url, extraHeaders);
//		mWebView.loadUrl("183.129.179.36");
//		mWebView.loadUrl("http://www.baidu.com");
		mWebView.setWebViewClient(new WebViewClient(){
			private ProgressDialog pDialog;
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
//				Toast.makeText(RegisterActivity.this, "网上轻纺城提醒您： " + description, Toast.LENGTH_SHORT).show();
				JackUtils.showToast(getActivity(), description);
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				if(null!=pDialog&&pDialog.isShowing()) pDialog.hide();
				super.onPageFinished(view, url);
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
//				Log.i("onPageStarted", url);

				if(null==pDialog){
					pDialog = JackUtils.showProgressDialog(getActivity(), "加载中");
					pDialog.setCancelable(true);
				}
				else pDialog.show();
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				Log.i("shouldOverrideUrlLoading",url);
				boolean check;
				check = JackRexUtil.checkRE(JackReRules.RE_INFO_1, url)
						|| JackRexUtil.checkRE(JackReRules.RE_INFO_2, url)
						|| JackRexUtil.checkRE(JackReRules.RE_INFO_3, url)
						|| JackRexUtil.checkRE(JackReRules.RE_INFO_4, url);

				if (check) {

					Intent intent = new Intent();
					intent.putExtra("informationurl", url);
					intent.setClass(getActivity(), InformationActivity.class);
					startActivity(intent);
				}
				return check;
			}
			@Override
			public void onScaleChanged(WebView view, float oldScale,
					float newScale) {
				super.onScaleChanged(view, oldScale, oldScale);
			}
		});
		
//		mWebView.addJavascriptInterface(obj, "");
//		mTitle.setOnClickListener(this);
//		mTitle.setRightImg(R.drawable.btn_share, this);
	}

	@Override
	public int getLayoutRid() {
		return R.layout.fragment_information;
	}

	
	
}
