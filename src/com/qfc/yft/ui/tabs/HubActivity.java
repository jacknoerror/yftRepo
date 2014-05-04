 package com.qfc.yft.ui.tabs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.data.CachMsg;
import com.qfc.yft.net.OfflineDownloadBuilder;
import com.qfc.yft.ui.tabs.chat.TabChatFragment;
import com.qfc.yft.ui.tabs.main.TabMainFragment;
import com.qfc.yft.ui.tabs.person.TabPersonFragment;
import com.qfc.yft.utils.JackUtils;
import com.umeng.analytics.MobclickAgent;

public class HubActivity extends FragmentActivity implements OnTabChangeListener{
	final String TAG = HubActivity.class.getName();
	final int[]	ICONS = new int[]{R.drawable.selector_tab_home ,
								R.drawable.selector_tab_search ,
								R.drawable.selector_tab_chat ,
								R.drawable.selector_tab_person  };
	final String[] TITLES= new String[]{"首页","搜索","纺织聊","个人中心"};
	final Class[] CLAZZZ=new Class[]{
			TabMainFragment.class,
			TabSearchFragment.class,
			TabChatFragment.class,
			TabPersonFragment.class
	};
	public static TextView staticTvChatCount;
	public static void updateChatTabCount() {
		if(null==staticTvChatCount) return;
		int count = CachMsg.getInstance().fullCount();
		if(count>0	){
			staticTvChatCount.setText(count+"");
			staticTvChatCount.setVisibility(View.VISIBLE);
		}else{
			staticTvChatCount.setVisibility(View.GONE);
		}
	}

	
	Fragment currentFragment;
	private TabHost mTabHost;
	private boolean singleBack;//0310

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK&&!singleBack){
			
			JackUtils.showDialog(this, "确定要退到登录界面么？", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					YftValues.logout();
					HubActivity.this.finish();
					dialog.dismiss();
					mTabHost =null;//
				}
			});
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_host);
        singleBack = getIntent().getBooleanExtra(YftValues.EXTRAS_SINGLEBACK, false);
        
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup( );
        
        mTabHost.setOnTabChangedListener(this);
        
        if(!singleBack)YftData.data().setHostTab(mTabHost);
        
        for(int i=0;i<4;i++){
        	addTabBtn(i);
        }
        int tabId = getIntent().getIntExtra(YftValues.EXTRAS_HUB_TAB, 0);
        mTabHost.setCurrentTab(tabId);
        
        if(YftData.data().getMe().getMemberType()==3&&!singleBack){
        	OfflineDownloadBuilder.setContext(this);
        	OfflineDownloadBuilder.onClick();
        }
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	MobclickAgent.onResume(this);
    	YftData.data().getNotificationManager().cancel(YftValues.notification_id);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	MobclickAgent.onPause(this);
    }
    
    @Override
	public void onTabChanged(String tabId) {
		android.support.v4.app.FragmentManager fm =   getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
		attachFragment(tabId, fm, ft);
	}
    
    private void attachFragment(String tabId,android.support.v4.app.FragmentManager fm,
    		android.support.v4.app.FragmentTransaction ft){
    	Fragment fra = fm.findFragmentByTag(tabId);
    	if(null!=currentFragment) ft.hide(currentFragment);//ft.detach(currentFragment);
    	if(null!=fra) {
//    		ft.attach(fra);
    		ft.show(fra);
    	}else{
    		fra = getNewFragment(tabId);
    		ft.add(R.id.realtabcontent, fra, tabId);
    	}
    	currentFragment = fra;
    	ft.commit();
    	
    }
    
	private Fragment getNewFragment(String tabId) {
//		if(tabId.equalsIgnoreCase(TITLES[0]))
		for(int i=0;i<TITLES.length;i++){
			if(tabId.equalsIgnoreCase(TITLES[i])){
				try {
					return (Fragment)CLAZZZ[i].newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	private void addTabBtn(int index){
    	if(index>=TITLES.length||index>=ICONS.length) return;
    	View view = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
    	
    	ImageView img = (ImageView)view.findViewById(R.id.img_tab);
    	TextView text = (TextView)view.findViewById(R.id.tv_tab);
    	if(index==2){//纺织聊按钮，缓存计数控件
    		staticTvChatCount = (TextView)view.findViewById(R.id.tv_countchat);
    		updateChatTabCount();
    	}
    	
    	text.setTextColor(getResources().getColorStateList(R.color.selector_tab_textcolor));
//    	text.setCompoundDrawablesWithIntrinsicBounds(0, ICONS[index], 0, 0);
    	img.setImageResource(ICONS[index]);
    	img.setScaleType(ScaleType.CENTER_INSIDE);
    	text.setText(TITLES[index]);
    	
    	mTabHost.addTab(mTabHost.newTabSpec(TITLES[index])
    			.setIndicator(view)
//    			.setIndicator(TITLES[index],getResources().getDrawable(R.drawable.ic_launcher))
    			.setContent(new DummiContiFac(getBaseContext())));
//               CLAZZZ[index], null);
//    	mTabHost.addTab(mTabHost.newTabSpec(TITLES[index]).setIndicator(
//    			TITLES[index],getResources().getDrawable(ICONS[index])),CLAZZZ[index],null);
    }
	
	
	public class DummiContiFac implements TabContentFactory{
    	Context dcContext;
    	public DummiContiFac(Context context){
    		dcContext = context;
    	}

		@Override
		public View createTabContent(String tag) {
			return new View(dcContext);
		}
    	
    }


}
