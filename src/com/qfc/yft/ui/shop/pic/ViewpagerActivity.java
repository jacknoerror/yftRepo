package com.qfc.yft.ui.shop.pic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.ui.MyPagerAdapater;
import com.qfc.yft.ui.custom.MyImageView;
import com.qfc.yft.util.JackImageLoader;

public class ViewpagerActivity extends Activity implements ViewPager.OnPageChangeListener,OnTouchListener{
	@SuppressWarnings("unused")
	private final String TAG = ViewpagerActivity.class.getSimpleName();
	
	ViewPager vpager;
	String[] vpaths;
	int curPage;
	List<MyImageView> vImages;
	
	GestureDetector gestureScanner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		vpager = (ViewPager)findViewById(R.id.viewpager_showpicture);
		vpager.setOnPageChangeListener(this);
		vImages = new ArrayList<MyImageView>();
		vpager.setOnTouchListener(this);
		gestureScanner = new GestureDetector(this, new MySimpleGesture());
		initExtras();
		if(vpaths!=null) initData();
		
	}
	private void initData() {
		List<View> vList=new ArrayList<View>();
		for(String p:vpaths){//����·����
			FrameLayout view = new FrameLayout(this);
			ImageView beforeImg = new ImageView(this);
			beforeImg.setImageResource(android.R.drawable.ic_menu_gallery);
			beforeImg.setScaleType(ScaleType.CENTER_INSIDE);
			view.addView(beforeImg);//0519
			MyImageView img = new MyImageView(this);
			img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
//			img.setImageResource(android.R.drawable.ic_menu_gallery);
			img.setScaleType(ScaleType.MATRIX);//0519
			view.addView(img);
			vImages.add(img);
			vList.add(view);
//			vpager.addView(img);
			
			if(p.equals(vpaths[curPage]	)) JackImageLoader.justSetMeImage(p, img);
		}
		vpager.setAdapter(new MyPagerAdapater(vList));
		vpager.setCurrentItem(curPage, true);
	}
	private void initExtras(){
		Intent intent = getIntent();
		vpaths = intent.getStringArrayExtra(Const.EXTRAS_SHOWPIC_PATHS);
		if(vpaths!=null){
			curPage= intent.getIntExtra(Const.EXTRAS_SHOWPIC_PAGE, 0);
			if(curPage>=vpaths.length) curPage=0;
		}
	}
	/**
	 * Ϊ��ǰҳ����ͼƬ
	 */
	private void loadCurPage(){
		String url = vpaths[curPage];
		if(null==url||url.equals("")) return;
		JackImageLoader.justSetMeImage(url, vImages.get(curPage));
		vpaths[curPage]="";//���غ��Ϳ�
	}
	
	private boolean isScaleDirty(MyImageView imageView){
		return imageView.getScale()> imageView.getMiniZoom();
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (gestureScanner != null) {
			return gestureScanner.onTouchEvent(event)||isScaleDirty(vImages.get(curPage));
		}
		return false;
	};
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
//		Log.i(TAG, arg1+"::$:::%::"+arg2);
		/*if(isScaleDirty(vImages.get(arg0)))//TODO ����
			vpager.scrollTo(arg0*screenWidth, 0); */
	}
	@Override
	public void onPageSelected(int arg0) {
		curPage = arg0;
		loadCurPage();
		
	}
	
	class MySimpleGesture extends SimpleOnGestureListener {
		// �����µĵڶ���Touch downʱ����
		@Override
		public boolean onDoubleTap(MotionEvent e) {
				MyImageView imageView = vImages.get(curPage);
/*				if (imageView.getScale() > imageView.getMiniZoom()) {
					imageView.zoomTo(imageView.getMiniZoom());
				} else {
					imageView.zoomTo(imageView.getMaxZoom());
				}*/				 
//				imageView.setPivotX(e.getX());
//				imageView.setPivotX(e.getY());
//Log.i(TAG, "scale::"+imageView.getScale()+"__"+imageView.getMiniZoom());
//				imageView.setScaleType(ScaleType.MATRIX);
				if(isScaleDirty(imageView)){
					imageView.zoomTo(imageView.getMiniZoom());
//					imageView.setScaleX(1);
				}else{
					imageView.zoomTo(imageView.getMaxZoom());
				}

			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// Logger.LOG("onSingleTapConfirmed",
			// "onSingleTapConfirmed excute");
			// mTweetShow = !mTweetShow;
			// tweetLayout.setVisibility(mTweetShow ? View.VISIBLE
			// : View.INVISIBLE);
			finish();
			return false;
		}
		
		float baseValue;
		float originalScale;
		@Override
		public boolean onDown(MotionEvent e) {
			MyImageView imageView = vImages.get(curPage);

				if (e.getAction() == MotionEvent.ACTION_DOWN) {
					baseValue = 0;
					originalScale = imageView.getScale();
				}
				/*if (e.getAction() == MotionEvent.ACTION_MOVE) {//unavailable here
					if (e.getPointerCount() == 2) {
						float x = e.getX(0) - e.getX(1);
						float y = e.getY(0) - e.getY(1);
						float value = (float) Math.sqrt(x * x + y * y);// ��������ľ���
						// System.out.println("value:" + value);
						if (baseValue == 0) {
							baseValue = value;
						} else {
							float scale = value / baseValue;// ��ǰ�����ľ��������ָ����ʱ�����ľ��������Ҫ���ŵı�����
							// scale the image
							imageView.zoomTo(originalScale * scale, x
									+ e.getX(1), y + e.getY(1));

						}
					}
				}*/
			return false;
		
		}
		int kEvent = KEY_INVALID; //invalid
		public static final int KEY_INVALID = -1;
		float vvv[] = new float[9];
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {

			MyImageView imageView = vImages.get(curPage);
				
				float xdistance = calXdistance(e1, e2);
				float min_distance = Const.SCREEN_WIDTH / 4f;
				if (isScrollingLeft(e1, e2) && xdistance > min_distance) {
					kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
				} else if (!isScrollingLeft(e1, e2) && xdistance > min_distance) {
					kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
				}
				

				Matrix m = imageView.getImageMatrix();
				m.getValues(vvv);
				// ͼƬʵʱ��������������
				float left, right , bottom, top;
				// ͼƬ��ʵʱ����
				float width = imageView.getScale() * imageView.getImageWidth();
				float height = imageView.getScale() * imageView.getImageHeight();
				
				if ((int) width <= Const.SCREEN_WIDTH
						&& (int) height <= Const.SCREEN_HEIGHT)// ���ͼƬ��ǰ��С<��Ļ��С��ֱ�Ӵ������¼�
				{
					super.onScroll(e1, e2, distanceX, distanceY);
					return false;
				} else {     
					left = vvv[Matrix.MTRANS_X];
					right = left + width;
					bottom = vvv[Matrix.MTRANS_Y];
					top = bottom+height;
					Rect r = new Rect();
					imageView.getGlobalVisibleRect(r);

					float dx=-distanceX,dy=-distanceY;
//					Log.i(TAG, System.currentTimeMillis()+"_let's see:::"+right+"--rl--"+left);
					if (distanceX > 0)// ���󻬶�
					{
						if (r.left > 0 || right < Const.SCREEN_WIDTH) {// �жϵ�ǰImageView�Ƿ���ʾ��ȫ
							super.onScroll(e1, e2, distanceX, distanceY);
							dx=0;
						} else {
//							imageView.postTranslate(-distanceX, -distanceY);
						}
					} else if (distanceX < 0)// ���һ���
					{
						if (r.right < Const.SCREEN_WIDTH || left > 0) {
							super.onScroll(e1, e2, distanceX, distanceY);
							dx=0;
						} else {
//							imageView.postTranslate(-distanceX, -distanceY);//-distanceY
						}
					}
					
					if (distanceY > 0)// ���󻬶�
					{
						if (top< Const.SCREEN_HEIGHT) {// �жϵ�ǰImageView�Ƿ���ʾ��ȫ
							super.onScroll(e1, e2, distanceX, distanceY);
							dy=0;
						} else {
//							imageView.postTranslate(0,-distanceY);
						}
					} else if (distanceY < 0)// ���һ���
					{
						if (bottom > 0) {
							super.onScroll(e1, e2, distanceX, distanceY);
							dy=0;
						} else {
//							imageView.postTranslate(0,-distanceY);//-distanceY
						}
					}
					imageView.postTranslate(dx, dy);
				}

			return true;
		
		}
		
		private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
			return e2.getX() > e1.getX();
		}
		
		private float calXdistance(MotionEvent e1, MotionEvent e2) {
			return Math.abs(e2.getX() - e1.getX());
		}
	}
}
