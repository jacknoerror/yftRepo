package com.qfc.yft.ui.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qfc.yft.R;
import com.qfc.yft.util.JackUtils;

public class StartPagerActivity extends Activity {
	final String[] PATHS = new String[]{"pics/load1.jpg","pics/load2.jpg","pics/load3.jpg","pics/load4.jpg"};
	
	ViewPager sViewPager;
	PagerAdapter sAdapter;
	Handler sHandler;
	
	List<View> viewList;
	List<ImageView> imgList;
	SharedPreferences sPref;
	boolean needRecycle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		init();
		loadImgs();
	}
	private void loadImgs() {
		new Thread(){
			@Override
			public void run() {
				super.run();
				for(int i=0;i<PATHS.length;i++){
					Bitmap bm = JackUtils.getbmFromAssetsFile(getResources(), PATHS[i]);
					if(bm!=null){
						Message msg = new Message();
						msg.what=i;
						msg.obj = bm;
						if(sHandler!=null)sHandler.sendMessage(msg);
					}
				}
			}
		}.start();
		
	}
	private void init() {
		sViewPager = (ViewPager)findViewById(R.id.start_viewpager);
		initPref();
		initHandler();
		initList();
		initAdapter();
		
		sViewPager.setAdapter(sAdapter);
	}
	private void initPref() {
		sPref = getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
	}
	private void initList() {
		viewList = new ArrayList<View>();
		imgList = new ArrayList<ImageView>();
		for(int i=0;i<PATHS.length;i++){
			View view = LayoutInflater .from(this) .inflate(R.layout.layout_start_img, null);
			imgList.add((ImageView)view.findViewById(R.id.img_start));
			viewList.add(view);
		}
	}
	private void initAdapter() {
		sAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {

				return arg0 == arg1;
			}

			@Override
			public int getCount() {

				return viewList.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(viewList.get(position));

			}

			@Override
			public int getItemPosition(Object object) {

				return super.getItemPosition(object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewList.get(position));
				return viewList.get(position);
			}
			
		};
	}
	
	
	private void initHandler() {
		sHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.obj!=null)setImg(msg.what,(Bitmap) msg.obj);
			}
		};
	}
	private synchronized void setImg(int index , Bitmap bm){
		if(imgList!=null&&imgList.size()>index){
			ImageView img = imgList.get(index);
			img.setImageBitmap(bm);
			if(sAdapter!=null) sAdapter.notifyDataSetChanged();
			if(index == PATHS.length-1){
				//last one
				img.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						setPrefAndQuit();
					}
				});
			}
		}
	}
	private void setPrefAndQuit(){
		if(sPref==null) return;
		needRecycle=true;
		Editor editor = sPref.edit();//获取编辑器
		editor.putBoolean(getString(R.string.pref_notfirsttime), true);
		editor.commit();//提交修改
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	@Override
	protected void onDestroy() {
		if(needRecycle){
			for(ImageView img : imgList){
				Bitmap bm = ((BitmapDrawable)(img.getDrawable())).getBitmap();
				if(!bm.isRecycled()){
					bm.recycle();
				}
			}
		}
		super.onDestroy();
	}
}
