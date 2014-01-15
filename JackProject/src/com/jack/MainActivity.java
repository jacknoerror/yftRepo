package com.jack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{
	
	Button btn_viewpager,btn_fallpics;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}

	private void init() {
		btn_viewpager = (Button)findViewById(R.id.btn_go_viewpager);
//		btn_viewpager.setId(R.id.btn_go_viewpager);
		btn_viewpager.setOnClickListener(this);
		
		btn_fallpics = (Button)findViewById(R.id.btn_go_fall);
		btn_fallpics.setOnClickListener(this);
	}
	
	private <T> void go(Class<T> clazz){
		Intent intent = new Intent();
		intent.setClass(this, clazz);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		System.out.println(v.getId()+":id");
		switch (v.getId()) {
		case R.id.btn_go_viewpager:
			go(ViewPagerActivity.class);
			break;
		case R.id.btn_go_fall:
			go(FallPicActivity.class);
			break;
		default:
			break;
		}
	}

}
