package com.qfc.yft.ui.account;

import android.content.Intent;
import android.view.View;

import com.qfc.yft.R;


public class FindPasswordStepOneActivity extends AccountStepOneActivity {
	
	@Override
	protected void initTitle() {
		super.initTitle();
		titleManager.setTitleName(getString(R.string.account_title_find_password));
	}
	
	@Override
	protected void initViews() {
		
		super.initViews();
		
		phone.setHint(R.string.account_step1_hint_phone_find_password);
		ok.setText(R.string.account_step1_btn_ok_find_password);
		license.setVisibility(View.GONE);
	}
	
	@Override
	protected void resetPhoneHint() {
		phone.setHint(R.string.account_step1_hint_phone_find_password);
	}
	
	@Override
	protected void jumpToStepTwo() {
		
		Intent intent = new Intent();
		intent.setClass(this, FindPasswordStepTwoActivity.class);
		startActivity(intent);
		
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	
	@Override
	protected Integer getSmsType() {
		return SEND_MOBILE_CODE_TYPE_RESET;
	}
}
