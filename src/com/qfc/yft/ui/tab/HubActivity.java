 package com.qfc.yft.ui.tab;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.qfc.yft.data.MyData;
import com.qfc.yft.ui.custom.JackFragmentTabChangedHelper;
import com.qfc.yft.ui.offline.OfflineHelper;
import com.qfc.yft.ui.tab.chat.TabChatFragment;
import com.qfc.yft.ui.tab.main.TabMainFragment;
import com.qfc.yft.ui.tab.person.TabPersonFragment;
import com.qfc.yft.ui.tab.work.TabWorkFragment;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.User;
import com.umeng.analytics.MobclickAgent;

public class HubActivity extends FragmentActivity implements OnTabChangeListener{
	final String TAG = HubActivity.class.getName();
	final int[]	ICONS = new int[]{R.drawable.selector_tab_home ,
								R.drawable.selector_tab_chat ,
								R.drawable.selector_tab_work ,
								R.drawable.selector_tab_person  };
	final String[] TITLES= new String[]{"首页","纺织聊","工作台","个人中心"};
	final Class[] CLAZZZ=new Class[]{
			TabMainFragment.class,
			TabChatFragment.class,
			TabWorkFragment.class,
			TabPersonFragment.class
	};
	/*public static TextView staticTvChatCount;
	public static void updateChatTabCount() {
		if(null==staticTvChatCount) return;
		int count = CachMsg.getInstance().fullCount();
		if(count>0	){
			staticTvChatCount.setText(count+"");
			staticTvChatCount.setVisibility(View.VISIBLE);
		}else{
			staticTvChatCount.setVisibility(View.GONE);
		}
	}*/

	
//	Fragment currentFragment;
	private TabHost mTabHost;
//	private boolean singleBack;//0310
	private JackFragmentTabChangedHelper jftcl;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK ){
			
			JackUtils.showDialog(this, "确定要退到登录界面么？", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
//					YftValues.logout();//TODO
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
//        singleBack = getIntent().getBooleanExtra(YftValues.EXTRAS_SINGLEBACK, false);
        jftcl = new JackFragmentTabChangedHelper(this, R.id.realtabcontent);
        for(int i=0;i<CLAZZZ.length;i++){
        	jftcl.addTabPack(jftcl.new TabPack(ICONS[i], TITLES[i], CLAZZZ[i]));
        }
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup( );
        mTabHost.setOnTabChangedListener(this);
        for(int i=0;i<CLAZZZ.length;i++){
			addTabBtn(i);
		}
//        if(!singleBack)YftData.data().setHostTab(mTabHost);
        
//        int tabId = getIntent().getIntExtra(YftValues.EXTRAS_HUB_TAB, 0);
        mTabHost.setCurrentTab(0);
        
        MyData.data().setTabHost(mTabHost);
        User me = MyData.data().getMe();
		if(me.getMemberType()==3){//&&!singleBack){
        	OfflineHelper.getInstance().init(this).initKeeper(me.getShopId());
        	OfflineHelper.getInstance().checkUpdateStatus(false);
//        	OfflineDownloadBuilder.setContext(this);
//        	OfflineDownloadBuilder.onClick();
        }
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	MobclickAgent.onResume(this);
//    	YftData.data().getNotificationManager().cancel(YftValues.notification_id);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	MobclickAgent.onPause(this);
    }
    
    @Override
	public void onTabChanged(String tabId) {
    	if(null!=jftcl) jftcl.onTabChanged(tabId);
	}
    
    
	private void addTabBtn(int index){
    	if(index>=TITLES.length||index>=ICONS.length) return;
    	View view = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
    	
    	ImageView img = (ImageView)view.findViewById(R.id.img_tab);
    	TextView text = (TextView)view.findViewById(R.id.tv_tab);
    	/*if(index==2){//纺织聊按钮，缓存计数控件
    		staticTvChatCount = (TextView)view.findViewById(R.id.tv_countchat);
    		updateChatTabCount();
    	}*/
    	
    	text.setTextColor(getResources().getColorStateList(R.color.selector_tab_textcolor));
    	img.setImageResource(ICONS[index]);
    	img.setScaleType(ScaleType.CENTER_INSIDE);
    	text.setText(TITLES[index]);
    	
    	mTabHost.addTab(mTabHost.newTabSpec(TITLES[index])
    			.setIndicator(view)
    			.setContent(new DummiContiFac(getBaseContext())));
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
