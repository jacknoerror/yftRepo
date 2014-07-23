package com.qfc.yft.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qfc.yft.R;
import com.qfc.yft.util.ShareHelper;

public class CurProdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_cp);
		Button btn = (Button) this.findViewById(R.id.btn_test);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showShareLayout();
			}
		});
		btn.setText("btn");

	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
		if (null != shareHelper)
			shareHelper.callback(requestCode, resultCode, data);
	}

	final int[] SHAREICONID = new int[] { R.drawable.share_wechat,
			R.drawable.share_pengyou, R.drawable.share_weibo };
	final String[] SHAREICONNAME = new String[] { "微信", "朋友圈", "微博" };
	private View shareLayout;
	/*
	 * cde3b539088694d4b74c55d852de3844 8f88de9693d22430ad7ce55047ec7946
	 */
	private ShareHelper shareHelper;

	protected void showShareLayout() {
		shareHelper = new ShareHelper(this);
		shareHelper.showShareDialog();
	}

}
