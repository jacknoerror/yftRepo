package com.qfc.yft.ui.account;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;

import com.qfc.yft.R;
import com.qfc.yft.ui.TitleManager;

@SuppressWarnings("deprecation")
public class RegisterLicenseActivity extends Activity {
	
	public static final String TAG = "YFT";
	public static final String REGISTER_LICENSE_WEB_URL = "http://member.qfc.cn/my/agreement-android.html";
	public static final String REGISTER_LICENSE_LOCAL_URL = "file:///android_asset/license.htm";
	
	private WebView licensePage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_license);
		
		initTitle();
		initViews();
		loadLicensePage();
		setPageStyle();
	}
	
	private void initTitle() {
		TitleManager titleManager = new TitleManager(this);
		titleManager.setTitleName(getString(R.string.account_title_license));
		titleManager.initTitleBack();
	}
	
	private void initViews() {
		licensePage = (WebView) findViewById(R.id.account_license_page);
	}
	
	private void loadLicensePage() {
		licensePage.loadUrl(REGISTER_LICENSE_LOCAL_URL);
	}
	
	private void setTextSize(WebSettings settings) {
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int dpi = metrics.densityDpi;
		
		Log.d(TAG, "dpi is " + dpi);
		Log.d(TAG, "detail is " + metrics.toString());
		
		if (dpi <= 120) {
			settings.setDefaultZoom(ZoomDensity.CLOSE);
		} else if (dpi > 120 && dpi < 240) {
			settings.setDefaultZoom(ZoomDensity.MEDIUM);
		} else if (dpi >= 240) {
			settings.setDefaultZoom(ZoomDensity.FAR);
		}
	}
	
	private void setPageStyle() {
		
		WebSettings settings = licensePage.getSettings();
		
		settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        
        setTextSize(settings);
	}
}
