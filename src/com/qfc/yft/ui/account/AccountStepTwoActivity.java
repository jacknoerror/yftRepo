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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.qfc.yft.ui.tabs.HubActivity;
import com.qfc.yft.util.ResourceUtils;
import com.qfc.yft.util.ValidateUtils;

public abstract class AccountStepTwoActivity extends Activity implements OnClickListener {
	
	public static final String TAG = "YFT";
	public static final int MAX_COUNTDOWN_SECOND = 60;
	public static final int MAX_RESEND_TIMES = 9;
	public static final Integer SEND_MOBILE_CODE_TYPE_REGISTER = 1;
	public static final Integer SEND_MOBILE_CODE_TYPE_RESET = 2;
	public static final String KEY_COUNTDOWN_SECOND = "countdown_second";
	public static final String KEY_SEND_PHONE = "send_phone";
	public static final String KEY_SEND_REMAIN_TIMES = "send_remain_times";
	public static final String KEY_SEND_DATE = "send_date";
	public static final String KEY_SEND_CAPTCHA = "send_captcha";
	
	EditText captcha;
	TextView resend;
	EditText password;
	EditText reenter;
	Button ok;
	TitleManager titleManager;
	int currentResendRemainTimes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_step2);
		
		initTitle();
		initViews();
		
		currentResendRemainTimes = getSendRemainTimes();
		setResendInfo(MAX_COUNTDOWN_SECOND, currentResendRemainTimes);
		startSetResendInfo();
	}
	
	protected void initTitle() {
		titleManager = new TitleManager(this);
		titleManager.initTitleBack();
	}
	
	protected void initViews() {
		
		captcha = (EditText) findViewById(R.id.account_step2_text_captcha);
		resend = (TextView) findViewById(R.id.account_step2_text_resend);
		password = (EditText) findViewById(R.id.account_step2_text_password);
		reenter = (EditText) findViewById(R.id.account_step2_text_reenter);
		ok = (Button) findViewById(R.id.account_step2_btn_ok);
		
		captcha.addTextChangedListener(captchaWatcher);
		password.addTextChangedListener(passwordWatcher);
		reenter.addTextChangedListener(reenterWatcher);
		ok.setOnClickListener(this);
	}
	
	protected abstract Integer getSmsType();
	
	protected abstract void confirm();
	
	protected abstract void showError();
	
	protected String getSendPhone() {
		
		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE);
		
		return sharedPreferences.getString(KEY_SEND_PHONE, null);
	}
	
	protected void saveUserInfo() {
		
		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE);
		
		Editor editor = sharedPreferences.edit();
		editor.putString(getString(R.string.pref_username), sharedPreferences.getString(KEY_SEND_PHONE, null));
		editor.commit();
	}
	
	protected void enter() {
		
		Intent intent = new Intent();
		intent.setClass(this, HubActivity.class);
		startActivity(intent);
		
		finish();
	}

	private void setResendInfo(int second, int times) {
		
		StringBuffer resendInfo = new StringBuffer();
		
		if(times > 0) {
			if(second > 0) {
				resendInfo.append(second).append(getString(R.string.account_step2_text_countdown_start))
					.append(times).append(getString(R.string.account_step2_text_countdown_end));
			} else {
				resendInfo.append(getString(R.string.account_step2_text_countdown_resend)).append(times)
					.append(getString(R.string.account_step2_text_countdown_end));
				
				resend.setOnClickListener(this);
			}
		} else {
			setSendRemainTimes(0);
			resendInfo.append(getString(R.string.account_step2_text_countdown_over));
		}
		
		resend.setText(resendInfo.toString());
	}
	
	private void startSetResendInfo() {
		new Thread(new CountdownRunnable()).start();
	}
	
	private void setSendRemainTimes(int times) {
		
		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE);
		
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_SEND_REMAIN_TIMES, times);
		editor.commit();
	}
	
	private int getSendRemainTimes() {
		
		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE);
		
		return sharedPreferences.getInt(KEY_SEND_REMAIN_TIMES, 0);
	}
	
	private void saveSendInfo(JSONObject resultJob) throws JSONException {
		
		if (!resultJob.has("mobileValidCodeNum")) {
			throw new JSONException("ʣ����֤�뷢�ʹ���Ϊ��");
		}
		
		if(!resultJob.has("mobileValidDate") && resultJob.getJSONObject("mobileValidDate").has("time")) {
			throw new JSONException("����֤�뷢�����ڸ�ʽΪ��");
		}
		
		if(!resultJob.has("mobileValidCode")) {
			throw new JSONException("��֤���ʽΪ��");
		}
		
		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE);
		
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_SEND_REMAIN_TIMES, resultJob.getInt("mobileValidCodeNum"));
		editor.putString(KEY_SEND_DATE, new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date(resultJob.getJSONObject("mobileValidDate").getLong("time"))));
		editor.putString(KEY_SEND_CAPTCHA, resultJob.getString("mobileValidCode"));
		editor.commit();
	}
	
	private void sendSms() throws Exception {
		
		ActionRequestImpl actReq = new SendMobileCodeReq(getSendPhone(), getSmsType());
		
		ActionReceiverImpl actRcv = new ActJobRcv(this) {
			@Override
			public boolean response(String result) throws JSONException {
				
				boolean sendFlag = super.response(result);
				if(sendFlag) {
					
					saveSendInfo(resultJob);
					
					currentResendRemainTimes = getSendRemainTimes();
					setResendInfo(MAX_COUNTDOWN_SECOND, currentResendRemainTimes);
					startSetResendInfo();
				} else {
					resend.setClickable(true);
				}
				
				return sendFlag;
			}
		};
		
		ActionBuilder.getInstance().request(actReq , actRcv);
	}
	
	private void resend() {
		
		try {
			resend.setClickable(false);
			sendSms();
		} catch (Exception ex) {
			Log.e(TAG, "getCaptcha fail", ex);
			Toast.makeText(this, R.string.account_step1_error, Toast.LENGTH_LONG).show();
		}
	}
	
	private boolean checkCaptcha() {
		return ValidateUtils.validateRegx("^\\S{6}$", captcha.getText().toString());
	}
	
	private boolean checkPassword() {
		return ValidateUtils.validateRegx("^\\S{6,20}$", password.getText().toString());
	}
	
	private boolean checkReenter() {
		
		if(!ValidateUtils.validateRegx("^\\S{6,20}$", reenter.getText().toString())) {
			return false;
		}
		
		return reenter.getText().toString().equals(password.getText().toString());
	}
	
	private void execute() {
		
		try {
			
			if(!checkCaptcha()) {
				captcha.setError(getString(R.string.account_step2_warn_captcha));
				captcha.requestFocus();
				return;
			}
			
			if(!checkPassword()) {
				password.setError(getString(R.string.account_step2_warn_password));
				password.requestFocus();
				return;
			}
			
			if(!checkReenter()) {
				reenter.setError(getString(R.string.account_step2_warn_reenter));
				reenter.requestFocus();
				return;
			}
			
			confirm();
		} catch (Exception ex) {
			Log.e(TAG, "register fail", ex);
			showError();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.account_step2_btn_ok:
				execute();
				break;
			case R.id.account_step2_text_resend:
				resend();
				break;
			default:
				break;
		}
	}
	
	TextWatcher captchaWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			String captchaStr = captcha.getText().toString();
			if(null == captchaStr || "".equals(captchaStr)) {
				captcha.setTextSize(TypedValue.COMPLEX_UNIT_SP, ResourceUtils.getXmlDef(AccountStepTwoActivity.this, R.dimen.account_edittext_textsize_normal));
				captcha.setHint(R.string.account_step2_hint_captcha);
			} else {
				captcha.setTextSize(TypedValue.COMPLEX_UNIT_SP, ResourceUtils.getXmlDef(AccountStepTwoActivity.this, R.dimen.account_edittext_textsize_large));
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		
		@Override
		public void afterTextChanged(Editable s) {}
	};
	
	TextWatcher passwordWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			String passwordStr = password.getText().toString();
			if(null == passwordStr || "".equals(passwordStr)) {
				password.setTextSize(TypedValue.COMPLEX_UNIT_SP, ResourceUtils.getXmlDef(AccountStepTwoActivity.this, R.dimen.account_edittext_textsize_normal));
				password.setHint(R.string.account_step2_hint_password);
			} else {
				password.setTextSize(TypedValue.COMPLEX_UNIT_SP, ResourceUtils.getXmlDef(AccountStepTwoActivity.this, R.dimen.account_edittext_textsize_large));
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		
		@Override
		public void afterTextChanged(Editable s) {}
	};
	
	TextWatcher reenterWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			String reenterStr = reenter.getText().toString();
			if(null == reenterStr || "".equals(reenterStr)) {
				reenter.setTextSize(TypedValue.COMPLEX_UNIT_SP, ResourceUtils.getXmlDef(AccountStepTwoActivity.this, R.dimen.account_edittext_textsize_normal));
				reenter.setHint(R.string.account_step2_hint_reenter);
			} else {
				reenter.setTextSize(TypedValue.COMPLEX_UNIT_SP, ResourceUtils.getXmlDef(AccountStepTwoActivity.this, R.dimen.account_edittext_textsize_large));
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		
		@Override
		public void afterTextChanged(Editable s) {}
	};
	
	class CountdownHandler extends Handler {
		
		public CountdownHandler() {
			
		}
		
		public CountdownHandler(Looper looper) {
			super(looper);
		}
		
		@Override
		public void handleMessage(Message msg) {
			
			super.handleMessage(msg);
			
			Bundle bundle = msg.getData();
			int second = bundle.getInt(KEY_COUNTDOWN_SECOND);
			
			setResendInfo(second, currentResendRemainTimes);
		}
	}
	
	class CountdownRunnable implements Runnable {
		
		CountdownHandler handler = new CountdownHandler();
		int i = MAX_COUNTDOWN_SECOND - 1;
		
		@Override
		public void run() {
			
			while (i >= 0) {
				
				try {
					Thread.sleep(1000);
					
					Message message = new Message();
					Bundle bundle = new Bundle();
					bundle.putInt(KEY_COUNTDOWN_SECOND, i--);
					message.setData(bundle);
					
					handler.sendMessage(message);
				} catch (Exception ex) {
					Log.e(TAG, "register fail", ex);
				}
			}
		}
	}
}