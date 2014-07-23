package com.qfc.yft.ui.account;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.ui.custom.JackResizeLayout;
import com.qfc.yft.utils.JackButtonColorFilter;
import com.qfc.yft.utils.JackUtils;

public class StartLoginActivity extends Activity {
	final String TAG = StartLoginActivity.class.getSimpleName();
	
	EditText et1,et2;
	TextView tv1,tv2;
	Button sButton;
	LinearLayout sLayout;
	Rect sLayPadRect;
	int sLayHeight;
	JackResizeLayout resizeLayout;
	
	Handler mHandler;
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
					break;
				case 2://变大
					sLayout.setPadding(sLayPadRect.left, sLayPadRect.top, sLayPadRect.right, sLayPadRect.bottom);
					break;
				default:
					break;
				}
			}
		};
	}

	private void initViews() {
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
		
		tv1 = (TextView)findViewById(R.id.tv_register);
		tv2 = (TextView)findViewById(R.id.tv_guest);
		JackUtils.textpaint_underline(tv1);
		JackUtils.textpaint_underline(tv2);
	}
	
	
	
	/*@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if(et1!=null)Log.i(TAG, action+"--"+et1.isEnabled()+":::enable");
		return super.onTouchEvent(event);
	}*/
	
}
