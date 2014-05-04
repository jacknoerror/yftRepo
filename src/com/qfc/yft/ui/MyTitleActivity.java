package com.qfc.yft.ui;

import com.qfc.yft.R;
import com.qfc.yft.ui.custom.JackTitle;

import android.app.Activity;

public class MyTitleActivity extends Activity {
	JackTitle jTitle;
	protected void setBackBtnAlive(){
		if(null==jTitle) jTitle = (JackTitle)findViewById(R.id.jacktitle);
		if(null!=jTitle) jTitle.setBackBtnActivity(this);
		
	}
}
