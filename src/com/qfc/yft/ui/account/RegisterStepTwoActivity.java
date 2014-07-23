package com.qfc.yft.ui.account;

import org.json.JSONException;

import android.widget.Toast;

import com.qfc.yft.R;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.BareReceiver;
import com.qfc.yft.net.action.member.RegisterByMobileReq;


public class RegisterStepTwoActivity extends AccountStepTwoActivity {
	
	@Override
	protected void initTitle() {
		super.initTitle();
		titleManager.setTitleName(getString(R.string.account_title_register));
	}
	
	@Override
	protected void initViews() {
		super.initViews();
		ok.setText(R.string.account_step2_btn_ok_register);
	}
	
	@Override
	protected void showError() {
		Toast.makeText(this, R.string.account_step2_error_register, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected Integer getSmsType() {
		return SEND_MOBILE_CODE_TYPE_REGISTER;
	}
	
	@Override
	protected void confirm() {
		
		ActionRequestImpl actReq = new RegisterByMobileReq(getSendPhone(), captcha.getText().toString(), 
				password.getText().toString());
		
		ActionReceiverImpl nRcv = new BareReceiver(this) {
			@Override
			public boolean response(String result) throws JSONException {
				
				boolean registerFlag = super.response(result);
				if(registerFlag) {
					saveUserInfo();
					enter();
				} else {
					showError();
				}
				
				return registerFlag;
			}
		};
		
		ActionBuilder.getInstance().request(actReq , nRcv);
	}
}
