package com.qfc.yft.ui.account;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.User;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.tabs.HubActivity;
import com.qfc.yft.utils.JackUtils;

public class RegisterActivity extends Activity implements HttpReceiver{
	String url ;
	boolean isReg;
	
	WebView webView;
	TextView tv_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypeople);
		url = getIntent().getStringExtra(YftValues.EXTRA_REGISTER_URL);
		if(url==null ||url.isEmpty()) url=YftValues.URL_REGISTER;
		isReg = url.equals(YftValues.URL_REGISTER);
		
		webView = new WebView(this);//(WebView)findViewById(R.id.wb_regist);
		((FrameLayout)findViewById(R.id.frame_people)).addView(webView);
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
//				Log.i(TAG, url+"_"+message+"+"+result);
//				return super.onJsAlert(view, url, message, result);
				AlertDialog.Builder builder = new Builder(RegisterActivity.this);
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
//				JackUtils.showToast(RegisterActivity.this, message);
				return true;
			}
			
			
		});
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				Toast.makeText(RegisterActivity.this, "网上轻纺城提醒您： " + description, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d("regist_finished", url);
				tv_title = (TextView)findViewById(R.id.tv_title);
				tv_title.setText(R.string.title_regist);
				if(!isReg)tv_title.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				Log.i("onPageStarted", url);

				if(tv_title!=null) {
					tv_title.setText(getString(R.string.loading));
				}
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				Log.i("shouldOverrideUrlLoading",url);
//				return super.shouldOverrideUrlLoading(view, url);
				final String USERCODE = "userCode=";
				if(url.contains(USERCODE)){
					String result= url.substring(url.indexOf(USERCODE)+USERCODE.length());
//					Log.i(TAG, "result:"+result);
					new HttpRequestTask(RegisterActivity.this).execute(YftValues.getHTTPBodyString(RequestType.MEMBER_INFO, result));
				}
				return true;
			}
			@Override
			public void onScaleChanged(WebView view, float oldScale,
					float newScale) {
				super.onScaleChanged(view, oldScale, oldScale);
			}
		});
		webView.setVisibility(WebView.VISIBLE);
//		webView.zoomOut();//1209
//		webView.zoomOut();//1209
        WebSettings ws = webView.getSettings();
//        ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(false);  
        ws.setBuiltInZoomControls(false);
        ws.setSupportZoom(false);
        webView.setInitialScale(100);
        
        ws.setJavaScriptEnabled(true);
//        ws.setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.addJavascriptInterface(new ContactsPlugin(), "contactsAction");
        
        //设置可以支持缩放   
//        ws.setSupportZoom(true);   

        //设置默认缩放方式尺寸是far   
        //设置出现缩放工具   


     // 让网页自适应屏幕宽度

        if(!isReg) ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        
        
		webView.loadUrl(url);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {       
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {       
            webView.goBack();       
                   return true;       
        }       
        return super.onKeyDown(keyCode, event);       
    }
	
	private void enter(boolean has){
		Intent intent = new Intent();
		intent.setClass(this, HubActivity.class);
//		intent.putExtra(YftValues.MAIN_LG, has?0:1);//无店则不入店 TODO
		startActivity(intent);
		finish();
		//TODO 纺织聊
	}
	private void fail() {
		JackUtils.showToast(this, "登录失败");
	}
	@Override
	public void response(String result) {
		JSONObject job;
		try {
			job = YftValues.getResultObject(result);
			if(job!=null){
				
				// 保存user信息
				User user = new User();
				boolean iReturn = user.initWithJsonString(job.toString());
//				if(!iReturn) Log.e(TAG, "user init failed!!");
				YftData.data().setMe(user);//1029
				YftData.data().setMeCurrentUser();
				//		YftData.data().setCurrentUser(user);
				//跳转
				enter(user.getMemberType()>2);
				SharedPreferences pref = getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
				pref.edit().putString(getString(R.string.pref_username), user.getUserName()).commit();//成功则保存用户名
			}else{
				fail();
			}
		} catch (JSONException e) {
			fail();
			e.printStackTrace();
		}
	}
	@Override
	public Context getReceiverContext() {
		return this;
	}   
}
