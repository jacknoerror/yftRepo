package com.qfc.yft.ui.tab.main.cat;

import com.qfc.yft.R;
import com.qfc.yft.R.color;
import com.qfc.yft.R.id;
import android.content.Context;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class JackRadioViewGroup extends LinearLayout{
	public FrameLayout mFrame,mFrame0;
	protected RadioGroup mRadioGroup;
	protected Context jrContext;
	protected View jrView;
	public static final int RADIO_ID_BASE = 0x413900;
	private LayoutInflater jrInflater;



	public JackRadioViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}


	public JackRadioViewGroup(Context context) {
		super(context);
		init(context);
	}


	/*public final RadioGroup getRadioGroup() {
		return mRadioGroup;
	}*/


	private void  init(Context context ) {
		jrContext = context;
		jrInflater = LayoutInflater.from(jrContext);
		jrView = jrInflater.inflate(R.layout.activity_cat_sch, this, true);
		
		mFrame0 = (FrameLayout) jrView.findViewById(id.frame_catsch_1);
		mFrame = (FrameLayout) jrView.findViewById(id.frame_catsch_2);
//		mRadioGroup = (RadioGroup)jrView.findViewById(id.radiogroup_cmn);
		
		
	}


	/*public void initBtns(String[] rbNames)   {
		int length = rbNames.length;
		if(length<=1) return;
		for(int i=0;i<length;i++){
			int layout = R.layout.view_rb_sub;
			if(i==0){
				 layout = R.layout.view_rb_subleft;
			}else if(i==length-1){
				 layout = R.layout.view_rb_subright;
			}
			RadioButton rb = (RadioButton) jrInflater.inflate(layout, null);
			
			rb.setText(rbNames[i]);
//			rb.setTextAppearance(jrContext, style);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f );
			rb.setLayoutParams(lp);
			rb.setTextColor(jrContext.getResources().getColorStateList(
					color.selector_radio_textcolor));
			rb.setId(JackRadioViewGroup.RADIO_ID_BASE+i);
			mRadioGroup.addView(rb,i );
		}
	}
*/
	/**
	 * @param rgId should be the layout of radioGroup
	 * @param listener
	 * @return 
	 */
	public RadioGroup initBtns(int rgId,  OnCheckedChangeListener listener){
		mRadioGroup = (RadioGroup) jrInflater.inflate(rgId, null);
		mFrame0.addView(mRadioGroup);
		mRadioGroup.setOnCheckedChangeListener(listener);
		return mRadioGroup;
	}



}