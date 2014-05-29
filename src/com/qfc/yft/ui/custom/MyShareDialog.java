package com.qfc.yft.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.YftApplication;
import com.qfc.yft.YftValues;
import com.qfc.yft.utils.JackButtonColorFilter;
import com.qfc.yft.utils.JackUtils;

public class MyShareDialog extends Dialog {
	final int[] SHAREICONID = new int[]{R.drawable.share_wechat,R.drawable.share_pengyou,R.drawable.share_weibo};
	final String[] SHAREICONNAME = new String[]{"微信","朋友圈","微博"};
	Context context;
	private android.view.View.OnClickListener itemListener;
	
	public MyShareDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merge_share);
		//获得当前窗体
		Window window = getWindow();
		window .setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.mysharedialogstyle);
		window.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = (int)(YftValues.SCREEN_WIDTH); //设置宽度
		window.setAttributes(lp);
		initShareLayout();
	}
	
	private void initShareLayout() {
		LinearLayout iconLayoutInShare = (LinearLayout)this.findViewById(R.id.layout_share_icons);
		View cancel = this.findViewById(R.id.btn_share_cancel);
		JackButtonColorFilter.setButtonFocusChanged(cancel);
		
		for(int i = 0;i<SHAREICONID.length; i++){
			TextView tv = new TextView(context);
			tv.setText(SHAREICONNAME[i]);
			tv.setCompoundDrawablesWithIntrinsicBounds(0, SHAREICONID[i], 0, 0);
			iconLayoutInShare.addView(tv);
			int padding = JackUtils.dip2px(context, 12);
			tv.setPadding(padding*2, padding, padding*2, padding);
			tv.setGravity(Gravity.CENTER);
			tv.setId(SHAREICONID[i]);
			tv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(null!=itemListener) itemListener.onClick(v);
					dismiss();//0529
				}
			});
		}
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public final void setItemListener(android.view.View.OnClickListener itemListener) {
		this.itemListener = itemListener;
	}
	
	
}
