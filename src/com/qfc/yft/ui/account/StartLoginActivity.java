package com.qfc.yft.ui.account;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.MyData;
import com.qfc.yft.entity.offline.OfflineDataKeeper;
import com.qfc.yft.net.action.ActStringRcv;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.BareReceiver;
import com.qfc.yft.net.action.member.MemberInfoReq;
import com.qfc.yft.net.action.member.PointVerifyReq;
import com.qfc.yft.ui.common.StartPagerActivity;
import com.qfc.yft.ui.custom.JackResizeLayout;
import com.qfc.yft.ui.tab.HubActivity;
import com.qfc.yft.util.JackButtonColorFilter;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.User;

public class StartLoginActivity extends Activity implements View.OnClickListener{
	final String TAG = StartLoginActivity.class.getSimpleName();
	
	EditText et1,et2;
	TextView tv1,tv2;
	Button sButton;
	LinearLayout sLayout;
	Rect sLayPadRect;
	int sLayHeight;
	JackResizeLayout resizeLayout;
	ImageView logo;
	
	Handler mHandler;
	SharedPreferences pref;
	
	String username,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resizeLayout = (JackResizeLayout)(LayoutInflater.from(this).inflate(R.layout.activity_login, null));
		setContentView(resizeLayout);
		initViews();
//		slideview( 0, -100);
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1://变小
					sLayout.setPadding(sLayPadRect.left, sLayPadRect.top, sLayPadRect.right, 0);
					if(null!=logo) logo.setVisibility(View.INVISIBLE);
					break;
				case 2://变大
					sLayout.setPadding(sLayPadRect.left, sLayPadRect.top, sLayPadRect.right, sLayPadRect.bottom);
					if(null!=logo) logo.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
			}
		};
//		TestDataTracker.simulateConnection(this, RequestType.MEMBER_INFO.toString());//miss '{'
	}

	private void initViews() {
		logo = (ImageView)findViewById(R.id.img_login);
		et1=(EditText)findViewById(R.id.et_login_account);
		et2=(EditText)findViewById(R.id.et_login_password);
		
		sLayout = (LinearLayout)findViewById(R.id.layout_login);
		sLayPadRect = new Rect(sLayout.getPaddingLeft(), sLayout.getPaddingTop(), sLayout.getPaddingRight(), sLayout.getPaddingBottom());
		
			resizeLayout.setOnResizeListener(new JackResizeLayout.OnResizeListener() {
				
				@Override
				public void OnResize(int w, int h, int oldw, int oldh) {
					
					if(sLayout!=null&&sLayPadRect!=null){
						mHandler.sendEmptyMessage(oldh>h?1:2);
					}
				}
			});
		
		sButton = (Button)findViewById(R.id.btn_log);
		JackButtonColorFilter.setButtonFocusChanged(sButton);
		sButton.setOnClickListener(this);
		
		tv1 = (TextView)findViewById(R.id.tv_register);
		tv2 = (TextView)findViewById(R.id.tv_guest);
		JackUtils.textpaint_underline(tv1);
		JackUtils.textpaint_underline(tv2);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		
		pref = getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
		if(pref.contains(getString(R.string.pref_username))) 
			et1.setText(pref.getString(getString(R.string.pref_username), "hansamao"));
		if(!pref.contains(getString(R.string.pref_notfirsttime))){
			goSlide();
		}
		
		et2.setText("333333a");//FIXME delete
	}
	
	private void initOfflinePref(User user) {
		if(null!=user&&user.getShopId()>0){
			OfflineDataKeeper.setCurrentUserOfflinePreference(this, user.getShopId());
			//
		}
		
	}
	
	private void enter(boolean has){
//		if(has)ChatLoginHelper.getInstance().logInChatGo(username, password);//TODO 纺织聊
		
		Intent intent = new Intent();
		intent.setClass(this, HubActivity.class);
		startActivity(intent);
		//  登录后切换界面
	}

	private void goSlide() {
		Intent intent = new Intent();
		intent.setClass(this, StartPagerActivity.class);
		startActivity(intent);
	}
	
	private void goLogin(){
		username = et1.getText().toString();
		password = et2.getText().toString();
		if(username.equals("")) {
			JackUtils.showToast(this, "用户名不能为空");
			return;
		}
		else if(password.equals("")){
			JackUtils.showToast(this, "密码不能为空");
			return;
		}
		else {
			
			ActionRequestImpl actReq = new PointVerifyReq(username, password);
			ActStringRcv actRcv = new ActStringRcv(this){
				@Override
				public boolean response(String result) throws JSONException {
					boolean response = super.response(result);
					if(response){
						MyData.data().setUserCode(resultStr);//0603
						ActionRequestImpl actReq0 = new MemberInfoReq(resultStr);
						ActionReceiverImpl actRcv0 = new BareReceiver(StartLoginActivity.this){
							@Override
							public boolean response(String result) throws JSONException {
								boolean response = super.response(result);
								// 保存user信息
								User user = new User();
								boolean iReturn = user.initWithJsonString(resultJob.getJSONObject(RESULT_OBJECT));
								if(!iReturn) Log.e(TAG, "user init failed!!");
								MyData.data().setMe(user);//1029
								MyData.data().setMeCurrentUser();
								//跳转
								enter(user.getMemberType()>2);
								pref.edit().putString(getString(R.string.pref_username), username).commit();//成功则保存用户名
								initOfflinePref(user);
								
								return response&&iReturn;
							};
						}
						;
						ActionBuilder.getInstance().request(actReq0, actRcv0);
					}
					return response;
				}
			};
			ActionBuilder.getInstance().request(actReq, actRcv);
		}
		hideSoftKeyboard();
	}
	private void goRegister(){
		//TODO 改成新注册界面
//		Intent intent = new Intent();
//		intent.setClass(StartLoginActivity.this, RegisterActivity.class);
//		StartLoginActivity.this.startActivity(intent);
//		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	
	}
	private void goGuest(){
		enter(false);
		User guest  = new User();//1113
		guest.setShopId(-1);//
		MyData.data().setMe(guest);
	}

	private void hideSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.SHOW_FORCED);
	}
	

	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_log:
			goLogin();
			break;
		case R.id.tv_register:
			goRegister();
			break;
		case R.id.tv_guest:
			goGuest();
			break;
		default:
			break;
		}
		
	}
	
	
}
