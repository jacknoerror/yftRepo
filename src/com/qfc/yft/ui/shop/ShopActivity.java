package com.qfc.yft.ui.shop;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.TitleManager;
import com.qfc.yft.ui.custom.JackTitle;
import com.qfc.yft.ui.shop.pic.ShActivityPCC;
import com.qfc.yft.ui.shop.pic.ShActivityPCP;
import com.qfc.yft.ui.shop.pro.ShActivityPro;
import com.qfc.yft.util.JackUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 注意点：
 * 1.确保在切换到该Activity时已经给currentUser重新赋值，否则数据将严重错误
 * 2.由于上一个版本界面设计的关系，需要extra参数shopId，shopName，memberType
 * @author taotao
 *
 */
public class ShopActivity extends TabActivity {
	private final String TAG = ShopActivity.class.getSimpleName();
//	final int[] tabTitleRes = new int[]{R.string.shop_1,R.string.shop_2,R.string.shop_3,R.string.shop_4,R.string.shop_5};
	final int[] tabDrawableRes=new int[]{R.drawable.selector_tabbar_home,
										R.drawable.selector_tabbar_lxfs,
										R.drawable.selector_tabbar_cpxx,
										R.drawable.selector_tabbar_gsxq,
										R.drawable.selector_tabbar_zzzs
										};
	private String[] tabTitleNames;
	
	TabHost tabHost;
	TabWidget tabWidget;
	
	Resources resources;
	int tabCount,memberType;// TODO membertype = -1
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_layout);
		getWindow().setBackgroundDrawableResource(R.color.bg_grey);
		initExtras();
//		((JackTitle)findViewById(R.id.jacktitle)).setBackBtnActivity(this);
		TitleManager mTitleManager = new TitleManager(this);
		mTitleManager.initTitleBack();
		
		tabHost = getTabHost();
		tabWidget = tabHost.getTabWidget();
		resources = getResources();
		tabTitleNames = getResources().getStringArray(R.array.shop_tabs);
		if(memberType==3){
			addTabSpec(ShActivityInfo.class, tabTitleNames[0], resources.getDrawable(tabDrawableRes[0]));//0
			addTabSpec(ShActivityAdd.class, tabTitleNames[1], resources.getDrawable(tabDrawableRes[1]));//2
			addTabSpec(ShActivityPro.class, tabTitleNames[2], resources.getDrawable(tabDrawableRes[2]));//1
			addTabSpec(ShActivityPCP.class, tabTitleNames[3], resources.getDrawable(tabDrawableRes[3]));//3
			addTabSpec(ShActivityPCC.class, tabTitleNames[4], resources.getDrawable(tabDrawableRes[4]));//4
		}else{
			addTabSpec(ShActivityInfo.class, tabTitleNames[0], resources.getDrawable(tabDrawableRes[0]));//0
			addTabSpec(ShActivityPro.class, tabTitleNames[2], resources.getDrawable(tabDrawableRes[2]));//1
			addTabSpec(ShActivityAdd.class, tabTitleNames[1], resources.getDrawable(tabDrawableRes[1]));//2
		}
		
		tabHost.setCurrentTab(tabCount);//默认 ?
		tabWidget.setStripEnabled(false);
		tabWidget.setDividerDrawable(null);
	}
	
	@Override
    protected void onResume() {
    	super.onResume();
    	MobclickAgent.onResume(this);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	MobclickAgent.onPause(this);
    }
	
	private void initExtras() {
		TextView tv = (TextView)findViewById(R.id.tv_title);
		Intent intent = getIntent();
		String title = intent.getStringExtra(NetConst.EXTRAS_SHOP_NAME);
		
		memberType = intent.getIntExtra(NetConst.EXTRAS_SHOP_MEMBER_TYPE,-1);
		tabCount = intent.getIntExtra(NetConst.EXTRAS_SHOP_TAB, memberType==3?2:1);
		if(null==title||title.isEmpty()) title = "我的商铺";
		else if(title.length()>10) tv.setPadding(JackUtils.dip2px(this, 50), 0, 0, 0);
		tv.setText(title);
	}
	private <T> void addTabSpec(Class<T> clazz , String tabString, Drawable tabDrawable){//1118
		if(tabHost==null) return;
		TabHost.TabSpec spec;
		Intent intent;
		intent = new Intent().setClass(this, clazz);
		
		spec = tabHost.newTabSpec(tabString)
//				.setIndicator(tabString, tabDrawable)
				.setIndicator(getTabView(tabString, tabDrawable))
				.setContent(intent);
		tabHost.addTab(spec); 
	}
	private View getTabView(String tabString, Drawable tabDrawable){
		View layout = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
		ImageView img = (ImageView)layout.findViewById(R.id.img_tab);
		TextView text = (TextView)layout.findViewById(R.id.tv_tab);
		
		img.setImageDrawable(tabDrawable);
		text.setText(tabString);
		text.setTextSize(12);
//		text.setTextColor(resources.getColor(android.R.color.black));
		text.setTextColor(resources.getColorStateList(R.color.selector_tab_textcolor_black ));
		layout.setBackgroundResource(tabString.equals(tabTitleNames[2])?R.drawable.shop_bg_2:R.drawable.shop_bg_1);
		return layout;
	}
	
}
