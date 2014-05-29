package com.qfc.yft.ui.account;

import org.json.JSONObject;

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
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.member.PointVerifyReq;
import com.qfc.yft.ui.custom.JackResizeLayout;
import com.qfc.yft.util.JackButtonColorFilter;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.User;

public class StartLoginActivity extends Activity implements HttpReceiver,View.OnClickListener{
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
	
	int requestStep;
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
				case 1://��С
					sLayout.setPadding(sLayPadRect.left, sLayPadRect.top, sLayPadRect.right, 0);
					if(null!=logo) logo.setVisibility(View.INVISIBLE);
					break;
				case 2://���
					sLayout.setPadding(sLayPadRect.left, sLayPadRect.top, sLayPadRect.right, sLayPadRect.bottom);
					if(null!=logo) logo.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
			}
		};
		requestStep=0;
//		TestDataTracker.simulateConnection(this, RequestType.MEMBER_INFO.toString());//miss '{'
	}

	private void initViews() {
		logo = (ImageView)findViewById(R.id.img_login);
		et1=(EditText)findViewById(R.id.et_login_account);
		et2=(EditText)findViewById(R.id.et_login_password);
		
		sLayout = (LinearLayout)findViewById(R.id.layout_login);
		sLayPadRect = new Rect(sLayout.getPaddingLeft(), sLayout.getPaddingTop(), sLayout.getPaddingRight(), sLayout.getPaddingBottom());
		
		if(resizeLayout!=null) {
			resizeLayout.setOnResizeListener(new JackResizeLayout.OnResizeListener() {
				
				@Override
				public void OnResize(int w, int h, int oldw, int oldh) {
					
					if(sLayout!=null&&sLayPadRect!=null){
						mHandler.sendEmptyMessage(oldh>h?1:2);
					}
				}
			});
		}
		
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
	}
	
	private void initOfflinePref(User user) {
		if(null!=user&&user.getShopId()>0){
			OfflineDataKeeper.setCurrentUserOfflinePreference(this, user.getShopId());
			//
		}
		
	}
	
	private void enter(boolean has){
//		if(has)ChatLoginHelper.getInstance().logInChatGo(username, password);//TODO ��֯��
		
		/*Intent intent = new Intent();
		intent.setClass(this, HubActivity.class);
		startActivity(intent);*/
		//TODO ��¼���л�����
	}

	private void goSlide() {
		Intent intent = new Intent();
		intent.setClass(this, StartPagerActivity.class);
		startActivity(intent);
	}
	
	private void goLogin(){
//		hideSoftKeyboard();
		if(requestStep>0) return;
		username = et1.getText().toString();
		password = et2.getText().toString();
		if(username.equals("")) {
			JackUtils.showToast(this, "�û�������Ϊ��");
			return;
		}
		else if(password.equals("")){
			JackUtils.showToast(this, "���벻��Ϊ��");
			return;
		}
		else {
//			new HttpRequestTask(this).execute(YftValues.getHTTPBodyString(RequestType.POINT_VERIFY, 
//					username,password));
			requestStep=1;
			
			ActionRequestImpl actReq = new PointVerifyReq(username, password);
			ActionReceiverImpl actRcv = null;
			ActionBuilder.getInstance().request(actReq, actRcv);
		}
		hideSoftKeyboard();
	}
	private void goRegister(){
		//TODO �ĳ���ע�����
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
	public void response(String result) {
		String rSign="resultSign",rObj = "resultObj",rError="errorMsg";
		try {
			JSONObject job = new JSONObject(result);//if null?
			if(job.has(rSign)){
				if(job.getBoolean(rSign)){//success
					
					if(job.has(rObj)){
						String ro = job.getString(rObj);
						Log.i(TAG, ro+";::resultObj");
						switch (requestStep) {
						case 1:
							new HttpRequestTask(this).execute(YftValues.getHTTPBodyString(RequestType.MEMBER_INFO, ro));
							requestStep=2;
							break;
						case 2:
							// ����user��Ϣ
							User user = new User();
							boolean iReturn = user.initWithJsonString(ro);
							if(!iReturn) Log.e(TAG, "user init failed!!");
							MyData.data().setMe(user);//1029
							MyData.data().setMeCurrentUser();
									
							
							//��ת
							enter(user.getMemberType()>2);
							pref.edit().putString(getString(R.string.pref_username), username).commit();//�ɹ��򱣴��û���
							initOfflinePref(user);
							requestStep=0;
//							TestDataTracker.settleDataString(RequestType.MEMBER_INFO.toString(), result);//
							break;
						default:
							break;
						}
					}
				}else{//failed
					if(job.has(rError)){
						JackUtils.showToast(this, job.getString(rError));
					}
					requestStep=0;
				}
			}
			
			
		} catch (Exception e) {
			requestStep=0;
			e.printStackTrace();
		}
		
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
	
	
	@Override
	public Context getReceiverContext() {
		return this;
	}
	
	
}
