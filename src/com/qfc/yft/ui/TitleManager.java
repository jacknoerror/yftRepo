package com.qfc.yft.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.MyData;
import com.qfc.yft.ui.custom.JackTitle;


/**
 *should be no specific
 * @author tao
 */
public class TitleManager implements View.OnClickListener{
	JackTitle jackTitle;
	TextView tv_titlename,tv_right,tv_left;
	ImageView btn_titleleft,btn_titleright,img_logo;
	
	View cartView;
	TextView cartTv;
	
	Activity activity;
	View mView;
	
	public TitleManager(Activity activity) {
		super();
		this.activity = activity;
		init();
	}
	public TitleManager(View view){
		this.mView = view;
		init();
	}
	private void init() {
		tv_titlename = (TextView) titleView().findViewById(R.id.tv_title);
		tv_left = (TextView) titleView().findViewById(R.id.tv_title_left);
		tv_right = (TextView) titleView().findViewById(R.id.tv_title_right);
		btn_titleleft = (ImageView) titleView().findViewById(R.id.btn_title_left);
		btn_titleright = (ImageView) titleView().findViewById(R.id.btn_title_right);
		img_logo = (ImageView) titleView().findViewById(R.id.img_title_logo);
	}
	
	public void setTitleName(String name){
		tv_titlename.setText(name);
	}
	public void setRightText(String text, View.OnClickListener listener){
		setTitleText(tv_right,text, listener);
	}
	public void setLeftText(String text, View.OnClickListener listener){
		setTitleText(tv_left,text, listener);
	}
	public void setLeftImg(int rid,View.OnClickListener listener){
		setTitleImg(btn_titleleft,rid,listener);
	}
	public void setRightImg(int rid,View.OnClickListener listener){
		setTitleImg(btn_titleright,rid,listener);
	}
	public void hideBothSides(){
		tv_right.setVisibility(View.GONE);
		tv_left.setVisibility(View.GONE);
		btn_titleleft.setVisibility(View.GONE);
		btn_titleright.setVisibility(View.GONE);
	}
	
	/**
	 * @param text
	 * @param listener
	 */
	private void setTitleText(TextView tv ,String text, View.OnClickListener listener) {
		if(null==text) {tv.setVisibility(View.GONE);return;}
		tv.setText(text);
		tv.setOnClickListener(listener);
		tv.setVisibility(View.VISIBLE);
	}
	private void setTitleImg(ImageView img, int rid,
			OnClickListener listener) {
		if(rid==0) {img.setVisibility(View.GONE);return;}
		img.setImageResource(rid);
		img.setOnClickListener(listener);
		img.setVisibility(View.VISIBLE);
		
	}
	public void initTitleLogo(){
		img_logo = (ImageView) titleView().findViewById(R.id.img_title_logo);
		img_logo.setVisibility(View.VISIBLE);
	}
	public void initTitleBack(){
		setTitleImg(btn_titleleft, R.drawable.btn_back, this);
	}
	
	public JackTitle titleView(){
		if(null==jackTitle) jackTitle = (JackTitle)findView(R.id.jacktitle);
		return jackTitle;
	}
	private View findView(int id){
		if(null!=activity) return activity.findViewById(id);
		if(null!=mView) return mView.findViewById(id);
		return null;
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_left:
			if(null!=activity)activity.finish();
			break;
		default:
			break;
		}
		
	}
}
