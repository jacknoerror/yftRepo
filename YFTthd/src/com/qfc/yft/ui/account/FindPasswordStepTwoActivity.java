package com.qfc.yft.ui.account;

import org.json.JSONException;

import android.widget.Toast;

import com.qfc.yft.R;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.BareReceiver;
import com.qfc.yft.net.action.member.ResetPasswordReq;


public class FindPasswordStepTwoActivity extends AccountStepTwoActivity {
	
	@Override
	protected void initTitle() {
		super.initTitle();
		titleManager.setTitleName(getString(R.string.account_title_find_password));
	}
	
	@Override
	protected void initViews() {
		super.initViews();
		ok.setText(R.string.account_step2_btn_ok_reset);
	}
	
	@Override
	protected void showError() {
		Toast.makeText(this, R.string.account_step2_error_reset, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected Integer getSmsType() {
		return SEND_MOBILE_CODE_TYPE_RESET;
	}
	
	@Override
	protected void confirm() {
		
		ActionRequestImpl actReq = new ResetPasswordReq(getSendPhone(), captcha.getText().toString(), 
				password.getText().toString());
		
		ActionReceiverImpl nRcv = new BareReceiver(this) {
			@Override
			public boolean response(String result) throws JSONException {
				
				boolean resetFlag = super.response(result);
				if(resetFlag) {
					saveUserInfo();
					enter();
				} else {
					showError();
				}
				
				return resetFlag;
			}
		};
		
		ActionBuilder.getInstance().request(actReq , nRcv);
	}
}
