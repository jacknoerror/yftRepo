package com.qfc.yft.ui.account;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qfc.yft.R;
import com.qfc.yft.net.action.ActJobRcv;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.member.SendMobileCodeReq;
import com.qfc.yft.ui.TitleManager;
import com.qfc.yft.util.ResourceUtils;
import com.qfc.yft.util.ValidateUtils;

public abstract class AccountStepOneActivity extends Activity implements OnClickListener {
	
	public static final String TAG = "YFT";
	public static final Integer SEND_MOBILE_CODE_TYPE_REGISTER = 1;
	public static final Integer SEND_MOBILE_CODE_TYPE_RESET = 2;
	public static final String KEY_SEND_PHONE = "send_phone";
	public static final String KEY_SEND_REMAIN_TIMES = "send_remain_times";
	public static final String KEY_SEND_DATE = "send_date";
	public static final String KEY_SEND_CAPTCHA = "send_captcha";
	
	EditText phone;
	Button ok;
	TextView license;
	TitleManager titleManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_step1);
		
		initTitle();
		initViews();
	}
	
	protected void initTitle() {
		titleManager = new TitleManager(this);
		titleManager.initTitleBack();
	}
	
	protected void initViews() {
		
		phone = (EditText) findViewById(R.id.account_step1_text_phone);
		ok = (Button) findViewById(R.id.account_step1_btn_ok);
		license = (TextView) findViewById(R.id.account_step1_text_license);
		
		phone.addTextChangedListener(phoneNumberWatcher);
		ok.setOnClickListener(this);
		license.setOnClickListener(this);
	}
	
	protected abstract void resetPhoneHint();
	
	protected abstract void jumpToStepTwo();
	
	protected abstract Integer getSmsType();
	
	private boolean checkPhoneNumber() {
		
		String phoneNumber = phone.getText().toString();
		if(!ValidateUtils.validatePhoneNumber(phoneNumber)) {
			
			phone.setHint("");
			phone.setError(getString(R.string.account_step1_warn_phone));
			
			return false;
		}
		
		return true;
	}
	
	private void saveSendInfo(JSONObject resultJob) throws JSONException {
		
		if (!resultJob.has("mobileValidCodeNum")) {
			throw new JSONException("剩余验证码发送次数为空");
		}
		
		if(!resultJob.has("mobileValidDate") && resultJob.getJSONObject("mobileValidDate").has("time")) {
			throw new JSONException("发送证码发送日期格式为空");
		}
		
		if(!resultJob.has("mobileValidCode")) {
			throw new JSONException("验证码格式为空");
		}
		
		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE);
		
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_SEND_PHONE, phone.getText().toString());
		editor.putInt(KEY_SEND_REMAIN_TIMES, resultJob.getInt("mobileValidCodeNum"));
		editor.putString(KEY_SEND_DATE, new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date(resultJob.getJSONObject("mobileValidDate").getLong("time"))));
		editor.putString(KEY_SEND_CAPTCHA, resultJob.getString("mobileValidCode"));
		editor.commit();
	}
	
	private void sendSms() throws Exception {
		
		ActionRequestImpl actReq = new SendMobileCodeReq(phone.getText().toString(), getSmsType());
		
		ActionReceiverImpl actRcv = new ActJobRcv(this) {
			@Override
			public boolean response(String result) throws JSONException {
				
				boolean sendFlag = super.response(result);
				if(sendFlag) {
					saveSendInfo(resultJob);
					jumpToStepTwo();
				}
				
				return sendFlag;
			}
		};
		
		ActionBuilder.getInstance().request(actReq , actRcv);
	}
	
	private void getCaptcha() {
		
		try {
			if(checkPhoneNumber()) {
				sendSms();
			}
		} catch (Exception ex) {
			Log.e(TAG, "getCaptcha fail", ex);
			Toast.makeText(this, R.string.account_step1_error, Toast.LENGTH_LONG).show();
		}
	}
	
	private void jumpToLicensePage() {
		Intent intent = new Intent();
		intent.setClass(this, RegisterLicenseActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.account_step1_btn_ok:
				getCaptcha();
				break;
			case R.id.account_step1_text_license:
				jumpToLicensePage();
				break;
			default:
				break;
		}
	}
	
	TextWatcher phoneNumberWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			String phoneNumber = phone.getText().toString();
			if(null == phoneNumber || "".equals(phoneNumber)) {
				phone.setTextSize(TypedValue.COMPLEX_UNIT_SP, ResourceUtils.getXmlDef(AccountStepOneActivity.this, R.dimen.account_edittext_textsize_normal));
				resetPhoneHint();
			} else {
				phone.setTextSize(TypedValue.COMPLEX_UNIT_SP, ResourceUtils.getXmlDef(AccountStepOneActivity.this, R.dimen.account_edittext_textsize_large));
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		
		@Override
		public void afterTextChanged(Editable s) {}
	};
}
