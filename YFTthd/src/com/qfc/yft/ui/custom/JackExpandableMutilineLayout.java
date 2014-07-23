package com.qfc.yft.ui.custom;

import com.qfc.yft.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JackExpandableMutilineLayout extends LinearLayout implements View.OnClickListener {
	View mView;
	
	TextView launchTitle;
	JackMultiLineLayout expandLayout;

	private LayoutInflater mInflater;

	protected View selectedView;//单选

	private final int itemMargin = (int) getResources().getDimension(R.dimen.common_margin);;

	public JackExpandableMutilineLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public JackExpandableMutilineLayout(Context context) {
		super(context);
		init();
	}

	private void init() {
		if (isInEditMode()) { return; }
		mInflater = LayoutInflater.from(getContext());
		mView = mInflater.inflate(R.layout.layout_expandable_multiline_view, this,true);
		launchTitle = (TextView)mView.findViewById(R.id.tv_expmul);
		expandLayout = (JackMultiLineLayout)mView.findViewById(R.id.jmll_expmul);
		
		launchTitle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean expanded = expandLayout.getVisibility()==View.VISIBLE;
				if(expanded){
					expandLayout.setVisibility(View.GONE);
				}else{
					expandLayout.setVisibility(View.VISIBLE);
					AnimationSet aSet = new AnimationSet(true); 
					Animation aScale = new ScaleAnimation(1, 1, 0, 1);
					Animation aAlpha = new AlphaAnimation(0, 1);
//					aScale.setDuration(100);
					aSet.addAnimation(aScale);
					aSet.addAnimation(aAlpha);
					aSet.setDuration(100);
					expandLayout.setAnimation(aSet );
					aSet.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub
//							Log.i("NOW", "height:"+getHeight());
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							;
//							Log.i("NOW",String.format("(%s)(%s)(%s)(%s)", getHeight()+"",getTop()+"",getBottom()+"",""));
						}
					});
				}
				
			}
		});
	}
	
	public void setTitle(String content){
		launchTitle.setText(content);
	}
	
	/**
	 * @param itemContent 
	 * @param idStr 如果选中，返回什么作为参数
	 */
	public void addItem(String itemContent, String idStr){
		TextView tv = (TextView) mInflater.inflate(R.layout.view_tv_stroke_orange, null);
		tv.setText(itemContent);
		tv.setClickable(true);
		tv.setOnClickListener(this);
		tv.setTag(idStr);
		LinearLayout layout = new LinearLayout(getContext());
		layout.addView(tv);
		layout.setPadding(itemMargin, itemMargin, itemMargin, itemMargin);
		expandLayout.addView(layout);
	}
	
	public String getSelectedIdStr(){
		return null!=selectedView?(String)selectedView.getTag():null;
	}

	@Override
	public void onClick(View v) {
		if(null!=selectedView) selectedView.setSelected(false);
		selectedView = v;
		selectedView.setSelected(true);
	}

	public void reset() {
		if(null!=selectedView){
			selectedView.setSelected(false);
			selectedView = null;
		}
	}
}
